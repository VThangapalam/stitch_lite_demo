package com.stitchlite.controller;

import java.util.List;

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
import com.stitchlite.entity.ProductDetail;
import com.stitchlite.entity.Store;
import com.stitchlite.service.StoreProductAPIService;
import com.stitchlite.service.StoreProductAPIServiceShopifyImpl;
import com.stitchlite.service.StoreProductAPIServiceVendmpl;
import com.stitchlite.service.StoreProductService;




@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
    StoreProductService storeProductService;
	
    @RequestMapping(value="/sync/{merchantId}", method=RequestMethod.GET)
    public void syncFromStores (@PathVariable long merchantId) {	
    	System.out.println("into sync func");
    	storeProductService.syncProductsFromStore(merchantId);    
     
    }

    
    
    @RequestMapping(value="/merchant/{merchantId}", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<?>  getAllProductsByMerchantId (@PathVariable long merchantId) {	
    List<ProductDetail> products = storeProductService.getAllProductsByMerchantId(merchantId); 
    ObjectMapper objectMapper = new ObjectMapper();
	//Set pretty printing of json
	objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	String arrayToJson;
	try {
		arrayToJson = objectMapper.writeValueAsString(products);
		return  new ResponseEntity<>(arrayToJson, HttpStatus.OK);
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
     	
    }
    
    
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
