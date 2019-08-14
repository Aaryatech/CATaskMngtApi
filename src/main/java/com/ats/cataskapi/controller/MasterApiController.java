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

import com.ats.cataskapi.model.ActivityMaster;
import com.ats.cataskapi.model.CustomerDetailMaster;
import com.ats.cataskapi.model.CustomerGroupMaster;
import com.ats.cataskapi.model.CustomerHeaderMaster;
import com.ats.cataskapi.model.DevPeriodicityMaster;
import com.ats.cataskapi.model.EmployeeMaster;
import com.ats.cataskapi.model.Info;
import com.ats.cataskapi.model.ServiceMaster;
import com.ats.cataskapi.model.TaskPeriodicityMaster;
import com.ats.cataskapi.repositories.ActivityMasterRepo;
import com.ats.cataskapi.repositories.CustomerDetailMasterRepo;
import com.ats.cataskapi.repositories.CustomerGroupMasterRepo;
import com.ats.cataskapi.repositories.CustomerHeaderMasterRepo;
import com.ats.cataskapi.repositories.DevPeriodicityMasterRepo;
import com.ats.cataskapi.repositories.EmployeeMasterRepo;
import com.ats.cataskapi.repositories.ServiceMasterRepo;
import com.ats.cataskapi.repositories.TaskPeriodicityMasterRepo;

@RestController
public class MasterApiController {	
	
	/******************ServiceMaster*******************/
	@Autowired ServiceMasterRepo srvMstrRepo;
	@RequestMapping(value = { "/saveService" }, method = RequestMethod.POST)
	public @ResponseBody ServiceMaster saveInstitute(@RequestBody ServiceMaster service) {

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
			servicsList = srvMstrRepo.findByDelStatus(1);
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
	
	@RequestMapping(value = {"/getActivityDetails"}, method=RequestMethod.POST)
	public @ResponseBody List<ActivityMaster>  getActivityDetails(@RequestParam int serviceId){
		List<ActivityMaster> activityDetailList = new ArrayList<ActivityMaster>();
		try {
			activityDetailList = actvtMstrRepo.getAllActivityDetailsList(serviceId);
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
			empList = empRepo.findAllBydelStatus(1);
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
	
	/*********************Customer Group Master************************/
	@Autowired CustomerGroupMasterRepo cstmrGrpRepo;
	
	@RequestMapping(value = {"/getAllCustomerGroups"}, method = RequestMethod.GET)
	public @ResponseBody List<CustomerGroupMaster> getAllCustomerGroups(){
		
		List<CustomerGroupMaster> cstmrGrpList = new ArrayList<CustomerGroupMaster>();
		try {
			cstmrGrpList = cstmrGrpRepo.findAllByDelStatus(1);
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
			custHeadList = custHeadRepo.findAllByDelStatus(1);
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
}
