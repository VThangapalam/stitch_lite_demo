package com.stitchlite.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VariantProperty implements Serializable{
	@Id
	private long variantID;
	@Id
	private String property;
	private String value;
	public VariantProperty(long variantID, String property, String value) {
		super();
		this.variantID = variantID;
		this.property = property;
		this.value = value;
	}
	public long getVariantID() {
		return variantID;
	}
	public void setVariantID(long variantID) {
		this.variantID = variantID;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
