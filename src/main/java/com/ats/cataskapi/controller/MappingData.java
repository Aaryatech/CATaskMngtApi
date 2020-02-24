package com.ats.cataskapi.controller;

import java.util.Date;

public class MappingData {
	private int mappingId;
	private int custId;
	private int actvId;
	private int periodicityId;
	private Date actvStartDate;
	private Date actvEndDate;
	private int actvStatutoryDays;
	private String actvManBudgHr;
	private String actvEmpBudgHr;
	private int actvBillingAmt;
	private int delStatus;
	private String updateDatetime;
	private int updateUsername;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;
	
	
	private String custFirmName;
	private String actiName;
	private String periodicityName;
	private String servName;
	
		
	
	
	public int getMappingId() {
		return mappingId;
	}
	public void setMappingId(int mappingId) {
		this.mappingId = mappingId;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public int getActvId() {
		return actvId;
	}
	public void setActvId(int actvId) {
		this.actvId = actvId;
	}
	public int getPeriodicityId() {
		return periodicityId;
	}
	public void setPeriodicityId(int periodicityId) {
		this.periodicityId = periodicityId;
	}
	public Date getActvStartDate() {
		return actvStartDate;
	}
	public void setActvStartDate(Date actvStartDate) {
		this.actvStartDate = actvStartDate;
	}
	public Date getActvEndDate() {
		return actvEndDate;
	}
	public void setActvEndDate(Date actvEndDate) {
		this.actvEndDate = actvEndDate;
	}
	public int getActvStatutoryDays() {
		return actvStatutoryDays;
	}
	public void setActvStatutoryDays(int actvStatutoryDays) {
		this.actvStatutoryDays = actvStatutoryDays;
	}
	public String getActvManBudgHr() {
		return actvManBudgHr;
	}
	public void setActvManBudgHr(String actvManBudgHr) {
		this.actvManBudgHr = actvManBudgHr;
	}
	public String getActvEmpBudgHr() {
		return actvEmpBudgHr;
	}
	public void setActvEmpBudgHr(String actvEmpBudgHr) {
		this.actvEmpBudgHr = actvEmpBudgHr;
	}
	public int getActvBillingAmt() {
		return actvBillingAmt;
	}
	public void setActvBillingAmt(int actvBillingAmt) {
		this.actvBillingAmt = actvBillingAmt;
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
	public String getCustFirmName() {
		return custFirmName;
	}
	public void setCustFirmName(String custFirmName) {
		this.custFirmName = custFirmName;
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
	public String getServName() {
		return servName;
	}
	public void setServName(String servName) {
		this.servName = servName;
	}
	
	@Override
	public String toString() {
		return "MappingData [mappingId=" + mappingId + ", custId=" + custId + ", actvId=" + actvId + ", periodicityId="
				+ periodicityId + ", actvStartDate=" + actvStartDate + ", actvEndDate=" + actvEndDate
				+ ", actvStatutoryDays=" + actvStatutoryDays + ", actvManBudgHr=" + actvManBudgHr + ", actvEmpBudgHr="
				+ actvEmpBudgHr + ", actvBillingAmt=" + actvBillingAmt + ", delStatus=" + delStatus
				+ ", updateDatetime=" + updateDatetime + ", updateUsername=" + updateUsername + ", exInt1=" + exInt1
				+ ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", custFirmName=" + custFirmName
				+ ", actiName=" + actiName + ", periodicityName=" + periodicityName + ", servName=" + servName + "]";
	}
	
	
}
