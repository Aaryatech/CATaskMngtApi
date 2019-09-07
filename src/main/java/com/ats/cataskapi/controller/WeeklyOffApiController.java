package com.ats.cataskapi.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.common.DateConvertor;
import com.ats.cataskapi.model.CapacityDetailByEmp;
import com.ats.cataskapi.model.DateWithAttendanceSts;
import com.ats.cataskapi.model.EmpListWithDateList;
import com.ats.cataskapi.model.EmpListWithDateWiseDetail;
import com.ats.cataskapi.model.EmployeeListWithAvailableHours;
import com.ats.cataskapi.model.GetWeeklyOff;
import com.ats.cataskapi.model.Holiday;
import com.ats.cataskapi.model.Info;
import com.ats.cataskapi.model.LeaveCount;
import com.ats.cataskapi.model.LeaveDetailWithFreeHours;
import com.ats.cataskapi.model.WeeklyOff;
import com.ats.cataskapi.repositories.CapacityDetailByEmpRepo;
import com.ats.cataskapi.repositories.EmpListWithDateWiseDetailRepo;
import com.ats.cataskapi.repositories.EmployeeListWithAvailableHoursRepo;
import com.ats.cataskapi.repositories.GetWeeklyOffRepo;
import com.ats.cataskapi.repositories.HolidayRepo;
import com.ats.cataskapi.repositories.WeeklyOffRepo;

@RestController
public class WeeklyOffApiController {

	@Autowired
	WeeklyOffRepo weeklyOffRepo;

	@Autowired
	GetWeeklyOffRepo getWeeklyOffRepo;

	@Autowired
	HolidayRepo holidayRepo;

	@Autowired
	EmployeeListWithAvailableHoursRepo employeeListWithAvailableHoursRepo;

	@Autowired
	EmpListWithDateWiseDetailRepo empListWithDateWiseDetailRepo;

	@Autowired
	CapacityDetailByEmpRepo capacityDetailByEmpRepo;

	@RequestMapping(value = { "/getWeeklyOffList" }, method = RequestMethod.POST)
	public @ResponseBody List<GetWeeklyOff> getWeeklyOffList(@RequestParam("companyId") int companyId,
			@RequestParam("locIdList") List<Integer> locIdList) {

		List<GetWeeklyOff> list = new ArrayList<GetWeeklyOff>();
		try {

			list = getWeeklyOffRepo.getListByCompanyId();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;

	}

	@RequestMapping(value = { "/saveWeeklyOff" }, method = RequestMethod.POST)
	public @ResponseBody WeeklyOff saveWeeklyOff(@RequestBody WeeklyOff weeklyOff) {

		WeeklyOff save = new WeeklyOff();
		try {

			save = weeklyOffRepo.saveAndFlush(weeklyOff);

			if (save != null) {
				save.setError(false);
			} else {

				save = new WeeklyOff();
				save.setError(true);
			}

		} catch (Exception e) {
			save = new WeeklyOff();
			save.setError(true);
			e.printStackTrace();
		}

		return save;
	}

	@RequestMapping(value = { "/getWeeklyOffList" }, method = RequestMethod.GET)
	public @ResponseBody List<WeeklyOff> getWeeklyOffList() {

		List<WeeklyOff> list = new ArrayList<WeeklyOff>();
		try {

			list = weeklyOffRepo.findByDelStatusAndIsActive(1, 1);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;

	}

	@RequestMapping(value = { "/getWeeklyOffById" }, method = RequestMethod.POST)
	public @ResponseBody WeeklyOff getWeeklyOffById(@RequestParam("woId") int woId) {

		WeeklyOff woo = new WeeklyOff();
		try {

			woo = weeklyOffRepo.findBywoIdAndDelStatus(woId, 1);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return woo;

	}

	@RequestMapping(value = { "/deleteWeeklyOff" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteWeeklyOff(@RequestParam("woId") int woId) {

		Info info = new Info();

		try {

			int delete = weeklyOffRepo.deleteWeeklyOff(woId);

			if (delete > 0) {
				info.setError(false);
				info.setMsg("deleted");
			} else {
				info.setError(true);
				info.setMsg("failed");
			}

		} catch (Exception e) {

			e.printStackTrace();
			info.setError(true);
			info.setMsg("failed");
		}

		return info;

	}

	@RequestMapping(value = { "/getWeeklyOffListByEmpId" }, method = RequestMethod.POST)
	public @ResponseBody List<WeeklyOff> getWeeklyOffListByEmpId(@RequestParam("empId") int empId) {

		List<WeeklyOff> list = new ArrayList<WeeklyOff>();
		try {

			list = weeklyOffRepo.getWeeklyOffListByEmpId();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;

	}

	@RequestMapping(value = { "/getHolidayByEmpIdAndFromDateTodate" }, method = RequestMethod.POST)
	public @ResponseBody List<Holiday> getHolidayByEmpIdAndFromDateTodate(@RequestParam("empId") int empId,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		List<Holiday> list = new ArrayList<Holiday>();
		try {

			list = holidayRepo.getHolidayByEmpIdAndFromDateTodate(fromDate, toDate);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;

	}

	List<Date> arryadate = new ArrayList<>();
	String datearry = new String();

	@RequestMapping(value = { "/calculateHolidayBetweenDate" }, method = RequestMethod.POST)
	public @ResponseBody LeaveCount calculateHolidayBetweenDate(@RequestParam("empId") int empId,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

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

					int year = fc.get(Calendar.YEAR);
					// System.out.println("year " + year);

					for (int k = fc.get(Calendar.MONTH) + 1; k <= tc.get(Calendar.MONTH) + 1; k++) {

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

						String dt = year + "-" + (k + 1) + "-0";
						Date lastDay = yydate.parse(dt);
						lastDay.setTime(lastDay.getTime() + 1000 * 60 * 60 * 24);
						Calendar a = Calendar.getInstance();
						a.setTime(lastDay);
						year = a.get(Calendar.YEAR);
					}
				} else if (Integer.parseInt(weeklyList.get(i).getWoType()) == 4) {

					Date frmdt = yydate.parse(fromDate);
					Date todt = yydate.parse(toDate);
					Calendar fc = Calendar.getInstance();
					fc.setTime(frmdt);

					Calendar tc = Calendar.getInstance();
					tc.setTime(todt);

					int year = fc.get(Calendar.YEAR);
					// System.out.println("year " + year);

					for (int k = fc.get(Calendar.MONTH) + 1; k <= tc.get(Calendar.MONTH) + 1; k++) {

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

						String dt = year + "-" + (k + 1) + "-0";
						Date lastDay = yydate.parse(dt);
						lastDay.setTime(lastDay.getTime() + 1000 * 60 * 60 * 24);
						Calendar a = Calendar.getInstance();
						a.setTime(lastDay);
						year = a.get(Calendar.YEAR);
					}
				} else if (Integer.parseInt(weeklyList.get(i).getWoType()) == 5) {

					Date frmdt = yydate.parse(fromDate);
					Date todt = yydate.parse(toDate);
					Calendar fc = Calendar.getInstance();
					fc.setTime(frmdt);

					Calendar tc = Calendar.getInstance();
					tc.setTime(todt);

					int year = fc.get(Calendar.YEAR);
					// System.out.println("year " + year);

					for (int k = fc.get(Calendar.MONTH) + 1; k <= tc.get(Calendar.MONTH) + 1; k++) {

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
						Date lastDay = yydate.parse(dt);
						lastDay.setTime(lastDay.getTime() + 1000 * 60 * 60 * 24);
						Calendar a = Calendar.getInstance();
						a.setTime(lastDay);
						year = a.get(Calendar.YEAR);
					}
				} else if (Integer.parseInt(weeklyList.get(i).getWoType()) == 6) {

					Date frmdt = yydate.parse(fromDate);
					Date todt = yydate.parse(toDate);
					Calendar fc = Calendar.getInstance();
					fc.setTime(frmdt);

					Calendar tc = Calendar.getInstance();
					tc.setTime(todt);

					int year = fc.get(Calendar.YEAR);
					// System.out.println("year " + year);

					for (int k = fc.get(Calendar.MONTH) + 1; k <= tc.get(Calendar.MONTH) + 1; k++) {

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
						Date lastDay = yydate.parse(dt);
						lastDay.setTime(lastDay.getTime() + 1000 * 60 * 60 * 24);
						Calendar a = Calendar.getInstance();
						a.setTime(lastDay);
						year = a.get(Calendar.YEAR);

					}
				} else if (Integer.parseInt(weeklyList.get(i).getWoType()) == 1) {

					Date frmdt = yydate.parse(fromDate);
					Date todt = yydate.parse(toDate);
					Calendar fc = Calendar.getInstance();
					fc.setTime(frmdt);

					Calendar tc = Calendar.getInstance();
					tc.setTime(todt);

					int year = fc.get(Calendar.YEAR);
					// System.out.println("year " + year);

					for (int k = fc.get(Calendar.MONTH) + 1; k <= tc.get(Calendar.MONTH) + 1; k++) {

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
						Date lastDay = yydate.parse(dt);
						lastDay.setTime(lastDay.getTime() + 1000 * 60 * 60 * 24);
						Calendar a = Calendar.getInstance();
						a.setTime(lastDay);
						year = a.get(Calendar.YEAR);
					}
				} else if (Integer.parseInt(weeklyList.get(i).getWoType()) == 2) {

					Date frmdt = yydate.parse(fromDate);
					Date todt = yydate.parse(toDate);
					Calendar fc = Calendar.getInstance();
					fc.setTime(frmdt);

					Calendar tc = Calendar.getInstance();
					tc.setTime(todt);

					int year = fc.get(Calendar.YEAR);
					System.out.println("year " + year);

					for (int k = fc.get(Calendar.MONTH) + 1; k <= tc.get(Calendar.MONTH) + 1; k++) {

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
						Date lastDay = yydate.parse(dt);
						lastDay.setTime(lastDay.getTime() + 1000 * 60 * 60 * 24);
						Calendar a = Calendar.getInstance();
						a.setTime(lastDay);
						year = a.get(Calendar.YEAR);
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

					System.out.println(dddate.parse(a[j]) + " arryadate.get(j) "
							+ yydate.parse(holidayList.get(i).getHolidayFromdt()) + " "
							+ yydate.parse(holidayList.get(i).getHolidayTodt()));

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
		} catch (Exception e) {

			e.printStackTrace();
		}

		return leaveCount;

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
						System.out.println("add in odd even" + dddate.format(m));
						totalcount++;
					}
					j.setTime(j.getTime() + 1000 * 60 * 60 * 24);

				}

			}
			m.setTime(m.getTime() + 1000 * 60 * 60 * 24);
		}

		return totalcount;
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

	@RequestMapping(value = { "/getTotalAvailableHours" }, method = RequestMethod.POST)
	public @ResponseBody LeaveDetailWithFreeHours getTotalAvailableHours(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		List<EmployeeListWithAvailableHours> list = new ArrayList<>();
		SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
		LeaveDetailWithFreeHours leaveDetailWithFreeHours = new LeaveDetailWithFreeHours();

		try {

			list = employeeListWithAvailableHoursRepo.getLeaveRecord(fromDate, toDate);
			int empCount = employeeListWithAvailableHoursRepo.getEmpCount();

			LeaveCount totalDayCount = calculateHolidayBetweenDate(0, fromDate, toDate);
			leaveDetailWithFreeHours.setTotalFreeHours(totalDayCount.getLeavecount() * 7 * empCount);

			for (int i = 0; i < list.size(); i++) {

				String lvfmdt = yy.format(list.get(i).getLeaveFromdt());
				String lvtodt = yy.format(list.get(i).getLeaveTodt());

				/*
				 * System.out.println("leave from date " + yy.parse(lvfmdt));
				 * System.out.println("leave to date " + yy.parse(lvtodt));
				 * System.out.println(" from date " + yy.parse(fromDate));
				 * System.out.println(" to date " + yy.parse(toDate));
				 */

				if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) <= 0
						&& yy.parse(toDate).compareTo(yy.parse(lvfmdt)) >= 0
						&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) < 0) {

					System.out.println("in if");
					if (list.get(i).getLeaveDuration() == 0) {

						LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)), toDate);
						list.get(i).setBsyHours((float) (bsyDaycount.getLeavecount() * 3.5));
						leaveDetailWithFreeHours.setTotalBsyHours(
								list.get(i).getBsyHours() + leaveDetailWithFreeHours.getTotalBsyHours());

					} else {

						LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)), toDate);
						list.get(i).setBsyHours(bsyDaycount.getLeavecount() * 7);
						leaveDetailWithFreeHours.setTotalBsyHours(
								list.get(i).getBsyHours() + leaveDetailWithFreeHours.getTotalBsyHours());
					}

				} else if (yy.parse(fromDate).compareTo(yy.parse(lvtodt)) <= 0
						&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) >= 0
						&& yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) > 0) {

					System.out.println("in if else 2");

					if (list.get(i).getLeaveDuration() == 0) {

						LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate, yy.format(yy.parse(lvtodt)));
						list.get(i).setBsyHours((float) (bsyDaycount.getLeavecount() * 3.5));
						leaveDetailWithFreeHours.setTotalBsyHours(
								list.get(i).getBsyHours() + leaveDetailWithFreeHours.getTotalBsyHours());

					} else {

						LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate, yy.format(yy.parse(lvtodt)));
						list.get(i).setBsyHours(bsyDaycount.getLeavecount() * 7);
						leaveDetailWithFreeHours.setTotalBsyHours(
								list.get(i).getBsyHours() + leaveDetailWithFreeHours.getTotalBsyHours());
					}

				} else if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) <= 0
						&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) >= 0) {

					System.out.println("in if else 3");

					if (list.get(i).getLeaveDuration() == 0) {

						LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
								yy.format(yy.parse(lvtodt)));
						list.get(i).setBsyHours((float) (bsyDaycount.getLeavecount() * 3.5));
						leaveDetailWithFreeHours.setTotalBsyHours(
								list.get(i).getBsyHours() + leaveDetailWithFreeHours.getTotalBsyHours());

					} else {

						LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
								yy.format(yy.parse(lvtodt)));
						list.get(i).setBsyHours(bsyDaycount.getLeavecount() * 7);
						leaveDetailWithFreeHours.setTotalBsyHours(
								list.get(i).getBsyHours() + leaveDetailWithFreeHours.getTotalBsyHours());
					}

				} else if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) >= 0
						&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) <= 0) {

					System.out.println("in if else 4");

					if (list.get(i).getLeaveDuration() == 0) {

						LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate, toDate);
						list.get(i).setBsyHours((float) (bsyDaycount.getLeavecount() * 3.5));
						leaveDetailWithFreeHours.setTotalBsyHours(
								list.get(i).getBsyHours() + leaveDetailWithFreeHours.getTotalBsyHours());

					} else {

						LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate, toDate);
						list.get(i).setBsyHours(bsyDaycount.getLeavecount() * 7);
						leaveDetailWithFreeHours.setTotalBsyHours(
								list.get(i).getBsyHours() + leaveDetailWithFreeHours.getTotalBsyHours());
					}
				}

			}

			leaveDetailWithFreeHours.setTotalAvailableHours(
					leaveDetailWithFreeHours.getTotalFreeHours() - leaveDetailWithFreeHours.getTotalBsyHours());
			leaveDetailWithFreeHours.setList(list);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return leaveDetailWithFreeHours;

	}

	@RequestMapping(value = { "/daywiseLeaveHistoryofEmployee" }, method = RequestMethod.POST)
	public @ResponseBody EmpListWithDateList daywiseLeaveHistoryofEmployee(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		List<EmpListWithDateWiseDetail> list = new ArrayList<>();
		EmpListWithDateList empListWithDateList = new EmpListWithDateList();
		List<String> dateslist = new ArrayList<>();

		try {
			List<Date> dates = new ArrayList<Date>();
			DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date startDate = (Date) formatter.parse(fromDate);
			Date endDate = (Date) formatter.parse(toDate);

			long interval = 24 * 1000 * 60 * 60;
			long endTime = endDate.getTime();
			long curTime = startDate.getTime();

			while (curTime <= endTime) {

				dateslist.add(formatter.format(new Date(curTime)));
				dates.add(new Date(curTime));
				curTime += interval;

			}
			DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String holidayArray = new String();
			list = empListWithDateWiseDetailRepo.getEmployeeList();

			calculateHolidayBetweenDate(0, DateConvertor.convertToYMD(fromDate), DateConvertor.convertToYMD(toDate));
			List<Holiday> holidayList = holidayRepo.getHolidayByEmpIdAndFromDateTodate(
					DateConvertor.convertToYMD(fromDate), DateConvertor.convertToYMD(toDate));

			for (int i = 0; i < holidayList.size(); i++) {

				String[] a = {};

				try {

					a = datearry.split(",");

				} catch (Exception e) {

				}
				Date date1 = sf.parse(holidayList.get(i).getHolidayFromdt());
				Date date2 = sf.parse(holidayList.get(i).getHolidayTodt());

				System.out.println("date1 " + date1 + "date2 " + date2);

				for (Date k = date1; k.compareTo(date2) <= 0;) {

					System.out.println("date1 " + date1 + "date2 " + date2);

					for (int j = 1; j < a.length; j++) {

						Calendar fc = Calendar.getInstance();
						fc.setTime(k);

						System.out.println(formatter.parse(a[j]) + " " + date1);
						if (formatter.parse(a[j]).compareTo(date1) != 0) {

							holidayArray = holidayArray + "," + formatter.format(date1);

						}

					}
					k.setTime(k.getTime() + 1000 * 60 * 60 * 24);

				}

			}
			datearry = datearry + holidayArray;

			for (int j = 0; j < list.size(); j++) {

				List<DateWithAttendanceSts> atndsList = new ArrayList<>();

				for (int i = 0; i < dates.size(); i++) {

					String[] a = {};

					try {

						a = datearry.split(",");

					} catch (Exception e) {

					}

					int flag = 0;
					for (int k = 1; k < a.length; k++) {

						if (formatter.parse(a[k]).compareTo(dates.get(i)) == 0) {
							DateWithAttendanceSts dateWithAttendanceSts = new DateWithAttendanceSts();
							dateWithAttendanceSts.setDate(formatter.format(dates.get(i)));
							dateWithAttendanceSts.setSts("HO");
							flag = 1;
							atndsList.add(dateWithAttendanceSts);
							break;
						}

					}

					if (flag == 0) {
						int halfCount = empListWithDateWiseDetailRepo.getcountOfLeave(sf.format(dates.get(i)),
								list.get(j).getEmpId(), 0);
						int fullCount = empListWithDateWiseDetailRepo.getcountOfLeave(sf.format(dates.get(i)),
								list.get(j).getEmpId(), 1);
						DateWithAttendanceSts dateWithAttendanceSts = new DateWithAttendanceSts();
						dateWithAttendanceSts.setDate(formatter.format(dates.get(i)));

						if (halfCount > 0) {
							dateWithAttendanceSts.setSts("H");
						} else if (fullCount > 0) {
							dateWithAttendanceSts.setSts("AB");
						} else {
							dateWithAttendanceSts.setSts("P");
						}
						atndsList.add(dateWithAttendanceSts);
					}

				}
				list.get(j).setAtndsList(atndsList);

			}

			empListWithDateList.setDateslist(dateslist);
			empListWithDateList.setEmpListWithDateWiseDetailLst(list);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return empListWithDateList;

	}

	@RequestMapping(value = { "/getEmployeeCapacityDetail" }, method = RequestMethod.POST)
	public @ResponseBody List<CapacityDetailByEmp> getEmployeeCapacityDetail(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("empId") int empId) {

		List<CapacityDetailByEmp> list = new ArrayList<CapacityDetailByEmp>();

		try {

			String empIds = capacityDetailByEmpRepo.getEmployeeList(empId);

			String[] ids = empIds.split(",");
			list = capacityDetailByEmpRepo.getEmployeeCapacityDetail(DateConvertor.convertToYMD(fromDate),
					DateConvertor.convertToYMD(toDate),ids);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;

	}

}
