package com.sap.demo.model;

import com.sap.cloud.sdk.result.ElementName;

public class UserConsumptionEntity {
	@ElementName("userId")
	private String UserID;

	@ElementName("totalSum")
	private Double OverallConsumption;

	@ElementName("currency")
	private String Currency;

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public Double getOverallConsumption() {
		return OverallConsumption;
	}

	public void setOverallConsumption(Double overallConsumption) {
		OverallConsumption = overallConsumption;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((UserID == null) ? 0 : UserID.hashCode());
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
		UserConsumptionEntity other = (UserConsumptionEntity) obj;
		if (UserID == null) {
			if (other.UserID != null)
				return false;
		} else if (!UserID.equals(other.UserID))
			return false;
		return true;
	}

	
	

}
