package com.ats.cataskapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.CustomerDetailMaster;

public interface CustomerDetailMasterRepo extends JpaRepository<CustomerDetailMaster, Integer> {

	List<CustomerDetailMaster>	findAllByDelStatus(int del);
	
	CustomerDetailMaster findBycustDetailIdAndDelStatus(int custDetailId, int del);
	List<CustomerDetailMaster> findByCustIdAndDelStatus(int custId, int delStatus);


	@Transactional
	@Modifying
	@Query(value="UPDATE  m_cust_detail SET del_status=0, update_username=:userId WHERE cust_detail_id=:custDetailId",nativeQuery=true)
	int deleteCustDetail(@Param("custDetailId") int custDetailId, @Param("userId") int userId);
	
	//UPDATE m_cust_detail SET del_status=0 WHERE cust_detail_id IN (:custDetailId) 
}
