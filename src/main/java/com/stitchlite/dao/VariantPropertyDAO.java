package com.stitchlite.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import com.stitchlite.entity.VariantProperty;

@Repository
public class VariantPropertyDAO {
	 @PersistenceContext
	 private EntityManager em;
	 
	 public void insertProperty(long varaintid, String propertyName,String value) {
	
		 String query = "insert into variant_property values ("+varaintid+",'"+propertyName+"','"+value+"');";
		 em.createNativeQuery(query).executeUpdate();		 
	 }
	 
	 public VariantProperty getVariantPropertyByIDAndPropetyName(long varaintid, String propertyName) {
		String query = "select * from variant_property where spro = "+ varaintid +" and property="+propertyName;
		List<VariantProperty> property = em.createNativeQuery(query, VariantProperty.class).getResultList();
		if(property.size()>0) {
			return property.get(0);
		}
		else return null;		 
	 }
	
}
