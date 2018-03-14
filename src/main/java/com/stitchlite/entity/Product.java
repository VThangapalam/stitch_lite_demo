package com.stitchlite.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long sproductID;
	private long merchantID;
	private String productID;
	private String name;
	private String description;
	
	public Product() {
		
	}
	
	public Product(String name,String description) {
		this.name = name;
		this.description = description;
	}
	
	  private Product(ProductBuilder builder) {
		    this.sproductID = builder.sproductID;
		    this.merchantID = builder.merchantID;
		    this.productID = builder.productID;
		    this.name = builder.name;
		    this.description = builder.description;
		  }
	
	public long getSproductID() {
		return sproductID;
	}
	public void setSproductID(long sproductID) {
		this.sproductID = sproductID;
	}
	public long getMerchantID() {
		return merchantID;
	}
	public void setMerchantID(long merchantID) {
		this.merchantID = merchantID;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public static class ProductBuilder {
		private long sproductID;
		private long merchantID;
		private String productID;
		private String name;
		private String description;
		
		public ProductBuilder() {
			
		}
		
		public ProductBuilder sproductID(long sproductID) {
		      this.sproductID = sproductID;
		      return this;
		}
		
		
		public ProductBuilder merchantID(long merchantID) {
		      this.merchantID = merchantID;
		      return this;
		}
		
		public ProductBuilder productID(String productID) {
		      this.productID = productID;
		      return this;
		}
		
		
		public ProductBuilder name(String name) {
		      this.name = name;
		      return this;
		}
		
		
		public ProductBuilder description(String description) {
		      this.description = description;
		      return this;
		}
		
		 public Product build() {
		      return new Product(this);
		    }
		
		
	
	}
	
	public String toString() {
		
	    String productStr=  " \"name\":\"" + name + "\",\"decription\":\"" + description +"\",\"variants\": [varaintJSONSting]";
	    return productStr;
	}
	
}
