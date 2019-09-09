package com.ats.cataskapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CapacityDetailByEmp {
	
	@Id	
	private int empId;
	private String empName;
	private float bugetedCap;
	private float allWork;
	private float actWork;
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
	public float getAllWork() {
		return allWork;
	}
	public void setAllWork(float allWork) {
		this.allWork = allWork;
	}
	public float getActWork() {
		return actWork;
	}
	public void setActWork(float actWork) {
		this.actWork = actWork;
	}
	@Override
	public String toString() {
		return "CapacityDetailByEmp [empId=" + empId + ", empName=" + empName + ", bugetedCap=" + bugetedCap
				+ ", allWork=" + allWork + ", actWork=" + actWork + "]";
	}
	  
	

}
