package com.ats.cataskapi.model.reportv2;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class VarianceReportByManger {

	@Id
	private int taskId;

	private String taskText;

	private String servName;

	private String actiName;

	private String periodicityName;

	private Date taskStatutoryDueDate;

	private Date taskStartDate;

	private Date taskEndDate;

	private String empBudHr;

	private String mngrBudHr;

	private String custFirmName;

	private String workHours;

	private String admin;

	private String partner;

	private String manager;

	private String teamLeader;

	private String employee;

	private String exVar1;

	private String varianceDays;

	private Date completionDate;
	
	
	private String tskStatus;

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

	 
	public String getEmpBudHr() {
		return empBudHr;
	}

	public void setEmpBudHr(String empBudHr) {
		this.empBudHr = empBudHr;
	}

	public String getMngrBudHr() {
		return mngrBudHr;
	}

	public void setMngrBudHr(String mngrBudHr) {
		this.mngrBudHr = mngrBudHr;
	}

	public String getCustFirmName() {
		return custFirmName;
	}

	public void setCustFirmName(String custFirmName) {
		this.custFirmName = custFirmName;
	}

	public String getWorkHours() {
		return workHours;
	}

	public void setWorkHours(String workHours) {
		this.workHours = workHours;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(String teamLeader) {
		this.teamLeader = teamLeader;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getVarianceDays() {
		return varianceDays;
	}

	public void setVarianceDays(String varianceDays) {
		this.varianceDays = varianceDays;
	}

	 
	@JsonFormat(locale = "hi",timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
 	public Date getTaskStatutoryDueDate() {
		return taskStatutoryDueDate;
	}

	public void setTaskStatutoryDueDate(Date taskStatutoryDueDate) {
		this.taskStatutoryDueDate = taskStatutoryDueDate;
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
 	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}
	
	

	public String getTskStatus() {
		return tskStatus;
	}

	public void setTskStatus(String tskStatus) {
		this.tskStatus = tskStatus;
	}

	@Override
	public String toString() {
		return "VarianceReportByManger [taskId=" + taskId + ", taskText=" + taskText + ", servName=" + servName
				+ ", actiName=" + actiName + ", periodicityName=" + periodicityName + ", taskStatutoryDueDate="
				+ taskStatutoryDueDate + ", taskStartDate=" + taskStartDate + ", taskEndDate=" + taskEndDate
				+ ", empBudHr=" + empBudHr + ", mngrBudHr=" + mngrBudHr + ", custFirmName=" + custFirmName
				+ ", workHours=" + workHours + ", admin=" + admin + ", partner=" + partner + ", manager=" + manager
				+ ", teamLeader=" + teamLeader + ", employee=" + employee + ", exVar1=" + exVar1 + ", varianceDays="
				+ varianceDays + ", completionDate=" + completionDate + ", tskStatus=" + tskStatus + "]";
	}

	 

}
