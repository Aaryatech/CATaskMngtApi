package com.ats.cataskapi.communication.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class GetAllCommunicationByTaskId {
	
	@Id
	private int communId;

	private int taskId;

	private int empId;

	private String communText;

	private Date updateDatetime;

	private int exInt1;

	private int exInt2;
	
	private int delStatus;

	private String exVar1;

	private String exVar2;
	
	private String empName;
	
	private String taskText;
	
 
	public int getCommunId() {
		return communId;
	}

	public void setCommunId(int communId) {
		this.communId = communId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getCommunText() {
		return communText;
	}

	public void setCommunText(String communText) {
		this.communText = communText;
	}
	@JsonFormat(locale = "Locale.ENGLISH", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy hh:mm:ss a")
	public Date getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public int getExInt2() {
		return exInt2;
	}

	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getExVar2() {
		return exVar2;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getTaskText() {
		return taskText;
	}

	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	@Override
	public String toString() {
		return "GetAllCommunicationByTaskId [communId=" + communId + ", taskId=" + taskId + ", empId=" + empId
				+ ", communText=" + communText + ", updateDatetime=" + updateDatetime + ", exInt1=" + exInt1
				+ ", exInt2=" + exInt2 + ", delStatus=" + delStatus + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ ", empName=" + empName + ", taskText=" + taskText + "]";
	}


	 
	
	
	
	
	

}
