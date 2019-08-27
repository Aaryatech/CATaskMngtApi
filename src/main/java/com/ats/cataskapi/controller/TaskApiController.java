package com.ats.cataskapi.controller;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.common.DateConvertor;
import com.ats.cataskapi.common.DateValues;
import com.ats.cataskapi.common.PeriodicityDates;
import com.ats.cataskapi.communication.Repo.CommunicationRepo;
import com.ats.cataskapi.communication.model.Communication;
import com.ats.cataskapi.model.ActivityMaster;
import com.ats.cataskapi.model.CustmrActivityMap;
import com.ats.cataskapi.model.DevPeriodicityMaster;
import com.ats.cataskapi.model.EmployeeMaster;
import com.ats.cataskapi.model.FinancialYear;
import com.ats.cataskapi.model.Info;
import com.ats.cataskapi.model.ServiceMaster;
import com.ats.cataskapi.repositories.ActivityMasterRepo;
import com.ats.cataskapi.repositories.CustmrActivityMapRepo;
import com.ats.cataskapi.repositories.DevPeriodicityMasterRepo;
import com.ats.cataskapi.repositories.EmployeeMasterRepo;
import com.ats.cataskapi.repositories.FinancialYearRepo;
import com.ats.cataskapi.repositories.ServiceMasterRepo;
import com.ats.cataskapi.task.model.GetTaskList;
import com.ats.cataskapi.task.model.Task;
import com.ats.cataskapi.task.repo.GetTaskListRepo;
import com.ats.cataskapi.task.repo.TaskRepo;
  
import ch.qos.logback.classic.pattern.DateConverter;

@RestController
public class TaskApiController {

     //Harsha Date:19 Aug 2019
	@Autowired
	TaskRepo taskRepo;

	@Autowired
	CustmrActivityMapRepo actMapRepo;
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


	/*
	 * @RequestMapping(value = { "/saveTask" }, method = RequestMethod.POST)
	 * public @ResponseBody List<DateValues> saveTask(@RequestParam String
	 * strDate1, @RequestParam String endDate1,
	 * 
	 * @RequestParam int perId) {
	 * 
	 * List<DateValues> listDate = PeriodicityDates.getDates(strDate1, endDate1,
	 * perId); int totdays = listDate.size(); Date date = new Date();
	 * System.out.println("after fun call**" + listDate.toString());
	 * 
	 * return listDate; }
	 */
	
	
	@Autowired
	ActivityMasterRepo actvtMstrRepo;
	@Autowired
	DevPeriodicityMasterRepo devPeriodRepo;
	@Autowired
	FinancialYearRepo financialYearRepo;

	@Autowired
	ServiceMasterRepo srvMstrRepo;

	

	@Autowired
	CommunicationRepo communicationRepo;
	
	@RequestMapping(value = { "/saveTask1" }, method = RequestMethod.POST)
	public @ResponseBody CustmrActivityMap saveCustSignatory(@RequestBody CustmrActivityMap custserv) {

		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		int perId = 0;
		Task serv = null;
		int totdays = 0;

		CustmrActivityMap cust = null;

		ActivityMaster actv = new ActivityMaster();

		actv = actvtMstrRepo.findByActiIdAndDelStatus(custserv.getActvId(), 1);

		DevPeriodicityMaster period = new DevPeriodicityMaster();
		period = devPeriodRepo.findByPeriodicityIdAndDelStatus(custserv.getPeriodicityId(), 1);

		try {
			cust = actMapRepo.saveAndFlush(custserv);
			perId = custserv.getPeriodicityId();

			String strDate = dateFormat.format(custserv.getActvStartDate());
			//System.out.println("Converted String str: " + strDate);
			String endDate = dateFormat.format(custserv.getActvEndDate());
			//System.out.println("Converted String end: " + endDate);
			//System.out.println("perId: " + perId);
			List<DateValues> listDate = PeriodicityDates.getDates(strDate, endDate, perId);
			totdays = listDate.size();
			

			ServiceMaster servc = new ServiceMaster();

			servc = srvMstrRepo.findByServIdAndDelStatus(actv.getServId(), 1);

			for (int i = 0; i < listDate.size(); i++) {

				Task task = new Task();

				Date date1 = listDate.get(i).getDate();
				 
				//System.out.println("date bef stat**" +dateFormat.format(date1));
				task.setTaskStatutoryDueDate(PeriodicityDates.addDaysToGivenDate(dateFormat.format(date1), custserv.getActvStatutoryDays()));

				//System.out.println("stat date       **" + task.getTaskStatutoryDueDate());
				FinancialYear fin = new FinancialYear();
				fin = financialYearRepo.getFinYearBetDate(String.valueOf(task.getTaskStatutoryDueDate()));
		

				task.setTaskStartDate(PeriodicityDates.addDaysToGivenDate(task.getTaskStatutoryDueDate(), -30));

				StringBuilder sb1 = new StringBuilder(servc.getServName());

				sb1.append("-").append(actv.getActiName()).append("-").append(listDate.get(i).getValue());
				//System.out.println("Fin task name" + sb1);

				task.setActvId(custserv.getActvId());
				task.setCustId(custserv.getCustId());
				task.setDelStatus(1);
				task.setEmpBudHr(custserv.getActvEmpBudgHr());
				task.setExInt1(1);
				task.setExInt2(1);
				task.setExVar1("NA");
				task.setExVar2("NA");
				task.setMappingId(custserv.getMappingId());
				task.setPeriodicityId(custserv.getPeriodicityId());
				task.setIsActive(1);

				task.setMngrBudHr(custserv.getActvManBudgHr());
				task.setServId(actv.getServId());
				task.setTaskCode("NA");
				task.setTaskEmpIds("0");
				task.setTaskFyId(fin.getFinYearId());
				task.setTaskEndDate(dateFormat.format(date));
				task.setTaskStatus(0);
				task.setTaskSubline("NA");
				task.setTaskText(String.valueOf(sb1));
				task.setUpdateDatetime(dateFormat1.format(date));
				task.setUpdateUsername(custserv.getUpdateUsername());

				serv = taskRepo.saveAndFlush(task);
				if(serv!=null) {
					Communication comcat=new Communication();
					comcat.setCommunText("Task Created");
					comcat.setDelStatus(1);
					comcat.setEmpId(custserv.getUpdateUsername());
					comcat.setExInt1(1);
					comcat.setExInt2(1);
					comcat.setExVar1("NA");
					comcat.setExVar2("NA");
					comcat.setTypeId(2);
					comcat.setRemark("NA");
					comcat.setTaskId(serv.getTaskId());
					comcat.setUpdateDatetime(dateFormat1.format(date));
					comcat.setUpdateUser(custserv.getUpdateUsername());
					Communication save = communicationRepo.saveAndFlush(comcat);
					 
				}
			

			}

		} catch (Exception e) {

			System.err.println("Exce in saving saveTask " + e.getMessage());
			e.printStackTrace();

		}
		return cust;
	}
	

	@RequestMapping(value = { "/saveMannualTask" }, method = RequestMethod.POST)
	public @ResponseBody Info saveMannualTask(@RequestBody CustmrActivityMap custserv) {

		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		int perId = 0;
		Task serv = null;
		int totdays = 0;

		Info inf =new Info();
		CustmrActivityMap cust = null;

		ActivityMaster actv = new ActivityMaster();

		actv = actvtMstrRepo.findByActiIdAndDelStatus(custserv.getActvId(), 1);

		DevPeriodicityMaster period = new DevPeriodicityMaster();
		period = devPeriodRepo.findByPeriodicityIdAndDelStatus(custserv.getPeriodicityId(), 1);

		try {
			 
			perId = custserv.getPeriodicityId();

			String strDate = dateFormat.format(custserv.getActvStartDate());
			//System.out.println("Converted String str: " + strDate);
			String endDate = dateFormat.format(custserv.getActvEndDate());
			//System.out.println("Converted String end: " + endDate);
			//System.out.println("perId: " + perId);
			List<DateValues> listDate = PeriodicityDates.getDates(strDate, endDate, perId);
			totdays = listDate.size();
			

			ServiceMaster servc = new ServiceMaster();

			servc = srvMstrRepo.findByServIdAndDelStatus(actv.getServId(), 1);

			for (int i = 0; i < listDate.size(); i++) {

				Task task = new Task();

				Date date1 = listDate.get(i).getDate();
				 
				//System.out.println("date bef stat**" +dateFormat.format(date1));
				task.setTaskStatutoryDueDate(PeriodicityDates.addDaysToGivenDate(dateFormat.format(date1), custserv.getActvStatutoryDays()));

				//System.out.println("stat date       **" + task.getTaskStatutoryDueDate());
				FinancialYear fin = new FinancialYear();
				fin = financialYearRepo.getFinYearBetDate(String.valueOf(task.getTaskStatutoryDueDate()));
		

				task.setTaskStartDate(PeriodicityDates.addDaysToGivenDate(task.getTaskStatutoryDueDate(), -30));

				StringBuilder sb1 = new StringBuilder(servc.getServName());

				sb1.append("-").append(actv.getActiName()).append("-").append(listDate.get(i).getValue());
				//System.out.println("Fin task name" + sb1);

				task.setActvId(custserv.getActvId());
				task.setCustId(custserv.getCustId());
				task.setDelStatus(1);
				task.setEmpBudHr(custserv.getActvEmpBudgHr());
				task.setExInt1(1);
				task.setExInt2(1);
				task.setExVar1("NA");
				task.setExVar2("NA");
				task.setMappingId(0);
				task.setPeriodicityId(custserv.getPeriodicityId());
				task.setIsActive(1);
				task.setMngrBudHr(custserv.getActvManBudgHr());
				task.setServId(custserv.getExInt1());
				task.setTaskCode("NA");
				task.setTaskEmpIds(custserv.getExVar1());
				task.setTaskFyId(fin.getFinYearId());
				task.setTaskEndDate(dateFormat.format(date));
				task.setTaskStatus(-1);
				task.setTaskSubline("NA");
				task.setTaskText(String.valueOf(sb1));
				task.setUpdateDatetime(dateFormat1.format(date));
				task.setUpdateUsername(custserv.getUpdateUsername());

				serv = taskRepo.saveAndFlush(task);
				if(serv!=null) {
					Communication comcat=new Communication();
					comcat.setCommunText("Manual Task Created");
					comcat.setDelStatus(1);
					comcat.setEmpId(custserv.getUpdateUsername());
					comcat.setExInt1(1);
					comcat.setExInt2(1);
					comcat.setExVar1("NA");
					comcat.setExVar2("NA");
					comcat.setTypeId(2);
					comcat.setRemark("NA");
					comcat.setTaskId(serv.getTaskId());
					comcat.setUpdateDatetime(dateFormat1.format(date));
					comcat.setUpdateUser(custserv.getUpdateUsername());
					Communication save = communicationRepo.saveAndFlush(comcat);
					 
				}
			

			}

		} catch (Exception e) {

			System.err.println("Exce in saving saveTask " + e.getMessage());
			e.printStackTrace();

		}
		return inf;
	}
	/*************************Update Task*************************/
	@RequestMapping(value = { "/updateTaskByTaskId" }, method = RequestMethod.POST)
	public @ResponseBody Info updateTaskByTaskId(@RequestParam int taskId, @RequestParam int statusVal ) {

		Info info = new Info();
		try {
			int res = taskRepo.updateStatus(taskId, statusVal);

			if (res > 0) {
				info.setError(false);
				info.setMsg("success");

			} else {
				info.setError(true);
				info.setMsg("failed");

			}
		} catch (Exception e) {

			System.err.println("Exce in updateTaskByTaskId  " + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}
		
		return info;
	}
	
	
	@RequestMapping(value = { "/updateManualTaskByTaskId" }, method = RequestMethod.POST)
	public @ResponseBody Info updateManualTaskByTaskId(@RequestParam int taskId, @RequestParam int statusVal ) {

		Info info = new Info();
		int res=0;
		try {
			
			if(statusVal==1) {
				//task approval by Manager
				  res = taskRepo.updateStatus(taskId, statusVal);

			}
			else if(statusVal==0) {
				//task disapproval by Manager
				  res=taskRepo.updateStatus1(taskId);
			}
			else if(statusVal==2) {
				//to activate inactive task
				  res=taskRepo.activateTask(taskId);
			}

			if (res > 0) {
				info.setError(false);
				info.setMsg("success");

			} else {
				info.setError(true);
				info.setMsg("failed");

			}
		} catch (Exception e) {

			System.err.println("Exce in updateTaskByTaskId  " + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}
		
		return info;
	}
	
	 //Harsha Date:20 Aug 2019 
	
	@Autowired
	GetTaskListRepo getTaskListRepo;
	
	@RequestMapping(value = {"/getAllTaskList"}, method = RequestMethod.POST)
	public @ResponseBody List<GetTaskList> getAllTaskList(@RequestParam int stat ) {
		List<GetTaskList> servicsList = new ArrayList<GetTaskList>();
		try {
			servicsList = getTaskListRepo.getAllTaskList(stat);
		}catch(Exception e) {
			System.err.println("Exce in getAllTaskList " + e.getMessage());
		}
		return servicsList;
	}
	
	
	@RequestMapping(value = {"/getAllManualTaskList"}, method = RequestMethod.POST)
	public @ResponseBody List<GetTaskList> getAllManualTaskList(@RequestParam int stat,@RequestParam int empId ) {
		List<GetTaskList> servicsList = new ArrayList<GetTaskList>();
		try {
			servicsList = getTaskListRepo.getAllManualTaskList(stat,empId);
		}catch(Exception e) {
			System.err.println("Exce in getAllTaskList " + e.getMessage());
		}
		return servicsList;
	}
	
	
	@RequestMapping(value = {"/getAllInactiveTaskList"}, method = RequestMethod.POST)
	public @ResponseBody List<GetTaskList> getAllInactiveTaskList(@RequestParam int empId ) {
		List<GetTaskList> servicsList = new ArrayList<GetTaskList>();
		try {
			servicsList = getTaskListRepo.getAllInactiveTaskByEmpId(empId);
		}catch(Exception e) {
			System.err.println("Exce in getAllTaskList " + e.getMessage());
		}
		return servicsList;
	}
	
	
	@Autowired EmployeeMasterRepo empRepo;
	
	@RequestMapping(value = {"/getEmpByEmpTypeId"}, method = RequestMethod.POST)
	public @ResponseBody List<EmployeeMaster> getEmpByEmpTypeId(@RequestParam int roleId) {
		List<EmployeeMaster> empList = new ArrayList<EmployeeMaster>();
		try {
			empList = empRepo.findByEmpTypeAndDelStatusAndIsActive(roleId, 1,1);
		}catch (Exception e) {
			System.err.println("Exce in getServiceById" + e.getMessage());
		}
		return empList;
	}
	
	
	@RequestMapping(value = { "/taskAssignmentUpdate" }, method = RequestMethod.POST)
	public @ResponseBody Info taskAssignmentUpdate(@RequestParam List<Integer> taskIdList, @RequestParam String empIdList, @RequestParam String userId, @RequestParam String curDateTime, @RequestParam String workDate) {

		Info info = new Info();
		try
		{
			
			String endDate=null;
			if(workDate.isEmpty()==false) {
				//System.err.println("in if**"+workDate);
				  endDate = DateConvertor.convertToYMD(workDate);
			}else {
				//System.err.println("in else**"+workDate);
				endDate="0000-00-00";
			}
		
			
			int res = taskRepo.assignTask(taskIdList, empIdList,userId,curDateTime,endDate);
			
			if (res > 0) {
				info.setError(false);
				info.setMsg("success");

			} else {
				info.setError(true);
				info.setMsg("failed");

			}
		} catch (Exception e) {

			System.err.println("Exce in deleteService  " + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}

		return info;

	}
	
	@RequestMapping(value = {"/getAllFinYear"}, method = RequestMethod.GET)
	public @ResponseBody List<FinancialYear> getAllFinYear() {
		List<FinancialYear> yrList = new ArrayList<FinancialYear>();
		try {
			yrList = financialYearRepo.findByDelStatus(1);
		}catch (Exception e) {
			System.err.println("Exce in getServiceById" + e.getMessage());
		}
		return yrList;
	}
	 
	

}
