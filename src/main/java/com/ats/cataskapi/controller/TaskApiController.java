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
import com.ats.cataskapi.common.EmailUtility;
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
import com.ats.cataskapi.model.SetttingKeyValue;
import com.ats.cataskapi.model.TaskListHome;
import com.ats.cataskapi.repositories.ActivityMasterRepo;
import com.ats.cataskapi.repositories.CustmrActivityMapRepo;
import com.ats.cataskapi.repositories.DevPeriodicityMasterRepo;
import com.ats.cataskapi.repositories.EmployeeMasterRepo;
import com.ats.cataskapi.repositories.FinancialYearRepo;
import com.ats.cataskapi.repositories.ServiceMasterRepo;
import com.ats.cataskapi.repositories.SetttingKeyValueRepo;
import com.ats.cataskapi.repositories.TaskListHomeRepo;
import com.ats.cataskapi.task.model.EmpWorkLogHrs;
import com.ats.cataskapi.task.model.GetTaskList;
import com.ats.cataskapi.task.model.Task;
import com.ats.cataskapi.task.model.TempTaskSave;
import com.ats.cataskapi.task.repo.EmpWorkLogHrsRepo;
import com.ats.cataskapi.task.repo.GetTaskListRepo;
import com.ats.cataskapi.task.repo.TaskRepo;

import ch.qos.logback.classic.pattern.DateConverter;

@RestController
public class TaskApiController {

	// Harsha Date:19 Aug 2019
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

	@RequestMapping(value = { "/getFinYearByStatdate" }, method = RequestMethod.POST)
	public @ResponseBody FinancialYear getFinYearByStatdate(@RequestParam String statDate) {
		FinancialYear fin = new FinancialYear();
		try {

			fin = financialYearRepo.getFinYearBetDate(statDate);
		} catch (Exception e) {
			System.err.println("Exce in getServiceById" + e.getMessage());
		}
		return fin;
	}

	@RequestMapping(value = { "/getCurrentFinYear" }, method = RequestMethod.GET)
	public @ResponseBody FinancialYear getCurrentFinYear() {
		FinancialYear fin = new FinancialYear();
		try {

			fin = financialYearRepo.getCurrYear();
		} catch (Exception e) {
			System.err.println("Exce in getServiceById" + e.getMessage());
		}
		return fin;
	}

	@RequestMapping(value = { "/saveTaskRes" }, method = RequestMethod.POST)
	public @ResponseBody Info saveTask(@RequestBody TempTaskSave tsk) {
		Info inf = new Info();
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		CustmrActivityMap tempCust = new CustmrActivityMap();

		try {
			tempCust = actMapRepo.saveAndFlush(tsk.getCmpList());
			Task serv = new Task();

			for (int j = 0; j < tsk.getTskList().size(); j++) {

				tsk.getTskList().get(j).setTaskId(0);
				tsk.getTskList().get(j).setMappingId(tempCust.getMappingId());

				serv = taskRepo.saveAndFlush(tsk.getTskList().get(j));

				if (serv != null) {
					try {
						Communication comcat = new Communication();
						comcat.setCommunText("Task Created");
						comcat.setDelStatus(1);
						comcat.setEmpId(tempCust.getUpdateUsername());
						comcat.setExInt1(1);
						comcat.setExInt2(1);
						comcat.setExVar1("NA");
						comcat.setExVar2("NA");
						comcat.setTypeId(2);
						comcat.setRemark("NA");
						comcat.setTaskId(serv.getTaskId());
						comcat.setUpdateDatetime(dateFormat1.format(date));
						comcat.setUpdateUser(tempCust.getUpdateUsername());
						Communication save = communicationRepo.saveAndFlush(comcat);
					} catch (Exception e) {
						System.err.println("Exce in saving saveService " + e.getMessage());
						e.printStackTrace();

					}

				}
			}

		} catch (Exception e) {
			System.err.println("Exce in saving saveService " + e.getMessage());
			e.printStackTrace();

		}
		return inf;
	}

	@Autowired
	SetttingKeyValueRepo setttingKeyValueRepo;

	@RequestMapping(value = { "/saveMannualTask" }, method = RequestMethod.POST)
	public @ResponseBody Info saveMannualTask(@RequestBody CustmrActivityMap custserv) {

		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		int perId = 0;
		Task serv = null;
		int totdays = 0;

		Info inf = new Info();
		CustmrActivityMap cust = null;

		ActivityMaster actv = new ActivityMaster();

		actv = actvtMstrRepo.findByActiIdAndDelStatus(custserv.getActvId(), 1);

		DevPeriodicityMaster period = new DevPeriodicityMaster();
		period = devPeriodRepo.findByPeriodicityIdAndDelStatus(custserv.getPeriodicityId(), 1);

		try {

			perId = custserv.getPeriodicityId();

			String strDate = dateFormat.format(custserv.getActvStartDate());
			// System.out.println("Converted String str: " + strDate);
			String endDate = dateFormat.format(custserv.getActvEndDate());
			// System.out.println("Converted String end: " + endDate);
			// System.out.println("perId: " + perId);
			List<DateValues> listDate = PeriodicityDates.getDates(strDate, endDate, perId);
			totdays = listDate.size();

			ServiceMaster servc = new ServiceMaster();

			servc = srvMstrRepo.findByServIdAndDelStatus(actv.getServId(), 1);

			for (int i = 0; i < listDate.size(); i++) {

				Task task = new Task();

				Date date1 = listDate.get(i).getDate();

				// System.out.println("date bef stat**" +dateFormat.format(date1));
				task.setTaskStatutoryDueDate(
						PeriodicityDates.addDaysToGivenDate(dateFormat.format(date1), custserv.getActvStatutoryDays()));

				// System.out.println("stat date **" + task.getTaskStatutoryDueDate());
				FinancialYear fin = new FinancialYear();
				fin = financialYearRepo.getFinYearBetDate(String.valueOf(task.getTaskStatutoryDueDate()));

				task.setTaskStartDate(PeriodicityDates.addDaysToGivenDate(task.getTaskStatutoryDueDate(), -30));

				StringBuilder sb1 = new StringBuilder(servc.getServName());

				sb1.append("-").append(actv.getActiName()).append("-").append(listDate.get(i).getValue());
				// System.out.println("Fin task name" + sb1);

				SetttingKeyValue sk = new SetttingKeyValue();
				sk = setttingKeyValueRepo.findBySettingKeyAndDelStatus("ManualTaskMapId", 1);

				task.setActvId(custserv.getActvId());
				task.setCustId(custserv.getCustId());
				task.setDelStatus(1);
				task.setEmpBudHr(String.valueOf(custserv.getActvEmpBudgHr()));
				task.setExInt1(1);
				task.setExInt2(1);
				task.setExVar1("NA");
				task.setExVar2("NA");
				task.setMappingId(sk.getIntValue());
				task.setPeriodicityId(custserv.getPeriodicityId());
				task.setIsActive(1);
				task.setMngrBudHr(String.valueOf(custserv.getActvManBudgHr()));
				task.setServId(custserv.getExInt1());
				task.setTaskCode("NA");
				task.setTaskEmpIds(custserv.getExVar1());
				task.setTaskFyId(fin.getFinYearId());
				// task.setTaskEndDate(dateFormat.format(date));
				task.setTaskStatus(-1);
				task.setTaskSubline("NA");
				task.setTaskText(String.valueOf(sb1));
				task.setUpdateDatetime(dateFormat1.format(date));
				task.setUpdateUsername(custserv.getUpdateUsername());
				task.setBillingAmt(String.valueOf(custserv.getActvBillingAmt()));

				serv = taskRepo.saveAndFlush(task);
				if (serv != null) {
					Communication comcat = new Communication();
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

	/************************* Update Task *************************/
	@RequestMapping(value = { "/updateStatusByTaskId" }, method = RequestMethod.POST)
	public @ResponseBody Info updateTaskByTaskId(@RequestParam int taskId, @RequestParam int statusVal,
			@RequestParam int userId, @RequestParam String curDateTime, @RequestParam String compltnDate) {

		Info info = new Info();
		try {
			int res = taskRepo.updateStatusComplete(taskId, statusVal, userId, curDateTime, compltnDate);

			if (res > 0) {
				info.setError(false);
				info.setMsg("success");

			} else {
				info.setError(true);
				info.setMsg("failed");

			}
		} catch (Exception e) {

			System.err.println("Exce in updateStatusByTaskId  " + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}

		return info;
	}

	@RequestMapping(value = { "/updateManualTaskByTaskId" }, method = RequestMethod.POST)
	public @ResponseBody Info updateManualTaskByTaskId(@RequestParam int taskId, @RequestParam int statusVal,
			@RequestParam int userId, @RequestParam String curDateTime) {

		Info info = new Info();
		int res = 0;
		try {

			if (statusVal == 1) {
				// task approval by Manager
				res = taskRepo.updateStatus(taskId, statusVal, userId, curDateTime);

			} else if (statusVal == 0) {
				// task disapproval by Manager
				res = taskRepo.updateStatus1(taskId, userId, curDateTime);
			} else if (statusVal == 2) {
				// to activate inactive task
				res = taskRepo.activateTask(taskId, userId, curDateTime);
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

	@RequestMapping(value = { "/updateCompletedTaskByTaskId" }, method = RequestMethod.POST)
	public @ResponseBody Info updateCompletedTaskByTaskId(@RequestParam int taskId, @RequestParam int statusVal,
			@RequestParam String curDateTime, @RequestParam int userId) {

		Info info = new Info();
		int res = 0;
		try {
			// System.out.println("updateCompletedTaskByTaskId");
			// task approval by Manager
			res = taskRepo.updateCompStatus(taskId, statusVal, userId, curDateTime);

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

	@RequestMapping(value = { "/updateCompletedTask" }, method = RequestMethod.POST)
	public @ResponseBody Info updateCompletedTask(@RequestParam List<Integer> taskId, @RequestParam int stat,
			@RequestParam String curDateTime, @RequestParam int userId) {

		Info info = new Info();
		int res = 0;
		try {
			// System.out.println("updateCompletedTaskByTaskId");
			// task approval by Manager
			res = taskRepo.updateMulCompStatus(taskId, stat, userId, curDateTime);

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

	// Harsha Date:20 Aug 2019

	@Autowired
	GetTaskListRepo getTaskListRepo;

	@RequestMapping(value = { "/getAllTaskList" }, method = RequestMethod.POST)
	public @ResponseBody List<GetTaskList> getAllTaskList(@RequestParam int stat, @RequestParam int servId,
			@RequestParam int custId) {
		List<GetTaskList> servicsList = new ArrayList<GetTaskList>();
		
		System.out.println("prm are"+servId+custId);
		try {

			if (servId != 0 && custId != 0) {
				servicsList = getTaskListRepo.getAllTaskListSpec(stat,servId,custId);
			} else if (servId != 0 && custId == 0) {
				servicsList = getTaskListRepo.getAllTaskListSpecServ(stat,servId);
			} else if (servId == 0 && custId != 0) {
				servicsList = getTaskListRepo.getAllTaskListSpecCust(stat,custId);
			} else if((servId == -1 && custId == -1)) {
				servicsList = getTaskListRepo.getAllTaskListAll(stat);
			}else {
				servicsList = getTaskListRepo.getAllTaskListAll(stat);
			}

		} catch (Exception e) {
			System.err.println("Exce in getAllTaskList " + e.getMessage());
		}
		return servicsList;
	}

	/**************************** Add Emp Hrs ********************************/
	@RequestMapping(value = { "/getTaskListForWorkLog" }, method = RequestMethod.POST)
	public @ResponseBody List<GetTaskList> getTaskListForWorkLog(@RequestParam int stat, @RequestParam int emp,
			@RequestParam int service, @RequestParam int activity, @RequestParam int customer) {
		List<GetTaskList> servicsList = new ArrayList<GetTaskList>();
		try {
			servicsList = getTaskListRepo.getTaskListForWorkLog(stat, emp, service, activity, customer);
		} catch (Exception e) {
			System.err.println("Exce in getTaskListForWorkLog " + e.getMessage());
		}
		return servicsList;
	}

	@Autowired
	EmpWorkLogHrsRepo workLogRepo;

	@RequestMapping(value = { "/getTaskDailyWorkLog" }, method = RequestMethod.POST)
	public @ResponseBody List<EmpWorkLogHrs> getTaskDailyWorkLog(@RequestParam int stat, @RequestParam int emp,
			@RequestParam String fromDate, @RequestParam String toDate, @RequestParam int service,
			@RequestParam int activity, @RequestParam int customer) {
		List<EmpWorkLogHrs> list = new ArrayList<EmpWorkLogHrs>();
		try {

			if (emp != 0 && fromDate != null && toDate != null && customer == 0 && service == 0) {
				list = workLogRepo.getDailyWorkLogList(stat, emp, fromDate, toDate);
				// System.out.println("// Query 1----------"+list);
				 if(list.isEmpty()) {					 
					 list = workLogRepo.getTaskLogList(stat, emp, fromDate, toDate);					 
				 }
			}
			if (emp != 0 && fromDate != null && toDate != null && customer != 0 && service == 0) {
				list = workLogRepo.getDailyWorkLogListByCust(stat, emp, customer, fromDate, toDate);
				 //System.out.println("// Query 2----------"+list);
				
			}
			if (emp != 0 && fromDate != null && toDate != null && service != 0 && customer == 0) {
				list = workLogRepo.getDailyWorkLogListBySerAct(stat, emp, fromDate, toDate, service, activity);
				//System.out.println("// Query 3----------"+list);
			}
			if (emp != 0 && fromDate != null && toDate != null && customer != 0 && service != 0) {

				list = workLogRepo.getDailyWorkLogListByCustSerAct(stat, emp, customer, fromDate, toDate, service,
						activity);
				//System.out.println("// Query 4----------"+list);
			}
			if (emp == 0 && fromDate != null && toDate != null && customer == 0 && service == 0) {
				list = workLogRepo.getDailyWorkLogList(stat, fromDate, toDate);
				//System.out.println("// Query 5----------"+list);
			}
			if (emp == 0 && fromDate != null && toDate != null && customer != 0 && service == 0) {
				list = workLogRepo.getDailyWorkLogList(stat, fromDate, toDate, customer);
				//System.out.println("// Query 6----------"+list);
			}

		} catch (Exception e) {
			System.err.println("Exce in getTaskDailyWorkLog " + e.getMessage());
		}
		System.out.println("Log List-------------" + list.toString());
		return list;
	}

	/************************************************************************/

	@RequestMapping(value = { "/getAllManualTaskList" }, method = RequestMethod.POST)
	public @ResponseBody List<GetTaskList> getAllManualTaskList(@RequestParam int stat, @RequestParam int empId) {
		List<GetTaskList> servicsList = new ArrayList<GetTaskList>();
		try {

			SetttingKeyValue sk = new SetttingKeyValue();
			sk = setttingKeyValueRepo.findBySettingKeyAndDelStatus("ManualTaskMapId", 1);
			int mapId = sk.getIntValue();

			servicsList = getTaskListRepo.getAllManualTaskList(stat, empId, mapId);
		} catch (Exception e) {
			System.err.println("Exce in getAllTaskList " + e.getMessage());
		}
		return servicsList;
	}

	@RequestMapping(value = { "/getAllCompletedTaskList" }, method = RequestMethod.POST)
	public @ResponseBody List<GetTaskList> getAllCompletedTaskList(@RequestParam int stat, @RequestParam int empId,
			@RequestParam int servId, @RequestParam List<String> itemsAct, @RequestParam int custId) {
		List<GetTaskList> servicsList = new ArrayList<GetTaskList>();
		try {

			servicsList = getTaskListRepo.getAllCompletedTaskList(stat, empId);

			if (custId == 0 && itemsAct.contains("0") && servId == 0) {
				servicsList = getTaskListRepo.getAllCompletedTaskList(stat, empId);
			} else if (itemsAct.contains("-1")) {

				servicsList = getTaskListRepo.getAllCustCompletedTaskList(stat, empId, servId, custId);

			} else {
				servicsList = getTaskListRepo.getSpecCutCompletedTaskList(stat, empId, servId, custId, itemsAct);
			}

			System.err.println("ManualTakList***" + servicsList.toString());
		} catch (Exception e) {
			System.err.println("Exce in getAllTaskList " + e.getMessage());
		}
		return servicsList;
	}

	@RequestMapping(value = { "/getAllInactiveTaskList" }, method = RequestMethod.POST)
	public @ResponseBody List<GetTaskList> getAllInactiveTaskList(@RequestParam int empId, @RequestParam int servId,
			@RequestParam List<String> itemsAct, @RequestParam List<String> itemsCust) {
		List<GetTaskList> servicsList = new ArrayList<GetTaskList>();
		try {

			if (itemsCust.contains("0") && itemsAct.contains("0") && servId == 0) {
				servicsList = getTaskListRepo.getAllInactiveTaskByEmpId(empId);
			} else if (itemsCust.contains("-1") && itemsAct.contains("-1")) {

				servicsList = getTaskListRepo.getInactiveTaskByEmpIdAllCustAct(empId, servId);

			} else if (itemsCust.contains("-1")) {

				servicsList = getTaskListRepo.getAllInactiveTaskByEmpIdAllCust(empId, servId, itemsAct);

			} else if (itemsAct.contains("-1")) {

				servicsList = getTaskListRepo.getAllInactiveTaskByEmpIdAllAct(empId, servId, itemsCust);
			} else {
				servicsList = getTaskListRepo.getAllInactiveTaskByEmpIdSpec(empId, servId, itemsCust, itemsAct);
			}
			System.err.println("ManualTakList***" + servicsList.toString());

		} catch (Exception e) {
			System.err.println("Exce in getAllTaskList " + e.getMessage());
		}
		return servicsList;
	}

	@RequestMapping(value = { "/getTaskByTaskIdForEdit1" }, method = RequestMethod.POST)
	public @ResponseBody Task getTaskByTaskIdForEdit(@RequestParam int taskId) {
		Task empList = new Task();
		try {
			empList = taskRepo.findByTaskId(taskId);
		} catch (Exception e) {
			System.err.println("Exce in getServiceById" + e.getMessage());
		}
		return empList;
	}

	@Autowired
	EmployeeMasterRepo empRepo;

	@RequestMapping(value = { "/getEmpByEmpTypeId" }, method = RequestMethod.POST)
	public @ResponseBody List<EmployeeMaster> getEmpByEmpTypeId(@RequestParam int roleId) {
		List<EmployeeMaster> empList = new ArrayList<EmployeeMaster>();
		try {
			empList = empRepo.findByEmpTypeAndDelStatusAndIsActive(roleId, 1, 1);
		} catch (Exception e) {
			System.err.println("Exce in getServiceById" + e.getMessage());
		}
		return empList;
	}

	@RequestMapping(value = { "/taskAssignmentUpdate" }, method = RequestMethod.POST)
	public @ResponseBody Info taskAssignmentUpdate(@RequestParam List<Integer> taskIdList,
			@RequestParam String empIdList, @RequestParam String userId, @RequestParam String curDateTime,
			@RequestParam String workDate) {

		Info info = new Info();
		try {

			String endDate = null;
			if (workDate.isEmpty() == false) {
				// System.err.println("in if**"+workDate);
				endDate = DateConvertor.convertToYMD(workDate);
			} else {
				// System.err.println("in else**"+workDate);
				endDate = "0000-00-00";
			}

			int res = taskRepo.assignTask(taskIdList, empIdList, userId, curDateTime, endDate);

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

	@RequestMapping(value = { "/submitEditMannualTask" }, method = RequestMethod.POST)
	public @ResponseBody Info submitEditMannualTask(@RequestParam int taskId, @RequestParam String items1,
			@RequestParam String empBudgetHr, @RequestParam String mgBudgetHr, @RequestParam String startDate,
			 @RequestParam int customer, @RequestParam int service,
			@RequestParam int periodicityId, @RequestParam int activity, @RequestParam String curDateTime,
			@RequestParam int userId, @RequestParam String statDate, @RequestParam String billAmt) {

		Info info = new Info();
		try {

			String startDate1 = DateConvertor.convertToYMD(startDate);
			String statDate1 = DateConvertor.convertToYMD(statDate);

			int res = taskRepo.editTask(taskId, items1, empBudgetHr, mgBudgetHr, startDate1, curDateTime,
					customer, service, periodicityId, activity, userId, statDate1, billAmt);

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

	@RequestMapping(value = { "/getAllFinYear" }, method = RequestMethod.GET)
	public @ResponseBody List<FinancialYear> getAllFinYear() {
		List<FinancialYear> yrList = new ArrayList<FinancialYear>();
		try {
			yrList = financialYearRepo.findByDelStatus(1);
		} catch (Exception e) {
			System.err.println("Exce in getServiceById" + e.getMessage());
		}
		return yrList;
	}

	@RequestMapping(value = { "/updateEditTsk" }, method = RequestMethod.POST)
	public @ResponseBody Info updateTaskByTaskId(@RequestParam int taskId, @RequestParam String empHr,
			@RequestParam String mngHr, @RequestParam String dueDate, @RequestParam String workDate,
			@RequestParam String empId, int updateUserName, String updateDateTime) {

		Info info = new Info();

		try {
			int res = 0;
			if (workDate.equals("null")) {
				res = taskRepo.updateEditTaskForNull(taskId, empHr, mngHr, dueDate, empId, updateUserName,
						updateDateTime);
			} else {
				res = taskRepo.updateEditTask(taskId, empHr, mngHr, workDate, dueDate, empId, updateUserName,
						updateDateTime);
			}

			if (res > 0) {
				info.setError(false);
				info.setMsg("success");

				Communication comcat = new Communication();
				comcat.setCommunText("Task Updated");
				comcat.setDelStatus(1);
				comcat.setEmpId(updateUserName);
				comcat.setExInt1(1);
				comcat.setExInt2(1);
				comcat.setExVar1("NA");
				comcat.setExVar2("NA");
				comcat.setTypeId(2);
				comcat.setRemark("NA");
				comcat.setTaskId(taskId);
				comcat.setUpdateDatetime(updateDateTime);
				comcat.setUpdateUser(updateUserName);
				Communication save = communicationRepo.saveAndFlush(comcat);

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

	/********************************
	 * System Generated Status
	 ********************************/

	@Autowired
	TaskListHomeRepo taskListRepo;

	@RequestMapping(value = { "/getTaskAlloted" }, method = RequestMethod.GET)
	public @ResponseBody List<TaskListHome> getTaskAlloted() {
		List<TaskListHome> taskList = null;
		try {

			taskList = new ArrayList<TaskListHome>();
			taskList = taskListRepo.getAllotedTaskList();

		} catch (Exception e) {
			System.err.println("Exce in getTaskAlloted  " + e.getMessage());
			e.printStackTrace();
		}

		return taskList;

	}

	@RequestMapping(value = { "/getFilteredTaskUnalloted" }, method = RequestMethod.POST)
	public @ResponseBody List<TaskListHome> getFilteredTaskUnalloted(@RequestParam int service,
			@RequestParam int activity, @RequestParam int custId) {
		List<TaskListHome> taskList = null;
		try {

			taskList = new ArrayList<TaskListHome>();
			taskList = taskListRepo.getUnallotedFilterdTaskList(service, activity, custId);

		} catch (Exception e) {
			System.err.println("Exce in getTaskAlloted  " + e.getMessage());
			e.printStackTrace();
		}

		return taskList;

	}

	@RequestMapping(value = { "/getCriticalTask" }, method = RequestMethod.GET)
	public @ResponseBody List<TaskListHome> getCriticalTask() {
		List<TaskListHome> taskList = null;
		try {

			taskList = new ArrayList<TaskListHome>();
			taskList = taskListRepo.getCriticalTaskTaskList();

		} catch (Exception e) {
			System.err.println("Exce in getCriticalTask  " + e.getMessage());
			e.printStackTrace();
		}

		return taskList;

	}

	@RequestMapping(value = { "/getFilteredCriticalTask" }, method = RequestMethod.POST)
	public @ResponseBody List<TaskListHome> getFilteredCriticalTask(@RequestParam int service,
			@RequestParam int activity, @RequestParam int custId) {
		List<TaskListHome> taskList = null;
		try {

			taskList = new ArrayList<TaskListHome>();
			taskList = taskListRepo.getCriticalFilterdTaskList(service, activity, custId);

		} catch (Exception e) {
			System.err.println("Exce in getFilteredCriticalTask  " + e.getMessage());
			e.printStackTrace();
		}

		return taskList;

	}

	@RequestMapping(value = { "/getOverdueTask" }, method = RequestMethod.GET)
	public @ResponseBody List<TaskListHome> getOverdueTask() {
		List<TaskListHome> taskList = null;
		try {

			taskList = new ArrayList<TaskListHome>();
			taskList = taskListRepo.getOverdueTaskTaskList();

		} catch (Exception e) {
			System.err.println("Exce in getOverdueTask  " + e.getMessage());
			e.printStackTrace();
		}

		return taskList;

	}

	@RequestMapping(value = { "/getFilteredOverdueTask" }, method = RequestMethod.POST)
	public @ResponseBody List<TaskListHome> getFilteredOverdueTask(@RequestParam int service,
			@RequestParam int activity, @RequestParam int custId) {
		List<TaskListHome> taskList = null;
		try {

			taskList = new ArrayList<TaskListHome>();
			taskList = taskListRepo.getFiltredOverdueTaskTaskList(service, activity, custId);

		} catch (Exception e) {
			System.err.println("Exce in getFilteredOverdueTask  " + e.getMessage());
			e.printStackTrace();
		}

		return taskList;

	}

	// *********************forget Password*************************************

	@RequestMapping(value = { "/checkUserName" }, method = RequestMethod.POST)
	public @ResponseBody Info updateTaskByTaskId(@RequestParam String inputValue) {

		Info info = new Info();

		EmployeeMaster res = new EmployeeMaster();
		try {
			res = empRepo.findUserByEmailOrContactNumber(inputValue);

			if (res != null) {
				info.setError(false);
				info.setMsg("success");

				Info emailRes = EmailUtility.sendEmail("atsinfosoft@gmail.com", "atsinfosoft@123", res.getEmpEmail(),
						" CA Task Management Password Recovery", res.getEmpEmail(), res.getEmpPass());

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

	// **************************************Dash
	// WS*********************************************

	@RequestMapping(value = { "/getTaskListByEmpIdAndDashCountOverDue" }, method = RequestMethod.POST)
	public @ResponseBody List<TaskListHome> getTaskListByEmpIdAndDashCountOverDue(@RequestParam int empId,
			@RequestParam int stat, @RequestParam int userId) {
		List<TaskListHome> taskList = null;
		Date date = new Date();
		String endDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		// System.out.println("todays date"+endDate);

		try {

			taskList = new ArrayList<TaskListHome>();
			taskList = taskListRepo.getManualTaskListDashOverDue(stat, empId, endDate, userId);

		} catch (Exception e) {
			System.err.println("Exce in getTaskListByEmpId  " + e.getMessage());
			e.printStackTrace();
		}

		return taskList;

	}

	@RequestMapping(value = { "/getTaskListByEmpIdAndDashCountDueToday" }, method = RequestMethod.POST)
	public @ResponseBody List<TaskListHome> getTaskListByEmpIdAndDashCountDueDate(@RequestParam int empId,
			@RequestParam int stat, @RequestParam int userId) {
		List<TaskListHome> taskList = null;
		Date date = new Date();
		String endDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

		try {

			taskList = new ArrayList<TaskListHome>();
			taskList = taskListRepo.getManualTaskListDashDueToday(stat, empId, endDate, userId);

		} catch (Exception e) {
			System.err.println("Exce in getTaskListByEmpId  " + e.getMessage());
			e.printStackTrace();
		}

		return taskList;

	}

	@RequestMapping(value = { "/getTaskListByEmpIdAndDashCountDueWeek" }, method = RequestMethod.POST)
	public @ResponseBody List<TaskListHome> getTaskListByEmpIdAndDashCountDueWeek(@RequestParam int empId,
			@RequestParam int stat, @RequestParam int userId) {
		List<TaskListHome> taskList = null;
		Date date = new Date();
		String endDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

		try {

			taskList = new ArrayList<TaskListHome>();
			taskList = taskListRepo.getManualTaskListDashDueWeek(stat, empId, endDate, userId);

		} catch (Exception e) {
			System.err.println("Exce in getTaskListByEmpId  " + e.getMessage());
			e.printStackTrace();
		}

		return taskList;

	}

	@RequestMapping(value = { "/getTaskListByEmpIdAndDashCountDueMonth" }, method = RequestMethod.POST)
	public @ResponseBody List<TaskListHome> getTaskListByEmpIdAndDashCountDueMonth(@RequestParam int empId,
			@RequestParam int stat, @RequestParam int userId) {
		List<TaskListHome> taskList = null;
		Date date = new Date();
		String endDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

		try {

			taskList = new ArrayList<TaskListHome>();
			taskList = taskListRepo.getManualTaskListDashDueMonth(stat, empId, endDate, userId);

		} catch (Exception e) {
			System.err.println("Exce in getTaskListByEmpId  " + e.getMessage());
			e.printStackTrace();
		}

		return taskList;

	}

}
