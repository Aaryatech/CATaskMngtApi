package com.ats.cataskapi.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.model.ClientWiseTaskReport;
import com.ats.cataskapi.model.MonthWiseRateAndEmpActualHrs;
import com.ats.cataskapi.repositories.BugetedAmtAndRevenueRepo;
import com.ats.cataskapi.repositories.ClientWiseTaskReportRepository;
import com.ats.cataskapi.repositories.MonthWiseRateAndEmpActualHrsRepository;
import com.ats.cataskapi.task.model.EmpSalary;
import com.ats.cataskapi.task.repo.EmpSalaryRepo;

@RestController
public class ManagerReportRestApi {

	@Autowired
	ClientWiseTaskReportRepository clientWiseTaskReportRepository;

	@Autowired
	MonthWiseRateAndEmpActualHrsRepository monthWiseRateAndEmpActualHrsRepository;

	@Autowired
	EmpSalaryRepo empSalaryRepo;

	@Autowired
	BugetedAmtAndRevenueRepo bugetedAmtAndRevenueRepo;
	
	@RequestMapping(value = { "/clientWiseTaskReport" }, method = RequestMethod.POST)
	public @ResponseBody List<ClientWiseTaskReport> clientWiseTaskReport(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,  @RequestParam("groupId") int groupId,@RequestParam("clientId") int clientId,
			@RequestParam("yearId") int yearId, @RequestParam("rateType") int rateType) {

		List<ClientWiseTaskReport> list = new ArrayList<>();

		try {

			List<Integer> clntIds = new ArrayList<>();
			
			if(clientId==0) {
				clntIds = bugetedAmtAndRevenueRepo.getclientByGroupId(groupId);
			}else {
				clntIds.add(clientId);
			}
			
			list = clientWiseTaskReportRepository.getclientWiseTaskReport(fromDate, toDate, clntIds);

			String[] ids = {};
			String totalempIds = clientWiseTaskReportRepository.getEmpListByTaskId(fromDate, toDate, clntIds);
			ids = totalempIds.split(",");
			LinkedHashSet<String> hashSet = new LinkedHashSet<>(Arrays.asList(ids));
			ArrayList<String> arryids = new ArrayList<>(hashSet);

			List<MonthWiseRateAndEmpActualHrs> hrsList = monthWiseRateAndEmpActualHrsRepository.gethrsandsal(fromDate,
					toDate, clntIds, yearId, arryids);
			List<EmpSalary> salList = new ArrayList<>();
			
			if (rateType != 1) {
				salList = empSalaryRepo.getreocrdByempIdAndYearId(yearId, arryids);
			}

			System.out.println(hrsList);
			for (int i = 0; i < list.size(); i++) {

				try {

					String[] empIds = list.get(i).getTaskEmpIds().split(",");
					String[] employeeds = list.get(i).getEmployeeIds().split(",");
					String[] managerds = list.get(i).getMangerIds().split(",");

					float empBugHrs = employeeds.length * list.get(i).getEmpBudHr();
					float mngrBugHrs = managerds.length * list.get(i).getMngrBudHr();

					String empBugetedHrs = String.valueOf((int) (empBugHrs / 60) + "." + (int) (empBugHrs % 60));
					String mngrBugetedHrs = String.valueOf((int) (mngrBugHrs / 60) + "." + (int) (mngrBugHrs % 60));

					list.get(i).setEmpBugetedHrs(empBugetedHrs);
					list.get(i).setMngrBugetedHrs(mngrBugetedHrs);

					float empActlHrs = 0;
					float mngrActlHrs = 0;
					float tlActlHrs = 0;

					float empActlCost = 0;
					float mngrActlCost = 0;
					float tlActlCost = 0;

					for (int j = 0; j < hrsList.size(); j++) {

						if (hrsList.get(j).getTaskId() == list.get(i).getTasId()) {

							if (hrsList.get(j).getEmpType() == 5) {

								empActlHrs = empActlHrs + hrsList.get(j).getWorkedMin();

								if (rateType == 1) {

									int emptempHrs = (int) (hrsList.get(j).getWorkedMin() / 60);

									float actualRateMin = (hrsList.get(j).getSal() + 6000)
											/ hrsList.get(j).getTotalMinMonth();
									float actualRate = actualRateMin * 60;
									float remHrsValue = actualRateMin * (int) (hrsList.get(j).getWorkedMin() % 60);

									empActlCost = empActlCost + ((emptempHrs * actualRate) + remHrsValue);

								} else {

									int emptempHrs = (int) (hrsList.get(j).getWorkedMin() / 60);

									float actualRateMin = (hrsList.get(j).getSal() + 6000) / list.get(i).getEmpBudHr();
									float bugetedRate = actualRateMin * 60;
									float remHrsValue = actualRateMin * (int) (hrsList.get(j).getWorkedMin() % 60);

									empActlCost = empActlCost + ((emptempHrs * bugetedRate) + remHrsValue);

								}

							} else if (hrsList.get(j).getEmpType() == 3) {

								mngrActlHrs = mngrActlHrs + hrsList.get(j).getWorkedMin();

								if (rateType == 1) {

									int emptempHrs = (int) (hrsList.get(j).getWorkedMin() / 60);

									float actualRateMin = (hrsList.get(j).getSal() + 6000)
											/ hrsList.get(j).getTotalMinMonth();
									float actualRate = actualRateMin * 60;
									float remHrsValue = actualRateMin * (int) (hrsList.get(j).getWorkedMin() % 60);

									mngrActlCost = mngrActlCost + ((emptempHrs * actualRate) + remHrsValue);

								} else {

									int emptempHrs = (int) (hrsList.get(j).getWorkedMin() / 60);

									float actualRateMin = (hrsList.get(j).getSal() + 6000) / list.get(i).getEmpBudHr();
									float bugetedRate = actualRateMin * 60;
									float remHrsValue = actualRateMin * (int) (hrsList.get(j).getWorkedMin() % 60);

									mngrActlCost = mngrActlCost + ((emptempHrs * bugetedRate) + remHrsValue);

								}

							} else if (hrsList.get(j).getEmpType() == 4) {

								tlActlHrs = tlActlHrs + hrsList.get(j).getWorkedMin();

								if (rateType == 1) {

									int emptempHrs = (int) (hrsList.get(j).getWorkedMin() / 60);

									float actualRateMin = (hrsList.get(j).getSal() + 6000)
											/ hrsList.get(j).getTotalMinMonth();
									float actualRate = actualRateMin * 60;
									float remHrsValue = actualRateMin * (int) (hrsList.get(j).getWorkedMin() % 60);

									tlActlCost = tlActlCost + ((emptempHrs * actualRate) + remHrsValue);

								} else {

									int emptempHrs = (int) (hrsList.get(j).getWorkedMin() / 60);

									float actualRateMin = (hrsList.get(j).getSal() + 6000) / list.get(i).getEmpBudHr();
									float bugetedRate = actualRateMin * 60;
									float remHrsValue = actualRateMin * (int) (hrsList.get(j).getWorkedMin() % 60);

									tlActlCost = tlActlCost + ((emptempHrs * bugetedRate) + remHrsValue);

								}

							}
						}

					}

					String empActualHrs = String.valueOf((int) (empActlHrs / 60) + "." + (int) (empActlHrs % 60));
					String mngrActualHrs = String.valueOf((int) (mngrActlHrs / 60) + "." + (int) (mngrActlHrs % 60));
					String tlActualHrs = String.valueOf((int) (tlActlHrs / 60) + "." + (int) (tlActlHrs % 60));

					list.get(i).setEmpActualHrs(empActualHrs);
					list.get(i).setMngrActualHrs(mngrActualHrs);
					list.get(i).setTlActualHrs(tlActualHrs);

					list.get(i).setEmpActualCost(empActlCost);
					list.get(i).setMngrActualCost(mngrActlCost);
					list.get(i).setTlActualCost(tlActlCost);

					float empTotalBugetedCost = 0;
					float mngrTotalBugetedCost = 0;

					if (rateType == 1) {

						for (int k = 0; k < employeeds.length; k++) {

							float empBugetedCost = 0;

							try {
								for (int j = 0; j < hrsList.size(); j++) {

									if (Integer.parseInt(employeeds[k]) == hrsList.get(j).getEmpId()
											&& hrsList.get(j).getTaskId() == list.get(i).getTasId()) {

										int emptempHrs = (int) (hrsList.get(j).getWorkedMin() / 60);

										float actualRateMin = (hrsList.get(j).getSal() + 6000)
												/ hrsList.get(j).getTotalMinMonth();
										float actualRate = actualRateMin * 60;
										float remHrsValue = actualRateMin * (int) (hrsList.get(j).getWorkedMin() % 60);

										empBugetedCost = empBugetedCost + ((emptempHrs * actualRate) + remHrsValue);
 
									}

								}
							} catch (Exception e) {

							}
							empTotalBugetedCost = empTotalBugetedCost + empBugetedCost;

						}

						for (int k = 0; k < managerds.length; k++) {

							float mngrBugetedCost = 0;

							try {

								for (int j = 0; j < hrsList.size(); j++) {

									if (Integer.parseInt(managerds[k]) == hrsList.get(j).getEmpId()
											&& hrsList.get(j).getTaskId() == list.get(i).getTasId()) {

										int emptempHrs = (int) (hrsList.get(j).getWorkedMin() / 60);

										float actualRateMin = (hrsList.get(j).getSal() + 6000)
												/ hrsList.get(j).getTotalMinMonth();
										float actualRate = actualRateMin * 60;
										float remHrsValue = actualRateMin * (int) (hrsList.get(j).getWorkedMin() % 60);

										mngrBugetedCost = mngrBugetedCost + ((emptempHrs * actualRate) + remHrsValue);
 
									}

								}
							} catch (Exception e) {

							}
							mngrTotalBugetedCost = mngrTotalBugetedCost + mngrBugetedCost;
						}

					} else {

						SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
						String sdate = yy.format(list.get(i).getTaskEndDate());
						Date date = yy.parse(sdate);
						
						LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						int month = localDate.getMonthValue();

						
						
						
						for (int k = 0; k < employeeds.length; k++) {

							float empBugetedCost = 0;

							try {
								for (int j = 0; j < salList.size(); j++) {

									if (Integer.parseInt(employeeds[k]) == salList.get(j).getEmpId()) {

										float actualRateMin = 0;
										
										if (month == 1) {
											actualRateMin = (salList.get(j).getJan() + 6000) / list.get(i).getEmpBudHr();
										} else if (month == 2) {
											actualRateMin = (salList.get(j).getFeb() + 6000) / list.get(i).getEmpBudHr();
										} else if (month == 3) {
											actualRateMin = (salList.get(j).getMar() + 6000) / list.get(i).getEmpBudHr();
										} else if (month == 4) {
											actualRateMin = (salList.get(j).getApr() + 6000) / list.get(i).getEmpBudHr();
										} else if (month == 5) {
											actualRateMin = (salList.get(j).getMay() + 6000) / list.get(i).getEmpBudHr();
										} else if (month == 6) {
											actualRateMin = (salList.get(j).getJun() + 6000) / list.get(i).getEmpBudHr();
										} else if (month == 7) {
											actualRateMin = (salList.get(j).getJul() + 6000) / list.get(i).getEmpBudHr();
										} else if (month == 8) {
											actualRateMin = (salList.get(j).getAug() + 6000) / list.get(i).getEmpBudHr();
										} else if (month == 9) {
											actualRateMin = (salList.get(j).getSep() + 6000) / list.get(i).getEmpBudHr();
										} else if (month == 10) {
											actualRateMin = (salList.get(j).getOct() + 6000) / list.get(i).getEmpBudHr();
										} else if (month == 11) {
											actualRateMin = (salList.get(j).getNov() + 6000) / list.get(i).getEmpBudHr();
										} else if (month == 12) {
											actualRateMin = (salList.get(j).getDece() + 6000) / list.get(i).getEmpBudHr();
										}
										
										
										int emptempHrs = (int) (list.get(i).getEmpBudHr()/60);
										
										float bugetedRate = actualRateMin * 60;
										float remHrsValue = actualRateMin * (int) (list.get(i).getEmpBudHr() % 60); 
										empBugetedCost = empBugetedCost + ((emptempHrs * bugetedRate) + remHrsValue);
										
										
									}

								}
							} catch (Exception e) {

							}
							empTotalBugetedCost = empTotalBugetedCost + empBugetedCost;
							
						}
						
						
						for (int k = 0; k < managerds.length; k++) {

							float empBugetedCost = 0;

							try {
								for (int j = 0; j < salList.size(); j++) {

									if (Integer.parseInt(managerds[k]) == salList.get(j).getEmpId()) {

										float actualRateMin = 0;
										
										if (month == 1) {
											actualRateMin = (salList.get(j).getJan() + 6000) / list.get(i).getMngrBudHr();
										} else if (month == 2) {
											actualRateMin = (salList.get(j).getFeb() + 6000) / list.get(i).getMngrBudHr();
										} else if (month == 3) {
											actualRateMin = (salList.get(j).getMar() + 6000) / list.get(i).getMngrBudHr();
										} else if (month == 4) {
											actualRateMin = (salList.get(j).getApr() + 6000) / list.get(i).getMngrBudHr();
										} else if (month == 5) {
											actualRateMin = (salList.get(j).getMay() + 6000) / list.get(i).getMngrBudHr();
										} else if (month == 6) {
											actualRateMin = (salList.get(j).getJun() + 6000) / list.get(i).getMngrBudHr();
										} else if (month == 7) {
											actualRateMin = (salList.get(j).getJul() + 6000) / list.get(i).getMngrBudHr();
										} else if (month == 8) {
											actualRateMin = (salList.get(j).getAug() + 6000) / list.get(i).getMngrBudHr();
										} else if (month == 9) {
											actualRateMin = (salList.get(j).getSep() + 6000) / list.get(i).getMngrBudHr();
										} else if (month == 10) {
											actualRateMin = (salList.get(j).getOct() + 6000) / list.get(i).getMngrBudHr();
										} else if (month == 11) {
											actualRateMin = (salList.get(j).getNov() + 6000) / list.get(i).getMngrBudHr();
										} else if (month == 12) {
											actualRateMin = (salList.get(j).getDece() + 6000) / list.get(i).getMngrBudHr();
										}
										
										int emptempHrs = (int) (list.get(i).getMngrBudHr()/60);
 
										float bugetedRate = actualRateMin * 60;
										float remHrsValue = actualRateMin * (int) (list.get(i).getMngrBudHr() % 60); 
										empBugetedCost = empBugetedCost + ((emptempHrs * bugetedRate) + remHrsValue);
										 
									}

								}
							} catch (Exception e) {

							}
							mngrTotalBugetedCost = mngrTotalBugetedCost + empBugetedCost;

						}

					}
					 
					list.get(i).setEmpBugetedCost(empTotalBugetedCost);
					list.get(i).setMngrBugetedCost(mngrTotalBugetedCost);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} catch (Exception e) {

		}

		return list;

	}

}