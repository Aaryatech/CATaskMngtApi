package com.ats.cataskapi.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.common.DateConvertor;
import com.ats.cataskapi.model.CalenderYear;
import com.ats.cataskapi.model.GetHoliday;
import com.ats.cataskapi.model.Holiday;
import com.ats.cataskapi.model.Info;
import com.ats.cataskapi.model.LeaveApply;
import com.ats.cataskapi.model.LeaveDetail;
import com.ats.cataskapi.repositories.CalculateYearRepository;
import com.ats.cataskapi.repositories.GetHolidayRepo;
import com.ats.cataskapi.repositories.HolidayRepo;
import com.ats.cataskapi.repositories.LeaveApplyRepository;
import com.ats.cataskapi.repositories.LeaveDetailRepo;

@RestController
public class LeaveHolidayApiCon {

	@Autowired
	HolidayRepo holidayRepo;

	@Autowired
	GetHolidayRepo getHolidayRepo;

	@Autowired
	CalculateYearRepository calculateYearRepository;

	@Autowired
	LeaveApplyRepository leaveApplyRepository;

	@Autowired
	LeaveDetailRepo leaveDetailRepo;

	// -----------Holiday-----------------------
	@RequestMapping(value = { "/saveHoliday" }, method = RequestMethod.POST)
	public @ResponseBody Holiday saveHoliday(@RequestBody Holiday holiday) {

		Holiday save = new Holiday();
		try {

			save = holidayRepo.saveAndFlush(holiday);

			if (save != null) {
				save.setError(false);
			} else {

				save = new Holiday();
				save.setError(true);
			}

		} catch (Exception e) {
			save = new Holiday();
			save.setError(true);
			e.printStackTrace();
		}

		return save;

	}

	@RequestMapping(value = { "/getHolidayList" }, method = RequestMethod.POST)
	public @ResponseBody List<GetHoliday> getHolidayList(@RequestParam("companyId") int companyId) {

		List<GetHoliday> list = new ArrayList<GetHoliday>();
		try {

			list = getHolidayRepo.getHolidayListByCompany(companyId);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;

	}

	@RequestMapping(value = { "/getHolidayById" }, method = RequestMethod.POST)
	public @ResponseBody Holiday getHolidayById(@RequestParam("holidayId") int holidayId) {

		Holiday holiday = new Holiday();
		try {

			holiday = holidayRepo.findByHolidayIdAndDelStatus(holidayId, 1);
			/*
			 * holiday.setHolidayFromdt(DateConvertor.convertToDMY(holiday.getHolidayFromdt(
			 * )));
			 * holiday.setHolidayTodt(DateConvertor.convertToDMY(holiday.getHolidayTodt()));
			 */

		} catch (Exception e) {

			e.printStackTrace();
		}

		return holiday;

	}

	@RequestMapping(value = { "/deleteHoliday" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteHoliday(@RequestParam("holidayId") int holidayId) {

		Info info = new Info();

		try {
			int recordCount = 0;
			Holiday holiDay = holidayRepo.getOne(holidayId);
			Date curDate=new Date();
			Date holFdate=null;
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			holFdate=df.parse(holiDay.getHolidayFromdt());
			
			System.err.println("curDate "+curDate +" holFdate " +holFdate);
			
			
			recordCount = leaveApplyRepository.getCountofLeaveFdTd(holiDay.getHolidayFromdt(),
					holiDay.getHolidayTodt());
			int delete = 0;
			if(curDate.before(holFdate)) {
				//allow to delete
				recordCount=0;
							}else {
								recordCount=1;	
							}
			
			if (recordCount < 1)
				delete = holidayRepo.deleteHoliday(holidayId);

			if (delete > 0) {
				info.setError(false);
				info.setMsg("deleted");
			} else {
				info.setError(true);
				info.setMsg("failed");
			}

		} catch (Exception e) {

			e.printStackTrace();
			info.setError(true);
			info.setMsg("failed");
		}

		return info;

	}

	@RequestMapping(value = { "/getCalculateYearListIsCurrent" }, method = RequestMethod.GET)
	public @ResponseBody CalenderYear getCalculateYearListIsCurrent() {

		CalenderYear calendearYear = new CalenderYear();
		try {

			calendearYear = calculateYearRepository.findByIsCurrent(1);

			System.out.println(calendearYear);
			calendearYear.setCalYrFromDate(DateConvertor.convertToDMY(calendearYear.getCalYrFromDate()));
			calendearYear.setCalYrToDate(DateConvertor.convertToDMY(calendearYear.getCalYrToDate()));
			System.out.println(calendearYear);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return calendearYear;

	}

	@RequestMapping(value = { "/saveLeaveApply" }, method = RequestMethod.POST)
	public @ResponseBody LeaveApply saveLeaveApply(@RequestBody LeaveApply leave) {

		LeaveApply save = new LeaveApply();
		try {

			save = leaveApplyRepository.saveAndFlush(leave);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return save;

	}

	@RequestMapping(value = { "/deleteLeaveApply" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteLeaveApply(@RequestParam("leaveId") int leaveId) {

		Info info = new Info();

		try {

			LeaveApply leave=leaveApplyRepository.findByLeaveIdAndDelStatus(leaveId,1);
			
			Date curDate=new Date();
			Date leaveFrDate=null;
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			leaveFrDate=df.parse(leave.getLeaveFromdt());
			
			//System.err.println("curDate "+curDate +" leaveFrDate " +leaveFrDate);
			int delete=0;
			/*if(leaveFrDate.before(curDate)) {
//Dont allow 
				//System.err.println("Dont allow del leave");
			}else {*/
				delete = leaveApplyRepository.deleteLeaveApply(leaveId);
		//	}
					
			//int delete = leaveApplyRepository.deleteLeaveApply(leaveId);

			if (delete > 0) {
				info.setError(false);
				info.setMessage("successfull");
			} else {
				info.setError(true);
				info.setMessage("error");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return info;

	}
	
	@RequestMapping(value = { "/updateLeaveApply" }, method = RequestMethod.POST)
	public @ResponseBody Info updateLeaveApply(@RequestParam("leaveId") int leaveId,@RequestParam("noOfDays") float noOfDays,@RequestParam("toDate") String toDate) {

		Info info = new Info();

		try {

 			 
 			int delete=0;
 				delete = leaveApplyRepository.updateLeaveApply(leaveId,noOfDays,toDate);
	 

			if (delete > 0) {
				info.setError(false);
				info.setMessage("successfull");
			} else {
				info.setError(true);
				info.setMessage("error");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return info;

	}

	@RequestMapping(value = { "getLeaveListByEmp" }, method = RequestMethod.POST)
	public @ResponseBody List<LeaveDetail> getLeaveListByLocIdAndEmp(@RequestParam("empId") int empId) {

		List<LeaveDetail> employeeInfo = new ArrayList<LeaveDetail>();

		try {

			employeeInfo = leaveDetailRepo.getLeaveListByEmp(empId);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return employeeInfo;

	}

}
