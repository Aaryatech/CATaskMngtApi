package com.ats.cataskapi.task.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ShareDeshareRepo extends JpaRepository<ShareDeshare, Integer>{

	@Query(value=" SELECT UUID() as unique_id, m_emp.emp_email,m_cust_header.cust_folder_id FROM m_emp,m_cust_header,t_tasks\n" + 
			"WHERE m_emp.del_status=1 AND find_In_SET(m_emp.emp_id,t_tasks.task_emp_ids) AND t_tasks.cust_id=m_cust_header.cust_id AND m_cust_header.del_status=1 AND t_tasks.task_status!=9 group by m_cust_header.cust_id,m_emp.emp_id\n" + 
			"  ",nativeQuery=true)
	List<ShareDeshare> getEmpListShareDeshare();
	
}

