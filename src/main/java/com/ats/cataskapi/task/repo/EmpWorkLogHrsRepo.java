package com.ats.cataskapi.task.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.task.model.EmpWorkLogHrs;

public interface EmpWorkLogHrsRepo extends JpaRepository<EmpWorkLogHrs, Integer> {


	@Query(value="SELECT	\n" + 
			"		 t_daily_work_log.work_log_id,\n" + 
			"        t_daily_work_log.work_date,\n" + 
			"        t_daily_work_log.work_hours,\n" + 
			"        t_daily_work_log.work_remark,\n" + 
			"        t_daily_work_log.ex_var1,\n" + 
			"        t_daily_work_log.ex_var2,\n" + 
			"        t_daily_work_log.ex_int1,\n" + 
			"        t_daily_work_log.ex_int2,        \n" + 
			"        t_tasks.task_id,  \n" +
			"        t_tasks.task_text,  \n" + 
			"        m_activities.acti_name,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_emp.emp_name AS employees, \n" + 
			"        CASE                           \n" + 
			"            WHEN m_cust_header.cust_group_id=0 THEN m_cust_header.cust_firm_name      \n" + 
			"            ELSE COALESCE(( SELECT\n" + 
			"                m_cust_group.cust_group_name            \n" + 
			"            FROM\n" + 
			"                m_cust_group                           \n" + 
			"            WHERE\n" + 
			"                m_cust_group.cust_group_id=m_cust_header.cust_group_id            \n" + 
			"                AND m_cust_group.del_status=1 ),\n" + 
			"            0)                   \n" + 
			"        END AS cust_firm_name,\n" + 
			"        dm_fin_year.fin_year_name \n" + 
			"    FROM\n" + 
			"        m_services,\n" + 
			"        m_activities,       \n" + 
			"        m_cust_header,\n" + 
			"        t_tasks,\n" + 
			"        dm_fin_year,  \n" + 
			"        t_daily_work_log,\n" + 
			"        m_emp\n" + 
			"    WHERE\n" + 
			"    	t_daily_work_log.emp_id=m_emp.emp_id\n" + 
			"		AND t_daily_work_log.task_id=t_tasks.task_id \n" + 
			"    	AND t_daily_work_log.work_date BETWEEN :fromDate AND :toDate\n" + 
			"        AND t_tasks.cust_id = m_cust_header.cust_id          \n" + 
			"        AND t_tasks.task_fy_id = dm_fin_year.fin_year_id          \n" + 
			"        AND m_services.serv_id = t_tasks.serv_id          \n" + 
			"        AND m_activities.acti_id = t_tasks.actv_id               \n" + 
			"        AND t_tasks.task_status !=:stat        \n" + 
			"        AND t_tasks.del_status = 1           \n" + 
			"        AND t_tasks.is_active = 1 \n" + 
			"        AND t_daily_work_log.emp_id=:emp          \n" + 
			"    ORDER BY\n" + 
			"        t_daily_work_log.work_log_id DESC",nativeQuery=true)
	
	List<EmpWorkLogHrs> getDailyWorkLogList(@Param("stat") int stat, @Param("emp") int emp, @Param("fromDate") String fromDate, @Param("toDate") String toDate);
}
