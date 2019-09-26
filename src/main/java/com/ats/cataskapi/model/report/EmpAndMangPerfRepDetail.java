package com.ats.cataskapi.model.report;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmpAndMangPerfRepDetail {
	@Id
	private int taskId;

	private String taskText;

	private String servName;

	private String actiName;

	private String periodicityName;

	private Date taskStartDate;

	private Date taskEndDate;

	private Date taskStatutoryDueDate;

	private Date taskCompletionDate;

	private String statusText;

	private String mngrBudHr;

	private String empBudHr;

	private String custFirmName;

	private String workHours;

	private String admin;

	private String partner;

	private String manager;

	private String teamLeader;

	private String employee;

	private String managerHrs;

	private String teamLeaderHrs;

	private String employeeHrs;

	private String managerBetHrs;

	private String tlBetHrs;

	private String empBetHrs;
	
	private String ownerPartner;
	
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

	public Date getTaskStartDate() {
		return taskStartDate;
	}

	public void setTaskStartDate(Date taskStartDate) {
		this.taskStartDate = taskStartDate;
	}

	public Date getTaskEndDate() {
		return taskEndDate;
	}

	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}

	public Date getTaskStatutoryDueDate() {
		return taskStatutoryDueDate;
	}

	public void setTaskStatutoryDueDate(Date taskStatutoryDueDate) {
		this.taskStatutoryDueDate = taskStatutoryDueDate;
	}

	public Date getTaskCompletionDate() {
		return taskCompletionDate;
	}

	public void setTaskCompletionDate(Date taskCompletionDate) {
		this.taskCompletionDate = taskCompletionDate;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
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

	public String getManagerBetHrs() {
		return managerBetHrs;
	}

	public void setManagerBetHrs(String managerBetHrs) {
		this.managerBetHrs = managerBetHrs;
	}

	public String getTlBetHrs() {
		return tlBetHrs;
	}

	public void setTlBetHrs(String tlBetHrs) {
		this.tlBetHrs = tlBetHrs;
	}

	public String getEmpBetHrs() {
		return empBetHrs;
	}

	public void setEmpBetHrs(String empBetHrs) {
		this.empBetHrs = empBetHrs;
	}

	public String getOwnerPartner() {
		return ownerPartner;
	}

	public void setOwnerPartner(String ownerPartner) {
		this.ownerPartner = ownerPartner;
	}

	

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	@Override
	public String toString() {
		return "EmpAndMangPerfRepDetail [taskId=" + taskId + ", taskText=" + taskText + ", servName=" + servName
				+ ", actiName=" + actiName + ", periodicityName=" + periodicityName + ", taskStartDate=" + taskStartDate
				+ ", taskEndDate=" + taskEndDate + ", taskStatutoryDueDate=" + taskStatutoryDueDate
				+ ", taskCompletionDate=" + taskCompletionDate + ", statusText=" + statusText + ", mngrBudHr="
				+ mngrBudHr + ", empBudHr=" + empBudHr + ", custFirmName=" + custFirmName + ", workHours=" + workHours
				+ ", admin=" + admin + ", partner=" + partner + ", manager=" + manager + ", teamLeader=" + teamLeader
				+ ", employee=" + employee + ", managerHrs=" + managerHrs + ", teamLeaderHrs=" + teamLeaderHrs
				+ ", employeeHrs=" + employeeHrs + ", managerBetHrs=" + managerBetHrs + ", tlBetHrs=" + tlBetHrs
				+ ", empBetHrs=" + empBetHrs + ", ownerPartner=" + ownerPartner + ", exvar1=" + exVar1 + "]";
	}
	
	

}
