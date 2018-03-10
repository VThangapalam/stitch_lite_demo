package com.stitchlite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stitchlite.dao.MerchantStoreDAO;
import com.stitchlite.entity.Product;
import com.stitchlite.entity.ProductDetail;
import com.stitchlite.entity.Store;

public interface StoreProductAPIService {


	public List<ProductDetail> getAllProducts(Store store);

	
}
