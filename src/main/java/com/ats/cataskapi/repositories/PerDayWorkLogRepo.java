package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.PerDayWorkLog;

public interface PerDayWorkLogRepo extends JpaRepository<PerDayWorkLog, Integer> {

	@Query(value="SELECT\n" + 
			"		d.work_log_id,\n" + 
			"        d.emp_id,\n" + 
			"        d.task_id,\n" + 
			"    	d.work_date,\n" + 
			"        e.emp_name,\n" + 
			"        e.emp_type,\n" + 
			"    	CONCAT(FLOOR(d.work_hours/60),':', LPAD(MOD(d.work_hours, 60),2, '0')) as work_hours\n" + 
			"FROM\n" + 
			"    	 t_daily_work_log d,\n" + 
			"         m_emp e,\n" + 
			"         t_tasks t\n" + 
			"WHERE\n" + 
			"		 t.task_id=:taskId         \n" + 
			"        AND   d.del_status=1          \n" + 
			"        AND   d.task_id=t.task_id          \n" + 
			"        AND   d.emp_id=e.emp_id",nativeQuery=true)
	List<PerDayWorkLog> getPerDayLogByTaskId(@Param("taskId") int taskId);
}
