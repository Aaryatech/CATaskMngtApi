package com.ats.cataskapi.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.ats.cataskapi.repositories.ClientWiseTaskReportRepository;
import com.ats.cataskapi.repositories.MonthWiseRateAndEmpActualHrsRepository;

@RestController
public class ManagerReportRestApi {

	@Autowired
	ClientWiseTaskReportRepository clientWiseTaskReportRepository;

	@Autowired
	MonthWiseRateAndEmpActualHrsRepository monthWiseRateAndEmpActualHrsRepository;

	@RequestMapping(value = { "/clientWiseTaskReport" }, method = RequestMethod.POST)
	public @ResponseBody List<ClientWiseTaskReport> clientWiseTaskReport(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("custId") int custId,
			@RequestParam("yearId") int yearId, @RequestParam("rateType") int rateType) {

		List<ClientWiseTaskReport> list = new ArrayList<>();

		try {

			list = clientWiseTaskReportRepository.getclientWiseTaskReport(fromDate, toDate, custId);

			String[] ids = {};
			String totalempIds = clientWiseTaskReportRepository.getEmpListByTaskId(fromDate, toDate, custId);
			ids = totalempIds.split(",");
			LinkedHashSet<String> hashSet = new LinkedHashSet<>(Arrays.asList(ids));
			ArrayList<String> arryids = new ArrayList<>(hashSet);

			List<MonthWiseRateAndEmpActualHrs> hrsList = monthWiseRateAndEmpActualHrsRepository.gethrsandsal(fromDate,
					toDate, custId, yearId, arryids);

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

										/*
										 * int emptempHrs = (int) (hrsList.get(j).getWorkedMin() / 60);
										 * 
										 * float actualRateMin = (hrsList.get(j).getSal() + 6000) /
										 * list.get(i).getEmpBudHr(); float bugetedRate = actualRateMin * 60; float
										 * remHrsValue = actualRateMin (int) (hrsList.get(j).getWorkedMin() % 60);
										 * 
										 * empBugetedCost = empBugetedCost + ((emptempHrs * bugetedRate) + remHrsValue);
										 */

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

										/*
										 * int emptempHrs = (int) (hrsList.get(j).getWorkedMin() / 60);
										 * 
										 * float actualRateMin = (hrsList.get(j).getSal() + 6000) /
										 * list.get(i).getEmpBudHr(); float bugetedRate = actualRateMin * 60; float
										 * remHrsValue = actualRateMin (int) (hrsList.get(j).getWorkedMin() % 60);
										 * 
										 * mngrBugetedCost = mngrBugetedCost + ((emptempHrs * bugetedRate) +
										 * remHrsValue);
										 */

									}

								}
							} catch (Exception e) {

							}
							mngrTotalBugetedCost = mngrTotalBugetedCost + mngrBugetedCost;
						}

					} else {

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
