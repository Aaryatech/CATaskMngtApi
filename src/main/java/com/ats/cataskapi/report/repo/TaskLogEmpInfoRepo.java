package com.ats.cataskapi.report.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.report.TaskLogEmpInfo;

public interface TaskLogEmpInfoRepo extends JpaRepository<TaskLogEmpInfo, Integer> {
	
	
	@Query(value=" SELECT " + 
			"                            t_tasks.task_id, " + 
			"                            t_tasks.task_text, " + 
			"                            t_tasks.task_statutory_due_date, " + 
			"                            t_tasks.task_completion_date, " + 
			"                            t_tasks.billing_amt, " + 
			"                                                     m_emp.emp_id, " + 
			"                            m_emp.emp_type, " + 
			"                            m_emp.emp_name , " + 
			"                             t_daily_work_log.work_log_id, " + 
			"                                CONCAT(             FLOOR(                 SUM(t_daily_work_log.work_hours) / 60             ),\n" + 
			"                                '.',\n" + 
			"                                LPAD( MOD(                 SUM(t_daily_work_log.work_hours),\n" + 
			"                                60             ),\n" + 
			"                                2,\n" + 
			"                                '0')) as work_hrs,  \n" + 
			"                                \n" + 
			"                                CONCAT( FLOOR( t_tasks.emp_bud_hr   / 60 ),\n" + 
			"			            '.',\n" + 
			"			           LPAD(MOD( t_tasks.emp_bud_hr ,  60 ), 2, '0') \n" + 
			"			       ) as emp_bud_hr,\n" + 
			"                   \n" + 
			"                   CONCAT( FLOOR( t_tasks.mngr_bud_hr   / 60 ),\n" + 
			"			            '.',\n" + 
			"			           LPAD(MOD( t_tasks.mngr_bud_hr ,  60 ), 2, '0') \n" + 
			"			       ) as mngr_bud_hr\n" + 
			"                             FROM\n" + 
			"                            m_emp,\n" + 
			"                            t_tasks ,t_daily_work_log\n" + 
			"                            WHERE m_emp.emp_id=t_daily_work_log.emp_id and t_daily_work_log.task_id=t_tasks.task_id and t_tasks.task_id=:taskId and t_daily_work_log.del_status=1 and m_emp.del_status=1 and t_tasks.del_status=1\n" + 
			"                            group by t_tasks.task_id,m_emp.emp_id\n" + 
			"                             ",nativeQuery=true)
	
	List<TaskLogEmpInfo> getTaskLogEmpInfoListByTaskId(@Param("taskId") int taskId);
	

}
