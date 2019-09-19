package com.ats.cataskapi.task.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_tasks")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int taskId;

	private String taskCode;

	private int mappingId;

	private String taskSubline;

	private int taskFyId;

	private String taskText;

	private String taskEmpIds;

	private String taskStartDate;

	private String taskEndDate;

	private String taskStatutoryDueDate;
	
	private String taskCompletionDate;
	
	private String billingAmt;
	

	private int taskStatus;

	private String mngrBudHr;

	private String empBudHr;

	private int delStatus;

	private String updateDatetime;

	private int updateUsername;

	private int exInt1;

	private int exInt2;

	private String exVar1;

	private String exVar2;

	private int custId;

	private int periodicityId;

	private int actvId;

	private int servId;
	
	private int isActive;

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

	public String getTaskEmpIds() {
		return taskEmpIds;
	}

	public void setTaskEmpIds(String taskEmpIds) {
		this.taskEmpIds = taskEmpIds;
	}

	public String getTaskStartDate() {
		return taskStartDate;
	}

	public void setTaskStartDate(String taskStartDate) {
		this.taskStartDate = taskStartDate;
	}

	public String getTaskEndDate() {
		return taskEndDate;
	}

	public void setTaskEndDate(String taskEndDate) {
		this.taskEndDate = taskEndDate;
	}

	public String getTaskStatutoryDueDate() {
		return taskStatutoryDueDate;
	}

	public void setTaskStatutoryDueDate(String taskStatutoryDueDate) {
		this.taskStatutoryDueDate = taskStatutoryDueDate;
	}

	public int getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
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

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public String getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public int getUpdateUsername() {
		return updateUsername;
	}

	public void setUpdateUsername(int updateUsername) {
		this.updateUsername = updateUsername;
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
	
	

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	
	

	public String getTaskCompletionDate() {
		return taskCompletionDate;
	}

	public void setTaskCompletionDate(String taskCompletionDate) {
		this.taskCompletionDate = taskCompletionDate;
	}

	public String getBillingAmt() {
		return billingAmt;
	}

	public void setBillingAmt(String billingAmt) {
		this.billingAmt = billingAmt;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", taskCode=" + taskCode + ", mappingId=" + mappingId + ", taskSubline="
				+ taskSubline + ", taskFyId=" + taskFyId + ", taskText=" + taskText + ", taskEmpIds=" + taskEmpIds
				+ ", taskStartDate=" + taskStartDate + ", taskEndDate=" + taskEndDate + ", taskStatutoryDueDate="
				+ taskStatutoryDueDate + ", taskCompletionDate=" + taskCompletionDate + ", billingAmt=" + billingAmt
				+ ", taskStatus=" + taskStatus + ", mngrBudHr=" + mngrBudHr + ", empBudHr=" + empBudHr + ", delStatus="
				+ delStatus + ", updateDatetime=" + updateDatetime + ", updateUsername=" + updateUsername + ", exInt1="
				+ exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", custId=" + custId
				+ ", periodicityId=" + periodicityId + ", actvId=" + actvId + ", servId=" + servId + ", isActive="
				+ isActive + ", getTaskId()=" + getTaskId() + ", getTaskCode()=" + getTaskCode() + ", getMappingId()="
				+ getMappingId() + ", getTaskSubline()=" + getTaskSubline() + ", getTaskFyId()=" + getTaskFyId()
				+ ", getTaskText()=" + getTaskText() + ", getTaskEmpIds()=" + getTaskEmpIds() + ", getTaskStartDate()="
				+ getTaskStartDate() + ", getTaskEndDate()=" + getTaskEndDate() + ", getTaskStatutoryDueDate()="
				+ getTaskStatutoryDueDate() + ", getTaskStatus()=" + getTaskStatus() + ", getMngrBudHr()="
				+ getMngrBudHr() + ", getEmpBudHr()=" + getEmpBudHr() + ", getDelStatus()=" + getDelStatus()
				+ ", getUpdateDatetime()=" + getUpdateDatetime() + ", getUpdateUsername()=" + getUpdateUsername()
				+ ", getExInt1()=" + getExInt1() + ", getExInt2()=" + getExInt2() + ", getExVar1()=" + getExVar1()
				+ ", getExVar2()=" + getExVar2() + ", getCustId()=" + getCustId() + ", getPeriodicityId()="
				+ getPeriodicityId() + ", getActvId()=" + getActvId() + ", getServId()=" + getServId()
				+ ", getIsActive()=" + getIsActive() + ", getTaskCompletionDate()=" + getTaskCompletionDate()
				+ ", getBillingAmt()=" + getBillingAmt() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	 

}
