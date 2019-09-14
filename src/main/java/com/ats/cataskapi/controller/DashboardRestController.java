package com.ats.cataskapi.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.model.BugetedAmtAndRevenue;
import com.ats.cataskapi.model.DailyWorkLog;
import com.ats.cataskapi.model.EmpListForDashboard;
import com.ats.cataskapi.model.EmpListForDashboardByStatus;
import com.ats.cataskapi.model.TaskCountByStatus;
import com.ats.cataskapi.repositories.BugetedAmtAndRevenueRepo;
import com.ats.cataskapi.repositories.CapacityDetailByEmpRepo;
import com.ats.cataskapi.repositories.EmpListForDashboardByStatusRepo;
import com.ats.cataskapi.repositories.EmpListForDashboardRepo;
import com.ats.cataskapi.repositories.TaskCountByStatusRepo;
import com.ats.cataskapi.task.model.EmpSalary;
import com.ats.cataskapi.task.repo.EmpSalaryRepo;

@RestController
public class DashboardRestController {

	@Autowired
	TaskCountByStatusRepo taskCountByStatusRepo;

	@Autowired
	CapacityDetailByEmpRepo capacityDetailByEmpRepo;

	@Autowired
	EmpListForDashboardRepo empListForDashboardRepo;

	@Autowired
	EmpListForDashboardByStatusRepo empListForDashboardByStatusRepo;

	@Autowired
	BugetedAmtAndRevenueRepo bugetedAmtAndRevenueRepo;

	@Autowired
	EmpSalaryRepo empSalaryRepo;

	@RequestMapping(value = { "/getTaskCountByStatus" }, method = RequestMethod.POST)
	public @ResponseBody List<TaskCountByStatus> getTaskCountByStatus(@RequestParam("empId") int empId,
			@RequestParam("userId") int userId) {

		List<TaskCountByStatus> list = new ArrayList<TaskCountByStatus>();

		try {

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

			list = taskCountByStatusRepo.getTaskCountByStatus(sf.format(date), empId, userId);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;

	}

	@RequestMapping(value = { "/getTaskMemberIds" }, method = RequestMethod.POST)
	public @ResponseBody List<EmpListForDashboard> getTaskMemberIds(@RequestParam("empId") int empId) {

		List<EmpListForDashboard> empList = new ArrayList<>();

		try {

			String empIds = capacityDetailByEmpRepo.getEmployeeList(empId);
			String[] ids = empIds.split(",");
			empList = empListForDashboardRepo.getempList(ids);

		} catch (Exception e) {
			empList = new ArrayList<>();
			e.printStackTrace();
		}

		return empList;

	}

	@RequestMapping(value = { "/getTaskCountByGroupBymanager" }, method = RequestMethod.POST)
	public @ResponseBody List<EmpListForDashboard> getTaskCountByGroupBymanager(@RequestParam("userId") int userId) {

		List<EmpListForDashboard> empList = new ArrayList<>();

		try {

			empList = empListForDashboardRepo.getManagerEmpList();

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

			for (int i = 0; i < empList.size(); i++) {

				List<TaskCountByStatus> list = new ArrayList<TaskCountByStatus>();
				list = taskCountByStatusRepo.getTaskCountByStatus(sf.format(date), empList.get(i).getEmpId(), userId);
				empList.get(i).setList(list);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return empList;

	}

	@RequestMapping(value = { "/getTaskCountByManagerId" }, method = RequestMethod.POST)
	public @ResponseBody List<EmpListForDashboardByStatus> getTaskCountByManagerId(@RequestParam("userId") int userId,
			@RequestParam("empId") int empId, @RequestParam("status") int status) {

		List<EmpListForDashboardByStatus> empList = new ArrayList<>();

		try {

			String empIds = capacityDetailByEmpRepo.getEmployeeListByManagerIdAndUserId(empId, userId);
			String[] ids = empIds.split(",");

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			empList = empListForDashboardByStatusRepo.getTaskCountByStatus(sf.format(date), ids, status, userId);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return empList;

	}

	@RequestMapping(value = { "/calculateBugetedAmtAndBugetedRevenue" }, method = RequestMethod.POST)
	public @ResponseBody BugetedAmtAndRevenue calculateBugetedAmtAndBugetedRevenue(@RequestParam("empId") int empId,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		BugetedAmtAndRevenue bugetedAmtAndRevenue = new BugetedAmtAndRevenue();

		try {

			SimpleDateFormat yydate = new SimpleDateFormat("yyyy-MM-dd");
			Date date = yydate.parse(fromDate);
			LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int month = localDate.getMonthValue();
			float sal = 0;
			EmpSalary empSalary = empSalaryRepo.getrecordByEmpIdAndDate(fromDate, empId);
			bugetedAmtAndRevenue = bugetedAmtAndRevenueRepo.calculateBugetedAmtAndBugetedRevenue(empId, fromDate,
					toDate);

			if (month == 1) {
				sal=empSalary.getJan();
			} else if (month == 2) {
				sal=empSalary.getFeb();
			} else if (month == 3) {
				sal=empSalary.getMar();
			} else if (month == 4) {
				sal=empSalary.getApr();
			} else if (month == 5) {
				sal=empSalary.getMay();
			} else if (month == 6) {
				sal=empSalary.getJun();
			} else if (month == 7) {
				sal=empSalary.getJul();
			} else if (month == 8) {
				sal=empSalary.getAug();
			} else if (month == 9) {
				sal=empSalary.getSep();
			} else if (month == 10) {
				sal=empSalary.getOct();
			} else if (month == 11) {
				sal=empSalary.getNov();
			} else if (month == 12) {
				sal=empSalary.getDece();
			}
			
			int bugetedHrs = (int) (bugetedAmtAndRevenue.getBugetedHrs() / 60);
			int remHrs = (int) (bugetedAmtAndRevenue.getBugetedHrs() % 60); 
			float remHrsValue = (sal/60)*remHrs;
			
			//float bugetedTotalhrs = Float.parseFloat(String.valueOf(bugetedHrs).concat(".").concat(String.valueOf(remHrs)));
			
			int actualHrs = (int) (bugetedAmtAndRevenue.getActualHrs() / 60);
			int actualRemHrs = (int) (bugetedAmtAndRevenue.getActualHrs() % 60); 
			float remActuslHrsValue = (sal/60)*actualRemHrs;
			
			//float actualTotalhrs = Float.parseFloat(String.valueOf(actualHrs).concat(".").concat(String.valueOf(actualRemHrs)));
			
			bugetedAmtAndRevenue.setBugetedCost((bugetedHrs*sal)+remHrsValue);
			bugetedAmtAndRevenue.setActualCost((actualHrs*sal)+remActuslHrsValue);
		} catch (Exception e) {

			e.printStackTrace();
			bugetedAmtAndRevenue = new BugetedAmtAndRevenue();
		}

		return bugetedAmtAndRevenue;

	}

}
