package com.stitchlite.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.stitchlite.entity.ProductProperty;
import com.stitchlite.entity.StoreProperty;

@Repository
public class ProductPropertyDAO {
	 @PersistenceContext
	 private EntityManager em;
	 
	 public void insertProperty(long productid, String propertyName,String value) {
	
		 String query = "insert into product_property values ("+productid+",'"+propertyName+"','"+value+"');";
		 em.createNativeQuery(query).executeUpdate();		 
	 }
	 
	 public ProductProperty getProductPropertyByIDAndPropetyName(long productid, String propertyName) {
		String query = "select * from store_property where sproductid = "+ productid +" and property="+propertyName;
		List<ProductProperty> property = em.createNativeQuery(query, ProductProperty.class).getResultList();
		if(property.size()>0) {
			return property.get(0);
		}
		else return null;		 
	 }
	
}
