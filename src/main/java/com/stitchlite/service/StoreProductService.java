package com.stitchlite.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stitchlite.dao.MerchantStoreDAO;
import com.stitchlite.dao.ProductDAO;
import com.stitchlite.dao.StoreDAO;
import com.stitchlite.dao.VariantDAO;
import com.stitchlite.entity.MerchantStore;
import com.stitchlite.entity.Product;
import com.stitchlite.entity.ProductDetail;
import com.stitchlite.entity.Store;
import com.stitchlite.entity.Variant;
@Service
public class StoreProductService {
	@Autowired 
	MerchantStoreDAO merchantStoreDAO;	
	
	@Autowired 
	StoreDAO storeDAO;
	
	
	@Autowired
	VariantDAO variantDAO;
	
	
	@Autowired
	ProductDAO productDAO;
	
	public void syncProductsFromStore (long merchantID) {
		
		//get all stores of merchant 
		List<MerchantStore> merchantStores = merchantStoreDAO.getAllStoresByMerchantID(merchantID);
		
		for(MerchantStore merchantStore : merchantStores) {
			//get store api access cred 
			long stitchId = merchantStore.getStitchID();
			Store store = storeDAO.getStoreByStitchID(stitchId);
			String storeType = store.getStoreType();
			//retrieve list of products from API
             List<ProductDetail> products = StoreProductAPIServiceFactory.getStoreProductAPIService(storeType).getAllProducts(store);
             updateProductsToDB(products,merchantID);
             
						
		}
		
	}
	
	
	
	public List<ProductDetail>  getAllProductsByMerchantId(long merchantId) {
		 return variantDAO.getAllProductsByMerchantId(merchantId);
		
	}
	
	
	public ProductDetail getProductById(long productId) {
		return variantDAO.getProductById(productId);
	}
	
	
	public void updateProductsToDB(List<ProductDetail> products, long merchantID) {
		
		for(ProductDetail productDet: products) {
			Product product = productDet.getProduct();
			List<Variant> variants  = productDet.getVariants();
			for(Variant variant : variants) {
				if(!variantDAO.checkIfSkuExitsForMerchant(variant.getSku(),merchantID)) {
					
     				//product does not exist adding
					if(productDAO.checkIfProductExitsFromMerchIdNameDesc(merchantID,product.getName(),product.getDescription())) {
						long prodId = productDAO.createProduct(product); 
						variant.setParentProductID(prodId);
					}
					//adding to variant				
					variantDAO.createVariant(variant);
									
				}
			}
			
		}
		
	}

	
	public HashSet<String> getSkuSetFromVariants(List<Variant> variants) {
		
		HashSet<String> skuSet = new HashSet<>();
		for(Variant variant: variants) {
			skuSet.add(variant.getSku());
		}
		
		return skuSet;
	}
		
}
