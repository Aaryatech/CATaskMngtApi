package com.ats.cataskapi.model;

public class DateWithAttendanceSts {

	private String date;
	private String sts;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
	@Override
	public String toString() {
		return "DateWithAttendanceSts [date=" + date + ", sts=" + sts + "]";
	}
	
	
	
}
