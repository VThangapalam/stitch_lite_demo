package com.stitchlite.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StoreProperty implements Serializable {
	
	public long getStitchID() {
		return stitchID;
	}
	public void setStitchID(long stitchID) {
		this.stitchID = stitchID;
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
	@Id
	private long stitchID;
	@Id
	private String property;
	
	private String value;

}
