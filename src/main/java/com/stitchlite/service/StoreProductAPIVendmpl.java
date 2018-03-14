package com.stitchlite.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.jboss.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.stitchlite.entity.Token;
import com.stitchlite.entity.Product;
import com.stitchlite.entity.ProductDetail;
import com.stitchlite.entity.Store;

import com.stitchlite.entity.Variant;
import com.stitchlite.entity.Product.ProductBuilder;
import com.stitchlite.entity.Variant.VariantBuilder;
import com.stitchlite.util.HttpClientUtil;

public class StoreProductAPIVendmpl implements StoreProductAPI{
	Logger logger = Logger.getLogger(StoreProductAPIVendmpl.class);
@Autowired
StoreAccessService storeAccessService;
	
	private String products = "products";
	private String name = "base_name";
	private String description = "description";
	private String variants = "variants";
	private String sku = "sku";
	private String inventory = "inventory";
	private String qty = "count";
	private String price = "price";
	private String has_variants ="has_variants";
	
	private String baseUrl = "https://storeUrl";
	private String productApiPath ="/api/products";
	private String tokenPath = "/api/1.0/token";
	
	@Override
	public List<ProductDetail> getAllProducts(Store store) {
		String storeurl = store.getBaseStoreUrl();
		baseUrl = baseUrl.replace("storeUrl", storeurl);
		String tokenurl = baseUrl+ tokenPath;
		
		long stitchId = store.getStitchID();
	    	Token token = storeAccessService.getTokenFromStichId(stitchId);
		String accessToken = new HttpClientUtil().getAccessTokenFromRefreshToken(tokenurl,token.getRefreshToken(),store.getClientID(),store.getClientSecret());
        String authHeader = "Authorization";
        String authHeaderValue = "Bearer "+ accessToken;
        List<ProductDetail>  productsList = new ArrayList();
        try {
        String productUrl = baseUrl+productApiPath;
        HashMap< String,String> headers = new HashMap<>();
        headers.put(authHeader, authHeaderValue);
        HttpResponse response = new HttpClientUtil().httpGetRequest(productUrl,headers);
		String responseAsString = EntityUtils.toString(response.getEntity());
		//System.out.println(responseAsString);
		JSONArray productsArr= new JSONArray();
		try {
	     JSONObject productsJson = new JSONObject(responseAsString);
		 productsArr = (JSONArray) productsJson.get(products);
		}
		catch(Exception e) {
			e.getMessage();
		}
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
				 try {
					 JSONObject inventoryObj = productJson.getJSONArray(inventory).getJSONObject(0);
				Variant parentvariant = new VariantBuilder().
						sku(productJson.getString(sku)).
		 				price(productJson.getDouble(price)).
		 				qty(inventoryObj.getInt(qty)).
		 				build();  
				variantList.add(parentvariant);
				 }catch(Exception e) {
					 
				 }
				//check if it has variants 
				boolean hasVariant =  productJson.getBoolean(has_variants);
				 if(hasVariant) {
					    String handleCurr = productJson.getString("handle");
						String variantsurl= productUrl+"?handle="+handleCurr;
						
						HttpResponse responseProWithVar = new HttpClientUtil().httpGetRequest(variantsurl,headers);
						String varAsString = EntityUtils.toString(responseProWithVar.getEntity());
						JSONObject variantsJson = new JSONObject(varAsString); 
						JSONArray varArr = (JSONArray) variantsJson.get(products);
						int len = varArr.length();
						for(int j=0;j<len;j++) {
							try {
							JSONObject vJson = varArr.getJSONObject(j);
							boolean hasVariant_In =  vJson.getBoolean(has_variants);
							if(hasVariant_In) {
								//parent node already added so skip
								continue;
							}
						    try {
							JSONObject inventoryObjTemp = vJson.getJSONArray(inventory).getJSONObject(0);
							Variant  variant = new VariantBuilder().
									sku(vJson.getString(sku)).
					 				price(vJson.getDouble(price)).
					 				qty(inventoryObjTemp.getInt(qty)).
					 				build();
							variantList.add(variant);
						    } catch (Exception e) {
						    	
						    }
							}catch(Exception e) {
								e.printStackTrace();
								
							}
						} //variants loop
				 } // if parent has variant
				  
				 productsList.add(new ProductDetail(product, variantList));
			}//if it is a parent node
				
			
		}//all products loop
			
		}catch(Exception e) {
			logger.error("Exception "+e.getMessage());
		}
	   productsList.toString();
        
		return productsList;
	}

}
