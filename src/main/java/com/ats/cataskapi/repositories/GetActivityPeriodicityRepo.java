package com.ats.cataskapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.GetActivityPeriodicity;

public interface GetActivityPeriodicityRepo extends JpaRepository<GetActivityPeriodicity, Integer> {

	@Query(value="Select\n" + 
			"        m_activities.acti_id,\n" + 
			"        m_activities.acti_name ,\n" + 
			"        dm_periodicity.periodicity_name, \n" + 
			"        dm_periodicity.periodicity_id\n" + 
			"    From\n" + 
			"        dm_periodicity,\n" + 
			"        m_activities  \n" + 
			"    Where	m_activities.del_status=1 AND\n" + 
			"            m_activities.acti_id=:activityId AND\n" + 
			"            m_activities.periodicity_id=dm_periodicity.periodicity_id",nativeQuery=true)
	public GetActivityPeriodicity getPriodicityByActid(@Param("activityId") int activityId);
}
