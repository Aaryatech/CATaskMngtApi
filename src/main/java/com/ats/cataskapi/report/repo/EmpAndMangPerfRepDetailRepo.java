package com.ats.cataskapi.report.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.report.EmpAndMangPerfRepDetail;
 
public interface EmpAndMangPerfRepDetailRepo extends JpaRepository<EmpAndMangPerfRepDetail, Integer>{
	
	
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
			"    '-') AS employee_hrs,\n" + 
			"         COALESCE((c.manager_bet_hrs),\n" + 
			"    '-') AS manager_bet_hrs,\n" + 
			"     COALESCE((c.tl_bet_hrs),\n" + 
			"    '-') AS tl_bet_hrs,\n" + 
			"    COALESCE((c.emp_bet_hrs),\n" + 
			"    '-') AS emp_bet_hrs\n" + 
			"    \n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.task_completion_date as task_start_date,\n" + 
			"        t_tasks.update_datetime AS task_end_date,\n" + 
			"       t_tasks.ex_var1,t_tasks.task_completion_date,\n" + 
			"      m_cust_header.cust_firm_name ,dm_status_mst.status_text,\n" + 
			"        \n" + 
			"       \n" + 
			"        \n" + 
			"        CONCAT(\n" + 
			"            FLOOR(\n" + 
			"                t_tasks.emp_bud_hr   / 60\n" + 
			"            ),\n" + 
			"            '.',\n" + 
			"            LPAD(MOD(\n" + 
			"                t_tasks.emp_bud_hr ,\n" + 
			"                60\n" + 
			"            ), 2, '0')\n" + 
			"        ) as emp_bud_hr,CONCAT(\n" + 
			"            FLOOR(\n" + 
			"                 t_tasks.mngr_bud_hr   / 60\n" + 
			"            ),\n" + 
			"            '.',\n" + 
			"           LPAD( MOD(\n" + 
			"                t_tasks.mngr_bud_hr ,\n" + 
			"                60\n" + 
			"            ), 2, '0')\n" + 
			"        ) as  mngr_bud_hr,\n" + 
			"COALESCE(\n" + 
			"    (select\n" + 
			"        CONCAT(\n" + 
			"            FLOOR(\n" + 
			"                SUM(t_daily_work_log.work_hours) / 60\n" + 
			"            ),\n" + 
			"            '.',\n" + 
			"            LPAD(MOD(\n" + 
			"                SUM(t_daily_work_log.work_hours),\n" + 
			"                60\n" + 
			"            ), 2, '0')\n" + 
			"        ) from t_daily_work_log where t_daily_work_log.task_id = t_tasks.task_id \n" + 
			"    ),\n" + 
			"    0\n" + 
			") AS work_hours,\n" + 
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
			"    m_cust_header,dm_status_mst\n" + 
			"WHERE\n" + 
			"    m_services.serv_id = t_tasks.serv_id AND m_activities.acti_id = t_tasks.actv_id AND dm_periodicity.periodicity_id = t_tasks.periodicity_id  AND t_tasks.del_status = 1 AND t_tasks.cust_id = m_cust_header.cust_id AND  t_tasks.task_id not in (select   t_tasks.task_id from t_tasks where (t_tasks.task_completion_date < :fromDate and t_tasks.task_status=9) or (Date(t_tasks.update_datetime) < :fromDate and t_tasks.is_active=0)) AND FIND_IN_SET(:empId, t_tasks.task_emp_ids)  AND   dm_status_mst.status_value=t_tasks.task_status\n" + 
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
			") employee_hrs,\n" + 
			"   \n" + 
			"      GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '3' THEN a.mgr_hrs_bet\n" + 
			"END\n" + 
			") manager_bet_hrs,\n" + 
			"      GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '4' THEN a.mgr_hrs_bet\n" + 
			"END\n" + 
			") tl_bet_hrs,\n" + 
			"      GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '5' THEN a.mgr_hrs_bet\n" + 
			"END\n" + 
			") emp_bet_hrs\n" + 
			"   \n" + 
			"FROM\n" + 
			"    (SELECT n.task_id,n.emp_id,n.emp_type,n.emp_name,o.mgr_hrs,p.mgr_hrs_bet\n" + 
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
			"        )\n" + 
			"       \n" + 
			" ) n\n" + 
			" \n" + 
			" LEFT JOIN\n" + 
			" \n" + 
			" ( SELECT\n" + 
			"            t_tasks.task_id,t_daily_work_log.emp_id,CONCAT(\n" + 
			"            FLOOR(\n" + 
			"                SUM(t_daily_work_log.work_hours) / 60\n" + 
			"            ),\n" + 
			"            '.',\n" + 
			"            LPAD(MOD(\n" + 
			"                SUM(t_daily_work_log.work_hours),\n" + 
			"                60\n" + 
			"            ), 2, '0')) as mgr_hrs\n" + 
			"               \n" + 
			"        FROM\n" + 
			"            t_tasks,\n" + 
			"            t_daily_work_log\n" + 
			"        WHERE\n" + 
			"   FIND_IN_SET(\n" + 
			"            t_daily_work_log.emp_id,\n" + 
			"            t_tasks.task_emp_ids\n" + 
			"        ) AND\n" + 
			"            t_tasks.del_status = 1 AND t_daily_work_log.task_id = t_tasks.task_id AND t_tasks.task_id not in (select   t_tasks.task_id from t_tasks where (t_tasks.task_completion_date < :fromDate and t_tasks.task_status=:status) or (date(t_tasks.update_datetime) < :fromDate and t_tasks.is_active=0))\n" + 
			"        GROUP BY\n" + 
			"            t_tasks.task_id,t_daily_work_log.emp_id) o\n" + 
			" ON o.task_id=n.task_id and n.emp_id=o.emp_id\n" + 
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
			"            ), 2, '0')) as mgr_hrs_bet\n" + 
			"               \n" + 
			"        FROM\n" + 
			"            t_tasks,\n" + 
			"            t_daily_work_log\n" + 
			"        WHERE\n" + 
			"   FIND_IN_SET(\n" + 
			"            t_daily_work_log.emp_id,\n" + 
			"            t_tasks.task_emp_ids\n" + 
			"        ) AND\n" + 
			"            t_tasks.del_status = 1 and t_daily_work_log.work_date Between :fromDate and :toDate AND t_daily_work_log.task_id = t_tasks.task_id AND t_tasks.task_id not in (select   t_tasks.task_id from t_tasks where (t_tasks.task_start_date < :fromDate and t_tasks.task_status=:status) or (t_tasks.task_start_date < :fromDate and t_tasks.is_active=0 ))\n" + 
			"        GROUP BY\n" + 
			"            t_tasks.task_id,t_daily_work_log.emp_id) p\n" + 
			" ON p.task_id=n.task_id and n.emp_id=p.emp_id ) a\n" + 
			"GROUP BY\n" + 
			"    a.task_id\n" + 
			") c\n" + 
			"\n" + 
			"ON\n" + 
			"    b.task_id = c.task_id ",nativeQuery=true)
		List<EmpAndMangPerfRepDetail> getAllTaskDetail(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("status") int status,@Param("empId") int empId);

	
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
			"    '-') AS employee_hrs,\n" + 
			"         COALESCE((c.manager_bet_hrs),\n" + 
			"    '-') AS manager_bet_hrs,\n" + 
			"     COALESCE((c.tl_bet_hrs),\n" + 
			"    '-') AS tl_bet_hrs,\n" + 
			"    COALESCE((c.emp_bet_hrs),\n" + 
			"    '-') AS emp_bet_hrs\n" + 
			"    \n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.task_completion_date as task_start_date,\n" + 
			"        t_tasks.update_datetime AS task_end_date,\n" + 
			"       t_tasks.ex_var1,t_tasks.task_completion_date,\n" + 
			"      m_cust_header.cust_firm_name ,dm_status_mst.status_text,\n" + 
			"        \n" + 
			"       \n" + 
			"        \n" + 
			"        CONCAT(\n" + 
			"            FLOOR(\n" + 
			"                t_tasks.emp_bud_hr   / 60\n" + 
			"            ),\n" + 
			"            '.',\n" + 
			"            LPAD(MOD(\n" + 
			"                t_tasks.emp_bud_hr ,\n" + 
			"                60\n" + 
			"            ), 2, '0')\n" + 
			"        ) as emp_bud_hr,CONCAT(\n" + 
			"            FLOOR(\n" + 
			"                 t_tasks.mngr_bud_hr   / 60\n" + 
			"            ),\n" + 
			"            '.',\n" + 
			"           LPAD( MOD(\n" + 
			"                t_tasks.mngr_bud_hr ,\n" + 
			"                60\n" + 
			"            ), 2, '0')\n" + 
			"        ) as  mngr_bud_hr,\n" + 
			"COALESCE(\n" + 
			"    (select\n" + 
			"        CONCAT(\n" + 
			"            FLOOR(\n" + 
			"                SUM(t_daily_work_log.work_hours) / 60\n" + 
			"            ),\n" + 
			"            '.',\n" + 
			"            LPAD(MOD(\n" + 
			"                SUM(t_daily_work_log.work_hours),\n" + 
			"                60\n" + 
			"            ), 2, '0')\n" + 
			"        ) from t_daily_work_log where t_daily_work_log.task_id = t_tasks.task_id \n" + 
			"    ),\n" + 
			"    0\n" + 
			") AS work_hours,\n" + 
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
			"    m_cust_header,dm_status_mst\n" + 
			"WHERE\n" + 
			"    m_services.serv_id = t_tasks.serv_id AND m_activities.acti_id = t_tasks.actv_id AND dm_periodicity.periodicity_id = t_tasks.periodicity_id  AND t_tasks.del_status = 1 AND t_tasks.cust_id = m_cust_header.cust_id AND "
			+ "  t_tasks.task_completion_date between :fromDate and :toDate and t_tasks.task_status=:status AND FIND_IN_SET(:empId, t_tasks.task_emp_ids)  AND   dm_status_mst.status_value=t_tasks.task_status " + 
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
			") employee_hrs,\n" + 
			"   \n" + 
			"      GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '3' THEN a.mgr_hrs_bet\n" + 
			"END\n" + 
			") manager_bet_hrs,\n" + 
			"      GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '4' THEN a.mgr_hrs_bet\n" + 
			"END\n" + 
			") tl_bet_hrs,\n" + 
			"      GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '5' THEN a.mgr_hrs_bet\n" + 
			"END\n" + 
			") emp_bet_hrs\n" + 
			"   \n" + 
			"FROM\n" + 
			"    (SELECT n.task_id,n.emp_id,n.emp_type,n.emp_name,o.mgr_hrs,p.mgr_hrs_bet\n" + 
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
			"            t_tasks.task_emp_ids \n" + 
			"        ) and m_emp.emp_id=:empId \n" + 
			"       \n" + 
			" ) n\n" + 
			" \n" + 
			" LEFT JOIN\n" + 
			" \n" + 
			" ( SELECT\n" + 
			"            t_tasks.task_id,t_daily_work_log.emp_id,CONCAT(\n" + 
			"            FLOOR(\n" + 
			"                SUM(t_daily_work_log.work_hours) / 60\n" + 
			"            ),\n" + 
			"            '.',\n" + 
			"            LPAD(MOD(\n" + 
			"                SUM(t_daily_work_log.work_hours),\n" + 
			"                60\n" + 
			"            ), 2, '0')) as mgr_hrs\n" + 
			"               \n" + 
			"        FROM\n" + 
			"            t_tasks,\n" + 
			"            t_daily_work_log\n" + 
			"        WHERE\n" + 
			"   FIND_IN_SET(\n" + 
			"            t_daily_work_log.emp_id,\n" + 
			"            t_tasks.task_emp_ids\n" + 
			"        ) AND\n" + 
			"            t_tasks.del_status = 1 AND t_daily_work_log.task_id = t_tasks.task_id "
			+ "AND t_tasks.task_completion_date between :fromDate and :toDate  and t_tasks.task_status=:status \n" + 
			"        GROUP BY\n" + 
			"            t_tasks.task_id,t_daily_work_log.emp_id) o\n" + 
			" ON o.task_id=n.task_id and n.emp_id=o.emp_id\n" + 
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
			"            ), 2, '0')) as mgr_hrs_bet\n" + 
			"               \n" + 
			"        FROM\n" + 
			"            t_tasks,\n" + 
			"            t_daily_work_log\n" + 
			"        WHERE\n" + 
			"   FIND_IN_SET(\n" + 
			"            t_daily_work_log.emp_id,\n" + 
			"            t_tasks.task_emp_ids\n" + 
			"        ) AND\n" + 
			"            t_tasks.del_status = 1 and t_daily_work_log.work_date Between :fromDate and :toDate AND t_daily_work_log.task_id = t_tasks.task_id AND  "
			+ "t_tasks.task_completion_date between :fromDate and :toDate and t_tasks.task_status=:status \n" + 
			"        GROUP BY\n" + 
			"            t_tasks.task_id,t_daily_work_log.emp_id) p\n" + 
			" ON p.task_id=n.task_id and n.emp_id=p.emp_id ) a\n" + 
			"GROUP BY\n" + 
			"    a.task_id\n" + 
			") c\n" + 
			"\n" + 
			"ON\n" + 
			"    b.task_id = c.task_id ",nativeQuery=true)
		List<EmpAndMangPerfRepDetail> getAllTaskDetailSingleEmp(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("status") int status,@Param("empId") int empId);
//Sachin 04-04-2020 for Employee Manager Performance Detail Report to get only  detail of the emp selected not others, previously it was with others as well 

}
