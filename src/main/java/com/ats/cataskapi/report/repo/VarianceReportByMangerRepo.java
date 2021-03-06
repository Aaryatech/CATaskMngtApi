package com.ats.cataskapi.report.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

 import com.ats.cataskapi.model.reportv2.VarianceReportByManger;

public interface VarianceReportByMangerRepo  extends JpaRepository<VarianceReportByManger, Integer>{
	
	

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
			"    '-') AS employee\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        t_tasks.task_id,dm_status_mst.status_text  as tsk_status,\n" + 
			"        t_tasks.task_text,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        t_tasks.task_statutory_due_date ,\n" + 
			"        t_tasks.task_completion_date as completion_date ,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        DATEDIFF(\n" + 
			"            t_tasks.task_statutory_due_date,\n" + 
			"            t_tasks.task_start_date\n" + 
			"        ) AS variance_days,\n" + 
			"        CONCAT(\n" + 
			"            FLOOR(t_tasks.emp_bud_hr / 60),\n" + 
			"            ':',\n" + 
			"            LPAD(\n" + 
			"                MOD(t_tasks.emp_bud_hr, 60),\n" + 
			"                2,\n" + 
			"                '0'\n" + 
			"            )\n" + 
			"        ) AS emp_bud_hr,\n" + 
			"        CONCAT(\n" + 
			"            FLOOR(t_tasks.mngr_bud_hr / 60),\n" + 
			"            ':',\n" + 
			"            LPAD(\n" + 
			"                MOD(t_tasks.mngr_bud_hr, 60),\n" + 
			"                2,\n" + 
			"                '0'\n" + 
			"            )\n" + 
			"        ) AS mngr_bud_hr,\n" + 
			"        t_tasks.ex_var1,\n" + 
			"        m_cust_header.cust_firm_name,\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"                CONCAT(\n" + 
			"                    FLOOR(\n" + 
			"                        SUM(t_daily_work_log.work_hours) / 60\n" + 
			"                    ),\n" + 
			"                    '.',\n" + 
			"                    LPAD(\n" + 
			"                        MOD(\n" + 
			"                            SUM(t_daily_work_log.work_hours),\n" + 
			"                            60\n" + 
			"                        ),\n" + 
			"                        2,\n" + 
			"                        '0'\n" + 
			"                    )\n" + 
			"                )\n" + 
			"            ),\n" + 
			"            0\n" + 
			"        ) AS work_hours,\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                m_emp.emp_name\n" + 
			"            FROM\n" + 
			"                m_emp\n" + 
			"            WHERE\n" + 
			"                m_cust_header.owner_emp_id = m_emp.emp_id\n" + 
			"        ),\n" + 
			"        'NA'\n" + 
			"        ) AS owner_partner\n" + 
			"    FROM\n" + 
			"       dm_status_mst, m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        t_tasks,\n" + 
			"        m_cust_header,\n" + 
			"        t_daily_work_log\n" + 
			"    WHERE\n" + 
			"                t_tasks.task_status IN(5,9,12,10)\n" + 
			" AND dm_status_mst.status_value=t_tasks.task_status AND  m_services.serv_id = t_tasks.serv_id AND m_activities.acti_id = t_tasks.actv_id AND dm_periodicity.periodicity_id = t_tasks.periodicity_id AND  t_tasks.del_status = 1 AND t_tasks.is_active = 1 AND t_tasks.cust_id = m_cust_header.cust_id AND t_daily_work_log.task_id = t_tasks.task_id AND FIND_IN_SET(:empId, t_tasks.task_emp_ids)  AND t_tasks.actv_id =:actId AND t_tasks.cust_id =:custId AND t_tasks.serv_id =:servId \n" + 
			"    GROUP BY\n" + 
			"        t_tasks.task_id\n" + 
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
			") employee\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        n.task_id,\n" + 
			"        n.emp_id,\n" + 
			"        n.emp_type,\n" + 
			"        n.emp_name,\n" + 
			"        o.mgr_hrs\n" + 
			"    FROM\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            t_tasks.task_id,\n" + 
			"            m_emp.emp_id,\n" + 
			"            m_emp.emp_type,\n" + 
			"            m_emp.emp_name\n" + 
			"        FROM\n" + 
			"            m_emp,\n" + 
			"            t_tasks\n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(\n" + 
			"                m_emp.emp_id,\n" + 
			"                t_tasks.task_emp_ids\n" + 
			"            )\n" + 
			"    ) n\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_daily_work_log.emp_id,\n" + 
			"        CONCAT(\n" + 
			"            FLOOR(\n" + 
			"                SUM(t_daily_work_log.work_hours) / 60\n" + 
			"            ),\n" + 
			"            '.',\n" + 
			"            LPAD(\n" + 
			"                MOD(\n" + 
			"                    SUM(t_daily_work_log.work_hours),\n" + 
			"                    60\n" + 
			"                ),\n" + 
			"                2,\n" + 
			"                '0'\n" + 
			"            )\n" + 
			"        ) AS mgr_hrs\n" + 
			"    FROM\n" + 
			"        t_tasks,\n" + 
			"        t_daily_work_log\n" + 
			"    WHERE\n" + 
			"     t_tasks.task_status IN(5,9,12,10)  AND   t_tasks.del_status = 1 AND t_tasks.is_active = 1 AND t_tasks.actv_id =:actId AND t_tasks.serv_id =:servId AND t_tasks.cust_id =:custId \n" + 
			"    GROUP BY\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_daily_work_log.emp_id\n" + 
			") o\n" + 
			"ON\n" + 
			"    o.task_id = n.task_id AND n.emp_id = o.emp_id\n" + 
			") a\n" + 
			"GROUP BY\n" + 
			"    a.task_id\n" + 
			") c\n" + 
			"ON\n" + 
			"    b.task_id = c.task_id",nativeQuery=true)
		List<VarianceReportByManger> getVarianceByManager( @Param("servId") int servId,@Param("actId") int actId,@Param("empId") int empId,@Param("custId") int custId);
	
	

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
			"    '-') AS employee\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        t_tasks.task_id,dm_status_mst.status_text as tsk_status,\n" + 
			"        t_tasks.task_text,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.task_completion_date as completion_date,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        DATEDIFF(\n" + 
			"            t_tasks.task_statutory_due_date,\n" + 
			"            t_tasks.task_start_date\n" + 
			"        ) AS variance_days,\n" + 
			"        CONCAT(\n" + 
			"            FLOOR(t_tasks.emp_bud_hr / 60),\n" + 
			"            ':',\n" + 
			"            LPAD(\n" + 
			"                MOD(t_tasks.emp_bud_hr, 60),\n" + 
			"                2,\n" + 
			"                '0'\n" + 
			"            )\n" + 
			"        ) AS emp_bud_hr,\n" + 
			"        CONCAT(\n" + 
			"            FLOOR(t_tasks.mngr_bud_hr / 60),\n" + 
			"            ':',\n" + 
			"            LPAD(\n" + 
			"                MOD(t_tasks.mngr_bud_hr, 60),\n" + 
			"                2,\n" + 
			"                '0'\n" + 
			"            )\n" + 
			"        ) AS mngr_bud_hr,\n" + 
			"        t_tasks.ex_var1,\n" + 
			"        m_cust_header.cust_firm_name,\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"                CONCAT(\n" + 
			"                    FLOOR(\n" + 
			"                        SUM(t_daily_work_log.work_hours) / 60\n" + 
			"                    ),\n" + 
			"                    '.',\n" + 
			"                    LPAD(\n" + 
			"                        MOD(\n" + 
			"                            SUM(t_daily_work_log.work_hours),\n" + 
			"                            60\n" + 
			"                        ),\n" + 
			"                        2,\n" + 
			"                        '0'\n" + 
			"                    )\n" + 
			"                )\n" + 
			"            ),\n" + 
			"            0\n" + 
			"        ) AS work_hours,\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                m_emp.emp_name\n" + 
			"            FROM\n" + 
			"                m_emp\n" + 
			"            WHERE\n" + 
			"                m_cust_header.owner_emp_id = m_emp.emp_id\n" + 
			"        ),\n" + 
			"        'NA'\n" + 
			"        ) AS owner_partner\n" + 
			"    FROM\n" + 
			"       dm_status_mst,m_services,\n" + 
			"        m_activities,\n" + 
			"        dm_periodicity,\n" + 
			"        t_tasks,\n" + 
			"        m_cust_header,\n" + 
			"        t_daily_work_log\n" + 
			"    WHERE\n" + 
			"       t_tasks.task_status IN(5,9,12,10)  AND  dm_status_mst.status_value=t_tasks.task_status AND   m_services.serv_id = t_tasks.serv_id AND m_activities.acti_id = t_tasks.actv_id AND dm_periodicity.periodicity_id = t_tasks.periodicity_id AND  t_tasks.del_status = 1 AND t_tasks.is_active = 1 AND t_tasks.cust_id = m_cust_header.cust_id AND t_daily_work_log.task_id = t_tasks.task_id AND FIND_IN_SET(:empId, t_tasks.task_emp_ids)  AND t_tasks.actv_id =:actId AND  t_tasks.serv_id =:servId \n" + 
			"    GROUP BY\n" + 
			"        t_tasks.task_id\n" + 
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
			") employee\n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        n.task_id,\n" + 
			"        n.emp_id,\n" + 
			"        n.emp_type,\n" + 
			"        n.emp_name,\n" + 
			"        o.mgr_hrs\n" + 
			"    FROM\n" + 
			"        (\n" + 
			"        SELECT\n" + 
			"            t_tasks.task_id,\n" + 
			"            m_emp.emp_id,\n" + 
			"            m_emp.emp_type,\n" + 
			"            m_emp.emp_name\n" + 
			"        FROM\n" + 
			"            m_emp,\n" + 
			"            t_tasks\n" + 
			"        WHERE\n" + 
			"            FIND_IN_SET(\n" + 
			"                m_emp.emp_id,\n" + 
			"                t_tasks.task_emp_ids\n" + 
			"            )\n" + 
			"    ) n\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_daily_work_log.emp_id,\n" + 
			"        CONCAT(\n" + 
			"            FLOOR(\n" + 
			"                SUM(t_daily_work_log.work_hours) / 60\n" + 
			"            ),\n" + 
			"            '.',\n" + 
			"            LPAD(\n" + 
			"                MOD(\n" + 
			"                    SUM(t_daily_work_log.work_hours),\n" + 
			"                    60\n" + 
			"                ),\n" + 
			"                2,\n" + 
			"                '0'\n" + 
			"            )\n" + 
			"        ) AS mgr_hrs\n" + 
			"    FROM\n" + 
			"        t_tasks,\n" + 
			"        t_daily_work_log\n" + 
			"    WHERE\n" + 
			"        t_tasks.task_status IN(5,9,12,10)  AND   t_tasks.del_status = 1 AND t_tasks.is_active = 1 AND t_tasks.actv_id =:actId AND t_tasks.serv_id =:servId  \n" + 
			"    GROUP BY\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_daily_work_log.emp_id\n" + 
			") o\n" + 
			"ON\n" + 
			"    o.task_id = n.task_id AND n.emp_id = o.emp_id\n" + 
			") a\n" + 
			"GROUP BY\n" + 
			"    a.task_id\n" + 
			") c\n" + 
			"ON\n" + 
			"    b.task_id = c.task_id",nativeQuery=true)
		List<VarianceReportByManger> getAllCustVarianceByManager( @Param("servId") int servId, @Param("actId") int actId,@Param("empId") int empId);

}
