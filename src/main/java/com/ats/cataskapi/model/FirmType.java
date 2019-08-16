package com.ats.cataskapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dm_firm_type")
public class FirmType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int firmId;
	private String firmTypeName;
	private int delStatus;
	public int getFirmId() {
		return firmId;
	}
	public void setFirmId(int firmId) {
		this.firmId = firmId;
	}
	public String getFirmTypeName() {
		return firmTypeName;
	}
	public void setFirmTypeName(String firmTypeName) {
		this.firmTypeName = firmTypeName;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	@Override
	public String toString() {
		return "FirmType [firmId=" + firmId + ", firmTypeName=" + firmTypeName + ", delStatus=" + delStatus + "]";
	}
	
}
