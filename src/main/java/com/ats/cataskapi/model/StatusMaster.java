package com.ats.cataskapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dm_status_mst")
public class StatusMaster {
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int statusMstId;
	private String statusText;
	private int statusValue;
	private String statusDesc;
	private String statusColor;
	private int isEditable;
	private String typeIds;
	private int delStatus;
	private String updateDatetime;
	private int updateUsername;
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
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getStatusColor() {
		return statusColor;
	}
	public void setStatusColor(String statusColor) {
		this.statusColor = statusColor;
	}
	public int getIsEditable() {
		return isEditable;
	}
	public void setIsEditable(int isEditable) {
		this.isEditable = isEditable;
	}
	public String getTypeIds() {
		return typeIds;
	}
	public void setTypeIds(String typeIds) {
		this.typeIds = typeIds;
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
	@Override
	public String toString() {
		return "StatusMaster [statusMstId=" + statusMstId + ", statusText=" + statusText + ", statusValue="
				+ statusValue + ", statusDesc=" + statusDesc + ", statusColor=" + statusColor + ", isEditable="
				+ isEditable + ", typeIds=" + typeIds + ", delStatus=" + delStatus + ", updateDatetime="
				+ updateDatetime + ", updateUsername=" + updateUsername + "]";
	}
		
}
