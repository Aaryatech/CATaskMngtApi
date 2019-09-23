package com.ats.cataskapi.model.report;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class CompletedTaskReport {
	
	@Id
	private int taskId;
	
	private String taskText;
	
	private String  servName;
	
	private String  actiName;
	
	private String  periodicityName;
 	
	private Date  taskStatutoryDueDate;
	
	private Date  taskStartDate;
	
	private Date taskEndDate;
	
	private String empBudHr;
	
	private String  mngrBudHr;
	
	private String  custFirmName;
 	
	private String  workHours;
	
	private String  admin;
	
	private String  partner;
	
	private String  manager;
	
	private String  teamLeader;
	
 	private String  employee;

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
	
 	@JsonFormat(locale = "Locale.ENGLISH", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
 	public Date getTaskStatutoryDueDate() {
		return taskStatutoryDueDate;
	}

	public void setTaskStatutoryDueDate(Date taskStatutoryDueDate) {
		this.taskStatutoryDueDate = taskStatutoryDueDate;
	}

 	@JsonFormat(locale = "Locale.ENGLISH", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
 	public Date getTaskStartDate() {
		return taskStartDate;
	}

	public void setTaskStartDate(Date taskStartDate) {
		this.taskStartDate = taskStartDate;
	}

	public Date getTaskEndDate() {
		return taskEndDate;
	}

 	@JsonFormat(locale = "Locale.ENGLISH", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")// 
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

	@Override
	public String toString() {
		return "CompletedTaskReport [taskId=" + taskId + ", taskText=" + taskText + ", servName=" + servName
				+ ", actiName=" + actiName + ", periodicityName=" + periodicityName + ", taskStatutoryDueDate="
				+ taskStatutoryDueDate + ", taskStartDate=" + taskStartDate + ", taskEndDate=" + taskEndDate
				+ ", empBudHr=" + empBudHr + ", mngrBudHr=" + mngrBudHr + ", custFirmName=" + custFirmName
				+ ", workHours=" + workHours + ", admin=" + admin + ", partner=" + partner + ", manager=" + manager
				+ ", teamLeader=" + teamLeader + ", employee=" + employee + ", getTaskId()=" + getTaskId()
				+ ", getTaskText()=" + getTaskText() + ", getServName()=" + getServName() + ", getActiName()="
				+ getActiName() + ", getPeriodicityName()=" + getPeriodicityName() + ", getTaskStatutoryDueDate()="
				+ getTaskStatutoryDueDate() + ", getTaskStartDate()=" + getTaskStartDate() + ", getTaskEndDate()="
				+ getTaskEndDate() + ", getEmpBudHr()=" + getEmpBudHr() + ", getMngrBudHr()=" + getMngrBudHr()
				+ ", getCustFirmName()=" + getCustFirmName() + ", getWorkHours()=" + getWorkHours() + ", getAdmin()="
				+ getAdmin() + ", getPartner()=" + getPartner() + ", getManager()=" + getManager()
				+ ", getTeamLeader()=" + getTeamLeader() + ", getEmployee()=" + getEmployee() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
 	
 	
 	
 	
	
	

}
