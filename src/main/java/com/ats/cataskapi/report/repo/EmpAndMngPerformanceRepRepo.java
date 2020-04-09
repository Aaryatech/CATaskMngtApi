package com.ats.cataskapi.report.repo;

 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.report.EmpAndMngPerformanceRep;
 
public interface EmpAndMngPerformanceRepRepo extends JpaRepository<EmpAndMngPerformanceRep, Integer> {
	/*
	 * @Query(value="select\n" + "        e.emp_id,\n" +
	 * "        e.emp_name,e.emp_type,\n" +
	 * "        0 as bugeted_cap, 0 as budgeted_cap,\n" +
	 * "       ' NA' as ex_var1,'2019-09-09' as till_date ,\n" + "        case\n" +
	 * "            when e.emp_type=3             then             coalesce((select\n"
	 * + "                CONCAT(FLOOR(sum(mngr_bud_hr)/60),\n" +
	 * "                '.',\n" + "                LPAD(MOD( sum(mngr_bud_hr),\n" +
	 * "                60), 2, '0'))          \n" + "            from\n" +
	 * "                t_tasks          \n" + "            where\n" +
	 * "                 FIND_IN_SET(e.emp_id,task_emp_ids)  \n" +
	 * "                and del_status=1 and task_id not in (select task_id from t_tasks where (task_completion_date < :fromDate and task_status=9) or (date(update_datetime)< :fromDate and is_active=0))),\n"
	 * + "            0)            \n" +
	 * "            when e.emp_type=5 then  coalesce((select\n" +
	 * "                CONCAT(FLOOR(sum(emp_bud_hr)/60),\n" +
	 * "                '.',\n" + "                LPAD(MOD( sum(emp_bud_hr),\n" +
	 * "                60), 2, '0'))          \n" + "            from\n" +
	 * "                t_tasks          \n" + "            where\n" +
	 * "                 FIND_IN_SET(e.emp_id,task_emp_ids)\n" +
	 * "                and del_status=1 and task_id not in (select task_id from t_tasks where (task_completion_date < :fromDate and task_status=9) or (date(update_datetime)< :fromDate and is_active=0))),\n"
	 * + "            0)\n" + "            else\n" + "            0\n" +
	 * "        end as all_work ,\n" + "        coalesce((select\n" +
	 * "            CONCAT(FLOOR(sum(wl.work_hours)/60),\n" + "            '.',\n" +
	 * "           LPAD( MOD( sum(wl.work_hours),\n" +
	 * "            60), 2, '0'))          \n" + "        from\n" +
	 * "            t_daily_work_log wl          \n" + "        where\n" +
	 * "            wl.work_date between :fromDate and :toDate \n" +
	 * "            and wl.emp_id=e.emp_id),\n" + "        0) as act_work ,\n" +
	 * "        coalesce((select\n" + "            count(*)          \n" +
	 * "        from\n" + "                t_tasks          \n" +
	 * "            where\n" +
	 * "                FIND_IN_SET(e.emp_id,task_emp_ids)\n" +
	 * "                and del_status=1 and task_id not in (select task_id from t_tasks where (task_completion_date < :fromDate and task_status=9) or (date(update_datetime) < :fromDate and is_active=0))),\n"
	 * + "        0) as task_count\n" + "    from\n" + "        m_emp e      \n" +
	 * "    where\n" + "        e.del_status=1          \n" +
	 * "        and e.emp_id in (\n" + "           :empIdList \n" +
	 * "        )",nativeQuery=true) List<EmpAndMngPerformanceRep>
	 * getAllTask(@Param("fromDate") String fromDate, @Param("toDate") String
	 * toDate,@Param("empIdList") List<String> empIdList);
	 * 
	 */
	//New On 20-02-2020 Sachin And Mahesh
	@Query(value="select\n" + 
			"        e.emp_id,\n" + 
			"        e.emp_name,\n" + 
			"        e.emp_type,\n" + 
			"        0 as bugeted_cap,\n" + 
			"        0 as budgeted_cap,\n" + 
			"      coalesce((select\n" + 
			"            CONCAT(FLOOR(sum(wl.work_hours)/60),\n" + 
			"            '.',\n" + 
			"            LPAD( MOD( sum(wl.work_hours),\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0'))                   \n" + 
			"        from\n" + 
			"            t_daily_work_log wl                   \n" + 
			"        where\n" + 
			"           wl.emp_id=e.emp_id and wl.task_id IN (select\n" + 
			"           task_id                \n" + 
			"        from\n" + 
			"            t_tasks                       \n" + 
			"        where\n" + 
			"            FIND_IN_SET(e.emp_id,task_emp_ids)                 \n" + 
			"            and del_status=1 \n" + 
			"            and task_id not in (select\n" + 
			"                task_id \n" + 
			"            from\n" + 
			"                t_tasks \n" + 
			"            where\n" + 
			"                (task_completion_date < :fromDate\n" + 
			"                and task_status=9) \n" + 
			"                or (date(update_datetime) < :fromDate\n" + 
			"                and is_active=0)))),\n" + 
			"        0) as  ex_var1,\n" + 
			"        '2019-09-09' as till_date ,\n" + 
			"        case             \n" + 
			"            when e.emp_type=3             then             coalesce((select\n" + 
			"                CONCAT(FLOOR(sum(mngr_bud_hr)/60),\n" + 
			"                '.',\n" + 
			"                LPAD(MOD( sum(mngr_bud_hr),\n" + 
			"                60),\n" + 
			"                2,\n" + 
			"                '0'))                       \n" + 
			"            from\n" + 
			"                t_tasks                       \n" + 
			"            where\n" + 
			"                FIND_IN_SET(e.emp_id,task_emp_ids)                   \n" + 
			"                and del_status=1 \n" + 
			"                and task_id not in (select\n" + 
			"                    task_id \n" + 
			"                from\n" + 
			"                    t_tasks \n" + 
			"                where\n" + 
			"                    (task_completion_date <  :fromDate\n" + 
			"                    and task_status=9) \n" + 
			"                    or (date(update_datetime)< :fromDate \n" + 
			"                    and is_active=0))),             0)                         \n" + 
			"            when e.emp_type=5 then  coalesce((select\n" + 
			"                CONCAT(FLOOR(sum(emp_bud_hr)/60),\n" + 
			"                '.',\n" + 
			"                LPAD(MOD( sum(emp_bud_hr),\n" + 
			"                60),\n" + 
			"                2,\n" + 
			"                '0'))                       \n" + 
			"            from\n" + 
			"                t_tasks                       \n" + 
			"            where\n" + 
			"                FIND_IN_SET(e.emp_id,task_emp_ids)                 \n" + 
			"                and del_status=1 \n" + 
			"                and task_id not in (select\n" + 
			"                    task_id \n" + 
			"                from\n" + 
			"                    t_tasks \n" + 
			"                where\n" + 
			"                    (task_completion_date < :fromDate \n" + 
			"                    and task_status=9) \n" + 
			"                    or (date(update_datetime)< :fromDate \n" + 
			"                    and is_active=0))),             0)             \n" + 
			"            else             0         \n" + 
			"        end as all_work ,\n" + 
			"          coalesce((select\n" + 
			"            CONCAT(FLOOR(sum(wl.work_hours)/60),\n" + 
			"            '.',\n" + 
			"            LPAD( MOD( sum(wl.work_hours),\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0'))                   \n" + 
			"        from\n" + 
			"            t_daily_work_log wl                   \n" + 
			"        where\n" + 
			"            wl.work_date between :fromDate  and :toDate              \n" + 
			"            and wl.emp_id=e.emp_id and wl.task_id IN (select\n" + 
			"           task_id                \n" + 
			"        from\n" + 
			"            t_tasks                       \n" + 
			"        where\n" + 
			"            FIND_IN_SET(e.emp_id,task_emp_ids)                 \n" + 
			"            and del_status=1 \n" + 
			"            and task_id not in (select\n" + 
			"                task_id \n" + 
			"            from\n" + 
			"                t_tasks \n" + 
			"            where\n" + 
			"                (task_completion_date < :fromDate\n" + 
			"                and task_status=9) \n" + 
			"                or (date(update_datetime) < :fromDate\n" + 
			"                and is_active=0)))), " + 
			"        0) as act_work ,\n" + 
			"        coalesce((select\n" + 
			"            count(*)                   \n" + 
			"        from\n" + 
			"            t_tasks                        " + 
			"        where\n" + 
			"            FIND_IN_SET(e.emp_id,task_emp_ids)                  " + 
			"            and del_status=1 \n" + 
			"            and task_id not in (select\n" + 
			"                task_id \n" + 
			"            from\n" + 
			"                t_tasks \n" + 
			"            where\n" + 
			"                (task_completion_date < :fromDate  " + 
			"                and task_status=9)  " + 
			"                or (date(update_datetime) < :fromDate\n" + 
			"                and is_active=0))),         0) as task_count      " + 
			"    from\n" + 
			"        m_emp e           " + 
			"    where\n" + 
			"        e.del_status=1                   \n" + 
			"        and e.emp_id in ( " + 
			"          :empIdList " + 
			"        )",nativeQuery=true)
		List<EmpAndMngPerformanceRep> getAllTask1(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("empIdList") List<String> empIdList);
	
	//today 06-04-2020
@Query(value="select\n" + 
			"        e.emp_id,\n" + 
			"        e.emp_name,\n" + 
			"        e.emp_type,\n" + 
			"        0 as bugeted_cap,\n" + 
			"        0 as budgeted_cap,\n" + 
			"      coalesce((select\n" + 
			"            CONCAT(FLOOR(sum(wl.work_hours)/60),\n" + 
			"            '.',\n" + 
			"            LPAD( MOD( sum(wl.work_hours),\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0'))                   \n" + 
			"        from\n" + 
			"            t_daily_work_log wl                   \n" + 
			"        where\n" + 
			"           wl.emp_id=e.emp_id and wl.task_id IN (select\n" + 
			"           task_id                \n" + 
			"        from\n" + 
			"            t_tasks                       \n" + 
			"        where\n" + 
			"            FIND_IN_SET(e.emp_id,task_emp_ids)                 \n" + 
			"            and del_status=1 \n" + 
			"            and task_completion_date between :fromDate and :toDate  )),\n" + 
			"        0) as  ex_var1,\n" + 
			"        '2019-09-09' as till_date ,\n" + 
			"        case             \n" + 
			"            when e.emp_type=3             then             coalesce((select\n" + 
			"                CONCAT(FLOOR(sum(mngr_bud_hr)/60),\n" + 
			"                '.',\n" + 
			"                LPAD(MOD( sum(mngr_bud_hr),\n" + 
			"                60),\n" + 
			"                2,\n" + 
			"                '0'))                       \n" + 
			"            from\n" + 
			"                t_tasks                       \n" + 
			"            where\n" + 
			"                FIND_IN_SET(e.emp_id,task_emp_ids)                   \n" + 
			"                and del_status=1 \n" + 
			"                and task_completion_date between :fromDate and :toDate AND t_tasks.task_status=9),             0)                         \n" + 
			"            when e.emp_type=5 then  coalesce((select\n" + 
			"                CONCAT(FLOOR(sum(emp_bud_hr)/60),\n" + 
			"                '.',\n" + 
			"                LPAD(MOD( sum(emp_bud_hr),\n" + 
			"                60),\n" + 
			"                2,\n" + 
			"                '0'))                       \n" + 
			"            from\n" + 
			"                t_tasks                       \n" + 
			"            where\n" + 
			"                FIND_IN_SET(e.emp_id,task_emp_ids)                 \n" + 
			"                and del_status=1 \n" + 
			"                and task_completion_date between :fromDate and :toDate AND t_tasks.task_status=9 ),             0)             \n" + 
			"            else             0         \n" + 
			"        end as all_work ,\n" + 
			"          coalesce((select\n" + 
			"            CONCAT(FLOOR(sum(wl.work_hours)/60),\n" + 
			"            '.',\n" + 
			"            LPAD( MOD( sum(wl.work_hours),\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0'))                   \n" + 
			"        from\n" + 
			"            t_daily_work_log wl                   \n" + 
			"        where\n" + 
			"            wl.work_date between :fromDate  and :toDate              \n" + 
			"            and wl.emp_id=e.emp_id and wl.task_id IN (select\n" + 
			"           task_id                \n" + 
			"        from\n" + 
			"            t_tasks                       \n" + 
			"        where\n" + 
			"            FIND_IN_SET(e.emp_id,task_emp_ids)                 \n" + 
			"            and del_status=1 \n" + 
			"            and task_completion_date between :fromDate and :toDate AND task_status=9 )), " + 
			"        0) as act_work ,\n" + 
			"        coalesce((select\n" + 
			"            count(*)                   \n" + 
			"        from\n" + 
			"            t_tasks                        " + 
			"        where\n" + 
			"            FIND_IN_SET(e.emp_id,task_emp_ids)                  " + 
			"            and del_status=1 \n" + 
			"            and task_completion_date between :fromDate and :toDate AND task_status=9 ),         0) as task_count      " + 
			"    from\n" + 
			"        m_emp e           " + 
			"    where\n" + 
			"        e.del_status=1                   \n" + 
			"        and e.emp_id in ( " + 
			"          :empIdList " + 
			"        )",nativeQuery=true)
		List<EmpAndMngPerformanceRep> getAllTask(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("empIdList") List<String> empIdList);
	
	
	
	

}
