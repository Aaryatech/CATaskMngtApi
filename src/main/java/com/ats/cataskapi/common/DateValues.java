package com.ats.cataskapi.common;

import java.util.Date;

public class DateValues {
	
	Date date;
	String value;
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "DateValues [date=" + date + ", value=" + value + "]";
	}

}
