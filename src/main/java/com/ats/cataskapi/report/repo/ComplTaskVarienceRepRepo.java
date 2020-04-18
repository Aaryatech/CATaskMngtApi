package com.ats.cataskapi.report.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.reportv2.ComplTaskVarienceRep;

public interface ComplTaskVarienceRepRepo extends JpaRepository<ComplTaskVarienceRep, Integer> {
	
	
	@Query(value=" " + 
			"SELECT " + 
			"        b.*, " + 
			"        coalesce((x.work_hours), " + 
			"        0) as work_hours , " + 
			"         coalesce((x.work_min), " + 
			"        0) as work_min , " + 
			"        coalesce(( CONCAT(FLOOR( (b.emp_bud_min-x.work_min)/60),\n" + 
			"            ':',\n" + 
			"            LPAD(MOD((b.emp_bud_min-x.work_min),\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0'))),0) as emp_hr_variance,\n" + 
			"            \n" + 
			"            \n" + 
			"             coalesce(( CONCAT(FLOOR( (b.mng_bud_min-x.work_min)/60),\n" + 
			"            ':',\n" + 
			"            LPAD(MOD((b.mng_bud_min-x.work_min),\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0'))),0) as mng_hr_variance,\n" + 
			"            \n" + 
			"            \n" + 
			"                  \n" + 
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
			"            t_tasks.task_id,\n" + 
			"            t_tasks.task_text,\n" + 
			"            t_tasks.ex_var1,\n" + 
			"            m_services.serv_name,\n" + 
			"            m_activities.acti_name,\n" + 
			"            dm_periodicity.periodicity_name, " + 
			"            t_tasks.task_statutory_due_date, " + 
			"            t_tasks.task_completion_date , " + 
			"            t_tasks.task_end_date AS task_work_date, " + 
			"            CONCAT(FLOOR( t_tasks.emp_bud_hr/60), " + 
			"            ':',\n" + 
			"            LPAD(MOD(t_tasks.emp_bud_hr,\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0'))  as emp_bud_hr, " + 
			"            CONCAT(FLOOR( t_tasks.mngr_bud_hr/60), " + 
			"            ':', " + 
			"            LPAD(MOD(t_tasks.mngr_bud_hr, " + 
			"            60), " + 
			"            2, " + 
			"            '0')) as mngr_bud_hr, " + 
			"                 \n" + 
			"         DATEDIFF(t_tasks.task_statutory_due_date,t_tasks.task_completion_date) AS date_diff,\n" + 
			"         t_tasks.mngr_bud_hr AS mng_bud_min, " + 
			"         t_tasks.emp_bud_hr AS emp_bud_min, " + 
			"            m_cust_header.cust_firm_name,m_emp.emp_name as own_partner              \n" + 
			"        FROM " + 
			"            m_services, " + 
			"            m_activities, " + 
			"            dm_periodicity, " + 
			"            t_tasks, " + 
			"            m_cust_header, " + 
			"         m_emp " + 
			"        WHERE " + 
			"            m_services.serv_id = t_tasks.serv_id  " + 
			"            AND m_activities.acti_id = t_tasks.actv_id  " + 
			"            AND dm_periodicity.periodicity_id = t_tasks.periodicity_id  " + 
			"            AND t_tasks.task_status = 9  " + 
			"            AND t_tasks.del_status = 1  " + 
			"            AND t_tasks.is_active = 1   " + 
			"            AND   t_tasks.cust_id = m_cust_header.cust_id       " + 
			"            AND  t_tasks.task_completion_date between :fromDate and :toDate  " + 
			"            AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids)   " + 
			"         and m_emp.emp_id=m_cust_header.owner_emp_id " + 
			"        group by " + 
			"            t_tasks.task_id) b    " + 
			"    left join " + 
			"        ( " + 
			"            SELECT " + 
			"                t_daily_work_log.task_id, " + 
			"                COALESCE( (CONCAT(         FLOOR(             SUM(t_daily_work_log.work_hours) / 60         ), " + 
			"                '.', " + 
			"                LPAD(  MOD(             SUM(t_daily_work_log.work_hours),\n" + 
			"                60),\n" + 
			"                2,\n" + 
			"                '0')                  )),\n" + 
			"                0) AS work_hours,\n" + 
			"            \n" + 
			"            SUM(t_daily_work_log.work_hours) as work_min\n" + 
			"            from\n" + 
			"                t_daily_work_log,\n" + 
			"                t_tasks                 \n" + 
			"            WHERE\n" + 
			"                t_tasks.task_status = 9                              \n" + 
			"                AND t_tasks.del_status = 1                              \n" + 
			"                AND t_tasks.is_active = 1                               \n" + 
			"                AND    t_daily_work_log.task_id=t_tasks.task_id   \n" + 
			"                and t_daily_work_log.emp_id IN (:empIds) \n" + 
			"                and t_daily_work_log.del_status=1                              \n" + 
			"                AND  t_tasks.task_completion_date between :fromDate and :toDate                        \n" + 
			"                AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids)                          \n" + 
			"            group by\n" + 
			"                t_daily_work_log.task_id                     \n" + 
			"        ) x \n" + 
			"            on b.task_id=x.task_id    \n" + 
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
			"                            t_tasks.task_status = 9 \n" + 
			"                            AND t_tasks.del_status = 1 \n" + 
			"                            AND t_tasks.is_active = 1 \n" + 
			"                            AND  t_tasks.task_completion_date between :fromDate and :toDate \n" + 
			"                            AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids) \n" + 
			"                        group by\n" + 
			"                            t_tasks.task_id     \n" + 
			"                    )     \n" + 
			"                ) a \n" + 
			"            group by\n" + 
			"                a.task_id) c \n" + 
			"                ON b.task_id=c.task_id",nativeQuery=true)
		List<ComplTaskVarienceRep> getComplTaskVarienceReport1( @Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("empIds") String empIds);

	@Query(value=" " + 
			"SELECT " + 
			"        b.task_id "
			+ " ,b.task_text,b.ex_var1 as del_link,b.serv_name,"
			+ "b.acti_name,b.periodicity_name,b.task_statutory_due_date,"
			+ "b.task_completion_date,b.task_work_date,b.emp_bud_hr,b.mngr_bud_hr,"
			+ "b.mng_bud_min,b.emp_bud_min,b.own_partner,b.cust_firm_name,b.date_diff,"
			+ ""
			+ ""
			+ ""
			+ " coalesce((b.emp_bud_min-x.work_min),  " 
			+"		   0) as hour_diff_emp ," + 
 " coalesce((b.mng_bud_min-x.work_min), " + 
"			    0) as hour_diff_mng ," + 
			"        coalesce((x.work_hours), " + 
			"        0) as work_hours , " + 
			"         coalesce((x.work_min), " + 
			"        0) as work_min , " + 
			"        coalesce(( CONCAT(FLOOR( (b.emp_bud_min-x.work_min)/60),\n" + 
			"            ':',\n" + 
			"            LPAD(MOD((b.emp_bud_min-x.work_min),\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0'))),0) as emp_hr_variance,\n" + 
			"            \n" + 
			"            \n" + 
			"             coalesce(( CONCAT(FLOOR( (b.mng_bud_min-x.work_min)/60),\n" + 
			"            ':',\n" + 
			"            LPAD(MOD((b.mng_bud_min-x.work_min),\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0'))),0) as mng_hr_variance,\n" + 
			"            \n" + 
			"            \n" + 
			"                  \n" + 
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
			"            t_tasks.task_id,\n" + 
			"            t_tasks.task_text,\n" + 
			"            t_tasks.ex_var1,\n" + 
			"            m_services.serv_name,\n" + 
			"            m_activities.acti_name,\n" + 
			"            dm_periodicity.periodicity_name, " + 
			"            t_tasks.task_statutory_due_date, " + 
			"            t_tasks.task_completion_date , " + 
			"            t_tasks.task_end_date AS task_work_date, " + 
			"            CONCAT(FLOOR( t_tasks.emp_bud_hr/60), " + 
			"            ':',\n" + 
			"            LPAD(MOD(t_tasks.emp_bud_hr,\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0'))  as emp_bud_hr, " + 
			"            CONCAT(FLOOR( t_tasks.mngr_bud_hr/60), " + 
			"            ':', " + 
			"            LPAD(MOD(t_tasks.mngr_bud_hr, " + 
			"            60), " + 
			"            2, " + 
			"            '0')) as mngr_bud_hr, " + 
			"                 \n" + 
			"         DATEDIFF(t_tasks.task_statutory_due_date,t_tasks.task_completion_date) AS date_diff,\n" + 
			"         t_tasks.mngr_bud_hr AS mng_bud_min, " + 
			"         t_tasks.emp_bud_hr AS emp_bud_min, " + 
			"            m_cust_header.cust_firm_name,m_emp.emp_name as own_partner              \n" + 
			"        FROM " + 
			"            m_services, " + 
			"            m_activities, " + 
			"            dm_periodicity, " + 
			"            t_tasks, " + 
			"            m_cust_header, " + 
			"         m_emp " + 
			"        WHERE " + 
			"            m_services.serv_id = t_tasks.serv_id  " + 
			"            AND m_activities.acti_id = t_tasks.actv_id  " + 
			"            AND dm_periodicity.periodicity_id = t_tasks.periodicity_id  " + 
			"            AND t_tasks.task_status = 9  " + 
			"            AND t_tasks.del_status = 1  " + 
			"            AND t_tasks.is_active = 1   " + 
			"            AND   t_tasks.cust_id = m_cust_header.cust_id       " + 
			"            AND  t_tasks.task_completion_date between :fromDate and :toDate  " + 
			"            AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids)   " + 
			"         and m_emp.emp_id=m_cust_header.owner_emp_id " + 
			"        group by " + 
			"            t_tasks.task_id) b    " + 
			"    left join " + 
			"        ( " + 
			"            SELECT " + 
			"                t_daily_work_log.task_id, " + 
			"                COALESCE( (CONCAT(         FLOOR(             SUM(t_daily_work_log.work_hours) / 60         ), " + 
			"                '.', " + 
			"                LPAD(  MOD(             SUM(t_daily_work_log.work_hours),\n" + 
			"                60),\n" + 
			"                2,\n" + 
			"                '0')                  )),\n" + 
			"                0) AS work_hours,\n" + 
			"            \n" + 
			"            SUM(t_daily_work_log.work_hours) as work_min\n" + 
			"            from\n" + 
			"                t_daily_work_log,\n" + 
			"                t_tasks                 \n" + 
			"            WHERE\n" + 
			"                t_tasks.task_status = 9                              \n" + 
			"                AND t_tasks.del_status = 1                              \n" + 
			"                AND t_tasks.is_active = 1                               \n" + 
			"                AND    t_daily_work_log.task_id=t_tasks.task_id   \n" + 
			"                and t_daily_work_log.emp_id IN (:empIds) \n" + 
			"                and t_daily_work_log.del_status=1                              \n" + 
			"                AND  t_tasks.task_completion_date between :fromDate and :toDate                        \n" + 
			"                AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids)                          \n" + 
			"            group by\n" + 
			"                t_daily_work_log.task_id                     \n" + 
			"        ) x \n" + 
			"            on b.task_id=x.task_id    \n" + 
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
			"                            t_tasks.task_status = 9 \n" + 
			"                            AND t_tasks.del_status = 1 \n" + 
			"                            AND t_tasks.is_active = 1 \n" + 
			"                            AND  t_tasks.task_completion_date between :fromDate and :toDate \n" + 
			"                            AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids) \n" + 
			"                        group by\n" + 
			"                            t_tasks.task_id     \n" + 
			"                    )     \n" + 
			"                ) a \n" + 
			"            group by\n" + 
			"                a.task_id) c \n" + 
			"                ON b.task_id=c.task_id order by b.date_diff desc",nativeQuery=true)
		List<ComplTaskVarienceRep> getComplTaskVarienceReportDateDiff( @Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("empIds") String empIds);

	
	@Query(value=" " + 
			"SELECT " + 
			"        b.task_id "
			+ " ,b.task_text,b.ex_var1 as del_link,b.serv_name,"
			+ "b.acti_name,b.periodicity_name,b.task_statutory_due_date,"
			+ "b.task_completion_date,b.task_work_date,b.emp_bud_hr,b.mngr_bud_hr,"
			+ "b.mng_bud_min,b.emp_bud_min,b.own_partner,b.cust_firm_name,b.date_diff,"
			+ ""
			+ ""
			+ ""
			+ " coalesce((b.emp_bud_min-x.work_min),  " 
			+"		   0) as hour_diff_emp ," + 
 " coalesce((b.mng_bud_min-x.work_min), " + 
"			    0) as hour_diff_mng ," + 
			"        coalesce((x.work_hours), " + 
			"        0) as work_hours , " + 
			"         coalesce((x.work_min), " + 
			"        0) as work_min , " + 
			"        coalesce(( CONCAT(FLOOR( (b.emp_bud_min-x.work_min)/60),\n" + 
			"            ':',\n" + 
			"            LPAD(MOD((b.emp_bud_min-x.work_min),\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0'))),0) as emp_hr_variance,\n" + 
			"            \n" + 
			"            \n" + 
			"             coalesce(( CONCAT(FLOOR( (b.mng_bud_min-x.work_min)/60),\n" + 
			"            ':',\n" + 
			"            LPAD(MOD((b.mng_bud_min-x.work_min),\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0'))),0) as mng_hr_variance,\n" + 
			"            \n" + 
			"            \n" + 
			"                  \n" + 
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
			"            t_tasks.task_id,\n" + 
			"            t_tasks.task_text,\n" + 
			"            t_tasks.ex_var1,\n" + 
			"            m_services.serv_name,\n" + 
			"            m_activities.acti_name,\n" + 
			"            dm_periodicity.periodicity_name, " + 
			"            t_tasks.task_statutory_due_date, " + 
			"            t_tasks.task_completion_date , " + 
			"            t_tasks.task_end_date AS task_work_date, " + 
			"            CONCAT(FLOOR( t_tasks.emp_bud_hr/60), " + 
			"            ':',\n" + 
			"            LPAD(MOD(t_tasks.emp_bud_hr,\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0'))  as emp_bud_hr, " + 
			"            CONCAT(FLOOR( t_tasks.mngr_bud_hr/60), " + 
			"            ':', " + 
			"            LPAD(MOD(t_tasks.mngr_bud_hr, " + 
			"            60), " + 
			"            2, " + 
			"            '0')) as mngr_bud_hr, " + 
			"                 \n" + 
			"         DATEDIFF(t_tasks.task_statutory_due_date,t_tasks.task_completion_date) AS date_diff,\n" + 
			"         t_tasks.mngr_bud_hr AS mng_bud_min, " + 
			"         t_tasks.emp_bud_hr AS emp_bud_min, " + 
			"            m_cust_header.cust_firm_name,m_emp.emp_name as own_partner              \n" + 
			"        FROM " + 
			"            m_services, " + 
			"            m_activities, " + 
			"            dm_periodicity, " + 
			"            t_tasks, " + 
			"            m_cust_header, " + 
			"         m_emp " + 
			"        WHERE " + 
			"            m_services.serv_id = t_tasks.serv_id  " + 
			"            AND m_activities.acti_id = t_tasks.actv_id  " + 
			"            AND dm_periodicity.periodicity_id = t_tasks.periodicity_id  " + 
			"            AND t_tasks.task_status = 9  " + 
			"            AND t_tasks.del_status = 1  " + 
			"            AND t_tasks.is_active = 1   " + 
			"            AND   t_tasks.cust_id = m_cust_header.cust_id       " + 
			"            AND  t_tasks.task_completion_date between :fromDate and :toDate  " + 
			"            AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids)   " + 
			"         and m_emp.emp_id=m_cust_header.owner_emp_id " + 
			"        group by " + 
			"            t_tasks.task_id) b    " + 
			"    left join " + 
			"        ( " + 
			"            SELECT " + 
			"                t_daily_work_log.task_id, " + 
			"                COALESCE( (CONCAT(         FLOOR(             SUM(t_daily_work_log.work_hours) / 60         ), " + 
			"                '.', " + 
			"                LPAD(  MOD(             SUM(t_daily_work_log.work_hours),\n" + 
			"                60),\n" + 
			"                2,\n" + 
			"                '0')                  )),\n" + 
			"                0) AS work_hours,\n" + 
			"            \n" + 
			"            SUM(t_daily_work_log.work_hours) as work_min\n" + 
			"            from\n" + 
			"                t_daily_work_log,\n" + 
			"                t_tasks                 \n" + 
			"            WHERE\n" + 
			"                t_tasks.task_status = 9                              \n" + 
			"                AND t_tasks.del_status = 1                              \n" + 
			"                AND t_tasks.is_active = 1                               \n" + 
			"                AND    t_daily_work_log.task_id=t_tasks.task_id   \n" + 
			"                and t_daily_work_log.emp_id IN (:empIds) \n" + 
			"                and t_daily_work_log.del_status=1                              \n" + 
			"                AND  t_tasks.task_completion_date between :fromDate and :toDate                        \n" + 
			"                AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids)                          \n" + 
			"            group by\n" + 
			"                t_daily_work_log.task_id                     \n" + 
			"        ) x \n" + 
			"            on b.task_id=x.task_id    \n" + 
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
			"                            t_tasks.task_status = 9 \n" + 
			"                            AND t_tasks.del_status = 1 \n" + 
			"                            AND t_tasks.is_active = 1 \n" + 
			"                            AND  t_tasks.task_completion_date between :fromDate and :toDate \n" + 
			"                            AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids) \n" + 
			"                        group by\n" + 
			"                            t_tasks.task_id     \n" + 
			"                    )     \n" + 
			"                ) a \n" + 
			"            group by\n" + 
			"                a.task_id) c \n" + 
			"                ON b.task_id=c.task_id order by hour_diff_emp desc",nativeQuery=true)
		List<ComplTaskVarienceRep> getComplTaskVarienceReportHourDiffEmp( @Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("empIds") String empIds);

	
	
	@Query(value=" " + 
			"SELECT " + 
			"        b.task_id "
			+ " ,b.task_text,b.ex_var1 as del_link,b.serv_name,"
			+ "b.acti_name,b.periodicity_name,b.task_statutory_due_date,"
			+ "b.task_completion_date,b.task_work_date,b.emp_bud_hr,b.mngr_bud_hr,"
			+ "b.mng_bud_min,b.emp_bud_min,b.own_partner,b.cust_firm_name,b.date_diff,"
			+ ""
			+ ""
			+ ""
			+ " coalesce((b.emp_bud_min-x.work_min),  " 
			+"		   0) as hour_diff_emp ," + 
 " coalesce((b.mng_bud_min-x.work_min), " + 
"			    0) as hour_diff_mng ," + 
			"        coalesce((x.work_hours), " + 
			"        0) as work_hours , " + 
			"         coalesce((x.work_min), " + 
			"        0) as work_min , " + 
			"        coalesce(( CONCAT(FLOOR( (b.emp_bud_min-x.work_min)/60),\n" + 
			"            ':',\n" + 
			"            LPAD(MOD((b.emp_bud_min-x.work_min),\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0'))),0) as emp_hr_variance,\n" + 
			"            \n" + 
			"            \n" + 
			"             coalesce(( CONCAT(FLOOR( (b.mng_bud_min-x.work_min)/60),\n" + 
			"            ':',\n" + 
			"            LPAD(MOD((b.mng_bud_min-x.work_min),\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0'))),0) as mng_hr_variance,\n" + 
			"            \n" + 
			"            \n" + 
			"                  \n" + 
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
			"            t_tasks.task_id,\n" + 
			"            t_tasks.task_text,\n" + 
			"            t_tasks.ex_var1,\n" + 
			"            m_services.serv_name,\n" + 
			"            m_activities.acti_name,\n" + 
			"            dm_periodicity.periodicity_name, " + 
			"            t_tasks.task_statutory_due_date, " + 
			"            t_tasks.task_completion_date , " + 
			"            t_tasks.task_end_date AS task_work_date, " + 
			"            CONCAT(FLOOR( t_tasks.emp_bud_hr/60), " + 
			"            ':',\n" + 
			"            LPAD(MOD(t_tasks.emp_bud_hr,\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0'))  as emp_bud_hr, " + 
			"            CONCAT(FLOOR( t_tasks.mngr_bud_hr/60), " + 
			"            ':', " + 
			"            LPAD(MOD(t_tasks.mngr_bud_hr, " + 
			"            60), " + 
			"            2, " + 
			"            '0')) as mngr_bud_hr, " + 
			"                 \n" + 
			"         DATEDIFF(t_tasks.task_statutory_due_date,t_tasks.task_completion_date) AS date_diff,\n" + 
			"         t_tasks.mngr_bud_hr AS mng_bud_min, " + 
			"         t_tasks.emp_bud_hr AS emp_bud_min, " + 
			"            m_cust_header.cust_firm_name,m_emp.emp_name as own_partner              \n" + 
			"        FROM " + 
			"            m_services, " + 
			"            m_activities, " + 
			"            dm_periodicity, " + 
			"            t_tasks, " + 
			"            m_cust_header, " + 
			"         m_emp " + 
			"        WHERE " + 
			"            m_services.serv_id = t_tasks.serv_id  " + 
			"            AND m_activities.acti_id = t_tasks.actv_id  " + 
			"            AND dm_periodicity.periodicity_id = t_tasks.periodicity_id  " + 
			"            AND t_tasks.task_status = 9  " + 
			"            AND t_tasks.del_status = 1  " + 
			"            AND t_tasks.is_active = 1   " + 
			"            AND   t_tasks.cust_id = m_cust_header.cust_id       " + 
			"            AND  t_tasks.task_completion_date between :fromDate and :toDate  " + 
			"            AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids)   " + 
			"         and m_emp.emp_id=m_cust_header.owner_emp_id " + 
			"        group by " + 
			"            t_tasks.task_id) b    " + 
			"    left join " + 
			"        ( " + 
			"            SELECT " + 
			"                t_daily_work_log.task_id, " + 
			"                COALESCE( (CONCAT(         FLOOR(             SUM(t_daily_work_log.work_hours) / 60         ), " + 
			"                '.', " + 
			"                LPAD(  MOD(             SUM(t_daily_work_log.work_hours),\n" + 
			"                60),\n" + 
			"                2,\n" + 
			"                '0')                  )),\n" + 
			"                0) AS work_hours,\n" + 
			"            \n" + 
			"            SUM(t_daily_work_log.work_hours) as work_min\n" + 
			"            from\n" + 
			"                t_daily_work_log,\n" + 
			"                t_tasks                 \n" + 
			"            WHERE\n" + 
			"                t_tasks.task_status = 9                              \n" + 
			"                AND t_tasks.del_status = 1                              \n" + 
			"                AND t_tasks.is_active = 1                               \n" + 
			"                AND    t_daily_work_log.task_id=t_tasks.task_id   \n" + 
			"                and t_daily_work_log.emp_id IN (:empIds) \n" + 
			"                and t_daily_work_log.del_status=1                              \n" + 
			"                AND  t_tasks.task_completion_date between :fromDate and :toDate                        \n" + 
			"                AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids)                          \n" + 
			"            group by\n" + 
			"                t_daily_work_log.task_id                     \n" + 
			"        ) x \n" + 
			"            on b.task_id=x.task_id    \n" + 
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
			"                            t_tasks.task_status = 9 \n" + 
			"                            AND t_tasks.del_status = 1 \n" + 
			"                            AND t_tasks.is_active = 1 \n" + 
			"                            AND  t_tasks.task_completion_date between :fromDate and :toDate \n" + 
			"                            AND FIND_IN_SET(:empIds,t_tasks.task_emp_ids) \n" + 
			"                        group by\n" + 
			"                            t_tasks.task_id     \n" + 
			"                    )     \n" + 
			"                ) a \n" + 
			"            group by\n" + 
			"                a.task_id) c \n" + 
			"                ON b.task_id=c.task_id order by hour_diff_mng desc",nativeQuery=true)
		List<ComplTaskVarienceRep> getComplTaskVarienceReportHourDiffMng( @Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("empIds") String empIds);


}
