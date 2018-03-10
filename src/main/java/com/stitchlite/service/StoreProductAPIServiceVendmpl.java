package com.stitchlite.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.stitchlite.entity.Product;
import com.stitchlite.entity.ProductDetail;
import com.stitchlite.entity.Store;
import com.stitchlite.entity.Variant;
import com.stitchlite.entity.Product.ProductBuilder;
import com.stitchlite.entity.Variant.VariantBuilder;
import com.stitchlite.util.HttpClientUtil;

public class StoreProductAPIServiceVendmpl implements StoreProductAPIService{

	private String products = "products";
	private String name = "name";
	private String description = "description";
	private String variants = "variants";
	private String sku = "sku";
	private String inventory = "inventory";
	private String qty = "count";
	private String price = "price";
	private String has_variants ="has_variants";
	
	private String baseUrl = "https://storeUrl/api/products";
	
	@Override
	public List<ProductDetail> getAllProducts(Store store) {
		
			
		//temporary hardcoding for 1 store - in future details will be stored in DB and available in store obj
	    // current implementation of store using personal token 
		// must be enhanced for oauth
        String accessToken = "KWDZNSo67gRgRa2UeIXOj_ZBIelrtzjzyxqVXO00";
        String authHeader = "Authorization";
        String authHeaderValue = "Bearer "+ accessToken;
        baseUrl = baseUrl.replace("storeUrl", "rarity.vendhq.com");
        List<ProductDetail>  productsList = new ArrayList();
        try {
 
        HashMap< String,String> headers = new HashMap<>();
        headers.put(authHeader, authHeaderValue);
        HttpResponse response = new HttpClientUtil().httpGetRequest(baseUrl,headers);
		String responseAsString = EntityUtils.toString(response.getEntity());
		//System.out.println(responseAsString);
		
		JSONObject productsJson = new JSONObject(responseAsString);
		
		JSONArray productsArr = (JSONArray) productsJson.get(products);
		int productsArrSize = productsArr.length();
		int i;
		for(i=0;i<productsArrSize;i++) {
			
			JSONObject productJson = productsArr.getJSONObject(i);
			/* */
			//from parent get all variants using handle
			String varparentId = productJson.getString("variant_parent_id") ;
			if(varparentId.length()==0 || productJson.getString("variant_parent_id") == null) {
				
				 List<Variant> variantList = new ArrayList<>();
				//get parent product
				Product product = new ProductBuilder().
						//merchantID()
						name(productJson.getString(name)).
						description(productJson.getString(description))
						.build();
				//create the base product as a variant
				JSONObject inventoryObj = productJson.getJSONArray(inventory).getJSONObject(0);
				
				Variant parentvariant = new VariantBuilder().
						sku(productJson.getString(sku)).
		 				price(productJson.getDouble(price)).
		 				qty(inventoryObj.getInt(qty)).
		 				build();  
				variantList.add(parentvariant);
				
				//check if it has variants 
				boolean hasVariant =  productJson.getBoolean(has_variants);
				 if(hasVariant) {
					    String handleCurr = productJson.getString("handle");
						String variantsurl= baseUrl+"?handle="+handleCurr;
						HttpResponse responseProWithVar = new HttpClientUtil().httpGetRequest(variantsurl,headers);
						String varAsString = EntityUtils.toString(responseProWithVar.getEntity());
					
						//all products with the same handle
						JSONObject variantsJson = new JSONObject(varAsString); 
						JSONArray varArr = (JSONArray) variantsJson.get(products);
						int len = varArr.length();
						for(int j=0;j<len;j++) {
							JSONObject vJson = varArr.getJSONObject(j);
							boolean hasVariant_In =  vJson.getBoolean(has_variants);
							if(hasVariant_In) {
								//parent node already added so skip
								continue;
							}
							JSONObject inventoryObjTemp = vJson.getJSONArray(inventory).getJSONObject(0);
							Variant  variant = new VariantBuilder().
									sku(vJson.getString(sku)).
					 				price(vJson.getDouble(price)).
					 				qty(inventoryObj.getInt(qty)).
					 				build();
							variantList.add(variant);
							
						} //variants loop
				 } // if parent has variant
				  
				 productsList.add(new ProductDetail(product, variantList));
			}//if it is a parent node
				
			
		}//all products loop
			
		}catch(Exception e) {
			System.out.println("Exception "+e.getMessage());
		}
	   
		return productsList;
	}

}
