package com.ats.cataskapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.CustomerHeaderMaster;

public interface CustomerHeaderMasterRepo extends JpaRepository<CustomerHeaderMaster, Integer> {

	List<CustomerHeaderMaster> findAllByDelStatusOrderByCustIdDesc(int del);
	
	CustomerHeaderMaster findByCustIdAndDelStatus(int custHeadId, int del);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE  m_cust_header SET del_status=0, update_username=:userId WHERE cust_id=:custHeadId",nativeQuery=true)
	int deleteCustHeader(@Param("custHeadId") int custHeadId, @Param("userId") int userId);

	
	@Transactional
	@Modifying
	@Query(value = "UPDATE m_cust_header SET is_active=:isActiveStatus WHERE cust_id=:custId",nativeQuery=true)
	int updateIsActiveStatus(@Param("custId") int cust_id, @Param("isActiveStatus") int isActiveStatus);
	
	
}
