package com.ats.cataskapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.BugetedAmtAndRevenue;

public interface BugetedAmtAndRevenueRepo extends JpaRepository<BugetedAmtAndRevenue, Integer>{

	@Query(value="select coalesce ((select sum(t.emp_bud_hr) from t_tasks t where t.del_status=1 and "
			+ "FIND_IN_SET(:empId,t.task_emp_ids) and t.task_statutory_due_date between :fromDate and "
			+ ":toDate and is_active=1),0) as bugeted_hrs, coalesce((select sum(work_hours) from "
			+ "t_daily_work_log where work_date between :fromDate and :toDate and "
			+ "emp_id=:empId and del_status=1),0) as actual_hrs, coalesce((select sum(m.actv_billing_amt) "
			+ "from m_cust_acti_map m,t_tasks t where t.del_status=1 and FIND_IN_SET(:empId,t.task_emp_ids) "
			+ "and t.task_statutory_due_date between :fromDate and :toDate and is_active=1 and "
			+ "t.mapping_id=m.mapping_id),0) as bugeted_rev, coalesce((select sum(m.actv_billing_amt) "
			+ "from m_cust_acti_map m,t_tasks t where t.del_status=1 and FIND_IN_SET(:empId,t.task_emp_ids) "
			+ "and t.task_statutory_due_date between :fromDate and :toDate and is_active=1 and "
			+ "t.mapping_id=m.mapping_id and t.task_status=9),0) as actul_rev",nativeQuery=true)
	BugetedAmtAndRevenue calculateBugetedAmtAndBugetedRevenue(@Param("empId") int empId,@Param("fromDate") String fromDate, 
			@Param("toDate") String toDate);

}
