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

				// week
				if (periodicityId == 1) {
					if (dayOfWeek == 0) {
						arryadate.add(dateFormat.format(j));

						DateValues dv = new DateValues();
						dv.setDate(c.getTime());
						String n = "WEEK".concat(String.valueOf(c.get(Calendar.WEEK_OF_YEAR)));
						System.err.println("Final String is" + n);

						dv.setValue(n);
						dateList.add(dv);

						sundayDates = sundayDates + "," + dddate.format(j);
						System.out.println("Sunday " + dddate.format(j));
						totalcount++;
					}

				} // bi week
				else if (periodicityId == 2) {

					if (c.get(Calendar.DAY_OF_MONTH) == c.getActualMaximum(Calendar.DAY_OF_MONTH)
							|| 15 == c.get(Calendar.DAY_OF_MONTH)) {
						System.out.println("Hello Bi Weekly " + dddate.format(j));
						arryadate.add(dddate.format(j));

						DateValues dv = new DateValues();
						dv.setDate(c.getTime());
						String n = String.valueOf(Calendar.MONTH).concat(String.valueOf((Calendar.DAY_OF_MONTH)));
						dv.setValue(n);
						dateList.add(dv);

					}

				}
				// monthly
				else if (periodicityId == 3) {
					if (c.get(Calendar.DAY_OF_MONTH) == c.getActualMaximum(Calendar.DAY_OF_MONTH)) {
						// c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
						System.err.println("Month End " + dddate.format(j));
						arryadate.add(dddate.format(j));

						DateValues dv = new DateValues();
						dv.setDate(c.getTime());
						String monthName = null;
						System.err.println("Month No." + c.get(Calendar.MONTH) + "***" + c.get(Calendar.YEAR));
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
						String[] result = String.valueOf(c.get(Calendar.YEAR)).split("0");

						String n = monthName.concat(result[1]);
						System.err.println("Final String is" + n);

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
							quarNo="Q-I";
						} else if (c.get(Calendar.DAY_OF_MONTH) == 30 && c.get(Calendar.MONTH) == 8) {
							quarNo="Q-II";
						} else if (c.get(Calendar.DAY_OF_MONTH) == 31 && c.get(Calendar.MONTH) == 11) {
							quarNo="Q-III";
						} else {
							quarNo="Q-IV";
						}
						String[] result = String.valueOf(c.get(Calendar.YEAR)).split("0");

						String n = quarNo.concat(result[1]);
						System.err.println("Final String is" + n);

						dv.setValue(n);
						 
						dateList.add(dv);

					}
				}
				// Yearly
				else if (periodicityId == 5) {

					if (c.get(Calendar.DAY_OF_MONTH) == 30 && c.get(Calendar.MONTH) == 8
							|| c.get(Calendar.DAY_OF_MONTH) == 31 && c.get(Calendar.MONTH) == 2) {
						System.err.println("Half Yearly   " + dddate.format(j));
						arryadate.add(dddate.format(j));

						DateValues dv = new DateValues();
						dv.setDate(c.getTime());
						int CurrentYear = Calendar.getInstance().get(Calendar.YEAR);
						int CurrentMonth = (Calendar.getInstance().get(Calendar.MONTH) + 1);
						String financiyalYearFrom = "";
						String financiyalYearTo = "";
						if (CurrentMonth < 4) {
							financiyalYearFrom = "" + (CurrentYear - 1);
							financiyalYearTo = "" + (CurrentYear);
						} else {
							financiyalYearFrom = "" + (CurrentYear);
							financiyalYearTo = "" + (CurrentYear + 1);
						}

						int ab = (Integer.parseInt(financiyalYearFrom)) % 2000;
						int ab1 = (Integer.parseInt(financiyalYearTo)) % 2000;
						String[] result = String.valueOf(c.get(Calendar.YEAR)).split("0");
 						String n = "FY-".concat(result[1]);
						System.err.println("Final String is" + n);

						dv.setValue(n);
						dateList.add(dv);

					}
				} else {
					if (c.get(Calendar.DAY_OF_MONTH) == 31 && c.get(Calendar.MONTH) == 2) {
						System.out.println("Hello  Yearly   " + dddate.format(j));
						arryadate.add(dddate.format(j));

						DateValues dv = new DateValues();
						dv.setDate(c.getTime());
						dv.setValue("" + c.getWeekYear());
						dateList.add(dv);

					}
				}
				System.err.println("date array " + arryadate.toString());
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
