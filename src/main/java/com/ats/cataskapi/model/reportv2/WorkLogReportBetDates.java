package com.ats.cataskapi.model.reportv2;

import java.time.LocalDate;
import java.util.List;

import com.ats.cataskapi.model.EmployeeMaster;

public class WorkLogReportBetDates {
	
	List<EmployeeMaster> empList;
	
	List<LocalDate> dateList;
	
	
	List<WorkLogSub> logList;


	public List<EmployeeMaster> getEmpList() {
		return empList;
	}


	public void setEmpList(List<EmployeeMaster> empList) {
		this.empList = empList;
	}
 

	public List<LocalDate> getDateList() {
		return dateList;
	}


	public void setDateList(List<LocalDate> dateList) {
		this.dateList = dateList;
	}


	public List<WorkLogSub> getLogList() {
		return logList;
	}


	public void setLogList(List<WorkLogSub> logList) {
		this.logList = logList;
	}


	@Override
	public String toString() {
		return "WorkLogReportBetDates [empList=" + empList + ", dateList=" + dateList + ", logList=" + logList
				+ ", getEmpList()=" + getEmpList() + ", getDateList()=" + getDateList() + ", getLogList()="
				+ getLogList() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	

}
