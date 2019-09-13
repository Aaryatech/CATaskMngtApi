package com.ats.cataskapi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.model.DailyWorkLog;
import com.ats.cataskapi.model.EmpListForDashboard;
import com.ats.cataskapi.model.EmpListForDashboardByStatus;
import com.ats.cataskapi.model.TaskCountByStatus;
import com.ats.cataskapi.repositories.CapacityDetailByEmpRepo;
import com.ats.cataskapi.repositories.EmpListForDashboardByStatusRepo;
import com.ats.cataskapi.repositories.EmpListForDashboardRepo;
import com.ats.cataskapi.repositories.TaskCountByStatusRepo;

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

	@RequestMapping(value = { "/getTaskCountByStatus" }, method = RequestMethod.POST)
	public @ResponseBody List<TaskCountByStatus> getTaskCountByStatus(@RequestParam("empId") int empId,
			@RequestParam("userId") int userId) {

		List<TaskCountByStatus> list = new ArrayList<TaskCountByStatus>();

		try {

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

			list = taskCountByStatusRepo.getTaskCountByStatus(sf.format(date), empId,userId);

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

			for(int i=0 ; i<empList.size() ; i++) {
				
				List<TaskCountByStatus> list = new ArrayList<TaskCountByStatus>();
				list = taskCountByStatusRepo.getTaskCountByStatus(sf.format(date), empList.get(i).getEmpId(),userId);
				empList.get(i).setList(list);
			}
			
			

		} catch (Exception e) {

			e.printStackTrace();
		}

		return empList;

	}
	
	@RequestMapping(value = { "/getTaskCountByManagerId" }, method = RequestMethod.POST)
	public @ResponseBody List<EmpListForDashboardByStatus> getTaskCountByManagerId(@RequestParam("userId") int userId,
			@RequestParam("empId") int empId,@RequestParam("status") int status) {

		List<EmpListForDashboardByStatus> empList = new ArrayList<>();
		

		try {

			String empIds = capacityDetailByEmpRepo.getEmployeeListByManagerIdAndUserId(empId,userId);
			String[] ids = empIds.split(",");
			
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd"); 
			empList = empListForDashboardByStatusRepo.getTaskCountByStatus(sf.format(date),ids,status,userId);
			

		} catch (Exception e) {

			e.printStackTrace();
		}

		return empList;

	}

}
