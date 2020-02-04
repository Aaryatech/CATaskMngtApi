package com.ats.cataskapi.task.repo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ShareDeshare {

	@Id
	private String uniqueId;
	private String empEmail;
	private String custFolderId;
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getEmpEmail() {
		return empEmail;
	}
	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}
	public String getCustFolderId() {
		return custFolderId;
	}
	public void setCustFolderId(String custFolderId) {
		this.custFolderId = custFolderId;
	}
	@Override
	public String toString() {
		return "ShareDeshare [uniqueId=" + uniqueId + ", empEmail=" + empEmail + ", custFolderId=" + custFolderId + "]";
	}
	
	
}
