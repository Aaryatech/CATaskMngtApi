package com.ats.cataskapi.model;
 
import java.util.ArrayList;

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
	private String allWork;
	
	@Transient
	private String actlWork;

	@Transient
	ArrayList<String> ids;
	
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

	public String getAllWork() {
		return allWork;
	}

	public void setAllWork(String allWork) {
		this.allWork = allWork;
	}

	public String getActlWork() {
		return actlWork;
	}

	public void setActlWork(String actlWork) {
		this.actlWork = actlWork;
	}

	public ArrayList<String> getIds() {
		return ids;
	}

	public void setIds(ArrayList<String> ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		return "ManagerListWithEmpIds [empId=" + empId + ", empName=" + empName + ", memberIds=" + memberIds
				+ ", bugetedWork=" + bugetedWork + ", allWork=" + allWork + ", actlWork=" + actlWork + ", ids=" + ids
				+ "]";
	}
	
	 
	
	
}
