package com.ats.cataskapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.cataskapi.model.StatusMaster;

@Repository
public interface StatusMasterRepo extends JpaRepository<StatusMaster, Integer> {
	
	public List<StatusMaster> findAllByDelStatusAndIsEditableOrderByStatusMstIdDesc(int del, int isEdit);

	public StatusMaster findByStatusMstId(int statusId);

	@Transactional
	@Modifying
	@Query(value="UPDATE  dm_status_mst SET del_status=0, update_username=:userId WHERE status_mst_id=:statusId",nativeQuery=true)
	public int deleteStatusById(@Param("statusId") int statusId, @Param("userId") int userId);
	
	
	
	  @Query(value="SELECT *  \n" + 
	  		"    FROM\n" + 
	  		"        dm_status_mst \n" + 
	  		"    WHERE\n" + 
	  		"        dm_status_mst.del_status=1 \n" + 
	  		"        AND    (FIND_IN_SET(0, dm_status_mst.type_ids) OR FIND_IN_SET(:empType, dm_status_mst.type_ids))", nativeQuery=true) public
	  List<StatusMaster> getStatusByEmpType(@Param("empType") int empType);

	@Query(value="SELECT MAX(status_value) FROM dm_status_mst",nativeQuery=true) 
	public int getMaxStateValue();
	 
	@Query(value="SELECT count(status_text) FROM dm_status_mst where status_text=:statusText and del_status=1 ",nativeQuery=true) 
	public int getStatusForDuplicate(@Param("statusText") String statusText);
	 
	@Query(value="SELECT count(status_text) FROM dm_status_mst where status_text=:statusText and status_mst_id!=:statusMstId and del_status=1",nativeQuery=true) 
	public int getStatusForDuplicateForEdit(@Param("statusText") String statusText,@Param("statusMstId") int statusMstId);
	 
	 
	
	/*SELECT	m_status.status_id,
  		m_status.status_text 
    
FROM  	m_status

WHERE 	m_status.del_status=1 AND
		FIND_IN_SET(2,m_status.emp_type_ids)*/
}
