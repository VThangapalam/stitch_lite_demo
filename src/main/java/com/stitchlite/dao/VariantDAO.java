package com.stitchlite.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stitchlite.entity.Product;
import com.stitchlite.entity.ProductDetail;
import com.stitchlite.entity.Variant;
import com.stitchlite.service.StoreProductService;

@Repository
public class VariantDAO {
	
	Logger logger = Logger.getLogger(VariantDAO.class);
	
	 @PersistenceContext
	private EntityManager em;
	 
	public List<Variant> getAllVariantsByMerchantID(long merchantId) {
		
		String query = "select * from variant join  product on product.sproductid= variant.parent_product_id"
				+ " where product.merchantid = "+merchantId;
		 List<Variant> variants = em.createNativeQuery(query,Variant.class).getResultList();
		 return variants;
	}
	@Transactional
	public List<Variant> checkIfSkuExitsForMerchant(String sku, long merchantId) {
		List<Variant> variants= new ArrayList();
		String query = "select variantid from variant join  product on product.sproductid=variant.parent_productid"
				+ " where product.merchantid = "+merchantId+" and variant.sku = '"+ sku + "'";
		logger.info(query);
		
		List variantObj =  em.createNativeQuery(query).getResultList();
		
		if(variantObj.size() == 0 )
		{  //empty list
			return variants;
		}
		else {
			//adding the exisiting variant to list
			java.math.BigInteger varId = (BigInteger) variantObj.get(0);
			Variant var = getVariantID(varId.longValue());
			variants.add(var);
		}
		
		return variants;
	}
	@Transactional
	public void createVariant(Variant variant) {
		String query = "insert into variant (sku, info,qty,price,parent_productid) values ('"+variant.getSku()+"',"
				+ "'"+variant.getInfo()+"',"+variant.getQty()+","+variant.getPrice()+","+variant.getParentProductID()+")";
		
		em.createNativeQuery(query).executeUpdate();
		
	
	}

	
	@Transactional
    public List<ProductDetail> getAllProductsByMerchantId(long merchantId) {
      	 List<ProductDetail> allProducts = new ArrayList<>();
    	     String query = "select * from product"
				+ " where merchantid = "+merchantId;
		 List<Product> products = em.createNativeQuery(query,Product.class).getResultList();
		 logger.info("the prod ");
		 for(Product prod : products) {
			 
			 String queryVar = "select * from variant where parent_productid = "+prod.getSproductID();
			 try {
		    List<Variant> variants = em.createNativeQuery(queryVar,Variant.class).getResultList();
		    ProductDetail prodDet = new ProductDetail(prod, variants);
		    allProducts.add(prodDet);
			 }catch(Exception e) {
				 logger.info("in get all prod excep "+e.getMessage());
			 }
			 
		 }
		 
		 return allProducts;
		
	}

    @Transactional
    public ProductDetail getProductById(long productId) {
	     String query = "select * from variant "
			+ " where parent_productid="+ productId;
	 List<Variant> variants = em.createNativeQuery(query,Variant.class).getResultList();	
 	 Product product =   em.find(Product.class, productId);  	 
	 return new ProductDetail(product, variants);
	
}
    
    public Variant getVariantID(long variantid) {
		Variant variant = em.find(Variant.class, variantid);
		return variant;
	}
    
    @Transactional
    public void updateVariant(Variant variant) {    	
    String query = "UPDATE variant SET `price` = "+variant.getPrice()+"," + 
  		"`qty` = "+variant.getQty()+",`sku` = '"+variant.getSku()+"' WHERE `variantid` = "+variant.getVariantID()+";";
      em.createNativeQuery(query).executeUpdate();
    }

    @Transactional
    public Variant getVariantBySkuAndParentProdId(long parentProductId, String sku) {
    	String query = "select * from variant where parent_productid = "+parentProductId+" and sku = '"+sku+"'";
    List<Variant>variants = em.createNativeQuery(query,Variant.class).getResultList();
    if (variants.size()>0)
    return variants.get(0);
    //update to throw exception with message
    else return null; 
    }
    
   
    @Transactional
    public void updateTotalVariantCount(long variantID,int qty) {
    	
    	 String query = "UPDATE variant SET " + 
    		  		"`qty` = "+qty+" WHERE `variantid` = "+variantID+";";
    		      em.createNativeQuery(query).executeUpdate();
    		      
    }
	
}
