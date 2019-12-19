package com.ats.cataskapi.common;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class PeriodicityDates {

	public static List<DateValues> getDates(String fromDate, String toDate, int periodicityId) {

		List<String> arryadate = new ArrayList<>();

		SimpleDateFormat yydate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dddate = new SimpleDateFormat("dd-MM-yyyy");
		int totalcount = 0;
		String sundayDates = new String();
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		List<DateValues> dateList = new ArrayList<DateValues>();
		
	  

		try {
			for (Date j = yydate.parse(fromDate); j.compareTo(yydate.parse(toDate)) <= 0;) {
				// System.err.println("a");
				Calendar c = Calendar.getInstance();
				c.setTime(j);
				int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
				// System.err.println("day of week " + dayOfWeek);

				if (periodicityId == 7) {
					System.err.println("In 7 ");
					arryadate.add(dateFormat.format(j));

					DateValues dv = new DateValues();
					dv.setDate(c.getTime());
					
					//System.err.println("Final String is" + Fyyear);
					String myString= String.valueOf(c.get(Calendar.YEAR));
				    myString = myString.substring(myString.indexOf('0')+1);

					String n = "One Time ";//.concat(String.valueOf(c.get(Calendar.WEEK_OF_YEAR)).concat(" ").concat(myString).concat("-").concat(String.valueOf((Integer.parseInt(myString) + 1))));

					dv.setValue(n);
					dateList.add(dv);

					sundayDates = sundayDates + "," + dddate.format(j);
					//System.out.println("Sunday " + dddate.format(j));
					totalcount++;
						break;
				}
				// week
				else if (periodicityId == 1) {
					if (dayOfWeek == 0) {
						arryadate.add(dateFormat.format(j));

						DateValues dv = new DateValues();
						dv.setDate(c.getTime());
						
						//System.err.println("Final String is" + Fyyear);
						String myString= String.valueOf(c.get(Calendar.YEAR));
					    myString = myString.substring(myString.indexOf('0')+1);

						String n = "WK ".concat(String.valueOf(c.get(Calendar.WEEK_OF_YEAR)).concat(" ").concat(myString).concat("-").concat(String.valueOf((Integer.parseInt(myString) + 1))));

						dv.setValue(n);
						dateList.add(dv);

						sundayDates = sundayDates + "," + dddate.format(j);
						//System.out.println("Sunday " + dddate.format(j));
						totalcount++;
					}

				} // bi week
				else if (periodicityId == 2) {

					if (c.get(Calendar.DAY_OF_MONTH) == c.getActualMaximum(Calendar.DAY_OF_MONTH)
							|| 15 == c.get(Calendar.DAY_OF_MONTH)) {
						//System.out.println("Hello Bi Weekly " + dddate.format(j));
						arryadate.add(dddate.format(j));
						String biweek=null;
						if(c.get(Calendar.DAY_OF_MONTH)<=15) {
							biweek="BW 1-";
						}else {
							biweek="BW 2-";
						}
						String monthName = null;
						if (c.get(Calendar.MONTH) == 0) {
							monthName = "Jan";
						} else if (c.get(Calendar.MONTH) == 1) {
							monthName = "Feb";
						} else if (c.get(Calendar.MONTH) == 2) {
							monthName = "Mar";
						} else if (c.get(Calendar.MONTH) == 3) {
							monthName = "Apr";
						} else if (c.get(Calendar.MONTH) == 4) {
							monthName = "Jun";
						} else if (c.get(Calendar.MONTH) == 5) {
							monthName = "Feb";
						} else if (c.get(Calendar.MONTH) == 6) {
							monthName = "Jul";
						} else if (c.get(Calendar.MONTH) == 7) {
							monthName = "Aug";
						} else if (c.get(Calendar.MONTH) == 8) {
							monthName = "Sep";
						} else if (c.get(Calendar.MONTH) == 9) {
							monthName = "Oct";
						} else if (c.get(Calendar.MONTH) == 10) {
							monthName = "Nov";
						} else {
							monthName = "Dec";
						}

						DateValues dv = new DateValues();
						dv.setDate(c.getTime());
 						String myString= String.valueOf(c.get(Calendar.YEAR));
							myString = myString.substring(myString.indexOf('0')+1);
						String n = biweek.concat(monthName).concat(" ").concat(myString);
						dv.setValue(n);
						dateList.add(dv);

					}

				}
				// monthly
				else if (periodicityId == 3) {
					if (c.get(Calendar.DAY_OF_MONTH) == c.getActualMaximum(Calendar.DAY_OF_MONTH)) {
						// c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
						//System.err.println("Month End " + dddate.format(j));
						arryadate.add(dddate.format(j));
						
						String[] result = String.valueOf(c.get(Calendar.YEAR)).split("0");
						int a=Integer.parseInt(result[1])+1;

						DateValues dv = new DateValues();
						dv.setDate(c.getTime());
						String monthName = null;
						//System.err.println("Month No." + c.get(Calendar.MONTH) + "***" + c.get(Calendar.YEAR));
						if (c.get(Calendar.MONTH) == 0) {
							monthName = "Jan";
						} else if (c.get(Calendar.MONTH) == 1) {
							monthName = "Feb";
						} else if (c.get(Calendar.MONTH) == 2) {
							monthName = "Mar";
						} else if (c.get(Calendar.MONTH) == 3) {
							monthName = "April";
						} else if (c.get(Calendar.MONTH) == 4) {
							monthName = "Jun";
						} else if (c.get(Calendar.MONTH) == 5) {
							monthName = "Feb";
						} else if (c.get(Calendar.MONTH) == 6) {
							monthName = "Jul";
						} else if (c.get(Calendar.MONTH) == 7) {
							monthName = "Aug";
						} else if (c.get(Calendar.MONTH) == 8) {
							monthName = "Sep";
						} else if (c.get(Calendar.MONTH) == 9) {
							monthName = "Oct";
						} else if (c.get(Calendar.MONTH) == 10) {
							monthName = "Nov";
						} else {
							monthName = "Dec";
						}
						
						String myString= String.valueOf(c.get(Calendar.YEAR));
					    myString = myString.substring(myString.indexOf('0')+1);
						String n ="MT ".concat(monthName).concat(" ").concat(myString).concat("-").concat(String.valueOf((Integer.parseInt(myString) + 1)));
						//System.err.println("Final String is" + n);

						dv.setValue(n);
						dateList.add(dv);

					}
				}
				// Quarterly
				else if (periodicityId == 4) {

					if (c.get(Calendar.DAY_OF_MONTH) == 30 && c.get(Calendar.MONTH) == 5
							|| c.get(Calendar.DAY_OF_MONTH) == 30 && c.get(Calendar.MONTH) == 8
							|| c.get(Calendar.DAY_OF_MONTH) == 31 && c.get(Calendar.MONTH) == 11
							|| c.get(Calendar.DAY_OF_MONTH) == 31 && c.get(Calendar.MONTH) == 2) {
						System.out.println("Hello Quarterly  " + dddate.format(j));

						arryadate.add(dddate.format(j));

						DateValues dv = new DateValues();
						dv.setDate(c.getTime());
						String quarNo = null;
						
						if (c.get(Calendar.DAY_OF_MONTH) == 30 && c.get(Calendar.MONTH) == 5) {
							quarNo="Q1"+" "+"FY"+" ";
						} else if (c.get(Calendar.DAY_OF_MONTH) == 30 && c.get(Calendar.MONTH) == 8) {
							quarNo="Q2"+" "+"FY"+" ";
						} else if (c.get(Calendar.DAY_OF_MONTH) == 31 && c.get(Calendar.MONTH) == 11) {
							quarNo="Q3"+" "+"FY"+" ";
						} else {
							quarNo="Q4"+" "+"FY"+" ";
						}
						//String[] result = String.valueOf(c.get(Calendar.YEAR)).split("0");
						//int a=Integer.parseInt(result[1])+1;
						String myString= String.valueOf(c.get(Calendar.YEAR));
					    myString = myString.substring(myString.indexOf('0')+1);
						String n = quarNo.concat(myString).concat("-").concat(String.valueOf((Integer.parseInt(myString) + 1)));
						System.err.println("Final String is" + n);

						dv.setValue(n);
						 
						dateList.add(dv);

					}
				}
				// Yearly
				else if (periodicityId == 5) {

					if (c.get(Calendar.DAY_OF_MONTH) == 30 && c.get(Calendar.MONTH) == 8
							|| c.get(Calendar.DAY_OF_MONTH) == 31 && c.get(Calendar.MONTH) == 2) {
						//System.err.println("Half Yearly   " + dddate.format(j));
						arryadate.add(dddate.format(j));
						String text=null;

						if(c.get(Calendar.DAY_OF_MONTH) == 30 && c.get(Calendar.MONTH) == 8) {
							text ="Part 1 FY ";
						}else {
							text="Part 2 FY ";
						}
						
						DateValues dv = new DateValues();
						dv.setDate(c.getTime());
						String myString= String.valueOf(c.get(Calendar.YEAR));
					    myString = myString.substring(myString.indexOf('0')+1);
					
						String n = text.concat(myString).concat("-").concat(String.valueOf((Integer.parseInt(myString) + 1)));
						dv.setValue(n);
						dateList.add(dv);
						//System.err.println("Final String is" + n);

						

					}
				} else {
					if (c.get(Calendar.DAY_OF_MONTH) == 31 && c.get(Calendar.MONTH) == 2) {
						//System.out.println("Hello  Yearly   " + dddate.format(j));
						arryadate.add(dddate.format(j));
						
						
						DateValues dv = new DateValues();
						dv.setDate(c.getTime());
						
						String myString= String.valueOf(c.get(Calendar.YEAR)-1);
					    myString = myString.substring(myString.indexOf('0')+1);
 						String n = "FY ".concat(myString).concat("-").concat(String.valueOf((Integer.parseInt(myString) + 1)));
						//System.err.println("Final String is" + n);

						dv.setValue(n);
						dateList.add(dv);

					}
				}
				//System.err.println("date array " + arryadate.toString());
				j.setTime(j.getTime() + 1000 * 60 * 60 * 24);
			} // end of for

		} catch (

		Exception e) {
			System.err.println("exce in getting data between two dates " + e.getMessage());
			e.printStackTrace();// TODO: handle exception
		}

		return dateList;

	}

	public static String addDaysToGivenDate(String oldDate, int daysToAdd) {

		System.out.println("Date before Addition: " + oldDate);
		// Specifying date format that matches the given date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			// Setting the date to the given date
			c.setTime(sdf.parse(oldDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Number of Days to add
		c.add(Calendar.DAY_OF_MONTH, daysToAdd);
		// Date after adding the days to the given date
		String newDate = sdf.format(c.getTime());
		// Displaying the new Date after addition of Days
		System.out.println("Date after Addition: " + newDate);

		return newDate;
	}


}
