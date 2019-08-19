package com.ats.cataskapi.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.ats.cataskapi.common.PeriodicityDates;
import com.ats.cataskapi.model.ActivityMaster;
import com.ats.cataskapi.model.CustmrActivityMap;
import com.ats.cataskapi.model.DevPeriodicityMaster;
import com.ats.cataskapi.model.FinancialYear;

import com.ats.cataskapi.repositories.ActivityMasterRepo;
import com.ats.cataskapi.repositories.CustmrActivityMapRepo;
import com.ats.cataskapi.repositories.DevPeriodicityMasterRepo;
import com.ats.cataskapi.repositories.FinancialYearRepo;

import com.ats.cataskapi.task.model.Task;
import com.ats.cataskapi.task.repo.TaskRepo;

@RestController
public class TaskApiController {

//Harsha Date:19 Aug 2019
	@Autowired
	TaskRepo taskRepo;

	@Autowired
	CustmrActivityMapRepo actMapRepo;

	@RequestMapping(value = { "/saveTask" }, method = RequestMethod.POST)
	public @ResponseBody List<String> saveTask(@RequestParam String strDate1, @RequestParam String endDate1,
			@RequestParam int perId) {

		List<String> listDate = PeriodicityDates.getDates(strDate1, endDate1, perId);
		int totdays = listDate.size();
		Date date = new Date();
		System.out.println("after fun call**" + listDate.toString());

		return listDate;
	}

	@Autowired
	ActivityMasterRepo actvtMstrRepo;
	@Autowired
	DevPeriodicityMasterRepo devPeriodRepo;
	@Autowired
	FinancialYearRepo financialYearRepo;

	@RequestMapping(value = { "/saveTask1" }, method = RequestMethod.POST)
	public @ResponseBody CustmrActivityMap saveCustSignatory(@RequestBody CustmrActivityMap custact) {

		Date date = Calendar.getInstance().getTime();
		Date dm = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

		int perId = 0;
		Task serv = null;
		int totdays = 0;

		CustmrActivityMap custserv = null;

		ActivityMaster actv = new ActivityMaster();

		actv = actvtMstrRepo.findByActiIdAndDelStatus(custserv.getActvId(), 1);

		DevPeriodicityMaster period = new DevPeriodicityMaster();
		period = devPeriodRepo.findByPeriodicityIdAndDelStatus(custserv.getPeriodicityId(), 1);
		
		try {
 			custserv = actMapRepo.saveAndFlush(custact);
			perId = custserv.getPeriodicityId();

			String strDate = dateFormat.format(custserv.getActvStartDate());
			System.out.println("Converted String: " + strDate);
			String endDate = dateFormat.format(custserv.getActvEndDate());
			System.out.println("Converted String: " + endDate);
			List<String> listDate = PeriodicityDates.getDates(strDate, endDate, perId);
			totdays = listDate.size();
			System.out.println("after fun call**" + listDate.toString());

			for (int i = 0; i < totdays; i++) {
			 
				Task task = new Task();

				Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(listDate.get(i));
				date.setTime(date1.getTime() + 1000 * 60 * 60 * 24 * 10);
				task.setTaskStatutoryDueDate(dateFormat.format(date));

				dm.setTime(date1.getTime() - 1000 * 60 * 60 * 24 * 30);
				task.setTaskStartDate(dateFormat.format(dm));

				StringBuilder sb1 = new StringBuilder(actv.getActiName());

				sb1.append(period.getPeriodicityName()).append(i);
				
				FinancialYear fin=new FinancialYear();
				fin=financialYearRepo.getFinYearBetDate(String.valueOf(date1));

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

				task.setMngrBudHr(custserv.getActvManBudgHr());
				task.setServId(actv.getServId());
				task.setTaskCode("NA");
				task.setTaskEmpIds("0");
				task.setTaskFyId(fin.getFinYearId());
				task.setTaskEndDate(dateFormat.format(date));
				task.setTaskStatus(0);
				task.setTaskSubline("NA");
				task.setTaskText(String.valueOf(sb1));
				task.setUpdateDatetime(dateFormat.format(date));
				task.setUpdateUsername(custserv.getUpdateUsername());

				serv = taskRepo.saveAndFlush(task);

			}

		} catch (Exception e) {

			System.err.println("Exce in saving saveTask " + e.getMessage());
			e.printStackTrace();

		}
		return custserv;
	}

}
