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
	@Query(value="UPDATE t_tasks SET task_emp_ids=:empIdList  WHERE task_id IN(:taskIdList)",nativeQuery=true)
	int assignTask(@Param("taskIdList") List<Integer> taskIdList, @Param("empIdList") String empIdList);
	
	
	
	

}
