package com.ats.cataskapi.custdetailrepo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.cataskapi.model.CustomerDetails;
@Repository
public interface CustomerDetailsRepo extends JpaRepository<CustomerDetails, Integer> {
	
	@Query(value="SELECT 0 as cust_group_name,\n" + 
			" CASE WHEN m_cust_header.cust_group_id=0 THEN m_cust_header.cust_firm_name \n" + 
			"	 \n" + 
			"	  ELSE COALESCE(( SELECT m_cust_group.cust_group_name FROM m_cust_group WHERE m_cust_group.cust_group_id=m_cust_header.cust_group_id \n" + 
			"	 \n" + 
			"	  AND m_cust_group.del_status=1 ),0) END AS cust_firm_name,\n" + 
			"        m_cust_header.cust_id,\n" + 
			"        m_cust_header.cust_assessee_name,\n" + 
			"        m_cust_header.cust_pan_no,\n" + 
			"        m_cust_header.cust_email_id,\n" + 
			"        m_cust_header.cust_phone_no,\n" + 
			"        m_cust_header.is_active,\n" + 
			"        m_emp.emp_name\n" + 
			"    FROM\n" + 
			"        m_cust_header,\n" + 
			"        m_emp\n" + 
			"    WHERE\n" + 
			"        m_cust_header.del_status=1  AND  m_cust_header.is_active=1 \n" + 
			"        AND    m_cust_header.owner_emp_id=m_emp.emp_id \n" + 			
			"    ORDER BY\n" + 
			"        m_cust_header.cust_id DESC",nativeQuery=true)
	List<CustomerDetails> getAllCustomerDetails();
	
	/*SELECT m_cust_header.cust_id, m_cust_header.cust_firm_name,\n" + 
	"		m_cust_header.cust_assessee_name,\n" + 
	"        m_cust_header.cust_pan_no,\n" + 
	"        m_cust_header.cust_email_id,\n" + 
	"        m_cust_header.cust_phone_no,\n" + 
	"        m_emp.emp_name,\n" + 
	"		m_cust_group.cust_group_name\n" + 
	"        \n" + 
	"FROM m_cust_header, m_emp, m_cust_group \n" + 
	"WHERE m_cust_header.del_status=1 AND\n" + 
	"	  m_cust_header.owner_emp_id=m_emp.emp_id AND\n" + 
	"      m_cust_header.cust_group_id=m_cust_group.cust_group_id ORDER BY m_cust_header.cust_id DESC*/
	
	/***************************************************************************/

	@Query(value="SELECT\n" + 
			"        m_cust_header.cust_id,\n" + 
			"        m_cust_header.cust_firm_name,\n" + 
			"        m_cust_header.cust_assessee_name,\n" + 
			"        m_cust_header.cust_pan_no,\n" + 
			"        m_cust_header.cust_email_id,\n" + 
			"        m_cust_header.cust_phone_no,\n" + 
			"        m_emp.emp_name,\n" + 
			"        m_cust_group.cust_group_name          \n" + 
			"    FROM\n" + 
			"        m_cust_header,\n" + 
			"        m_emp,\n" + 
			"        m_cust_group  \n" + 
			"    WHERE\n" + 
			"    	m_cust_header.cust_id=:custId AND  \n" + 
			"        m_cust_header.del_status=1 AND  m_cust_header.is_active=1 AND\n" + 
			"        m_cust_header.owner_emp_id=m_emp.emp_id AND\n" + 
			"        m_cust_header.cust_group_id=m_cust_group.cust_group_id",nativeQuery=true)
	CustomerDetails findByCustId(@Param("custId") int custId);
	
	
	@Query(value="SELECT 0 as cust_group_name,\n" + 
			" 		 m_cust_header.cust_firm_name,\n" + 
			"        m_cust_header.cust_id,\n" + 
			"        m_cust_header.cust_assessee_name,\n" + 
			"        m_cust_header.cust_pan_no,\n" + 
			"        m_cust_header.cust_email_id,\n" + 
			"        m_cust_header.cust_phone_no,\n" + 
			"        m_cust_header.is_active,\n" + 
			"        m_emp.emp_name\n" + 
			"    FROM\n" + 
			"        m_cust_header,\n" + 
			"        m_emp\n" + 
			"    WHERE\n" + 
			"        m_cust_header.del_status=1   \n" + 
			"        AND    m_cust_header.owner_emp_id=m_emp.emp_id \n" + 			
			"    ORDER BY\n" + 
			"        m_cust_header.cust_id DESC",nativeQuery=true)
	List<CustomerDetails> getAllCustomerDetailsActiveInactive();
	
	
	/*SELECT 0 as cust_group_name,\n" + 
	" CASE WHEN m_cust_header.cust_group_id=0 THEN m_cust_header.cust_firm_name \n" + 
	"	 \n" + 
	"	  ELSE COALESCE(( SELECT m_cust_group.cust_group_name FROM m_cust_group WHERE m_cust_group.cust_group_id=m_cust_header.cust_group_id \n" + 
	"	 \n" + 
	"	  AND m_cust_group.del_status=1 ),0) END AS cust_firm_name,\n" + 
	"        m_cust_header.cust_id,\n" + 
	"        m_cust_header.cust_assessee_name,\n" + 
	"        m_cust_header.cust_pan_no,\n" + 
	"        m_cust_header.cust_email_id,\n" + 
	"        m_cust_header.cust_phone_no,\n" + 
	"        m_cust_header.is_active,\n" + 
	"        m_emp.emp_name\n" + 
	"    FROM\n" + 
	"        m_cust_header,\n" + 
	"        m_emp\n" + 
	"    WHERE\n" + 
	"        m_cust_header.del_status=1   \n" + 
	"        AND    m_cust_header.owner_emp_id=m_emp.emp_id \n" + 			
	"    ORDER BY\n" + 
	"        m_cust_header.cust_id DESC*/

	
}
