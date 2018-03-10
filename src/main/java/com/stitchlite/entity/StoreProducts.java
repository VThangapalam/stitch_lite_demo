package com.stitchlite.entity;

import javax.persistence.Entity;
import javax.persistence.Id;


public class StoreProducts {
	
	private long stitchID;
	
	private long variantID;
	
	public long getVariantID() {
		return variantID;
	}
	public void setVariantID(long variantID) {
		this.variantID = variantID;
	}
	public long getStitchID() {
		return stitchID;
	}
	public void setStitchID(long stitchID) {
		this.stitchID = stitchID;
	}
	
	   
}
