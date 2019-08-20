package com.ats.cataskapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity 
public class EmployeeListWithAvailableHours {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="leave_id")
	private int leaveId;
	
	@Column(name="cal_yr_id")
	private int calYrId;
	
	@Column(name="emp_id")
	private int empId;
	  
	@Column(name="leave_duration")
	private int leaveDuration;
	
	@Column(name="leave_fromdt")
	private Date leaveFromdt;
	
	@Column(name="leave_todt")
	private Date leaveTodt ;
	
	@Column(name="leave_num_days")
	private float leaveNumDays;
	 
	@Column(name="emp_name")
	private String empName;
	
	@Transient
	float bsyHours;

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}

	public int getCalYrId() {
		return calYrId;
	}

	public void setCalYrId(int calYrId) {
		this.calYrId = calYrId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}
  
	public int getLeaveDuration() {
		return leaveDuration;
	}

	public void setLeaveDuration(int leaveDuration) {
		this.leaveDuration = leaveDuration;
	}
	
	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy") 
	public Date getLeaveFromdt() {
		return leaveFromdt;
	}

	public void setLeaveFromdt(Date leaveFromdt) {
		this.leaveFromdt = leaveFromdt;
	}
	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy") 
	public Date getLeaveTodt() {
		return leaveTodt;
	}

	public void setLeaveTodt(Date leaveTodt) {
		this.leaveTodt = leaveTodt;
	}

	public float getLeaveNumDays() {
		return leaveNumDays;
	}

	public void setLeaveNumDays(float leaveNumDays) {
		this.leaveNumDays = leaveNumDays;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public float getBsyHours() {
		return bsyHours;
	}

	public void setBsyHours(float bsyHours) {
		this.bsyHours = bsyHours;
	}

	@Override
	public String toString() {
		return "EmployeeListWithAvailableHours [leaveId=" + leaveId + ", calYrId=" + calYrId + ", empId=" + empId
				+ ", leaveDuration=" + leaveDuration + ", leaveFromdt=" + leaveFromdt + ", leaveTodt=" + leaveTodt
				+ ", leaveNumDays=" + leaveNumDays + ", empName=" + empName + ", bsyHours=" + bsyHours + "]";
	}
	
	
	 
}
