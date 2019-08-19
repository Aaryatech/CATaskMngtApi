package com.ats.cataskapi.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dm_cal_year")
public class FinancialYear {

	private int finYearId;

	private String finYearName;

	private String finStartDate;

	private String finEndDate;

	private int isCurrent;

	private int isClosed;

	private int delStatus;

	private String updateDatetime;

	public int getFinYearId() {
		return finYearId;
	}

	public void setFinYearId(int finYearId) {
		this.finYearId = finYearId;
	}

	public String getFinYearName() {
		return finYearName;
	}

	public void setFinYearName(String finYearName) {
		this.finYearName = finYearName;
	}

	public String getFinStartDate() {
		return finStartDate;
	}

	public void setFinStartDate(String finStartDate) {
		this.finStartDate = finStartDate;
	}

	public String getFinEndDate() {
		return finEndDate;
	}

	public void setFinEndDate(String finEndDate) {
		this.finEndDate = finEndDate;
	}

	public int getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(int isCurrent) {
		this.isCurrent = isCurrent;
	}

	public int getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(int isClosed) {
		this.isClosed = isClosed;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public String getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	@Override
	public String toString() {
		return "FinancialYear [finYearId=" + finYearId + ", finYearName=" + finYearName + ", finStartDate="
				+ finStartDate + ", finEndDate=" + finEndDate + ", isCurrent=" + isCurrent + ", isClosed=" + isClosed
				+ ", delStatus=" + delStatus + ", updateDatetime=" + updateDatetime + "]";
	}
	
	
	
	
}
