package com.ats.cataskapi.custdetailrepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ats.cataskapi.model.CustomerDetails;

public interface CustomerDetailsRepo extends JpaRepository<CustomerDetails, Integer> {
	
	@Query(value="SELECT m_cust_header.cust_id, m_cust_header.cust_firm_name,\n" + 
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
			"      m_cust_header.cust_group_id=m_cust_group.cust_group_id ORDER BY m_cust_header.cust_id DESC",nativeQuery=true)
	List<CustomerDetails> getAllCustomerDetails();

}
