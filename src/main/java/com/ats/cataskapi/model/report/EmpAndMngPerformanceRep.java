package com.ats.cataskapi.model.report;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class EmpAndMngPerformanceRep {
	
	
	@Id
	private int empId;
	
	private String empName;
	
	private String noOfTask;
	
	private String empWorkHours;
	
	private String workHours;
	
	private String totAvailHrs;
	
	private Date tillDate;
	
	private String exVar1;

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

	public String getNoOfTask() {
		return noOfTask;
	}

	public void setNoOfTask(String noOfTask) {
		this.noOfTask = noOfTask;
	}

	public String getEmpWorkHours() {
		return empWorkHours;
	}

	public void setEmpWorkHours(String empWorkHours) {
		this.empWorkHours = empWorkHours;
	}

	public String getWorkHours() {
		return workHours;
	}

	public void setWorkHours(String workHours) {
		this.workHours = workHours;
	}

	public String getTotAvailHrs() {
		return totAvailHrs;
	}

	public void setTotAvailHrs(String totAvailHrs) {
		this.totAvailHrs = totAvailHrs;
	}
	
	@JsonFormat(locale = "hi",timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getTillDate() {
		return tillDate;
	}

	public void setTillDate(Date tillDate) {
		this.tillDate = tillDate;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	@Override
	public String toString() {
		return "EmpAndMngPerformanceRep [empId=" + empId + ", empName=" + empName + ", noOfTask=" + noOfTask
				+ ", empWorkHours=" + empWorkHours + ", workHours=" + workHours + ", totAvailHrs=" + totAvailHrs
				+ ", tillDate=" + tillDate + ", exVar1=" + exVar1 + "]";
	}
	
	

}
