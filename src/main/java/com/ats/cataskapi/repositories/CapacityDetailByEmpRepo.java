package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.CapacityDetailByEmp;

public interface CapacityDetailByEmpRepo extends JpaRepository<CapacityDetailByEmp, Integer> {

	@Query(value = "select GROUP_CONCAT(DISTINCT task_emp_ids)  from t_tasks where FIND_IN_SET(:empId,task_emp_ids) and is_active=1", nativeQuery = true)
	String getEmployeeList(@Param("empId") int empId);

	@Query(value = "select e.emp_id,e.emp_name,0 as bugeted_cap,coalesce((select CONCAT(FLOOR(sum(emp_bud_hr)/60),'.',MOD( sum(emp_bud_hr),60)) from t_tasks where task_end_date between :fromDate "
			+ "and :toDate and FIND_IN_SET(e.emp_id,task_emp_ids) and is_active=1 and del_status=1 ),0) as all_work,coalesce((select CONCAT(FLOOR(sum(wl.work_hours)/60),'.',MOD( sum(wl.work_hours),60)) from t_daily_work_log wl "
			+ "where wl.work_date between :fromDate and :toDate and wl.emp_id=e.emp_id),0) as act_work from m_emp e where e.del_status=1 and "
			+ "e.emp_id in (:empId)", nativeQuery = true)
	List<CapacityDetailByEmp> getEmployeeCapacityDetail(@Param("fromDate") String fromDate,
			@Param("toDate") String toDate, @Param("empId") String[] empId);

	@Query(value = "select GROUP_CONCAT(DISTINCT task_emp_ids)  from t_tasks where FIND_IN_SET(:empId,task_emp_ids) and FIND_IN_SET(:userId,task_emp_ids) and is_active=1", nativeQuery = true)
	String getEmployeeListByManagerIdAndUserId(@Param("empId")int empId, @Param("userId") int userId);

}
