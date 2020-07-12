package com.ats.cataskapi.controller;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.common.DateConvertor;
import com.ats.cataskapi.model.EmployeeListWithAvailableHours;
import com.ats.cataskapi.model.EmployeeMaster;
import com.ats.cataskapi.model.LeaveApply;
import com.ats.cataskapi.model.reportv2.ComplTaskVarienceRep;
import com.ats.cataskapi.model.reportv2.OverDueTaskReport;
import com.ats.cataskapi.model.reportv2.VarianceReportByManger;
import com.ats.cataskapi.model.reportv2.WorkLofForReport;
import com.ats.cataskapi.model.reportv2.WorkLogDetailReport;
import com.ats.cataskapi.model.reportv2.WorkLogReportBetDates;
import com.ats.cataskapi.model.reportv2.WorkLogSub;
import com.ats.cataskapi.report.repo.OverDueTaskReportRepo;
import com.ats.cataskapi.report.repo.VarianceReportByMangerRepo;
import com.ats.cataskapi.report.repo.WorkLofForReportRepo;
import com.ats.cataskapi.report.repo.WorkLogDetailReportRepo;
import com.ats.cataskapi.repositories.EmployeeListWithAvailableHoursRepo;
import com.ats.cataskapi.repositories.EmployeeMasterRepo;
import com.ats.cataskapi.repositories.LeaveApplyRepository;

@RestController
public class ReportContV2 {

	@Autowired
	OverDueTaskReportRepo overDueAndWorkDiaryRepo;

	@RequestMapping(value = { "/getOverDueOrWorkDiaryEmpFdTd" }, method = RequestMethod.POST)
	public @ResponseBody List<OverDueTaskReport> getOverDueOrWorkDiaryEmpFdTd(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String empIds, @RequestParam int reportType) {
		List<OverDueTaskReport> taskReportList = new ArrayList<OverDueTaskReport>();

		String fromDate1 = DateConvertor.convertToYMD(fromDate);
		String toDate1 = DateConvertor.convertToYMD(toDate);

		try {
			if (reportType == 1) {
				System.err.println("A");
				taskReportList = overDueAndWorkDiaryRepo.getEmpOverDueTaskBetFdTd(fromDate1, toDate1, empIds);
			} else {
				System.err.println("B");
				taskReportList = overDueAndWorkDiaryRepo.getEmpWorkDiaryBetDate(fromDate1, toDate1, empIds);
			}
		} catch (Exception e) {
			System.out.println("Excep in getComplTaskVarienceReport : " + e.getMessage());
		}
		return taskReportList;
	}

	@Autowired
	WorkLogDetailReportRepo workLogDetailReportRepo;

	@RequestMapping(value = { "/getTaskLogDetailByTaskEmpId" }, method = RequestMethod.POST)
	public @ResponseBody List<WorkLogDetailReport> getTaskLogEmpInfoListByTaskId(@RequestParam int taskId,
			@RequestParam int empId) {
		System.err.println("Hi");
		List<WorkLogDetailReport> logList = new ArrayList<WorkLogDetailReport>();
		try {
			logList = workLogDetailReportRepo.getDetailWorkLogByEmpTaskId(taskId, empId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return logList;
	}

	@Autowired
	VarianceReportByMangerRepo varianceReportByMangerRepo;

	@RequestMapping(value = { "/getVarianceReportByManagerReport" }, method = RequestMethod.POST)
	public @ResponseBody List<VarianceReportByManger> getVarianceReportByManagerReport(@RequestParam int custId,
			@RequestParam int servId, @RequestParam int actId, @RequestParam int empId) {
		List<VarianceReportByManger> taskReportList = new ArrayList<VarianceReportByManger>();

		try {
			if (custId == 0) {
				taskReportList = varianceReportByMangerRepo.getAllCustVarianceByManager(servId, actId, empId);
			} else {
				taskReportList = varianceReportByMangerRepo.getVarianceByManager(servId, actId, empId, custId);
			}
		} catch (Exception e) {
			System.out.println("Excep in getComplTaskVarienceReport : " + e.getMessage());
		}
		return taskReportList;
	}

	@Autowired
	EmployeeMasterRepo employeeMasterRepo;

	@Autowired
	WorkLofForReportRepo workLofForReportRepo;
	@Autowired
	EmployeeListWithAvailableHoursRepo empListForLeave;
	@Autowired
	LeaveApplyRepository leaveApplyRepository;

	@RequestMapping(value = { "/getWorkDetBetDatesReport" }, method = RequestMethod.POST)
	public @ResponseBody WorkLogReportBetDates getWorkDetBetDatesReport(@RequestParam String startDate,
			String endDate) {

		WorkLogReportBetDates list = new WorkLogReportBetDates();
		List<WorkLogSub> logList = new ArrayList<WorkLogSub>();
		List<EmployeeMaster> empList = new ArrayList<EmployeeMaster>();
		List<LocalDate> totalDates = new ArrayList<>();
		List<WorkLofForReport> wkList = new ArrayList<WorkLofForReport>();

		try {
			empList = employeeMasterRepo.getAllMangAndEmp();
 			wkList = workLofForReportRepo.getworklog(startDate, endDate);
			List<LeaveApply> leaveList = leaveApplyRepository.getAllLeaves();

			System.err.println("leaveList" + leaveList.toString());

			list.setEmpList(empList);
			LocalDate start = LocalDate.parse(startDate);
			LocalDate end = LocalDate.parse(endDate);
			while (!start.isAfter(end)) {
				totalDates.add(start);
				start = start.plusDays(1);
			}

			SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
			// Main data setting

			for (int i = 0; i < empList.size(); i++) {
 
				int empId = empList.get(i).getEmpId();
				 
				List<String> tempLogList = new ArrayList<String>();
				WorkLogSub subRec = new WorkLogSub();
				subRec.setEmpId(empId);

				for (int j = 0; j < totalDates.size(); j++) {

 
					String tempLog = "00.00";
					String currDate = String.valueOf(totalDates.get(j));

					Date d1 = sdformat.parse(currDate);
					int flag = 0;
					for (int k = 0; k < wkList.size(); k++) {

						WorkLofForReport wk = wkList.get(k);
						 

						if (currDate.equals(wk.getWorkDate()) && empId == wk.getEmpId()
								&& ((wk.getWorkHr() != "0" || wk.getWorkHr() != ""))) {
							tempLog = wk.getWorkHr();
							flag = 1;
							break;
						}

					}
					if (flag == 0) {
						/*
						 * System.err.println("no work log:" + empId + "--" + currDate);
						 */
						/*
						 * List<EmployeeListWithAvailableHours> empLeave = empListForLeave
						 * .getLeaveRecordByEmpIdSac(currDate, currDate, empId);
						 * 
						 * if (empLeave.size() > 0) { tempLog = "Leave";
						 * 
						 * }
						 */
 
						for (int l = 0; l < leaveList.size(); l++) {

							LeaveApply leeave = leaveList.get(l);
							Date frmDate = sdformat.parse(leeave.getLeaveFromdt());
							Date toDate = sdformat.parse(leeave.getLeaveTodt());
							
							
							if (leeave.getEmpId() == empId
									&& ((frmDate.compareTo(d1) == 0) || (toDate.compareTo(d1) == 0)
											|| ((d1.compareTo(frmDate) > 0) && (d1.compareTo(toDate) < 0)))) {

 
  								tempLog = "Leave";
								break;
							}

						}

					}
					tempLogList.add(tempLog);

				}
				subRec.setStatus(tempLogList);

				logList.add(subRec);
				list.setLogList(logList);

			}

		} catch (Exception e) {
			System.out.println("Excep in getComplTaskVarienceReport : " + e.getMessage());
		}
		return list;
	}

}
