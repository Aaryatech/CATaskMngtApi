package com.ats.cataskapi.controller;
 

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.model.Info;
import com.ats.cataskapi.task.model.EmpSalary;
import com.ats.cataskapi.task.model.TempSalList;
import com.ats.cataskapi.task.repo.EmpSalaryRepo;

@RestController
public class SalaryApiController {
	
	@Autowired
	EmpSalaryRepo empSalaryRepo;
	 
	@RequestMapping(value = {"/getEmpSalByYear"}, method = RequestMethod.POST)
	public @ResponseBody  List<EmpSalary> getEmpSalByEmpIdAndFinYearId(@RequestParam int year) {
		 List<EmpSalary>  empList = new ArrayList<EmpSalary>();
		try {
			empList = empSalaryRepo.findByFinYearIdAndDelStatus(year,1);
		}catch (Exception e) {
			System.err.println("Exce in getServiceById" + e.getMessage());
		}
		return empList;
	}
	
	
	@RequestMapping(value = { "/updateSalRecord" }, method = RequestMethod.POST)
	public @ResponseBody Info updateSalRecord(@RequestBody List<TempSalList> salList) {
		
		Info info = new Info();
		EmpSalary emp = new EmpSalary();
		try
		{
			for(int i=0;i< salList.size();i++){
				emp = empSalaryRepo.findByEmpIdAndFinYearIdAndDelStatus(salList.get(i).getEmpId(),salList.get(i).getFinYear(),1);
			
				TempSalList sal=salList.get(i);
				int n=0;
			if(emp!=null) {
				
				if(sal.getMonth()==1) {
					n=empSalaryRepo.updateSalRecJan(sal.getCurDateTime(),sal.getUserId(),sal.getEmpSalary(),emp.getSalaryId());
				}
				else if(sal.getMonth()==2) {
					n=empSalaryRepo.updateSalRecFeb(sal.getCurDateTime(),sal.getUserId(),sal.getEmpSalary(),emp.getSalaryId());

				}
				else if(sal.getMonth()==3) {
					n=empSalaryRepo.updateSalRecMar(sal.getCurDateTime(),sal.getUserId(),sal.getEmpSalary(),emp.getSalaryId());

				}
				else if(sal.getMonth()==4) {
					n=empSalaryRepo.updateSalRecApr(sal.getCurDateTime(),sal.getUserId(),sal.getEmpSalary(),emp.getSalaryId());

				}
				else if(sal.getMonth()==5) {
					n=empSalaryRepo.updateSalRecMay(sal.getCurDateTime(),sal.getUserId(),sal.getEmpSalary(),emp.getSalaryId());

				}
				else if(sal.getMonth()==6) {
					n=empSalaryRepo.updateSalRecJun(sal.getCurDateTime(),sal.getUserId(),sal.getEmpSalary(),emp.getSalaryId());

				}
				else if(sal.getMonth()==7) {
					n=empSalaryRepo.updateSalRecJul(sal.getCurDateTime(),sal.getUserId(),sal.getEmpSalary(),emp.getSalaryId());

				}
				else if(sal.getMonth()==8) {
					n=empSalaryRepo.updateSalRecAug(sal.getCurDateTime(),sal.getUserId(),sal.getEmpSalary(),emp.getSalaryId());

				}
				else if(sal.getMonth()==9) {
					n=empSalaryRepo.updateSalRecSep(sal.getCurDateTime(),sal.getUserId(),sal.getEmpSalary(),emp.getSalaryId());

				}
				else if(sal.getMonth()==10) {
					n=empSalaryRepo.updateSalRecOct(sal.getCurDateTime(),sal.getUserId(),sal.getEmpSalary(),emp.getSalaryId());

				}
				else if(sal.getMonth()==11) {
					n=empSalaryRepo.updateSalRecNov(sal.getCurDateTime(),sal.getUserId(),sal.getEmpSalary(),emp.getSalaryId());

				}
				else if(sal.getMonth()==12) {
					n=empSalaryRepo.updateSalRecDece(sal.getCurDateTime(),sal.getUserId(),sal.getEmpSalary(),emp.getSalaryId());

				}
				 
				
			}else {
				
				EmpSalary empSave = new EmpSalary();
				empSave.setDelStatus(1);
				empSave.setFinYearId(sal.getFinYear());
				empSave.setEmpId(sal.getEmpId());
				empSave.setExInt1(1);
				empSave.setExVar1("NA");
				empSave.setUpdateDatetime(sal.getCurDateTime());
				empSave.setUpdateUsername(sal.getUserId());
				if(sal.getMonth()==1) {
					empSave.setJan(sal.getEmpSalary());
 				}
				else if(sal.getMonth()==2) {
					empSave.setFeb(sal.getEmpSalary());
				}
				else if(sal.getMonth()==3) {
					empSave.setMar(sal.getEmpSalary());
				}
				else if(sal.getMonth()==4) {
					empSave.setApr(sal.getEmpSalary());
				}
				else if(sal.getMonth()==5) {
					empSave.setMay(sal.getEmpSalary());
				}
				else if(sal.getMonth()==6) {
					empSave.setJun(sal.getEmpSalary());
				}
				else if(sal.getMonth()==7) {
					empSave.setJul(sal.getEmpSalary());
				}
				else if(sal.getMonth()==8) {
					empSave.setAug(sal.getEmpSalary());
				}
				else if(sal.getMonth()==9) {
					empSave.setSep(sal.getEmpSalary());
				}
				else if(sal.getMonth()==10) {
					empSave.setOct(sal.getEmpSalary());
				}
				else if(sal.getMonth()==11) {
					empSave.setNov(sal.getEmpSalary());
				}
				else if(sal.getMonth()==12) {
					empSave.setDece(sal.getEmpSalary());
				}
				empSalaryRepo.saveAndFlush(empSave);
				
				
				
			}
			
			}
		
		} catch (Exception e) {

			System.err.println("Exce in deleteService  " + e.getMessage());
			e.printStackTrace();
			info.setError(true);
			info.setMsg("excep");
		}

		return info;

	}
	
	
	
	
}
