package com.ats.cataskapi.report.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.report.EmpAndMngPerformanceRep;
 
public interface EmpAndMngPerformanceRepRepo extends JpaRepository<EmpAndMngPerformanceRep, Integer> {
	
	@Query(value="SELECT\n" + 
			"    e.emp_id,\n" + 
			"    e.emp_name,\n" + 
			"    CASE WHEN e.emp_type = 3 THEN(\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                CONCAT(\n" + 
			"                    FLOOR(SUM(mngr_bud_hr) / 60),\n" + 
			"                    '.',\n" + 
			"                    MOD(SUM(mngr_bud_hr),\n" + 
			"                    60)\n" + 
			"                )\n" + 
			"            FROM\n" + 
			"                t_tasks\n" + 
			"            WHERE\n" + 
			"                update_datetime BETWEEN :fromDate1 AND :toDate1 AND FIND_IN_SET(e.emp_id, task_emp_ids) AND FIND_IN_SET(:userId, task_emp_ids) AND is_active = 1 AND del_status = 1 AND t_tasks.task_status =:status \n" + 
			"        ),\n" + 
			"        0\n" + 
			"        )\n" + 
			"    ) ELSE(\n" + 
			"        COALESCE(\n" + 
			"            (\n" + 
			"            SELECT\n" + 
			"                CONCAT(\n" + 
			"                    FLOOR(SUM(emp_bud_hr) / 60),\n" + 
			"                    '.',\n" + 
			"                    MOD(SUM(emp_bud_hr),\n" + 
			"                    60)\n" + 
			"                )\n" + 
			"            FROM\n" + 
			"                t_tasks\n" + 
			"            WHERE\n" + 
			"                update_datetime BETWEEN  :fromDate1 AND :toDate1 AND FIND_IN_SET(e.emp_id, task_emp_ids) AND FIND_IN_SET(:userId, task_emp_ids) AND is_active = 1 AND del_status = 1 AND t_tasks.task_status =:status\n" + 
			"        ),\n" + 
			"        0\n" + 
			"        )\n" + 
			"    )\n" + 
			"END AS emp_work_hours,\n" + 
			"COALESCE(\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        CONCAT(\n" + 
			"            FLOOR(SUM(wl.work_hours) / 60),\n" + 
			"            '.',\n" + 
			"            MOD(SUM(wl.work_hours),\n" + 
			"            60)\n" + 
			"        )\n" + 
			"    FROM\n" + 
			"        t_daily_work_log wl,\n" + 
			"        t_tasks t\n" + 
			"    WHERE\n" + 
			"        wl.work_date BETWEEN :fromDate AND :toDate AND wl.emp_id = e.emp_id AND t.task_id = wl.task_id AND FIND_IN_SET(:userId, t.task_emp_ids)\n" + 
			"),\n" + 
			"0\n" + 
			") AS work_hours,\n" + 
			"COALESCE(\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        COUNT(*)\n" + 
			"    FROM\n" + 
			"        t_tasks\n" + 
			"    WHERE\n" + 
			"        update_datetime BETWEEN :fromDate1 AND :toDate1 AND FIND_IN_SET(e.emp_id, task_emp_ids) AND FIND_IN_SET(:userId, task_emp_ids) AND is_active = 1 AND del_status = 1 AND t_tasks.task_status =:status \n" + 
			"),\n" + 
			"0\n" + 
			") AS no_of_task,\n" + 
			"0 AS ex_var1,\n" + 
			"'2019-06-09' AS till_date,\n" + 
			"0 AS tot_avail_hrs\n" + 
			"FROM\n" + 
			"    m_emp e\n" + 
			"WHERE\n" + 
			"    e.del_status = 1 AND e.emp_id IN(:empId)",nativeQuery=true)
		List<EmpAndMngPerformanceRep> getAllTask( @Param("fromDate1") String fromDate1, @Param("toDate1") String toDate1, @Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("status") int status,@Param("empId") ArrayList<String> empId,@Param("userId") int userId);
	
	
	
	
	

}
