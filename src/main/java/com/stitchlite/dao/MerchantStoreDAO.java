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
	
	/**
	 * Get all the stores by merchant id
	 * @param merchantID
	 * @return
	 */
	public List<MerchantStore> getAllStoresByMerchantID(long merchantID) {
		 String query = "select * from merchant_store where merchantid = "+merchantID;
		 List<MerchantStore> stores = em.createNativeQuery(query, MerchantStore.class).getResultList();
		 return stores;
	}
	
}
