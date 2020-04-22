package com.ats.cataskapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.common.DateConvertor;
import com.ats.cataskapi.model.reportv2.ComplTaskVarienceRep;
import com.ats.cataskapi.model.reportv2.OverDueTaskReport;
import com.ats.cataskapi.model.reportv2.WorkLogDetailReport;
import com.ats.cataskapi.report.repo.OverDueTaskReportRepo;
import com.ats.cataskapi.report.repo.WorkLogDetailReportRepo;

@RestController
public class ReportContV2 {

	@Autowired 
	OverDueTaskReportRepo overDueAndWorkDiaryRepo;
	
	
	@RequestMapping(value = { "/getOverDueOrWorkDiaryEmpFdTd" }, method = RequestMethod.POST)
	public @ResponseBody List<OverDueTaskReport> getOverDueOrWorkDiaryEmpFdTd(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String empIds,@RequestParam int reportType) {
		List<OverDueTaskReport> taskReportList = new ArrayList<OverDueTaskReport>();
		
		String fromDate1 = DateConvertor.convertToYMD(fromDate);
		String toDate1 = DateConvertor.convertToYMD(toDate);

		try {
			if(reportType==1) {
				System.err.println("A");
				taskReportList = overDueAndWorkDiaryRepo.getEmpOverDueTaskBetFdTd(fromDate1, toDate1, empIds);
			}else   {
				System.err.println("B");
			taskReportList = overDueAndWorkDiaryRepo.getEmpWorkDiaryBetDate(fromDate1, toDate1, empIds);
			}
		} catch (Exception e) {
			System.out.println("Excep in getComplTaskVarienceReport : " + e.getMessage());
		}
		return taskReportList;
	}
	
	@Autowired WorkLogDetailReportRepo workLogDetailReportRepo;
	
	@RequestMapping(value = { "/getTaskLogEmpInfoListByTaskId" }, method = RequestMethod.POST)
	public @ResponseBody List<WorkLogDetailReport> getTaskLogEmpInfoListByTaskId(@RequestParam int taskId ,
			@RequestParam int  empId) {
		List<WorkLogDetailReport> logList = new ArrayList<WorkLogDetailReport>();
		try {
			logList=workLogDetailReportRepo.getDetailWorkLogByEmpTaskId(taskId, empId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return logList;
	}
}
