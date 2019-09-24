package com.ats.cataskapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class EmpIdNameList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private String id; 
	private int empId; 
	private String empName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	@Override
	public String toString() {
		return "EmpIdNameList [id=" + id + ", empId=" + empId + ", empName=" + empName + "]";
	}
	
	
	

}
