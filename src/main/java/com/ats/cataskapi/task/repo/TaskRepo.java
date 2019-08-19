package com.ats.cataskapi.task.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.cataskapi.task.model.Task;

public interface TaskRepo extends JpaRepository<Task, Integer> {
	
	
	
	

}
