package com.ats.cataskapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.accessrights.AssignRoleDetailList;
import com.ats.cataskapi.model.Info;
import com.ats.cataskapi.task.repo.ShareDeshare;
import com.ats.cataskapi.task.repo.ShareDeshareRepo;

@RestController
public class ShareDeshareService {
/*
 SELECT UUID() as unique_id, m_emp.emp_email,m_cust_header.cust_folder_id FROM m_emp,m_cust_header,t_tasks
WHERE m_emp.del_status=1 AND find_In_SET(m_emp.emp_id,t_tasks.task_emp_ids) AND t_tasks.cust_id=m_cust_header.cust_id AND m_cust_header.del_status=1 AND t_tasks.task_status!=9 group by m_cust_header.cust_id,m_emp.emp_id
 */
	
	@Autowired
	ShareDeshareRepo shareDeshareRepo;
	
	@RequestMapping(value = { "/getShareDeshare" }, method = RequestMethod.GET)
	public @ResponseBody List<ShareDeshare> getShareDeshare() {
		
		List<ShareDeshare> desharesList=new ArrayList<ShareDeshare>();
		try {
		 desharesList=shareDeshareRepo.getEmpListShareDeshare();
		}catch (Exception e) {
			desharesList=new ArrayList<ShareDeshare>();
			System.err.println("exce Occ at getShareDeshare " +e.getMessage());
		}
		return desharesList;
		
	}
}
