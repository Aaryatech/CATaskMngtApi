package com.ats.cataskapi.report.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.reportv2.ComplTaskVarienceRep;
import com.ats.cataskapi.model.reportv2.WorkLofForReport;

public interface WorkLofForReportRepo extends JpaRepository<WorkLofForReport, String> {
	
	
	@Query(value="SELECT\n" + 
			"    UUID() as uuid, t_daily_work_log.emp_id, t_daily_work_log.work_date, SUM(t_daily_work_log.work_hours) as work_hr_min,\n" + 
			"            COALESCE( (FLOOR(SUM(t_daily_work_log.work_hours)/60          )               +                round(  MOD(             SUM(t_daily_work_log.work_hours),\n" + 
			"            60)*0.0166  ,\n" + 
			"            2)                ),\n" + 
			"            0) AS work_hr \n" + 
 			"FROM\n" + 
			"    t_daily_work_log,m_emp\n" + 
			"WHERE\n" + 
			"    t_daily_work_log.work_date BETWEEN :fromDate AND :toDate AND t_daily_work_log.del_status=1 AND m_emp.emp_type IN (3,5)  AND m_emp.emp_id=t_daily_work_log.emp_id\n" + 
			"GROUP BY\n" + 
			"    t_daily_work_log.work_date,\n" + 
			"    t_daily_work_log.emp_id",nativeQuery=true)
		List<WorkLofForReport> getworklog( @Param("fromDate") String fromDate, @Param("toDate") String toDate);


}
