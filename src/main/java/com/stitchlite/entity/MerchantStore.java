package com.stitchlite.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MerchantStore {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long stitchID;
	private long merchantID;
	private long storeID;
	
	public long getStitchID() {
		return stitchID;
	}
	public void setStitchID(long stitchID) {
		this.stitchID = stitchID;
	}
	public long getMerchantID() {
		return merchantID;
	}
	public void setMerchantID(long merchantID) {
		this.merchantID = merchantID;
	}
	public long getStoreID() {
		return storeID;
	}
	public void setStoreID(long storeID) {
		this.storeID = storeID;
	}
	
	
	       
	
	
}
