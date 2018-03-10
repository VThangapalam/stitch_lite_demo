package com.stitchlite.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stitchlite.StitchLiteApplication;

public class StoreProductAPIServiceFactory {
	public static final Logger logger = LoggerFactory.getLogger(StoreProductAPIServiceFactory.class);
	public static StoreProductAPIService getStoreProductAPIService(String storeType) {
		
		switch (storeType.toUpperCase()) {
			case "SHOPIFY":
				return new StoreProductAPIServiceShopifyImpl();
				
			case "VEND":
				return new StoreProductAPIServiceVendmpl();
		default: 
			logger.debug("Unsupported store : "+ storeType);
				
		}
		return null;
		
	}
	
}
