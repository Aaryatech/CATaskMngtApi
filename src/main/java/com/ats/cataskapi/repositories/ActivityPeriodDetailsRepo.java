package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.ActivityPeriodDetails;

public interface ActivityPeriodDetailsRepo extends JpaRepository<ActivityPeriodDetails, Integer> {

	@Query(value="SELECT m_activities.acti_id,\n" + 
			"	m_activities.acti_name, \n" + 
			"	m_activities.serv_id, \n" + 
			"	 m_activities.acti_desc, \n" + 
			"    dm_periodicity.periodicity_id, \n" + 
			"    dm_periodicity.periodicity_name \n" + 
			"   \n" + 
			"FROM m_activities, dm_periodicity\n" + 
			"WHERE m_activities.del_status=1 AND\n" + 
			"	m_activities.serv_id=:serviceId AND\n" + 
			"	  dm_periodicity.periodicity_id=m_activities.acti_id",nativeQuery=true)
	List<ActivityPeriodDetails> getAllActivityDetailsList(@Param("serviceId") int serviceId);
}
