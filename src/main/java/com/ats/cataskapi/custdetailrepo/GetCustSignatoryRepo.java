package com.ats.cataskapi.custdetailrepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.custdetail.GetCustSignatory;

public interface GetCustSignatoryRepo extends JpaRepository<GetCustSignatory, Integer> {

	@Query(value=" SELECT CASE WHEN m_cust_header.cust_group_id=0 THEN m_cust_header.cust_firm_name  " + 
			"	    ELSE COALESCE(( SELECT m_cust_group.cust_group_name FROM m_cust_group WHERE m_cust_group.cust_group_id=m_cust_header.cust_group_id  " + 
			"	   AND m_cust_header.cust_id=m_cust_signatory.cust_id AND m_cust_group.del_status=1 ),0) END AS cust_firm_name, " + 
			"	   m_cust_signatory.signf_name,m_cust_signatory.signl_name," + 
			" m_cust_signatory.sign_reg_no,m_cust_signatory.sign_design,m_cust_signatory.sign_phno," + 
			" m_cust_signatory.contact_name,m_cust_signatory.contact_email,m_cust_signatory.contact_phno," + 
			" m_cust_signatory.cust_id,m_cust_signatory.cust_remark,m_cust_signatory.sign_id " + 
			"" + 
			" FROM m_cust_signatory,m_cust_header " + 
			" WHERE " + 
			" m_cust_signatory.cust_id=m_cust_header.cust_id AND m_cust_signatory.del_status=1 AND m_cust_header.del_status=1  and m_cust_header.cust_id=:custId "
			+ " order by m_cust_signatory.sign_id desc ", nativeQuery=true)
	List<GetCustSignatory> getGetCustSignatoryByCustId(@Param("custId") int custId);

	@Query(value=" SELECT CASE WHEN m_cust_header.cust_group_id=0 THEN m_cust_header.cust_firm_name  " + 
			"	    ELSE COALESCE(( SELECT m_cust_group.cust_group_name FROM m_cust_group WHERE m_cust_group.cust_group_id=m_cust_header.cust_group_id  " + 
			"	   AND m_cust_header.cust_id=m_cust_signatory.cust_id AND m_cust_group.del_status=1 ),0) END AS cust_firm_name,"
			+ " m_cust_signatory.signf_name,m_cust_signatory.signl_name," + 
			" m_cust_signatory.sign_reg_no,m_cust_signatory.sign_design,m_cust_signatory.sign_phno," + 
			" m_cust_signatory.contact_name,m_cust_signatory.contact_email,m_cust_signatory.contact_phno," + 
			" m_cust_signatory.cust_id,m_cust_signatory.cust_remark,m_cust_signatory.sign_id " + 
			"" + 
			" FROM m_cust_signatory,m_cust_header " + 
			" WHERE " + 
			" m_cust_signatory.cust_id=m_cust_header.cust_id AND m_cust_signatory.del_status=1 AND m_cust_header.del_status=1"
			+ "  and m_cust_signatory.sign_id=:signId "
			+ "", nativeQuery=true)
	GetCustSignatory getGetCustSignatoryBySignId(@Param("signId") int signId);

}
