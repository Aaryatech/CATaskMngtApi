package com.ats.cataskapi.service;
  
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ats.cataskapi.model.CapacityDetailByEmp;
 

@Service
public interface CommonFunctionService {

	public @ResponseBody List<CapacityDetailByEmp> CalculateActualAvailableHrs(List<Integer> empIds, String fromDate, String toDate);

}
