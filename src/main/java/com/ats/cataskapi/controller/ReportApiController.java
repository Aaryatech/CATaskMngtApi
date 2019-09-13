package com.ats.cataskapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.model.report.CompletedTaskReport;
import com.ats.cataskapi.report.repo.CompletedTaskReportRepo;

@RestController
public class ReportApiController {
	
	
	  @Autowired CompletedTaskReportRepo completedTaskReportRepo;
	  
	  @RequestMapping(value = {"/getCompletedTaskReport"},
	  method=RequestMethod.POST) public @ResponseBody List<CompletedTaskReport>
	  getCompletedTaskReport(@RequestParam String fromDate,@RequestParam String
	  toDate ){ List<CompletedTaskReport> logList = new
	  ArrayList<CompletedTaskReport>(); fromDate.concat("  00:00:01");
	  toDate.concat("  23:59:59");
	  
	  try { logList = completedTaskReportRepo.getAllCompletedTask(fromDate,
	  toDate);
	  
	  }catch (Exception e) {
	  System.out.println("Excep in getAllDailyWorkLogs : "+e.getMessage()); }
	  return logList;
	  
	  }
	 
	
	
	
	

}
