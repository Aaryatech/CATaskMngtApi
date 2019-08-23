package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.CustNameId;

public interface CustNameIdRepo extends JpaRepository<CustNameId, Integer> {
	
	@Query(value="SELECT CASE WHEN m_cust_header.cust_group_id=0 THEN m_cust_header.cust_firm_name " + 
			"	  ELSE COALESCE(( SELECT m_cust_group.cust_group_name FROM m_cust_group WHERE m_cust_group.cust_group_id=m_cust_header.cust_group_id  " + 
			"	   AND m_cust_header.cust_id=:custId AND m_cust_group.del_status=1 ),0) END AS cust_name, "+
			" m_cust_header.cust_id FROM m_cust_header where m_cust_header.cust_id=:custId and m_cust_header.del_status=1   ",nativeQuery=true)
	CustNameId getCustNameId(@Param("custId") int custId);
	
}
