package com.ats.cataskapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CustomerDetails {
	@Id
	private int custId;
	private String custFirmName;
	private String custGroupName;
	private String custAssesseeName;
	private String custPanNo;
	private String custEmailId;
	private String custPhoneNo;
	private String empName;
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public String getCustFirmName() {
		return custFirmName;
	}
	public void setCustFirmName(String custFirmName) {
		this.custFirmName = custFirmName;
	}
	public String getCustGroupName() {
		return custGroupName;
	}
	public void setCustGroupName(String custGroupName) {
		this.custGroupName = custGroupName;
	}
	public String getCustAssesseeName() {
		return custAssesseeName;
	}
	public void setCustAssesseeName(String custAssesseeName) {
		this.custAssesseeName = custAssesseeName;
	}
	public String getCustPanNo() {
		return custPanNo;
	}
	public void setCustPanNo(String custPanNo) {
		this.custPanNo = custPanNo;
	}
	public String getCustEmailId() {
		return custEmailId;
	}
	public void setCustEmailId(String custEmailId) {
		this.custEmailId = custEmailId;
	}
	public String getCustPhoneNo() {
		return custPhoneNo;
	}
	public void setCustPhoneNo(String custPhoneNo) {
		this.custPhoneNo = custPhoneNo;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	@Override
	public String toString() {
		return "CustomerDetails [custId=" + custId + ", custFirmName=" + custFirmName + ", custGroupName="
				+ custGroupName + ", custAssesseeName=" + custAssesseeName + ", custPanNo=" + custPanNo
				+ ", custEmailId=" + custEmailId + ", custPhoneNo=" + custPhoneNo + ", empName=" + empName + "]";
	}
	
	
}
