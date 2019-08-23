package com.ats.cataskapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
public class CustNameId {
	
	@Id
	private int custId;
	
	private String custName;

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Override
	public String toString() {
		return "CustNameId [custId=" + custId + ", custName=" + custName + "]";
	}
	
	

}
