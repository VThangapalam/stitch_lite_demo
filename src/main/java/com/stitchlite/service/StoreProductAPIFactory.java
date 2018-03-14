package com.stitchlite.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stitchlite.StitchLiteApplication;
//@Service

public interface StoreProductAPIFactory  {
	public StoreProductAPI getStoreProductAPI(String storeType);

}
