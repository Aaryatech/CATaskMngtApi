package com.ats.cataskapi.model;
 

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TaskCountByStatus {

	@Id	
	private int statusMstId;
	private String statusText;
	private int statusValue;
	private int overdeu;
	private int duetoday;
	private int week;
	private int month;
	
	public int getStatusMstId() {
		return statusMstId;
	}
	public void setStatusMstId(int statusMstId) {
		this.statusMstId = statusMstId;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public int getStatusValue() {
		return statusValue;
	}
	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}
	public int getOverdeu() {
		return overdeu;
	}
	public void setOverdeu(int overdeu) {
		this.overdeu = overdeu;
	}
	public int getDuetoday() {
		return duetoday;
	}
	public void setDuetoday(int duetoday) {
		this.duetoday = duetoday;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	@Override
	public String toString() {
		return "TaskCountByStatus [statusMstId=" + statusMstId + ", statusText=" + statusText + ", statusValue="
				+ statusValue + ", overdeu=" + overdeu + ", duetoday=" + duetoday + ", week=" + week + ", month="
				+ month + "]";
	} 
	
	
	
}
