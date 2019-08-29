package com.ats.cataskapi.task.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class GetTaskList {

	@Id
	private int taskId;

	private String taskCode;

	private int mappingId;

	private String taskSubline;

	private int taskFyId;

	private String taskText;

	private Date taskStartDate;

	private Date taskEndDate;

	private Date taskStatutoryDueDate;

	private String mngrBudHr;

	private String empBudHr;

	private int custId;

	private int periodicityId;

	private int actvId;

	private int servId;

	private String periodicity_name;

	private String actiName;

	private String servName;

	private String custFirmName;

	private String finYearName;
	private String exVar1;

	private int exInt1;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public int getMappingId() {
		return mappingId;
	}

	public void setMappingId(int mappingId) {
		this.mappingId = mappingId;
	}

	public String getTaskSubline() {
		return taskSubline;
	}

	public void setTaskSubline(String taskSubline) {
		this.taskSubline = taskSubline;
	}

	public int getTaskFyId() {
		return taskFyId;
	}

	public void setTaskFyId(int taskFyId) {
		this.taskFyId = taskFyId;
	}

	public String getTaskText() {
		return taskText;
	}

	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}
	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getTaskStartDate() {
		return taskStartDate;
	}

	public void setTaskStartDate(Date taskStartDate) {
		this.taskStartDate = taskStartDate;
	}

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getTaskEndDate() {
		return taskEndDate;
	}

	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}
	
	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getTaskStatutoryDueDate() {
		return taskStatutoryDueDate;
	}

	public void setTaskStatutoryDueDate(Date taskStatutoryDueDate) {
		this.taskStatutoryDueDate = taskStatutoryDueDate;
	}

	public String getMngrBudHr() {
		return mngrBudHr;
	}

	public void setMngrBudHr(String mngrBudHr) {
		this.mngrBudHr = mngrBudHr;
	}

	public String getEmpBudHr() {
		return empBudHr;
	}

	public void setEmpBudHr(String empBudHr) {
		this.empBudHr = empBudHr;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public int getPeriodicityId() {
		return periodicityId;
	}

	public void setPeriodicityId(int periodicityId) {
		this.periodicityId = periodicityId;
	}

	public int getActvId() {
		return actvId;
	}

	public void setActvId(int actvId) {
		this.actvId = actvId;
	}

	public int getServId() {
		return servId;
	}

	public void setServId(int servId) {
		this.servId = servId;
	}

	public String getPeriodicity_name() {
		return periodicity_name;
	}

	public void setPeriodicity_name(String periodicity_name) {
		this.periodicity_name = periodicity_name;
	}

	public String getActiName() {
		return actiName;
	}

	public void setActiName(String actiName) {
		this.actiName = actiName;
	}

	public String getServName() {
		return servName;
	}

	public void setServName(String servName) {
		this.servName = servName;
	}

	public String getCustFirmName() {
		return custFirmName;
	}

	public void setCustFirmName(String custFirmName) {
		this.custFirmName = custFirmName;
	}

	public String getFinYearName() {
		return finYearName;
	}

	public void setFinYearName(String finYearName) {
		this.finYearName = finYearName;
	}

	
	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	@Override
	public String toString() {
		return "GetTaskList [taskId=" + taskId + ", taskCode=" + taskCode + ", mappingId=" + mappingId
				+ ", taskSubline=" + taskSubline + ", taskFyId=" + taskFyId + ", taskText=" + taskText
				+ ", taskStartDate=" + taskStartDate + ", taskEndDate=" + taskEndDate + ", taskStatutoryDueDate="
				+ taskStatutoryDueDate + ", mngrBudHr=" + mngrBudHr + ", empBudHr=" + empBudHr + ", custId=" + custId
				+ ", periodicityId=" + periodicityId + ", actvId=" + actvId + ", servId=" + servId
				+ ", periodicity_name=" + periodicity_name + ", actiName=" + actiName + ", servName=" + servName
				+ ", custFirmName=" + custFirmName + ", finYearName=" + finYearName + ", exVar1=" + exVar1 + ", exInt1="
				+ exInt1 + "]";
	}


}
