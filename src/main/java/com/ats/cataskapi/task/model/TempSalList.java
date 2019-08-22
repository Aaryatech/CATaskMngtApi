package com.ats.cataskapi.task.model;

 
public class TempSalList {
	
private int empId;
	
	private float empSalary;
	
	private int finYear;
	
	private String curDateTime;
	
	private int month;
	
	private int userId;

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public float getEmpSalary() {
		return empSalary;
	}

	public void setEmpSalary(float empSalary) {
		this.empSalary = empSalary;
	}

	public int getFinYear() {
		return finYear;
	}

	public void setFinYear(int finYear) {
		this.finYear = finYear;
	}

	public String getCurDateTime() {
		return curDateTime;
	}

	public void setCurDateTime(String curDateTime) {
		this.curDateTime = curDateTime;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "TempSalList [empId=" + empId + ", empSalary=" + empSalary + ", finYear=" + finYear + ", curDateTime="
				+ curDateTime + ", month=" + month + ", userId=" + userId + "]";
	}
	
 
	 
	

	

}
