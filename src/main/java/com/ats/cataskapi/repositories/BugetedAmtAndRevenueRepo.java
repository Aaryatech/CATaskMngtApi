package com.ats.cataskapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.BugetedAmtAndRevenue;

public interface BugetedAmtAndRevenueRepo extends JpaRepository<BugetedAmtAndRevenue, Integer>{

	/*@Query(value="select coalesce ((select sum(t.emp_bud_hr) from t_tasks t where t.del_status=1 and "
			+ "FIND_IN_SET(:empId,t.task_emp_ids) and t.task_statutory_due_date between :fromDate and "
			+ ":toDate and is_active=1),0) as bugeted_hrs, coalesce((select sum(work_hours) from "
			+ "t_daily_work_log where work_date between :fromDate and :toDate and "
			+ "emp_id=:empId and del_status=1),0) as actual_hrs, coalesce((select sum(m.actv_billing_amt) "
			+ "from m_cust_acti_map m,t_tasks t where t.del_status=1 and FIND_IN_SET(:empId,t.task_emp_ids) "
			+ "and t.task_statutory_due_date between :fromDate and :toDate and is_active=1 and "
			+ "t.mapping_id=m.mapping_id),0) as bugeted_rev, coalesce((select sum(m.actv_billing_amt) "
			+ "from m_cust_acti_map m,t_tasks t where t.del_status=1 and FIND_IN_SET(:empId,t.task_emp_ids) "
			+ "and t.task_statutory_due_date between :fromDate and :toDate and is_active=1 and "
			+ "t.mapping_id=m.mapping_id and t.task_status=9),0) as actul_rev",nativeQuery=true)*/
	
	@Query(value="select\n" + 
			"        case \n" + 
			"            when e.emp_type=3             then             coalesce((select\n" + 
			"                sum(mngr_bud_hr)          \n" + 
			"            from\n" + 
			"                t_tasks          \n" + 
			"            where\n" + 
			"                task_end_date between :fromDate and :toDate              \n" + 
			"                and FIND_IN_SET(e.emp_id,task_emp_ids)              \n" + 
			"                and is_active=1              \n" + 
			"                and del_status=1 ),\n" + 
			"            0)             \n" + 
			"            when e.emp_type=5       then      coalesce((select\n" + 
			"                sum(emp_bud_hr)         \n" + 
			"            from\n" + 
			"                t_tasks          \n" + 
			"            where\n" + 
			"                task_end_date between :fromDate and :toDate              \n" + 
			"                and FIND_IN_SET(e.emp_id,task_emp_ids)              \n" + 
			"                and is_active=1              \n" + 
			"                and del_status=1 ),\n" + 
			"            0)\n" + 
			"            else\n" + 
			"            0\n" + 
			"        end as bugeted_hrs ,\n" + 
			"        coalesce((select\n" + 
			"            sum(work_hours)          \n" + 
			"        from\n" + 
			"            t_daily_work_log          \n" + 
			"        where\n" + 
			"            work_date between :fromDate and :toDate              \n" + 
			"            and emp_id=e.emp_id             \n" + 
			"            and del_status=1),\n" + 
			"        0) as actual_hrs,\n" + 
			"        coalesce((select\n" + 
			"            sum(m.actv_billing_amt)          \n" + 
			"        from\n" + 
			"            m_cust_acti_map m,\n" + 
			"            t_tasks t          \n" + 
			"        where\n" + 
			"            t.del_status=1              \n" + 
			"            and FIND_IN_SET(e.emp_id,t.task_emp_ids)              \n" + 
			"            and t.task_end_date between :fromDate and :toDate              \n" + 
			"            and is_active=1              \n" + 
			"            and t.mapping_id=m.mapping_id),\n" + 
			"        0) as bugeted_rev,\n" + 
			"        coalesce((select\n" + 
			"            sum(m.actv_billing_amt)          \n" + 
			"        from\n" + 
			"            m_cust_acti_map m,\n" + 
			"            t_tasks t          \n" + 
			"        where\n" + 
			"            t.del_status=1              \n" + 
			"            and FIND_IN_SET(e.emp_id,t.task_emp_ids)              \n" + 
			"            and t.task_end_date between :fromDate and :toDate             \n" + 
			"            and is_active=1              \n" + 
			"            and t.mapping_id=m.mapping_id              \n" + 
			"            and t.task_status=9),\n" + 
			"        0) as actul_rev     \n" + 
			"    from\n" + 
			"        m_emp e \n" + 
			"    where\n" + 
			"        e.emp_id =:empId",nativeQuery=true)
	BugetedAmtAndRevenue calculateBugetedAmtAndBugetedRevenue(@Param("empId") int empId,@Param("fromDate") String fromDate, 
			@Param("toDate") String toDate);

}
