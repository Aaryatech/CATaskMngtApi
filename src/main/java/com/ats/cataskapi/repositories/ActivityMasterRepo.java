package com.ats.cataskapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.ActivityMaster;

public interface ActivityMasterRepo extends JpaRepository<ActivityMaster, Integer> {

	List<ActivityMaster> findByDelStatus(int del);

	List<ActivityMaster> findByServIdAndDelStatus(int serviceid, int del);

	ActivityMaster findByActiIdAndDelStatus(int activityId, int i);

	@Transactional
	@Modifying
	@Query(value = "UPDATE m_activities SET del_status=0, update_username=:userId WHERE acti_id=:activityId", nativeQuery = true)
	int deleteActivity(@Param("activityId") int activityId, @Param("userId") int userId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE m_activities SET ex_int1=:isActiveStatus WHERE serv_id=:servId", nativeQuery = true)
	int updateIsActiveStatus(@Param("servId") int servId, @Param("isActiveStatus") int isActiveStatus);

}
