package com.stitchlite.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.stitchlite.entity.MerchantStore;
import com.stitchlite.entity.Product;
@Repository
public class MerchantStoreDAO {
	@PersistenceContext
    private EntityManager em;
	public List<MerchantStore> getAllStoresByMerchantID(long merchantID) {
		
		
		 String query = "select * from merchant_store where merchant_id = "+merchantID;
		 List<MerchantStore> stores = em.createNativeQuery(query).getResultList();
		 return stores;
	}
	
}
