package com.ats.cataskapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CapacityDetailByEmp {
	
	@Id	
	private int empId;
	private String empName;
	private float bugetedCap;
	private String allWork;
	private String actWork;
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public float getBugetedCap() {
		return bugetedCap;
	}
	public void setBugetedCap(float bugetedCap) {
		this.bugetedCap = bugetedCap;
	}
	public String getAllWork() {
		return allWork;
	}
	public void setAllWork(String allWork) {
		this.allWork = allWork;
	}
	public String getActWork() {
		return actWork;
	}
	public void setActWork(String actWork) {
		this.actWork = actWork;
	}
	@Override
	public String toString() {
		return "CapacityDetailByEmp [empId=" + empId + ", empName=" + empName + ", bugetedCap=" + bugetedCap
				+ ", allWork=" + allWork + ", actWork=" + actWork + "]";
	}
	  
	

}
