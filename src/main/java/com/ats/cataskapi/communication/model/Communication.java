package com.ats.cataskapi.communication.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_comunication")
public class Communication {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int communId;

	private int taskId;

	private int empId;

	private String communText;

	private String updateDatetime;

	private int exInt1;

	private int exInt2;

	private String exVar1;

	private String exVar2;
	
	private int updateUser;
	
	
	private int delStatus;

	private int typeId;
	
	
	private String remark;
	
	
	


	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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

	public String getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(String updateDatetime) {
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

	public int getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(int updateUser) {
		this.updateUser = updateUser;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	@Override
	public String toString() {
		return "Communication [communId=" + communId + ", taskId=" + taskId + ", empId=" + empId + ", communText="
				+ communText + ", updateDatetime=" + updateDatetime + ", exInt1=" + exInt1 + ", exInt2=" + exInt2
				+ ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", updateUser=" + updateUser + ", delStatus="
				+ delStatus + "]";
	}

	 
	

}
