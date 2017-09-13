package com.sap.demo.model;

import com.sap.cloud.sdk.result.ElementName;

public class OrderConsumptionEntity {
	@ElementName("ProductID")
	private String ProductID;

	@ElementName("OverallOrderAmount")
	private int OverallOrderAmount;

	@ElementName("AverageOrderAmount")
	private Double AverageOrderAmount;
	
	@ElementName("AvailableAmount")
	private int AvailableAmount;
	
	@ElementName("PredictedAmount")
	private int PredictedAmount;
	
	@ElementName("AveragePrice")
	private Double AveragePrice;
	
	@ElementName("Currency")
	private String Currency;
	
	@ElementName("NumberOrders")
	private int NumberOrders;
	
	@ElementName("Description")
	private String Description;

	public Double getAveragePrice() {
		return AveragePrice;
	}

	public void setAveragePrice(Double averagePrice) {
		AveragePrice = averagePrice;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getProductID() {
		return ProductID;
	}

	public void setProductID(String productID) {
		ProductID = productID;
	}

	public int getOverallOrderAmount() {
		return OverallOrderAmount;
	}

	public void setOverallOrderAmount(int overallOrderAmount) {
		OverallOrderAmount = overallOrderAmount;
	}

	public Double getAverageOrderAmount() {
		return AverageOrderAmount;
	}

	public void setAverageOrderAmount(Double averageOrderAmount) {
		AverageOrderAmount = averageOrderAmount;
	}

	public int getAvailableAmount() {
		return AvailableAmount;
	}

	public void setAvailableAmount(int availableAmount) {
		AvailableAmount = availableAmount;
	}

	public int getPredictedAmount() {
		return PredictedAmount;
	}

	public void setPredictedAmount(int predictedAmount) {
		PredictedAmount = predictedAmount;
	}

	public int getNumberOrders() {
		return NumberOrders;
	}

	public void setNumberOrders(int numberOrders) {
		NumberOrders = numberOrders;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ProductID == null) ? 0 : ProductID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderConsumptionEntity other = (OrderConsumptionEntity) obj;
		if (ProductID == null) {
			if (other.ProductID != null)
				return false;
		} else if (!ProductID.equals(other.ProductID))
			return false;
		return true;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}


		
	
}

