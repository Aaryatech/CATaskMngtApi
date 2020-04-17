package com.ats.cataskapi.report.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.report.CompletedTaskReport;
 
public interface CompletedTaskReportRepo extends JpaRepository<CompletedTaskReport, Integer>{
	
	@Query(value="SELECT b.*,coalesce((c.admin),'-')as admin,coalesce((c.partner),'-') as partner ,coalesce((c.manager),'-') as manager,coalesce((c.team_leader),'-') as team_leader,coalesce((c.employee),'-') as  employee from (SELECT\n" + 
			"    t_tasks.task_id,t_tasks.task_text,t_tasks.ex_var1,\n" + 
			"    m_services.serv_name,\n" + 
			"    m_activities.acti_name,\n" + 
			"    dm_periodicity.periodicity_name,\n" + 
			"    t_tasks.task_statutory_due_date,\n" + 
			"    t_tasks.task_completion_date as task_start_date,\n" + 
			"      t_tasks.update_datetime AS task_end_date,CONCAT(FLOOR( t_tasks.emp_bud_hr/60),':',LPAD(MOD(t_tasks.emp_bud_hr, 60), 2, '0'))  as emp_bud_hr, \n" + 
			" \n" + 
			"  CONCAT(FLOOR( t_tasks.mngr_bud_hr/60),':',LPAD(MOD(t_tasks.mngr_bud_hr, 60), 2, '0')) as mngr_bud_hr,\n" + 
			"   m_cust_header.cust_firm_name ,\n" + 
			"       \n" + 
			"        COALESCE( (CONCAT(\n" + 
			"        FLOOR(\n" + 
			"            SUM(t_daily_work_log.work_hours) / 60\n" + 
			"        ),\n" + 
			"        '.',\n" + 
			"      LPAD(  MOD(\n" + 
			"            SUM(t_daily_work_log.work_hours),\n" + 
			"            60), 2, '0') \n" + 
			"           \n" + 
			"    )),0) AS work_hours\n" + 
			"   \n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,t_tasks,  m_cust_header,t_daily_work_log\n" + 
			" \n" + 
			"WHERE\n" + 
			"  m_services.serv_id = t_tasks.serv_id AND m_activities.acti_id = t_tasks.actv_id AND dm_periodicity.periodicity_id = t_tasks.periodicity_id AND t_tasks.task_status = 9 AND t_tasks.del_status = 1 AND t_tasks.is_active = 1  AND   t_tasks.cust_id = m_cust_header.cust_id AND t_daily_work_log.task_id=t_tasks.task_id      AND  t_daily_work_log.emp_id=:empIds  AND  t_tasks.task_completion_date between :fromDate1 and :toDate1  AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids)  group by t_tasks.task_id) b\n" + 
			" \n" + 
			" LEFT JOIN\n" + 
			" \n" + 
			" (\n" + 
			" select a.task_id,group_concat(CASE a.emp_type WHEN '1' THEN a.emp_name  END) admin, group_concat(CASE a.emp_type WHEN '2' THEN a.emp_name  END) partner,group_concat(CASE a.emp_type WHEN '3' THEN a.emp_name  END) manager,group_concat(CASE a.emp_type WHEN '4' THEN a.emp_name END) team_leader,group_concat(CASE a.emp_type WHEN '5' THEN a.emp_name END) employee\n" + 
			"\n" + 
			"\n" + 
			"from\n" + 
			"(select t_tasks.task_id,m_emp.emp_id,m_emp.emp_type, m_emp.emp_name from m_emp,t_tasks where FIND_IN_SET(m_emp.emp_id,t_tasks.task_emp_ids) and t_tasks.task_id IN  (SELECT\n" + 
			"    t_tasks.task_id\n" + 
			"   \n" + 
			"FROM\n" + 
			"   t_tasks,t_daily_work_log\n" + 
			" \n" + 
			"WHERE\n" + 
			"    t_tasks.task_status = 9 AND t_tasks.del_status = 1 AND t_tasks.is_active = 1  AND    t_daily_work_log.task_id=t_tasks.task_id  AND  t_tasks.task_completion_date between :fromDate1 and :toDate1   AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids) group by t_tasks.task_id\n" + 
			"    )\n" + 
			"    ) a group by a.task_id) c ON b.task_id=c.task_id",nativeQuery=true)
		List<CompletedTaskReport> getAllCompletedTask1( @Param("fromDate1") String fromDate1, @Param("toDate1") String toDate1,@Param("empIds") String empIds);
//Old
	
	
	//New 17-04-2020
	
	@Query(value="SELECT b.*,x.work_hours,coalesce((c.admin),'-')as admin,coalesce((c.partner),'-') as partner ,coalesce((c.manager),'-') as manager,coalesce((c.team_leader),'-') as team_leader,coalesce((c.employee),'-') as  employee from (SELECT\n" + 
			"    t_tasks.task_id,t_tasks.task_text,t_tasks.ex_var1,\n" + 
			"    m_services.serv_name,\n" + 
			"    m_activities.acti_name,\n" + 
			"    dm_periodicity.periodicity_name,\n" + 
			"    t_tasks.task_statutory_due_date,\n" + 
			"    t_tasks.task_completion_date as task_start_date,\n" + 
			"      t_tasks.update_datetime AS task_end_date,CONCAT(FLOOR( t_tasks.emp_bud_hr/60),':',LPAD(MOD(t_tasks.emp_bud_hr, 60), 2, '0'))  as emp_bud_hr, \n" + 
			" \n" + 
			"  CONCAT(FLOOR( t_tasks.mngr_bud_hr/60),':',LPAD(MOD(t_tasks.mngr_bud_hr, 60), 2, '0')) as mngr_bud_hr,\n" + 
			"   m_cust_header.cust_firm_name \n" + 
			"       \n" + 
			"   \n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,t_tasks,  m_cust_header\n" + 
			" \n" + 
			"WHERE\n" + 
			"  m_services.serv_id = t_tasks.serv_id AND m_activities.acti_id = t_tasks.actv_id AND dm_periodicity.periodicity_id = t_tasks.periodicity_id AND t_tasks.task_status = 9 AND t_tasks.del_status = 1 AND t_tasks.is_active = 1  AND   t_tasks.cust_id = m_cust_header.cust_id      AND  t_tasks.task_completion_date between :fromDate1 and :toDate1  AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids)  group by t_tasks.task_id) b"
			+ ""
			+ "   left join (\n" + 
			"                \n" + 
			"                SELECT t_daily_work_log.task_id, COALESCE( (CONCAT(         FLOOR(             SUM(t_daily_work_log.work_hours) / 60         ),\n" + 
			"            '.',\n" + 
			"            LPAD(  MOD(             SUM(t_daily_work_log.work_hours),\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0')                  )),\n" + 
			"            0) AS work_hours  from t_daily_work_log,t_tasks\n" + 
			"                WHERE \n" + 
			"                 t_tasks.task_status = 9 \n" + 
			"                            AND t_tasks.del_status = 1 \n" + 
			"                            AND t_tasks.is_active = 1  \n" + 
			"                            AND    t_daily_work_log.task_id=t_tasks.task_id   and t_daily_work_log.emp_id =:empIds and t_daily_work_log.del_status=1 \n" + 
			"                            AND  t_tasks.task_completion_date between :fromDate1 and :toDate1 \n" + 
			"                            AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids) \n" + 
			"                        group by\n" + 
			"                             t_daily_work_log.task_id    \n" + 
			"                ) x on b.task_id=x.task_id"
			+ ""
			+ "\n" + 
			" \n" + 
			" LEFT JOIN\n" + 
			" \n" + 
			" (\n" + 
			" select a.task_id,group_concat(CASE a.emp_type WHEN '1' THEN a.emp_name  END) admin, group_concat(CASE a.emp_type WHEN '2' THEN a.emp_name  END) partner,group_concat(CASE a.emp_type WHEN '3' THEN a.emp_name  END) manager,group_concat(CASE a.emp_type WHEN '4' THEN a.emp_name END) team_leader,group_concat(CASE a.emp_type WHEN '5' THEN a.emp_name END) employee\n" + 
			"\n" + 
			"\n" + 
			"from\n" + 
			"(select t_tasks.task_id,m_emp.emp_id,m_emp.emp_type, m_emp.emp_name from m_emp,t_tasks where FIND_IN_SET(m_emp.emp_id,t_tasks.task_emp_ids) and t_tasks.task_id IN  (SELECT\n" + 
			"    t_tasks.task_id\n" + 
			"   \n" + 
			"FROM\n" + 
			"   t_tasks\n" + 
			" \n" + 
			"WHERE\n" + 
			"    t_tasks.task_status = 9 AND t_tasks.del_status = 1 AND t_tasks.is_active = 1 AND  t_tasks.task_completion_date between :fromDate1 and :toDate1   AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids) group by t_tasks.task_id\n" + 
			"    )\n" + 
			"    ) a group by a.task_id) c ON b.task_id=c.task_id",nativeQuery=true)
		List<CompletedTaskReport> getAllCompletedTask( @Param("fromDate1") String fromDate1, @Param("toDate1") String toDate1,@Param("empIds") String empIds);
}
