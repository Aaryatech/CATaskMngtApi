package com.ats.cataskapi.model;
 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class ManagerListWithEmpIds {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)  
	private int empId;
	private String empName;
	private String memberIds;
	
	@Transient
	private float bugetedWork;
	
	@Transient
	private float allWork;
	
	@Transient
	private float actlWork;

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

	public String getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(String memberIds) {
		this.memberIds = memberIds;
	}

	public float getBugetedWork() {
		return bugetedWork;
	}

	public void setBugetedWork(float bugetedWork) {
		this.bugetedWork = bugetedWork;
	}

	public float getAllWork() {
		return allWork;
	}

	public void setAllWork(float allWork) {
		this.allWork = allWork;
	}

	public float getActlWork() {
		return actlWork;
	}

	public void setActlWork(float actlWork) {
		this.actlWork = actlWork;
	}

	@Override
	public String toString() {
		return "ManagerListWithEmpIds [empId=" + empId + ", empName=" + empName + ", memberIds=" + memberIds
				+ ", bugetedWork=" + bugetedWork + ", allWork=" + allWork + ", actlWork=" + actlWork + "]";
	}
	
	 
	
	
}
