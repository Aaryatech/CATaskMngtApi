package com.ats.cataskapi.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.catalina.authenticator.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.custdetailrepo.CustomerDetailsRepo;
import com.ats.cataskapi.model.ActivityMaster;
import com.ats.cataskapi.model.ActivityPeriodDetails;
import com.ats.cataskapi.model.AssesseeTypeMaster;
import com.ats.cataskapi.model.CustmrActivityMap;
import com.ats.cataskapi.model.CustomerDetailMaster;
import com.ats.cataskapi.model.CustomerDetails;
import com.ats.cataskapi.model.CustomerGroupMaster;
import com.ats.cataskapi.model.CustomerHeaderMaster;
import com.ats.cataskapi.model.DevPeriodicityMaster;
import com.ats.cataskapi.model.EmployeeMaster;
import com.ats.cataskapi.model.FirmType;
import com.ats.cataskapi.model.GetActivityPeriodicity;
import com.ats.cataskapi.model.Info;
import com.ats.cataskapi.model.ServiceMaster;
import com.ats.cataskapi.model.ShowCustActiMapped;
import com.ats.cataskapi.model.StatusMaster;
import com.ats.cataskapi.model.TaskListHome;
import com.ats.cataskapi.model.TaskPeriodicityMaster;
import com.ats.cataskapi.model.TotalWorkHrs;
import com.ats.cataskapi.repositories.ActivityMasterRepo;
import com.ats.cataskapi.repositories.ActivityPeriodDetailsRepo;
import com.ats.cataskapi.repositories.AssesseeTypeMasterRepo;
import com.ats.cataskapi.repositories.CustmrActivityMapRepo;
import com.ats.cataskapi.repositories.CustomerDetailMasterRepo;
import com.ats.cataskapi.repositories.CustomerGroupMasterRepo;
import com.ats.cataskapi.repositories.CustomerHeaderMasterRepo;
import com.ats.cataskapi.repositories.DevPeriodicityMasterRepo;
import com.ats.cataskapi.repositories.EmployeeMasterRepo;
import com.ats.cataskapi.repositories.FirmTypeRepo;
import com.ats.cataskapi.repositories.GetActivityPeriodicityRepo;
import com.ats.cataskapi.repositories.ServiceMasterRepo;
import com.ats.cataskapi.repositories.ShowCustActiMappedRepo;
import com.ats.cataskapi.repositories.StatusMasterRepo;
import com.ats.cataskapi.repositories.TaskListHomeRepo;
import com.ats.cataskapi.repositories.TaskPeriodicityMasterRepo;
import com.ats.cataskapi.repositories.TotalWorkHrsRepo;
import com.ats.cataskapi.task.repo.TaskRepo;

@RestController
public class MasterApiController {

	/****************** ServiceMaster *******************/
	@Autowired
	ServiceMasterRepo srvMstrRepo;

	@RequestMapping(value = { "/saveService" }, method = RequestMethod.POST)
	public @ResponseBody ServiceMaster saveService(@RequestBody ServiceMaster service) {

		ServiceMaster serv = null;

		try {
			serv = srvMstrRepo.saveAndFlush(service);

		} catch (Exception e) {
			System.err.println("Exce in saving saveService " + e.getMessage());
			e.printStackTrace();

		}
		return serv;
	}

	@RequestMapping(value = { "/getAllServices" }, method = RequestMethod.GET)
	public @ResponseBody List<ServiceMaster> getAllServices() {
		List<ServiceMaster> servicsList = new ArrayList<ServiceMaster>();
		try {
			servicsList = srvMstrRepo.findByDelStatusOrderByServIdDesc(1);
		} catch (Exception e) {
			System.err.println("Exce in getAllServices " + e.getMessage());
		}
		return servicsList;
	}
	//Sachin 31-03-2020 
	@RequestMapping(value = { "/getServicesByPeriodId" }, method = RequestMethod.POST)
	public @ResponseBody List<ServiceMaster> getServicesByPeriodId(@RequestParam int periodcityId ) {
		List<ServiceMaster> servicsList = new ArrayList<ServiceMaster>();
		try {
			if(periodcityId!=0) {
			servicsList = srvMstrRepo.getServListByPeriodId(periodcityId);
			}else {
				servicsList = srvMstrRepo.findByDelStatusAndExInt1OrderByServIdDesc(1, 1);
			}
		} catch (Exception e) {
			System.err.println("Exce in getAllServices " + e.getMessage());
		}
		return servicsList;
	}

	// to get services with delStatus=1 and is_active=1
	@RequestMapping(value = { "/getAllEnrolledServices" }, method = RequestMethod.GET)
	public @ResponseBody List<ServiceMaster> getAllEnrolledServices() {
		List<ServiceMaster> servicsList = new ArrayList<ServiceMaster>();
		try {
			servicsList = srvMstrRepo.findByDelStatusAndExInt1OrderByServIdDesc(1, 1);
		} catch (Exception e) {
			System.err.println("Exce in getAllServices " + e.getMessage());
		}
		return servicsList;
	}

	@RequestMapping(value = { "/getServiceById" }, method = RequestMethod.POST)
	public @ResponseBody ServiceMaster getServiceById(@RequestParam int serviceId) {
		ServiceMaster servc = null;
		try {
			servc = srvMstrRepo.findByServIdAndDelStatus(serviceId, 1);
		} catch (Exception e) {
			System.err.println("Exce in getServiceById" + e.getMessage());
		}
		return servc;
	}

	@RequestMapping(value = { "/deleteService" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteService(@RequestParam int serviceId, @RequestParam int userId) {

		Info info = new Info();
		try {

			int res = 0;

			int taskCount = 0;
			taskCount = taskRepo.getTaskCountByServId(serviceId);
			if (taskCount < 1)
				res = srvMstrRepo.deleteService(serviceId, userId);

			if (res > 0) {
				info.setError(false);
				info.setMsg("success");

			} else {
				info.setError(true);
				info.setMsg("failed");

			}
			System.err.println("taskCount " + taskCount);
		} catch (Exception e) {

			System.err.println("Exce in deleteService  " + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}

		return info;

	}

	/********************** Activity Master **********************/

	@Autowired
	ActivityMasterRepo actvtMstrRepo;

	@RequestMapping(value = { "/getAllActivites" }, method = RequestMethod.GET)
	public @ResponseBody List<ActivityMaster> getAllActivites() {
		List<ActivityMaster> activitsList = new ArrayList<ActivityMaster>();
		try {
			activitsList = actvtMstrRepo.findByDelStatus(1);
		} catch (Exception e) {
			System.err.println("Exce in getAllActivites  " + e.getMessage());
		}
		return activitsList;
	}

	@RequestMapping(value = { "/saveActivity" }, method = RequestMethod.POST)
	public @ResponseBody ActivityMaster addNewActivity(@RequestBody ActivityMaster activity) {
		ActivityMaster actv = null;
		try {
			actv = actvtMstrRepo.saveAndFlush(activity);
		} catch (Exception e) {
			System.err.println("Exce in saveActivity  " + e.getMessage());
		}
		return actv;
	}

	@RequestMapping(value = { "/getActivityById" }, method = RequestMethod.POST)
	public @ResponseBody ActivityMaster getActivityById(@RequestParam int activityId) {
		ActivityMaster actvt = null;
		try {
			actvt = actvtMstrRepo.findByActiIdAndDelStatus(activityId, 1);
		} catch (Exception e) {
			System.err.println("Exce in getActivityById" + e.getMessage());
		}
		return actvt;
	}

	@RequestMapping(value = { "/getAllActivitesByServiceId" }, method = RequestMethod.POST)
	public @ResponseBody List<ActivityMaster> getAllActivitesByServiceId(@RequestParam int serviceId) {
		List<ActivityMaster> activitsList = new ArrayList<ActivityMaster>();
		try {
			activitsList = actvtMstrRepo.findByServIdAndDelStatus(serviceId, 1);
		} catch (Exception e) {
			System.err.println("Exce in getAllActivitesByServiceId  " + e.getMessage());
		}
		return activitsList;
	}

	@RequestMapping(value = { "/getAllEnrolledActivitesByServiceId" }, method = RequestMethod.POST)
	public @ResponseBody List<ActivityMaster> getAllEnrolledActivitesByServiceId(@RequestParam int serviceId) {
		List<ActivityMaster> activitsList = new ArrayList<ActivityMaster>();
		try {
			activitsList = actvtMstrRepo.findByServIdAndDelStatusAndExInt1(serviceId, 1, 1);
		} catch (Exception e) {
			System.err.println("Exce in getAllEnrolledActivitesByServiceId  " + e.getMessage());
		}
		return activitsList;
	}

	@RequestMapping(value = { "/deleteActivity" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteActivity(@RequestParam int activityId, @RequestParam int userId) {

		Info info = new Info();
		try {
			int res = 0;

			int taskCount = 0;
			taskCount = taskRepo.getTaskCountByActvId(activityId);
			if (taskCount < 1)
				res = actvtMstrRepo.deleteActivity(activityId, userId);

			if (res > 0) {
				info.setError(false);
				info.setMsg("success");

			} else {
				info.setError(true);
				info.setMsg("failed");
			}
		} catch (Exception e) {

			System.err.println("Exce in deleteActivity  " + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}
		return info;
	}

	@Autowired
	ActivityPeriodDetailsRepo actDtlRepo;

	@RequestMapping(value = { "/getActivityDetails" }, method = RequestMethod.POST)
	public @ResponseBody List<ActivityPeriodDetails> getActivityDetails(@RequestParam int serviceId) {
		List<ActivityPeriodDetails> activityDetailList = new ArrayList<ActivityPeriodDetails>();
		try {
			activityDetailList = actDtlRepo.getAllActivityDetailsList(serviceId);
			System.err.println("Srvd list:" + activityDetailList.toString());

		} catch (Exception e) {
			System.err.println("Exce in getActivityDetails  " + e.getMessage());
		}
		return activityDetailList;
	}

	/********************** Task Periodicity Master **********************/
	@Autowired
	TaskPeriodicityMasterRepo taskPeriodRepo;

	@RequestMapping(value = { "/getAllTaskPriodicityInfo" }, method = RequestMethod.GET)
	public @ResponseBody List<TaskPeriodicityMaster> getAllTaskPriodicityInfo() {
		List<TaskPeriodicityMaster> taskPriodList = new ArrayList<TaskPeriodicityMaster>();
		try {
			taskPriodList = taskPeriodRepo.findByDelStatus(1);
		} catch (Exception e) {
			System.err.println("Exce in getAllTaskPriodicityInfo  " + e.getMessage());
		}
		return taskPriodList;
	}

	@RequestMapping(value = { "/saveTaskPeriodicity" }, method = RequestMethod.POST)
	public @ResponseBody TaskPeriodicityMaster addNewTaskPeriodicity(@RequestBody TaskPeriodicityMaster taskPeriod) {
		TaskPeriodicityMaster priodicity = null;
		try {
			priodicity = taskPeriodRepo.saveAndFlush(taskPeriod);
		} catch (Exception e) {
			System.err.println("Exce in saveTaskPeriodicity  " + e.getMessage());
		}
		return priodicity;
	}

	@RequestMapping(value = { "/getTaskPerodicityId" }, method = RequestMethod.POST)
	public @ResponseBody TaskPeriodicityMaster getTaskPerodicityId(@RequestParam int periodicityId) {
		TaskPeriodicityMaster period = null;
		try {
			period = taskPeriodRepo.findByPeriodicityIdAndDelStatus(periodicityId, 1);
		} catch (Exception e) {
			System.err.println("Exce in getTaskPerodicityId" + e.getMessage());
		}
		return period;
	}

	@RequestMapping(value = { "/deleteTaskPeriodicity" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteTaskPeriodicity(@RequestParam int periodicityId, @RequestParam int userId) {

		Info info = new Info();
		try {
			int res = taskPeriodRepo.deleteTaskPeriodicity(periodicityId, userId);

			if (res > 0) {
				info.setError(false);
				info.setMsg("success");

			} else {
				info.setError(true);
				info.setMsg("failed");

			}
		} catch (Exception e) {

			System.err.println("Exce in deleteTaskPeriodicity  " + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}
		return info;
	}

	/************************ Employee Master **************************/
	@Autowired
	EmployeeMasterRepo empRepo;

	@RequestMapping(value = { "/getEmployees" }, method = RequestMethod.GET)
	public @ResponseBody List<EmployeeMaster> getAllEmployees() {

		List<EmployeeMaster> empList = new ArrayList<EmployeeMaster>();
		try {
			empList = empRepo.findByDelStatusAndIsActiveAndEmpTypeOrderByEmpIdDesc(1, 1, 2); // Fetched those employees
																								// which are partner
																								// type
		} catch (Exception e) {
			System.err.println("Exce in getAllEmployees  " + e.getMessage());
		}

		return empList;

	}

	@RequestMapping(value = { "/getAllEmployees" }, method = RequestMethod.GET)
	public @ResponseBody List<EmployeeMaster> getEmployees() {

		List<EmployeeMaster> empList = new ArrayList<EmployeeMaster>();
		try {
			empList = empRepo.findAllByDelStatusAndIsActiveOrderByEmpIdDesc(1, 1); // Fetched all employees
		} catch (Exception e) {
			System.err.println("Exce in getEmployees  " + e.getMessage());
		}

		return empList;

	}
	
	//Sachin 13-02-2020
	@RequestMapping(value = { "/getEmpForChangePass" }, method = RequestMethod.POST)
	public @ResponseBody EmployeeMaster getEmp(@RequestParam String email) {

		EmployeeMaster emp = new EmployeeMaster();
		try {
			emp= empRepo.findByEmpEmailAndDelStatus(email, 1);
		} catch (Exception e) {
			emp = new EmployeeMaster();
			System.err.println("Exce in getEmpForChangePass MasterApi  " + e.getMessage());
		}

		return emp;
	}

	@RequestMapping(value = { "/getAllEmployeesActiveInactive" }, method = RequestMethod.GET)
	public @ResponseBody List<EmployeeMaster> getAllEmployeesActiveInactive() {

		List<EmployeeMaster> empList = new ArrayList<EmployeeMaster>();
		try {
			empList = empRepo.findAllByDelStatusOrderByEmpIdDesc(1);
		} catch (Exception e) {
			System.err.println("Exce in getAllEmployees  " + e.getMessage());
		}

		return empList;

	}

	@RequestMapping(value = { "/getAllEmployeesWithRoleName" }, method = RequestMethod.GET)
	public @ResponseBody List<EmployeeMaster> getAllEmployeesWithRoleName() {

		List<EmployeeMaster> empList = new ArrayList<EmployeeMaster>();
		try {
			empList = empRepo.getAllEmployeesWithRoleName();
		} catch (Exception e) {
			System.err.println("Exce in getAllEmployees  " + e.getMessage());
		}

		return empList;

	}

	@RequestMapping(value = { "/saveNewEmployee" }, method = RequestMethod.POST)
	public @ResponseBody EmployeeMaster saveNewEmployee(@RequestBody EmployeeMaster employee) {
		EmployeeMaster emp = null;
		try {
			emp = empRepo.saveAndFlush(employee);

		} catch (Exception e) {
			System.err.println("Exce in getAllEmployees  " + e.getMessage());
		}

		return emp;

	}

	@RequestMapping(value = { "/getEmployeeById" }, method = RequestMethod.POST)
	public @ResponseBody EmployeeMaster getEmployeeById(@RequestParam int empId) {
		EmployeeMaster emp = null;
		try {
			emp = empRepo.findByEmpIdAndDelStatus(empId, 1);

		} catch (Exception e) {
			System.err.println("Exce in getEmployeeById  " + e.getMessage());
		}

		return emp;

	}
	
	 


	@RequestMapping(value = { "/checkEmployeeEmail" }, method = RequestMethod.POST)
	public @ResponseBody Info checkEmployeeEmail(@RequestParam String email, @RequestParam int eid) {

		Info info = new Info();
		EmployeeMaster emp = new EmployeeMaster();
		try {
			if (eid == 0) {
				emp = empRepo.findByEmpEmail(email);

				if (emp != null) {
					info.setError(false);
				} else {
					info.setError(true);
				}
			} else {

				emp = empRepo.findByEmpEmailAndEmpIdNot(email, eid);

				if (emp != null) {
					info.setError(false);
				} else {
					info.setError(true);
				}
			}

		} catch (Exception e) {
			System.err.println("Exce in checkEmployeeEmail  " + e.getMessage());
		}

		return info;

	}

	/********************************************************************************/
	@RequestMapping(value = { "/getExecutionPartner" }, method = RequestMethod.GET)
	public @ResponseBody List<EmployeeMaster> getExecutionPartner(@RequestParam int empId) {
		List<EmployeeMaster> emp = null;
		try {
			emp = empRepo.getExecutionartner();

		} catch (Exception e) {
			System.err.println("Exce in getEmployeeById  " + e.getMessage());
		}

		return emp;

	}

	@RequestMapping(value = { "/deleteEmployee" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteEmployee(@RequestParam int empId, @RequestParam int userId) {

		Info info = new Info();
		try {
			int res = 0;

			int taskCount = 0;
			taskCount = taskRepo.getTaskCountByEmpId(empId);
			if (taskCount < 1)
			 res = empRepo.deleteEmployee(empId, userId);

			if (res > 0) {
				info.setError(false);
				info.setMsg("success");

			} else {
				info.setError(true);
				info.setMsg("failed");

			}
		} catch (Exception e) {

			System.err.println("Exce in deleteTaskPeriodicity  " + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}
		return info;
	}

	@RequestMapping(value = { "/updateEmployeeActiveness" }, method = RequestMethod.POST)
	public @ResponseBody Info updateEmployeeActiveness(@RequestParam int empId, @RequestParam int userId) {

		Info info = new Info();
		EmployeeMaster emp = new EmployeeMaster();
		int stat = 0;
		try {
			emp = empRepo.findByEmpIdAndDelStatus(empId, 1);

			if (emp.getIsActive() == 1) {
				stat = 0;
			} else {
				stat = 1;
			}

			int res = empRepo.updateEmployeeActive(empId, userId, stat);

			if (res > 0) {
				info.setError(false);
				info.setMsg("success");

			} else {
				info.setError(true);
				info.setMsg("failed");

			}
		} catch (Exception e) {

			System.err.println("Exce in deleteTaskPeriodicity  " + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}
		return info;
	}

	/********************* Customer Group Master ************************/
	@Autowired
	CustomerGroupMasterRepo cstmrGrpRepo;

	@RequestMapping(value = { "/getAllCustomerGroups" }, method = RequestMethod.GET)
	public @ResponseBody List<CustomerGroupMaster> getAllCustomerGroups() {

		List<CustomerGroupMaster> cstmrGrpList = new ArrayList<CustomerGroupMaster>();
		try {
			cstmrGrpList = cstmrGrpRepo.findByDelStatusOrderByCustGroupIdDesc(1);
		} catch (Exception e) {
			System.err.println("Exce in getAllCustomerGroups  " + e.getMessage());
		}

		return cstmrGrpList;

	}

	@RequestMapping(value = { "/saveNewCustomerGroup" }, method = RequestMethod.POST)
	public @ResponseBody CustomerGroupMaster saveNewCustomerGroup(@RequestBody CustomerGroupMaster customerGrp) {
		CustomerGroupMaster cstrGrp = null;
		try {
			cstrGrp = cstmrGrpRepo.saveAndFlush(customerGrp);

		} catch (Exception e) {
			System.err.println("Exce in saveNewCustomerGroup  " + e.getMessage());
		}

		return cstrGrp;

	}

	@RequestMapping(value = { "/getCustomerGroupById" }, method = RequestMethod.POST)
	public @ResponseBody CustomerGroupMaster getCustomerGroupById(@RequestParam int custGrpId) {
		CustomerGroupMaster cust = null;
		try {
			cust = cstmrGrpRepo.findByCustGroupIdAndDelStatus(custGrpId, 1);

		} catch (Exception e) {
			System.err.println("Exce in getCustomerGroupById  " + e.getMessage());
		}

		return cust;

	}
@Autowired CustomerHeaderMasterRepo custMstRepo;
	@RequestMapping(value = { "/deleteCustomerGroup" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteCustomerGroup(@RequestParam int custGrpId, @RequestParam int userId) {

		Info info = new Info();
		try {
			
			int res = 0;

			int taskCount = 0;
			taskCount=custHeadRepo.getCustCountByCustGrpId(custGrpId);
			if(taskCount<1)
			 res = cstmrGrpRepo.deleteCustGroup(custGrpId, userId);
			if (res > 0) {
				info.setError(false);
				info.setMsg("success");

			} else {
				info.setError(true);
				info.setMsg("failed");

			}
		} catch (Exception e) {

			System.err.println("Exce in deleteCustomerGroup  " + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}
		return info;
	}

	/************************ Customer Header Master *************************/
	@Autowired
	CustomerHeaderMasterRepo custHeadRepo;

	@RequestMapping(value = { "/getAllCustomerHeaders" }, method = RequestMethod.GET)
	public @ResponseBody List<CustomerHeaderMaster> getAllCustomerHeaders() {

		List<CustomerHeaderMaster> custHeadList = new ArrayList<CustomerHeaderMaster>();
		try {
			custHeadList = custHeadRepo.findAllByDelStatusOrderByCustIdDesc(1);
		} catch (Exception e) {
			System.err.println("Exce in getAllCustomerHeaders  " + e.getMessage());
		}

		return custHeadList;

	}
	
	//Sac 27-03-2020
	
	@RequestMapping(value = { "/getCustomerByGroupId" }, method = RequestMethod.POST)
	public @ResponseBody List<CustomerHeaderMaster> getCustomerByGroupId(@RequestParam int custGrpId) {

		List<CustomerHeaderMaster> custHeadList = new ArrayList<CustomerHeaderMaster>();
		try {
			custHeadList = custHeadRepo.findAllByDelStatusAndCustGroupIdAndCustTypeOrderByCustFirmNameAsc(1, custGrpId,1);
		} catch (Exception e) {
			System.err.println("Exce in getCustomerByGroupId  " + e.getMessage());
		}

		return custHeadList;

	}
	
	
	@RequestMapping(value = { "/getCustListForExcel" }, method = RequestMethod.GET)
	public @ResponseBody List<CustomerHeaderMaster> getCustListForExcel() {

		List<CustomerHeaderMaster> custHeadList = new ArrayList<CustomerHeaderMaster>();
		try {
			custHeadList = custHeadRepo.getCustMstForExcel();
		} catch (Exception e) {
			System.err.println("Exce in getCustomerByGroupId  " + e.getMessage());
		}

		return custHeadList;

	}

	@RequestMapping(value = { "/saveNewCustomerHeader" }, method = RequestMethod.POST)
	public @ResponseBody CustomerHeaderMaster saveNewCustomerHeader(@RequestBody CustomerHeaderMaster custHeader) {
		CustomerHeaderMaster custHead = null;
		try {
			custHead = custHeadRepo.saveAndFlush(custHeader);

		} catch (Exception e) {
			System.err.println("Exce in saveNewCustomerGroup  " + e.getMessage());
		}

		return custHead;

	}

	@RequestMapping(value = { "/getCustomerHeadById" }, method = RequestMethod.POST)
	public @ResponseBody CustomerHeaderMaster getCustomerHeadById(@RequestParam int custHeadId) {
		CustomerHeaderMaster cust = null;
		try {
			cust = custHeadRepo.findByCustIdAndDelStatus(custHeadId, 1);

		} catch (Exception e) {
			System.err.println("Exce in getCustomerHeadById  " + e.getMessage());
		}

		return cust;

	}

	@RequestMapping(value = { "/deleteCustomerHeader" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteCustomerHeader(@RequestParam int custHeadId, @RequestParam int userId) {

		Info info = new Info();
		try {
			int res = custHeadRepo.deleteCustHeader(custHeadId, userId);

			if (res > 0) {
				info.setError(false);
				info.setMsg("success");

			} else {
				info.setError(true);
				info.setMsg("failed");

			}
		} catch (Exception e) {

			System.err.println("Exce in deleteCustomerHeader  " + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}
		return info;
	}

	/*********************
	 * CustomerDetails (Customer Header Master)
	 ************************/
	@Autowired
	CustomerDetailsRepo custDetlRepo;

	@RequestMapping(value = { "/getAllCustomerInfo" }, method = RequestMethod.GET)
	public @ResponseBody List<CustomerDetails> getAllCustomerInfo() {

		List<CustomerDetails> custHeadList = new ArrayList<CustomerDetails>();
		try {
			custHeadList = custDetlRepo.getAllCustomerDetails();
		} catch (Exception e) {
			System.err.println("Exce in getAllCustomerInfo  " + e.getMessage());
		}

		return custHeadList;

	}
//Sachin 1-04-2020 for getting cust in Assign task page by using periodicity id
	@RequestMapping(value = { "/getCustByPeriodcityId" }, method = RequestMethod.POST)
	public @ResponseBody List<CustomerDetails> getCustByPeriodcityId(@RequestParam int periodId) {

		List<CustomerDetails> custHeadList = new ArrayList<CustomerDetails>();
		try {
			if(periodId!=0) {
			custHeadList = custDetlRepo.getCustByPeriodIdInMappingTbl(periodId);
			}else {
				custHeadList = custDetlRepo.getAllCustomerDetails();
			}
		} catch (Exception e) {
			System.err.println("Exce in getCustByPeriodcityId  " + e.getMessage());
		}

		return custHeadList;
	}
	
	@RequestMapping(value = { "/getAllCustomerInfoActiveInactive" }, method = RequestMethod.GET)
	public @ResponseBody List<CustomerDetails> getAllCustomerInfoActiveInactive() {

		List<CustomerDetails> custHeadList = new ArrayList<CustomerDetails>();
		try {
			custHeadList = custDetlRepo.getAllCustomerDetailsActiveInactive();
		} catch (Exception e) {
			System.err.println("Exce in getAllCustomerInfo  " + e.getMessage());
		}

		return custHeadList;

	}

	@RequestMapping(value = { "/getcustById" }, method = RequestMethod.POST)
	public @ResponseBody CustomerDetails getcustById(@RequestParam int custId) {
		CustomerDetails custDetl = null;

		try {
			custDetl = custDetlRepo.findByCustId(custId);

		} catch (Exception e) {
			System.err.println("Exce in getcustById  " + e.getMessage());
		}
		return custDetl;
	}

	/*****************************
	 * Customer Detail Master
	 ****************************/

	@Autowired
	CustomerDetailMasterRepo custDetailRepo;

	@RequestMapping(value = { "/getAllCustomerDetail" }, method = RequestMethod.GET)
	public @ResponseBody List<CustomerDetailMaster> getAllCustomerDetail() {

		List<CustomerDetailMaster> custDetailList = new ArrayList<CustomerDetailMaster>();
		try {
			custDetailList = custDetailRepo.findAllByDelStatus(1);
		} catch (Exception e) {
			System.err.println("Exce in getAllCustomerDetail  " + e.getMessage());
		}

		return custDetailList;

	}

	@RequestMapping(value = { "/saveNewCustomerDetail" }, method = RequestMethod.POST)
	public @ResponseBody CustomerDetailMaster saveNewCustomerDetail(@RequestBody CustomerDetailMaster custDetail) {
		CustomerDetailMaster custDtl = null;
		try {
			custDtl = custDetailRepo.saveAndFlush(custDetail);

		} catch (Exception e) {
			System.err.println("Exce in saveNewCustomerDetail  " + e.getMessage());
		}

		return custDtl;

	}

	@RequestMapping(value = { "/getCustomerDetailById" }, method = RequestMethod.POST)
	public @ResponseBody CustomerDetailMaster getCustomerDetailById(@RequestParam int custDetailId) {
		CustomerDetailMaster custDetail = null;
		try {
			custDetail = custDetailRepo.findBycustDetailIdAndDelStatus(custDetailId, 1);

		} catch (Exception e) {
			System.err.println("Exce in getCustomerDetailById  " + e.getMessage());
		}

		return custDetail;

	}

	@RequestMapping(value = { "/deleteCustomerDetail" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteCustomerDetail(@RequestParam int custDetId, @RequestParam int userId,
			@RequestParam String curDateTime) {

		Info info = new Info();
		try {
			int res = custDetailRepo.deleteCustDetail(custDetId, userId, curDateTime);

			if (res > 0) {
				info.setError(false);
				info.setMsg("success");

			} else {
				info.setError(true);
				info.setMsg("failed");

			}
		} catch (Exception e) {

			System.err.println("Exce in deleteCustomerDetail  " + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}
		return info;
	}

	/************** Developer Periodicity Master ****************/

	@Autowired
	DevPeriodicityMasterRepo devPeriodRepo;

	@RequestMapping(value = { "/getAllPeriodicityDurations" }, method = RequestMethod.GET)
	public @ResponseBody List<DevPeriodicityMaster> getAllPeriodicityDurations() {

		List<DevPeriodicityMaster> list = new ArrayList<DevPeriodicityMaster>();
		try {
			list = devPeriodRepo.findAllByDelStatus(1);
		} catch (Exception e) {
			System.err.println("Exce in getAllPeriodicityDurations  " + e.getMessage());
		}

		return list;

	}

	/************ Firm Type **************/
	@Autowired
	FirmTypeRepo firmTypeRepo;

	@RequestMapping(value = { "/getAllFirms" }, method = RequestMethod.GET)
	public @ResponseBody List<FirmType> getAllFirms() {
		List<FirmType> firms = new ArrayList<FirmType>();
		try {
			firms = firmTypeRepo.findAllBydelStatus(1);

		} catch (Exception e) {
			System.err.println("Exce in getAllFirms  " + e.getMessage());
		}
		return firms;
	}

	/***************** Customer Activity Mapping Master ****************/

	@Autowired
	CustmrActivityMapRepo actMapRepo;

	@RequestMapping(value = { "/getAllMappedActivities" }, method = RequestMethod.GET)
	public @ResponseBody List<CustmrActivityMap> getAllMappedActivities() {
		List<CustmrActivityMap> activityMap = new ArrayList<CustmrActivityMap>();
		try {
			activityMap = actMapRepo.findAllBydelStatus(1);

		} catch (Exception e) {
			System.err.println("Exce in getAllMappedActivities  " + e.getMessage());
		}
		return activityMap;

	}

	@RequestMapping(value = { "/addNewMappedActivities" }, method = RequestMethod.POST)
	public @ResponseBody CustmrActivityMap addNewMappedActivities(@RequestBody CustmrActivityMap activityMapped) {
		CustmrActivityMap actMap = null;
		try {
			actMap = actMapRepo.saveAndFlush(activityMapped);

		} catch (Exception e) {

			System.err.println("Exce in addNewMappedActivities  " + e.getMessage());
		}
		return actMap;
	}

	@RequestMapping(value = { "/getCustMappedActivitiesById" }, method = RequestMethod.POST)
	@ResponseBody
	CustmrActivityMap getCustMappedActivitiesById(@RequestParam int actId) {
		CustmrActivityMap actMap = null;
		try {
			actMap = actMapRepo.findByActvIdAndDelStatus(actId, 1);

		} catch (Exception e) {
			System.err.println("Exce in getCustMappedActivitiesById  " + e.getMessage());
		}

		return actMap;

	}

	@RequestMapping(value = { "/deleteMappedActivity" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteMappedActivity(@RequestParam int mapId, @RequestParam int userId) {

		Info info = new Info();
		try {
			int res = actMapRepo.deleteMappedCustomerActivity(mapId, userId);

			if (res > 0) {
				info.setError(false);
				info.setMsg("success");

			} else {
				info.setError(true);
				info.setMsg("failed");

			}
		} catch (Exception e) {

			System.err.println("Exce in deleteMappedActivity  " + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}

		return info;
	}

	/************************/
	@Autowired
	ShowCustActiMappedRepo custActMapRepo;

	@RequestMapping(value = { "/getAllCustActivityMapped" }, method = RequestMethod.POST)
	public @ResponseBody List<ShowCustActiMapped> getAllCustActivityMapped(@RequestParam int custId) {
		List<ShowCustActiMapped> custActivityMap = new ArrayList<ShowCustActiMapped>();
		try {
			custActivityMap = custActMapRepo.getAllCustActiMapList(custId);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Exce in getAllCustActivityMapped  " + e.getMessage());
		}
		return custActivityMap;

	}

	/***************************************************************/
	@Autowired
	GetActivityPeriodicityRepo getActiPeriodRepo;

	@RequestMapping(value = { "/getPeriodicityByActivityId" }, method = RequestMethod.POST)
	public @ResponseBody GetActivityPeriodicity getPeriodicityByActId(@RequestParam int activityId) {
		GetActivityPeriodicity period = null;
		try {
			period = getActiPeriodRepo.getPriodicityByActid(activityId);
			System.err.println("MapPeriod--------------" + period.toString());
		} catch (Exception e) {
			System.err.println("Exce in getPeriodicityByActivityId  " + e.getMessage());
			e.printStackTrace();
		}
		return period;

	}

	/********************** Task List Home Page **************************/// ;.
	@Autowired
	TaskListHomeRepo taskListRepo;

	@RequestMapping(value = { "/getTaskListByEmpId" }, method = RequestMethod.POST)
	public @ResponseBody List<TaskListHome> getTaskListByEmpId(@RequestParam int empId,
			@RequestParam List<String> statusIds) {
		List<TaskListHome> taskList = null;
		try {

			taskList = new ArrayList<TaskListHome>();
			taskList = taskListRepo.getTaskList(empId, statusIds);

		} catch (Exception e) {
			System.err.println("Exce in getTaskListByEmpId  " + e.getMessage());
			e.printStackTrace();
		}

		return taskList;

	}

	@RequestMapping(value = { "/getTaskListByFilters" }, method = RequestMethod.POST)
	public @ResponseBody List<TaskListHome> getTaskListByFilters(@RequestParam int empId, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam int service, @RequestParam int activity,
			@RequestParam int custId, @RequestParam List<String> statusIds, @RequestParam int stats) {
		List<TaskListHome> taskList = null;
		try {

			taskList = new ArrayList<TaskListHome>();

			if (empId != 0 && fromDate != null && toDate != null && service != 0 && activity != 0 && custId != 0
					&& stats != 0) {
				System.out.println("Q1");
				taskList = taskListRepo.getTaskList1(empId, fromDate, toDate, service, activity, custId, statusIds,
						stats);
			} else if (empId != 0 && fromDate != null && toDate != null && service != 0 && activity != 0 && custId != 0
					&& stats == 0) {
				System.out.println("Q2");
				taskList = taskListRepo.getTaskList2(empId, fromDate, toDate, service, activity, custId, statusIds);
			} else if (empId != 0 && fromDate != null && toDate != null && service != 0 && activity != 0 && custId == 0
					&& stats == 0) {
				System.out.println("Q3");
				taskList = taskListRepo.getTaskList3(empId, fromDate, toDate, service, activity, statusIds);
			} else if (empId != 0 && fromDate != null && toDate != null && stats != 0 && custId == 0) {
				System.out.println("Q4");
				taskList = taskListRepo.getTaskListByStatus4(empId, fromDate, toDate, stats, statusIds);
			} else if (empId != 0 && fromDate != null && toDate != null && service == 0 && activity == 0 && custId == 0
					&& stats == 0) {
				System.out.println("Q6");
				taskList = taskListRepo.getTaskListByStatus6(empId, fromDate, toDate, statusIds);
			} else if (empId != 0) {
				System.out.println("Q5");
				taskList = taskListRepo.getTaskList(empId, statusIds);
			}

		} catch (Exception e) {
			System.err.println("Exce in getTaskListByFilters  " + e.getMessage());
			e.printStackTrace();
		}

		return taskList;

	}

	/******************************* Status Master ********************************/

	@Autowired
	StatusMasterRepo statusMstrRepo;

	@RequestMapping(value = { "/saveStatus" }, method = RequestMethod.POST)
	public @ResponseBody StatusMaster saveStatus(@RequestBody StatusMaster status) {

		StatusMaster stat = null;

		try {
			if(status.getStatusMstId()<1) {
				int recCount=statusMstrRepo.getStatusForDuplicate(status.getStatusText().trim());
				if(recCount<1) {
					stat = statusMstrRepo.saveAndFlush(status);
				}
			}else {
				int recCount=statusMstrRepo.getStatusForDuplicateForEdit(status.getStatusText().trim(),status.getStatusMstId());
				if(recCount<1) {
					stat = statusMstrRepo.saveAndFlush(status);
				}
			}
			//stat = statusMstrRepo.saveAndFlush(status);

		} catch (Exception e) {
			System.err.println("Exce in saving saveStatus " + e.getMessage());
			e.printStackTrace();

		}
		return stat;
	}

	@RequestMapping(value = { "/getAllStatus" }, method = RequestMethod.GET)
	public @ResponseBody List<StatusMaster> getAllStatus() {
		List<StatusMaster> statusList = new ArrayList<StatusMaster>();
		try {
			statusList = statusMstrRepo.findAllByDelStatusAndIsEditableOrderByStatusMstIdDesc(1, 1);
		} catch (Exception e) {
			System.err.println("Exce in getAllStatus " + e.getMessage());
		}
		return statusList;
	}

	@RequestMapping(value = { "/getStatusById" }, method = RequestMethod.POST)
	public @ResponseBody StatusMaster getStatusById(@RequestParam int statusId) {
		StatusMaster status = new StatusMaster();
		try {
			status = statusMstrRepo.findByStatusMstId(statusId);
		} catch (Exception e) {
			System.err.println("Exce in getStatusById " + e.getMessage());
		}
		return status;
	}

	@RequestMapping(value = { "/getMaxStatusValue" }, method = RequestMethod.GET)
	public @ResponseBody int getMaxStatusValue() {
		int maxStatus = 0;
		try {
			maxStatus = statusMstrRepo.getMaxStateValue();
		} catch (Exception e) {
			System.err.println("Exce in getMaxStatusValue " + e.getMessage());
		}
		return maxStatus;
	}

	@RequestMapping(value = { "/deleteStatusById" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteStatusById(@RequestParam int statusId, @RequestParam int userId) {

		Info info = new Info();
		try {
			int res = 0;
			StatusMaster statMst=statusMstrRepo.getOne(statusId);
			int taskCount = 0;
			taskCount=taskRepo.getTaskCountByStatus(statMst.getStatusValue());
			if(taskCount<1)
			
			 res = statusMstrRepo.deleteStatusById(statusId, userId);

			if (res > 0) {
				info.setError(false);
				info.setMsg("success");

			} else {
				info.setError(true);
				info.setMsg("failed");

			}
		} catch (Exception e) {

			System.err.println("Exce in deleteStatusById  " + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}

		return info;
	}

	@RequestMapping(value = { "/getStatusByEmpTypeIds" }, method = RequestMethod.POST)
	public @ResponseBody List<StatusMaster> getStatusByEmpIds(@RequestParam int empType) {
		List<StatusMaster> statusList = new ArrayList<StatusMaster>();
		try {
			statusList = statusMstrRepo.getStatusByEmpType(empType);
		} catch (Exception e) {
			System.err.println("Exce in getStatusByEmpIds " + e.getMessage());
		}
		return statusList;
	}

	/************************** Communication & Log ***************************/
	@RequestMapping(value = { "/getTaskByTaskId" }, method = RequestMethod.POST)
	public @ResponseBody TaskListHome getTaskByTaskId(@RequestParam int empType, @RequestParam int taskId) {
		TaskListHome task = new TaskListHome();
		try {
			task = taskListRepo.getTaskById(empType, taskId);
		} catch (Exception e) {
			System.err.println("Exce in getStatusByEmpIds " + e.getMessage());
		}
		return task;
	}

	@Autowired
	TotalWorkHrsRepo workHrsRepo;

	@RequestMapping(value = { "/getEmpWorkHrsByEmpId" }, method = RequestMethod.POST)
	public @ResponseBody List<TotalWorkHrs> getEmpWorkHrsByEmpId(@RequestParam int taskId) {
		List<TotalWorkHrs> hrs = new ArrayList<TotalWorkHrs>();
		try {
			hrs = workHrsRepo.getEmpTtlWorkHrs(taskId);
		} catch (Exception e) {
			System.err.println("Exce in getEmpWorkHrsByEmpId " + e.getMessage());
		}
		return hrs;
	}

	/************** Active Deactive *************/
	@Autowired
	TaskRepo taskRepo;

	@RequestMapping(value = { "/updateCustomerIsActiveStatus" }, method = RequestMethod.POST)
	public @ResponseBody Info updateServiceIsActiveStatus(@RequestParam("custId") int custId,
			@RequestParam("isActiveStatus") int isActiveStatus, @RequestParam("taskIds") List<Integer> taskIds) {

		Info info = new Info();

		try {

			int update = custHeadRepo.updateIsActiveStatus(custId, isActiveStatus);

			update = taskRepo.updateIsActiveStatus(taskIds, isActiveStatus);

			info.setError(false);
			info.setMessage("successfully");

		} catch (Exception e) {
			e.printStackTrace();

			info.setError(true);
			info.setMessage("failed");
		}

		return info;
	}

	@RequestMapping(value = { "/getDevPerodicityById" }, method = RequestMethod.POST)
	public @ResponseBody DevPeriodicityMaster getDevPerodicityById(@RequestParam int periodicityId) {
		DevPeriodicityMaster period = new DevPeriodicityMaster();
		try {
			period = devPeriodRepo.findByPeriodicityIdAndDelStatus(periodicityId, 1);

		} catch (Exception e) {
			System.err.println("Exce in getTaskPerodicityId" + e.getMessage());
		}
		return period;
	}

	/*********************** Change Password *****************/
	@RequestMapping(value = { "/changePass" }, method = RequestMethod.POST)
	public @ResponseBody Info changePass(@RequestParam String password, @RequestParam int userId) {
		Info info = new Info();
		try {
			int res = empRepo.chagePass(password, userId);

			if (res > 0) {
				info.setError(false);
				info.setMsg("success");

			} else {
				info.setError(true);
				info.setMsg("failed");

			}
		} catch (Exception e) {

			System.err.println("Exce in changePass" + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}

		return info;

	}

	/****************** Communication Deliverable Link ********************/

	@RequestMapping(value = { "/updateTaskDeliverLink" }, method = RequestMethod.POST)
	public @ResponseBody Info updateTaskDeliverLink(@RequestParam String link, @RequestParam int taskId) {
		Info info = new Info();
		try {
			int res = taskRepo.addDeliverLink(link, taskId);

			if (res > 0) {
				info.setError(false);
				info.setMsg("success");

			} else {
				info.setError(true);
				info.setMsg("failed");

			}
		} catch (Exception e) {

			System.err.println("Exce in updateTaskDeliverLink" + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}

		return info;

	}

	@RequestMapping(value = { "/checkUniquePan" }, method = RequestMethod.POST)
	public @ResponseBody Info checkUniquePan(@RequestParam int custId, @RequestParam String panNo) {

		Info info = new Info();
		try {
			if (custId < 1) {
				System.err.println("A");
				CustomerHeaderMaster custMst = custHeadRepo.findByCustPanNoAndDelStatus(panNo, 1);
				if (custMst != null) {
					System.err.println("B");
					info.setError(true);
				} else {
					System.err.println("C");
					info.setError(false);
				}
			} else {
				System.err.println("a");
				CustomerHeaderMaster custMst = custHeadRepo.findByCustPanNoAndDelStatusAndCustIdNot(panNo, 1, custId);

				if (custMst != null) {
					System.err.println("b");
					info.setError(true);
				} else {
					System.err.println("c");
					info.setError(false);
				}
			}

		} catch (Exception e) {
			System.err.println("Exce in checkUniquePan  " + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}
		return info;
	}
	
	//Sachin 2-04-2020 to show DB Assessee Type in customer master page
	@Autowired AssesseeTypeMasterRepo asseTypeRepo;
	
	@RequestMapping(value = { "/getAssesseeTypeList" }, method = RequestMethod.GET)
	public @ResponseBody List<AssesseeTypeMaster> getAssesseeTypeList() {
		List<AssesseeTypeMaster> assesseeList = new ArrayList<AssesseeTypeMaster>();
		try {
			
			assesseeList = asseTypeRepo.findByDelStatus(1);
			
		} catch (Exception e) {
			
			System.err.println("Exce in getAssesseeTypeList " + e.getMessage());
		}
		
		return assesseeList;
	}
}
