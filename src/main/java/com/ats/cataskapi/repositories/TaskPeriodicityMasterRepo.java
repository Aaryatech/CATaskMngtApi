package com.ats.cataskapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.TaskPeriodicityMaster;

public interface TaskPeriodicityMasterRepo extends JpaRepository<TaskPeriodicityMaster, Integer> {

	List<TaskPeriodicityMaster> findByDelStatus(int i);

	TaskPeriodicityMaster findByPeriodicityIdAndDelStatus(int periodicityId, int i);
	
	
	@Transactional
	@Modifying
	@Query(value="UPDATE m_task_periodicity SET del_status=0, update_username=:userId WHERE taskp_id=:periodicityId",nativeQuery=true)
	int deleteTaskPeriodicity(@Param("periodicityId") int periodicityId, @Param("userId") int userId);
	
	//UPDATE m_task_periodicity SET del_status=0 WHERE acti_id IN (:actvgtList) 
	


}
