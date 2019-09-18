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

import com.ats.cataskapi.model.report.CompletedTaskReport;
import com.ats.cataskapi.model.report.EmpAndMngPerformanceRep;
import com.ats.cataskapi.model.report.InactiveTaskReport;
import com.ats.cataskapi.report.repo.CompletedTaskReportRepo;
import com.ats.cataskapi.report.repo.EmpAndMngPerformanceRepRepo;
import com.ats.cataskapi.report.repo.InactiveTaskReportRepo;
import com.ats.cataskapi.repositories.CapacityDetailByEmpRepo;

@RestController
public class ReportApiController {

	@Autowired
	CompletedTaskReportRepo completedTaskReportRepo;

	@RequestMapping(value = { "/getCompletedTaskReport" }, method = RequestMethod.POST)
	public @ResponseBody List<CompletedTaskReport> getCompletedTaskReport(@RequestParam String fromDate,
			@RequestParam String toDate,@RequestParam String empIds) {
		List<CompletedTaskReport> logList = new ArrayList<CompletedTaskReport>();
		String fromDate1=fromDate.concat("  00:00:01");
		String toDate1=	toDate.concat("  23:59:59");
	
		
		//System.out.println("dates"+fromDate+toDate);

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
			@RequestParam String toDate1,@RequestParam String empIds,@RequestParam int  status) {
		List<InactiveTaskReport> logList = new ArrayList<InactiveTaskReport>();
	 
		try {
			logList = inactiveTaskReportRepo.getAllInactiveTask(fromDate1, toDate1, empIds,status);
			System.err.println("dates"+logList.toString());

		} catch (Exception e) {
			System.out.println("Excep in getAllDailyWorkLogs : " + e.getMessage());
		}
		return logList;

	}
	
	@Autowired
	EmpAndMngPerformanceRepRepo empAndMngPerformanceRepRepo;
	
	@Autowired
	CapacityDetailByEmpRepo capacityDetailByEmpRepo;
	
	
	@RequestMapping(value = { "/getEmpAndMngPerformanceReport" }, method = RequestMethod.POST)
	public @ResponseBody List<EmpAndMngPerformanceRep> getEmpAndMngPerformanceReport(@RequestParam String fromDate,
			@RequestParam String toDate,@RequestParam int  status,@RequestParam int  empId) {
		List<EmpAndMngPerformanceRep> logList = new ArrayList<EmpAndMngPerformanceRep>();
	 
		try {
			 
			String empIds = capacityDetailByEmpRepo.getEmployeeList(empId);
			LinkedHashSet<String> hashSet = new LinkedHashSet<>(Arrays.asList(empIds.split(",")));
			ArrayList<String> arryids = new ArrayList<>(hashSet);
			//System.out.println("empIds are***"+arryids);
			String fromDate1=fromDate.concat("  00:00:01");
			String toDate1=	toDate.concat("  23:59:59");
			int userId=empId;
		
			logList = empAndMngPerformanceRepRepo.getAllTask(fromDate1, toDate1,fromDate, toDate,status,arryids,userId);
			///System.err.println("dates"+logList.toString());

		} catch (Exception e) {
			System.out.println("Excep in getEmpAndMngPerformanceReport : " + e.getMessage());
		}
		return logList;

	}
	
	
	
	

}
