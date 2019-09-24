package com.ats.cataskapi.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.CapacityDetailByEmp;

public interface CapacityDetailByEmpRepo extends JpaRepository<CapacityDetailByEmp, Integer> {

	@Query(value = "select GROUP_CONCAT(DISTINCT task_emp_ids)  from t_tasks where FIND_IN_SET(:empId,task_emp_ids) and is_active=1", nativeQuery = true)
	String getEmployeeList(@Param("empId") int empId);

	@Query(value = "select e.emp_id, e.emp_name, 0 as bugeted_cap,\n" + 
			"        case \n" + 
			"            when e.emp_type=3             then             coalesce((select\n" + 
			"                CONCAT(FLOOR(sum(mngr_bud_hr)/60),\n" + 
			"                '.',\n" + 
			"                MOD( sum(mngr_bud_hr),\n" + 
			"                60))          \n" + 
			"            from\n" + 
			"                t_tasks          \n" + 
			"            where\n" + 
			"                task_end_date between :fromDate and :toDate              \n" + 
			"                and FIND_IN_SET(e.emp_id,task_emp_ids)              \n" + 
			"                and is_active=1              \n" + 
			"                and del_status=1 ),\n" + 
			"            0)             \n" + 
			"            when e.emp_type=5             then             coalesce((select\n" + 
			"                CONCAT(FLOOR(sum(emp_bud_hr)/60),\n" + 
			"                '.',\n" + 
			"                MOD( sum(emp_bud_hr),\n" + 
			"                60))          \n" + 
			"            from\n" + 
			"                t_tasks          \n" + 
			"            where\n" + 
			"                task_end_date between :fromDate and :toDate              \n" + 
			"                and FIND_IN_SET(e.emp_id,task_emp_ids)              \n" + 
			"                and is_active=1              \n" + 
			"                and del_status=1 ),\n" + 
			"            0)\n" + 
			"            else \n" + 
			"            0\n" + 
			"        end as all_work ,\n" + 
			"        coalesce((select\n" + 
			"            CONCAT(FLOOR(sum(wl.work_hours)/60),\n" + 
			"            '.',\n" + 
			"            MOD( sum(wl.work_hours),\n" + 
			"            60))          \n" + 
			"        from\n" + 
			"            t_daily_work_log wl          \n" + 
			"        where\n" + 
			"            wl.work_date between :fromDate and :toDate              \n" + 
			"            and wl.emp_id=e.emp_id),\n" + 
			"        0) as act_work      \n" + 
			"    from\n" + 
			"        m_emp e      \n" + 
			"    where\n" + 
			"        e.del_status=1          \n" + 
			"        and e.emp_id in (:empId)", nativeQuery = true)
	List<CapacityDetailByEmp> getEmployeeCapacityDetail(@Param("fromDate") String fromDate,
			@Param("toDate") String toDate, @Param("empId") ArrayList<String> empId);

	@Query(value = "select GROUP_CONCAT(DISTINCT task_emp_ids)  from t_tasks where FIND_IN_SET(:empId,task_emp_ids) and FIND_IN_SET(:userId,task_emp_ids) and is_active=1", nativeQuery = true)
	String getEmployeeListByManagerIdAndUserId(@Param("empId")int empId, @Param("userId") int userId);
	
	 

	@Query(value = "select\n" + 
			"        GROUP_CONCAT(DISTINCT task_emp_ids)  \n" + 
			"    from\n" + 
			"        t_tasks \n" + 
			"    where\n" + 
			"        FIND_IN_SET(:empId,task_emp_ids) \n" + 
			"        and FIND_IN_SET(:userId,task_emp_ids) \n" + 
			"        and is_active=1 and task_end_date between :fromDate and :toDate ", nativeQuery = true)
	String getEmployeeListByManagerIdAndUserIdBetweenDate(@Param("empId")int empId, @Param("userId") int userId,
			@Param("fromDate")String fromDate,@Param("toDate") String toDate);

	@Query(value = "select e.emp_id, e.emp_name, 0 as bugeted_cap,0 as all_work ,0 as act_work from m_emp e where e.del_status=1 and e.emp_id in (:empId)", nativeQuery = true)
	List<CapacityDetailByEmp> getempIdOnly(@Param("empId") List<Integer> empId);

}
