package com.ats.cataskapi.task.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmpWorkLogHrs {
@Id
	private int workLogId;
	private String workDate;		
	private float workHours;
	private String workRemark;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;	
	private int taskId;
	private String taskText;
	private String actiName;
	private String servName;
	private String employees;
	private String custFirmName;
	private String finYearName;
	
	public int getWorkLogId() {
		return workLogId;
	}
	public void setWorkLogId(int workLogId) {
		this.workLogId = workLogId;
	}
	public String getWorkDate() {
		return workDate;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public float getWorkHours() {
		return workHours;
	}
	public void setWorkHours(float workHours) {
		this.workHours = workHours;
	}
	public String getWorkRemark() {
		return workRemark;
	}
	public void setWorkRemark(String workRemark) {
		this.workRemark = workRemark;
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
	public String getEmployees() {
		return employees;
	}
	public void setEmployees(String employees) {
		this.employees = employees;
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
	@Override
	public String toString() {
		return "EmpWorkLogHrs [workLogId=" + workLogId + ", workDate=" + workDate + ", workHours=" + workHours
				+ ", workRemark=" + workRemark + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1
				+ ", exVar2=" + exVar2 + ", taskText=" + taskText + ", actiName=" + actiName
				+ ", servName=" + servName + ", employees=" + employees + ", custFirmName=" + custFirmName
				+ ", finYearName=" + finYearName + "]";
	}
	
	
}
