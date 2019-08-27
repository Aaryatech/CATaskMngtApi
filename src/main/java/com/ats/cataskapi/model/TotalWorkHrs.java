package com.ats.cataskapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TotalWorkHrs {
	@Id
	private int workLogId;
	private float ttlWorkHrs;
	private String empName;
	private String empNickname;
	private String taskText;
	private int exInt1;
	private String exVar1;
	public int getWorkLogId() {
		return workLogId;
	}
	public void setWorkLogId(int workLogId) {
		this.workLogId = workLogId;
	}
	public float getTtlWorkHrs() {
		return ttlWorkHrs;
	}
	public void setTtlWorkHrs(float ttlWorkHrs) {
		this.ttlWorkHrs = ttlWorkHrs;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpNickname() {
		return empNickname;
	}
	public void setEmpNickname(String empNickname) {
		this.empNickname = empNickname;
	}
	public int getExInt1() {
		return exInt1;
	}
	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}
	public String getExVar1() {
		return exVar1;
	}
	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}
	
	public String getTaskText() {
		return taskText;
	}
	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}
	@Override
	public String toString() {
		return "TotalWorkHrs [workLogId=" + workLogId + ", ttlWorkHrs=" + ttlWorkHrs + ", empName=" + empName
				+ ", empNickname=" + empNickname + ", taskText=" + taskText + ", exInt1=" + exInt1 + ", exVar1="
				+ exVar1 + "]";
	}
	
	
}
