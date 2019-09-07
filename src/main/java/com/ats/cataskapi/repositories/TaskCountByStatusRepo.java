package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.TaskCountByStatus;

public interface TaskCountByStatusRepo extends JpaRepository<TaskCountByStatus, Integer>{

	@Query(value="select s.status_mst_id,s.status_text,s.status_value, coalesce((select count(*) from t_tasks where task_end_date<:date "
			+ "and s.status_value=task_status and FIND_IN_SET(:empId,task_emp_ids)),0) as overdeu, coalesce((select count(*) from t_tasks where "
			+ "task_end_date=:date and s.status_value=task_status and  FIND_IN_SET(:empId,task_emp_ids)),0) as duetoday, coalesce ((select count(*) "
			+ "from t_tasks where WEEKOFYEAR(task_end_date)=WEEKOFYEAR(:date)  and YEAR(:date) = "
			+ "YEAR(task_end_date) and s.status_value=task_status and  FIND_IN_SET(:empId,task_emp_ids) ),0) as week  , coalesce ((select count(*) from t_tasks "
			+ "where MONTH(task_end_date)=MONTH(:date) and YEAR(:date) = YEAR(task_end_date) and s.status_value=task_status and  "
			+ "FIND_IN_SET(:empId,task_emp_ids)),0) as month from dm_status_mst s where del_status=1",nativeQuery=true)
	List<TaskCountByStatus> getTaskCountByStatus(@Param("date") String date, @Param("empId") int empId);

}
