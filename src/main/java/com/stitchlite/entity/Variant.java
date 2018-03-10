package com.stitchlite.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.stitchlite.entity.Product.ProductBuilder;

@Entity
public class Variant {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long variantID;
	
	private String sku;
	private String info;
	private int qty;
	private double price;
	private long parentProductID;
	
	public Variant(String sku, double price, int qty) {
		this.sku = sku;
		this.price = price;
		this.qty =  qty;
	}
	
	 private Variant(VariantBuilder builder) {
		    this.variantID = builder.variantID;
		    this.sku = builder.sku;
		    this.info = builder.info;
		    this.qty = builder.qty;
		    this.price = builder.price;
		    this.parentProductID = builder.parentProductID;
		  }
	
	public long getVariantID() {
		return variantID;
	}
	public void setVariantID(long variantID) {
		this.variantID = variantID;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getParentProductID() {
		return parentProductID;
	}
	public void setParentProductID(long parentProductID) {
		this.parentProductID = parentProductID;
	}
	
	public static class VariantBuilder {
		private long variantID;
		
		private String sku;
		private String info;
		private int qty;
		private double price;
		private long parentProductID;
		
		public VariantBuilder variantID(long variantID) {
		      this.variantID = variantID;
		      return this;
		}
		
		public VariantBuilder sku(String sku) {
		      this.sku = sku;
		      return this;
		}
		
		
		public VariantBuilder info(String info) {
		      this.info = info;
		      return this;
		}
		
		public VariantBuilder qty(int qty) {
		      this.qty = qty;
		      return this;
		}
		
		
		public VariantBuilder price(double price) {
		      this.price = price;
		      return this;
		}
		
		public VariantBuilder parentProductID(long parentProductID) {
		      this.parentProductID = parentProductID;
		      return this;
		}
		
		 public Variant build() {
		      return new Variant(this);
		    }
		
		
	}
	
	
	public String toString() {
	    return "{sku:" + sku + ",price:" + price +
		       ", qty:" + qty +"}";
	}
}
