package com.ats.cataskapi.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.common.DateConvertor;
import com.ats.cataskapi.common.DateValues;
import com.ats.cataskapi.common.PerDatesAdmin;
import com.ats.cataskapi.common.PeriodicityDates;
import com.ats.cataskapi.model.ActivityMaster;
import com.ats.cataskapi.model.CustmrActivityMap;
import com.ats.cataskapi.model.FinancialYear;
import com.ats.cataskapi.model.GetWeeklyOff;
import com.ats.cataskapi.model.Info;
import com.ats.cataskapi.model.ServiceMaster;
import com.ats.cataskapi.repositories.ActivityMasterRepo;
import com.ats.cataskapi.repositories.CustmrActivityMapRepo;
import com.ats.cataskapi.repositories.FinancialYearRepo;
import com.ats.cataskapi.repositories.ServiceMasterRepo;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;

import ch.qos.logback.classic.pattern.DateConverter;

@RestController
public class YearlyActivityController {
	/*
	 * SELECT m_cust_acti_map.*,m_cust_header.cust_firm_name,
	 * m_activities.acti_name,dm_periodicity.periodicity_name, m_services.serv_name
	 * FROM m_cust_acti_map,m_cust_header,m_activities,dm_periodicity,m_services
	 * WHERE m_cust_acti_map.cust_id=m_cust_header.cust_id AND
	 * m_cust_acti_map.actv_id=m_activities.acti_id AND
	 * m_cust_acti_map.periodicity_id=dm_periodicity.periodicity_id AND
	 * m_activities.serv_id=m_services.serv_id
	 * 
	 */
	@Autowired
	JdbcTemplate jdbcTemp;
	@Autowired
	CustmrActivityMapRepo camap;

	

	@RequestMapping(value = { "/getNextFinYear" }, method = RequestMethod.GET)
	public @ResponseBody FinancialYear getNextFinYear() {
		FinancialYear fy = new FinancialYear();
		try {
			fy=financialYearRepo.getNextFinYear();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fy;

	}

	@RequestMapping(value = { "/getCustActMapListYearly" }, method = RequestMethod.GET)
	public @ResponseBody List<MappingData> getCustActMapList() {

		List<MappingData> list = new ArrayList<MappingData>();
		try {
			System.err.println("Start time  " + DateConvertor.getCurTime());
			String query = "SELECT m_cust_acti_map.*,CONCAT(FLOOR( m_cust_acti_map.actv_man_budg_hr/60),\n"
					+ "        ':',\n" + "        LPAD(MOD( m_cust_acti_map.actv_man_budg_hr,\n" + "        60),\n"
					+ "        2,\n" + "        '0')) as actv_man_budg_hr,\n"
					+ "        CONCAT(FLOOR( m_cust_acti_map.actv_emp_budg_hr/60),\n" + "        ':',\n"
					+ "        LPAD(MOD( m_cust_acti_map.actv_emp_budg_hr,\n" + "        60),\n" + "        2,\n"
					+ "        '0')) as actv_emp_budg_hr ,m_cust_header.cust_firm_name, m_activities.acti_name,dm_periodicity.periodicity_name, m_services.serv_name FROM m_cust_acti_map,m_cust_header,m_activities,dm_periodicity,m_services WHERE m_cust_acti_map.cust_id=m_cust_header.cust_id AND m_cust_acti_map.actv_id=m_activities.acti_id AND m_cust_acti_map.periodicity_id=dm_periodicity.periodicity_id AND m_activities.serv_id=m_services.serv_id and m_cust_acti_map.mapping_id!=0 and m_activities.del_status=1 and m_services.del_status=1  ORDER BY m_cust_header.cust_firm_name asc ";
			;
			list = jdbcTemp.query(query, new BeanPropertyRowMapper(MappingData.class));
			System.err.println("End time  " + DateConvertor.getCurTime());

		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;

	}

	@Autowired
	FinancialYearRepo financialYearRepo;
	@Autowired
	ServiceMasterRepo srvMstrRepo;

	@Autowired
	ActivityMasterRepo actvtMstrRepo;

	@RequestMapping(value = { "/genSaveYearlyTasks" }, method = RequestMethod.POST)
	public @ResponseBody String genSaveYearlyTasks(@RequestParam List<String> strMappingList) {
		System.err.println("Datetime Start " + DateConvertor.getCurDateTimeYmD());
		StringBuilder querySb = new StringBuilder();
		String finalInsertQuery = new String();
		try {
			int taskId = 0;
			try {
				taskId = camap.getMaxOfTTaskTemp();
			} catch (Exception e) {
				taskId = 0;
			}
			System.err.println("Task Id  Received Max" +taskId);
			strMappingList.remove("0");
			List<CustmrActivityMap> mapList = camap.getMappingForyearlyTaskGen(strMappingList);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String myString = "INSERT INTO t_tasks_temp1 (task_id, task_code, mapping_id, task_subline, task_fy_id, task_text, task_emp_ids, task_start_date, task_end_date, task_statutory_due_date, task_completion_date, billing_amt, task_status, mngr_bud_hr, emp_bud_hr, del_status, is_active, update_datetime, update_username, ex_int1, ex_int2, ex_var1, ex_var2, cust_id, periodicity_id, actv_id, serv_id) VALUES";
			querySb.append(myString);
			int userId = 0;
			FinancialYear fy = new FinancialYear();
			fy = financialYearRepo.getNextFinYear();
			List<ActivityMaster> activitsList = new ArrayList<ActivityMaster>();
			try {
				activitsList = actvtMstrRepo.findByDelStatus(1);
			} catch (Exception e) {
				System.err.println("Exce in getAllActivites  " + e.getMessage());
			}

			List<ServiceMaster> servicsList = new ArrayList<ServiceMaster>();
			try {
				servicsList = srvMstrRepo.findByDelStatusOrderByServIdDesc(1);
			} catch (Exception e) {
				System.err.println("Exce in getAllServices " + e.getMessage());
			}

			ActivityMaster actv = new ActivityMaster();// actvtMstrRepo.findByActiIdAndDelStatus(mapList.get(a).getActvId(),
														// 1);

			ServiceMaster servc = new ServiceMaster();// srvMstrRepo.findByServIdAndDelStatus(actv.getServId(), 1);

			List<FinancialYear> finYearList = financialYearRepo.findByDelStatus(1);

			// First For Loop
			for (int a = 0; a < mapList.size(); a++) {

				DateFormat dateFrmt = new SimpleDateFormat("yyyy-MM-dd");

				/*
				 * List<DateValues> listDate =
				 * PerDatesAdmin.getDates(dateFrmt.format(mapList.get(a).getActvStartDate()),
				 * dateFrmt.format(mapList.get(a).getActvEndDate()),
				 * mapList.get(a).getPeriodicityId());
				 */
				List<DateValues> listDate = PerDatesAdmin.getDates(fy.getFinStartDate(), fy.getFinEndDate(),
						mapList.get(a).getPeriodicityId());

				System.err.println("getMappingId() " + mapList.get(a).getMappingId());

				actv = new ActivityMaster();// actvtMstrRepo.findByActiIdAndDelStatus(mapList.get(a).getActvId(), 1);

				servc = new ServiceMaster();// srvMstrRepo.findByServIdAndDelStatus(actv.getServId(), 1);

				for (int x = 0; x < activitsList.size(); x++) {
					Integer result = Integer.compare(activitsList.get(x).getActiId(), mapList.get(a).getActvId());

					//
					// if (activitsList.get(x).getActiId() == mapList.get(a).getActvId()) {
					if (result.equals(0)) {
						actv = activitsList.get(x);
						break;
					}

				}
				// Integer.compare(servicsList.get(0).getServId(),actv.getServId());
				for (int y = 0; y < servicsList.size(); y++) {
					Integer result = Integer.compare(servicsList.get(y).getServId(), actv.getServId());
					// if ((servicsList.get(y).getServId()) == actv.getServId()) {
					if (result.equals(0)) {
						servc = servicsList.get(y);
						break;
					}

				}

				for (int i = 0; i < listDate.size(); i++) {
					taskId = taskId + 1;
					FinancialYear fin = new FinancialYear();
					// get Task Name
					StringBuilder taskText = new StringBuilder(servc.getServName());

					taskText.append("-").append(actv.getActiName()).append("-").append(listDate.get(i).getValue());

					// get Statu Due Date
					Date date1 = listDate.get(i).getDate();
					String statDueDate = PeriodicityDates.addDaysToGivenDate(dateFormat.format(date1),
							mapList.get(a).getActvStatutoryDays());
					// get Task Start Date ie statDueDate-30 days;
					String startDate = PeriodicityDates.addDaysToGivenDate(statDueDate, -30);
					// get End date ie work date by Default due date is work date
					// String workDate=statDueDate;

					// to get Fin Year Id
					/*
					 * FinancialYear fin = new FinancialYear(); try { // pass Statu due date to it
					 * fin = financialYearRepo.getFinYearBetDate(statDueDate); } catch (Exception e)
					 * { System.err.println("Exce in getFinYearBetDate" + e.getMessage()); }
					 */
					Date dateStatDueDate = null;
					dateStatDueDate = dateFormat.parse(statDueDate);
					for (int c = 0; c < finYearList.size(); c++) {
						Date sd = dateFormat.parse(finYearList.get(c).getFinStartDate());
						Date ed = dateFormat.parse(finYearList.get(c).getFinEndDate());

						if (dateStatDueDate.after(sd) && dateStatDueDate.before(ed)) {
							fin = finYearList.get(c);
							break;
						}
					}
					
					querySb.append("('"+taskId+"','na','" + mapList.get(a).getMappingId() + "','na',    '"
							+ fin.getFinYearId() + "', '" + String.valueOf(taskText) + "', '0', '" + startDate + "', '"
							+ statDueDate + "', '" + statDueDate + "' , NULL, " + "'"
							+ mapList.get(a).getActvBillingAmt() + "','0','" + mapList.get(a).getActvManBudgHr() + "','"
							+ mapList.get(a).getActvEmpBudgHr() + "','1','1','" + DateConvertor.getCurDateTimeYmD()
							+ "','" + userId + "', '0','0','na','na', '" + mapList.get(a).getCustId() + "'," + "'"
							+ mapList.get(a).getPeriodicityId() + "','" + mapList.get(a).getActvId() + "','0' ),");

					// '', '', '', '', '', '', '', NULL, '', NULL, NULL, '', '', '', '', '',
					// CURRENT_TIMESTAMP, '', '', '', '', '', '', '', '', ''
				}

				// System.err.println("querySb " +querySb.toString());

			} // end of mapList For Loop
			finalInsertQuery = querySb.toString().substring(0, querySb.toString().length() - 1);
			System.err.println("start insert " + DateConvertor.getCurDateTimeYmD());
			jdbcTemp.batchUpdate(finalInsertQuery);
			System.err.println("end insert " + DateConvertor.getCurDateTimeYmD());
			System.err.println("start insert2 " + DateConvertor.getCurDateTimeYmD());
			jdbcTemp.batchUpdate("insert into t_tasks_temp select * from t_tasks_temp1");
			System.err.println("end insert2 " + DateConvertor.getCurDateTimeYmD());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("Datetime End " + DateConvertor.getCurDateTimeYmD());

		return finalInsertQuery; // querySb.toString().substring(0, querySb.toString().length() - 1);

	}

	@RequestMapping(value = { "/saveEditeMappingTableByMappingId" }, method = RequestMethod.POST)
	public @ResponseBody Info saveEditeMappingTableByMappingId(@RequestParam int mappingId, @RequestParam int dueDays,
			@RequestParam int bilAmt, @RequestParam int emphr, @RequestParam int mngHr, @RequestParam int userId) {
		Info info = new Info();
		try {

			int result = 0;
			try {
				result = camap.updateCAM(mappingId, dueDays, bilAmt, emphr, mngHr, userId);

				if (result > 0) {
					info.setError(false);
				} else {
					info.setError(true);
				}
			} catch (Exception e) {
				info.setError(true);
			}
		} catch (Exception e) {
			info.setError(true);
		}
		return info;
	}
}
