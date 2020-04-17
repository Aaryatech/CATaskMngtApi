package com.ats.cataskapi.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
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
import com.ats.cataskapi.model.BugetedAmtAndRevenue;
import com.ats.cataskapi.model.BugetedMinAndWorkedMinByEmpIds;
import com.ats.cataskapi.model.CapacityDetailByEmp;
import com.ats.cataskapi.model.DateWithAttendanceSts;
import com.ats.cataskapi.model.EmpListWithDateList;
import com.ats.cataskapi.model.EmpListWithDateWiseDetail;
import com.ats.cataskapi.model.EmployeeHolidayListForDashbord;
import com.ats.cataskapi.model.EmployeeListWithAvailableHours;
import com.ats.cataskapi.model.GetWeeklyOff;
import com.ats.cataskapi.model.Holiday;
import com.ats.cataskapi.model.Info;
import com.ats.cataskapi.model.LeaveCount;
import com.ats.cataskapi.model.LeaveDetailWithFreeHours;
import com.ats.cataskapi.model.ManagerListWithEmpIds;
import com.ats.cataskapi.model.WeeklyOff;
import com.ats.cataskapi.repositories.BugetedAmtAndRevenueRepo;
import com.ats.cataskapi.repositories.BugetedMinAndWorkedMinByEmpIdsRepo;
import com.ats.cataskapi.repositories.CapacityDetailByEmpRepo;
import com.ats.cataskapi.repositories.EmpListWithDateWiseDetailRepo;
import com.ats.cataskapi.repositories.EmployeeHolidayListForDashbordRepo;
import com.ats.cataskapi.repositories.EmployeeListWithAvailableHoursRepo;
import com.ats.cataskapi.repositories.GetWeeklyOffRepo;
import com.ats.cataskapi.repositories.HolidayRepo;
import com.ats.cataskapi.repositories.ManagerListWithEmpIdsRepo;
import com.ats.cataskapi.repositories.WeeklyOffRepo;
import com.ats.cataskapi.task.model.EmpSalary;
import com.ats.cataskapi.task.repo.EmpSalaryRepo;

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

	@Autowired
	EmpSalaryRepo empSalaryRepo;

	@Autowired
	BugetedAmtAndRevenueRepo bugetedAmtAndRevenueRepo;

	@Autowired
	ManagerListWithEmpIdsRepo managerListWithEmpIdsRepo;

	@Autowired
	EmployeeHolidayListForDashbordRepo employeeHolidayListForDashbordRepo;

	@Autowired
	BugetedMinAndWorkedMinByEmpIdsRepo bugetedMinAndWorkedMinByEmpIdsRepo;

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

					Calendar temp = Calendar.getInstance();
					temp.setTime(yydate.parse(fromDate));
					int k = temp.get(Calendar.MONTH) + 1;
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

						String dt = year + "-" + (k + 1) + "-0";
						e = yydate.parse(dt);
						e.setTime(e.getTime() + 1000 * 60 * 60 * 24);
						Calendar a = Calendar.getInstance();
						a.setTime(e);
						year = a.get(Calendar.YEAR);
						k = a.get(Calendar.MONTH) + 1;
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
					int k = temp.get(Calendar.MONTH) + 1;
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

						String dt = year + "-" + (k + 1) + "-0";
						e = yydate.parse(dt);
						e.setTime(e.getTime() + 1000 * 60 * 60 * 24);
						Calendar a = Calendar.getInstance();
						a.setTime(e);
						year = a.get(Calendar.YEAR);
						k = a.get(Calendar.MONTH) + 1;
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
					int k = temp.get(Calendar.MONTH) + 1;
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
						k = a.get(Calendar.MONTH) + 1;
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
					int k = temp.get(Calendar.MONTH) + 1;
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
						k = a.get(Calendar.MONTH) + 1;

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
					int k = temp.get(Calendar.MONTH) + 1;
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
						k = a.get(Calendar.MONTH) + 1;
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
					int k = temp.get(Calendar.MONTH) + 1;
					int year = fc.get(Calendar.YEAR);
					System.out.println("year " + year);

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
						k = a.get(Calendar.MONTH) + 1;
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
			@RequestParam("toDate") String toDate, @RequestParam("empId") int empId,
			@RequestParam("empType") int empType) {

		List<CapacityDetailByEmp> empCaplist = new ArrayList<CapacityDetailByEmp>();

		try {

			String[] ids = {};

			if (empType == 5) {

				String empIds = String.valueOf(empId);
				ids = empIds.split(",");

			} else {

				String empIds = capacityDetailByEmpRepo.getEmployeeList(empId);
				ids = empIds.split(",");

			}

			LinkedHashSet<String> hashSet = new LinkedHashSet<>(Arrays.asList(ids));
			ArrayList<String> arryids = new ArrayList<>(hashSet);

			empCaplist = capacityDetailByEmpRepo.getEmployeeCapacityDetail(fromDate, toDate, arryids);
			SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");

			LeaveCount totalDayCount = calculateHolidayBetweenDate(0, fromDate, toDate);
			float freeHours = totalDayCount.getLeavecount() * 7;

			List<EmployeeListWithAvailableHours> list = new ArrayList<>();
			list = employeeListWithAvailableHoursRepo.getLeaveRecord(fromDate, toDate, arryids);

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

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}

						} else if (yy.parse(fromDate).compareTo(yy.parse(lvtodt)) <= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) >= 0
								&& yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) > 0) {

							System.out.println("in if else 2");

							if (list.get(i).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate,
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate,
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}

						} else if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) <= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) >= 0) {

							System.out.println("in if else 3");

							if (list.get(i).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}

						} else if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) >= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) <= 0) {

							System.out.println("in if else 4");

							if (list.get(i).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate, toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate, toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}
						}
					}
				}

				empCaplist.get(j).setBugetedCap((float) (freeHours - bsyhrs));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return empCaplist;

	}
	
	//Sachin 17-04-2020 Expected Report A 1  Total Capacity Utilisation report"
	@RequestMapping(value = { "/getAllEmployeeCapacityDetail" }, method = RequestMethod.POST)
	public @ResponseBody List<CapacityDetailByEmp> getAllEmployeeCapacityDetail(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		List<CapacityDetailByEmp> empCaplist = new ArrayList<CapacityDetailByEmp>();

		try {

			String[] ids = {};


				String empIds = capacityDetailByEmpRepo.getMngAndEmpEmployeeList();
				ids = empIds.split(",");


			LinkedHashSet<String> hashSet = new LinkedHashSet<>(Arrays.asList(ids));
			ArrayList<String> arryids = new ArrayList<>(hashSet);

			empCaplist = capacityDetailByEmpRepo.getEmployeeCapacityDetail(fromDate, toDate, arryids);
			SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");

			LeaveCount totalDayCount = calculateHolidayBetweenDate(0, fromDate, toDate);
			float freeHours = totalDayCount.getLeavecount() * 7;

			List<EmployeeListWithAvailableHours> list = new ArrayList<>();
			list = employeeListWithAvailableHoursRepo.getLeaveRecord(fromDate, toDate, arryids);

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

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}

						} else if (yy.parse(fromDate).compareTo(yy.parse(lvtodt)) <= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) >= 0
								&& yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) > 0) {

							System.out.println("in if else 2");

							if (list.get(i).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate,
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate,
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}

						} else if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) <= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) >= 0) {

							System.out.println("in if else 3");

							if (list.get(i).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}

						} else if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) >= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) <= 0) {

							System.out.println("in if else 4");

							if (list.get(i).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate, toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate, toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}
						}
					}
				}

				empCaplist.get(j).setBugetedCap((float) (freeHours - bsyhrs));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return empCaplist;

	}

	@RequestMapping(value = { "/getEmployeeCapacityDetailForManagerDashboard" }, method = RequestMethod.POST)
	public @ResponseBody List<CapacityDetailByEmp> getEmployeeCapacityDetailForManagerDashboard(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("empId") int empId, @RequestParam("userId") int userId) {

		List<CapacityDetailByEmp> empCaplist = new ArrayList<CapacityDetailByEmp>();

		try {

			String[] ids = {};

			String empIds = capacityDetailByEmpRepo.getEmployeeListByManagerIdAndUserIdBetweenDate(empId, userId,
					fromDate, toDate);
			ids = empIds.split(",");

			LinkedHashSet<String> hashSet = new LinkedHashSet<>(Arrays.asList(ids));
			ArrayList<String> arryids = new ArrayList<>(hashSet);

			empCaplist = capacityDetailByEmpRepo.getEmployeeCapacityDetail(fromDate, toDate, arryids);
			SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");

			LeaveCount totalDayCount = calculateHolidayBetweenDate(0, fromDate, toDate);
			float freeHours = totalDayCount.getLeavecount() * 7;

			List<EmployeeListWithAvailableHours> list = new ArrayList<>();
			list = employeeListWithAvailableHoursRepo.getLeaveRecord(fromDate, toDate, arryids);

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

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}

						} else if (yy.parse(fromDate).compareTo(yy.parse(lvtodt)) <= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) >= 0
								&& yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) > 0) {

							System.out.println("in if else 2");

							if (list.get(i).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate,
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate,
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}

						} else if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) <= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) >= 0) {

							System.out.println("in if else 3");

							if (list.get(i).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}

						} else if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) >= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) <= 0) {

							System.out.println("in if else 4");

							if (list.get(i).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate, toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate, toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}
						}
					}
				}

				empCaplist.get(j).setBugetedCap((float) (freeHours - bsyhrs));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return empCaplist;

	}

	/*
	 * @RequestMapping(value = { "/calculateBugetedAmtAndBugetedRevenue" }, method =
	 * RequestMethod.POST) public @ResponseBody BugetedAmtAndRevenue
	 * calculateBugetedAmtAndBugetedRevenue(@RequestParam("empId") int empId,
	 * 
	 * @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String
	 * toDate,
	 * 
	 * @RequestParam("typeId") int typeId, @RequestParam("groupId") int groupId,
	 * 
	 * @RequestParam("clientId") int clientId) {
	 * 
	 * BugetedAmtAndRevenue bugetedAmtAndRevenue = new BugetedAmtAndRevenue();
	 * 
	 * try {
	 * 
	 * List<Integer> clntIds = new ArrayList<>();
	 * 
	 * if (clientId == 0) { clntIds =
	 * bugetedAmtAndRevenueRepo.getclientByGroupId(groupId); } else {
	 * clntIds.add(clientId); } LeaveCount totalDayCount =
	 * calculateHolidayBetweenDate(0, fromDate, toDate); float freeHours =
	 * totalDayCount.getLeavecount() * 7;
	 * 
	 * List<EmployeeListWithAvailableHours> list = new ArrayList<>(); list =
	 * employeeListWithAvailableHoursRepo.getLeaveRecordByEmpId(fromDate, toDate,
	 * empId);
	 * 
	 * SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
	 * 
	 * float bsyhrs = 0;
	 * 
	 * for (int i = 0; i < list.size(); i++) {
	 * 
	 * String lvfmdt = yy.format(list.get(i).getLeaveFromdt()); String lvtodt =
	 * yy.format(list.get(i).getLeaveTodt());
	 * 
	 * if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) <= 0 &&
	 * yy.parse(toDate).compareTo(yy.parse(lvfmdt)) >= 0 &&
	 * yy.parse(toDate).compareTo(yy.parse(lvtodt)) < 0) {
	 * 
	 * if (list.get(i).getLeaveDuration() == 0) {
	 * 
	 * LeaveCount bsyDaycount = calculateHolidayBetweenDate(0,
	 * yy.format(yy.parse(lvfmdt)), toDate); bsyhrs = (float) (bsyhrs +
	 * (bsyDaycount.getLeavecount() * 3.5));
	 * 
	 * } else {
	 * 
	 * LeaveCount bsyDaycount = calculateHolidayBetweenDate(0,
	 * yy.format(yy.parse(lvfmdt)), toDate); bsyhrs = (float) (bsyhrs +
	 * (bsyDaycount.getLeavecount() * 7)); }
	 * 
	 * } else if (yy.parse(fromDate).compareTo(yy.parse(lvtodt)) <= 0 &&
	 * yy.parse(toDate).compareTo(yy.parse(lvtodt)) >= 0 &&
	 * yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) > 0) {
	 * 
	 * if (list.get(i).getLeaveDuration() == 0) {
	 * 
	 * LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate,
	 * yy.format(yy.parse(lvtodt))); bsyhrs = (float) (bsyhrs +
	 * (bsyDaycount.getLeavecount() * 3.5));
	 * 
	 * } else {
	 * 
	 * LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate,
	 * yy.format(yy.parse(lvtodt))); bsyhrs = (float) (bsyhrs +
	 * (bsyDaycount.getLeavecount() * 7)); }
	 * 
	 * } else if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) <= 0 &&
	 * yy.parse(toDate).compareTo(yy.parse(lvtodt)) >= 0) {
	 * 
	 * if (list.get(i).getLeaveDuration() == 0) {
	 * 
	 * LeaveCount bsyDaycount = calculateHolidayBetweenDate(0,
	 * yy.format(yy.parse(lvfmdt)), yy.format(yy.parse(lvtodt))); bsyhrs = (float)
	 * (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));
	 * 
	 * } else {
	 * 
	 * LeaveCount bsyDaycount = calculateHolidayBetweenDate(0,
	 * yy.format(yy.parse(lvfmdt)), yy.format(yy.parse(lvtodt))); bsyhrs = (float)
	 * (bsyhrs + (bsyDaycount.getLeavecount() * 7)); }
	 * 
	 * } else if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) >= 0 &&
	 * yy.parse(toDate).compareTo(yy.parse(lvtodt)) <= 0) {
	 * 
	 * if (list.get(i).getLeaveDuration() == 0) {
	 * 
	 * LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate, toDate);
	 * bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));
	 * 
	 * } else {
	 * 
	 * LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate, toDate);
	 * bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7)); } }
	 * 
	 * }
	 * 
	 * float bugetedCap = freeHours - bsyhrs;
	 * 
	 * Date date = yy.parse(fromDate); LocalDate localDate =
	 * date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); int month =
	 * localDate.getMonthValue(); float sal = 0; EmpSalary empSalary =
	 * empSalaryRepo.getrecordByEmpIdAndDate(fromDate, empId); bugetedAmtAndRevenue
	 * = bugetedAmtAndRevenueRepo.calculateBugetedAmtAndBugetedRevenue(empId,
	 * fromDate, toDate, clntIds);
	 * 
	 * if (month == 1) { sal = (empSalary.getJan() + 6000) / bugetedCap; } else if
	 * (month == 2) { sal = (empSalary.getFeb() + 6000) / bugetedCap; } else if
	 * (month == 3) { sal = (empSalary.getMar() + 6000) / bugetedCap; } else if
	 * (month == 4) { sal = (empSalary.getApr() + 6000) / bugetedCap; } else if
	 * (month == 5) { sal = (empSalary.getMay() + 6000) / bugetedCap; } else if
	 * (month == 6) { sal = (empSalary.getJun() + 6000) / bugetedCap; } else if
	 * (month == 7) { sal = (empSalary.getJul() + 6000) / bugetedCap; } else if
	 * (month == 8) { sal = (empSalary.getAug() + 6000) / bugetedCap; } else if
	 * (month == 9) { sal = (empSalary.getSep() + 6000) / bugetedCap; } else if
	 * (month == 10) { sal = (empSalary.getOct() + 6000) / bugetedCap; } else if
	 * (month == 11) { sal = (empSalary.getNov() + 6000) / bugetedCap; } else if
	 * (month == 12) { sal = (empSalary.getDece() + 6000) / bugetedCap; }
	 * 
	 * int bugetedHrs = (int) (bugetedAmtAndRevenue.getBugetedHrs() / 60); int
	 * remHrs = (int) (bugetedAmtAndRevenue.getBugetedHrs() % 60); float remHrsValue
	 * = (sal / 60) * remHrs;
	 * 
	 * int actualHrs = (int) (bugetedAmtAndRevenue.getActualHrs() / 60); int
	 * actualRemHrs = (int) (bugetedAmtAndRevenue.getActualHrs() % 60); float
	 * remActuslHrsValue = (sal / 60) * actualRemHrs;
	 * 
	 * bugetedAmtAndRevenue.setBugetedCost((bugetedHrs * sal) + remHrsValue);
	 * bugetedAmtAndRevenue.setActualCost((actualHrs * sal) + remActuslHrsValue); }
	 * catch (Exception e) {
	 * 
	 * e.printStackTrace(); bugetedAmtAndRevenue = new BugetedAmtAndRevenue(); }
	 * 
	 * return bugetedAmtAndRevenue;
	 * 
	 * }
	 */

	@RequestMapping(value = { "/calculateBugetedAmtAndBugetedRevenue" }, method = RequestMethod.POST)
	public @ResponseBody BugetedAmtAndRevenue calculateBugetedAmtAndBugetedRevenue(
			@RequestParam("empId") List<Integer> empIds, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("typeId") int typeId,
			@RequestParam("groupId") int groupId, @RequestParam("clientId") int clientId) {

		BugetedAmtAndRevenue bugetedAmtAndRevenue1 = new BugetedAmtAndRevenue();

		try {

			List<Integer> clntIds = new ArrayList<>();
			if (groupId == 0 && clientId == 0) {
//All group option Sachin 8-04-2020 get cust id of all cust who belongs to group 
				clntIds = bugetedAmtAndRevenueRepo.getclientForAllGroup();
				
			} else if (clientId == 0) {
				clntIds = bugetedAmtAndRevenueRepo.getclientByGroupId(groupId);
			} else {
				clntIds.add(clientId);
			}

			LeaveCount totalDayCount = calculateHolidayBetweenDate(0, fromDate, toDate);
			List<EmployeeListWithAvailableHours> list = employeeListWithAvailableHoursRepo
					.getLeaveRecordByEmpId(fromDate, toDate, empIds);
			List<EmpSalary> empSalaryList = empSalaryRepo.getrecordByEmpIdAndDate(fromDate, empIds);

			List<BugetedAmtAndRevenue> bugetedAmtAndRevenueList = bugetedAmtAndRevenueRepo
					.calculateBugetedAmtAndBugetedRevenueSumofRev(empIds, fromDate, toDate, clntIds);

			for (int a = 0; a < empIds.size(); a++) {

				int empId = empIds.get(a);

				float freeHours = totalDayCount.getLeavecount() * 7;

				SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");

				float bsyhrs = 0;

				for (int i = 0; i < list.size(); i++) {

					if (empId == list.get(i).getEmpId()) {

						String lvfmdt = yy.format(list.get(i).getLeaveFromdt());
						String lvtodt = yy.format(list.get(i).getLeaveTodt());

						if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) <= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvfmdt)) >= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) < 0) {

							if (list.get(i).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}

						} else if (yy.parse(fromDate).compareTo(yy.parse(lvtodt)) <= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) >= 0
								&& yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) > 0) {

							if (list.get(i).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate,
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate,
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}

						} else if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) <= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) >= 0) {

							if (list.get(i).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}

						} else if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) >= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) <= 0) {

							if (list.get(i).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate, toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate, toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}
						}
					}
				}

				float bugetedCap = freeHours - bsyhrs;

				Date date = yy.parse(fromDate);
				LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				int month = localDate.getMonthValue();
				float sal = 0;

				BugetedAmtAndRevenue bugetedAmtAndRevenue = new BugetedAmtAndRevenue();
				EmpSalary empSalary = new EmpSalary();

				for (int i = 0; i < bugetedAmtAndRevenueList.size(); i++) {

					if (empId == bugetedAmtAndRevenueList.get(i).getEmpId()) {
						bugetedAmtAndRevenue = bugetedAmtAndRevenueList.get(i);
						bugetedAmtAndRevenue1.setBugetedRev(bugetedAmtAndRevenue.getBugetedRev());
						bugetedAmtAndRevenue1.setActulRev(bugetedAmtAndRevenue.getActulRev());
						break;
					}

				}

				for (int i = 0; i < empSalaryList.size(); i++) {

					if (empId == empSalaryList.get(i).getEmpId()) {
						empSalary = empSalaryList.get(i);
						break;
					}

				}

				try {

					if (month == 1) {
						sal = (empSalary.getJan() + 6000) / bugetedCap;
					} else if (month == 2) {
						sal = (empSalary.getFeb() + 6000) / bugetedCap;
					} else if (month == 3) {
						sal = (empSalary.getMar() + 6000) / bugetedCap;
					} else if (month == 4) {
						sal = (empSalary.getApr() + 6000) / bugetedCap;
					} else if (month == 5) {
						sal = (empSalary.getMay() + 6000) / bugetedCap;
					} else if (month == 6) {
						sal = (empSalary.getJun() + 6000) / bugetedCap;
					} else if (month == 7) {
						sal = (empSalary.getJul() + 6000) / bugetedCap;
					} else if (month == 8) {
						sal = (empSalary.getAug() + 6000) / bugetedCap;
					} else if (month == 9) {
						sal = (empSalary.getSep() + 6000) / bugetedCap;
					} else if (month == 10) {
						sal = (empSalary.getOct() + 6000) / bugetedCap;
					} else if (month == 11) {
						sal = (empSalary.getNov() + 6000) / bugetedCap;
					} else if (month == 12) {
						sal = (empSalary.getDece() + 6000) / bugetedCap;
					}
				} catch (Exception e) {
					sal = 0;
				}

				if (bugetedCap == 0) {
					sal = 0;
				}
				int bugetedHrs = (int) (bugetedAmtAndRevenue.getBugetedHrs() / 60);
				int remHrs = (int) (bugetedAmtAndRevenue.getBugetedHrs() % 60);
				float remHrsValue = (sal / 60) * remHrs;

				int actualHrs = (int) (bugetedAmtAndRevenue.getActualHrs() / 60);
				int actualRemHrs = (int) (bugetedAmtAndRevenue.getActualHrs() % 60);
				float remActuslHrsValue = (sal / 60) * actualRemHrs;

				bugetedAmtAndRevenue.setBugetedCost((bugetedHrs * sal) + remHrsValue);
				bugetedAmtAndRevenue.setActualCost((actualHrs * sal) + remActuslHrsValue);

				bugetedAmtAndRevenue1
						.setBugetedCost(bugetedAmtAndRevenue1.getBugetedCost() + bugetedAmtAndRevenue.getBugetedCost());

				bugetedAmtAndRevenue1
						.setActualCost(bugetedAmtAndRevenue1.getActualCost() + bugetedAmtAndRevenue.getActualCost());

				bugetedAmtAndRevenue1
						.setActualHrs(bugetedAmtAndRevenue1.getActualHrs() + bugetedAmtAndRevenue.getActualHrs());
				bugetedAmtAndRevenue1
						.setBugetedHrs(bugetedAmtAndRevenue1.getBugetedHrs() + bugetedAmtAndRevenue.getBugetedHrs());

			}

		} catch (Exception e) {

			e.printStackTrace();
			bugetedAmtAndRevenue1 = new BugetedAmtAndRevenue();
		}

		return bugetedAmtAndRevenue1;

	}

	@RequestMapping(value = { "/getCapcityDetailForPartnerDashboard" }, method = RequestMethod.POST)
	public @ResponseBody List<ManagerListWithEmpIds> getCapcityDetailForPartnerDashboard(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("empId") int empId) {

		List<ManagerListWithEmpIds> managerListWithEmpIds = new ArrayList<>();

		try {

			LeaveCount totalDayCount = calculateHolidayBetweenDate(0, fromDate, toDate);
			float freeHours = totalDayCount.getLeavecount() * 7;

			// System.out.println("freeHours " + freeHours);
			SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
			managerListWithEmpIds = managerListWithEmpIdsRepo.managerListWithEmpIds(fromDate, toDate, empId);

			for (int i = 0; i < managerListWithEmpIds.size(); i++) {

				if (!managerListWithEmpIds.get(i).getMemberIds().equals("0")) {

					LinkedHashSet<String> hashSet = new LinkedHashSet<>(
							Arrays.asList(managerListWithEmpIds.get(i).getMemberIds().split(",")));
					ArrayList<String> ids = new ArrayList<>(hashSet);

					List<EmployeeHolidayListForDashbord> list = employeeHolidayListForDashbordRepo
							.getLeaveRecordForManagerDashboard(fromDate, toDate, ids);

					managerListWithEmpIds.get(i).setIds(ids);

					BugetedMinAndWorkedMinByEmpIds bugetedMinAndWorkedMinByEmpIds = bugetedMinAndWorkedMinByEmpIdsRepo
							.bugetedMinAndWorkedMinByEmpIds(fromDate, toDate, ids);
					int allocatedHrs = (int) (bugetedMinAndWorkedMinByEmpIds.getAllWork() / 60);
					int allocateHrs = (int) (bugetedMinAndWorkedMinByEmpIds.getAllWork() % 60);

					if (String.valueOf(allocateHrs).length() == 1) {
						String concateAllocated = allocatedHrs + "." + 0 + allocateHrs;
						managerListWithEmpIds.get(i).setAllWork(concateAllocated);
					} else {
						String concateAllocated = allocatedHrs + "." + allocateHrs;
						managerListWithEmpIds.get(i).setAllWork(concateAllocated);
					}

					int actualdHrs = (int) (bugetedMinAndWorkedMinByEmpIds.getActWork() / 60);
					int actualHrs = (int) (bugetedMinAndWorkedMinByEmpIds.getActWork() % 60);

					if (String.valueOf(actualHrs).length() == 1) {
						String concateActual = actualdHrs + "." + 0 + actualHrs;
						managerListWithEmpIds.get(i).setActlWork(concateActual);
					} else {
						String concateActual = actualdHrs + "." + actualHrs;
						managerListWithEmpIds.get(i).setActlWork(concateActual);
					}

					float bsyhrs = 0;

					for (int k = 0; k < list.size(); k++) {

						String lvfmdt = yy.format(list.get(k).getLeaveFromdt());
						String lvtodt = yy.format(list.get(k).getLeaveTodt());

						if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) <= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvfmdt)) >= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) < 0) {

							if (list.get(k).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}

						} else if (yy.parse(fromDate).compareTo(yy.parse(lvtodt)) <= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) >= 0
								&& yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) > 0) {

							if (list.get(k).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate,
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate,
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}

						} else if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) <= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) >= 0) {

							if (list.get(k).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, yy.format(yy.parse(lvfmdt)),
										yy.format(yy.parse(lvtodt)));
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}

						} else if (yy.parse(fromDate).compareTo(yy.parse(lvfmdt)) >= 0
								&& yy.parse(toDate).compareTo(yy.parse(lvtodt)) <= 0) {

							if (list.get(k).getLeaveDuration() == 0) {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate, toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 3.5));

							} else {

								LeaveCount bsyDaycount = calculateHolidayBetweenDate(0, fromDate, toDate);
								bsyhrs = (float) (bsyhrs + (bsyDaycount.getLeavecount() * 7));
							}
						}

					}
					float bugetedCap = (ids.size() * freeHours) - bsyhrs;
					managerListWithEmpIds.get(i).setBugetedWork(bugetedCap);
				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return managerListWithEmpIds;

	}

}
