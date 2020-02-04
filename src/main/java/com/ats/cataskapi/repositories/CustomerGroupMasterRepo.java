package com.ats.cataskapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.CustomerGroupMaster;

public interface CustomerGroupMasterRepo extends JpaRepository<CustomerGroupMaster, Integer> {

	CustomerGroupMaster findByCustGroupIdAndDelStatus(int custGrpId, int del);
	
	List<CustomerGroupMaster> findByDelStatusOrderByCustGroupIdDesc(int del);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE  m_cust_group SET del_status=0, update_username=:userId WHERE cust_group_id=:custGrpId",nativeQuery=true)
	int deleteCustGroup(@Param("custGrpId") int custGrpId, @Param("userId") int userId);
	
	//UPDATE m_cust_group SET del_status=0 WHERE cust_group_id IN (:custGrpId) 
	@Query(value="SELECT count(  m_cust_group SET del_status=0, update_username=:userId WHERE cust_group_id=:custGrpId",nativeQuery=true)
	int getCustCount(@Param("custGrpId") int custGrpId, @Param("userId") int userId);
	
	
	@Query(value="SELECT COUNT(m_cust_group.cust_group_id) from m_cust_group WHERE m_cust_group.cust_id=:id",nativeQuery=true)
    int getCustCountByCustId(@Param("id") int id);
	
}
