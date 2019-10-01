package com.ats.cataskapi.report.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

 import com.ats.cataskapi.model.report.InactiveTaskReport;

public interface InactiveTaskReportRepo extends JpaRepository<InactiveTaskReport, Integer>{
	
	@Query(value="SELECT\n" + 
			"    b.*,\n" + 
			"    COALESCE((c.admin),\n" + 
			"    '-') AS admin,\n" + 
			"    COALESCE((c.partner),\n" + 
			"    '-') AS partner,\n" + 
			"    COALESCE((c.manager),\n" + 
			"    '-') AS manager,\n" + 
			"    COALESCE((c.team_leader),\n" + 
			"    '-') AS team_leader,\n" + 
			"    COALESCE((c.employee),\n" + 
			"    '-') AS employee,\n" + 
			"    COALESCE((c.manager_hrs),\n" + 
			"    '-') AS manager_hrs,\n" + 
			"     COALESCE((c.team_leader_hrs),\n" + 
			"    '-') AS team_leader_hrs,\n" + 
			"    COALESCE((c.employee_hrs),\n" + 
			"    '-') AS employee_hrs\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.task_completion_date as task_start_date ,\n" + 
			"        t_tasks.update_datetime AS task_end_date,\n" + 
			"         CONCAT(FLOOR( t_tasks.emp_bud_hr/60),':',LPAD(MOD(t_tasks.emp_bud_hr, 60), 2, '0'))  as emp_bud_hr,  \n" + 
			"       CONCAT(FLOOR( t_tasks.mngr_bud_hr/60),':',LPAD(MOD(t_tasks.mngr_bud_hr, 60), 2, '0')) as mngr_bud_hr, t_tasks.ex_var1,\n" + 
			"         m_cust_header.cust_firm_name, \n" + 
		
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
			"    )),0) AS work_hours, \n" + 
			"COALESCE(\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        m_emp.emp_name\n" + 
			"    FROM\n" + 
			"        m_emp\n" + 
			"    WHERE\n" + 
			"        m_cust_header.owner_emp_id = m_emp.emp_id\n" + 
			"),\n" + 
			"'NA'\n" + 
			") AS owner_partner\n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    t_tasks,\n" + 
			"    m_cust_header,\n" + 
			"    t_daily_work_log\n" + 
			"WHERE\n" + 
			"    m_services.serv_id = t_tasks.serv_id AND m_activities.acti_id = t_tasks.actv_id AND dm_periodicity.periodicity_id = t_tasks.periodicity_id AND t_tasks.task_status =:status AND t_tasks.del_status = 1 AND t_tasks.is_active = 1 AND t_tasks.cust_id = m_cust_header.cust_id AND t_daily_work_log.task_id = t_tasks.task_id AND Date(t_tasks.update_datetime)   BETWEEN :fromDate1 AND :toDate1  AND FIND_IN_SET(:empIds, t_tasks.task_emp_ids)\n" + 
			"GROUP BY\n" + 
			"    t_tasks.task_id\n" + 
			") b\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        a.task_id,\n" + 
			"        GROUP_CONCAT(\n" + 
			"            CASE a.emp_type WHEN '1' THEN a.emp_name\n" + 
			"        END\n" + 
			") admin,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '2' THEN a.emp_name\n" + 
			"END\n" + 
			") partner,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '3' THEN a.emp_name\n" + 
			"END\n" + 
			") manager,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '4' THEN a.emp_name\n" + 
			"END\n" + 
			") team_leader,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '5' THEN a.emp_name\n" + 
			"END\n" + 
			") employee,\n" + 
			"  GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '3' THEN a.mgr_hrs\n" + 
			"END\n" + 
			") manager_hrs,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '4' THEN a.mgr_hrs\n" + 
			"END\n" + 
			") team_leader_hrs,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '5' THEN a.mgr_hrs\n" + 
			"END\n" + 
			") employee_hrs\n" + 
			"   \n" + 
			"FROM\n" + 
			"    (SELECT n.task_id,n.emp_id,n.emp_type,n.emp_name,o.mgr_hrs\n" + 
			"FROM\n" + 
			"(SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        m_emp.emp_id,\n" + 
			"        m_emp.emp_type,\n" + 
			"        m_emp.emp_name\n" + 
			"       \n" + 
			"    FROM\n" + 
			"        m_emp,\n" + 
			"        t_tasks\n" + 
			"    WHERE\n" + 
			"        FIND_IN_SET(\n" + 
			"            m_emp.emp_id,\n" + 
			"            t_tasks.task_emp_ids\n" + 
			"        ) \n" + 
			"       \n" + 
			" ) n \n" + 
			" \n" + 
			" LEFT JOIN\n" + 
			" \n" + 
			" ( SELECT\n" + 
			"            t_tasks.task_id,t_daily_work_log.emp_id,CONCAT(\n" + 
			"            FLOOR(\n" + 
			"                SUM(t_daily_work_log.work_hours) / 60\n" + 
			"            ),\n" + 
			"            '.',\n" + 
			"           LPAD( MOD(\n" + 
			"                SUM(t_daily_work_log.work_hours),\n" + 
			"                60\n" + 
			"            ), 2, '0')) as mgr_hrs\n" + 
			"                \n" + 
			"        FROM\n" + 
			"            t_tasks,\n" + 
			"            t_daily_work_log\n" + 
			"        WHERE\n" + 
			"            t_tasks.task_status = :status AND t_tasks.del_status = 1 AND t_tasks.is_active = 1 AND t_daily_work_log.task_id = t_tasks.task_id AND Date(t_tasks.update_datetime) BETWEEN :fromDate1 AND :toDate1 \n" + 
			"        GROUP BY\n" + 
			"            t_tasks.task_id,t_daily_work_log.emp_id) o \n" + 
			" ON o.task_id=n.task_id and n.emp_id=o.emp_id ) a\n" + 
			"GROUP BY\n" + 
			"    a.task_id\n" + 
			") c\n" + 
			"ON\n" + 
			"    b.task_id = c.task_id",nativeQuery=true)
		List<InactiveTaskReport> getAllInactiveTask( @Param("fromDate1") String fromDate1, @Param("toDate1") String toDate1,@Param("empIds") String empIds,@Param("status") int status);
	
	
	
	@Query(value="SELECT\n" + 
			"    b.*,\n" + 
			"    COALESCE((c.admin),\n" + 
			"    '-') AS admin,\n" + 
			"    COALESCE((c.partner),\n" + 
			"    '-') AS partner,\n" + 
			"    COALESCE((c.manager),\n" + 
			"    '-') AS manager,\n" + 
			"    COALESCE((c.team_leader),\n" + 
			"    '-') AS team_leader,\n" + 
			"    COALESCE((c.employee),\n" + 
			"    '-') AS employee,\n" + 
			"    COALESCE((c.manager_hrs),\n" + 
			"    '-') AS manager_hrs,\n" + 
			"     COALESCE((c.team_leader_hrs),\n" + 
			"    '-') AS team_leader_hrs,\n" + 
			"    COALESCE((c.employee_hrs),\n" + 
			"    '-') AS employee_hrs\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.task_completion_date as task_start_date ,\n" + 
			"        t_tasks.update_datetime AS task_end_date,\n" + 
			"         CONCAT(FLOOR( t_tasks.emp_bud_hr/60),':',LPAD(MOD(t_tasks.emp_bud_hr, 60), 2, '0'))  as emp_bud_hr,  \n" + 
			"       CONCAT(FLOOR( t_tasks.mngr_bud_hr/60),':',LPAD(MOD(t_tasks.mngr_bud_hr, 60), 2, '0')) as mngr_bud_hr, t_tasks.ex_var1,\n" + 
			"         m_cust_header.cust_firm_name, \n" + 
		
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
			"    )),0) AS work_hours, \n" + 
			"COALESCE(\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        m_emp.emp_name\n" + 
			"    FROM\n" + 
			"        m_emp\n" + 
			"    WHERE\n" + 
			"        m_cust_header.owner_emp_id = m_emp.emp_id\n" + 
			"),\n" + 
			"'NA'\n" + 
			") AS owner_partner\n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    t_tasks,\n" + 
			"    m_cust_header,\n" + 
			"    t_daily_work_log\n" + 
			"WHERE\n" + 
			"    t_tasks.task_completion_date BETWEEN :fromDate1 AND  :toDate1 AND m_services.serv_id = t_tasks.serv_id AND m_activities.acti_id = t_tasks.actv_id AND dm_periodicity.periodicity_id = t_tasks.periodicity_id AND t_tasks.task_status =:status AND t_tasks.del_status = 1 AND t_tasks.is_active = 1 AND t_tasks.cust_id = m_cust_header.cust_id AND t_daily_work_log.task_id = t_tasks.task_id AND    FIND_IN_SET(:empIds, t_tasks.task_emp_ids)\n" + 
			"GROUP BY\n" + 
			"    t_tasks.task_id\n" + 
			") b\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        a.task_id,\n" + 
			"        GROUP_CONCAT(\n" + 
			"            CASE a.emp_type WHEN '1' THEN a.emp_name\n" + 
			"        END\n" + 
			") admin,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '2' THEN a.emp_name\n" + 
			"END\n" + 
			") partner,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '3' THEN a.emp_name\n" + 
			"END\n" + 
			") manager,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '4' THEN a.emp_name\n" + 
			"END\n" + 
			") team_leader,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '5' THEN a.emp_name\n" + 
			"END\n" + 
			") employee,\n" + 
			"  GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '3' THEN a.mgr_hrs\n" + 
			"END\n" + 
			") manager_hrs,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '4' THEN a.mgr_hrs\n" + 
			"END\n" + 
			") team_leader_hrs,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '5' THEN a.mgr_hrs\n" + 
			"END\n" + 
			") employee_hrs\n" + 
			"   \n" + 
			"FROM\n" + 
			"    (SELECT n.task_id,n.emp_id,n.emp_type,n.emp_name,o.mgr_hrs\n" + 
			"FROM\n" + 
			"(SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        m_emp.emp_id,\n" + 
			"        m_emp.emp_type,\n" + 
			"        m_emp.emp_name\n" + 
			"       \n" + 
			"    FROM\n" + 
			"        m_emp,\n" + 
			"        t_tasks\n" + 
			"    WHERE\n" + 
			"        FIND_IN_SET(\n" + 
			"            m_emp.emp_id,\n" + 
			"            t_tasks.task_emp_ids\n" + 
			"        ) \n" + 
			"       \n" + 
			" ) n \n" + 
			" \n" + 
			" LEFT JOIN\n" + 
			" \n" + 
			" ( SELECT\n" + 
			"            t_tasks.task_id,t_daily_work_log.emp_id,CONCAT(\n" + 
			"            FLOOR(\n" + 
			"                SUM(t_daily_work_log.work_hours) / 60\n" + 
			"            ),\n" + 
			"            '.',\n" + 
			"           LPAD( MOD(\n" + 
			"                SUM(t_daily_work_log.work_hours),\n" + 
			"                60\n" + 
			"            ), 2, '0')) as mgr_hrs\n" + 
			"                \n" + 
			"        FROM\n" + 
			"            t_tasks,\n" + 
			"            t_daily_work_log\n" + 
			"        WHERE\n" + 
			"         t_tasks.task_completion_date BETWEEN :fromDate1 AND  :toDate1 AND   t_tasks.task_status = :status AND t_tasks.del_status = 1 AND t_tasks.is_active = 1  \n" + 
			"        GROUP BY\n" + 
			"            t_tasks.task_id,t_daily_work_log.emp_id) o \n" + 
			" ON o.task_id=n.task_id and n.emp_id=o.emp_id ) a\n" + 
			"GROUP BY\n" + 
			"    a.task_id\n" + 
			") c\n" + 
			"ON\n" + 
			"    b.task_id = c.task_id",nativeQuery=true)
		List<InactiveTaskReport> getAllCompleteTask( @Param("fromDate1") String fromDate1, @Param("toDate1") String toDate1,@Param("empIds") String empIds,@Param("status") int status);
 
}
