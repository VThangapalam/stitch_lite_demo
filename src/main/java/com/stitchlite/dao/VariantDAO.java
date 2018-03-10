package com.stitchlite.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.stitchlite.entity.Product;
import com.stitchlite.entity.ProductDetail;
import com.stitchlite.entity.Variant;

@Repository
public class VariantDAO {
	 @PersistenceContext
	private EntityManager em;
	 
	public List<Variant> getAllVariantsByMerchantID(long merchantId) {
		
		String query = "select * from variant join  product on product.sproduct_id= variant.parent_product_id"
				+ "where product.merchant_id = "+merchantId;
		 List<Variant> variants = em.createNativeQuery(query).getResultList();
		 return variants;
	}
	
	public boolean checkIfSkuExitsForMerchant(String sku, long merchantId) {
		
		String query = "select * from variant join  product on product.sproduct_id= variant.parent_product_id"
				+ "where product.merchant_id = "+merchantId+" and variant.sku = '"+ sku + "'";
		if( em.createNativeQuery(query).getResultList().size() > 0 )
			return true;
		else return false;
		
	}
	
	public void createVariant(Variant variant) {
		String query = "insert into variant (sku, info,qty,price,parent_product_id) values ('"+variant.getSku()+"',"
				+ "'"+variant.getInfo()+"' "+variant.getQty()+" "+variant.getPrice()+" "+variant.getParentProductID()+")";
		
		em.createNativeQuery(query).executeUpdate();
	}

	
	
    public List<ProductDetail> getAllProductsByMerchantId(long merchantId) {
      	 List<ProductDetail> allProducts = new ArrayList<>();
    	     String query = "select * from product"
				+ "where merchant_id = "+merchantId;
		 List<Product> products = em.createNativeQuery(query).getResultList();
		 for(Product prod : products) {
			 
			 String queryVar = "select * from variant"
						+ "where parent_product_id = "+prod.getSproductID();
		    List<Variant> variants = em.createNativeQuery(query).getResultList();
		    ProductDetail prodDet = new ProductDetail(prod, variants);
		    allProducts.add(prodDet);
			 
		 }
		 
		 return allProducts;
		
	}

    
    public ProductDetail getProductById(long productId) {
	     String query = "select * from variant "
			+ "where parent_product_id="+ productId;
	 List<Variant> variants = em.createNativeQuery(query).getResultList();	
 	 Product product =   em.find(Product.class, productId);  	 
	 return new ProductDetail(product, variants);
	
}

	
}
