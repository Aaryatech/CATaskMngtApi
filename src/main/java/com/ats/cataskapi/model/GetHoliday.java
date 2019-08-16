package com.ats.cataskapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class GetHoliday {

	@Id

	private int holidayId;

	private int calYrId;

	private int companyId;

	private String locId;

	private Date holidayFromdt;

	private Date holidayTodt;

	private String holidayRemark;

	private String companyName;
	private String locName;
	private Date calYrFromDate;
	private Date calYrToDate;

	private String exVar1;

	private String exVar2;

	public int getHolidayId() {
		return holidayId;
	}

	public void setHolidayId(int holidayId) {
		this.holidayId = holidayId;
	}

	public int getCalYrId() {
		return calYrId;
	}

	public void setCalYrId(int calYrId) {
		this.calYrId = calYrId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getLocId() {
		return locId;
	}

	public void setLocId(String locId) {
		this.locId = locId;
	}

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getHolidayFromdt() {
		return holidayFromdt;
	}

	public void setHolidayFromdt(Date holidayFromdt) {
		this.holidayFromdt = holidayFromdt;
	}

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getHolidayTodt() {
		return holidayTodt;
	}

	public void setHolidayTodt(Date holidayTodt) {
		this.holidayTodt = holidayTodt;
	}

	public String getHolidayRemark() {
		return holidayRemark;
	}

	public void setHolidayRemark(String holidayRemark) {
		this.holidayRemark = holidayRemark;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getCalYrFromDate() {
		return calYrFromDate;
	}

	public void setCalYrFromDate(Date calYrFromDate) {
		this.calYrFromDate = calYrFromDate;
	}

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getCalYrToDate() {
		return calYrToDate;
	}

	public void setCalYrToDate(Date calYrToDate) {
		this.calYrToDate = calYrToDate;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getExVar2() {
		return exVar2;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}

	@Override
	public String toString() {
		return "GetHoliday [holidayId=" + holidayId + ", calYrId=" + calYrId + ", companyId=" + companyId + ", locId="
				+ locId + ", holidayFromdt=" + holidayFromdt + ", holidayTodt=" + holidayTodt + ", holidayRemark="
				+ holidayRemark + ", companyName=" + companyName + ", locName=" + locName + ", calYrFromDate="
				+ calYrFromDate + ", calYrToDate=" + calYrToDate + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
	}

}
