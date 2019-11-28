package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.TaskCountByStatus;

public interface TaskCountByStatusRepo extends JpaRepository<TaskCountByStatus, Integer>{

	@Query(value="select\n" + 
			"        UUID() as status_mst_id,\n" + 
			"        s.status_text,\n" + 
			"        s.status_value,\n" + 
			"        coalesce((select\n" + 
			"            count(*) \n" + 
			"        from\n" + 
			"            t_tasks \n" + 
			"        where\n" + 
			"            task_end_date<:date \n" + 
			"            and s.status_value=task_status \n" + 
			"            and FIND_IN_SET(:empId,task_emp_ids) and FIND_IN_SET(:userId,task_emp_ids) and del_status = 1 and is_active=1),\n" + 
			"        0) as overdeu,\n" + 
			"        coalesce((select\n" + 
			"            count(*) \n" + 
			"        from\n" + 
			"            t_tasks \n" + 
			"        where\n" + 
			"            task_end_date=:date \n" + 
			"            and s.status_value=task_status \n" + 
			"            and  FIND_IN_SET(:empId,task_emp_ids) and FIND_IN_SET(:userId,task_emp_ids) and del_status = 1 and is_active=1),\n" + 
			"        0) as duetoday,\n" + 
			"        coalesce ((select\n" + 
			"            count(*) \n" + 
			"        from\n" + 
			"            t_tasks \n" + 
			"        where\n" + 
			"            WEEKOFYEAR(task_end_date)=WEEKOFYEAR(:date)  \n" + 
			"            and YEAR(:date) = YEAR(task_end_date) \n" + 
			"            and s.status_value=task_status \n" + 
			"            and  FIND_IN_SET(:empId,task_emp_ids) and FIND_IN_SET(:userId,task_emp_ids) and del_status = 1 and is_active=1),\n" + 
			"        0) as week  ,\n" + 
			"        coalesce ((select\n" + 
			"            count(*) \n" + 
			"        from\n" + 
			"            t_tasks \n" + 
			"        where\n" + 
			"            MONTH(task_end_date)=MONTH(:date) \n" + 
			"            and YEAR(:date) = YEAR(task_end_date) \n" + 
			"            and s.status_value=task_status \n" + 
			"            and  FIND_IN_SET(:empId,task_emp_ids) and FIND_IN_SET(:userId,task_emp_ids) and del_status = 1 and is_active=1),\n" + 
			"        0) as month  \n" + 
			"    from\n" + 
			"        dm_status_mst s \n" + 
			"    where\n" + 
			"        del_status=1 and s.status_value!=9",nativeQuery=true)
	List<TaskCountByStatus> getTaskCountByStatus(@Param("date") String date, @Param("empId") int empId, @Param("userId") int userId);

}
