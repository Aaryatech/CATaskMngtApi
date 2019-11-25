package com.ats.cataskapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.CustomerHeaderMaster;
import com.ats.cataskapi.model.EmpListForDashboard;

public interface CustomerHeaderMasterRepo extends JpaRepository<CustomerHeaderMaster, Integer> {

	List<CustomerHeaderMaster> findAllByDelStatusOrderByCustIdDesc(int del);
	
	CustomerHeaderMaster findByCustIdAndDelStatus(int custHeadId, int del);
	
	//Sachin 25-11-2019
	CustomerHeaderMaster findByCustPanNoAndDelStatus(String panNo,int delStatus);
	//Sachin 25-11-2019
	CustomerHeaderMaster findByCustPanNoAndDelStatusAndCustIdNot(String panNo,int delStatus,int custId);

	@Transactional
	@Modifying
	@Query(value="UPDATE  m_cust_header SET del_status=0, update_username=:userId WHERE cust_id=:custHeadId",nativeQuery=true)
	int deleteCustHeader(@Param("custHeadId") int custHeadId, @Param("userId") int userId);

	
	@Transactional
	@Modifying
	@Query(value = "UPDATE m_cust_header SET is_active=:isActiveStatus WHERE cust_id=:custId",nativeQuery=true)
	int updateIsActiveStatus(@Param("custId") int cust_id, @Param("isActiveStatus") int isActiveStatus);
	
	/*
	 * @Query(value="SELECT m_cust_header.cust_id, CASE \n" +
	 * "            WHEN m_cust_header.cust_group_id = 0 THEN m_cust_header.cust_firm_name \n"
	 * + "            ELSE COALESCE(         (         SELECT\n" +
	 * "                m_cust_group.cust_group_name         \n" +
	 * "            FROM\n" + "                m_cust_group      \n" +
	 * "            WHERE\n" +
	 * "                m_cust_group.cust_group_id = m_cust_header.cust_group_id \n"
	 * + "                AND m_cust_group.del_status = 1),\n" +
	 * "            0     ) \n" +
	 * "        END AS cust_firm_name FROM m_cust_header  WHERE m_cust_header.cust_id=:custHeadId "
	 * ,nativeQuery=true) CustomerHeaderMaster getCustHead(@Param("custHeadId") int
	 * custHeadId);
	 */
}
