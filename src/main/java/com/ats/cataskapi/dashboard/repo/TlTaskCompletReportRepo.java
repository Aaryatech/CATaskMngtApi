package com.ats.cataskapi.dashboard.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.report.TlTaskCompletReport;

public interface TlTaskCompletReportRepo extends JpaRepository<TlTaskCompletReport, Integer>{

	@Query(value="SELECT \n" + 
			"	b.*,coalesce((c.admin),'-')as admin,\n" + 
			"    coalesce((c.partner),'-') as execution_partner ,\n" + 
			"    coalesce((c.manager),'-') as manager_name,\n" + 
			"    coalesce((c.team_leader),'-') as team_lead_name,\n" + 
			"    coalesce((c.employee),'-') as  employee,\n" + 
			"      coalesce((c.employee_hrs),'-') as  ttl_emp_hrs,\n" + 
			"      coalesce((c.tl_hrs),'-') as  tl_total_hrs,\n" + 
			"      0 as tl_period_hrs\n" + 
			"FROM\n" + 
			"	(SELECT\n" + 
			"    		t_tasks.task_id,t_tasks.task_text,\n" + 
			"   			m_services.serv_name AS service,\n" + 
			"    		m_activities.acti_name AS activity,\n" + 
			"    		dm_periodicity.periodicity_name as task_periodicity,\n" + 
			"    		t_tasks.task_statutory_due_date AS due_date,\n" + 
			"    		t_tasks.task_start_date AS start_date,\n" + 
			"    		t_tasks.task_end_date as completion_date,\n" + 
			"    		CONCAT( FLOOR(t_tasks.emp_bud_hr / 60),'.', MOD(t_tasks.emp_bud_hr, 60)) as emp_bud_hr,\n" + 
			"    		CONCAT( FLOOR(t_tasks.mngr_bud_hr / 60),'.', MOD(t_tasks.mngr_bud_hr, 60)) as mngr_bud_hr, \n" + 
			"     		CASE WHEN m_cust_header.cust_group_id = 0 \n" + 
			"     		THEN m_cust_header.cust_firm_name \n" + 
			"     		ELSE COALESCE((SELECT\n" + 
			"                                m_cust_group.cust_group_name\n" + 
			"                           FROM\n" + 
			"                                m_cust_group\n" + 
			"                           WHERE\n" + 
			"                                m_cust_group.cust_group_id = m_cust_header.cust_group_id AND m_cust_group.del_status = 1), 0) END AS client_name, \n" + 
			"        \n" + 
			"         	CONCAT( FLOOR( SUM(t_daily_work_log.work_hours) / 60),'.', MOD(SUM(t_daily_work_log.work_hours), 60)) AS work_hours\n" + 
			"   \n" + 
			"	FROM\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"     	t_tasks,  \n" + 
			"        m_cust_header,\n" + 
			"        t_daily_work_log\n" + 
			"  \n" + 
			"	WHERE\n" + 
			"         m_services.serv_id = t_tasks.serv_id AND \n" + 
			"         m_activities.acti_id = t_tasks.actv_id AND \n" + 
			"         dm_periodicity.periodicity_id = t_tasks.periodicity_id AND 			\n" + 
			"         t_tasks.task_status = 9 AND \n" + 
			"         t_tasks.del_status = 1 AND \n" + 
			"         t_tasks.is_active = 1  AND  \n" + 
			"         t_tasks.cust_id = m_cust_header.cust_id AND 							\n" + 
			"         t_daily_work_log.task_id=t_tasks.task_id  AND \n" + 
			"         t_tasks.update_datetime between :fromDate AND :toDate \n" + 
			"         group by t_tasks.task_id) b\n" + 
			"\n" + 
			" LEFT JOIN\n" + 
			" \n" + 
			" (\n" + 
			" SELECT\n" + 
			"     	a.task_id,\n" + 
			"     	 group_concat(CASE a.emp_type WHEN '1' THEN a.emp_name  END) admin,\n" + 
			"        group_concat(CASE a.emp_type WHEN '2' THEN a.emp_name  END) partner,\n" + 
			"        group_concat(CASE a.emp_type WHEN '3' THEN a.emp_name  END) manager,\n" + 
			"        group_concat(CASE a.emp_type WHEN '4' THEN a.emp_name END) team_leader,\n" + 
			"        group_concat(CASE a.emp_type WHEN '5' THEN a.emp_name END) employee,\n" + 
			"        group_concat(CASE a.emp_type WHEN '5' THEN a.hours END) employee_hrs,\n" + 
			"        group_concat(CASE a.emp_type WHEN '4' THEN a.hours END) tl_hrs\n" + 
			"\n" + 
			"FROM\n" + 
			"	(SELECT \n" + 
			"     		t_tasks.task_id,\n" + 
			"            m_emp.emp_id,\n" + 
			"            m_emp.emp_type,\n" + 
			"            m_emp.emp_name,\n" + 
			"            coalesce((SELECT \n" + 
			"		\n" + 
			"		CONCAT( FLOOR( SUM(t_daily_work_log.work_hours) / 60),'.', MOD(SUM(t_daily_work_log.work_hours), 60)) \n" + 
			"FROM 	\n" + 
			"	t_daily_work_log\n" + 
			"WHERE\n" + 
			"t_daily_work_log.emp_id=m_emp.emp_id and t_daily_work_log.task_id=t_tasks.task_id\n" + 
			"GROUP BY\n" + 
			"		t_daily_work_log.task_id),0) hours\n" + 
			"     FROM \n" + 
			"     		m_emp,\n" + 
			"     		t_tasks \n" + 
			"     WHERE \n" + 
			"     		FIND_IN_SET(m_emp.emp_id,t_tasks.task_emp_ids) AND \n" + 
			"     		t_tasks.task_id IN  (SELECT\n" + 
			"   										t_tasks.task_id\n" + 
			"   \n" + 
			"								FROM\n" + 
			"   									t_tasks,t_daily_work_log\n" + 
			"								WHERE\n" + 
			"                                    t_tasks.task_status = 9 AND \n" + 
			"                                    t_tasks.del_status = 1 AND \n" + 
			"                                    t_tasks.is_active = 1  AND    \n" + 
			"                                    t_daily_work_log.task_id=t_tasks.task_id  AND  \n" + 
			"                                    t_tasks.update_datetime between :fromDate AND :toDate \n" + 
			"                                    group by t_tasks.task_id)\n" + 
			"    ) a group by a.task_id) c ON b.task_id=c.task_id", nativeQuery=true)
	List<TlTaskCompletReport> getTlCompleteTaskReport(@Param("fromDate") String fromDate, @Param("toDate") String toDate); 
}
