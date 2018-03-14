package com.stitchlite.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VariantQty implements Serializable {
    @Id
	private long stitchID;
    @Id
	private long variantID;
	private int qty;
	public long getStitchID() {
		return stitchID;
	}
	public void setStitchID(long stitchID) {
		this.stitchID = stitchID;
	}
	public long getVariantID() {
		return variantID;
	}
	public void setVariantID(long variantID) {
		this.variantID = variantID;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	
}
