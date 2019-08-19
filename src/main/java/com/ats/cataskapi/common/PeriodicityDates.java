package com.ats.cataskapi.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

public class PeriodicityDates {

	public  List<Date> getDates( String fromDate,
			  String toDate) {
		System.err.println("Hello  ");
		List<Date> arryadate = new ArrayList<>();

		SimpleDateFormat yydate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dddate = new SimpleDateFormat("dd-MM-yyyy");
		int totalcount = 0;
		String sundayDates = new String();
		try {
			for (Date j = yydate.parse(fromDate); j.compareTo(yydate.parse(toDate)) <= 0;) {
				// System.err.println("a");
				Calendar c = Calendar.getInstance();
				c.setTime(j);
				int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
				// System.err.println("day of week " + dayOfWeek);
				if (dayOfWeek == 0) {
					arryadate.add(j);
					sundayDates = sundayDates + "," + dddate.format(j);
					System.out.println("Sunday " + dddate.format(j));
					totalcount++;
				}
				
				if (c.get(Calendar.DAY_OF_MONTH) == c.getActualMaximum(Calendar.DAY_OF_MONTH)
						|| 15 == c.get(Calendar.DAY_OF_MONTH)) {
					System.out.println("Hello Bi Weekly " + dddate.format(j));
					arryadate.add(j);
				}

				if (c.get(Calendar.DAY_OF_MONTH) == c.getActualMaximum(Calendar.DAY_OF_MONTH)) {
					//c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
					System.err.println("Month End " + dddate.format(j));
					arryadate.add(j);
				}
								
				if (c.get(Calendar.DAY_OF_MONTH) == 30 && c.get(Calendar.MONTH)==5
						||c.get(Calendar.DAY_OF_MONTH) == 30 && c.get(Calendar.MONTH)==8
						||c.get(Calendar.DAY_OF_MONTH) == 31 && c.get(Calendar.MONTH)==11
						||c.get(Calendar.DAY_OF_MONTH) == 31 && c.get(Calendar.MONTH)==2){
					System.out.println("Hello Quarterly  " + dddate.format(j));
	
					arryadate.add(j);
				}
				
				if (c.get(Calendar.DAY_OF_MONTH) == 30 && c.get(Calendar.MONTH)==8
						||c.get(Calendar.DAY_OF_MONTH) == 31 && c.get(Calendar.MONTH)==2){
					System.err.println("Half Yearly   " + dddate.format(j));
					arryadate.add(j);
				}
				
				if (c.get(Calendar.DAY_OF_MONTH) == 31 && c.get(Calendar.MONTH)==2){
					System.out.println("Hello  Yearly   " + dddate.format(j));
					arryadate.add(j);
				}

				j.setTime(j.getTime() + 1000 * 60 * 60 * 24);
			} // end of for

		} catch (Exception e) {
			System.err.println("exce in getting data between two dates " + e.getMessage());
			e.printStackTrace();// TODO: handle exception
		}

		return arryadate;

	}

}
