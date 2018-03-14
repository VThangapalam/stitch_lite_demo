package com.stitchlite.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Token {
	@Id
	private long stitchID;
	private String accessToken;
	private String refreshToken;
	private Timestamp expiry;
	public long getStitchID() {
		return stitchID;
	}
	public void setStitchID(long stitchID) {
		this.stitchID = stitchID;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public Timestamp getExpiry() {
		return expiry;
	}
	public void setExpiry(Timestamp expiry) {
		this.expiry = expiry;
	}
	
	

}
