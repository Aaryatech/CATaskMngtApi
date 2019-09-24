package com.ats.cataskapi.model;

import java.util.List;

public class EmpwithPartnerList {
	
	private int empId;
	private String empName; 
	private String bugetedHrs;
	private String workedHrs;
	private long workedMin;
	private long bugetedMin;
	
	private String overtime;
	private String idealtime;
	
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
	 
	public String getBugetedHrs() {
		return bugetedHrs;
	}
	public void setBugetedHrs(String bugetedHrs) {
		this.bugetedHrs = bugetedHrs;
	}
	public String getWorkedHrs() {
		return workedHrs;
	}
	public void setWorkedHrs(String workedHrs) {
		this.workedHrs = workedHrs;
	}
	public long getWorkedMin() {
		return workedMin;
	}
	public void setWorkedMin(long workedMin) {
		this.workedMin = workedMin;
	}
	public long getBugetedMin() {
		return bugetedMin;
	}
	public void setBugetedMin(long bugetedMin) {
		this.bugetedMin = bugetedMin;
	}
	public String getOvertime() {
		return overtime;
	}
	public void setOvertime(String overtime) {
		this.overtime = overtime;
	}
	public String getIdealtime() {
		return idealtime;
	}
	public void setIdealtime(String idealtime) {
		this.idealtime = idealtime;
	}
	@Override
	public String toString() {
		return "EmpwithPartnerList [empId=" + empId + ", empName=" + empName + ", bugetedHrs=" + bugetedHrs
				+ ", workedHrs=" + workedHrs + ", workedMin=" + workedMin + ", bugetedMin=" + bugetedMin + ", overtime="
				+ overtime + ", idealtime=" + idealtime + ", list=" + list + "]";
	}
	
	
	

}
