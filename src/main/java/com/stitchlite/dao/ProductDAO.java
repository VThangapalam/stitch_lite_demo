package com.stitchlite.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;


import com.stitchlite.entity.Product;
import com.sun.media.jfxmedia.logging.Logger;
@Repository
public class ProductDAO {
	 @PersistenceContext
	    private EntityManager em;

	 /**
	  * Get all products of a merchant in the Stitch database
	  * @param merchantID
	  * @return
	  */
	public List<Product> getAllProductsByMerchantID(long merchantID) {
		
		 String query = "select * from product where merchantid = "+merchantID;
		 List<Product> products = em.createNativeQuery(query).getResultList();
		 return products;
		
	}
	
	/**
	 * Checks id a product exists for a given merchant
	 *Note this function needs to be improved by storing some unique identifiers from store for a given product 
	 * @param merchantId
	 * @param name
	 * @param desc
	 * @return
	 */
	public boolean checkIfProductExitsFromMerchIdNameDesc(long merchantId, String name,String desc) {
		
		String query = "select * from product where merchantid = "+merchantId+" and name = '"+ name +"'";
		if( em.createNativeQuery(query).getResultList().size() > 0 )
			return true;
		else return false;
		
	}
	/**
	 * create a given product in the Stitch DB
	 * @param product
	 */
	@Transactional
	public void createProduct(Product product) {
		
		 String query = "insert into product (merchantid, name, description) values ("+product.getMerchantID()+",'"+product.getName()+"','"+product.getDescription()+"');";
         em.createNativeQuery(query).executeUpdate();        
        
		
	}
	
	/**
	 * Returns the product found by merch id, prod name, desc
	 * @param merchantID
	 * @param name
	 * @param description
	 * @return
	 */
	public Product findProductByNameDescMercID(long merchantID, String name, String description) {
		 String findProduct = "select * from product where merchantid="+merchantID+" and name ='"+name+"'";
		 try {
		 List<Product> prods = em.createNativeQuery(findProduct, Product.class).getResultList();
		 System.out.println("size ***  "+prods.size());
		 return prods.get(0);
		 } catch(Exception e) {
			 
			 e.printStackTrace();
		 }
		 return null;	 
		
	}
	
}
