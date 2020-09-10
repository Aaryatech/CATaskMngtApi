package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.ClientGroupList;

public interface ClientGroupRepo extends JpaRepository<ClientGroupList, Integer> {

	@Query(value = "select cust_group_id as id ,cust_group_name as name from m_cust_group where del_status=1", nativeQuery = true)
	List<ClientGroupList> getClientGroupList();

	@Query(value = "select cust_id as id ,cust_firm_name as name from m_cust_header where del_status=1 and is_active=1 and cust_group_id =:groupId", nativeQuery = true)
	List<ClientGroupList> getClinetListByGroupId(@Param("groupId") int groupId);

	@Query(value = "select cust_id as id ,cust_firm_name as name from m_cust_header where del_status=1 and is_active=1 and cust_type=1 and cust_group_id!=0 ", nativeQuery = true)
	List<ClientGroupList> getClinetListAllGroup();
	
	
	
	@Query(value = "select cust_id as id ,cust_firm_name as name from m_cust_header where del_status=1 and is_active=1 and cust_type=0  ", nativeQuery = true)
	List<ClientGroupList> getIndividualClinet();

}
