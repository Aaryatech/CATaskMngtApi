package com.ats.cataskapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.logindetailrepo.GetCustLoginDetailRepo;
import com.ats.cataskapi.model.custlogindetail.GetCustLoginDetail;

@RestController
public class CustDetailController   {

	@Autowired GetCustLoginDetailRepo getCustLoginDetailRepo;
	
	
	
	@RequestMapping(value = {"/getCustLoginDetailByCustId"}, method = RequestMethod.POST)
	public @ResponseBody List<GetCustLoginDetail> getCustLoginDetailByCustId(@RequestParam int custId) {
		List<GetCustLoginDetail> custDetail = null;
		try {
			
				custDetail = getCustLoginDetailRepo.getGetCustLoginDetailByCustId(custId);
			
					if(custDetail.size()==0) {
						System.err.println("null getCustLoginDetailByCustId ");
						custDetail=new ArrayList<>();
					}
			
		}catch (Exception e) {
			    System.err.println("Exce in getCustLoginDetailByCustId  " );
				e.printStackTrace();
		}
		
		return custDetail;
	}
	
}
