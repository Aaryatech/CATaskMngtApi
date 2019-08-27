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

}
