package com.ats.cataskapi.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateControl {

	/*
	 * public static void main(String[] ar) { System.err.println("date 1"
	 * +getDate(-1)); System.err.println("date 2" +getDate(-2));
	 * System.err.println("date 3" +getDate(-3)); }
	 */
	
	public static String getDate(int noOfDays) {
		
		String date=new String();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Date date1=new Date();
		c.setTime(date1);
		date=sdf.format(c.getTime());
		//System.err.println("date "+date);
		try {
			c.setTime(sdf.parse(date));

		} catch (ParseException e) {
			System.out.println("Exception while incrementing date " + e.getMessage());
			e.printStackTrace();
		}
		c.add(Calendar.DATE, noOfDays); // number of days to add
		date = sdf.format(c.getTime());
		//System.err.println("date "+date);
	return date;
		
}
public static String getDateByDateAndSubDays(String date,int noOfDays) {
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(date));

		} catch (ParseException e) {
			System.out.println("Exception while incrementing date " + e.getMessage());
			e.printStackTrace();
		}
		c.add(Calendar.DATE, noOfDays); // number of days to add
		date = sdf.format(c.getTime());
		System.err.println("date "+date);
	return date;
		
}
}
