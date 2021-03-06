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

import com.ats.cataskapi.model.DailyWorkLog;
import com.ats.cataskapi.model.Info;
import com.ats.cataskapi.model.PerDayWorkLog;
import com.ats.cataskapi.repositories.DailyWorkLogRepo;
import com.ats.cataskapi.repositories.PerDayWorkLogRepo;

@RestController
public class DailyWorkLogApiController {

	@Autowired DailyWorkLogRepo workLogRepo;
	
	@RequestMapping(value = {"/getAllDailyWorkLogs"}, method=RequestMethod.POST)
	public @ResponseBody List<DailyWorkLog> getAllDailyWorkLogs(@RequestParam int taskId ){
		List<DailyWorkLog> logList = new ArrayList<DailyWorkLog>();
		try {
			logList = workLogRepo.findByDelStatusAndTaskId(taskId);
			System.err.println("logList---------"+logList);
		}catch (Exception e) {
			System.out.println("Excep in getAllDailyWorkLogs : "+e.getMessage());
		}
		return logList;
		
	}
	
	@RequestMapping(value = {"/addNewWorkLog"}, method=RequestMethod.POST)
	public @ResponseBody DailyWorkLog addNewWorkLog(@RequestBody DailyWorkLog workLog){
		DailyWorkLog log = new DailyWorkLog();
		try {
			log = workLogRepo.saveAndFlush(workLog);
			
		}catch (Exception e) {
			System.out.println("Excep in addNewWorkLog : "+e.getMessage());
		}
		return log;
		
	}
	
	
	
	@RequestMapping(value = {"/addNewWorkLogNew"}, method=RequestMethod.POST)
	public @ResponseBody Info addNewWorkLogNew(@RequestBody DailyWorkLog workLog){
		DailyWorkLog log = new DailyWorkLog();
		Info info=new Info();
		try {
			log = workLogRepo.saveAndFlush(workLog);
			
			if(log==null) {
				info.setError(true);
				info.setMsg("Failed to Add thr Record .. Try Again");

			}else {
				info.setError(false);
				info.setMsg("Added Successfully");

			}
		}catch (Exception e) {
			System.out.println("Excep in addNewWorkLog : "+e.getMessage());
		}
		return info;
		
	}
	@RequestMapping(value = {"/addEmpWorkLogList"}, method=RequestMethod.POST)
	public @ResponseBody List<DailyWorkLog> addNewWorkLog(@RequestBody List<DailyWorkLog> workLogList){
		List<DailyWorkLog> log = new ArrayList<DailyWorkLog>();
		try {
			log = workLogRepo.saveAll(workLogList);
			
		}catch (Exception e) {
			System.out.println("Excep in addNewWorkLog : "+e.getMessage());
		}
		return log;
		
	}
	@RequestMapping(value = {"/getWorkLogById"}, method=RequestMethod.POST)
	public @ResponseBody DailyWorkLog addNewWorkLog(@RequestParam int logId){
		DailyWorkLog log = new DailyWorkLog();
		try {
			log = workLogRepo.findByWorkLogId(logId);
			
		}catch (Exception e) {
			System.out.println("Excep in getWorkLogById : "+e.getMessage());
		}
		return log;
		
	}
	
	@RequestMapping(value = {"/getWorkLogHrsById"}, method=RequestMethod.POST)
	public @ResponseBody DailyWorkLog getWorkLogHrsById(@RequestParam int logId){
		DailyWorkLog log = new DailyWorkLog();
		try {
			log = workLogRepo.findByWorkHrsLogId(logId);
			
		}catch (Exception e) {
			System.out.println("Excep in getWorkLogHrsById : "+e.getMessage());
		}
		return log;
		
	}
	
	@RequestMapping(value = { "/deleteWorkLogById" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteWorkLogById(@RequestParam int logId, @RequestParam int userId) {

		Info info = new Info();
		try
		{
			int res = workLogRepo.deleteworkLogId(logId, userId);

			if (res > 0) {
				info.setError(false);
				info.setMsg("success");

			} else {
				info.setError(true);
				info.setMsg("failed");

			}
		} catch (Exception e) {

			System.err.println("Exce in deleteWorkLogById  " + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}

		return info;

	}
	
	@Autowired PerDayWorkLogRepo perDayWrkLogRepo;
	@RequestMapping(value = {"/getPerDayWorkLogs"}, method=RequestMethod.POST)
	public @ResponseBody List<PerDayWorkLog> getPerDayWorkLogs(@RequestParam int taskId ){
		List<PerDayWorkLog> dayLog = new ArrayList<PerDayWorkLog>();
		try {
			dayLog = perDayWrkLogRepo.getPerDayLogByTaskId(taskId);
			System.err.println("dayLog---------"+dayLog);
		}catch (Exception e) {
			System.out.println("Excep in getPerDayWorkLogs : "+e.getMessage());
		}
		return dayLog;
		
	}
}
