package com.ats.cataskapi.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ats.cataskapi.model.CapacityDetailByEmp;
import com.ats.cataskapi.model.EmployeeListWithAvailableHours;
import com.ats.cataskapi.model.Holiday;
import com.ats.cataskapi.model.LeaveCount;
import com.ats.cataskapi.model.WeeklyOff;
import com.ats.cataskapi.repositories.CapacityDetailByEmpRepo;
import com.ats.cataskapi.repositories.EmployeeListWithAvailableHoursRepo;
import com.ats.cataskapi.repositories.GetWeeklyOffRepo;
import com.ats.cataskapi.repositories.HolidayRepo;
import com.ats.cataskapi.repositories.WeeklyOffRepo;
import com.ats.cataskapi.service.CommonFunctionService;

@Service
public class CommonFunctionServiceImpl implements CommonFunctionService{

	List<Date> arryadate = new ArrayList<>();
	String datearry = new String();
	
	@Autowired
	WeeklyOffRepo weeklyOffRepo;

	@Autowired
	GetWeeklyOffRepo getWeeklyOffRepo;
	
	@Autowired
	HolidayRepo holidayRepo;
	
	@Autowired
	EmployeeListWithAvailableHoursRepo employeeListWithAvailableHoursRepo;
	
	@Autowired
	CapacityDetailByEmpRepo capacityDetailByEmpRepo;
	
	@Override
	public List<CapacityDetailByEmp> CalculateActualAvailableHrs(List<Integer> arryids, String fromDate, String toDate) {
		 
		float totalfreehrs = 0;

		List<CapacityDetailByEmp> empCaplist = new ArrayList<CapacityDetailByEmp>();
		
		
		try {
  
			//following code is for remove leave day 
			
			 
			empCaplist = capacityDetailByEmpRepo.getempIdOnly(arryids);
			
			List<EmployeeListWithAvailableHours> list = new ArrayList<>();
			list = employeeListWithAvailableHoursRepo.getLeaveRecordCommon(fromDate, toDate, arryids);
			SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
			
			LeaveCount totalDayCount = CalculateDayConsideringHolidayAndWeekend(0, fromDate, toDate);
			float freeHours = totalDayCount.getLeavecount() * 7;
			 
			
			for (int j = 0; j < empCaplist.size(); j++) {

				float bsyhrs = 0;

				for (int i = 0; i < list.size(); i++) {

					if (empCaplist.get(j).getEmpId() == list.get(i).getEmpId()) {

						String lvfmdt = yy.format(list.get(i).getLeaveFromdt());
						String lvtodt = yy.format(list.get(i).getLeaveTodt());

						if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) <= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvfmdt)) >= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) < 0) {

							System.out.println("in if");
							if (list.get(i).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = CalculateDayConsideringHolidayAndWeekend(0, yy.format(yy.parse(lvfmdt)),
										toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = CalculateDayConsideringHolidayAndWeekend(0, yy.format(yy.parse(lvfmdt)),
										toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}

						} else if (yy.parse(fromDate).compareTo(yy.parse(lvtodt)) <= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) >= 0
								&& yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) > 0) {

							System.out.println("in if else 2");

							if (list.get(i).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = CalculateDayConsideringHolidayAndWeekend(0, fromDate,
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = CalculateDayConsideringHolidayAndWeekend(0, fromDate,
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}

						} else if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) <= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) >= 0) {

							System.out.println("in if else 3");

							if (list.get(i).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = CalculateDayConsideringHolidayAndWeekend(0, yy.format(yy.parse(lvfmdt)),
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = CalculateDayConsideringHolidayAndWeekend(0, yy.format(yy.parse(lvfmdt)),
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}

						} else if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) >= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) <= 0) {

							System.out.println("in if else 4");

							if (list.get(i).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = CalculateDayConsideringHolidayAndWeekend(0, fromDate, toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = CalculateDayConsideringHolidayAndWeekend(0, fromDate, toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}
						}
					}
				}

				empCaplist.get(j).setBugetedCap((float) (freeHours - bsyhrs));
			}
			
			 //totalfreehrs = (float) (freeHours - bsyhrs) ;
			 
			 /*if((int)totalfreehrs<totalfreehrs && (int)totalfreehrs+1>totalfreehrs) {
				 
				 totalfreehrs=(float) (totalfreehrs-0.20);
			 }
			 String.valueOf(totalfreehrs)+0 ;*/
			
		} catch (Exception e) {

			e.printStackTrace();
		}

		return empCaplist ;
	}
	
	public LeaveCount CalculateDayConsideringHolidayAndWeekend(int empId,String fromDate, String toDate) {
		List<Holiday> holidayList = new ArrayList<Holiday>();
		List<WeeklyOff> weeklyList = new ArrayList<WeeklyOff>();
		int totalcount = 0;
		int diff = difffun(fromDate, toDate);

		LeaveCount leaveCount = new LeaveCount();

		try {

			SimpleDateFormat yydate = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dddate = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat yearfrmt = new SimpleDateFormat("yyyy");
			SimpleDateFormat ddfrmt = new SimpleDateFormat("dd");

			arryadate.clear();
			datearry = new String();

			weeklyList = weeklyOffRepo.getWeeklyOffListByEmpId();
			holidayList = holidayRepo.getHolidayByEmpIdAndFromDateTodate(fromDate, toDate);
			arryadate = new ArrayList<>();

			for (int i = 0; i < weeklyList.size(); i++) {

				// System.out.println("in for ");

				if (Integer.parseInt(weeklyList.get(i).getWoType()) == 0) {

					for (Date j = yydate.parse(fromDate); j.compareTo(yydate.parse(toDate)) <= 0;) {

						Calendar c = Calendar.getInstance();
						c.setTime(j);
						int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;

						if (dayOfWeek == Integer.parseInt(weeklyList.get(i).getWoDay())) {

							arryadate.add(j);
							datearry = datearry + "," + dddate.format(j);
							// System.out.println("add in all" + dddate.format(j));
							totalcount++;
						}
						j.setTime(j.getTime() + 1000 * 60 * 60 * 24);

					}

				} else if (Integer.parseInt(weeklyList.get(i).getWoType()) == 3) {

					Date frmdt = yydate.parse(fromDate);
					Date todt = yydate.parse(toDate);
					Calendar fc = Calendar.getInstance();
					fc.setTime(frmdt);

					Calendar tc = Calendar.getInstance();
					tc.setTime(todt);

					Calendar temp = Calendar.getInstance();
					temp.setTime(yydate.parse(fromDate));
					int k=temp.get(Calendar.MONTH)+1;
					int year = fc.get(Calendar.YEAR);
					// System.out.println("year " + year);

					for (Date e = yydate.parse(fromDate); e.compareTo(yydate.parse(toDate)) <= 0;) {

						String fd = year + "-" + k + "-01";
						String ld = year + "-" + k + "-07";

						Date wkfstdt = yydate.parse(fd);
						Date wklstdt = yydate.parse(ld);

						// System.out.println(wkfstdt + " " + fd + " " + wklstdt + " " + ld);

						for (Date m = yydate.parse(fromDate); m.compareTo(yydate.parse(toDate)) <= 0;) {

							if (m.compareTo(wkfstdt) >= 0 && m.compareTo(wklstdt) <= 0) {

								for (Date j = m; j.compareTo(wklstdt) <= 0;) {

									Calendar tempc = Calendar.getInstance();
									tempc.setTime(j);
									int dayOfWeek = tempc.get(Calendar.DAY_OF_WEEK) - 1;

									if (dayOfWeek == Integer.parseInt(weeklyList.get(i).getWoDay())
											&& m.compareTo(yydate.parse(fromDate)) >= 0
											&& m.compareTo(yydate.parse(toDate)) <= 0) {

										arryadate.add(m);
										datearry = datearry + "," + dddate.format(m);
										// System.out.println("dayOfWeek"+dayOfWeek+"Integer.parseInt(weeklyList.get(i).getWoDay())"
										// + Integer.parseInt(weeklyList.get(i).getWoDay())+"add in 1st" +
										// dddate.format(m) + " " +m);
										totalcount++;
									}

									j.setTime(j.getTime() + 1000 * 60 * 60 * 24);
								}

							}
							m.setTime(m.getTime() + 1000 * 60 * 60 * 24);
						}

						String dt = year + "-" + (k+1) + "-0";
						e = yydate.parse(dt);
						e.setTime(e.getTime() + 1000 * 60 * 60 * 24);
						Calendar a = Calendar.getInstance();
						a.setTime(e);
						year = a.get(Calendar.YEAR);
						k=a.get(Calendar.MONTH)+1;
					}
				} else if (Integer.parseInt(weeklyList.get(i).getWoType()) == 4) {

					Date frmdt = yydate.parse(fromDate);
					Date todt = yydate.parse(toDate);
					Calendar fc = Calendar.getInstance();
					fc.setTime(frmdt);

					Calendar tc = Calendar.getInstance();
					tc.setTime(todt);

					Calendar temp = Calendar.getInstance();
					temp.setTime(yydate.parse(fromDate));
					int k=temp.get(Calendar.MONTH)+1;
					int year = fc.get(Calendar.YEAR);
					// System.out.println("year " + year);

					for (Date e = yydate.parse(fromDate); e.compareTo(yydate.parse(toDate)) <= 0;) {

						String fd = year + "-" + k + "-08";
						String ld = year + "-" + k + "-14";

						Date wkfstdt = yydate.parse(fd);
						Date wklstdt = yydate.parse(ld);

						// System.out.println(wkfstdt + " " + fd + " " + wklstdt + " " + ld);

						for (Date m = yydate.parse(fromDate); m.compareTo(yydate.parse(toDate)) <= 0;) {

							if (m.compareTo(wkfstdt) >= 0 && m.compareTo(wklstdt) <= 0) {

								for (Date j = m; j.compareTo(wklstdt) <= 0;) {

									Calendar tempc = Calendar.getInstance();
									tempc.setTime(j);
									int dayOfWeek = tempc.get(Calendar.DAY_OF_WEEK) - 1;

									if (dayOfWeek == Integer.parseInt(weeklyList.get(i).getWoDay())
											&& m.compareTo(yydate.parse(fromDate)) >= 0
											&& m.compareTo(yydate.parse(toDate)) <= 0) {

										arryadate.add(m);
										datearry = datearry + "," + dddate.format(m);
										// System.out.println("add in 2nd" + dddate.format(m));
										totalcount++;
									}

									j.setTime(j.getTime() + 1000 * 60 * 60 * 24);
								}

							}
							m.setTime(m.getTime() + 1000 * 60 * 60 * 24);
						}

						String dt = year + "-" + (k+1) + "-0";
						e = yydate.parse(dt);
						e.setTime(e.getTime() + 1000 * 60 * 60 * 24);
						Calendar a = Calendar.getInstance();
						a.setTime(e);
						year = a.get(Calendar.YEAR);
						k=a.get(Calendar.MONTH)+1;
					}
				} else if (Integer.parseInt(weeklyList.get(i).getWoType()) == 5) {

					Date frmdt = yydate.parse(fromDate);
					Date todt = yydate.parse(toDate);
					Calendar fc = Calendar.getInstance();
					fc.setTime(frmdt);

					Calendar tc = Calendar.getInstance();
					tc.setTime(todt);

					Calendar temp = Calendar.getInstance();
					temp.setTime(yydate.parse(fromDate));
					int k=temp.get(Calendar.MONTH)+1;
					int year = fc.get(Calendar.YEAR);
					// System.out.println("year " + year);

					for (Date e = yydate.parse(fromDate); e.compareTo(yydate.parse(toDate)) <= 0;) {

						String fd = year + "-" + k + "-15";
						String ld = year + "-" + k + "-21";

						Date wkfstdt = yydate.parse(fd);
						Date wklstdt = yydate.parse(ld);

						// System.out.println(wkfstdt + " " + fd + " " + wklstdt + " " + ld);

						for (Date m = yydate.parse(fromDate); m.compareTo(yydate.parse(toDate)) <= 0;) {

							if (m.compareTo(wkfstdt) >= 0 && m.compareTo(wklstdt) <= 0) {

								for (Date j = m; j.compareTo(wklstdt) <= 0;) {

									Calendar tempc = Calendar.getInstance();
									tempc.setTime(j);
									int dayOfWeek = tempc.get(Calendar.DAY_OF_WEEK) - 1;

									if (dayOfWeek == Integer.parseInt(weeklyList.get(i).getWoDay())
											&& m.compareTo(yydate.parse(fromDate)) >= 0
											&& m.compareTo(yydate.parse(toDate)) <= 0) {

										arryadate.add(m);
										datearry = datearry + "," + dddate.format(m);
										// System.out.println("add in 3rd" + dddate.format(m));
										totalcount++;
									}

									j.setTime(j.getTime() + 1000 * 60 * 60 * 24);
								}

							}
							m.setTime(m.getTime() + 1000 * 60 * 60 * 24);
						}

						String dt = year + "-" + (k + 1) + "-0";
						e = yydate.parse(dt);
						e.setTime(e.getTime() + 1000 * 60 * 60 * 24);
						Calendar a = Calendar.getInstance();
						a.setTime(e);
						year = a.get(Calendar.YEAR);
						k=a.get(Calendar.MONTH)+1;
					}
				} else if (Integer.parseInt(weeklyList.get(i).getWoType()) == 6) {

					Date frmdt = yydate.parse(fromDate);
					Date todt = yydate.parse(toDate);
					Calendar fc = Calendar.getInstance();
					fc.setTime(frmdt);

					Calendar tc = Calendar.getInstance();
					tc.setTime(todt);

					Calendar temp = Calendar.getInstance();
					temp.setTime(yydate.parse(fromDate));
					int k=temp.get(Calendar.MONTH)+1;
					int year = fc.get(Calendar.YEAR);
					// System.out.println("year " + year);

					for (Date e = yydate.parse(fromDate); e.compareTo(yydate.parse(toDate)) <= 0;) {

						String fd = year + "-" + k + "-22";
						String ld = year + "-" + k + "-28";

						Date wkfstdt = yydate.parse(fd);
						Date wklstdt = yydate.parse(ld);

						// System.out.println(wkfstdt + " " + fd + " " + wklstdt + " " + ld);

						for (Date m = yydate.parse(fromDate); m.compareTo(yydate.parse(toDate)) <= 0;) {

							if (m.compareTo(wkfstdt) >= 0 && m.compareTo(wklstdt) <= 0) {

								for (Date j = m; j.compareTo(wklstdt) <= 0;) {

									Calendar tempc = Calendar.getInstance();
									tempc.setTime(j);
									int dayOfWeek = tempc.get(Calendar.DAY_OF_WEEK) - 1;

									if (dayOfWeek == Integer.parseInt(weeklyList.get(i).getWoDay())
											&& m.compareTo(yydate.parse(fromDate)) >= 0
											&& m.compareTo(yydate.parse(toDate)) <= 0) {

										arryadate.add(m);
										datearry = datearry + "," + dddate.format(m);
										// System.out.println("add in 4th" + dddate.format(m));
										totalcount++;
									}

									j.setTime(j.getTime() + 1000 * 60 * 60 * 24);
								}

							}
							m.setTime(m.getTime() + 1000 * 60 * 60 * 24);
						}

						String dt = year + "-" + (k + 1) + "-0";
						e = yydate.parse(dt);
						e.setTime(e.getTime() + 1000 * 60 * 60 * 24);
						Calendar a = Calendar.getInstance();
						a.setTime(e);
						year = a.get(Calendar.YEAR);
						k=a.get(Calendar.MONTH)+1;

					}
				} else if (Integer.parseInt(weeklyList.get(i).getWoType()) == 1) {

					Date frmdt = yydate.parse(fromDate);
					Date todt = yydate.parse(toDate);
					Calendar fc = Calendar.getInstance();
					fc.setTime(frmdt);

					Calendar tc = Calendar.getInstance();
					tc.setTime(todt);

					Calendar temp = Calendar.getInstance();
					temp.setTime(yydate.parse(fromDate));
					int k=temp.get(Calendar.MONTH)+1;
					int year = fc.get(Calendar.YEAR);
					// System.out.println("year " + year);

					for (Date e = yydate.parse(fromDate); e.compareTo(yydate.parse(toDate)) <= 0;) {

						String fd = year + "-" + k + "-08";
						String ld = year + "-" + k + "-14";

						Date wkfstdt = yydate.parse(fd);
						Date wklstdt = yydate.parse(ld);
						frmdt = yydate.parse(fromDate);
						todt = yydate.parse(toDate);

						int cnt1 = diffrence(wkfstdt, wklstdt, frmdt, todt,
								Integer.parseInt(weeklyList.get(i).getWoDay()));

						String fd1 = year + "-" + k + "-22";
						String ld1 = year + "-" + k + "-28";

						Date wkfstdt1 = yydate.parse(fd1);
						Date wklstdt1 = yydate.parse(ld1);
						frmdt = yydate.parse(fromDate);
						todt = yydate.parse(toDate);

						int cnt2 = diffrence(wkfstdt1, wklstdt1, frmdt, todt,
								Integer.parseInt(weeklyList.get(i).getWoDay()));

						totalcount = totalcount + cnt1 + cnt2;

						// System.out.println("cnt1 " + cnt1 + "cnt2 " + cnt2 + " wkfstdt1 " + wkfstdt1
						// + " wklstdt1 " + wklstdt1 + " " + weeklyList.get(i).getWoType());

						String dt = year + "-" + (k + 1) + "-0";
						e = yydate.parse(dt);
						e.setTime(e.getTime() + 1000 * 60 * 60 * 24);
						Calendar a = Calendar.getInstance();
						a.setTime(e);
						year = a.get(Calendar.YEAR);
						k=a.get(Calendar.MONTH)+1;
					}
				} else if (Integer.parseInt(weeklyList.get(i).getWoType()) == 2) {

					Date frmdt = yydate.parse(fromDate);
					Date todt = yydate.parse(toDate);
					Calendar fc = Calendar.getInstance();
					fc.setTime(frmdt);

					Calendar tc = Calendar.getInstance();
					tc.setTime(todt);

					Calendar temp = Calendar.getInstance();
					temp.setTime(yydate.parse(fromDate));
					int k=temp.get(Calendar.MONTH)+1;
					int year = fc.get(Calendar.YEAR);
					//System.out.println("year " + year);

					for (Date e = yydate.parse(fromDate); e.compareTo(yydate.parse(toDate)) <= 0;) {

						String fd = year + "-" + k + "-01";
						String ld = year + "-" + k + "-07";

						Date wkfstdt = yydate.parse(fd);
						Date wklstdt = yydate.parse(ld);
						frmdt = yydate.parse(fromDate);
						todt = yydate.parse(toDate);

						int cnt1 = diffrence(wkfstdt, wklstdt, frmdt, todt,
								Integer.parseInt(weeklyList.get(i).getWoDay()));

						String fd1 = year + "-" + k + "-15";
						String ld1 = year + "-" + k + "-21";

						Date wkfstdt1 = yydate.parse(fd1);
						Date wklstdt1 = yydate.parse(ld1);
						frmdt = yydate.parse(fromDate);
						todt = yydate.parse(toDate);

						int cnt2 = diffrence(wkfstdt1, wklstdt1, frmdt, todt,
								Integer.parseInt(weeklyList.get(i).getWoDay()));

						String fd3 = year + "-" + k + "-29";
						String ld3 = year + "-" + (k + 1) + "-0";

						Date wkfstdt3 = yydate.parse(fd3);
						Date wklstdt3 = yydate.parse(ld3);

						frmdt = yydate.parse(fromDate);
						todt = yydate.parse(toDate);

						int cnt3 = diffrence(wkfstdt3, wklstdt3, frmdt, todt,
								Integer.parseInt(weeklyList.get(i).getWoDay()));

						totalcount = totalcount + cnt1 + cnt2 + cnt3;

						String dt = year + "-" + (k + 1) + "-0";
						e = yydate.parse(dt);
						e.setTime(e.getTime() + 1000 * 60 * 60 * 24);
						Calendar a = Calendar.getInstance();
						a.setTime(e);
						year = a.get(Calendar.YEAR);
						k=a.get(Calendar.MONTH)+1;
					}
				}

			}

			for (int i = 0; i < holidayList.size(); i++) {

				// alert("Data " +JSON.stringify(data.holidayList[i]));

				Date frmdt = yydate.parse(fromDate);
				Date todt = yydate.parse(toDate);

				int tempdiff = difffun(holidayList.get(i).getHolidayFromdt(), holidayList.get(i).getHolidayTodt());

				String[] a = {};
				// datearry.substring(1 ,datearry.length());

				// System.out.println(datearry);
				try {

					a = datearry.split(",");

				} catch (Exception e) {

				}

				for (int j = 1; j < a.length; j++) {

					/*System.out.println(dddate.parse(a[j]) + " arryadate.get(j) "
							+ yydate.parse(holidayList.get(i).getHolidayFromdt()) + " "
							+ yydate.parse(holidayList.get(i).getHolidayTodt()));*/

					if (dddate.parse(a[j]).compareTo(yydate.parse(holidayList.get(i).getHolidayFromdt())) >= 0
							&& dddate.parse(a[j]).compareTo(yydate.parse(holidayList.get(i).getHolidayTodt())) <= 0) {

						tempdiff--;
					}
				}

				totalcount = totalcount + tempdiff;
			}

			diff = diff - totalcount;

			leaveCount.setHolidaycount(totalcount);
			leaveCount.setLeavecount(diff);
			
			//above remove holiday and weekend
			  
			
		} catch (Exception e) {

			e.printStackTrace();
		}

		return leaveCount;
	}
	
	
	
	public int difffun(String date1, String date2) {

		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

		int result = 0;

		try {
			Date date3 = myFormat.parse(date1);
			Date date4 = myFormat.parse(date2);
			long diff = date4.getTime() - date3.getTime();
			result = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		} catch (Exception e) {

		}

		return result + 1;
	}
	
	public int diffrence(Date date1, Date date2, Date holfrstdt, Date holseconddt, int day) {

		int totalcount = 0;
		SimpleDateFormat dddate = new SimpleDateFormat("dd-MM-yyyy");
		// System.out.println("date1 " + date1 + "date2 " + date2 + " holfrstdt " +
		// holfrstdt + " holseconddt " + holseconddt + " day " + day);

		for (Date m = holfrstdt; m.compareTo(holseconddt) <= 0;) {

			if (m.compareTo(date1) >= 0 && m.compareTo(date2) <= 0) {

				for (Date j = m; j.compareTo(date2) <= 0;) {

					Calendar fc = Calendar.getInstance();
					fc.setTime(j);
					int dayOfWeek = fc.get(Calendar.DAY_OF_WEEK) - 1;

					if (dayOfWeek == day && m.compareTo(holfrstdt) >= 0 && m.compareTo(holseconddt) <= 0) {

						arryadate.add(m);
						datearry = datearry + "," + dddate.format(m);
						//System.out.println("add in odd even" + dddate.format(m));
						totalcount++;
					}
					j.setTime(j.getTime() + 1000 * 60 * 60 * 24);

				}

			}
			m.setTime(m.getTime() + 1000 * 60 * 60 * 24);
		}

		return totalcount;
	}

}
