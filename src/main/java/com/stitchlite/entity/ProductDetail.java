package com.stitchlite.entity;

import java.util.List;

public class ProductDetail {

	private Product product;
	private List<Variant> variants;
	
	public ProductDetail(Product product, List<Variant> variants) {
		super();
		this.product = product;
		this.variants = variants;
	}
	
	
	public ProductDetail() {
		
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Variant> getVariants() {
		return variants;
	}
	public void setVariants(List<Variant> variants) {
		this.variants = variants;
	}
	
	public String toString() {
	    //String productString =  "product: "+product.toString() ;
		String productString =  "{"+product.toString()+"}";
	    StringBuffer variantsStr = new StringBuffer();
	    int varListLen = variants.size();
	    for(int i =0; i< varListLen;i++) {
	    	variantsStr.append(variants.get(i).toString());
	    	if(i!=varListLen-1) {
	    		variantsStr.append(",");
	    	}	    	
	    }
	    
	  //  System.out.print("prod det "+productString.replace("varaintJSONSting", variantsStr.toString()));
	    return productString.replace("varaintJSONSting", variantsStr.toString());
	}
	
	
}


