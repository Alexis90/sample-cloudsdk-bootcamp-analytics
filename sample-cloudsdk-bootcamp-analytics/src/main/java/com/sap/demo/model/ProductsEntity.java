package com.sap.demo.model;

import com.sap.cloud.sdk.result.ElementName;

public class ProductsEntity {
	@ElementName("productId")
	private String ProductID;
	
	@ElementName("availableAmount")
	private int AvailableAmount;	
	
	@ElementName("price")
	private Double Price;	
	
	@ElementName("currency")
	private String Currency;

	@ElementName("name")
	private String Description;

	public String getProductID() {
		return ProductID;
	}

	public void setProductID(String productID) {
		ProductID = productID;
	}

	public int getAvailableAmount() {
		return AvailableAmount;
	}

	public void setAvailableAmount(int availableAmount) {
		AvailableAmount = availableAmount;
	}

	public Double getPrice() {
		return Price;
	}

	public void setPrice(Double price) {
		Price = price;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}	
	
	
	
}
