package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.TaskListHome;

public interface TaskListHomeRepo extends JpaRepository<TaskListHome, Integer> {

	@Query(value="SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.mngr_bud_hr,\n" + 
			"        t_tasks.emp_bud_hr,\n" + 
			"        t_tasks.task_emp_ids,\n" + 
			"        dm_status_mst.status_text AS task_status, dm_status_mst.status_color,\n" + 
			"        m_emp.emp_name,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        CASE \n" + 
			"            WHEN m_cust_header.cust_group_id=0 THEN m_cust_header.cust_firm_name         \n" + 
			"            ELSE COALESCE(( SELECT\n" + 
			"                m_cust_group.cust_group_name                               \n" + 
			"            FROM\n" + 
			"                m_cust_group                              \n" + 
			"            WHERE\n" + 
			"                m_cust_group.cust_group_id=m_cust_header.cust_group_id            \n" + 
			"                AND m_cust_group.del_status=1 ),\n" + 
			"            0) \n" + 
			"        END AS cust_group_name,\n" + 
			"        dm_fin_year.fin_year_name                    \n" + 
			"    FROM\n" + 
			"        t_tasks,\n" + 
			"        m_emp,\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        m_cust_header,\n" + 
			"        dm_fin_year,\n" + 
			"        dm_status_mst\n" + 
			"    WHERE\n" + 
			"        t_tasks.del_status=1                   \n" + 
			"        AND         m_emp.emp_id=:empId                   \n" + 
			"        AND     	 FIND_IN_SET(:empId,t_tasks.task_emp_ids)                   \n" + 
			"        AND         t_tasks.actv_id=m_activities.acti_id                   \n" + 
			"        AND         t_tasks.serv_id=m_services.serv_id                   \n" + 
			"        AND         m_activities.periodicity_id=dm_periodicity.periodicity_id               \n" + 
			"        AND         t_tasks.cust_id=m_cust_header.cust_id               \n" + 
			"        AND         dm_fin_year.fin_year_id=t_tasks.task_fy_id\n" + 
			"        AND		 dm_status_mst.status_value=t_tasks.task_status", nativeQuery=true)
	List<TaskListHome> getTaskList(@Param("empId") int empId);

	/**************************************************************************/
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
	List<TaskListHome> getTaskList(@Param("empId")int empId, @Param("fromDate") String fromDate, @Param("toDate") String toDate);
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
			"        dm_status_mst.status_text AS task_status,\n" + 
			"        m_emp.emp_name,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        CASE \n" + 
			"            WHEN m_cust_header.cust_group_id=0           THEN m_cust_header.cust_firm_name     \n" + 
			"            ELSE COALESCE(( SELECT\n" + 
			"                m_cust_group.cust_group_name                         \n" + 
			"            FROM\n" + 
			"                m_cust_group                                   \n" + 
			"            WHERE\n" + 
			"                m_cust_group.cust_group_id=m_cust_header.cust_group_id        \n" + 
			"                AND m_cust_group.del_status=1 ),\n" + 
			"            0)        \n" + 
			"        END AS cust_group_name,\n" + 
			"        dm_fin_year.fin_year_name                    \n" + 
			"    FROM\n" + 
			"        t_tasks,\n" + 
			"        m_emp,\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        m_cust_group,\n" + 
			"        m_cust_header,\n" + 
			"        dm_fin_year,\n" + 
			"        dm_status_mst\n" + 
			"    WHERE\n" + 
			"        t_tasks.del_status=1                   \n" + 
			"        AND         m_emp.emp_id=2                  \n" + 
			"        AND         FIND_IN_SET(2,t_tasks.task_emp_ids)                   \n" + 
			"        AND         t_tasks.task_start_date BETWEEN '2019-08-01' AND '2019-08-22'            \n" + 
			"        AND         m_services.serv_id=1                 \n" + 
			"        AND         m_activities.acti_id=5                \n" + 
			"        AND         m_cust_group.cust_group_id=8                \n" + 
			"        AND         t_tasks.actv_id=m_activities.acti_id                   \n" + 
			"        AND         t_tasks.serv_id=m_services.serv_id                   \n" + 
			"        AND         m_activities.periodicity_id=dm_periodicity.periodicity_id                   	  AND		  t_tasks.task_status=dm_status_mst.status_value\n" + 
			"        AND         t_tasks.cust_id=m_cust_header.cust_id                  \n" + 
			"        AND         m_cust_header.cust_group_id=m_cust_group.cust_group_id                   \n" + 
			"        AND         dm_fin_year.fin_year_id=t_tasks.task_fy_id", nativeQuery=true)
	List<TaskListHome> getTaskList(@Param("empId") int empId, @Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("service") int service, @Param("activity") int activity, @Param("custId") int custId);

	@Query(value = "SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.mngr_bud_hr,\n" + 
			"        t_tasks.emp_bud_hr,\n" + 
			"        t_tasks.task_emp_ids,\n" + 
			"        dm_status_mst.status_text AS task_status, dm_status_mst.status_color,\n" + 
			"        m_emp.emp_name,\n" + 
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
			"        dm_fin_year.fin_year_name                         \n" + 
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
			"        AND         m_emp.emp_id=:empType                           \n" + 
			"        AND         FIND_IN_SET(:empType,t_tasks.task_emp_ids)                            \n" + 
			"        AND         t_tasks.actv_id=m_activities.acti_id                            \n" + 
			"        AND         t_tasks.serv_id=m_services.serv_id                            \n" + 
			"        AND         m_activities.periodicity_id=dm_periodicity.periodicity_id                        \n" + 
			"        AND         t_tasks.cust_id=m_cust_header.cust_id                        \n" + 
			"        AND         dm_fin_year.fin_year_id=t_tasks.task_fy_id         \n" + 
			"        AND  	 	 dm_status_mst.status_value=t_tasks.task_status",nativeQuery=true)
TaskListHome getTaskById(@Param("empType") int empType, @Param("taskId") int taskId);
	
	
}
