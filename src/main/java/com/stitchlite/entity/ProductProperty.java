package com.stitchlite.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductProperty implements Serializable {
	@Id
	private long sproductID;
	@Id
	private String property;
	private String value;
	public long getProductID() {
		return sproductID;
	}
	public void setProductID(long productID) {
		this.sproductID = productID;
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
