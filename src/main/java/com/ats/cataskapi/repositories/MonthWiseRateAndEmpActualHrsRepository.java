package com.ats.cataskapi.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.MonthWiseRateAndEmpActualHrs;

public interface MonthWiseRateAndEmpActualHrsRepository extends JpaRepository<MonthWiseRateAndEmpActualHrs, Integer>{

	/*@Query(value=" select\n" + 
			"        UUID() as id,\n" + 
			"        e.emp_id,\n" + 
			"        e.emp_name,\n" + 
			"        wl.task_id,\n" + 
			"        sum(wl.work_hours) as worked_min,\n" + 
			"        CASE \n" + 
			"            WHEN  month(wl.work_date)=1 THEN es.Jan           \n" + 
			"            WHEN  month(wl.work_date)=2 then es.Feb          \n" + 
			"            WHEN  month(wl.work_date)=3 then es.Mar           \n" + 
			"            WHEN  month(wl.work_date)=4 THEN es.Apr           \n" + 
			"            WHEN  month(wl.work_date)=5 THEN es.May           \n" + 
			"            WHEN  month(wl.work_date)=6 THEN es.Jun           \n" + 
			"            WHEN  month(wl.work_date)=7 THEN es.Jul           \n" + 
			"            WHEN  month(wl.work_date)=8 THEN es.Aug           \n" + 
			"            WHEN  month(wl.work_date)=9 THEN es.Sep           \n" + 
			"            WHEN  month(wl.work_date)=10 THEN es.Oct           \n" + 
			"            WHEN  month(wl.work_date)=11 THEN es.Nov           \n" + 
			"            WHEN  month(wl.work_date)=12 THEN es.Dece       \n" + 
			"        END as sal,\n" + 
			"        e.emp_type     \n" + 
			"    from\n" + 
			"        m_emp e,\n" + 
			"        t_daily_work_log wl,\n" + 
			"        t_emp_salary es        \n" + 
			"    where\n" + 
			"        e.del_status=1                            \n" + 
			"        and e.emp_id in (:empIds) \n" + 
			"        and  wl.emp_id=e.emp_id \n" + 
			"        and es.emp_id=e.emp_id \n" + 
			"        and es.fin_year_id=:yearId \n" + 
			"        and wl.task_id in (\n" + 
			"            select\n" + 
			"                task_id \n" + 
			"            from\n" + 
			"                t_tasks \n" + 
			"            where\n" + 
			"                task_end_date BETWEEN :fromDate AND :toDate\n" + 
			"                and t_tasks.cust_id in (:custId) \n" + 
			"                and t_tasks.del_status = 1 \n" + 
			"                AND t_tasks.is_active = 1\n" + 
			"        )       \n" + 
			"    group by\n" + 
			"        e.emp_id,\n" + 
			"        month(wl.work_date),\n" + 
			"        wl.task_id",nativeQuery=true)*/
	
	@Query(value="select \n" + 
			"a.id,a.emp_id,a.emp_name,a.task_id,a.sal,a.worked_min,a.month,a.emp_type,b.total_min_month\n" + 
			"from\n" + 
			" ( select\n" + 
			"        UUID() as id,e.emp_id,\n" + 
			"        e.emp_name,\n" + 
			"        wl.task_id,\n" + 
			"        sum(wl.work_hours) as worked_min,  \n" + 
			"    CASE WHEN  month(wl.work_date)=1 THEN es.Jan \n" + 
			"         WHEN  month(wl.work_date)=2 then es.Feb\n" + 
			"         WHEN  month(wl.work_date)=3 then es.Mar \n" + 
			"         WHEN  month(wl.work_date)=4 THEN es.Apr \n" + 
			"         WHEN  month(wl.work_date)=5 THEN es.May \n" + 
			"         WHEN  month(wl.work_date)=6 THEN es.Jun \n" + 
			"         WHEN  month(wl.work_date)=7 THEN es.Jul \n" + 
			"         WHEN  month(wl.work_date)=8 THEN es.Aug \n" + 
			"         WHEN  month(wl.work_date)=9 THEN es.Sep \n" + 
			"         WHEN  month(wl.work_date)=10 THEN es.Oct \n" + 
			"         WHEN  month(wl.work_date)=11 THEN es.Nov \n" + 
			"         WHEN  month(wl.work_date)=12 THEN es.Dece \n" + 
			"     END as sal,\n" + 
			"      month(wl.work_date) as month,\n" + 
			"     e.emp_type \n" + 
			"    from\n" + 
			"        m_emp e,t_daily_work_log wl,t_emp_salary es   \n" + 
			"    where\n" + 
			"        e.del_status=1                   \n" + 
			"        and e.emp_id in (:empIds) and  wl.emp_id=e.emp_id and es.emp_id=e.emp_id and es.fin_year_id=:yearId and wl.task_id in (select task_id from t_tasks where task_end_date BETWEEN :fromDate AND :toDate and t_tasks.cust_id in (:custId) and t_tasks.del_status = 1 AND t_tasks.is_active = 1)  \n" + 
			"    group by e.emp_id,month(wl.work_date),wl.task_id) a\n" + 
			" LEFT JOIN\n" + 
			" \n" + 
			"( select\n" + 
			"        e.emp_id,\n" + 
			"        sum(wl.work_hours) as total_min_month,\n" + 
			"        month(wl.work_date) as month\n" + 
			"  \n" + 
			"    from\n" + 
			"        m_emp e,t_daily_work_log wl \n" + 
			"    where\n" + 
			"        e.del_status=1                   \n" + 
			"        and e.emp_id in (:empIds) and  wl.emp_id=e.emp_id   \n" + 
			"    group by e.emp_id,month(wl.work_date)) b ON a.emp_id=b.emp_id and a.month=b.month",nativeQuery=true)
	List<MonthWiseRateAndEmpActualHrs> gethrsandsal(@Param("fromDate")String fromDate,@Param("toDate") String toDate,
			@Param("custId") int custId,@Param("yearId") int yearId,@Param("empIds") ArrayList<String> arryids);

}
