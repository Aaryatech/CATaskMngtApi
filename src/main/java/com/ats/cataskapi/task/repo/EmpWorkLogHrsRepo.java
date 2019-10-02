package com.ats.cataskapi.task.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.task.model.EmpWorkLogHrs;

public interface EmpWorkLogHrsRepo extends JpaRepository<EmpWorkLogHrs, Integer> {

/********************Date Emp*************************/
	// Query 1
	
	@Query(value="SELECT UUID() as ex_var1,	\n" + 
			"		 t_daily_work_log.work_log_id,\n" + 
			"        t_daily_work_log.work_date,\n" + 
			"        CONCAT(FLOOR(t_daily_work_log.work_hours/60),':',LPAD(MOD(t_daily_work_log.work_hours,60), 2, '0')) as work_hours,\n" +    
			"        t_daily_work_log.work_remark,\n" + 
			"        t_daily_work_log.ex_var2,\n" + 
			"        t_daily_work_log.emp_id AS ex_int1,\n" + 
			"        t_daily_work_log.ex_int2,        \n" + 
			"        t_tasks.task_id,  \n" +
			"        t_tasks.task_text,  \n" + 
			"        m_activities.acti_name,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_emp.emp_name AS employees, \n" + 
			"        m_cust_header.cust_firm_name,\n" + 
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
	@Query(value="SELECT UUID() as ex_var1,\n" + 
			"        t_daily_work_log.work_log_id,\n" + 
			"        t_daily_work_log.work_date,\n" + 
			"        CONCAT(FLOOR(t_daily_work_log.work_hours/60),':',LPAD(MOD(t_daily_work_log.work_hours,60), 2, '0')) as work_hours,\n" +   
			"        t_daily_work_log.work_remark,\n" +
			"        t_daily_work_log.ex_var2,\n" + 
			"        t_daily_work_log.emp_id AS ex_int1,\n" +  
			"        t_daily_work_log.ex_int2,\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        m_activities.acti_name,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_emp.emp_name AS employees,\n" + 
			"        m_cust_header.cust_firm_name\n" + 
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
	

//	Query 3	
	@Query(value="SELECT UUID() as ex_var1,\n" + 
			"        t_daily_work_log.work_log_id,\n" + 
			"        t_daily_work_log.work_date,\n" + 
			"        CONCAT(FLOOR(t_daily_work_log.work_hours/60),':',LPAD(MOD(t_daily_work_log.work_hours,60), 2, '0')) as work_hours,\n" +    
			"        t_daily_work_log.work_remark,\n" + 
			"        t_daily_work_log.ex_var2,\n" + 
			"        t_daily_work_log.emp_id AS ex_int1,\n" +  
			"        t_daily_work_log.ex_int2,\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        m_activities.acti_name,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_emp.emp_name AS employees,\n" + 
			"        m_cust_header.cust_firm_name,\n" + 
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

	
	// Query 4
	
	@Query(value = "SELECT UUID() as ex_var1,\n" + 
			"        t_daily_work_log.work_log_id,\n" + 
			"        t_daily_work_log.work_date,\n" + 
			"        CONCAT(FLOOR(t_daily_work_log.work_hours/60),':',LPAD(MOD(t_daily_work_log.work_hours,60), 2, '0')) as work_hours,\n" +   
			"        t_daily_work_log.work_remark,\n" + 
			"        t_daily_work_log.ex_var2,\n" + 
			"        t_daily_work_log.emp_id AS ex_int1,\n" +  
			"        t_daily_work_log.ex_int2,\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        m_activities.acti_name,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_emp.emp_name AS employees,\n" + 
			"        m_cust_header.cust_firm_name,\n" + 
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
	

	//Query 5
	@Query(value="SELECT UUID() as ex_var1,\n" + 
			"        t_daily_work_log.work_log_id,\n" + 
			"        t_daily_work_log.work_date,\n" + 
			"        CONCAT(FLOOR(t_daily_work_log.work_hours/60),':',LPAD(MOD(t_daily_work_log.work_hours,60), 2, '0')) as work_hours,\n" +    
			"        t_daily_work_log.work_remark,\n" + 
			"        t_daily_work_log.ex_var2,\n" + 
			"        t_daily_work_log.emp_id AS ex_int1,\n" +  
			"        t_daily_work_log.ex_int2,\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        m_activities.acti_name,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_emp.emp_name AS employees,\n" + 
			"        m_cust_header.cust_firm_name,\n" + 
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

	
	//Query 6
	@Query(value="SELECT UUID() as ex_var1,\n" + 
			"        t_daily_work_log.work_log_id,\n" + 
			"        t_daily_work_log.work_date,\n" + 
			"        CONCAT(FLOOR(t_daily_work_log.work_hours/60),':',LPAD(MOD(t_daily_work_log.work_hours,60), 2, '0')) as work_hours,\n" +   
			"        t_daily_work_log.work_remark,\n" + 
			"        t_daily_work_log.ex_var2,\n" + 
			"        t_daily_work_log.emp_id AS ex_int1,\n" + 
			"        t_daily_work_log.ex_int2,\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        m_activities.acti_name,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_emp.emp_name AS employees,\n" + 
			"        m_cust_header.cust_firm_name,\n" + 
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


	
	/********************************Else Part***********************************/
	//Query1
	
	@Query(value="SELECT UUID() as ex_var1, \n" + 
			"        t_tasks.task_id,\n" +
			"        0 as work_log_id,\n" +
			"        '9999-09-09' as work_date,\n" + 
			"        '00:00' as work_hours,\n" + 
			"        '' as work_remark,\n" +
			"        t_tasks.ex_var2,\n" + 
			"        m_emp.emp_id AS ex_int1,\n" + 
			"        t_tasks.ex_int2,\n" + 
			"        t_tasks.task_text,\n" + 
			"        m_activities.acti_name,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_emp.emp_name AS employees,\n" + 
			"        m_cust_header.cust_firm_name,                \n" + 
			"        dm_fin_year.fin_year_name      \n" + 
			"    FROM\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        m_cust_header,\n" + 
			"        t_tasks,\n" + 
			"        dm_fin_year,\n" + 
			"        m_emp	 \n" + 
			"    WHERE  \n" + 
			"       	FIND_IN_SET(m_emp.emp_id, t_tasks.task_emp_ids)       \n" + 
			"        AND t_tasks.task_statutory_due_date BETWEEN :fromDate AND :toDate  \n" + 
			"        AND t_tasks.cust_id = m_cust_header.cust_id                   \n" + 
			"        AND t_tasks.task_fy_id = dm_fin_year.fin_year_id                   \n" + 
			"        AND m_services.serv_id = t_tasks.serv_id                   \n" + 
			"        AND m_activities.acti_id = t_tasks.actv_id                        \n" + 
			"        AND t_tasks.task_status !=:stat               \n" + 
			"        AND t_tasks.del_status = 1                    \n" + 
			"        AND t_tasks.is_active = 1   \n" + 
			"      	 AND m_emp.emp_id=:emp AND t_tasks.task_id NOT IN (SELECT t_daily_work_log.task_id FROM  t_daily_work_log)\n" + 
			"    ORDER BY\n" + 
			"        t_tasks.task_id DESC",nativeQuery=true)
	List<EmpWorkLogHrs> getTaskLogList(@Param("stat") int stat, @Param("emp") int emp, 
			@Param("fromDate") String fromDate, @Param("toDate") String toDate);

}

/*SELECT
t_tasks.task_id,
0 as work_log_id,
t_tasks.task_statutory_due_date as work_date,
'00:00' as work_hours,
'' as work_remark,
t_tasks.ex_var1,
t_tasks.ex_var2,
m_emp.emp_id AS ex_int1,
t_tasks.ex_int2,
t_tasks.task_text,
m_activities.acti_name,
m_services.serv_name,
m_emp.emp_name AS employees,
m_cust_header.cust_firm_name,
dm_fin_year.fin_year_name           
FROM
m_services,
m_activities,
m_cust_header,
t_tasks,
dm_fin_year,
m_emp          
WHERE
FIND_IN_SET(m_emp.emp_id, t_tasks.task_emp_ids)                
AND t_tasks.task_statutory_due_date BETWEEN '2019-10-01' AND '2019-10-14'
AND t_tasks.cust_id = m_cust_header.cust_id                            
AND t_tasks.task_fy_id = dm_fin_year.fin_year_id                            
AND m_services.serv_id = t_tasks.serv_id                            
AND m_activities.acti_id = t_tasks.actv_id                                 
AND t_tasks.task_status !=9                       
AND t_tasks.del_status = 1                             
AND t_tasks.is_active = 1            
AND m_emp.emp_id=64    
ORDER BY
t_tasks.task_id DESC*/
