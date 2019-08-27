package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.TotalWorkHrs;

public interface TotalWorkHrsRepo extends JpaRepository<TotalWorkHrs, Integer> {

	@Query(value="SELECT  t_daily_work_log.work_log_id,\n" + 
			"        SUM(t_daily_work_log.work_hours) AS ttl_work_hrs,\n" + 
			"        m_emp.emp_name,\n" + 
			"        m_emp.emp_nickname,\n" + 
			"        t_tasks.task_text,\n" + 
			"       t_daily_work_log.ex_int1, t_daily_work_log.ex_var1\n" + 
			"FROM t_tasks, m_emp, t_daily_work_log\n" + 			
			"WHERE t_daily_work_log.task_id=:taskId AND\n" + 
			"        t_daily_work_log.del_status=1 AND\n" + 
			"        t_daily_work_log.emp_id=m_emp.emp_id AND\n" + 
			"        t_daily_work_log.task_id=t_tasks.task_id\n" + 
			"        GROUP BY t_daily_work_log.emp_id",nativeQuery=true)
	public List<TotalWorkHrs> getEmpTtlWorkHrs(@Param("taskId") int taskId);
}
