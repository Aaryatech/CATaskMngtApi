package com.ats.cataskapi.controller;

import java.util.ArrayList;
import java.util.List;

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
	
	/******************ServiceMaster*******************/
	@Autowired ServiceMasterRepo srvMstrRepo;
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
	
	@RequestMapping(value = {"/getAllServices"}, method = RequestMethod.GET)
	public @ResponseBody List<ServiceMaster> getAllServices() {
		List<ServiceMaster> servicsList = new ArrayList<ServiceMaster>();
		try {
			servicsList = srvMstrRepo.findByDelStatusOrderByServIdDesc(1);
		}catch(Exception e) {
			System.err.println("Exce in getAllServices " + e.getMessage());
		}
		return servicsList;
	}
	
	@RequestMapping(value = {"/getServiceById"}, method = RequestMethod.POST)
	public @ResponseBody ServiceMaster getServiceById(@RequestParam int serviceId) {
		ServiceMaster servc = null;
		try {
			 servc = srvMstrRepo.findByServIdAndDelStatus(serviceId, 1);
		}catch (Exception e) {
			System.err.println("Exce in getServiceById" + e.getMessage());
		}
		return servc;
	}
	
	@RequestMapping(value = { "/deleteService" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteService(@RequestParam int serviceId, @RequestParam int userId) {

		Info info = new Info();
		try
		{
			int res = srvMstrRepo.deleteService(serviceId, userId);

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
	
	
	/**********************Activity Master**********************/
	
	@Autowired ActivityMasterRepo actvtMstrRepo;
	
	@RequestMapping(value = {"/getAllActivites"}, method = RequestMethod.GET)
	public @ResponseBody List<ActivityMaster>  getAllActivites(){
		List<ActivityMaster> activitsList = new ArrayList<ActivityMaster>(); 
		try {
			activitsList = actvtMstrRepo.findByDelStatus(1);
		}catch (Exception e) {
			System.err.println("Exce in getAllActivites  " + e.getMessage());
		}
		return activitsList;
	}
	
	@RequestMapping(value = {"/saveActivity"}, method = RequestMethod.POST)
	public @ResponseBody ActivityMaster addNewActivity(@RequestBody ActivityMaster activity) {
		ActivityMaster actv = null;
		try {
			actv = actvtMstrRepo.saveAndFlush(activity);
		}catch (Exception e) {
			System.err.println("Exce in saveActivity  " + e.getMessage());
		}
		return actv;
	}

	@RequestMapping(value = {"/getActivityById"}, method = RequestMethod.POST)
	public @ResponseBody ActivityMaster getActivityById(@RequestParam int activityId) {
		ActivityMaster actvt = null;
		try {
			actvt = actvtMstrRepo.findByActiIdAndDelStatus(activityId, 1);
		}catch (Exception e) {
			System.err.println("Exce in getActivityById" + e.getMessage());
		}
		return actvt;
	}
	
	@RequestMapping(value = {"/getAllActivitesByServiceId"}, method = RequestMethod.POST)
	public @ResponseBody List<ActivityMaster>  getAllActivitesByServiceId(@RequestParam int serviceId){
		List<ActivityMaster> activitsList = new ArrayList<ActivityMaster>(); 
		try {
			activitsList = actvtMstrRepo.findByServIdAndDelStatus(serviceId, 1);
		}catch (Exception e) {
			System.err.println("Exce in getAllActivitesByServiceId  " + e.getMessage());
		}
		return activitsList;
	}
	
	@RequestMapping(value = { "/deleteActivity" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteActivity(@RequestParam int activityId, @RequestParam int userId) {
		
		Info info = new Info();
		try
		{
			
			int res = actvtMstrRepo.deleteActivity(activityId, userId);

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
	
	@Autowired ActivityPeriodDetailsRepo actDtlRepo;
	@RequestMapping(value = {"/getActivityDetails"}, method=RequestMethod.POST)
	public @ResponseBody List<ActivityPeriodDetails>  getActivityDetails(@RequestParam int serviceId){
		List<ActivityPeriodDetails> activityDetailList = new ArrayList<ActivityPeriodDetails>();
		try {
			activityDetailList = actDtlRepo.getAllActivityDetailsList(serviceId);
				System.err.println("Srvd list:"+activityDetailList.toString());
			
		}catch (Exception e) {
			System.err.println("Exce in getActivityDetails  " + e.getMessage());
		}
		return activityDetailList;
	}
	
	/**********************Task Periodicity Master**********************/
	@Autowired TaskPeriodicityMasterRepo taskPeriodRepo;
	
	@RequestMapping(value = {"/getAllTaskPriodicityInfo"}, method = RequestMethod.GET)
	public @ResponseBody List<TaskPeriodicityMaster>  getAllTaskPriodicityInfo(){
		List<TaskPeriodicityMaster> taskPriodList = new ArrayList<TaskPeriodicityMaster>(); 
		try {
			taskPriodList = taskPeriodRepo.findByDelStatus(1);
		}catch (Exception e) {
			System.err.println("Exce in getAllTaskPriodicityInfo  " + e.getMessage());
		}
		return taskPriodList;
	}
	
	@RequestMapping(value = {"/saveTaskPeriodicity"}, method = RequestMethod.POST)
	public @ResponseBody TaskPeriodicityMaster addNewTaskPeriodicity(@RequestBody TaskPeriodicityMaster taskPeriod) {
		TaskPeriodicityMaster priodicity = null;
		try {
			priodicity = taskPeriodRepo.saveAndFlush(taskPeriod);
		}catch (Exception e) {
			System.err.println("Exce in saveTaskPeriodicity  " + e.getMessage());
		}
		return priodicity;
	}
	
	@RequestMapping(value = {"/getTaskPerodicityId"}, method = RequestMethod.POST)
	public @ResponseBody TaskPeriodicityMaster getTaskPerodicityId(@RequestParam int periodicityId) {
		TaskPeriodicityMaster period = null;
		try {
			period = taskPeriodRepo.findByPeriodicityIdAndDelStatus(periodicityId, 1);
		}catch (Exception e) {
			System.err.println("Exce in getTaskPerodicityId" + e.getMessage());
		}
		return period;
	}
	
	@RequestMapping(value = { "/deleteTaskPeriodicity" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteTaskPeriodicity(@RequestParam int periodicityId, @RequestParam int userId) {

		Info info = new Info();
		try
		{
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
	
	/************************Employee Master**************************/
	@Autowired EmployeeMasterRepo empRepo;
	
	@RequestMapping(value = {"/getAllEmployees"}, method = RequestMethod.GET)
	public @ResponseBody List<EmployeeMaster> getAllEmployees(){
		
		List<EmployeeMaster> empList = new ArrayList<EmployeeMaster>();
		try {
			empList = empRepo.findAllByDelStatusAndIsActiveOrderByEmpIdDesc(1,1);
		}catch (Exception e) {
			System.err.println("Exce in getAllEmployees  " + e.getMessage());
		}
		
		return empList;
		
	}
	
	@RequestMapping(value = {"/getAllEmployeesWithRoleName"}, method = RequestMethod.GET)
	public @ResponseBody List<EmployeeMaster> getAllEmployeesWithRoleName(){
		
		List<EmployeeMaster> empList = new ArrayList<EmployeeMaster>();
		try {
			empList = empRepo.getAllEmployeesWithRoleName();
		}catch (Exception e) {
			System.err.println("Exce in getAllEmployees  " + e.getMessage());
		}
		
		return empList;
		
	}
	
	@RequestMapping(value = {"/saveNewEmployee"}, method = RequestMethod.POST)
	public @ResponseBody EmployeeMaster saveNewEmployee(@RequestBody EmployeeMaster employee) {
		EmployeeMaster emp = null;
		try {
				emp = empRepo.saveAndFlush(employee);
			
		}catch (Exception e) {
			System.err.println("Exce in getAllEmployees  " + e.getMessage());
		}
		
		return emp;
		
	}
	
	@RequestMapping(value = {"/getEmployeeById"}, method = RequestMethod.POST)
	public @ResponseBody EmployeeMaster getEmployeeById(@RequestParam int empId) {
		EmployeeMaster emp = null;
		try {
				emp = empRepo.findByEmpIdAndDelStatus(empId, 1);
			
		}catch (Exception e) {
			System.err.println("Exce in getEmployeeById  " + e.getMessage());
		}
		
		return emp;
		
	}
	
	@RequestMapping(value = { "/deleteEmployee" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteEmployee(@RequestParam int empId, @RequestParam int userId) {

		Info info = new Info();
		try
		{
			int res = empRepo.deleteEmployee(empId, userId);

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
		int stat=0;
		try
		{
			emp = empRepo.findByEmpIdAndDelStatus(empId, 1);
			
			if(emp.getIsActive()==1) {
				stat=0;
			}else {
				stat=1;
			}
			
			int res = empRepo.updateEmployeeActive(empId, userId,stat);

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
	
	/*********************Customer Group Master************************/
	@Autowired CustomerGroupMasterRepo cstmrGrpRepo;
	
	@RequestMapping(value = {"/getAllCustomerGroups"}, method = RequestMethod.GET)
	public @ResponseBody List<CustomerGroupMaster> getAllCustomerGroups(){
		
		List<CustomerGroupMaster> cstmrGrpList = new ArrayList<CustomerGroupMaster>();
		try {
			cstmrGrpList = cstmrGrpRepo.findByDelStatusOrderByCustGroupIdDesc(1);
		}catch (Exception e) {
			System.err.println("Exce in getAllCustomerGroups  " + e.getMessage());
		}
		
		return cstmrGrpList;
		
	}
	
	@RequestMapping(value = {"/saveNewCustomerGroup"}, method = RequestMethod.POST)
	public @ResponseBody CustomerGroupMaster saveNewCustomerGroup(@RequestBody CustomerGroupMaster customerGrp) {
		CustomerGroupMaster cstrGrp = null;
		try {
			cstrGrp = cstmrGrpRepo.saveAndFlush(customerGrp);
			
		}catch (Exception e) {
			System.err.println("Exce in saveNewCustomerGroup  " + e.getMessage());
		}
		
		return cstrGrp;
		
	}
	
	@RequestMapping(value = {"/getCustomerGroupById"}, method = RequestMethod.POST)
	public @ResponseBody CustomerGroupMaster getCustomerGroupById(@RequestParam int custGrpId) {
		CustomerGroupMaster cust = null;
		try {
			cust = cstmrGrpRepo.findByCustGroupIdAndDelStatus(custGrpId, 1);
			
		}catch (Exception e) {
			System.err.println("Exce in getCustomerGroupById  " + e.getMessage());
		}
		
		return cust;
		
	}
	
	@RequestMapping(value = { "/deleteCustomerGroup" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteCustomerGroup(@RequestParam int custGrpId, @RequestParam int userId) {

		Info info = new Info();
		try
		{
			int res = cstmrGrpRepo.deleteCustGroup(custGrpId, userId);

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
	
	/************************Customer Header Master*************************/
	@Autowired CustomerHeaderMasterRepo custHeadRepo;
	
	@RequestMapping(value = {"/getAllCustomerHeaders"}, method = RequestMethod.GET)
	public @ResponseBody List<CustomerHeaderMaster> getAllCustomerHeaders(){
		
		List<CustomerHeaderMaster> custHeadList = new ArrayList<CustomerHeaderMaster>();
		try {
			custHeadList = custHeadRepo.findAllByDelStatusOrderByCustIdDesc(1);
		}catch (Exception e) {
			System.err.println("Exce in getAllCustomerHeaders  " + e.getMessage());
		}
		
		return custHeadList;
		
	}
	
	@RequestMapping(value = {"/saveNewCustomerHeader"}, method = RequestMethod.POST)
	public @ResponseBody CustomerHeaderMaster saveNewCustomerHeader(@RequestBody CustomerHeaderMaster custHeader) {
		CustomerHeaderMaster custHead = null;
		try {
			custHead = custHeadRepo.saveAndFlush(custHeader);
			
		}catch (Exception e) {
			System.err.println("Exce in saveNewCustomerGroup  " + e.getMessage());
		}
		
		return custHead;
		
	}
	
	@RequestMapping(value = {"/getCustomerHeadById"}, method = RequestMethod.POST)
	public @ResponseBody CustomerHeaderMaster getCustomerHeadById(@RequestParam int custHeadId) {
		CustomerHeaderMaster cust = null;
		try {
			cust = custHeadRepo.findByCustIdAndDelStatus(custHeadId, 1);
			
		}catch (Exception e) {
			System.err.println("Exce in getCustomerHeadById  " + e.getMessage());
		}
		
		return cust;
		
	}
	
	@RequestMapping(value = { "/deleteCustomerHeader" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteCustomerHeader(@RequestParam int custHeadId,@RequestParam int userId ) {

		Info info = new Info();
		try
		{
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
	
	
	/*********************   CustomerDetails  (Customer Header Master)************************/
	@Autowired CustomerDetailsRepo custDetlRepo;
	@RequestMapping(value = {"/getAllCustomerInfo"}, method = RequestMethod.GET)
	public @ResponseBody List<CustomerDetails> getAllCustomerInfo(){
		
		List<CustomerDetails> custHeadList = new ArrayList<CustomerDetails>();
		try {
			custHeadList = custDetlRepo.getAllCustomerDetails();
		}catch (Exception e) {
			System.err.println("Exce in getAllCustomerInfo  " + e.getMessage());
		}
		
		return custHeadList;
		
	}
	
	@RequestMapping(value = {"/getcustById"}, method=RequestMethod.POST)
	public @ResponseBody CustomerDetails getcustById(@RequestParam int custId) {
		CustomerDetails custDetl = null;
		
		try {
			custDetl = custDetlRepo.findByCustId(custId);
			
			
		}catch (Exception e) {
			System.err.println("Exce in getcustById  " + e.getMessage());
		}
		return custDetl;
	}
	
	/*****************************Customer Detail Master****************************/
	
@Autowired CustomerDetailMasterRepo custDetailRepo;
	
	@RequestMapping(value = {"/getAllCustomerDetail"}, method = RequestMethod.GET)
	public @ResponseBody List<CustomerDetailMaster> getAllCustomerDetail(){
		
		List<CustomerDetailMaster> custDetailList = new ArrayList<CustomerDetailMaster>();
		try {
			custDetailList = custDetailRepo.findAllByDelStatus(1);
		}catch (Exception e) {
			System.err.println("Exce in getAllCustomerDetail  " + e.getMessage());
		}
		
		return custDetailList;
		
	}
	
	@RequestMapping(value = {"/saveNewCustomerDetail"}, method = RequestMethod.POST)
	public @ResponseBody CustomerDetailMaster saveNewCustomerDetail(@RequestBody CustomerDetailMaster custDetail) {
		CustomerDetailMaster custDtl = null;
		try {
			custDtl = custDetailRepo.saveAndFlush(custDetail);
			
		}catch (Exception e) {
			System.err.println("Exce in saveNewCustomerDetail  " + e.getMessage());
		}
		
		return custDtl;
		
	}
	
	@RequestMapping(value = {"/getCustomerDetailById"}, method = RequestMethod.POST)
	public @ResponseBody CustomerDetailMaster getCustomerDetailById(@RequestParam int custDetailId) {
		CustomerDetailMaster custDetail = null;
		try {
			custDetail = custDetailRepo.findBycustDetailIdAndDelStatus(custDetailId, 1);
			
		}catch (Exception e) {
			System.err.println("Exce in getCustomerDetailById  " + e.getMessage());
		}
		
		return custDetail;
		
	}
	
	
	@RequestMapping(value = { "/deleteCustomerDetail" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteCustomerDetail(@RequestParam int custDetailId, @RequestParam int userId ) {

		Info info = new Info();
		try
		{
			int res = custDetailRepo.deleteCustDetail(custDetailId, userId);

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
	
	/**************Developer Periodicity Master****************/
	
	@Autowired DevPeriodicityMasterRepo devPeriodRepo;
	
	@RequestMapping(value = {"/getAllPeriodicityDurations"}, method = RequestMethod.GET)
	public @ResponseBody List<DevPeriodicityMaster> getAllPeriodicityDurations(){
		
		List<DevPeriodicityMaster> list = new ArrayList<DevPeriodicityMaster>();
		try {
			list = devPeriodRepo.findAllByDelStatus(1);
		}catch (Exception e) {
			System.err.println("Exce in getAllPeriodicityDurations  " + e.getMessage());
		}
		
		return list;
		
	}
	
	/************Firm Type**************/
	@Autowired FirmTypeRepo firmTypeRepo;
	
	@RequestMapping(value = {"/getAllFirms"}, method = RequestMethod.GET)
	public @ResponseBody List<FirmType> getAllFirms(){
		List<FirmType> firms = new ArrayList<FirmType>();
		try {
			firms = firmTypeRepo.findAllBydelStatus(1);
			
		}catch (Exception e) {
			System.err.println("Exce in getAllFirms  " + e.getMessage());
		}
		return firms;
	}
	
	/*****************Customer Activity Mapping Master****************/
	
	@Autowired CustmrActivityMapRepo actMapRepo;
	
	@RequestMapping(value = {"/getAllMappedActivities"}, method = RequestMethod.GET)
	public @ResponseBody List<CustmrActivityMap> getAllMappedActivities(){
		List<CustmrActivityMap> activityMap = new ArrayList<CustmrActivityMap>();
		try {
			activityMap = actMapRepo.findAllBydelStatus(1);
			
		}catch (Exception e) {
			System.err.println("Exce in getAllMappedActivities  " + e.getMessage());
		}
		return activityMap;
		
	}
	
	@RequestMapping(value = {"/addNewMappedActivities"}, method = RequestMethod.POST)
	public @ResponseBody CustmrActivityMap addNewMappedActivities(@RequestBody  CustmrActivityMap activityMapped){
		CustmrActivityMap actMap = null;
		try {
			actMap = actMapRepo.saveAndFlush(activityMapped);
			
		}catch (Exception e) {
			
			System.err.println("Exce in addNewMappedActivities  " + e.getMessage());
		}
		return actMap;		
	}
	
	@RequestMapping(value = {"/getCustMappedActivitiesById"}, method = RequestMethod.POST)
	@ResponseBody CustmrActivityMap getCustMappedActivitiesById(@RequestParam int actId) {
		CustmrActivityMap actMap = null;
		try {
			actMap = actMapRepo.findByActvIdAndDelStatus(actId, 1);
				
			
		}catch (Exception e) {
			System.err.println("Exce in getCustMappedActivitiesById  " + e.getMessage());
		}
		
		return actMap;
		
	}
	
	@RequestMapping(value = { "/deleteMappedActivity" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteMappedActivity(@RequestParam int mapId, @RequestParam int userId ) {

		Info info = new Info();
		try
		{
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
	@Autowired ShowCustActiMappedRepo custActMapRepo;
	@RequestMapping(value = {"/getAllCustActivityMapped"}, method = RequestMethod.POST)
	public @ResponseBody List<ShowCustActiMapped> getAllCustActivityMapped(@RequestParam int custId){
		List<ShowCustActiMapped> custActivityMap = new ArrayList<ShowCustActiMapped>();
		try {
			custActivityMap = custActMapRepo.getAllCustActiMapList(custId);
			
		}catch (Exception e) {
			System.err.println("Exce in getAllCustActivityMapped  " + e.getMessage());
		}
		return custActivityMap;
		
	}
	
	/***************************************************************/
	@Autowired GetActivityPeriodicityRepo getActiPeriodRepo;
	
	@RequestMapping(value = {"/getPeriodicityByActivityId"}, method = RequestMethod.POST)
	public @ResponseBody GetActivityPeriodicity getPeriodicityByActId(@RequestParam int activityId) {
		GetActivityPeriodicity period = null; 
		try {
			period = getActiPeriodRepo.getPriodicityByActid(activityId);
			System.err.println("MapPeriod--------------"+period.toString());
		}catch (Exception e) {
			System.err.println("Exce in getPeriodicityByActivityId  " + e.getMessage());
			e.printStackTrace();
		}
		return period;
		
	}
	
	/**********************Task List Home Page**************************///;.
	@Autowired TaskListHomeRepo taskListRepo;
	
	@RequestMapping(value = {"/getTaskListByEmpId"}, method = RequestMethod.POST)
	public @ResponseBody List<TaskListHome> getTaskListByEmpId(@RequestParam int empId, @RequestParam  List<String> statusIds) {
		List<TaskListHome> taskList = null;
		try {
			
			taskList = new ArrayList<TaskListHome>();
			taskList = taskListRepo.getTaskList(empId, statusIds);			

		}catch (Exception e) {
			System.err.println("Exce in getTaskListByEmpId  " + e.getMessage());
			e.printStackTrace();
		}
		
		return taskList;
	
	}
	
	@RequestMapping(value = {"/getTaskListByFilters"}, method = RequestMethod.POST)
	public @ResponseBody List<TaskListHome> getTaskListByFilters(@RequestParam int empId, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam int service, @RequestParam int activity, @RequestParam int custId,
			@RequestParam List<String> statusIds) {
		List<TaskListHome> taskList = null;
		try {
			
			taskList = new ArrayList<TaskListHome>();
			
			if(empId!=0 && fromDate!=null && toDate!=null && service!=0 && activity!=0 && custId!=0){
				
				taskList = taskListRepo.getTaskList(empId, fromDate, toDate, service, activity, custId, statusIds);
			}
			
			/*else if(empId!=0 && fromDate!=null && toDate!=null && service!=0 && activity!=0) {
				taskList = taskListRepo.getTaskList(empId, fromDate, toDate, service, activity);
			}
			else if(empId!=0 && fromDate!=null && toDate!=null) {
				taskList = taskListRepo.getTaskList(empId, fromDate, toDate);
				System.err.println("getTaskList(empId, fromDate, toDate)");
			}*/
			else if(empId!=0) {
				taskList = taskListRepo.getTaskList(empId, statusIds);
				System.err.println("getTaskList(empId)");
			}
			
		}catch (Exception e) {
			System.err.println("Exce in getTaskListByFilters  " + e.getMessage());
			e.printStackTrace();
		}
		
		return taskList;
	
	}
	
	
	
	/*******************************Status Master********************************/
	
	@Autowired StatusMasterRepo statusMstrRepo;
	@RequestMapping(value = { "/saveStatus" }, method = RequestMethod.POST)
	public @ResponseBody StatusMaster saveStatus(@RequestBody StatusMaster status) {

		StatusMaster stat = null;

		try {
			stat = statusMstrRepo.saveAndFlush(status);

		} catch (Exception e) {
			System.err.println("Exce in saving saveStatus " + e.getMessage());
			e.printStackTrace();

		}
		return stat;
	}
	
	@RequestMapping(value = {"/getAllStatus"}, method = RequestMethod.GET)
	public @ResponseBody List<StatusMaster> getAllStatus() {
		List<StatusMaster> statusList = new ArrayList<StatusMaster>();
		try {
			statusList = statusMstrRepo.findAllByDelStatusAndIsEditableOrderByStatusMstIdDesc(1, 1);
		}catch(Exception e) {
			System.err.println("Exce in getAllStatus " + e.getMessage());
		}
		return statusList;
	}
	
	@RequestMapping(value = {"/getStatusById"}, method = RequestMethod.POST)
	public @ResponseBody StatusMaster getStatusById(@RequestParam int statusId) {
		StatusMaster status = new StatusMaster();
		try {
			status = statusMstrRepo.findByStatusMstId(statusId);
		}catch(Exception e) {
			System.err.println("Exce in getStatusById " + e.getMessage());
		}
		return status;
	}
	
	
	@RequestMapping(value = {"/getMaxStatusValue"}, method = RequestMethod.GET)
	public @ResponseBody int getMaxStatusValue() {
		int maxStatus = 0;
		try {
			maxStatus = statusMstrRepo.getMaxStateValue();
		}catch(Exception e) {
			System.err.println("Exce in getMaxStatusValue " + e.getMessage());
		}
		return maxStatus;
	}
	
	@RequestMapping(value = { "/deleteStatusById" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteStatusById(@RequestParam int statusId, @RequestParam int userId ) {

		Info info = new Info();
		try
		{
			int res = statusMstrRepo.deleteStatusById(statusId, userId);

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
	
	@RequestMapping(value = {"/getStatusByEmpTypeIds"}, method = RequestMethod.POST)
	public @ResponseBody List<StatusMaster> getStatusByEmpIds(@RequestParam int empType) {
		List<StatusMaster> statusList = new ArrayList<StatusMaster>();
		try {
			statusList = statusMstrRepo.getStatusByEmpType(empType);
		}catch(Exception e) {
			System.err.println("Exce in getStatusByEmpIds " + e.getMessage());
		}
		return statusList;
	}
	
	
	/**************************Communication & Log***************************/
	@RequestMapping(value = {"/getTaskByTaskId"}, method = RequestMethod.POST)
	public @ResponseBody TaskListHome getTaskByTaskId(@RequestParam int empType, @RequestParam int taskId) {
		TaskListHome task = new TaskListHome();
		try {
			task = taskListRepo.getTaskById(empType, taskId);
		}catch(Exception e) {
			System.err.println("Exce in getStatusByEmpIds " + e.getMessage());
		}
		return task;
	}
	
	@Autowired TotalWorkHrsRepo workHrsRepo;
	
	@RequestMapping(value = {"/getEmpWorkHrsByEmpId"}, method = RequestMethod.POST)
	public @ResponseBody List<TotalWorkHrs> getEmpWorkHrsByEmpId(@RequestParam int taskId) {
		List<TotalWorkHrs> hrs = new ArrayList<TotalWorkHrs>();
		try {
			hrs = workHrsRepo.getEmpTtlWorkHrs(taskId);
		}catch(Exception e) {
			System.err.println("Exce in getEmpWorkHrsByEmpId " + e.getMessage());
		}
		return hrs;
	}
	
	/**************Active Deactive*************/
@Autowired TaskRepo taskRepo;
	
	@RequestMapping(value = { "/updateCustomerIsActiveStatus" }, method = RequestMethod.POST)
	public @ResponseBody Info updateServiceIsActiveStatus(@RequestParam("custId") int custId,
			@RequestParam("isActiveStatus") int isActiveStatus, @RequestParam("taskIds") List<Integer> taskIds) {

		Info info = new Info();

		try {

			int update = custHeadRepo.updateIsActiveStatus(custId,isActiveStatus);	
			
			update = taskRepo.updateIsActiveStatus(taskIds,isActiveStatus);
			
			info.setError(false);
			info.setMessage("successfully");
			
		} catch (Exception e) {
			e.printStackTrace();
			
			info.setError(true);
			info.setMessage("failed");
		}
		
		return info;
	}
	
	
	@RequestMapping(value = {"/getDevPerodicityById"}, method = RequestMethod.POST)
	public @ResponseBody DevPeriodicityMaster getDevPerodicityById(@RequestParam int periodicityId) {
		DevPeriodicityMaster period = new DevPeriodicityMaster();
		try {
 			period = devPeriodRepo.findByPeriodicityIdAndDelStatus(periodicityId, 1);

		}catch (Exception e) {
			System.err.println("Exce in getTaskPerodicityId" + e.getMessage());
		}
		return period;
	}
	
}
