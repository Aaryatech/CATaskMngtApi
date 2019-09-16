package com.ats.cataskapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.dashboard.repo.TlTaskCompletReportRepo;
import com.ats.cataskapi.model.report.TlTaskCompletReport;

@RestController
public class TlReportControler {

	@Autowired TlTaskCompletReportRepo tlRepo;
	
	@RequestMapping(value = {"/getTlCompletedTeskRepot"}, method=RequestMethod.POST)
	public@ResponseBody List<TlTaskCompletReport> getTlCompletedTeskRepot(@RequestParam String  fromDate, @RequestParam String  toDate){
		List<TlTaskCompletReport> tlList = new ArrayList<TlTaskCompletReport>();
		//fromDate.concat("  00:00:01");
		//  toDate.concat("  23:59:59");
		try
		{
			
			System.out.println("Dates--------"+fromDate+" to "+toDate);
			tlList=tlRepo.getTlCompleteTaskReport(fromDate, toDate);
		}catch (Exception e) {
		System.err.println("Exception in getTlCompletedTeskRepot : "+e.getMessage());
		e.printStackTrace();
		}
		return tlList;
		
	}
	
}
