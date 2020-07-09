package com.ats.cataskapi.model.reportv2;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class WorkLofForReport {

	@Id
	private String uuid;

	private String workHrMin;

	private String workHr;
	private String workDate;

	private int empId;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getWorkHrMin() {
		return workHrMin;
	}

	public void setWorkHrMin(String workHrMin) {
		this.workHrMin = workHrMin;
	}

	public String getWorkHr() {
		return workHr;
	}

	public void setWorkHr(String workHr) {
		this.workHr = workHr;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	@Override
	public String toString() {
		return "WorkLofForReport [uuid=" + uuid + ", workHrMin=" + workHrMin + ", workHr=" + workHr + ", workDate="
				+ workDate + ", empId=" + empId + ", getUuid()=" + getUuid() + ", getWorkHrMin()=" + getWorkHrMin()
				+ ", getWorkHr()=" + getWorkHr() + ", getWorkDate()=" + getWorkDate() + ", getEmpId()=" + getEmpId()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	

}
