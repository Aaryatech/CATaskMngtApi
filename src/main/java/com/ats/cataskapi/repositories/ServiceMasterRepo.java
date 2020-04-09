package com.ats.cataskapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.ServiceMaster;

public interface ServiceMasterRepo extends JpaRepository<ServiceMaster, Integer> {

	List<ServiceMaster> findByDelStatusOrderByServIdDesc(int del);
	
	List<ServiceMaster> findByDelStatusAndExInt1OrderByServIdDesc(int del, int enrol);

	ServiceMaster findByServIdAndDelStatus(int serviceId, int del);

	@Transactional
	@Modifying
	@Query(value="UPDATE m_services SET del_status=0, update_username=:userId  WHERE serv_id=:serviceId",nativeQuery=true)
	int deleteService(@Param("serviceId") int serviceId, @Param("userId") int userId);


	@Transactional
	@Modifying
	@Query(value="UPDATE m_services SET ex_int1=:isActiveStatus WHERE serv_id=:servId",nativeQuery=true)
	int updateIsActiveStatus(@Param("servId") int servId,@Param("isActiveStatus") int isActiveStatus);
	
	//UPDATE m_services SET del_status=0 WHERE serv_id IN (:servIdList) 
	//Sachin 31-03-2020
	@Query(value="	SELECT * from m_services WHERE m_services.serv_id IN (SELECT m_activities.serv_id FROM m_activities WHERE m_activities.periodicity_id=:periodcityId) and m_services.del_status=1 and m_services.ex_int1=1 " + 
			"",nativeQuery=true)
	List<ServiceMaster> getServListByPeriodId(@Param("periodcityId") int periodcityId);
	

}
