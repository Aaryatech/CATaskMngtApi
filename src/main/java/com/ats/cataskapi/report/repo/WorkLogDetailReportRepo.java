package com.ats.cataskapi.report.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.reportv2.WorkLogDetailReport;

public interface WorkLogDetailReportRepo extends JpaRepository<WorkLogDetailReport, Integer> {
	@Query(value=" "
			+ ""
			+ " SELECT t_daily_work_log.work_log_id, t_daily_work_log.work_date,"
			+ " t_daily_work_log.emp_id,t_daily_work_log.task_id,"
			+ " t_daily_work_log.work_hours as work_min, "
			+ " t_daily_work_log.work_remark,t_daily_work_log.update_datetime, "
			+ " COALESCE( (CONCAT( FLOOR( t_daily_work_log.work_hours / 60 ), '.', LPAD( MOD( t_daily_work_log.work_hours, 60),2,'0'))), 0)"
			+ " AS work_hours FROM t_daily_work_log "
			+ " WHERE t_daily_work_log.emp_id=:empId and t_daily_work_log.task_id=:taskId " + 
			"" + 
			"                             ",nativeQuery=true)
	List<WorkLogDetailReport> getDetailWorkLogByEmpTaskId(@Param("taskId") int taskId,@Param("empId") int empId);
	

}
