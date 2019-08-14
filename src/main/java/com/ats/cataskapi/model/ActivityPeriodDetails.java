package com.ats.cataskapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ActivityPeriodDetails {
	
	@Id
	private int actiId;
	private String actiName;
	private int periodicityId;
	private String periodicityname;
	public int getActiId() {
		return actiId;
	}
	public void setActiId(int actiId) {
		this.actiId = actiId;
	}
	public String getActiName() {
		return actiName;
	}
	public void setActiName(String actiName) {
		this.actiName = actiName;
	}
	public int getPeriodicityId() {
		return periodicityId;
	}
	public void setPeriodicityId(int periodicityId) {
		this.periodicityId = periodicityId;
	}
	public String getPeriodicityname() {
		return periodicityname;
	}
	public void setPeriodicityname(String periodicityname) {
		this.periodicityname = periodicityname;
	}
	@Override
	public String toString() {
		return "ActivityPeriodDetails [actiId=" + actiId + ", actiName=" + actiName + ", periodicityId=" + periodicityId
				+ ", periodicityname=" + periodicityname + "]";
	}
	

}
