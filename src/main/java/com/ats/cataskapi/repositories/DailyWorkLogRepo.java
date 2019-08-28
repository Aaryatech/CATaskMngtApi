package com.ats.cataskapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.cataskapi.model.DailyWorkLog;

@Repository
public interface DailyWorkLogRepo extends JpaRepository<DailyWorkLog, Integer> {
	
	@Query(value = "SELECT t_daily_work_log.work_log_id,\n" + 
			"	t_daily_work_log.work_date,\n" + 
			"    t_daily_work_log.emp_id,\n" + 
			"    t_daily_work_log.task_id,\n" + 
			"    SUM(t_daily_work_log.work_hours) AS work_hours,\n" + 
			"    t_daily_work_log.work_remark,\n" + 
			"    t_daily_work_log.del_status,\n" + 
			"    t_daily_work_log.update_datetime,\n" + 
			"    t_daily_work_log.update_username,\n" + 
			"    t_daily_work_log.ex_int1,\n" + 
			"    t_daily_work_log.ex_int2,\n" + 
			"    m_emp.emp_name AS ex_var1,\n" + 
			"    t_tasks.task_text AS ex_var2\n" + 
			"FROM  t_daily_work_log, m_emp, t_tasks\n" + 
			"WHERE t_tasks.task_id=:taskId AND\n" + 
			"		t_daily_work_log.del_status=1 AND\n" + 
			"        t_daily_work_log.task_id=t_tasks.task_id AND\n" + 
			"        t_daily_work_log.emp_id=m_emp.emp_id\n" + 
			"        GROUP BY t_daily_work_log.emp_id", nativeQuery=true)
	List<DailyWorkLog> findByDelStatusAndTaskId(@Param("taskId") int taskId);

	DailyWorkLog findByWorkLogId(int logId);

	@Transactional
	@Modifying
	@Query(value="UPDATE t_daily_work_log SET del_status=0, update_username=:userId  WHERE work_log_id=:logId",nativeQuery=true)
	int deleteworkLogId(@Param("logId") int logId, @Param("userId") int userId);
	
}
