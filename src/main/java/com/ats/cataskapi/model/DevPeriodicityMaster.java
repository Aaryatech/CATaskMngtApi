package com.ats.cataskapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dm_periodicity")
public class DevPeriodicityMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int periodicityId;
	private String periodicityName;
	private int delStatus;
	private String remark;
	public int getPeriodicityId() {
		return periodicityId;
	}
	public void setPeriodicityId(int periodicityId) {
		this.periodicityId = periodicityId;
	}
	public String getPeriodicityName() {
		return periodicityName;
	}
	public void setPeriodicityName(String periodicityName) {
		this.periodicityName = periodicityName;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "DevPeriodicityMaster [periodicityId=" + periodicityId + ", periodicityName=" + periodicityName
				+ ", delStatus=" + delStatus + ", remark=" + remark + "]";
	}
	
	
}
