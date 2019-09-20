package com.ats.cataskapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MonthWiseRateAndEmpActualHrs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private String id;
	
	@Column(name="emp_id")
	private int empId;
	
	@Column(name="emp_name")
	private String empName;

	@Column(name="task_id")
	private int taskId;
	
	@Column(name="worked_min")
	private long workedMin;
	
	@Column(name="sal")
	private float sal;
	
	@Column(name="emp_type")
	private int empType;
	
	@Column(name="month")
	private int month;
	 
	@Column(name="total_min_month")
	private long totalMinMonth;
	
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

	public long getWorkedMin() {
		return workedMin;
	}

	public void setWorkedMin(long workedMin) {
		this.workedMin = workedMin;
	}

	public float getSal() {
		return sal;
	}

	public void setSal(float sal) {
		this.sal = sal;
	}

	public int getEmpType() {
		return empType;
	}

	public void setEmpType(int empType) {
		this.empType = empType;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public long getTotalMinMonth() {
		return totalMinMonth;
	}

	public void setTotalMinMonth(long totalMinMonth) {
		this.totalMinMonth = totalMinMonth;
	}

	@Override
	public String toString() {
		return "MonthWiseRateAndEmpActualHrs [id=" + id + ", empId=" + empId + ", empName=" + empName + ", taskId="
				+ taskId + ", workedMin=" + workedMin + ", sal=" + sal + ", empType=" + empType + ", month=" + month
				+ ", totalMinMonth=" + totalMinMonth + "]";
	}
	
	

}
