package com.ats.cataskapi.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.model.CapacityDetailByEmp;
import com.ats.cataskapi.model.EmployeeMaster;
import com.ats.cataskapi.model.report.CompletedTaskReport;
import com.ats.cataskapi.model.report.EmpAndMangPerfRepDetail;
import com.ats.cataskapi.model.report.EmpAndMngPerformanceRep;
import com.ats.cataskapi.model.report.InactiveTaskReport;
import com.ats.cataskapi.report.repo.CompletedTaskReportRepo;
import com.ats.cataskapi.report.repo.EmpAndMangPerfRepDetailRepo;
import com.ats.cataskapi.report.repo.EmpAndMngPerformanceRepRepo;
import com.ats.cataskapi.report.repo.InactiveTaskReportRepo;
import com.ats.cataskapi.repositories.CapacityDetailByEmpRepo;
import com.ats.cataskapi.repositories.EmployeeMasterRepo;
import com.ats.cataskapi.service.CommonFunctionService;

@RestController
public class ReportApiController {

	@Autowired
	CompletedTaskReportRepo completedTaskReportRepo;

	@RequestMapping(value = { "/getCompletedTaskReport" }, method = RequestMethod.POST)
	public @ResponseBody List<CompletedTaskReport> getCompletedTaskReport(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String empIds) {
		List<CompletedTaskReport> logList = new ArrayList<CompletedTaskReport>();
		String fromDate1 = fromDate;
		String toDate1 = toDate;

		// System.out.println("dates"+fromDate+toDate);

		try {
			logList = completedTaskReportRepo.getAllCompletedTask(fromDate1, toDate1, empIds);

		} catch (Exception e) {
			System.out.println("Excep in getAllDailyWorkLogs : " + e.getMessage());
		}
		return logList;

	}

	@Autowired
	InactiveTaskReportRepo inactiveTaskReportRepo;

	@RequestMapping(value = { "/getInactiveTaskReport" }, method = RequestMethod.POST)
	public @ResponseBody List<InactiveTaskReport> getInactiveTaskReport(@RequestParam String fromDate1,
			@RequestParam String toDate1, @RequestParam String empIds, @RequestParam int status) {
		List<InactiveTaskReport> logList = new ArrayList<InactiveTaskReport>();

		try {
			
			if(status==9) {
				logList = inactiveTaskReportRepo.getAllCompleteTask(fromDate1, toDate1, empIds, status);
			}else {
				logList = inactiveTaskReportRepo.getAllInactiveTask(fromDate1, toDate1, empIds, status);
			}
		
			
			System.err.println("dates" + logList.toString());

		} catch (Exception e) {
			System.out.println("Excep in getAllDailyWorkLogs : " + e.getMessage());
		}
		return logList;

	}

	@Autowired
	EmpAndMngPerformanceRepRepo empAndMngPerformanceRepRepo;

	@Autowired
	CapacityDetailByEmpRepo capacityDetailByEmpRepo;

	@Autowired
	CommonFunctionService commonFunctionService;

	@RequestMapping(value = { "/getEmpAndMngPerformanceReportHead" }, method = RequestMethod.POST)
	public @ResponseBody List<EmpAndMngPerformanceRep> getEmpAndMngPerformanceReportHead(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam List<String> empIdList) {
		List<EmpAndMngPerformanceRep> logList = new ArrayList<EmpAndMngPerformanceRep>();
		List<Integer> empIdsList = new ArrayList<Integer>();
		List<CapacityDetailByEmp> caplogList = new ArrayList<CapacityDetailByEmp>();
		// System.out.println("a" + empIdList.toString());
		try {

			logList = empAndMngPerformanceRepRepo.getAllTask(fromDate, toDate, empIdList);
			// System.err.println("empAndMngPerformanceRepRepo "+logList.toString());
			for (int i = 0; i < logList.size(); i++) {
				empIdsList.add(logList.get(i).getEmpId());

			}

			caplogList = commonFunctionService.CalculateActualAvailableHrs(empIdsList, fromDate, toDate);
			for (int i = 0; i < logList.size(); i++) {

				for (int j = 0; j < caplogList.size(); j++) {

					if (logList.get(i).getEmpId() == caplogList.get(j).getEmpId()) {
						logList.get(i).setBudgetedCap(String.valueOf(caplogList.get(j).getBugetedCap()));

						break;
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Excep in getEmpAndMngPerformanceReport : " + e.getMessage());
		}
		return logList;

	}

	@Autowired
	EmpAndMangPerfRepDetailRepo empAndMangPerfRepDetailRepo;

	@RequestMapping(value = { "/getEmpAndMngPerformanceReportDetail" }, method = RequestMethod.POST)
	public @ResponseBody List<EmpAndMangPerfRepDetail> getEmpAndMngPerformanceReportDetail(
			@RequestParam String fromDate, @RequestParam String toDate, @RequestParam int status,
			@RequestParam int empId) {
		List<EmpAndMangPerfRepDetail> logList = new ArrayList<EmpAndMangPerfRepDetail>();

		try {

			logList = empAndMangPerfRepDetailRepo.getAllTaskDetail(fromDate, toDate, status, empId);
			/// System.err.println("dates"+logList.toString());

		} catch (Exception e) {
			System.out.println("Excep in getEmpAndMngPerformanceReport : " + e.getMessage());
		}
		return logList;

	}

	@Autowired
	EmployeeMasterRepo empRepo;

	@RequestMapping(value = { "/getAllEmployeesByIds" }, method = RequestMethod.POST)
	public @ResponseBody List<EmployeeMaster> getEmployees(@RequestParam int empId) {

		List<EmployeeMaster> empList = new ArrayList<EmployeeMaster>();
		System.out.println("b");

		try {
			String empIds = capacityDetailByEmpRepo.getEmployeeList(empId);

			LinkedHashSet<String> hashSet = new LinkedHashSet<>(Arrays.asList(empIds.split(",")));
			ArrayList<String> ids = new ArrayList<>(hashSet);
			empList = empRepo.findAllByEmpIds(ids); // Fetched all employees
		} catch (Exception e) {
			System.err.println("Exce in getEmployees  " + e.getMessage());
		}

		return empList;

	}

}
