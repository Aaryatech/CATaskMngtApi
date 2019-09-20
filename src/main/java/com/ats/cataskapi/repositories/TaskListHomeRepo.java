package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.TaskListHome;

public interface TaskListHomeRepo extends JpaRepository<TaskListHome, Integer> {

	@Query(value="SELECT\n" + 
			"    t_tasks.task_id,\n" + 
			"    t_tasks.task_text,\n" + 
			"    t_tasks.task_start_date,\n" + 
			"    t_tasks.task_end_date,\n" + 
			"    t_tasks.task_statutory_due_date,\n" + 
			"        CONCAT(FLOOR( t_tasks.mngr_bud_hr/60),':',MOD( t_tasks.mngr_bud_hr,60)) as mngr_bud_hr,\n" + 
			"         CONCAT(FLOOR( t_tasks.emp_bud_hr/60),':',MOD( t_tasks.emp_bud_hr,60)) as emp_bud_hr ,\n" + 
			"    t_tasks.task_emp_ids,\n" + 
			"    t_tasks.task_status AS ex_int1,\n" + 
			"    t_tasks.ex_int2,\n" + 
			"    t_tasks.ex_var1,\n" + 
			"    t_tasks.ex_var2,\n" + 
			"    dm_status_mst.status_text AS task_status,\n" + 
			"    dm_status_mst.status_color,\n" + 
			"    m_emp.emp_name,\n" + 
			"    m_emp.emp_id,\n" + 
			"    m_services.serv_name,\n" + 
			"    m_activities.acti_name,\n" + 
			"    dm_periodicity.periodicity_name,\n" + 
			"    m_cust_header.cust_firm_name AS cust_group_name,\n" + 
			"dm_fin_year.fin_year_name,\n" + 
			"(\n" + 
			"    SELECT\n" + 
			"        GROUP_CONCAT(DISTINCT c.emp_name)\n" + 
			"    FROM\n" + 
			"        t_tasks i,\n" + 
			"        m_emp c\n" + 
			"    WHERE\n" + 
			"        FIND_IN_SET(c.emp_id, task_emp_ids) AND i.task_id = t_tasks.task_id\n" + 
			") AS employees,COALESCE(\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        m_emp.emp_name\n" + 
			"        from m_emp\n" + 
			"   \n" + 
			"    WHERE  \n" + 
			"        m_cust_header.owner_emp_id = m_emp.emp_id\n" + 
			"),\n" + 
			" 'NA' \n" + 
			") AS owner_partner\n" + 
			"\n" + 
			"FROM\n" + 
			"    t_tasks,\n" + 
			"    m_emp,\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    m_cust_header,\n" + 
			"    dm_fin_year,\n" + 
			"    dm_status_mst\n" + 
			"WHERE\n" + 
			"   t_tasks.del_status = 1 AND t_tasks.is_active=1 AND m_services.ex_int1 = 1 AND m_activities.ex_int1 = 1 AND m_emp.emp_id =:empId AND FIND_IN_SET(:empId, t_tasks.task_emp_ids) AND t_tasks.actv_id = m_activities.acti_id AND t_tasks.serv_id = m_services.serv_id AND m_activities.periodicity_id = dm_periodicity.periodicity_id AND t_tasks.cust_id = m_cust_header.cust_id AND dm_fin_year.fin_year_id = t_tasks.task_fy_id AND dm_status_mst.status_value = t_tasks.task_status AND t_tasks.task_status NOT IN(:statusIds)", nativeQuery=true)
	List<TaskListHome> getTaskList(@Param("empId") int empId,@Param("statusIds") List<String> statusIds);
	

	/**************************************************************************/
	/*@Query(value="SELECT \n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.mngr_bud_hr,\n" + 
			"        t_tasks.emp_bud_hr,\n" + 
			"        t_tasks.task_emp_ids,\n" + 
			"        t_tasks.task_status,\n" + 
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
			"        m_emp.emp_id=:empId AND\n" +
			"		 FIND_IN_SET(:empId,t_tasks.task_emp_ids) AND\n" + 
			"        t_tasks.task_start_date BETWEEN :fromDate AND :toDate AND\n" + 
			"        t_tasks.actv_id=m_activities.acti_id AND\n" + 
			"        t_tasks.serv_id=m_services.serv_id AND\n" + 
			"        m_activities.periodicity_id=dm_periodicity.periodicity_id AND\n" + 
			"        t_tasks.cust_id=m_cust_header.cust_id AND\n" + 
			"        m_cust_header.cust_group_id=m_cust_group.cust_group_id AND\n" + 
			"        dm_fin_year.fin_year_id=t_tasks.task_fy_id", nativeQuery=true)
	List<TaskListHome> getTaskList(@Param("empId")int empId, @Param("fromDate") String fromDate, @Param("toDate") String toDate);*/
/*****************************************************************************************/
	
	@Query(value="SELECT \n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.mngr_bud_hr,\n" + 
			"        t_tasks.emp_bud_hr,\n" + 
			"        t_tasks.task_emp_ids,\n" + 
			"        t_tasks.task_status,\n" + 
			"        m_emp.emp_id,\n" + 
			"        m_emp.emp_name,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        m_cust_group.cust_group_name,\n" + 
			"        dm_fin_year.fin_year_name\n" + 
			"         (SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)            \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) \n" + 
			"            AND            i.task_id=t_tasks.task_id) as employees  ,'NA'  as owner_partner \n" + 
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
			"        m_emp.emp_id=:empId AND\n" +
			"		 FIND_IN_SET(:empId,t_tasks.task_emp_ids) AND\n" + 
			"        t_tasks.task_start_date BETWEEN :fromDate AND :toDate AND\n" + 
			"        m_services.serv_id=:service AND\n" +  
			"        m_activities.acti_id=:activity AND\n" +  
			"        t_tasks.actv_id=m_activities.acti_id AND\n" + 
			"        t_tasks.serv_id=m_services.serv_id AND\n" + 
			"        m_activities.periodicity_id=dm_periodicity.periodicity_id AND\n" + 
			"        t_tasks.cust_id=m_cust_header.cust_id AND\n" + 
			"        m_cust_header.cust_group_id=m_cust_group.cust_group_id AND\n" + 
			"        dm_fin_year.fin_year_id=t_tasks.task_fy_id", nativeQuery=true)

	List<TaskListHome> getTaskList(@Param("empId") int empId, @Param("fromDate") String fromDate, @Param("toDate") String toDate, 
			@Param("service") int service, @Param("activity") int activity);

/*****************************************************************************************/
	
	@Query(value="SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.mngr_bud_hr,\n" + 
			"        t_tasks.emp_bud_hr,\n" + 
			"        t_tasks.task_emp_ids,\n" + 
			"        t_tasks.ex_int1,\n" + 
			"        t_tasks.ex_int2,\n" + 
			"        t_tasks.ex_var1,\n" + 
			"        t_tasks.ex_var2,\n" + 
			"        dm_status_mst.status_text AS task_status,\n" + 
			"        dm_status_mst.status_color,\n" + 
			"        m_emp.emp_name,\n" + 
			"        m_emp.emp_id,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        m_cust_header.cust_firm_name AS cust_group_name,\n" + 
			"        dm_fin_year.fin_year_name,\n" + 
			"        (SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)                     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c              \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids)              \n" + 
			"            AND            i.task_id=t_tasks.task_id) as employees  ,\n" + 
			"        'NA'  as owner_partner                \n" + 
			"    FROM\n" + 
			"        t_tasks,\n" + 
			"        m_emp,\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        m_cust_header,\n" + 
			"        dm_fin_year,\n" + 
			"        dm_status_mst          \n" + 
			"    WHERE\n" + 
			"        t_tasks.del_status=1                                     \n" + 
			"        AND         m_services.ex_int1=1                                     \n" + 
			"        AND         m_activities.ex_int1=1                                     \n" + 
			"        AND         m_emp.emp_id=:empId                                    \n" + 
			"        AND         FIND_IN_SET(m_emp.emp_id,t_tasks.task_emp_ids)                                     \n" + 
			"        AND         t_tasks.task_statutory_due_date BETWEEN :fromDate AND :toDate                      \n" + 
			"        AND         m_services.serv_id=:service                                   \n" + 
			"        AND         m_activities.acti_id=:activity                                  \n" + 
			"        AND         m_cust_header.cust_id=:custId                                \n" + 
			"        AND         t_tasks.actv_id=m_activities.acti_id                                     \n" + 
			"        AND         m_activities.serv_id=m_services.serv_id                                     \n" + 
			"        AND         m_activities.periodicity_id=dm_periodicity.periodicity_id \n" + 
			"      	AND			dm_status_mst.status_value=:stats\n" + 
			"        AND      t_tasks.task_status=dm_status_mst.status_value                  \n" + 
			"        AND         t_tasks.cust_id=m_cust_header.cust_id                                      \n" + 
			"        AND         dm_fin_year.fin_year_id=t_tasks.task_fy_id              \n" + 
			"        AND    t_tasks.task_status NOT IN(:statusIds)", nativeQuery=true)
	List<TaskListHome> getTaskList(@Param("empId") int empId, @Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("service") int service, @Param("activity") int activity, @Param("custId") int custId, @Param("statusIds") List<String> statusIds,
			@Param("stats") int stats);

	@Query(value = "SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.mngr_bud_hr,\n" + 
			"        t_tasks.emp_bud_hr,\n" + 
			"        t_tasks.task_emp_ids,\n" + 
			"        t_tasks.ex_int1,\n" +
			"        t_tasks.ex_int2,\n" +
			"        t_tasks.ex_var1,\n" +
			"        t_tasks.ex_var2,\n" +
			"        dm_status_mst.status_text AS task_status, dm_status_mst.status_color,\n" + 
			"        m_emp.emp_name,\n" + 
			"        m_emp.emp_id,\n" +
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        CASE              \n" + 
			"            WHEN m_cust_header.cust_group_id=0 THEN m_cust_header.cust_firm_name                      \n" + 
			"            ELSE COALESCE(( SELECT\n" + 
			"                m_cust_group.cust_group_name                                            \n" + 
			"            FROM\n" + 
			"                m_cust_group                                           \n" + 
			"            WHERE\n" + 
			"                m_cust_group.cust_group_id=m_cust_header.cust_group_id                             \n" + 
			"                AND m_cust_group.del_status=1 ),\n" + 
			"            0)          \n" + 
			"        END AS cust_group_name,\n" + 
			"        dm_fin_year.fin_year_name, '' as employees   ,COALESCE(\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        m_emp.emp_name\n" + 
			"        from m_emp\n" + 
			"   \n" + 
			"    WHERE  \n" + 
			"        m_cust_header.owner_emp_id = m_emp.emp_id\n" + 
			"),\n" + 
			"'NA'\n" + 
			") AS owner_partner \n" + 
			"            \n" + 
			"    FROM\n" + 
			"        t_tasks,\n" + 
			"        m_emp,\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        m_cust_header,\n" + 
			"        dm_fin_year,\n" + 
			"        dm_status_mst     \n" + 
			"    WHERE\n" + 
			"        t_tasks.task_id=:taskId                           \n" + 
			"        AND         m_services.ex_int1=1                            \n" + 
			"        AND         m_activities.ex_int1=1                            \n" + 
			"        AND         m_emp.emp_id=:empType                           \n" + 
			"        AND         FIND_IN_SET(:empType,t_tasks.task_emp_ids)                            \n" + 
			"        AND         t_tasks.actv_id=m_activities.acti_id                            \n" + 
			"        AND         t_tasks.serv_id=m_services.serv_id                            \n" + 
			"        AND         m_activities.periodicity_id=dm_periodicity.periodicity_id                        \n" + 
			"        AND         t_tasks.cust_id=m_cust_header.cust_id                        \n" + 
			"        AND         dm_fin_year.fin_year_id=t_tasks.task_fy_id         \n" + 
			"        AND  	 	 dm_status_mst.status_value=t_tasks.task_status",nativeQuery=true)
TaskListHome getTaskById(@Param("empType") int empType, @Param("taskId") int taskId);

	
	@Query(value="SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.mngr_bud_hr,\n" + 
			"        t_tasks.emp_bud_hr,\n" + 
			"        t_tasks.task_emp_ids,\n" + 
			"        0 AS emp_name,\n" + 
			"        0 AS emp_id,\n" + 
			"        t_tasks.ex_int1,\n" + 
			"        t_tasks.ex_int2,\n" + 
			"        t_tasks.ex_var1,\n" + 
			"        t_tasks.ex_var2,\n" + 
			"        dm_status_mst.status_text AS task_status,\n" + 
			"        dm_status_mst.status_color,\n" + 
			"        \n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        CASE              \n" + 
			"            WHEN m_cust_header.cust_group_id=0 THEN m_cust_header.cust_firm_name                      \n" + 
			"            ELSE COALESCE(( SELECT\n" + 
			"                m_cust_group.cust_group_name                                            \n" + 
			"            FROM\n" + 
			"                m_cust_group                                           \n" + 
			"            WHERE\n" + 
			"                m_cust_group.cust_group_id=m_cust_header.cust_group_id                             \n" + 
			"                AND m_cust_group.del_status=1 ),\n" + 
			"            0)          \n" + 
			"        END AS cust_group_name,\n" + 
			"        dm_fin_year.fin_year_name,\n" + 
			"        (SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)            \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) \n" + 
			"            AND            i.task_id=t_tasks.task_id) as employees  ,'NA'  as owner_partner    \n" + 
			"    FROM\n" + 
			"        t_tasks,\n" + 
			"        \n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        m_cust_header,\n" + 
			"        dm_fin_year,\n" + 
			"        dm_status_mst     \n" + 
			"    WHERE\n" + 
			"        t_tasks.del_status=1                            \n" + 
			"        AND         m_services.ex_int1=1                            \n" + 
			"        AND         m_activities.ex_int1=1                            \n" + 
			"        AND         t_tasks.cust_id=:custId\n" + 
			"        AND		 m_activities.acti_id=:activity\n" + 
			"        AND		 m_services.serv_id=:service\n" + 
			"        AND         t_tasks.actv_id=m_activities.acti_id                            \n" + 
			"        AND         t_tasks.serv_id=m_services.serv_id                            \n" + 
			"        AND         m_activities.periodicity_id=dm_periodicity.periodicity_id                        \n" + 
			"        AND         t_tasks.cust_id=m_cust_header.cust_id                        \n" + 
			"        AND         dm_fin_year.fin_year_id=t_tasks.task_fy_id         \n" + 
			"        AND   		 dm_status_mst.status_value=t_tasks.task_status      \n" + 
			"        AND   		 t_tasks.task_status=0\n" + 
			"        AND 		 DATEDIFF(curdate(), t_tasks.task_statutory_due_date)>30 \n" + 
			"        AND 		 curdate()>=t_tasks.task_statutory_due_date",nativeQuery=true)
	List<TaskListHome> getUnallotedFilterdTaskList(@Param("service") int service, @Param("activity") int activity, @Param("custId") int custId);

	@Query(value="SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.mngr_bud_hr,\n" + 
			"        t_tasks.emp_bud_hr,\n" + 
			"        t_tasks.task_emp_ids,\n" + 
			"        0 AS emp_name,\n" + 
			"        0 AS emp_id,\n" + 
			"        t_tasks.ex_int1,\n" + 
			"        t_tasks.ex_int2,\n" + 
			"        t_tasks.ex_var1,\n" + 
			"        t_tasks.ex_var2,\n" + 
			"        dm_status_mst.status_text AS task_status,\n" + 
			"        dm_status_mst.status_color,\n" + 
			"        \n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        m_cust_header.cust_firm_name AS cust_group_name,\n" + 
			"        dm_fin_year.fin_year_name,\n" + 
			"        (SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)            \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) \n" + 
			"            AND            i.task_id=t_tasks.task_id) as employees ,'NA'  as owner_partner     \n" + 
			"    FROM\n" + 
			"        t_tasks,\n" + 
			"        \n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        m_cust_header,\n" + 
			"        dm_fin_year,\n" + 
			"        dm_status_mst     \n" + 
			"    WHERE\n" + 
			"        t_tasks.del_status=1                            \n" + 
			"        AND         m_services.ex_int1=1                            \n" + 
			"        AND         m_activities.ex_int1=1                            \n" + 
			"        AND         t_tasks.actv_id=m_activities.acti_id                            \n" + 
			"        AND         t_tasks.serv_id=m_services.serv_id                            \n" + 
			"        AND         m_activities.periodicity_id=dm_periodicity.periodicity_id                        \n" + 
			"        AND         t_tasks.cust_id=m_cust_header.cust_id                        \n" + 
			"        AND         dm_fin_year.fin_year_id=t_tasks.task_fy_id         \n" + 
			"        AND   		dm_status_mst.status_value=t_tasks.task_status      \n" + 
			"        AND   		t_tasks.task_status=0\n" + 
			"        AND 		DATEDIFF(curdate(), t_tasks.task_statutory_due_date)>30 \n" + 
			"        AND 		curdate()>=t_tasks.task_statutory_due_date",nativeQuery=true)
	List<TaskListHome> getAllotedTaskList();

	@Query(value="SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.mngr_bud_hr,\n" + 
			"        t_tasks.emp_bud_hr,\n" + 
			"        t_tasks.task_emp_ids,\n" + 
			"        0 AS emp_name,\n" + 
			"        0 AS emp_id,\n" + 
			"        t_tasks.ex_int1,\n" + 
			"        t_tasks.ex_int2,\n" + 
			"        t_tasks.ex_var1,\n" + 
			"        t_tasks.ex_var2,\n" + 
			"        dm_status_mst.status_text AS task_status,\n" + 
			"        dm_status_mst.status_color,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        m_cust_header.cust_firm_name AS cust_group_name,\n" + 
			"        dm_fin_year.fin_year_name,\n" + 
			"        (SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)                     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c              \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids)              \n" + 
			"            AND            i.task_id=t_tasks.task_id) as employees ,'NA'  as owner_partner          \n" + 
			"    FROM\n" + 
			"        t_tasks,\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        m_cust_header,\n" + 
			"        dm_fin_year,\n" + 
			"        dm_status_mst          \n" + 
			"    WHERE\n" + 
			"        t_tasks.del_status=1                                     \n" + 
			"        AND         m_services.ex_int1=1                            \n" + 
			"        AND         m_activities.ex_int1=1                            \n" + 
			"        AND         t_tasks.actv_id=m_activities.acti_id                                     \n" + 
			"        AND         t_tasks.serv_id=m_services.serv_id                                     \n" + 
			"        AND         m_activities.periodicity_id=dm_periodicity.periodicity_id                             \n" + 
			"        AND         t_tasks.cust_id=m_cust_header.cust_id                                 \n" + 
			"        AND         dm_fin_year.fin_year_id=t_tasks.task_fy_id                  \n" + 
			"        AND     dm_status_mst.status_value=t_tasks.task_status               \n" + 
			"        AND     dm_status_mst.status_value=0        \n" + 
			"        AND   DATEDIFF(curdate(), t_tasks.task_statutory_due_date)<5         \n" + 
			"        AND   curdate()>=t_tasks.task_statutory_due_date",nativeQuery=true)
	List<TaskListHome> getCriticalTaskTaskList();

	@Query(value="SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.mngr_bud_hr,\n" + 
			"        t_tasks.emp_bud_hr,\n" + 
			"        t_tasks.task_emp_ids,\n" + 
			"        0 AS emp_name,\n" + 
			"        0 AS emp_id,\n" + 
			"        t_tasks.ex_int1,\n" + 
			"        t_tasks.ex_int2,\n" + 
			"        t_tasks.ex_var1,\n" + 
			"        t_tasks.ex_var2,\n" + 
			"        dm_status_mst.status_text AS task_status,\n" + 
			"        dm_status_mst.status_color,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        m_cust_header.cust_firm_name AS cust_group_name,\n" + 
			"        dm_fin_year.fin_year_name,\n" + 
			"        (SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)                     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c              \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids)              \n" + 
			"            AND            i.task_id=t_tasks.task_id) as employees ,'NA'  as owner_partner         \n" + 
			"    FROM\n" + 
			"        t_tasks,\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        m_cust_header,\n" + 
			"        dm_fin_year,\n" + 
			"        dm_status_mst          \n" + 
			"    WHERE\n" + 
			"        t_tasks.del_status=1 \n" + 
			"        AND         m_services.ex_int1=1                            \n" + 
			"        AND         m_activities.ex_int1=1                            \n" + 
			"        AND			t_tasks.cust_id=:custId\n" + 
			"		AND		 	m_activities.acti_id=:activity\n" + 
			"		AND		 	m_services.serv_id=:service\n" + 
			"        AND         t_tasks.actv_id=m_activities.acti_id                                     \n" + 
			"        AND         t_tasks.serv_id=m_services.serv_id                                     \n" + 
			"        AND         m_activities.periodicity_id=dm_periodicity.periodicity_id                             \n" + 
			"        AND         t_tasks.cust_id=m_cust_header.cust_id                                 \n" + 
			"        AND         dm_fin_year.fin_year_id=t_tasks.task_fy_id                  \n" + 
			"        AND    	 	dm_status_mst.status_value=t_tasks.task_status               \n" + 
			"        AND     	dm_status_mst.status_value=0         \n" + 
			"        AND   		DATEDIFF(curdate(), t_tasks.task_statutory_due_date)<5         \n" + 
			"        AND   		curdate()>=t_tasks.task_statutory_due_date", nativeQuery=true)
	List<TaskListHome> getCriticalFilterdTaskList(@Param("service") int service, @Param("activity") int activity, @Param("custId") int custId);

	@Query(value="SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.mngr_bud_hr,\n" + 
			"        t_tasks.emp_bud_hr,\n" + 
			"        t_tasks.task_emp_ids,\n" + 
			"        0 AS emp_name,\n" + 
			"        0 AS emp_id,\n" + 
			"        t_tasks.ex_int1,\n" + 
			"        t_tasks.ex_int2,\n" + 
			"        t_tasks.ex_var1,\n" + 
			"        t_tasks.ex_var2,\n" + 
			"        dm_status_mst.status_text AS task_status,\n" + 
			"        dm_status_mst.status_color,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        m_cust_header.cust_firm_name AS cust_group_name,\n" + 
			"        dm_fin_year.fin_year_name,\n" + 
			"        (SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)                     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c              \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids)              \n" + 
			"            AND            i.task_id=t_tasks.task_id) as employees  ,'NA'  as owner_partner         \n" + 
			"    FROM\n" + 
			"        t_tasks,\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        m_cust_header,\n" + 
			"        dm_fin_year,\n" + 
			"        dm_status_mst          \n" + 
			"    WHERE\n" + 
			"        t_tasks.del_status=1       \n" + 
			"        AND         m_services.ex_int1=1                            \n" + 
			"        AND         m_activities.ex_int1=1                            \n" + 
			"        AND         t_tasks.actv_id=m_activities.acti_id                                     \n" + 
			"        AND         t_tasks.serv_id=m_services.serv_id                                     \n" + 
			"        AND         m_activities.periodicity_id=dm_periodicity.periodicity_id                             \n" + 
			"        AND         t_tasks.cust_id=m_cust_header.cust_id                                 \n" + 
			"        AND         dm_fin_year.fin_year_id=t_tasks.task_fy_id                  \n" + 
			"        AND    	 	dm_status_mst.status_value=t_tasks.task_status               \n" + 
			"        AND     	dm_status_mst.status_value=0             \n" + 
			"        AND   		curdate()<t_tasks.task_statutory_due_date",nativeQuery=true)
	List<TaskListHome> getOverdueTaskTaskList();
	
	@Query(value="SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.mngr_bud_hr,\n" + 
			"        t_tasks.emp_bud_hr,\n" + 
			"        t_tasks.task_emp_ids,\n" + 
			"        0 AS emp_name,\n" + 
			"        0 AS emp_id,\n" + 
			"        t_tasks.ex_int1,\n" + 
			"        t_tasks.ex_int2,\n" + 
			"        t_tasks.ex_var1,\n" + 
			"        t_tasks.ex_var2,\n" + 
			"        dm_status_mst.status_text AS task_status,\n" + 
			"        dm_status_mst.status_color,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        m_cust_header.cust_firm_name AS cust_group_name,\n" + 
			"        dm_fin_year.fin_year_name,\n" + 
			"        (SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)                     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c              \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids)              \n" + 
			"            AND            i.task_id=t_tasks.task_id) as employees   ,'NA'   as owner_partner        \n" + 
			"    FROM\n" + 
			"        t_tasks,\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        m_cust_header,\n" + 
			"        dm_fin_year,\n" + 
			"        dm_status_mst          \n" + 
			"    WHERE\n" + 
			"        t_tasks.del_status=1  \n" + 
			"        AND         m_services.ex_int1=1                            \n" + 
			"        AND         m_activities.ex_int1=1                            \n" + 
			"        AND			t_tasks.cust_id=:custId\n" + 
			"		AND		 	m_activities.acti_id=:activity\n" + 
			"		AND		 	m_services.serv_id=:service	\n" + 
			"        AND         t_tasks.actv_id=m_activities.acti_id                                     \n" + 
			"        AND         t_tasks.serv_id=m_services.serv_id                                     \n" + 
			"        AND         m_activities.periodicity_id=dm_periodicity.periodicity_id                             \n" + 
			"        AND         t_tasks.cust_id=m_cust_header.cust_id                                 \n" + 
			"        AND         dm_fin_year.fin_year_id=t_tasks.task_fy_id                  \n" + 
			"        AND    	 	dm_status_mst.status_value=t_tasks.task_status               \n" + 
			"        AND     	dm_status_mst.status_value=0             \n" + 
			"        AND   		curdate()<t_tasks.task_statutory_due_date",nativeQuery=true)
	List<TaskListHome> getFiltredOverdueTaskTaskList(@Param("service") int service, @Param("activity") int activity, @Param("custId") int custId);



	@Query(value="SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.mngr_bud_hr,\n" + 
			"        t_tasks.emp_bud_hr,\n" + 
			"        t_tasks.task_emp_ids,\n" + 
			"        t_tasks.ex_int1,\n" + 
			"        t_tasks.ex_int2,\n" + 
			"        t_tasks.ex_var1,\n" + 
			"        t_tasks.ex_var2,\n" + 
			"        dm_status_mst.status_text AS task_status,\n" + 
			"        dm_status_mst.status_color,\n" + 
			"        m_emp.emp_name,\n" + 
			"        m_emp.emp_id,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        m_cust_header.cust_firm_name AS cust_group_name,\n" + 
			"        dm_fin_year.fin_year_name,\n" + 
			"         (SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)            \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) \n" + 
			"            AND            i.task_id=t_tasks.task_id) as employees  ,'NA'  as owner_partner   \n" + 
			"       \n" + 
			"    FROM\n" + 
			"        t_tasks,\n" + 
			"        m_emp,\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        m_cust_header,\n" + 
			"        dm_fin_year,\n" + 
			"        dm_status_mst     \n" + 
			"    WHERE\n" + 
			"        t_tasks.del_status=1                            \n" + 
			"        AND         m_services.ex_int1=1                            \n" + 
			"        AND         m_activities.ex_int1=1                            \n" + 
			"        AND         m_emp.emp_id=:empId                           \n" + 
			"        AND         FIND_IN_SET(m_emp.emp_id,t_tasks.task_emp_ids)                            \n" + 
			"        AND         t_tasks.task_start_date BETWEEN :fromDate AND :toDate             \n" + 
			"                              \n" + 
			"                               \n" + 
			"                           \n" + 
			"        AND         t_tasks.actv_id=m_activities.acti_id                            \n" + 
			"        AND         m_activities.serv_id=m_services.serv_id                            \n" + 
			"        AND         m_activities.periodicity_id=dm_periodicity.periodicity_id                 \n" + 
			"        AND   	 	t_tasks.task_status=dm_status_mst.status_value         \n" + 
			"        AND         t_tasks.cust_id=m_cust_header.cust_id                             \n" + 
			"        AND         dm_fin_year.fin_year_id=t_tasks.task_fy_id     \n" + 
			"        AND  		t_tasks.task_status=:stat AND t_tasks.map_id=:mapId  ", nativeQuery=true)
	List<TaskListHome> getManualTaskList(@Param("stat") int stat,@Param("empId") int empId,@Param("mapId") int mapId);

//*********************************Dash Board Count Task***************************************************
	
	
	@Query(value="SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        CONCAT(FLOOR( t_tasks.mngr_bud_hr/60),':',MOD( t_tasks.mngr_bud_hr,60)) as mngr_bud_hr,\n" + 
			"         CONCAT(FLOOR( t_tasks.emp_bud_hr/60),':',MOD( t_tasks.emp_bud_hr,60)) as emp_bud_hr ,\n" + 
			"        t_tasks.task_emp_ids,\n" + 
			"        t_tasks.ex_int1,\n" + 
			"        t_tasks.ex_int2,\n" + 
			"        t_tasks.ex_var1,\n" + 
			"        t_tasks.ex_var2,\n" + 
			"        dm_status_mst.status_text AS task_status,\n" + 
			"        dm_status_mst.status_color,\n" + 
			"        m_emp.emp_name,\n" + 
			"        m_emp.emp_id,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        m_cust_header.cust_firm_name AS cust_group_name,\n" + 
			"        dm_fin_year.fin_year_name,\n" + 
			"        (     SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) \n" + 
			"            AND i.task_id = t_tasks.task_id ) AS employees,\n" + 
			"        COALESCE(     (     SELECT\n" + 
			"            m_emp.emp_name         \n" + 
			"        from\n" + 
			"            m_emp         \n" + 
			"        WHERE\n" + 
			"            m_cust_header.owner_emp_id = m_emp.emp_id ),\n" + 
			"        'NA'  ) AS owner_partner  \n" + 
			"    FROM\n" + 
			"        t_tasks,\n" + 
			"        m_emp,\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        m_cust_header,\n" + 
			"        dm_fin_year,\n" + 
			"        dm_status_mst \n" + 
			"    WHERE\n" + 
			"        t_tasks.del_status = 1 AND   t_tasks.is_active=1 \n" + 
			"        AND m_emp.emp_id =:empId \n" + 
			"        AND FIND_IN_SET(:empId, t_tasks.task_emp_ids)  AND FIND_IN_SET(:userId, t_tasks.task_emp_ids) \n" + 
			"        AND t_tasks.actv_id = m_activities.acti_id \n" + 
			"        AND t_tasks.serv_id = m_services.serv_id \n" + 
			"        AND m_activities.periodicity_id = dm_periodicity.periodicity_id \n" + 
			"        AND t_tasks.cust_id = m_cust_header.cust_id \n" + 
			"        AND dm_fin_year.fin_year_id = t_tasks.task_fy_id \n" + 
			"        AND dm_status_mst.status_value = t_tasks.task_status \n" + 
			"        AND t_tasks.task_status =:stat AND  task_end_date < :endDate ", nativeQuery=true)
	
	List<TaskListHome> getManualTaskListDashOverDue(@Param("stat") int stat,@Param("empId") int empId,@Param("endDate") String  endDate,@Param("userId") int userId);
	


	@Query(value="SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        CONCAT(FLOOR( t_tasks.mngr_bud_hr/60),':',MOD( t_tasks.mngr_bud_hr,60)) as mngr_bud_hr,\n" + 
			"         CONCAT(FLOOR( t_tasks.emp_bud_hr/60),':',MOD( t_tasks.emp_bud_hr,60)) as emp_bud_hr ,\n" + 
			"        t_tasks.task_emp_ids,\n" + 
			"        t_tasks.ex_int1,\n" + 
			"        t_tasks.ex_int2,\n" + 
			"        t_tasks.ex_var1,\n" + 
			"        t_tasks.ex_var2,\n" + 
			"        dm_status_mst.status_text AS task_status,\n" + 
			"        dm_status_mst.status_color,\n" + 
			"        m_emp.emp_name,\n" + 
			"        m_emp.emp_id,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        m_cust_header.cust_firm_name AS cust_group_name,\n" + 
			"        dm_fin_year.fin_year_name,\n" + 
			"        (     SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) \n" + 
			"            AND i.task_id = t_tasks.task_id ) AS employees,\n" + 
			"        COALESCE(     (     SELECT\n" + 
			"            m_emp.emp_name         \n" + 
			"        from\n" + 
			"            m_emp         \n" + 
			"        WHERE\n" + 
			"            m_cust_header.owner_emp_id = m_emp.emp_id ),\n" + 
			"        'NA'  ) AS owner_partner  \n" + 
			"    FROM\n" + 
			"        t_tasks,\n" + 
			"        m_emp,\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        m_cust_header,\n" + 
			"        dm_fin_year,\n" + 
			"        dm_status_mst \n" + 
			"    WHERE\n" + 
			"           t_tasks.del_status = 1 AND   t_tasks.is_active=1  \n" + 
			"        AND m_emp.emp_id =:empId \n" + 
			"        AND FIND_IN_SET(:empId, t_tasks.task_emp_ids) AND FIND_IN_SET(:userId, t_tasks.task_emp_ids) \n" + 
			"        AND t_tasks.actv_id = m_activities.acti_id \n" + 
			"        AND t_tasks.serv_id = m_services.serv_id \n" + 
			"        AND m_activities.periodicity_id = dm_periodicity.periodicity_id \n" + 
			"        AND t_tasks.cust_id = m_cust_header.cust_id \n" + 
			"        AND dm_fin_year.fin_year_id = t_tasks.task_fy_id \n" + 
			"        AND dm_status_mst.status_value = t_tasks.task_status \n" + 
			"        AND t_tasks.task_status =:stat AND  t_tasks.task_end_date=:endDate ", nativeQuery=true)
	
	List<TaskListHome> getManualTaskListDashDueToday(@Param("stat") int stat,@Param("empId") int empId,@Param("endDate") String  endDate,@Param("userId") int userId);


	
	@Query(value="SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        CONCAT(FLOOR( t_tasks.mngr_bud_hr/60),':',MOD( t_tasks.mngr_bud_hr,60)) as mngr_bud_hr,\n" + 
			"         CONCAT(FLOOR( t_tasks.emp_bud_hr/60),':',MOD( t_tasks.emp_bud_hr,60)) as emp_bud_hr ,\n" + 
			"        t_tasks.task_emp_ids,\n" + 
			"        t_tasks.ex_int1,\n" + 
			"        t_tasks.ex_int2,\n" + 
			"        t_tasks.ex_var1,\n" + 
			"        t_tasks.ex_var2,\n" + 
			"        dm_status_mst.status_text AS task_status,\n" + 
			"        dm_status_mst.status_color,\n" + 
			"        m_emp.emp_name,\n" + 
			"        m_emp.emp_id,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        m_cust_header.cust_firm_name AS cust_group_name,\n" + 
			"        dm_fin_year.fin_year_name,\n" + 
			"        (     SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) \n" + 
			"            AND i.task_id = t_tasks.task_id ) AS employees,\n" + 
			"        COALESCE(     (     SELECT\n" + 
			"            m_emp.emp_name         \n" + 
			"        from\n" + 
			"            m_emp         \n" + 
			"        WHERE\n" + 
			"            m_cust_header.owner_emp_id = m_emp.emp_id ),\n" + 
			"        'NA'  ) AS owner_partner  \n" + 
			"    FROM\n" + 
			"        t_tasks,\n" + 
			"        m_emp,\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        m_cust_header,\n" + 
			"        dm_fin_year,\n" + 
			"        dm_status_mst \n" + 
			"    WHERE\n" + 
			"          t_tasks.del_status = 1 AND   t_tasks.is_active=1  \n" + 
			"        AND m_emp.emp_id =:empId \n" + 
			"        AND FIND_IN_SET(:empId, t_tasks.task_emp_ids) AND FIND_IN_SET(:userId, t_tasks.task_emp_ids) \n" + 
			"        AND t_tasks.actv_id = m_activities.acti_id \n" + 
			"        AND t_tasks.serv_id = m_services.serv_id \n" + 
			"        AND m_activities.periodicity_id = dm_periodicity.periodicity_id \n" + 
			"        AND t_tasks.cust_id = m_cust_header.cust_id \n" + 
			"        AND dm_fin_year.fin_year_id = t_tasks.task_fy_id \n" + 
			"        AND dm_status_mst.status_value = t_tasks.task_status \n" + 
			"        AND t_tasks.task_status =:stat AND   WEEKOFYEAR(t_tasks.task_end_date)=WEEKOFYEAR(:endDate)  \n" + 
			"            and YEAR(:endDate) = YEAR(t_tasks.task_end_date)", nativeQuery=true)
	
	List<TaskListHome> getManualTaskListDashDueWeek(@Param("stat") int stat,@Param("empId") int empId,@Param("endDate") String  endDate,@Param("userId") int userId);




	@Query(value="SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        CONCAT(FLOOR( t_tasks.mngr_bud_hr/60),':',MOD( t_tasks.mngr_bud_hr,60)) as mngr_bud_hr,\n" + 
			"         CONCAT(FLOOR( t_tasks.emp_bud_hr/60),':',MOD( t_tasks.emp_bud_hr,60)) as emp_bud_hr ,\n" + 
			"        t_tasks.task_emp_ids,\n" + 
			"        t_tasks.ex_int1,\n" + 
			"        t_tasks.ex_int2,\n" + 
			"        t_tasks.ex_var1,\n" + 
			"        t_tasks.ex_var2,\n" + 
			"        dm_status_mst.status_text AS task_status,\n" + 
			"        dm_status_mst.status_color,\n" + 
			"        m_emp.emp_name,\n" + 
			"        m_emp.emp_id,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        m_cust_header.cust_firm_name AS cust_group_name,\n" + 
			"        dm_fin_year.fin_year_name,\n" + 
			"        (     SELECT\n" + 
			"            GROUP_CONCAT(DISTINCT c.emp_name)     \n" + 
			"        FROM\n" + 
			"            t_tasks i,\n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(c.emp_id, task_emp_ids) \n" + 
			"            AND i.task_id = t_tasks.task_id ) AS employees,\n" + 
			"        COALESCE(     (     SELECT\n" + 
			"            m_emp.emp_name         \n" + 
			"        from\n" + 
			"            m_emp         \n" + 
			"        WHERE\n" + 
			"            m_cust_header.owner_emp_id = m_emp.emp_id ),\n" + 
			"        'NA'  ) AS owner_partner  \n" + 
			"    FROM\n" + 
			"        t_tasks,\n" + 
			"        m_emp,\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        m_cust_header,\n" + 
			"        dm_fin_year,\n" + 
			"        dm_status_mst \n" + 
			"    WHERE\n" + 
			"       t_tasks.del_status = 1 AND   t_tasks.is_active=1  \n" +
			"        AND m_emp.emp_id =:empId \n" + 
			"        AND FIND_IN_SET(:empId, t_tasks.task_emp_ids) AND FIND_IN_SET(:userId, t_tasks.task_emp_ids) \n" + 
			"        AND t_tasks.actv_id = m_activities.acti_id \n" + 
			"        AND t_tasks.serv_id = m_services.serv_id \n" + 
			"        AND m_activities.periodicity_id = dm_periodicity.periodicity_id \n" + 
			"        AND t_tasks.cust_id = m_cust_header.cust_id \n" + 
			"        AND dm_fin_year.fin_year_id = t_tasks.task_fy_id \n" + 
			"        AND dm_status_mst.status_value = t_tasks.task_status \n" + 
			"        AND t_tasks.task_status =:stat AND  MONTH(t_tasks.task_end_date)=MONTH(:endDate)\n" + 
			"            and YEAR(:endDate) = YEAR(t_tasks.task_end_date)", nativeQuery=true)
	
	List<TaskListHome> getManualTaskListDashDueMonth(@Param("stat") int stat,@Param("empId") int empId,@Param("endDate") String  endDate,@Param("userId") int userId);




@Query(value="SELECT\n" + 
		"        t_tasks.task_id,\n" + 
		"        t_tasks.task_text,\n" + 
		"        t_tasks.task_start_date,\n" + 
		"        t_tasks.task_end_date,\n" + 
		"        t_tasks.task_statutory_due_date,\n" + 
		"        CONCAT(FLOOR( t_tasks.mngr_bud_hr/60),':',MOD( t_tasks.mngr_bud_hr,60)) as mngr_bud_hr,\n" + 
		"         CONCAT(FLOOR( t_tasks.emp_bud_hr/60),':',MOD( t_tasks.emp_bud_hr,60)) as emp_bud_hr ,\n" + 
		"        t_tasks.task_emp_ids,\n" + 
		"        t_tasks.ex_int1,\n" + 
		"        t_tasks.ex_int2,\n" + 
		"        t_tasks.ex_var1,\n" + 
		"        t_tasks.ex_var2,\n" + 
		"        dm_status_mst.status_text AS task_status,\n" + 
		"        dm_status_mst.status_color,\n" + 
		"        m_emp.emp_name,\n" + 
		"        m_emp.emp_id,\n" + 
		"        m_services.serv_name,\n" + 
		"        m_activities.acti_name,\n" + 
		"        dm_periodicity.periodicity_name,\n" + 
		"        m_cust_header.cust_firm_name AS cust_group_name,\n" + 
		"        dm_fin_year.fin_year_name,\n" + 
		"        (SELECT\n" + 
		"            GROUP_CONCAT(DISTINCT c.emp_name)                     \n" + 
		"        FROM\n" + 
		"            t_tasks i,\n" + 
		"            m_emp c              \n" + 
		"        WHERE\n" + 
		"            FIND_IN_SET(c.emp_id, task_emp_ids)              \n" + 
		"            AND            i.task_id=t_tasks.task_id) as employees  ,\n" + 
		"        'NA'  as owner_partner                \n" + 
		"    FROM\n" + 
		"        t_tasks,\n" + 
		"        m_emp,\n" + 
		"        m_services,\n" + 
		"        m_activities,\n" + 
		"        dm_periodicity,\n" + 
		"        m_cust_header,\n" + 
		"        dm_fin_year,\n" + 
		"        dm_status_mst          \n" + 
		"    WHERE\n" + 
		"        t_tasks.del_status=1                                     \n" + 
		"        AND         m_services.ex_int1=1                                     \n" + 
		"        AND         m_activities.ex_int1=1                                     \n" + 
		"        AND         m_emp.emp_id=:empId                                   \n" + 
		"        AND         FIND_IN_SET(m_emp.emp_id,t_tasks.task_emp_ids)                                     \n" + 
		"        AND         t_tasks.task_statutory_due_date BETWEEN :fromDate AND :toDate                     \n" + 
		"        AND         m_services.serv_id=:service                                 \n" + 
		"        AND         m_activities.acti_id=:activity                                 \n" + 
		"        AND         m_cust_header.cust_id=:custId                                \n" + 
		"        AND         t_tasks.actv_id=m_activities.acti_id                                     \n" + 
		"        AND         m_activities.serv_id=m_services.serv_id                                     \n" + 
		"        AND         m_activities.periodicity_id=dm_periodicity.periodicity_id \n" + 
		"        AND      t_tasks.task_status=dm_status_mst.status_value                  \n" + 
		"        AND         t_tasks.cust_id=m_cust_header.cust_id                                      \n" + 
		"        AND         dm_fin_year.fin_year_id=t_tasks.task_fy_id              \n" + 
		"        AND    t_tasks.task_status NOT IN(:statusIds)",nativeQuery=true)
	List<TaskListHome> getTaskList(@Param("empId") int empId, @Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("service") int service, @Param("activity") int activity, @Param("custId") int custId, @Param("statusIds") List<String> statusIds);

@Query(value="SELECT\n" + 
		"        t_tasks.task_id,\n" + 
		"        t_tasks.task_text,\n" + 
		"        t_tasks.task_start_date,\n" + 
		"        t_tasks.task_end_date,\n" + 
		"        t_tasks.task_statutory_due_date,\n" + 
		"        CONCAT(FLOOR( t_tasks.mngr_bud_hr/60),\n" + 
		"        ':',\n" + 
		"        MOD( t_tasks.mngr_bud_hr,\n" + 
		"        60)) as mngr_bud_hr,\n" + 
		"        CONCAT(FLOOR( t_tasks.emp_bud_hr/60),\n" + 
		"        ':',\n" + 
		"        MOD( t_tasks.emp_bud_hr,\n" + 
		"        60)) as emp_bud_hr ,\n" + 
		"        t_tasks.task_emp_ids,\n" + 
		"        t_tasks.task_status AS ex_int1,\n" + 
		"        t_tasks.ex_int2,\n" + 
		"        t_tasks.ex_var1,\n" + 
		"        t_tasks.ex_var2,\n" + 
		"        dm_status_mst.status_text AS task_status,\n" + 
		"        dm_status_mst.status_color,\n" + 
		"        m_emp.emp_name,\n" + 
		"        m_emp.emp_id,\n" + 
		"        m_services.serv_name,\n" + 
		"        m_activities.acti_name,\n" + 
		"        dm_periodicity.periodicity_name,\n" + 
		"        m_cust_header.cust_firm_name AS cust_group_name,\n" + 
		"        dm_fin_year.fin_year_name,\n" + 
		"        (     SELECT\n" + 
		"            GROUP_CONCAT(DISTINCT c.emp_name)     \n" + 
		"        FROM\n" + 
		"            t_tasks i,\n" + 
		"            m_emp c     \n" + 
		"        WHERE\n" + 
		"            FIND_IN_SET(c.emp_id, task_emp_ids) \n" + 
		"            AND i.task_id = t_tasks.task_id ) AS employees,\n" + 
		"        COALESCE(     (     SELECT\n" + 
		"            m_emp.emp_name         \n" + 
		"        from\n" + 
		"            m_emp         \n" + 
		"        WHERE\n" + 
		"            m_cust_header.owner_emp_id = m_emp.emp_id ),\n" + 
		"        'NA'  ) AS owner_partner  \n" + 
		"    FROM\n" + 
		"        t_tasks,\n" + 
		"        m_emp,\n" + 
		"        m_services,\n" + 
		"        m_activities,\n" + 
		"        dm_periodicity,\n" + 
		"        m_cust_header,\n" + 
		"        dm_fin_year,\n" + 
		"        dm_status_mst \n" + 
		"    WHERE\n" + 
		"        t_tasks.del_status = 1 \n" + 
		"        AND t_tasks.is_active=1 \n" + 
		"        AND m_services.ex_int1 = 1 \n" + 
		"        AND m_activities.ex_int1 = 1 \n" + 
		"        AND m_emp.emp_id =:empId \n" + 
		"        AND FIND_IN_SET(:empId, t_tasks.task_emp_ids) \n" + 
		"        AND t_tasks.actv_id = m_activities.acti_id \n" + 
		"        AND t_tasks.serv_id = m_services.serv_id \n" + 
		"        AND t_tasks.task_statutory_due_date BETWEEN :fromDate AND :toDate\n" + 
		"        AND m_activities.periodicity_id = dm_periodicity.periodicity_id \n" + 
		"        AND t_tasks.cust_id = m_cust_header.cust_id \n" + 
		"        AND dm_fin_year.fin_year_id = t_tasks.task_fy_id \n" + 
		"        AND dm_status_mst.status_value = t_tasks.task_status \n" + 
		"        AND t_tasks.task_status =:stats\n" + 
		"        AND t_tasks.task_status NOT IN(:statusIds)",nativeQuery=true)
List<TaskListHome> getTaskListByStatus(@Param("empId") int empId, @Param("fromDate") String fromDate, @Param("toDate") String toDate,
		@Param("stats") int stats,@Param("statusIds") List<String> statusIds);







}
