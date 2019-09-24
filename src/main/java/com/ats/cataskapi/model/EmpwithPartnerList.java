package com.ats.cataskapi.model;

import java.util.List;

public class EmpwithPartnerList {
	
	private int empId;
	private String empName; 
	private float bugetedHrs;
	
	List<PartnerListWithHrs> list;
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
	public List<PartnerListWithHrs> getList() {
		return list;
	}
	public void setList(List<PartnerListWithHrs> list) {
		this.list = list;
	}
	 
	public float getBugetedHrs() {
		return bugetedHrs;
	}
	public void setBugetedHrs(float bugetedHrs) {
		this.bugetedHrs = bugetedHrs;
	}
	@Override
	public String toString() {
		return "EmpwithPartnerList [empId=" + empId + ", empName=" + empName + ", bugetedHrs=" + bugetedHrs + ", list="
				+ list + "]";
	}
	
	
	

}
