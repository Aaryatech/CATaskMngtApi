package com.ats.cataskapi.model.mailnotif;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmpHoursUpdate {

	@Id
	private int empId;
	private String empEmail;
	private int empType;
	
	private int flag;
	private int workHrs;
	private int rowCount;
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpEmail() {
		return empEmail;
	}
	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}
	public int getEmpType() {
		return empType;
	}
	public void setEmpType(int empType) {
		this.empType = empType;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getWorkHrs() {
		return workHrs;
	}
	public void setWorkHrs(int workHrs) {
		this.workHrs = workHrs;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	@Override
	public String toString() {
		return "EmpHoursUpdate [empId=" + empId + ", empEmail=" + empEmail + ", empType=" + empType + ", flag=" + flag
				+ ", workHrs=" + workHrs + ", rowCount=" + rowCount + "]";
	}
	
}
