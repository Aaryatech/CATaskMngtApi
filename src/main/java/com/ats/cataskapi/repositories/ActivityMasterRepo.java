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
	@Query(value="UPDATE m_activities SET del_status=0, update_username=:userId WHERE acti_id=:activityId",nativeQuery=true)
	int deleteActivity(@Param("activityId") int activityId, @Param("userId") int userId);

	
	@Query(value="SELECT m_activities.acti_id,\n" + 
			"	m_activities.acti_name,\n" + 
			"    m_activities.acti_desc,\n" + 
			"    m_activities.serv_id,\n" + 
			"    m_activities.periodicity_id,\n" + 
			"    m_activities.del_status,\n" + 
			"    m_activities.update_datetime,\n" + 
			"    m_activities.update_username,\n" + 
			"    m_activities.ex_int2,\n" + 
			"    m_activities.ex_var2,\n" + 
			"    dm_periodicity.periodicity_id as ex_int1,\n" + 	//Periodicity ID
			"    dm_periodicity.periodicity_name as ex_var1\n" + 	//Periodicity Name
			"   \n" + 
			"FROM m_activities, dm_periodicity\n" + 
			"WHERE m_activities.del_status=1 AND\n" + 
			"	  m_activities.serv_id=:serviceId AND\n" + 
			"	  dm_periodicity.periodicity_id=m_activities.acti_id",nativeQuery=true)
	List<ActivityMaster> getAllActivityDetailsList(@Param("serviceId") int serviceId);
	
	//UPDATE m_activities SET del_status=0 WHERE acti_id IN (:actvgtList) 
	

}
