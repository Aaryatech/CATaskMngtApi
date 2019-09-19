package com.ats.cataskapi.service;
 
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
 

@Service
public interface CommonFunctionService {

	public @ResponseBody float CalculateActualAvailableHrs(int empId, String fromDate, String toDate);

}
