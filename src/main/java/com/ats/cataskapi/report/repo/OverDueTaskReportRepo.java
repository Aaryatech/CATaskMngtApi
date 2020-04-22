package com.ats.cataskapi.report.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.reportv2.ComplTaskVarienceRep;
import com.ats.cataskapi.model.reportv2.OverDueTaskReport;
//Same Repo for getting emp Work Log Diary 
public interface OverDueTaskReportRepo extends JpaRepository<OverDueTaskReport, String> {
	
	@Query(value=" " + 
			" SELECT\n" + 
			"        b.*,\n" + 
			"        \n" + 
			"        coalesce((c.admin),\n" + 
			"        '-')as admin,\n" + 
			"        coalesce((c.partner),\n" + 
			"        '-') as partner ,\n" + 
			"        coalesce((c.manager),\n" + 
			"        '-') as manager,\n" + 
			"        coalesce((c.team_leader),\n" + 
			"        '-') as team_leader,\n" + 
			"        coalesce((c.employee),\n" + 
			"        '-') as  employee \n" + 
			"    from\n" + 
			"        (SELECT\n" + 
			"        UUID() as unique_id,\n" + 
			"            t_tasks.task_id,\n" + 
			"            t_tasks.task_text,\n" + 
			"        \n" + 
			"            m_services.serv_name,\n" + 
			"            m_activities.acti_name,\n" + 
			"            dm_periodicity.periodicity_name,\n" + 
			"            t_tasks.task_statutory_due_date,\n" + 
			"            t_tasks.task_completion_date ,\n" + 
			"            t_tasks.task_end_date AS task_work_date,\n" + 
			"         \n" + 
			"            CONCAT(FLOOR( t_tasks.emp_bud_hr/60),\n" + 
			"            ':',\n" + 
			"            LPAD(MOD(t_tasks.emp_bud_hr,\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0'))  as emp_bud_hr,\n" + 
			"            CONCAT(FLOOR( t_tasks.mngr_bud_hr/60),\n" + 
			"            ':',\n" + 
			"            LPAD(MOD(t_tasks.mngr_bud_hr,\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0')) as mngr_bud_hr,\n" + 
			"        0 as work_hours,\n" + 
			"            m_cust_header.cust_firm_name,m_emp.emp_name as own_partner,\n" + 
			"         dm_status_mst.status_text\n" + 
			"        FROM\n" + 
			"            m_services,\n" + 
			"            m_activities,\n" + 
			"            dm_periodicity,\n" + 
			"            t_tasks,\n" + 
			"            m_cust_header,\n" + 
			"         dm_status_mst,\n" + 
			"         m_emp\n" + 
			"        WHERE\n" + 
			"            m_services.serv_id = t_tasks.serv_id \n" + 
			"            AND m_activities.acti_id = t_tasks.actv_id \n" + 
			"            AND dm_periodicity.periodicity_id = t_tasks.periodicity_id \n" + 
			"            AND t_tasks.task_status != 9 \n" + 
			"            AND t_tasks.del_status = 1 \n" + 
			"            AND t_tasks.is_active = 1  \n" + 
			"            AND   t_tasks.cust_id = m_cust_header.cust_id      \n" + 
			"            AND  t_tasks.task_statutory_due_date between :fromDate and  :toDate \n" + 
			"         and dm_status_mst.status_value=t_tasks.task_status \n" + 
			"            AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids)  \n" + 
			"         and m_emp.emp_id=m_cust_header.owner_emp_id\n" + 
			"        group by\n" + 
			"            t_tasks.task_id) b   \n" + 
			"  \n" + 
			"    LEFT JOIN\n" + 
			"        (\n" + 
			"            select\n" + 
			"                a.task_id,\n" + 
			"                group_concat(CASE a.emp_type \n" + 
			"                    WHEN '1' THEN a.emp_name  \n" + 
			"                END) admin,\n" + 
			"                group_concat(CASE a.emp_type \n" + 
			"                    WHEN '2' THEN a.emp_name  \n" + 
			"                END) partner,\n" + 
			"                group_concat(CASE a.emp_type \n" + 
			"                    WHEN '3' THEN a.emp_name  \n" + 
			"                END) manager,\n" + 
			"                group_concat(CASE a.emp_type \n" + 
			"                    WHEN '4' THEN a.emp_name \n" + 
			"                END) team_leader,\n" + 
			"                group_concat(CASE a.emp_type \n" + 
			"                    WHEN '5' THEN a.emp_name \n" + 
			"                END) employee   \n" + 
			"            from\n" + 
			"                (select\n" + 
			"                    t_tasks.task_id,\n" + 
			"                    m_emp.emp_id,\n" + 
			"                    m_emp.emp_type,\n" + 
			"                    m_emp.emp_name \n" + 
			"                from\n" + 
			"                    m_emp,\n" + 
			"                    t_tasks \n" + 
			"                where\n" + 
			"                    FIND_IN_SET(m_emp.emp_id,t_tasks.task_emp_ids) \n" + 
			"                    and t_tasks.task_id IN  (\n" + 
			"                        SELECT\n" + 
			"                            t_tasks.task_id     \n" + 
			"                        FROM\n" + 
			"                            t_tasks   \n" + 
			"                        WHERE\n" + 
			"                            t_tasks.task_status != 9 \n" + 
			"                            AND t_tasks.del_status = 1 \n" + 
			"                            AND t_tasks.is_active = 1 \n" + 
			"                            AND  t_tasks.task_statutory_due_date between :fromDate and  :toDate \n" + 
			"                            AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids) \n" + 
			"                        group by\n" + 
			"                            t_tasks.task_id     \n" + 
			"                    )     \n" + 
			"                ) a \n" + 
			"            group by\n" + 
			"                a.task_id) c \n" + 
			"                ON b.task_id=c.task_id",nativeQuery=true)
		List<OverDueTaskReport> getEmpOverDueTaskBetFdTd( @Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("empIds") String empIds);

	
	@Query(value=" " + 
			"  SELECT\n" + 
			"        b.*,\n" + 
			"        \n" + 
			"        coalesce((c.admin),\n" + 
			"        '-')as admin,\n" + 
			"        coalesce((c.partner),\n" + 
			"        '-') as partner ,\n" + 
			"        coalesce((c.manager),\n" + 
			"        '-') as manager,\n" + 
			"        coalesce((c.team_leader),\n" + 
			"        '-') as team_leader,\n" + 
			"        coalesce((c.employee),\n" + 
			"        '-') as  employee \n" + 
			"    from\n" + 
			"        (SELECT\n" + 
			"         t_daily_work_log.work_log_id as unique_id,\n" + 
			"            t_tasks.task_id,\n" + 
			"            t_tasks.task_text,\n" + 
			"        \n" + 
			"            m_services.serv_name,\n" + 
			"            m_activities.acti_name,\n" + 
			"            dm_periodicity.periodicity_name,\n" + 
			"            t_tasks.task_statutory_due_date,\n" + 
			"            t_tasks.task_completion_date ,\n" + 
			"            t_tasks.task_end_date AS task_work_date,\n" + 
			"         0 as emp_bud_hr,\n" + 
			"	      0 as mngr_bud_hr,\n" + 
			"\n" + 
			"        \n" + 
			"         \n" + 
			"           COALESCE( (CONCAT(         FLOOR(             SUM(t_daily_work_log.work_hours) / 60         ),\n" + 
			"                '.',\n" + 
			"                LPAD(  MOD(             SUM(t_daily_work_log.work_hours),\n" + 
			"                60),\n" + 
			"                2,\n" + 
			"                '0')                  )),\n" + 
			"                0) AS work_hours,\n" + 
			"         \n" + 
			"         \n" + 
			"            m_cust_header.cust_firm_name,m_emp.emp_name as own_partner,\n" + 
			"         dm_status_mst.status_text\n" + 
			"        FROM\n" + 
			"            m_services,\n" + 
			"            m_activities,\n" + 
			"            dm_periodicity,\n" + 
			"            t_tasks,\n" + 
			"            m_cust_header,\n" + 
			"         dm_status_mst,\n" + 
			"         m_emp,t_daily_work_log\n" + 
			"        WHERE\n" + 
			"            m_services.serv_id = t_tasks.serv_id \n" + 
			"            AND m_activities.acti_id = t_tasks.actv_id \n" + 
			"            AND dm_periodicity.periodicity_id = t_tasks.periodicity_id \n" + 
			"            AND t_tasks.del_status = 1 \n" + 
			"            AND t_tasks.is_active = 1  \n" + 
			"            AND   t_tasks.cust_id = m_cust_header.cust_id      \n" + 
			"            AND  t_daily_work_log.work_date between :fromDate and :toDate \n" + 
			"         and t_daily_work_log.task_id=t_tasks.task_id\n" + 
			"         and t_daily_work_log.emp_id=1\n" + 
			"         and dm_status_mst.status_value=t_tasks.task_status \n" + 
			"            AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids)  \n" + 
			"         and m_emp.emp_id=m_cust_header.owner_emp_id\n" + 
			"        GROUP by t_daily_work_log.work_log_id ) b   \n" + 
			"  \n" + 
			"    LEFT JOIN\n" + 
			"        (\n" + 
			"            select\n" + 
			"                a.task_id,\n" + 
			"                group_concat(CASE a.emp_type \n" + 
			"                    WHEN '1' THEN a.emp_name  \n" + 
			"                END) admin,\n" + 
			"                group_concat(CASE a.emp_type \n" + 
			"                    WHEN '2' THEN a.emp_name  \n" + 
			"                END) partner,\n" + 
			"                group_concat(CASE a.emp_type \n" + 
			"                    WHEN '3' THEN a.emp_name  \n" + 
			"                END) manager,\n" + 
			"                group_concat(CASE a.emp_type \n" + 
			"                    WHEN '4' THEN a.emp_name \n" + 
			"                END) team_leader,\n" + 
			"                group_concat(CASE a.emp_type \n" + 
			"                    WHEN '5' THEN a.emp_name \n" + 
			"                END) employee   \n" + 
			"            from\n" + 
			"                (select\n" + 
			"                    t_tasks.task_id,\n" + 
			"                    m_emp.emp_id,\n" + 
			"                    m_emp.emp_type,\n" + 
			"                    m_emp.emp_name \n" + 
			"                from\n" + 
			"                    m_emp,\n" + 
			"                    t_tasks \n" + 
			"                where\n" + 
			"                    FIND_IN_SET(m_emp.emp_id,t_tasks.task_emp_ids) \n" + 
			"                    and t_tasks.task_id IN  (\n" + 
			"                        SELECT\n" + 
			"                            t_tasks.task_id     \n" + 
			"                        FROM\n" + 
			"                            t_tasks   \n" + 
			"                        WHERE\n" + 
			"                             t_tasks.del_status = 1 \n" + 
			"                            AND t_tasks.is_active = 1 \n" + 
			"                            AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids) \n" + 
			"                        group by\n" + 
			"                            t_tasks.task_id     \n" + 
			"                    )     \n" + 
			"                ) a \n" + 
			"            group by " + 
			"                a.task_id) c \n" + 
			"                ON b.task_id=c.task_id",nativeQuery=true)
		List<OverDueTaskReport> getEmpWorkDiaryBetDate( @Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("empIds") String empIds);

	

}
