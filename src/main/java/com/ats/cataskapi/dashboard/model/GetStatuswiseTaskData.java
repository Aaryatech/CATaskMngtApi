package com.ats.cataskapi.dashboard.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GetStatuswiseTaskData {

	@Id
	private String UUID;

	private String dueToday;
	
	private String overDue;
	
	private String weekCnt;
	
	private String monthCnt;

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getDueToday() {
		return dueToday;
	}

	public void setDueToday(String dueToday) {
		this.dueToday = dueToday;
	}

	public String getOverDue() {
		return overDue;
	}

	public void setOverDue(String overDue) {
		this.overDue = overDue;
	}

	public String getWeekCnt() {
		return weekCnt;
	}

	public void setWeekCnt(String weekCnt) {
		this.weekCnt = weekCnt;
	}

	public String getMonthCnt() {
		return monthCnt;
	}

	public void setMonthCnt(String monthCnt) {
		this.monthCnt = monthCnt;
	}

	@Override
	public String toString() {
		return "GetStatuswiseTaskData [UUID=" + UUID + ", dueToday=" + dueToday + ", overDue=" + overDue + ", weekCnt="
				+ weekCnt + ", monthCnt=" + monthCnt + "]";
	}
	
	
	
	

}
