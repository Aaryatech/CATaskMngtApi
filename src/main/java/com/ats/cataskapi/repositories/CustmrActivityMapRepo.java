package com.ats.cataskapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.cataskapi.model.CustmrActivityMap;
@Repository
public interface CustmrActivityMapRepo extends JpaRepository<CustmrActivityMap, Integer> {

	List<CustmrActivityMap> findAllBydelStatus(int del);
	
	CustmrActivityMap findByActvIdAndDelStatus(int actId, int del);
	@Query(value="select * from   m_cust_acti_map   WHERE mapping_id IN (:mappingIdList)",nativeQuery=true)
	List<CustmrActivityMap> getMappingForyearlyTaskGen(List<String> mappingIdList);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE  m_cust_acti_map SET del_status=0, update_username=:userId WHERE mapping_id=:mapId",nativeQuery=true)
	int deleteMappedCustomerActivity(@Param("mapId") int mapId, @Param("userId") int userId);
}
