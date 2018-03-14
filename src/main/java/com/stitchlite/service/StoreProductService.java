package com.stitchlite.service;

import java.util.HashSet;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stitchlite.dao.TokenDAO;
import com.stitchlite.dao.MerchantStoreDAO;
import com.stitchlite.dao.ProductDAO;
import com.stitchlite.dao.StoreDAO;
import com.stitchlite.dao.VariantDAO;
import com.stitchlite.dao.VariantQtyDAO;
import com.stitchlite.entity.Token;
import com.stitchlite.entity.MerchantStore;
import com.stitchlite.entity.Product;
import com.stitchlite.entity.ProductDetail;
import com.stitchlite.entity.Store;

import com.stitchlite.entity.Variant;
@Service
public class StoreProductService {
	Logger logger = Logger.getLogger(StoreProductService.class);
	@Autowired 
	MerchantStoreDAO merchantStoreDAO;	
	
	@Autowired 
	StoreDAO storeDAO;
	
	@Autowired
	TokenDAO accessTokenDAO;
	
	
	@Autowired
	VariantDAO variantDAO;
	
	
	@Autowired
	VariantQtyDAO variantQtyDAO;
	
	@Autowired
	ProductDAO productDAO;
	
	
	@Autowired
	StoreProductAPIFactory storeProductAPIFactory;
	
	public void syncProductsFromStore (long merchantID) throws Exception {
	
		try {
		//get all stores of merchant 
		List<MerchantStore> merchantStores = merchantStoreDAO.getAllStoresByMerchantID(merchantID);
		for(MerchantStore merchantStore : merchantStores) {
			//get store api access cred 
			long stitchId = merchantStore.getStitchID();
			Store store = storeDAO.getStoreByStitchID(stitchId);
			String storeType = store.getStoreType();
			//retrieve list of products from API
			Token accessToken = accessTokenDAO.getTokenFromStichId(stitchId);
			StoreProductAPI storeProductAPI = storeProductAPIFactory.getStoreProductAPI(storeType);
             List<ProductDetail> products = storeProductAPI.getAllProducts(store);
             updateProductsToDB(products,merchantID,stitchId);				
		}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Error in syncing products");
		}
		
	}
	
	
	
	public List<ProductDetail>  getAllProductsByMerchantId(long merchantId) {
		 return variantDAO.getAllProductsByMerchantId(merchantId);
		
	}
	
	
	public ProductDetail getProductById(long productId) {
		return variantDAO.getProductById(productId);
	}
	

	public void updateProductsToDB(List<ProductDetail> products, long merchantID,long stitchId) {
		for(ProductDetail productDet: products) {
			Product product = productDet.getProduct();
			List<Variant> variants  = productDet.getVariants();
			//product does not exist adding
			long prodId = 0;
			if(!productDAO.checkIfProductExitsFromMerchIdNameDesc(merchantID,product.getName(),product.getDescription())) {
			
				 product.setMerchantID(merchantID);
				 productDAO.createProduct(product); 
				
			}
			
			prodId  = productDAO.findProductByNameDescMercID(merchantID, product.getName(), product.getDescription()).getSproductID();
		
			for(Variant variant : variants) {
				List<Variant> varObjInDB = variantDAO.checkIfSkuExitsForMerchant(variant.getSku(),merchantID); 
				if(varObjInDB.size() == 0) {
					
			     variant.setParentProductID(prodId);
					//adding to variant				
				 variantDAO.createVariant(variant);	
				 
				  //Variant varNew = variantDAO.getVariantBySkuAndParentProdId(prodId,variant.getSku());
				 //update var Qty
				// variantQtyDAO.insertVariantQty(stitchId, varNew.getVariantID(), varNew.getQty());
			
				 
				}else {
					/*
					//variant already exists
					long varId = varObjInDB.get(0).getVariantID();
					//update the variant with new info
					 //get total var qty 
					 int val = variantQtyDAO.getTotalVariantQty(varId);
					 //update
					 variantDAO.updateTotalVariantCount(varId, val);
                     			 
					 */
					
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
