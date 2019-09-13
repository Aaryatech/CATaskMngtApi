package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.EmpListForDashboardByStatus;

public interface EmpListForDashboardByStatusRepo extends JpaRepository<EmpListForDashboardByStatus, Integer>{

	@Query(value="select\n" + 
			"        e.emp_id,\n" + 
			"        e.emp_name,:status as status_value, \n" + 
			"        coalesce((select\n" + 
			"            count(*)          \n" + 
			"        from\n" + 
			"            t_tasks          \n" + 
			"        where\n" + 
			"            task_end_date<:date             \n" + 
			"            and task_status=:status             \n" + 
			"            and FIND_IN_SET(e.emp_id,task_emp_ids) and  FIND_IN_SET(:userId,task_emp_ids) \n" + 
			"            and del_status = 1 \n" + 
			"            and is_active=1),\n" + 
			"        0) as overdeu,\n" + 
			"        coalesce((select\n" + 
			"            count(*)          \n" + 
			"        from\n" + 
			"            t_tasks          \n" + 
			"        where\n" + 
			"            task_end_date=:date              \n" + 
			"            and task_status=:status            \n" + 
			"            and  FIND_IN_SET(e.emp_id,task_emp_ids) and  FIND_IN_SET(:userId,task_emp_ids) \n" + 
			"            and del_status = 1 \n" + 
			"            and is_active=1),\n" + 
			"        0) as duetoday,\n" + 
			"        coalesce ((select\n" + 
			"            count(*)          \n" + 
			"        from\n" + 
			"            t_tasks          \n" + 
			"        where\n" + 
			"            WEEKOFYEAR(task_end_date)=WEEKOFYEAR(:date)               \n" + 
			"            and YEAR(:date) = YEAR(task_end_date)              \n" + 
			"            and task_status=:status              \n" + 
			"            and  FIND_IN_SET(e.emp_id,task_emp_ids) and  FIND_IN_SET(:userId,task_emp_ids) \n" + 
			"            and del_status = 1 \n" + 
			"            and is_active=1),\n" + 
			"        0) as week  ,\n" + 
			"        coalesce ((select\n" + 
			"            count(*)          \n" + 
			"        from\n" + 
			"            t_tasks          \n" + 
			"        where\n" + 
			"            MONTH(task_end_date)=MONTH(:date)              \n" + 
			"            and YEAR(:date) = YEAR(task_end_date)              \n" + 
			"            and task_status=:status             \n" + 
			"            and  FIND_IN_SET(e.emp_id,task_emp_ids) and  FIND_IN_SET(:userId,task_emp_ids)    \n" + 
			"            and del_status = 1 \n" + 
			"            and is_active=1),\n" + 
			"        0) as month       \n" + 
			"    from\n" + 
			"        m_emp e      \n" + 
			"    where\n" + 
			"        e.del_status=1 and e.emp_id in (:ids)",nativeQuery=true)
	List<EmpListForDashboardByStatus> getTaskCountByStatus(@Param("date") String date,@Param("ids") String[] ids,@Param("status") int status
			,@Param("userId") int userId);

}
