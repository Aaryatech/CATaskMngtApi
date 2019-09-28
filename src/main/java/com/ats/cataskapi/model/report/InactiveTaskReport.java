package com.ats.cataskapi.model.report;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class InactiveTaskReport {

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
	
	private String ownerPartner;

	private String manager;

	private String teamLeader;

	private String employee;
	
	private String managerHrs;
	
	private String teamLeaderHrs;
	
	private String employeeHrs;
	
	private String exVar1;
	 
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

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
 	public Date getTaskStatutoryDueDate() {
		return taskStatutoryDueDate;
	}

	public void setTaskStatutoryDueDate(Date taskStatutoryDueDate) {
		this.taskStatutoryDueDate = taskStatutoryDueDate;
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

	public String getOwnerPartner() {
		return ownerPartner;
	}

	public void setOwnerPartner(String ownerPartner) {
		this.ownerPartner = ownerPartner;
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

	public String getManagerHrs() {
		return managerHrs;
	}

	public void setManagerHrs(String managerHrs) {
		this.managerHrs = managerHrs;
	}

	public String getTeamLeaderHrs() {
		return teamLeaderHrs;
	}

	public void setTeamLeaderHrs(String teamLeaderHrs) {
		this.teamLeaderHrs = teamLeaderHrs;
	}

	public String getEmployeeHrs() {
		return employeeHrs;
	}

	public void setEmployeeHrs(String employeeHrs) {
		this.employeeHrs = employeeHrs;
	}

	@Override
	public String toString() {
		return "InactiveTaskReport [taskId=" + taskId + ", taskText=" + taskText + ", servName=" + servName
				+ ", actiName=" + actiName + ", periodicityName=" + periodicityName + ", taskStatutoryDueDate="
				+ taskStatutoryDueDate + ", taskStartDate=" + taskStartDate + ", taskEndDate=" + taskEndDate
				+ ", empBudHr=" + empBudHr + ", mngrBudHr=" + mngrBudHr + ", custFirmName=" + custFirmName
				+ ", workHours=" + workHours + ", admin=" + admin + ", partner=" + partner + ", ownerPartner="
				+ ownerPartner + ", manager=" + manager + ", teamLeader=" + teamLeader + ", employee=" + employee
				+ ", managerHrs=" + managerHrs + ", teamLeaderHrs=" + teamLeaderHrs + ", employeeHrs=" + employeeHrs
				+ ", exVar1=" + exVar1 + "]";
	}

	 

	

}
