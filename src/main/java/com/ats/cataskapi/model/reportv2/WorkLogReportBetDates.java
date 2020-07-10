
package com.ats.cataskapi.model.reportv2;

import java.time.LocalDate;
import java.util.List;

import com.ats.cataskapi.model.EmployeeMaster;
import com.fasterxml.jackson.annotation.JsonFormat;

public class WorkLogReportBetDates {

	List<EmployeeMaster> empList;

 	List<WorkLogSub> logList;

	public List<EmployeeMaster> getEmpList() {
		return empList;
	}

	public void setEmpList(List<EmployeeMaster> empList) {
		this.empList = empList;
	}
 	 

	public List<WorkLogSub> getLogList() {
		return logList;
	}

	public void setLogList(List<WorkLogSub> logList) {
		this.logList = logList;
	}

	@Override
	public String toString() {
		return "WorkLogReportBetDates [empList=" + empList + ", logList=" + logList + "]";
	}

	 

}
