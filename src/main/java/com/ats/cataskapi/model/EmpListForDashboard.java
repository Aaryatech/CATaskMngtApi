package com.ats.cataskapi.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class EmpListForDashboard {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="emp_id")
	private int empId;
	
	@Column(name="emp_name")
	private String empName;

	@Transient
	List<TaskCountByStatus> list;
	
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

	public List<TaskCountByStatus> getList() {
		return list;
	}

	public void setList(List<TaskCountByStatus> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "EmpListForDashboard [empId=" + empId + ", empName=" + empName + ", list=" + list + "]";
	}
	 
	
}
