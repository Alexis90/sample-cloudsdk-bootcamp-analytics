package com.sap.demo.model;

import com.sap.cloud.sdk.result.ElementName;

/*
 * Generic class as representation for BillItems or BasketItems
 */
public class ItemEntity {
	@ElementName("productId")
	private String ProductID;
	
	@ElementName("amount")
	private int Amount;

	public String getProductID() {
		return ProductID;
	}

	public void setProductID(String productID) {
		ProductID = productID;
	}

	public int getAmount() {
		return Amount;
	}

	public void setAmount(int amount) {
		Amount = amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ProductID == null) ? 0 : ProductID.toString().hashCode());
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
		ItemEntity other = (ItemEntity) obj;
		if (ProductID == null) {
			if (other.ProductID != null)
				return false;
		} else if (!ProductID.toString().equals(other.ProductID.toString()))
			return false;
		return true;
	}
	
	
	
}
