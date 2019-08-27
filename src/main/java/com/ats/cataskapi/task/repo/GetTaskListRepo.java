package com.ats.cataskapi.task.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.task.model.GetTaskList;

public interface GetTaskListRepo extends JpaRepository<GetTaskList, Integer>{
	
	@Query(value=" SELECT\n" + 
			"    t_tasks.task_id,\n" + 
			"    t_tasks.task_code,\n" + 
			"    t_tasks.mapping_id,\n" + 
			"    t_tasks.task_subline,\n" + 
			"    t_tasks.task_fy_id,\n" + 
			"    t_tasks.task_text,\n" + 
			"    t_tasks.task_start_date,\n" + 
			"    t_tasks.task_end_date,\n" + 
			"    t_tasks.task_statutory_due_date,\n" + 
			"    t_tasks.mngr_bud_hr,\n" + 
			"    t_tasks.cust_id,\n" + 
			"    t_tasks.periodicity_id,\n" + 
			"    t_tasks.actv_id,\n" + 
			"    t_tasks.serv_id,\n" + 
			"    t_tasks.emp_bud_hr,t_tasks.ex_var1,t_tasks.ex_int1, \n" + 
			"    dm_periodicity.periodicity_name,\n" + 
			"    m_activities.acti_name,\n" + 
			"    m_services.serv_name,\n" + 
			"    m_cust_header.cust_firm_name,\n" + 
			"    dm_fin_year.fin_year_name\n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    m_cust_header,\n" + 
			"    t_tasks,\n" + 
			"    dm_fin_year\n" + 
			"WHERE\n" + 
			"    t_tasks.cust_id = m_cust_header.cust_id AND "
			+ "t_tasks.task_fy_id = dm_fin_year.fin_year_id AND "
			+ "m_services.serv_id = t_tasks.serv_id AND "
			+ "m_activities.acti_id = t_tasks.actv_id AND "
			+ "dm_periodicity.periodicity_id = t_tasks.periodicity_id AND "
			+ "t_tasks.task_status =:stat AND t_tasks.del_status = 1  ORDER BY t_tasks.task_id DESC",nativeQuery=true)
	List<GetTaskList> getAllTaskList(@Param("stat") int stat);
	
	
	@Query(value=" SELECT\n" + 
			"    t_tasks.task_id,\n" + 
			"    t_tasks.task_code,\n" + 
			"    t_tasks.mapping_id,\n" + 
			"    t_tasks.task_subline,\n" + 
			"    t_tasks.task_fy_id,\n" + 
			"    t_tasks.task_text,\n" + 
			"    t_tasks.task_start_date,\n" + 
			"    t_tasks.task_end_date,\n" + 
			"    t_tasks.task_statutory_due_date,\n" + 
			"    t_tasks.mngr_bud_hr,\n" + 
			"    t_tasks.cust_id,\n" + 
			"    t_tasks.periodicity_id,\n" + 
			"    t_tasks.actv_id,\n" + 
			"    t_tasks.serv_id,\n" + 
			"    t_tasks.emp_bud_hr,t_tasks.ex_var1,t_tasks.ex_int1, \n" + 
			"    dm_periodicity.periodicity_name,\n" + 
			"    m_activities.acti_name,\n" + 
			"    m_services.serv_name,\n" + 
			"    m_cust_header.cust_firm_name,\n" + 
			"    dm_fin_year.fin_year_name\n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    m_cust_header,\n" + 
			"    t_tasks,\n" + 
			"    dm_fin_year\n" + 
			"WHERE\n" + 
			"    t_tasks.cust_id = m_cust_header.cust_id AND "
			+ "t_tasks.task_fy_id = dm_fin_year.fin_year_id AND "
			+ "m_services.serv_id = t_tasks.serv_id AND "
			+ "m_activities.acti_id = t_tasks.actv_id AND "
			+ "dm_periodicity.periodicity_id = t_tasks.periodicity_id AND "
			+ "t_tasks.task_status =:stat AND t_tasks.del_status = 1  AND  FIND_IN_SET (:empId,t_tasks.task_emp_ids) AND t_tasks.mapping_id=0   ORDER BY t_tasks.task_id DESC",nativeQuery=true)
	List<GetTaskList> getAllManualTaskList(@Param("stat") int stat,@Param("empId") int empId);
}
