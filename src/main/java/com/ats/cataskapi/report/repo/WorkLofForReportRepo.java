package com.ats.cataskapi.report.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.reportv2.ComplTaskVarienceRep;
import com.ats.cataskapi.model.reportv2.WorkLofForReport;

public interface WorkLofForReportRepo extends JpaRepository<WorkLofForReport, String> {
	
	
	@Query(value="SELECT\n" + 
			"    UUID() as uuid, emp_id, work_date, SUM(t_daily_work_log.work_hours) as work_hr_min,\n" + 
			"    CONCAT(\n" + 
			"        FLOOR(SUM(work_hours) / 60),\n" + 
			"        '.',\n" + 
			"        LPAD(\n" + 
			"            MOD(SUM(work_hours),\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0'\n" + 
			"        )\n" + 
			"    ) as work_hr\n" + 
			"FROM\n" + 
			"    t_daily_work_log\n" + 
			"WHERE\n" + 
			"    work_date BETWEEN :fromDate AND :toDate AND del_status=1 \n" + 
			"GROUP BY\n" + 
			"    work_date,\n" + 
			"    emp_id",nativeQuery=true)
		List<WorkLofForReport> getworklog( @Param("fromDate") String fromDate, @Param("toDate") String toDate);


}
