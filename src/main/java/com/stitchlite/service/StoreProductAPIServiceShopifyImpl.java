package com.stitchlite.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.stitchlite.entity.Product;
import com.stitchlite.entity.Product.ProductBuilder;
import com.stitchlite.entity.Variant.VariantBuilder;
import com.stitchlite.util.HttpClientUtil;
import com.stitchlite.entity.ProductDetail;
import com.stitchlite.entity.Store;
import com.stitchlite.entity.Variant;

public class StoreProductAPIServiceShopifyImpl implements StoreProductAPIService{
	private String products = "products";
	private String name = "title";
	private String description = "body_html";
	private String variants = "variants";
	private String sku = "sku";
	private String qty = "inventory_quantity";
	private String price = "price";
	private String baseUrl = "https://apikey:password@storeUrl/admin/products.json";
	
	@Override
	public List<ProductDetail> getAllProducts(Store store) {
		
		/*String apikey = store.getClientID();
		String password = store.getClientSecret();
		String storeUrl = store.getBaseStoreUrl(); */
		
		//temporary hardcoding for 1 store - in future details will be stored in DB and available in store obj
	
        String apikey = "86801bdba2050e40df1fd39866bd79de";
        String password	 =	"d4bb910a4da9f050d6426d15bc526326";
        String storeUrl = "rarity-sf.myshopify.com";
        baseUrl = baseUrl.replace("apikey", apikey);
        baseUrl = baseUrl.replace("password", password);
        baseUrl = baseUrl.replace("storeUrl", storeUrl);
		
		System.out.println("base url ********* "+ baseUrl);
        List<ProductDetail>  productsList = new ArrayList();
        try {
 
        HashMap<String,String> headers = new HashMap<String,String>();
        HttpResponse response = new HttpClientUtil().httpGetRequest(baseUrl,headers);
		String responseAsString = EntityUtils.toString(response.getEntity());
		System.out.println(responseAsString);
		
		JSONObject productsJson = new JSONObject(responseAsString);
		
		JSONArray productsArr = (JSONArray) productsJson.get(products);
		int productsArrSize = productsArr.length();
		int i;
		for(i=0;i<productsArrSize;i++) {
			
			JSONObject productJson = productsArr.getJSONObject(i);
			Product product = new ProductBuilder().
					//merchantID()
					name(productJson.getString(name)).
					description(productJson.getString(description))
					.build();
			 
			 
			JSONArray variantsArr = (JSONArray) productJson.get(variants);
	         int variantsArrSize = variantsArr.length();
	         List<Variant> variantList = new ArrayList<>();
	         for(int j=0;j<variantsArrSize;j++) {
	        	 JSONObject variantJson = variantsArr.getJSONObject(j);
                  Variant variant = new VariantBuilder().
            		 				sku(variantJson.getString(sku)).
            		 				price(variantJson.getDouble(price)).
            		 				qty(variantJson.getInt(qty)).
            		 				build();  
                  variantList.add(variant);
	         }
					
			 productsList.add(new ProductDetail(product, variantList));
		}
			
		}catch(Exception e) {
			System.out.println("Exception "+e.getMessage());
		}
	
		return productsList;
	}

	

}
