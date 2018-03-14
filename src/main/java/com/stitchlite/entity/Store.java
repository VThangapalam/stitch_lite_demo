package com.stitchlite.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.boot.autoconfigure.session.StoreType;
@Entity
public class Store {
@Id
	private long stitchID;
	private String storeName;
	private String clientID;
	private String clientSecret;
	//private String accessToken;
	//private String refreshToken;
	private String storeType;
	private String baseStoreUrl;
	
	
	public long getStitchID() {
		return stitchID;
	}
	public void setStitchID(long stitchID) {
		this.stitchID = stitchID;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	public String getBaseStoreUrl() {
		return baseStoreUrl;
	}
	public void setBaseStoreUrl(String baseStoreUrl) {
		this.baseStoreUrl = baseStoreUrl;
	}

	
	
}
