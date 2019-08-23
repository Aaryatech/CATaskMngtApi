package com.ats.cataskapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.custdetailrepo.CustSignatoryMasterRepo;
import com.ats.cataskapi.custdetailrepo.GetCustLoginDetailRepo;
import com.ats.cataskapi.custdetailrepo.GetCustSignatoryRepo;
import com.ats.cataskapi.model.EmployeeMaster;
import com.ats.cataskapi.model.ServiceMaster;
import com.ats.cataskapi.model.custdetail.CustSignatoryMaster;
import com.ats.cataskapi.model.custdetail.GetCustLoginDetail;
import com.ats.cataskapi.model.custdetail.GetCustSignatory;
import com.ats.cataskapi.repositories.EmployeeMasterRepo;

@RestController
public class CustDetailController {

	@Autowired
	GetCustLoginDetailRepo getCustLoginDetailRepo;

	@RequestMapping(value = { "/getCustLoginDetailByCustId" }, method = RequestMethod.POST)
	public @ResponseBody List<GetCustLoginDetail> getCustLoginDetailByCustId(@RequestParam int custId) {
		List<GetCustLoginDetail> custDetail = null;
		try {

			custDetail = getCustLoginDetailRepo.getGetCustLoginDetailByCustId(custId);

			if (custDetail.size() == 0) {
				System.err.println("null getCustLoginDetailByCustId ");
				custDetail = new ArrayList<>();
			}

		} catch (Exception e) {
			System.err.println("Exce in getCustLoginDetailByCustId  ");
			e.printStackTrace();
		}

		return custDetail;
	}

	@RequestMapping(value = { "/getCustLoginDetailByCustDetailId" }, method = RequestMethod.POST)
	public @ResponseBody GetCustLoginDetail getCustLoginDetailByCustDetailId(@RequestParam int custDetailId) {
		GetCustLoginDetail custDetail = null;
		try {
				System.out.println("DetailId----------------"+custDetailId);
				
			custDetail = getCustLoginDetailRepo.getGetCustLoginDetailByCustDetailId(custDetailId);

			if (custDetail == null) {
				custDetail = new GetCustLoginDetail();
			}

		} catch (Exception e) {
			System.err.println("Exce in getCustLoginDetailByCustId  ");
			e.printStackTrace();
		}

		return custDetail;
	}

	@Autowired
	GetCustSignatoryRepo getCustSignatoryRepo;

	@RequestMapping(value = { "/getCustSignatoryByCustId" }, method = RequestMethod.POST)
	public @ResponseBody List<GetCustSignatory> getCustSignatoryByCustId(@RequestParam int custId) {
		List<GetCustSignatory> custDetail = null;
		try {

			custDetail = getCustSignatoryRepo.getGetCustSignatoryByCustId(custId);
			if (custDetail.size() == 0) {
				System.err.println("null getCustSignatoryByCustId ");
				custDetail = new ArrayList<>();
			}

		} catch (Exception e) {
			System.err.println("Exce in getCustSignatoryByCustId  ");
			e.printStackTrace();
		}

		return custDetail;
	}

	@RequestMapping(value = { "/getCustSignatoryBySignId" }, method = RequestMethod.POST)
	public @ResponseBody GetCustSignatory getCustSignatoryBySignId(@RequestParam int signId) {
		GetCustSignatory custDetail = null;
		try {

			custDetail = getCustSignatoryRepo.getGetCustSignatoryBySignId(signId);
			if (custDetail == null) {
				custDetail = new GetCustSignatory();
			}

		} catch (Exception e) {
			System.err.println("Exce in getCustSignatoryBySignId  ");
			e.printStackTrace();
		}

		return custDetail;
	}

	@Autowired
	CustSignatoryMasterRepo custSignatoryMasterRepo;

	@RequestMapping(value = { "/saveCustSignatory" }, method = RequestMethod.POST)
	public @ResponseBody CustSignatoryMaster saveCustSignatory(@RequestBody CustSignatoryMaster custSing) {

		CustSignatoryMaster serv = null;

		try {
			serv = custSignatoryMasterRepo.saveAndFlush(custSing);

		} catch (Exception e) {

			System.err.println("Exce in saving saveCustSignatory " + e.getMessage());
			e.printStackTrace();

		}
		return serv;
	}

	@Autowired
	EmployeeMasterRepo empRepo;

	@RequestMapping(value = { "/loginCheck" }, method = RequestMethod.POST)
	public @ResponseBody EmployeeMaster loginCheck(@RequestParam String userName, @RequestParam String password) {

		EmployeeMaster res = null;

		try {
			res = empRepo.loginCheck(userName, password);
			if (res == null) {
				System.err.println("Null Emp Found login Failed ");
				//res = new EmployeeMaster();
			}
		} catch (Exception e) {

			System.err.println("Exce in  loginCheck " + e.getMessage());
			e.printStackTrace();

		}
		return res;
	}

}
