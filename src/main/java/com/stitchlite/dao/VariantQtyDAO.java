package com.stitchlite.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.stitchlite.entity.VariantQty;

@Repository
public class VariantQtyDAO {

	@PersistenceContext
    private EntityManager em;
	
	@Transactional
	public void insertVariantQty(long stitchID, long variantID, int qty) {
		String query ="INSERT INTO  varaint_qty" + 
				" (`variantid`,`stitchid`,`qty`)" + 
				" VALUES("+variantID+","+stitchID+","+qty+");";
		em.createNativeQuery(query).executeUpdate();
		
	}
	
	   @Transactional
	public void updateVariantQty(long stitchID, long variantID, int newqty) {
		String query ="UPDATE `stitch_lite`.`variant_qty` SET " + 
				"`qty` = "+newqty+" WHERE `variantid` = "+variantID+" AND `stitchid` = "+stitchID+";";
		em.createNativeQuery(query).executeUpdate();
		
	}
	
	
	public int getTotalVariantQty(long variantID) {
		String query ="select sum(qty) as totalQuantity from variant_qty WHERE `variantid` = "+variantID+"";
		
		java.math.BigDecimal totqty = (BigDecimal) em.createNativeQuery(query).getResultList().get(0);
		System.out.println("total qty***"+ totqty.intValue());
		return totqty.intValue();

	}
	 
}
