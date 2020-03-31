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
	@Query(value="	SELECT COUNT(*) from m_emp WHERE emp_id IN (:empIdList) AND emp_type=3",nativeQuery=true)
	int getCountofManagers(@Param("empIdList") List<String> empIdList);
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
	//Sac 04-02-2020
	@Query(value="SELECT COUNT(m_cust_header.cust_group_id) from m_cust_header WHERE m_cust_header.cust_group_id=:id",nativeQuery=true)
    int getCustCountByCustGrpId(@Param("id") int id);
	
	//Sac 27-03-2020
	List<CustomerHeaderMaster> findAllByDelStatusAndCustGroupIdOrderByCustFirmNameAsc(int del,int grpId);

	
	/*
	 * @Query(
	 * value=" SELECT a.*, CASE WHEN a.cust_type=1 THEN grp.cust_group_name ELSE '-' end as ex_var1, "
	 * + " emp.emp_name as ex_var2 FROM " +
	 * " ( SELECT m_cust_header.* FROM m_cust_header WHERE del_status=1 ) a " +
	 * " LEFT JOIN (SELECT m_cust_group.cust_group_id,m_cust_group.cust_group_name FROM m_cust_group) grp on a.cust_group_id=grp.cust_group_id "
	 * +
	 * " LEFT JOIN (SELECT m_emp.emp_name,m_emp.emp_id FROM m_emp WHERE del_status=1 and m_emp.emp_type=2 ) emp on a.owner_emp_id=emp.emp_id "
	 * + " ",nativeQuery=true) List<CustomerHeaderMaster> getCustMstForExcel();
	 */
	
	@Query(value=" SELECT a.*, CASE WHEN a.cust_type=1 THEN grp.cust_group_name ELSE '-' end as ex_var1, "
			+ " emp.emp_name as ex_var2 FROM "
			+ " ( SELECT  m_cust_header.cust_id,m_cust_header.owner_emp_id,m_cust_header.cust_type,m_cust_header.cust_group_id,m_cust_header.cust_firm_name,m_cust_header.cust_assessee_type_id,m_cust_header.cust_assessee_name, " + 
			"	m_cust_header.cust_pan_no,m_cust_header.cust_email_id,m_cust_header.cust_phone_no,m_cust_header.cust_addr1,m_cust_header.cust_addr2,m_cust_header.cust_city,m_cust_header.cust_pin_code, " + 
			"	m_cust_header.cust_busin_natute,m_cust_header.cust_is_dsc_avail,m_cust_header.cust_folder_id,m_cust_header.cust_file_no,m_cust_header.cust_dob,m_cust_header.cust_aadhar,m_cust_header.del_status, " + 
			"	m_cust_header.is_active,m_cust_header.update_datetime,m_cust_header.update_username,m_cust_header.ex_int1,m_cust_header.ex_int2  FROM m_cust_header WHERE del_status=1 ) a "
			+ " LEFT JOIN (SELECT m_cust_group.cust_group_id,m_cust_group.cust_group_name FROM m_cust_group) grp on a.cust_group_id=grp.cust_group_id "
			+ " LEFT JOIN (SELECT m_emp.emp_name,m_emp.emp_id FROM m_emp WHERE del_status=1 and m_emp.emp_type=2 ) emp on a.owner_emp_id=emp.emp_id " + 
			" ",nativeQuery=true)
	List<CustomerHeaderMaster> getCustMstForExcel();
		
	
			
}
