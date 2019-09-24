package com.ats.cataskapi.model;
 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PartnerEmployeeHrs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private String id; 
	private int partnerId; 
	private String partnerName;
	private int empId; 
	private String empName; 
	private long totalMin; 
	private String totalHrs;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getTotalHrs() {
		return totalHrs;
	}
	public void setTotalHrs(String totalHrs) {
		this.totalHrs = totalHrs;
	}
	public long getTotalMin() {
		return totalMin;
	}
	public void setTotalMin(long totalMin) {
		this.totalMin = totalMin;
	}
	@Override
	public String toString() {
		return "PartnerEmployeeHrs [id=" + id + ", partnerId=" + partnerId + ", partnerName=" + partnerName + ", empId="
				+ empId + ", empName=" + empName + ", totalMin=" + totalMin + ", totalHrs=" + totalHrs + "]";
	}
	
	 

}
