package com.ats.cataskapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmpListForDashboardByStatus {
	
	@Id	
	private String empId;
	private String empName;
	private int statusValue;
	private int overdeu;
	private int duetoday;
	private int week;
	private int month;
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public int getStatusValue() {
		return statusValue;
	}
	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}
	public int getOverdeu() {
		return overdeu;
	}
	public void setOverdeu(int overdeu) {
		this.overdeu = overdeu;
	}
	public int getDuetoday() {
		return duetoday;
	}
	public void setDuetoday(int duetoday) {
		this.duetoday = duetoday;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	@Override
	public String toString() {
		return "EmpListForDashboardByStatus [empId=" + empId + ", empName=" + empName + ", statusValue=" + statusValue
				+ ", overdeu=" + overdeu + ", duetoday=" + duetoday + ", week=" + week + ", month=" + month + "]";
	}

	
}
