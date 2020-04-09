package com.ats.cataskapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dm_assessee_type")
public class AssesseeTypeMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer asseTypeId;
	private String asseTypeName;
	private String asseRemark;
	private Integer delStatus;
	public Integer getAsseTypeId() {
		return asseTypeId;
	}
	public void setAsseTypeId(Integer asseTypeId) {
		this.asseTypeId = asseTypeId;
	}
	public String getAsseTypeName() {
		return asseTypeName;
	}
	public void setAsseTypeName(String asseTypeName) {
		this.asseTypeName = asseTypeName;
	}
	public String getAsseRemark() {
		return asseRemark;
	}
	public void setAsseRemark(String asseRemark) {
		this.asseRemark = asseRemark;
	}
	public Integer getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}
	
	@Override
	public String toString() {
		return "AssesseeTypeMaster [asseTypeId=" + asseTypeId + ", asseTypeName=" + asseTypeName + ", asseRemark="
				+ asseRemark + ", delStatus=" + delStatus + "]";
	}
	
}
