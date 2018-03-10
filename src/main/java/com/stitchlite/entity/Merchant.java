package com.stitchlite.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Merchant {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long merchantID;
	private String name;
	private String email;
	
	
	public long getMerchantID() {
		return merchantID;
	}
	public void setMerchantID(long merchantID) {
		this.merchantID = merchantID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
