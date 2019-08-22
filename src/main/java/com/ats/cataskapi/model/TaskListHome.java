package com.ats.cataskapi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class TaskListHome {
	
	@Id	
	private int taskId;
	private String taskText;
	private Date taskStartDate;
	private Date taskEndDate;
	private Date taskStatutoryDueDate;
	private String mngrBudHr;
	private String empBudHr;
	private String taskEmpIds;
	private String empName;
	private String servName;
	private String actiName;
	private String periodicityName;
	private String custGroupName;
	private String finYearName;
	private String taskStatus;
	
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTaskText() {
		return taskText;
	}
	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}
	
	@JsonFormat(locale = "hi",timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getTaskStartDate() {
		return taskStartDate;
	}
	public void setTaskStartDate(Date taskStartDate) {
		this.taskStartDate = taskStartDate;
	}
	
	@JsonFormat(locale = "hi",timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getTaskEndDate() {
		return taskEndDate;
	}
	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}
	
	@JsonFormat(locale = "hi",timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
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
	public String getTaskEmpIds() {
		return taskEmpIds;
	}
	public void setTaskEmpIds(String taskEmpIds) {
		this.taskEmpIds = taskEmpIds;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getServName() {
		return servName;
	}
	public void setServName(String servName) {
		this.servName = servName;
	}
	public String getActiName() {
		return actiName;
	}
	public void setActiName(String actiName) {
		this.actiName = actiName;
	}
	public String getPeriodicityName() {
		return periodicityName;
	}
	public void setPeriodicityName(String periodicityName) {
		this.periodicityName = periodicityName;
	}
	public String getFinYearName() {
		return finYearName;
	}
	public void setFinYearName(String finYearName) {
		this.finYearName = finYearName;
	}
	public String getCustGroupName() {
		return custGroupName;
	}
	public void setCustGroupName(String custGroupName) {
		this.custGroupName = custGroupName;
	}
	
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	@Override
	public String toString() {
		return "TaskListHome [taskId=" + taskId + ", taskText=" + taskText + ", taskStartDate=" + taskStartDate
				+ ", taskEndDate=" + taskEndDate + ", taskStatutoryDueDate=" + taskStatutoryDueDate + ", mngrBudHr="
				+ mngrBudHr + ", empBudHr=" + empBudHr + ", taskEmpIds=" + taskEmpIds + ", empName=" + empName
				+ ", servName=" + servName + ", actiName=" + actiName + ", periodicityName=" + periodicityName
				+ ", custGroupName=" + custGroupName + ", finYearName=" + finYearName + ", taskStatus=" + taskStatus
				+ "]";
	}
	
	
}
