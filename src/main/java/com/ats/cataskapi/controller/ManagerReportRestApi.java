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

import com.ats.cataskapi.model.CapacityDetailByEmp;
import com.ats.cataskapi.model.ClientWiseTaskReport;
import com.ats.cataskapi.model.EmpIdNameList;
import com.ats.cataskapi.model.EmpwithPartnerList;
import com.ats.cataskapi.model.MonthWiseRateAndEmpActualHrs;
import com.ats.cataskapi.model.PartnerEmployeeHrs;
import com.ats.cataskapi.model.PartnerListWithHrs;
import com.ats.cataskapi.repositories.BugetedAmtAndRevenueRepo;
import com.ats.cataskapi.repositories.ClientWiseTaskReportRepository;
import com.ats.cataskapi.repositories.EmpIdNameListRepository;
import com.ats.cataskapi.repositories.MonthWiseRateAndEmpActualHrsRepository;
import com.ats.cataskapi.repositories.PartnerEmployeeHrsRepository;
import com.ats.cataskapi.service.CommonFunctionService;
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

	@Autowired
	PartnerEmployeeHrsRepository partnerEmployeeHrsRepository;

	@Autowired
	EmpIdNameListRepository empIdNameListRepository;

	@Autowired
	CommonFunctionService commonFunctionService;

	@RequestMapping(value = { "/clientWiseTaskReport" }, method = RequestMethod.POST)
	public @ResponseBody List<ClientWiseTaskReport> clientWiseTaskReport(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("groupId") int groupId,
			@RequestParam("clientId") int clientId, @RequestParam("yearId") int yearId,
			@RequestParam("rateType") int rateType) {

		List<ClientWiseTaskReport> list = new ArrayList<>();

		try {

			List<Integer> clntIds = new ArrayList<>();

			if (clientId == 0) {
				clntIds = bugetedAmtAndRevenueRepo.getclientByGroupId(groupId);
			} else {
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
											actualRateMin = (salList.get(j).getJan() + 6000) / empBugHrs;
										} else if (month == 2) {
											actualRateMin = (salList.get(j).getFeb() + 6000) / empBugHrs;
										} else if (month == 3) {
											actualRateMin = (salList.get(j).getMar() + 6000) / empBugHrs;
										} else if (month == 4) {
											actualRateMin = (salList.get(j).getApr() + 6000) / empBugHrs;
										} else if (month == 5) {
											actualRateMin = (salList.get(j).getMay() + 6000) / empBugHrs;
										} else if (month == 6) {
											actualRateMin = (salList.get(j).getJun() + 6000) / empBugHrs;
										} else if (month == 7) {
											actualRateMin = (salList.get(j).getJul() + 6000) / empBugHrs;
										} else if (month == 8) {
											actualRateMin = (salList.get(j).getAug() + 6000) / empBugHrs;
										} else if (month == 9) {
											actualRateMin = (salList.get(j).getSep() + 6000) / empBugHrs;
										} else if (month == 10) {
											actualRateMin = (salList.get(j).getOct() + 6000) / empBugHrs;
										} else if (month == 11) {
											actualRateMin = (salList.get(j).getNov() + 6000) / empBugHrs;
										} else if (month == 12) {
											actualRateMin = (salList.get(j).getDece() + 6000) / empBugHrs;
										}

										int emptempHrs = (int) (list.get(i).getEmpBudHr() / 60);

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
											actualRateMin = (salList.get(j).getJan() + 6000) / mngrBugHrs;
										} else if (month == 2) {
											actualRateMin = (salList.get(j).getFeb() + 6000) / mngrBugHrs;
										} else if (month == 3) {
											actualRateMin = (salList.get(j).getMar() + 6000) / mngrBugHrs;
										} else if (month == 4) {
											actualRateMin = (salList.get(j).getApr() + 6000) / mngrBugHrs;
										} else if (month == 5) {
											actualRateMin = (salList.get(j).getMay() + 6000) / mngrBugHrs;
										} else if (month == 6) {
											actualRateMin = (salList.get(j).getJun() + 6000) / mngrBugHrs;
										} else if (month == 7) {
											actualRateMin = (salList.get(j).getJul() + 6000) / mngrBugHrs;
										} else if (month == 8) {
											actualRateMin = (salList.get(j).getAug() + 6000) / mngrBugHrs;
										} else if (month == 9) {
											actualRateMin = (salList.get(j).getSep() + 6000) / mngrBugHrs;
										} else if (month == 10) {
											actualRateMin = (salList.get(j).getOct() + 6000) / mngrBugHrs;
										} else if (month == 11) {
											actualRateMin = (salList.get(j).getNov() + 6000) / mngrBugHrs;
										} else if (month == 12) {
											actualRateMin = (salList.get(j).getDece() + 6000) / mngrBugHrs;
										}

										int emptempHrs = (int) (list.get(i).getMngrBudHr() / 60);

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

	@RequestMapping(value = { "/employeepartnerwiseworkreport" }, method = RequestMethod.POST)
	public @ResponseBody List<EmpwithPartnerList> employeepartnerwiseworkreport(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("partnerType") int partnerType) {

		List<EmpwithPartnerList> finalList = new ArrayList<>();

		try {

			List<PartnerEmployeeHrs> list = new ArrayList<>();

			List<Integer> empIds = partnerEmployeeHrsRepository.getempList();

			List<EmpIdNameList> partnerList = empIdNameListRepository.getpartnerList();
			List<EmpIdNameList> empList = empIdNameListRepository.getempList();

			List<CapacityDetailByEmp> bugetedCapList = commonFunctionService.CalculateActualAvailableHrs(empIds,
					fromDate, toDate);

			if (partnerType == 0) {

				list = partnerEmployeeHrsRepository.employeeexcutionpartnerwiseworkreport(fromDate, toDate);

			} else {

				list = partnerEmployeeHrsRepository.employeepartnerwiseworkreport(fromDate, toDate);
			}

			for (int i = 0; i < empList.size(); i++) {

				EmpwithPartnerList empwithPartnerList = new EmpwithPartnerList();
				empwithPartnerList.setEmpId(empList.get(i).getEmpId());
				empwithPartnerList.setEmpName(empList.get(i).getEmpName());

				long totalMin = 0;

				List<PartnerListWithHrs> partnerHrsList = new ArrayList<>();

				for (int j = 0; j < partnerList.size(); j++) {

					PartnerListWithHrs partnerListWithHrs = new PartnerListWithHrs();
					partnerListWithHrs.setPartnerId(partnerList.get(j).getEmpId());
					partnerListWithHrs.setPartnerName(partnerList.get(j).getEmpName());
					partnerListWithHrs.setTotalHrs("0");

					for (int k = 0; k < list.size(); k++) {

						if (list.get(k).getPartnerId() == partnerList.get(j).getEmpId()
								&& list.get(k).getEmpId() == empList.get(i).getEmpId()) {
							partnerListWithHrs.setTotalHrs(list.get(k).getTotalHrs());
							totalMin = totalMin + list.get(k).getTotalMin();
							break;
						}

					}

					partnerHrsList.add(partnerListWithHrs);

				}

				int hrs = (int) (totalMin / 60);
				int min = (int) (totalMin % 60);
				empwithPartnerList.setWorkedHrs(hrs + "." + min);
				empwithPartnerList.setWorkedMin(totalMin);

				empwithPartnerList.setList(partnerHrsList);
				finalList.add(empwithPartnerList);
			}

			for (int i = 0; i < finalList.size(); i++) {

				for (int j = 0; j < bugetedCapList.size(); j++) {

					if (bugetedCapList.get(j).getEmpId() == finalList.get(i).getEmpId()) {

						// finalList.get(i).setBugetedHrs(bugetedCapList.get(j).getBugetedCap());

						float totalfreehrs = bugetedCapList.get(j).getBugetedCap();

						if ((int) totalfreehrs < totalfreehrs && (int) totalfreehrs + 1 > totalfreehrs) {

							totalfreehrs = (float) (totalfreehrs - 0.20);
						}
						finalList.get(i).setBugetedHrs(String.valueOf(totalfreehrs) + 0);

						String[] strValues = String.valueOf(finalList.get(i).getBugetedHrs()).split("\\."); 

						long totalMin =   ((((int) (totalfreehrs)) * 60) + Integer.parseInt(strValues[1]));

						finalList.get(i).setBugetedMin(totalMin);

						long dif = totalMin - finalList.get(i).getWorkedMin();

						if (dif > 0) {

							int hrs = (int) (dif / 60);
							int min = (int) (dif % 60);
							finalList.get(i).setIdealtime(hrs + "." + min);
							finalList.get(i).setOvertime("-");
						} else {

							dif = finalList.get(i).getWorkedMin() - totalMin;

							int hrs = (int) (dif / 60);
							int min = (int) (dif % 60);
							finalList.get(i).setIdealtime("-");
							finalList.get(i).setOvertime(hrs + "." + min);
						}

						break;
					}

				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return finalList;

	}

	@RequestMapping(value = { "/getPartnerList" }, method = RequestMethod.GET)
	public @ResponseBody List<EmpIdNameList> getPartnerList() {

		List<EmpIdNameList> partnerList = new ArrayList<>();

		try {

			partnerList = empIdNameListRepository.getpartnerList();

		} catch (Exception e) {

		}

		return partnerList;

	}

}
