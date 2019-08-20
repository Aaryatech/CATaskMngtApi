package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ats.cataskapi.model.TaskListHome;

public interface TaskListHomeRepo extends JpaRepository<TaskListHome, Integer> {

	@Query(value="SELECT \n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.mngr_bud_hr,\n" + 
			"        t_tasks.emp_bud_hr,\n" + 
			"        t_tasks.task_emp_ids,\n" + 
			"        m_emp.emp_name,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        m_cust_group.cust_group_name,\n" + 
			"        dm_fin_year.fin_year_name\n" + 
			"       \n" + 
			"\n" + 
			"FROM 	\n" + 
			"        t_tasks,\n" + 
			"        m_emp,\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        m_cust_group,\n" + 
			"        m_cust_header,\n" + 
			"        dm_fin_year\n" + 
			"       \n" + 
			"WHERE \n" + 
			"        t_tasks.del_status=1 AND\n" + 
			"        m_emp.emp_id=2 AND\n" + 
			"		t_tasks.task_emp_ids=m_emp.emp_id AND\n" + 
			"        t_tasks.actv_id=m_activities.acti_id AND\n" + 
			"        m_activities.serv_id=m_services.serv_id AND\n" + 
			"        m_activities.periodicity_id=dm_periodicity.periodicity_id AND\n" + 
			"        t_tasks.cust_id=m_cust_header.cust_id AND\n" + 
			"        m_cust_header.cust_group_id=m_cust_group.cust_group_id AND\n" + 
			"        dm_fin_year.fin_year_id=t_tasks.task_fy_id", nativeQuery=true)
	List<TaskListHome> getInfo();
}
