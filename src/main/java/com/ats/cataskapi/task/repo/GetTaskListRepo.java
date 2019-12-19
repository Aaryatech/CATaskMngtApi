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
			"    t_tasks.task_end_date, t_tasks.task_completion_date ,\n" + 
			"    t_tasks.task_statutory_due_date,\n" + 
			"    CONCAT(FLOOR( t_tasks.mngr_bud_hr/60),':',MOD( t_tasks.mngr_bud_hr,60)) as mngr_bud_hr ,\n" + 
			"    t_tasks.cust_id,\n" + 
			"    t_tasks.periodicity_id,\n" + 
			"    t_tasks.actv_id,\n" + 
			"    t_tasks.serv_id,\n" + 
			"    CONCAT(FLOOR( t_tasks.emp_bud_hr/60),':',MOD( t_tasks.emp_bud_hr,60)) as emp_bud_hr,t_tasks.ex_var1,t_tasks.ex_int1,t_tasks.ex_int2, \n" + 
			"    dm_periodicity.periodicity_name,\n" + 
			"    m_activities.acti_name,\n" + 
			"    m_services.serv_name,concat (m_cust_header.cust_firm_name,' :' ,m_emp.emp_name) as cust_firm_name," + 
			"    dm_fin_year.fin_year_name,(SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) AND i.task_id=t_tasks.task_id) AS employees\n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    m_cust_header,\n" + 
			"    t_tasks,\n" + 
			"    dm_fin_year,m_emp\n" + 
			"WHERE"
			+ " m_emp.emp_id=m_cust_header.owner_emp_id and \n" + 
			" m_emp.emp_id=m_cust_header.owner_emp_id and   t_tasks.cust_id = m_cust_header.cust_id AND "
			+ "t_tasks.task_fy_id = dm_fin_year.fin_year_id AND "
			+ "m_services.serv_id = t_tasks.serv_id AND "
			+ "m_activities.acti_id = t_tasks.actv_id AND "
			+ "dm_periodicity.periodicity_id = t_tasks.periodicity_id AND "
			+ "t_tasks.task_status =:stat AND t_tasks.del_status = 1  AND t_tasks.is_active = 1   ORDER BY t_tasks.task_id DESC",nativeQuery=true)
	List<GetTaskList> getAllTaskListAll(@Param("stat") int stat);
	
	
	@Query(value=" SELECT\n" + 
			"    t_tasks.task_id,\n" + 
			"    t_tasks.task_code,\n" + 
			"    t_tasks.mapping_id,\n" + 
			"    t_tasks.task_subline,\n" + 
			"    t_tasks.task_fy_id,\n" + 
			"    t_tasks.task_text,\n" + 
			"    t_tasks.task_start_date,\n" + 
			"    t_tasks.task_end_date, t_tasks.task_completion_date ,\n" + 
			"    t_tasks.task_statutory_due_date,\n" + 
			"    CONCAT(FLOOR( t_tasks.mngr_bud_hr/60),':',MOD( t_tasks.mngr_bud_hr,60)) as mngr_bud_hr ,\n" + 
			"    t_tasks.cust_id,\n" + 
			"    t_tasks.periodicity_id,\n" + 
			"    t_tasks.actv_id,\n" + 
			"    t_tasks.serv_id,\n" + 
			"    CONCAT(FLOOR( t_tasks.emp_bud_hr/60),':',MOD( t_tasks.emp_bud_hr,60)) as emp_bud_hr,t_tasks.ex_var1,t_tasks.ex_int1,t_tasks.ex_int2, \n" + 
			"    dm_periodicity.periodicity_name,\n" + 
			"    m_activities.acti_name,\n" + 
			"    m_services.serv_name,concat (m_cust_header.cust_firm_name,' :' ,m_emp.emp_name) as cust_firm_name ,  \n" + 
			"    dm_fin_year.fin_year_name,(SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) AND i.task_id=t_tasks.task_id) AS employees\n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    m_cust_header,\n" + 
			"    t_tasks,\n" + 
			"    dm_fin_year,m_emp\n" + 
			"WHERE"
			+ " m_emp.emp_id=m_cust_header.owner_emp_id and \n" + 
			"    t_tasks.cust_id = m_cust_header.cust_id AND "
			+ "t_tasks.task_fy_id = dm_fin_year.fin_year_id AND "
			+ "m_services.serv_id = t_tasks.serv_id AND "
			+ "m_activities.acti_id = t_tasks.actv_id AND "
			+ "dm_periodicity.periodicity_id = t_tasks.periodicity_id AND "
			+ "t_tasks.task_status =:stat AND t_tasks.del_status = 1  AND t_tasks.is_active = 1  AND  t_tasks.serv_id=:servId AND t_tasks.cust_id=:custId   ORDER BY t_tasks.task_id DESC",nativeQuery=true)
	List<GetTaskList> getAllTaskListSpec(@Param("stat") int stat,@Param("servId") int servId,@Param("custId") int custId);
	
	
	
	@Query(value=" SELECT\n" + 
			"    t_tasks.task_id,\n" + 
			"    t_tasks.task_code,\n" + 
			"    t_tasks.mapping_id,\n" + 
			"    t_tasks.task_subline,\n" + 
			"    t_tasks.task_fy_id,\n" + 
			"    t_tasks.task_text,\n" + 
			"    t_tasks.task_start_date,\n" + 
			"    t_tasks.task_end_date, t_tasks.task_completion_date ,\n" + 
			"    t_tasks.task_statutory_due_date,\n" + 
			"    CONCAT(FLOOR( t_tasks.mngr_bud_hr/60),':',MOD( t_tasks.mngr_bud_hr,60)) as mngr_bud_hr ,\n" + 
			"    t_tasks.cust_id,\n" + 
			"    t_tasks.periodicity_id,\n" + 
			"    t_tasks.actv_id,\n" + 
			"    t_tasks.serv_id,\n" + 
			"    CONCAT(FLOOR( t_tasks.emp_bud_hr/60),':',MOD( t_tasks.emp_bud_hr,60)) as emp_bud_hr,t_tasks.ex_var1,t_tasks.ex_int1,t_tasks.ex_int2, \n" + 
			"    dm_periodicity.periodicity_name,\n" + 
			"    m_activities.acti_name,\n" + 
			"    m_services.serv_name,concat (m_cust_header.cust_firm_name,' :' ,m_emp.emp_name) as cust_firm_name ,  \n" + 
			"    dm_fin_year.fin_year_name,(SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) AND i.task_id=t_tasks.task_id) AS employees\n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    m_cust_header,\n" + 
			"    t_tasks,\n" + 
			"    dm_fin_year,m_emp\n" + 
			"WHERE"
			+ " m_emp.emp_id=m_cust_header.owner_emp_id and \n" + 
			"    t_tasks.cust_id = m_cust_header.cust_id AND "
			+ "t_tasks.task_fy_id = dm_fin_year.fin_year_id AND "
			+ "m_services.serv_id = t_tasks.serv_id AND "
			+ "m_activities.acti_id = t_tasks.actv_id AND "
			+ "dm_periodicity.periodicity_id = t_tasks.periodicity_id AND "
			+ "t_tasks.task_status =:stat AND t_tasks.del_status = 1  AND t_tasks.is_active = 1   AND t_tasks.cust_id=:custId   ORDER BY t_tasks.task_id DESC",nativeQuery=true)
	List<GetTaskList> getAllTaskListSpecCust(@Param("stat") int stat,@Param("custId") int custId);
	
	
	
	@Query(value=" SELECT\n" + 
			"    t_tasks.task_id,\n" + 
			"    t_tasks.task_code,\n" + 
			"    t_tasks.mapping_id,\n" + 
			"    t_tasks.task_subline,\n" + 
			"    t_tasks.task_fy_id,\n" + 
			"    t_tasks.task_text,\n" + 
			"    t_tasks.task_start_date,\n" + 
			"    t_tasks.task_end_date, t_tasks.task_completion_date ,\n" + 
			"    t_tasks.task_statutory_due_date,\n" + 
			"    CONCAT(FLOOR( t_tasks.mngr_bud_hr/60),':',MOD( t_tasks.mngr_bud_hr,60)) as mngr_bud_hr ,\n" + 
			"    t_tasks.cust_id,\n" + 
			"    t_tasks.periodicity_id,\n" + 
			"    t_tasks.actv_id,\n" + 
			"    t_tasks.serv_id,\n" + 
			"    CONCAT(FLOOR( t_tasks.emp_bud_hr/60),':',MOD( t_tasks.emp_bud_hr,60)) as emp_bud_hr,t_tasks.ex_var1,t_tasks.ex_int1,t_tasks.ex_int2, \n" + 
			"    dm_periodicity.periodicity_name,\n" + 
			"    m_activities.acti_name,\n" + 
			"    m_services.serv_name,concat (m_cust_header.cust_firm_name,' :' ,m_emp.emp_name) as cust_firm_name ,  \n" + 
			"    dm_fin_year.fin_year_name,(SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) AND i.task_id=t_tasks.task_id) AS employees\n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    m_cust_header,\n" + 
			"    t_tasks,\n" + 
			"    dm_fin_year,m_emp\n" + 
			"WHERE"
			+ " m_emp.emp_id=m_cust_header.owner_emp_id and \n" + 
			"    t_tasks.cust_id = m_cust_header.cust_id AND "
			+ "t_tasks.task_fy_id = dm_fin_year.fin_year_id AND "
			+ "m_services.serv_id = t_tasks.serv_id AND "
			+ "m_activities.acti_id = t_tasks.actv_id AND "
			+ "dm_periodicity.periodicity_id = t_tasks.periodicity_id AND "
			+ "t_tasks.task_status =:stat AND t_tasks.del_status = 1  AND t_tasks.is_active = 1   AND t_tasks.serv_id=:servId   ORDER BY t_tasks.task_id DESC",nativeQuery=true)
	List<GetTaskList> getAllTaskListSpecServ(@Param("stat") int stat,@Param("servId") int servId);
	
	
	
	
	@Query(value=" SELECT\n" + 
			"    t_tasks.task_id,\n" + 
			"    t_tasks.task_code,\n" + 
			"    t_tasks.mapping_id,\n" + 
			"    t_tasks.task_subline,\n" + 
			"    t_tasks.task_fy_id,\n" + 
			"    t_tasks.task_text,\n" + 
			"    t_tasks.task_start_date,\n" + 
			"    t_tasks.task_end_date,t_tasks.task_completion_date , \n" + 
			"    t_tasks.task_statutory_due_date,\n" + 
			"    t_tasks.mngr_bud_hr,\n" + 
			"    t_tasks.cust_id,\n" + 
			"    t_tasks.periodicity_id,\n" + 
			"    t_tasks.actv_id,\n" + 
			"    t_tasks.serv_id,\n" + 
			"    t_tasks.emp_bud_hr,t_tasks.ex_var1,t_tasks.ex_int1,t_tasks.ex_int2, \n" + 
			"    dm_periodicity.periodicity_name,\n" + 
			"    m_activities.acti_name,\n" + 
			"    m_services.serv_name,concat (m_cust_header.cust_firm_name,' :' ,m_emp.emp_name) as cust_firm_name , \n" + 
			"    dm_fin_year.fin_year_name,(SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) AND i.task_id=t_tasks.task_id) AS employees \n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    m_cust_header,\n" + 
			"    t_tasks,\n" + 
			"    dm_fin_year,m_emp \n" + 
			"WHERE"
			+ " m_emp.emp_id=m_cust_header.owner_emp_id and \n" + 
			"    t_tasks.cust_id = m_cust_header.cust_id AND "
			+ "t_tasks.task_fy_id = dm_fin_year.fin_year_id AND "
			+ "m_services.serv_id = t_tasks.serv_id AND "
			+ "m_activities.acti_id = t_tasks.actv_id AND "
			+ "dm_periodicity.periodicity_id = t_tasks.periodicity_id AND "
			+ "t_tasks.task_status =:stat AND t_tasks.del_status = 1   AND t_tasks.is_active = 1 AND  FIND_IN_SET (:empId,t_tasks.task_emp_ids) AND t_tasks.mapping_id=:mapId   ORDER BY t_tasks.task_id DESC",nativeQuery=true)
	List<GetTaskList> getAllManualTaskList(@Param("stat") int stat,@Param("empId") int empId,@Param("mapId") int mapId);
	
	

	@Query(value=" SELECT\n" + 
			"    t_tasks.task_id,\n" + 
			"    t_tasks.task_code,\n" + 
			"    t_tasks.mapping_id,\n" + 
			"    t_tasks.task_subline,\n" + 
			"    t_tasks.task_fy_id,\n" + 
			"    t_tasks.task_text,\n" + 
			"    t_tasks.task_start_date,\n" + 
			"    t_tasks.task_end_date,t_tasks.task_completion_date ,\n" + 
			"    t_tasks.task_statutory_due_date,\n" + 
			"    t_tasks.mngr_bud_hr,\n" + 
			"    t_tasks.cust_id,\n" + 
			"    t_tasks.periodicity_id,\n" + 
			"    t_tasks.actv_id,\n" + 
			"    t_tasks.serv_id,\n" + 
			"    t_tasks.emp_bud_hr,t_tasks.ex_var1,t_tasks.ex_int1, t_tasks.ex_int2, \n" + 
			"    dm_periodicity.periodicity_name,\n" + 
			"    m_activities.acti_name,\n" + 
			"    m_services.serv_name,concat (m_cust_header.cust_firm_name,' :' ,m_emp.emp_name) as cust_firm_name , \n" + 
			 "    dm_fin_year.fin_year_name,(SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) AND i.task_id=t_tasks.task_id) AS employees \n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    m_cust_header,\n" + 
			"    t_tasks,\n" + 
			"    dm_fin_year,m_emp \n" + 
			"WHERE"
			+ " m_emp.emp_id=m_cust_header.owner_emp_id and \n" + 
			"    t_tasks.cust_id = m_cust_header.cust_id AND "
			+ "t_tasks.task_fy_id = dm_fin_year.fin_year_id AND "
			+ "m_services.serv_id = t_tasks.serv_id AND "
			+ "m_activities.acti_id = t_tasks.actv_id AND "
			+ "dm_periodicity.periodicity_id = t_tasks.periodicity_id AND "
			+ "  t_tasks.del_status = 1   AND t_tasks.is_active = 0 AND  FIND_IN_SET (:empId,t_tasks.task_emp_ids)  ORDER BY t_tasks.task_id DESC",nativeQuery=true)
	List<GetTaskList> getAllInactiveTaskByEmpId(@Param("empId") int empId);

	@Query(value=" SELECT\n" + 
			"    t_tasks.task_id,\n" + 
			"    t_tasks.task_code,\n" + 
			"    t_tasks.mapping_id,\n" + 
			"    t_tasks.task_subline,\n" + 
			"    t_tasks.task_fy_id,\n" + 
			"    t_tasks.task_text,\n" + 
			"    t_tasks.task_start_date,\n" + 
			"    t_tasks.task_end_date,t_tasks.task_completion_date ,\n" + 
			"    t_tasks.task_statutory_due_date,\n" + 
			"    t_tasks.mngr_bud_hr,\n" + 
			"    t_tasks.cust_id,\n" + 
			"    t_tasks.periodicity_id,\n" + 
			"    t_tasks.actv_id,\n" + 
			"    t_tasks.serv_id,\n" + 
			"    t_tasks.emp_bud_hr,t_tasks.ex_var1,t_tasks.ex_int1,t_tasks.ex_int2, \n" + 
			"    dm_periodicity.periodicity_name,\n" + 
			"    m_activities.acti_name,\n" + 
			"    m_services.serv_name,concat (m_cust_header.cust_firm_name,' :' ,m_emp.emp_name) as cust_firm_name ,\n" + 
			"    dm_fin_year.fin_year_name,(SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) AND i.task_id=t_tasks.task_id) AS employees \n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    m_cust_header,\n" + 
			"    t_tasks,\n" + 
			"    dm_fin_year,m_emp \n" + 
			"WHERE"
			+ " m_emp.emp_id=m_cust_header.owner_emp_id and \n" + 
			"    t_tasks.cust_id = m_cust_header.cust_id AND "
			+ "t_tasks.task_fy_id = dm_fin_year.fin_year_id AND "
			+ "m_services.serv_id = t_tasks.serv_id AND "
			+ "m_activities.acti_id = t_tasks.actv_id AND "
			+ "dm_periodicity.periodicity_id = t_tasks.periodicity_id AND "
			+ "  t_tasks.del_status = 1   AND t_tasks.is_active = 0 AND  FIND_IN_SET (:empId,t_tasks.task_emp_ids) AND t_tasks.serv_id=:servId  ORDER BY t_tasks.task_id DESC",nativeQuery=true)
	List<GetTaskList> getInactiveTaskByEmpIdAllCustAct(@Param("empId") int empId, @Param("servId") int servId);

	@Query(value=" SELECT\n" + 
			"    t_tasks.task_id,\n" + 
			"    t_tasks.task_code,\n" + 
			"    t_tasks.mapping_id,\n" + 
			"    t_tasks.task_subline,\n" + 
			"    t_tasks.task_fy_id,\n" + 
			"    t_tasks.task_text,\n" + 
			"    t_tasks.task_start_date,\n" + 
			"    t_tasks.task_end_date,\n" + 
			"    t_tasks.task_statutory_due_date,t_tasks.task_completion_date ,\n" + 
			"    t_tasks.mngr_bud_hr,\n" + 
			"    t_tasks.cust_id,\n" + 
			"    t_tasks.periodicity_id,\n" + 
			"    t_tasks.actv_id,\n" + 
			"    t_tasks.serv_id,\n" + 
			"    t_tasks.emp_bud_hr,t_tasks.ex_var1,t_tasks.ex_int1,t_tasks.ex_int2, \n" + 
			"    dm_periodicity.periodicity_name,\n" + 
			"    m_activities.acti_name,\n" + 
			"    m_services.serv_name,concat (m_cust_header.cust_firm_name,' :' ,m_emp.emp_name) as cust_firm_name , \n" + 
			"    dm_fin_year.fin_year_name,(SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) AND i.task_id=t_tasks.task_id) AS employees\n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    m_cust_header,\n" + 
			"    t_tasks,\n" + 
			"    dm_fin_year,m_emp \n" + 
			"WHERE"
			+ " m_emp.emp_id=m_cust_header.owner_emp_id and \n" + 
			"    t_tasks.cust_id = m_cust_header.cust_id AND "
			+ "t_tasks.task_fy_id = dm_fin_year.fin_year_id AND "
			+ "m_services.serv_id = t_tasks.serv_id AND "
			+ "m_activities.acti_id = t_tasks.actv_id AND "
			+ "dm_periodicity.periodicity_id = t_tasks.periodicity_id AND "
			+ "  t_tasks.del_status = 1   AND t_tasks.is_active = 0 AND  FIND_IN_SET (:empId,t_tasks.task_emp_ids) AND t_tasks.serv_id=:servId AND t_tasks.cust_id IN(:itemsCust) AND t_tasks.actv_id IN(:itemsAct) ORDER BY t_tasks.task_id DESC",nativeQuery=true)
	List<GetTaskList> getAllInactiveTaskByEmpIdSpec(@Param("empId") int empId, @Param("servId") int servId,@Param("itemsCust") List<String> itemsCust,@Param("itemsAct") List<String> itemsAct);

	@Query(value=" SELECT\n" + 
			"    t_tasks.task_id,\n" + 
			"    t_tasks.task_code,\n" + 
			"    t_tasks.mapping_id,\n" + 
			"    t_tasks.task_subline,\n" + 
			"    t_tasks.task_fy_id,\n" + 
			"    t_tasks.task_text,\n" + 
			"    t_tasks.task_start_date,\n" + 
			"    t_tasks.task_end_date,\n" + 
			"    t_tasks.task_statutory_due_date,t_tasks.task_completion_date ,\n" + 
			"    t_tasks.mngr_bud_hr,\n" + 
			"    t_tasks.cust_id,\n" + 
			"    t_tasks.periodicity_id,\n" + 
			"    t_tasks.actv_id,\n" + 
			"    t_tasks.serv_id,\n" + 
			"    t_tasks.emp_bud_hr,t_tasks.ex_var1,t_tasks.ex_int1,t_tasks.ex_int2, \n" + 
			"    dm_periodicity.periodicity_name,\n" + 
			"    m_activities.acti_name,\n" + 
			"    m_services.serv_name,concat (m_cust_header.cust_firm_name,' :' ,m_emp.emp_name) as cust_firm_name , \n" +  
			"    dm_fin_year.fin_year_name,(SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) AND i.task_id=t_tasks.task_id) AS employees\n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    m_cust_header,\n" + 
			"    t_tasks,\n" + 
			"    dm_fin_year,m_emp \n" + 
			"WHERE"
			+ " m_emp.emp_id=m_cust_header.owner_emp_id and \n" + 
			"    t_tasks.cust_id = m_cust_header.cust_id AND "
			+ "t_tasks.task_fy_id = dm_fin_year.fin_year_id AND "
			+ "m_services.serv_id = t_tasks.serv_id AND "
			+ "m_activities.acti_id = t_tasks.actv_id AND "
			+ "dm_periodicity.periodicity_id = t_tasks.periodicity_id AND "
			+ "  t_tasks.del_status = 1   AND t_tasks.is_active = 0 AND  FIND_IN_SET (:empId,t_tasks.task_emp_ids) AND t_tasks.serv_id=:servId AND t_tasks.cust_id IN(:itemsCust) ORDER BY t_tasks.task_id DESC",nativeQuery=true)
	List<GetTaskList> getAllInactiveTaskByEmpIdAllAct(@Param("empId") int empId,@Param("servId")  int servId, @Param("itemsCust") List<String> itemsCust);

	@Query(value=" SELECT\n" + 
			"    t_tasks.task_id,\n" + 
			"    t_tasks.task_code,\n" + 
			"    t_tasks.mapping_id,\n" + 
			"    t_tasks.task_subline,\n" + 
			"    t_tasks.task_fy_id,\n" + 
			"    t_tasks.task_text,\n" + 
			"    t_tasks.task_start_date,\n" + 
			"    t_tasks.task_end_date,\n" + 
			"    t_tasks.task_statutory_due_date,t_tasks.task_completion_date , \n" + 
			"    t_tasks.mngr_bud_hr,\n" + 
			"    t_tasks.cust_id,\n" + 
			"    t_tasks.periodicity_id,\n" + 
			"    t_tasks.actv_id,\n" + 
			"    t_tasks.serv_id,\n" + 
			"    t_tasks.emp_bud_hr,t_tasks.ex_var1,t_tasks.ex_int1,t_tasks.ex_int2, \n" + 
			"    dm_periodicity.periodicity_name,\n" + 
			"    m_activities.acti_name,\n" + 
			"    m_services.serv_name,concat (m_cust_header.cust_firm_name,' :' ,m_emp.emp_name) as cust_firm_name, \n" + 
			"    dm_fin_year.fin_year_name,(SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) AND i.task_id=t_tasks.task_id) AS employees\n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    m_cust_header,\n" + 
			"    t_tasks,\n" + 
			"    dm_fin_year,m_emp \n" + 
			"WHERE"
			+ " m_emp.emp_id=m_cust_header.owner_emp_id and \n" + 
			"    t_tasks.cust_id = m_cust_header.cust_id AND "
			+ "t_tasks.task_fy_id = dm_fin_year.fin_year_id AND "
			+ "m_services.serv_id = t_tasks.serv_id AND "
			+ "m_activities.acti_id = t_tasks.actv_id AND "
			+ "dm_periodicity.periodicity_id = t_tasks.periodicity_id AND "
			+ "  t_tasks.del_status = 1   AND t_tasks.is_active = 0 AND  FIND_IN_SET (:empId,t_tasks.task_emp_ids) AND t_tasks.serv_id=:servId  AND t_tasks.actv_id IN(:itemsAct) ORDER BY t_tasks.task_id DESC",nativeQuery=true)
	List<GetTaskList> getAllInactiveTaskByEmpIdAllCust(@Param("empId") int empId, @Param("servId") int servId,@Param("itemsAct") List<String> itemsAct);
	




	@Query(value=" SELECT\n" + 
			"    t_tasks.task_id,\n" + 
			"    t_tasks.task_code,\n" + 
			"    t_tasks.mapping_id,\n" + 
			"    t_tasks.task_subline,\n" + 
			"    t_tasks.task_fy_id,\n" + 
			"    t_tasks.task_text,\n" + 
			"    t_tasks.task_start_date,\n" + 
			"    t_tasks.task_end_date,t_tasks.task_completion_date , \n" + 
			"    t_tasks.task_statutory_due_date,\n" + 
			"    t_tasks.mngr_bud_hr,\n" + 
			"    t_tasks.cust_id,\n" + 
			"    t_tasks.periodicity_id,\n" + 
			"    t_tasks.actv_id,\n" + 
			"    t_tasks.serv_id,\n" + 
			"    t_tasks.emp_bud_hr,t_tasks.ex_var1,t_tasks.ex_int1, t_tasks.ex_int2,\n" + 
			"    dm_periodicity.periodicity_name,\n" + 
			"    m_activities.acti_name,\n" + 
			"    m_services.serv_name,concat (m_cust_header.cust_firm_name,' :' ,m_emp.emp_name) as cust_firm_name , \n" + 
			"    dm_fin_year.fin_year_name,(SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) AND i.task_id=t_tasks.task_id) AS employees \n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    m_cust_header,\n" + 
			"    t_tasks,\n" + 
			"    dm_fin_year,m_emp \n" + 
			"WHERE  m_emp.emp_id=m_cust_header.owner_emp_id and \n" + 
			"    t_tasks.cust_id = m_cust_header.cust_id AND "
			+ "t_tasks.task_fy_id = dm_fin_year.fin_year_id AND "
			+ "m_services.serv_id = t_tasks.serv_id AND "
			+ "m_activities.acti_id = t_tasks.actv_id AND "
			+ "dm_periodicity.periodicity_id = t_tasks.periodicity_id AND "
			+ "t_tasks.task_status =:stat AND t_tasks.del_status = 1   AND t_tasks.is_active = 1 AND  FIND_IN_SET (:empId,t_tasks.task_emp_ids)  ORDER BY t_tasks.task_id DESC",nativeQuery=true)
	List<GetTaskList> getAllCompletedTaskList(@Param("stat") int stat,@Param("empId") int empId);
	
	

	@Query(value=" SELECT\n" + 
			"    t_tasks.task_id,\n" + 
			"    t_tasks.task_code,\n" + 
			"    t_tasks.mapping_id,\n" + 
			"    t_tasks.task_subline,\n" + 
			"    t_tasks.task_fy_id,\n" + 
			"    t_tasks.task_text,\n" + 
			"    t_tasks.task_start_date,\n" + 
			"    t_tasks.task_end_date,\n" + 
			"    t_tasks.task_statutory_due_date,t_tasks.task_completion_date , \n" + 
			"    t_tasks.mngr_bud_hr,\n" + 
			"    t_tasks.cust_id,\n" + 
			"    t_tasks.periodicity_id,\n" + 
			"    t_tasks.actv_id,\n" + 
			"    t_tasks.serv_id,\n" + 
			"    t_tasks.emp_bud_hr,t_tasks.ex_var1,t_tasks.ex_int1, t_tasks.ex_int2,\n" + 
			"    dm_periodicity.periodicity_name,\n" + 
			"    m_activities.acti_name,\n" + 
			"    m_services.serv_name,concat (m_cust_header.cust_firm_name,' :' ,m_emp.emp_name) as cust_firm_name , \n" + 
			"    dm_fin_year.fin_year_name,(SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) AND i.task_id=t_tasks.task_id) AS employees \n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    m_cust_header,\n" + 
			"    t_tasks,\n" + 
			"    dm_fin_year,m_emp \n" + 
			"WHERE"
			+ " m_emp.emp_id=m_cust_header.owner_emp_id and \n" + 
			"    t_tasks.cust_id = m_cust_header.cust_id AND "
			+ "t_tasks.task_fy_id = dm_fin_year.fin_year_id AND "
			+ "m_services.serv_id = t_tasks.serv_id AND "
			+ "m_activities.acti_id = t_tasks.actv_id AND "
			+ "dm_periodicity.periodicity_id = t_tasks.periodicity_id AND "
			+ "t_tasks.task_status =:stat AND t_tasks.del_status = 1   AND t_tasks.is_active = 1 AND  FIND_IN_SET (:empId,t_tasks.task_emp_ids)  AND t_tasks.serv_id=:servId AND t_tasks.cust_id=:custId  ORDER BY t_tasks.task_id DESC",nativeQuery=true)
	List<GetTaskList> getAllCustCompletedTaskList(@Param("stat") int stat,@Param("empId") int empId,@Param("servId") int servId,@Param("custId") int custId);



	@Query(value=" SELECT\n" + 
			"    t_tasks.task_id,\n" + 
			"    t_tasks.task_code,\n" + 
			"    t_tasks.mapping_id,\n" + 
			"    t_tasks.task_subline,\n" + 
			"    t_tasks.task_fy_id,\n" + 
			"    t_tasks.task_text,\n" + 
			"    t_tasks.task_start_date,\n" + 
			"    t_tasks.task_end_date,\n" + 
			"    t_tasks.task_statutory_due_date,t_tasks.task_completion_date , \n" + 
			"    t_tasks.mngr_bud_hr,\n" + 
			"    t_tasks.cust_id,\n" + 
			"    t_tasks.periodicity_id,\n" + 
			"    t_tasks.actv_id,\n" + 
			"    t_tasks.serv_id,\n" + 
			"    t_tasks.emp_bud_hr,t_tasks.ex_var1,t_tasks.ex_int1, t_tasks.ex_int2,\n" + 
			"    dm_periodicity.periodicity_name,\n" + 
			"    m_activities.acti_name,\n" + 
			"    m_services.serv_name,concat (m_cust_header.cust_firm_name,' :' ,m_emp.emp_name) as cust_firm_name , \n" + 
			"    dm_fin_year.fin_year_name,(SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) AND i.task_id=t_tasks.task_id) AS employees \n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    m_cust_header,\n" + 
			"    t_tasks,\n" + 
			"    dm_fin_year,m_emp \n" + 
			"WHERE  m_emp.emp_id=m_cust_header.owner_emp_id and \n" + 
			"    t_tasks.cust_id = m_cust_header.cust_id AND "
			+ "t_tasks.task_fy_id = dm_fin_year.fin_year_id AND "
			+ "m_services.serv_id = t_tasks.serv_id AND "
			+ "m_activities.acti_id = t_tasks.actv_id AND "
			+ "dm_periodicity.periodicity_id = t_tasks.periodicity_id AND "
			+ "t_tasks.task_status =:stat AND t_tasks.del_status = 1   AND t_tasks.is_active = 1 AND  FIND_IN_SET (:empId,t_tasks.task_emp_ids)  AND t_tasks.serv_id=:servId AND t_tasks.cust_id=:custId AND t_tasks.actv_id IN(:itemsAct)  ORDER BY t_tasks.task_id DESC",nativeQuery=true)
	List<GetTaskList> getSpecCutCompletedTaskList(@Param("stat") int stat,@Param("empId") int empId,@Param("servId") int servId,@Param("custId") int custId,@Param("itemsAct") List<String> itemsAct);

	/*************************For Add Emp hours*******************************/
	@Query(value="SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_code,\n" + 
			"        t_tasks.mapping_id,\n" + 
			"        t_tasks.task_subline,\n" + 
			"        t_tasks.task_fy_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,t_tasks.task_completion_date , \n" + 
			"        t_tasks.mngr_bud_hr,\n" + 
			"        t_tasks.cust_id,\n" + 
			"        t_tasks.periodicity_id,\n" + 
			"        t_tasks.actv_id,\n" + 
			"        t_tasks.serv_id,\n" + 
			"        t_tasks.emp_bud_hr,\n" + 
			"        t_tasks.ex_var1,\n" + 
			"        t_tasks.ex_int1,\n" + 
			"        t_tasks.ex_int2,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        m_services.serv_name,concat (m_cust_header.cust_firm_name,' :' ,m_emp.emp_name) as cust_firm_name , \n" + 
			"        dm_fin_year.fin_year_name,\n" + 
			"        (SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)              \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c              \n" + 
			"        WHERE\n" + 
			"         	c.emp_id = :emp AND\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids)          	\n" + 
			"            AND i.task_id=t_tasks.task_id) AS employees \n" + 
			"    FROM\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        m_cust_header,\n" + 
			"        t_tasks,\n" + 
			"        dm_fin_year,m_emp \n" + 
			"       \n" + 
			"    WHERE  m_emp.emp_id=m_cust_header.owner_emp_id and \n" + 
			"        t_tasks.cust_id = m_cust_header.cust_id \n" + 
			"        AND t_tasks.task_fy_id = dm_fin_year.fin_year_id \n" + 
			"        AND m_services.serv_id = t_tasks.serv_id \n" + 
			"        AND m_activities.acti_id = t_tasks.actv_id \n" + 
			"        AND dm_periodicity.periodicity_id = t_tasks.periodicity_id \n" + 
			"        AND t_tasks.task_status !=:stat\n" + 
			"        AND t_tasks.del_status = 1  \n" + 
			"        AND t_tasks.is_active = 1  \n" + 
			"        AND t_tasks.cust_id=:customer\n" + 
			"        AND t_tasks.serv_id = :service\n" + 
			"        AND t_tasks.actv_id = :activity\n" + 
			"        and t_tasks.task_emp_ids IN(:emp)\n" + 
			"       \n" + 
			"    ORDER BY\n" + 
			"        t_tasks.task_id DESC",nativeQuery=true)
	List<GetTaskList> getTaskListForWorkLog(@Param("stat") int stat, @Param("emp") int emp, @Param("service") int service, @Param("activity") int activity
			,@Param("customer") int customer);

}
