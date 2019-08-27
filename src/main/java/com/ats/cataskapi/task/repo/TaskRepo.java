package com.ats.cataskapi.task.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.task.model.Task;

public interface TaskRepo extends JpaRepository<Task, Integer> {

	@Transactional
	@Modifying
	@Query(value = "UPDATE t_tasks SET task_emp_ids=:empIdList,update_username=:userId,update_datetime=:curDateTime,task_status=1,task_end_date=:endDate WHERE task_id IN(:taskIdList)", nativeQuery = true)
	int assignTask(@Param("taskIdList") List<Integer> taskIdList, @Param("empIdList") String empIdList,
			@Param("userId") String userId, @Param("curDateTime") String curDateTime, @Param("endDate") String endDate);

	@Transactional
	@Modifying
	@Query(value = "UPDATE t_tasks SET t_tasks.task_status=:statusVal WHERE t_tasks.task_id=:taskId", nativeQuery = true)
	int updateStatus(@Param("taskId") int taskId, @Param("statusVal") int statusVal);

	@Transactional
	@Modifying
	@Query(value = "UPDATE t_tasks SET t_tasks.del_status=0 WHERE t_tasks.task_id=:taskId", nativeQuery = true)
	int updateStatus1(@Param("taskId") int taskId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE t_tasks SET t_tasks.is_active=1 WHERE t_tasks.task_id=:taskId", nativeQuery = true)
	int activateTask(@Param("taskId") int taskId);

	@Query(value = "select * from t_tasks where serv_id=:servId  and task_status not in (0,8,9)", nativeQuery = true)
	List<Task> getTaskListForisactive(@Param("servId") int servId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE t_tasks SET t_tasks.is_active=:isActiveStatus WHERE t_tasks.task_id in (:taskIds)", nativeQuery = true)
	int updateIsActiveStatus(@Param("taskIds") List<Integer> taskIds, @Param("isActiveStatus") int isActiveStatus);

}
