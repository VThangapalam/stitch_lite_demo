

package com.stitchlite.controller;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.stitchlite.dao.TokenDAO;
import com.stitchlite.dao.VariantQtyDAO;
import com.stitchlite.entity.Token;
import com.stitchlite.entity.VariantQty;
import com.stitchlite.entity.ProductDetail;
import com.stitchlite.entity.Store;

import com.stitchlite.service.StoreProductAPIShopifyImpl;
import com.stitchlite.service.StoreProductAPIVendmpl;
import com.stitchlite.service.StoreProductService;
import com.stitchlite.util.HttpClientUtil;




@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	TokenDAO accessToken;
	
	@Autowired
    StoreProductService storeProductService;
	
	/**
	 * Sync all products from different stores of a merchant into stitch database
	 * @param merchantId
	 * @return
	 */
    @RequestMapping(value="sync/{merchantId}", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<?>syncFromStores (@PathVariable("merchantId") long merchantId) {	
    	try {
    	storeProductService.syncProductsFromStore(merchantId); 
    	return  new ResponseEntity<>("Products sysnc successfully", HttpStatus.OK);   
    	}catch(Exception e) {
    		e.printStackTrace();
    		return  new ResponseEntity<>("Error in sync", HttpStatus.INTERNAL_SERVER_ERROR); 
    	}    
    }

    
    /**
     * Push all inventory quantities from stitch to stores 
     * @param merchantId
     * @return
     */
    @RequestMapping(value="sync/stores/{merchantId}", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<?>syncInventoryToStores (@PathVariable("merchantId") long merchantId) {	
    	return  new ResponseEntity<>("Not yet implemented", HttpStatus.FORBIDDEN);
    	
    }
    
    /**
     * get all products of a merchant
     * @param merchantId
     * @return
     * @throws JSONException
     */
    @RequestMapping(value="/merchant/{merchantId}", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<?>  getAllProductsByMerchantId (@PathVariable long merchantId) throws JSONException {	
    List<ProductDetail> products = storeProductService.getAllProductsByMerchantId(merchantId); 
    ObjectMapper objectMapper = new ObjectMapper();
	//Set pretty printing of json
	objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	String arrayToJson;
	try {
		arrayToJson = objectMapper.writeValueAsString(products);
		return  new ResponseEntity<>(products, HttpStatus.OK);
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
     	
    }
    
    /**
     * get all the product detail and list of variant by the stitch product id
     * @param productId
     * @return
     */
    @RequestMapping(value="/{productId}", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<?>  getProductById (@PathVariable long productId) {
    ProductDetail product = storeProductService.getProductById(productId); 
    ObjectMapper objectMapper = new ObjectMapper();
	//Set pretty printing of json
	objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	String arrayToJson;
	try {
		arrayToJson = objectMapper.writeValueAsString(product);
		return  new ResponseEntity<>(arrayToJson, HttpStatus.OK);
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
     	   
    }
    
   
    
}
