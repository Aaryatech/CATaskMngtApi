package com.ats.cataskapi.task.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.task.model.EmpWorkLogHrs;

public interface EmpWorkLogHrsRepo extends JpaRepository<EmpWorkLogHrs, Integer> {

/********************Date Emp*************************/
	// Query 1
	
	@Query(value="SELECT	\n" + 
			"		 t_daily_work_log.work_log_id,\n" + 
			"        t_daily_work_log.work_date,\n" + 
			"        CONCAT(FLOOR(t_daily_work_log.work_hours/60),':',LPAD(MOD(t_daily_work_log.work_hours,60), 2, '0')) as work_hours,\n" +    
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
			"        AND t_daily_work_log.del_status = 1           \n" + 
			"        AND t_tasks.is_active = 1 \n" + 
			"        AND t_daily_work_log.emp_id=:emp          \n" + 
			"    ORDER BY\n" + 
			"        t_daily_work_log.work_log_id DESC",nativeQuery=true)
	
	List<EmpWorkLogHrs> getDailyWorkLogList(@Param("stat") int stat, @Param("emp") int emp, @Param("fromDate") String fromDate, @Param("toDate") String toDate);

	/***********************Date Emp Customer*******************************/
//	Query 2
	@Query(value="SELECT\n" + 
			"        t_daily_work_log.work_log_id,\n" + 
			"        t_daily_work_log.work_date,\n" + 
			"        CONCAT(FLOOR(t_daily_work_log.work_hours/60),':',LPAD(MOD(t_daily_work_log.work_hours,60), 2, '0')) as work_hours,\n" +   
			"        t_daily_work_log.work_remark,\n" + 
			"        t_daily_work_log.ex_var1,\n" + 
			"        t_daily_work_log.ex_var2,\n" + 
			"        t_daily_work_log.ex_int1,\n" + 
			"        t_daily_work_log.ex_int2,\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        m_activities.acti_name,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_emp.emp_name AS employees,\n" + 
			"        CASE                                        \n" + 
			"            WHEN m_cust_header.cust_group_id=0 THEN m_cust_header.cust_firm_name                   \n" + 
			"            ELSE COALESCE(( SELECT\n" + 
			"                m_cust_group.cust_group_name                         \n" + 
			"            FROM\n" + 
			"                m_cust_group                                        \n" + 
			"            WHERE\n" + 
			"                m_cust_group.cust_group_id=m_cust_header.cust_group_id                             \n" + 
			"                AND m_cust_group.del_status=1 ),\n" + 
			"            0)                            \n" + 
			"        END AS cust_firm_name,\n" + 
			"        dm_fin_year.fin_year_name      \n" + 
			"    FROM\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        m_cust_header,\n" + 
			"        t_tasks,\n" + 
			"        dm_fin_year,\n" + 
			"        t_daily_work_log,\n" + 
			"        m_emp     \n" + 
			"    WHERE\n" + 
			"        t_daily_work_log.emp_id=m_emp.emp_id   \n" + 
			"        AND t_daily_work_log.task_id=t_tasks.task_id       \n" + 
			"        AND t_daily_work_log.work_date BETWEEN :fromDate AND :toDate        \n" + 
			"        AND t_tasks.cust_id = m_cust_header.cust_id                   \n" + 
			"        AND t_tasks.task_fy_id = dm_fin_year.fin_year_id                   \n" + 
			"        AND m_services.serv_id = t_tasks.serv_id                   \n" + 
			"        AND m_activities.acti_id = t_tasks.actv_id                        \n" + 
			"        AND t_tasks.task_status !=:stat                 \n" + 
			"        AND t_daily_work_log.del_status = 1                    \n" + 
			"        AND t_tasks.is_active = 1          \n" + 
			"        AND t_daily_work_log.emp_id = :emp   \n" + 
			"        AND t_tasks.cust_id = :customer\n" + 
			"    ORDER BY\n" + 
			"        t_daily_work_log.work_log_id DESC", nativeQuery=true)
	List<EmpWorkLogHrs> getDailyWorkLogListByCust(@Param("stat") int stat, @Param("emp") int emp, @Param("customer") int customer, 
			@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	/************Date Emp Customer Service Activity*************/
	
	// Query 4
	
	@Query(value = "SELECT\n" + 
			"        t_daily_work_log.work_log_id,\n" + 
			"        t_daily_work_log.work_date,\n" + 
			"        CONCAT(FLOOR(t_daily_work_log.work_hours/60),':',LPAD(MOD(t_daily_work_log.work_hours,60), 2, '0')) as work_hours,\n" +   
			"        t_daily_work_log.work_remark,\n" + 
			"        t_daily_work_log.ex_var1,\n" + 
			"        t_daily_work_log.ex_var2,\n" + 
			"        t_daily_work_log.ex_int1,\n" + 
			"        t_daily_work_log.ex_int2,\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        m_activities.acti_name,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_emp.emp_name AS employees,\n" + 
			"        CASE                                        \n" + 
			"            WHEN m_cust_header.cust_group_id=0 THEN m_cust_header.cust_firm_name                   \n" + 
			"            ELSE COALESCE(( SELECT\n" + 
			"                m_cust_group.cust_group_name                         \n" + 
			"            FROM\n" + 
			"                m_cust_group                                        \n" + 
			"            WHERE\n" + 
			"                m_cust_group.cust_group_id=m_cust_header.cust_group_id                             \n" + 
			"                AND m_cust_group.del_status=1 ),\n" + 
			"            0)                            \n" + 
			"        END AS cust_firm_name,\n" + 
			"        dm_fin_year.fin_year_name      \n" + 
			"    FROM\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        m_cust_header,\n" + 
			"        t_tasks,\n" + 
			"        dm_fin_year,\n" + 
			"        t_daily_work_log,\n" + 
			"        m_emp     \n" + 
			"    WHERE\n" + 
			"        t_daily_work_log.emp_id=m_emp.emp_id   \n" + 
			"        AND t_daily_work_log.task_id=t_tasks.task_id       \n" + 
			"        AND t_daily_work_log.work_date BETWEEN :fromDate AND :toDate        \n" + 
			"        AND t_tasks.cust_id = m_cust_header.cust_id                   \n" + 
			"        AND t_tasks.task_fy_id = dm_fin_year.fin_year_id                   \n" + 
			"        AND m_services.serv_id = t_tasks.serv_id                   \n" + 
			"        AND m_activities.acti_id = t_tasks.actv_id                        \n" + 
			"        AND t_tasks.task_status !=:stat                 \n" + 
			"        AND t_daily_work_log.del_status = 1                    \n" + 
			"        AND t_tasks.is_active = 1          \n" + 
			"        AND t_daily_work_log.emp_id = :emp   \n" + 
			"        AND t_tasks.cust_id = :customer\n" + 
			"        AND m_services.serv_id = :service\n" + 
			"        AND m_activities.acti_id = :activity\n" + 
			"    ORDER BY\n" + 
			"        t_daily_work_log.work_log_id DESC",nativeQuery=true)
	
	List<EmpWorkLogHrs> getDailyWorkLogListByCustSerAct(@Param("stat") int stat, @Param("emp") int emp, @Param("customer") int customer, 
			@Param("fromDate") String fromDate, @Param("toDate") String toDate
			, @Param("service") int service, @Param("activity") int activity);
	
		/*************************Date Emp Service Activity*************************/
//	Query 3
	
	@Query(value="SELECT\n" + 
			"        t_daily_work_log.work_log_id,\n" + 
			"        t_daily_work_log.work_date,\n" + 
			"        CONCAT(FLOOR(t_daily_work_log.work_hours/60),':',LPAD(MOD(t_daily_work_log.work_hours,60), 2, '0')) as work_hours,\n" +    
			"        t_daily_work_log.work_remark,\n" + 
			"        t_daily_work_log.ex_var1,\n" + 
			"        t_daily_work_log.ex_var2,\n" + 
			"        t_daily_work_log.ex_int1,\n" + 
			"        t_daily_work_log.ex_int2,\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        m_activities.acti_name,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_emp.emp_name AS employees,\n" + 
			"        CASE                                        \n" + 
			"            WHEN m_cust_header.cust_group_id=0 THEN m_cust_header.cust_firm_name                   \n" + 
			"            ELSE COALESCE(( SELECT\n" + 
			"                m_cust_group.cust_group_name                         \n" + 
			"            FROM\n" + 
			"                m_cust_group                                        \n" + 
			"            WHERE\n" + 
			"                m_cust_group.cust_group_id=m_cust_header.cust_group_id                             \n" + 
			"                AND m_cust_group.del_status=1 ),\n" + 
			"            0)                            \n" + 
			"        END AS cust_firm_name,\n" + 
			"        dm_fin_year.fin_year_name      \n" + 
			"    FROM\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        m_cust_header,\n" + 
			"        t_tasks,\n" + 
			"        dm_fin_year,\n" + 
			"        t_daily_work_log,\n" + 
			"        m_emp     \n" + 
			"    WHERE\n" + 
			"        t_daily_work_log.emp_id=m_emp.emp_id   \n" + 
			"        AND t_daily_work_log.task_id=t_tasks.task_id       \n" + 
			"        AND t_daily_work_log.work_date BETWEEN :fromDate AND :toDate        \n" + 
			"        AND t_tasks.cust_id = m_cust_header.cust_id                   \n" + 
			"        AND t_tasks.task_fy_id = dm_fin_year.fin_year_id                   \n" + 
			"        AND m_services.serv_id = t_tasks.serv_id                   \n" + 
			"        AND m_activities.acti_id = t_tasks.actv_id                        \n" + 
			"        AND t_tasks.task_status !=:stat                \n" + 
			"        AND t_daily_work_log.del_status = 1                    \n" + 
			"        AND t_tasks.is_active = 1          \n" + 
			"        AND t_daily_work_log.emp_id=:emp         \n" + 
			"        AND m_services.serv_id = :service\n" + 
			"        AND m_activities.acti_id = :activity\n" + 
			"    ORDER BY\n" + 
			"        t_daily_work_log.work_log_id DESC",nativeQuery=true)
	List<EmpWorkLogHrs> getDailyWorkLogListBySerAct(@Param("stat") int stat, @Param("emp") int emp,  
			@Param("fromDate") String fromDate, @Param("toDate") String toDate
			, @Param("service") int service, @Param("activity") int activity);

	@Query(value="SELECT\n" + 
			"        t_daily_work_log.work_log_id,\n" + 
			"        t_daily_work_log.work_date,\n" + 
			"        CONCAT(FLOOR(t_daily_work_log.work_hours/60),':',LPAD(MOD(t_daily_work_log.work_hours,60), 2, '0')) as work_hours,\n" +    
			"        t_daily_work_log.work_remark,\n" + 
			"        t_daily_work_log.ex_var1,\n" + 
			"        t_daily_work_log.ex_var2,\n" + 
			"        t_daily_work_log.ex_int1,\n" + 
			"        t_daily_work_log.ex_int2,\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        m_activities.acti_name,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_emp.emp_name AS employees,\n" + 
			"        CASE                                        \n" + 
			"            WHEN m_cust_header.cust_group_id=0 THEN m_cust_header.cust_firm_name                   \n" + 
			"            ELSE COALESCE(( SELECT\n" + 
			"                m_cust_group.cust_group_name                         \n" + 
			"            FROM\n" + 
			"                m_cust_group                                        \n" + 
			"            WHERE\n" + 
			"                m_cust_group.cust_group_id=m_cust_header.cust_group_id                             \n" + 
			"                AND m_cust_group.del_status=1 ),\n" + 
			"            0)                            \n" + 
			"        END AS cust_firm_name,\n" + 
			"        dm_fin_year.fin_year_name      \n" + 
			"    FROM\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        m_cust_header,\n" + 
			"        t_tasks,\n" + 
			"        dm_fin_year,\n" + 
			"        t_daily_work_log,\n" + 
			"        m_emp     \n" + 
			"    WHERE\n" + 
			"        t_daily_work_log.emp_id=m_emp.emp_id   \n" + 
			"        AND t_daily_work_log.task_id=t_tasks.task_id       \n" + 
			"        AND t_daily_work_log.work_date BETWEEN :fromDate AND :toDate         \n" + 
			"        AND t_tasks.cust_id = m_cust_header.cust_id                   \n" + 
			"        AND t_tasks.task_fy_id = dm_fin_year.fin_year_id                   \n" + 
			"        AND m_services.serv_id = t_tasks.serv_id                   \n" + 
			"        AND m_activities.acti_id = t_tasks.actv_id                        \n" + 
			"        AND t_tasks.task_status !=:stat                 \n" + 
			"        AND t_daily_work_log.del_status = 1                    \n" + 
			"        AND t_tasks.is_active = 1                      \n" + 
			"    ORDER BY\n" + 
			"        t_daily_work_log.work_log_id DESC",nativeQuery=true)
	List<EmpWorkLogHrs> getDailyWorkLogList(@Param("stat") int stat, @Param("fromDate") String fromDate, @Param("toDate") String toDate);

	@Query(value="SELECT\n" + 
			"        t_daily_work_log.work_log_id,\n" + 
			"        t_daily_work_log.work_date,\n" + 
			"        CONCAT(FLOOR(t_daily_work_log.work_hours/60),':',LPAD(MOD(t_daily_work_log.work_hours,60), 2, '0')) as work_hours,\n" +   
			"        t_daily_work_log.work_remark,\n" + 
			"        t_daily_work_log.ex_var1,\n" + 
			"        t_daily_work_log.ex_var2,\n" + 
			"        t_daily_work_log.ex_int1,\n" + 
			"        t_daily_work_log.ex_int2,\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        m_activities.acti_name,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_emp.emp_name AS employees,\n" + 
			"        CASE                                                     \n" + 
			"            WHEN m_cust_header.cust_group_id=0 THEN m_cust_header.cust_firm_name                                \n" + 
			"            ELSE COALESCE(( SELECT\n" + 
			"                m_cust_group.cust_group_name                                      \n" + 
			"            FROM\n" + 
			"                m_cust_group                                                     \n" + 
			"            WHERE\n" + 
			"                m_cust_group.cust_group_id=m_cust_header.cust_group_id                                              \n" + 
			"                AND m_cust_group.del_status=1 ),\n" + 
			"            0)                                     \n" + 
			"        END AS cust_firm_name,\n" + 
			"        dm_fin_year.fin_year_name           \n" + 
			"    FROM\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        m_cust_header,\n" + 
			"        t_tasks,\n" + 
			"        dm_fin_year,\n" + 
			"        t_daily_work_log,\n" + 
			"        m_emp          \n" + 
			"    WHERE\n" + 
			"        t_daily_work_log.emp_id=m_emp.emp_id            \n" + 
			"        AND t_daily_work_log.task_id=t_tasks.task_id                \n" + 
			"        AND t_daily_work_log.work_date BETWEEN :fromDate AND :toDate                 \n" + 
			"        AND t_tasks.cust_id = m_cust_header.cust_id                            \n" + 
			"        AND t_tasks.task_fy_id = dm_fin_year.fin_year_id                            \n" + 
			"        AND m_services.serv_id = t_tasks.serv_id                            \n" + 
			"        AND m_activities.acti_id = t_tasks.actv_id                                 \n" + 
			"        AND t_tasks.task_status !=:stat                         \n" + 
			"        AND t_daily_work_log.del_status = 1                             \n" + 
			"        AND t_tasks.is_active = 1   \n" + 
			"        AND t_tasks.cust_id = :customer\n" + 
			"    ORDER BY\n" + 
			"        t_daily_work_log.work_log_id DESC",nativeQuery=true)
	List<EmpWorkLogHrs> getDailyWorkLogList(@Param("stat") int stat, @Param("fromDate") String fromDate, @Param("toDate") String toDate,  @Param("customer") int customer);
}
