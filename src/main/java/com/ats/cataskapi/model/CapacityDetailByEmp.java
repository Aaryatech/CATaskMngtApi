package com.ats.cataskapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CapacityDetailByEmp {
	
	@Id	
	private int empId;
	private String empName;
	private int bugetedCap;
	private int allWork;
	private int actWork;
	
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
	public int getBugetedCap() {
		return bugetedCap;
	}
	public void setBugetedCap(int bugetedCap) {
		this.bugetedCap = bugetedCap;
	}
	public int getAllWork() {
		return allWork;
	}
	public void setAllWork(int allWork) {
		this.allWork = allWork;
	}
	public int getActWork() {
		return actWork;
	}
	public void setActWork(int actWork) {
		this.actWork = actWork;
	}
	@Override
	public String toString() {
		return "CapacityDetailByEmp [empId=" + empId + ", empName=" + empName + ", bugetedCap=" + bugetedCap
				+ ", allWork=" + allWork + ", actWork=" + actWork + "]";
	} 
	
	

}
