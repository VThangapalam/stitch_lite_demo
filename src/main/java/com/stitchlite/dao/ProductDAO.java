package com.stitchlite.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;


import com.stitchlite.entity.Product;
@Repository
public class ProductDAO {
	 @PersistenceContext
	    private EntityManager em;

	public List<Product> getAllProductsByMerchantID(long merchantID) {
		
		 String query = "select * from product where merchant_id = "+merchantID;
		 List<Product> products = em.createNativeQuery(query).getResultList();
		 return products;
		
	}
	
	public boolean checkIfProductExitsFromMerchIdNameDesc(long merchantId, String name,String desc) {
		
		String query = "select * from product where merchant_id = "+merchantId+" and name = '"+ name + "' and desc = '"+desc+"'";
		if( em.createNativeQuery(query).getResultList().size() > 0 )
			return true;
		else return false;
		
	}
	
	
	public long createProduct(Product product) {
		
		 String query = "insert into product (merchantID, name, description) values ("+product.getMerchantID()+",'"+product.getName()+"','"+product.getDescription()+"')";
         em.createNativeQuery(query).executeUpdate();
		return 0;
		
	}
	
}
