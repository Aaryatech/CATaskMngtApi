package com.ats.cataskapi.common;
 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConvertor {

	
		public static String convertToYMD(String date) {
			
			String convertedDate=null;
			try {
				SimpleDateFormat ymdSDF = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat dmySDF = new SimpleDateFormat("dd-MM-yyyy");
				Date dmyDate = dmySDF.parse(date);
				
				convertedDate=ymdSDF.format(dmyDate);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return convertedDate;

		}
		
	public static String convertToDMY(String utildate) {
			
			String convertedDate=null;
			try {
				SimpleDateFormat ymdSDF = new SimpleDateFormat("yyyy-mm-dd");
				SimpleDateFormat ymdSDF2 = new SimpleDateFormat("yyyy-mm-dd");

				
				SimpleDateFormat dmySDF = new SimpleDateFormat("dd-MM-yyyy");

				Date ymdDate = ymdSDF2.parse(utildate);
				
				convertedDate=dmySDF.format(ymdDate);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return convertedDate;

		}
		
	public static java.sql.Date convertToSqlDate(String date) {
			
			java.sql.Date convertedDate=null;
			try {
				SimpleDateFormat ymdSDF = new SimpleDateFormat("yyyy-mm-dd");
				SimpleDateFormat dmySDF = new SimpleDateFormat("dd-MM-yyyy");

				Date dmyDate = dmySDF.parse(date);
				
				System.out.println("converted util date commons "+dmyDate);

				
				

				convertedDate=new java.sql.Date(dmyDate.getTime());
				System.out.println("converted sql date commons "+convertedDate);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return convertedDate;

		}

		
	public static String getCurTime() {

		int interval = 0;
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar curCal = Calendar.getInstance();

		//System.out.println("Time " + String.valueOf(df.format(curCal.getTime())));

		curCal.add(Calendar.MINUTE, interval);
		 return String.valueOf(df.format(curCal.getTime()));
		//return curCal;

	}
	
	public static String getCurDateTimeYmD() {

		int interval = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar curCal = Calendar.getInstance();

		//System.out.println("Time " + String.valueOf(df.format(curCal.getTime())));

		curCal.add(Calendar.MINUTE, interval);
		 return String.valueOf(df.format(curCal.getTime()));
		//return curCal;

	}
	
	public static String add60DaystoCurDate(int days) {
		String leaveDateRange = null;
		String fromDate = null;
		String toDate = null;
		Calendar c = Calendar.getInstance(); // this takes current date

		// System.out.println(c.getTime());
		//c.set(Calendar.DAY_OF_MONTH, 1);
		Date toDate1 = c.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		toDate = sdf.format(toDate1);

		//c.set(Calendar.DAY_OF_MONTH, 60);
		c.add(Calendar.DAY_OF_MONTH, days);

		Date fromDate1 = c.getTime();
		fromDate = sdf.format(fromDate1);
System.err.println("fromDate "+fromDate);
		leaveDateRange = fromDate.concat(" to ").concat(toDate);
		return fromDate;
	}

	}


