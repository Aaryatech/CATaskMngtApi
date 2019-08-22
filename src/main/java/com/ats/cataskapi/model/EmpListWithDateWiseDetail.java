package com.ats.cataskapi.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class EmpListWithDateWiseDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int empId;
	private String empName;
	 
	@Transient
	List<DateWithAttendanceSts> atndsList;

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

	public List<DateWithAttendanceSts> getAtndsList() {
		return atndsList;
	}

	public void setAtndsList(List<DateWithAttendanceSts> atndsList) {
		this.atndsList = atndsList;
	}

	@Override
	public String toString() {
		return "EmpListWithDateWiseDetail [empId=" + empId + ", empName=" + empName + ", atndsList=" + atndsList + "]";
	}
	 
	
}
