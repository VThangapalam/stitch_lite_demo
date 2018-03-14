package com.stitchlite.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.stitchlite.entity.Store;
@Repository
public class StoreDAO {
	 @PersistenceContext
	 private EntityManager em;

	public Store getStoreByStitchID (long stitchID) {		
		Store store = em.find(Store.class, stitchID);
		return store;
		
	}
	

	
}
