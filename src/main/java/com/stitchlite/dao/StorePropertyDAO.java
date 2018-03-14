package com.stitchlite.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.stitchlite.entity.StoreProperty;

@Repository
public class StorePropertyDAO {
	 @PersistenceContext
	 private EntityManager em;
	 
	 public void insertProperty(long stichID, String propertyName,String value) {
	
		 String query = "insert into store_property values ("+stichID+",'"+propertyName+"','"+value+"');";
		 em.createNativeQuery(query).executeUpdate();		 
	 }
	 
	 public StoreProperty getStorePropertyByIDAndPropetyName(long stitchID, String propertyName) {
		String query = "select * from store_property where stitchid = "+ stitchID +" and property="+propertyName;
		List<StoreProperty> property = em.createNativeQuery(query, StoreProperty.class).getResultList();
		if(property.size()>0) {
			return property.get(0);
		}
		else return null;		 
	 }
	
}
