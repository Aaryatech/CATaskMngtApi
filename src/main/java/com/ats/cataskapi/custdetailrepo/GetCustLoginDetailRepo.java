package com.ats.cataskapi.custdetailrepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.custdetail.GetCustLoginDetail;

public interface GetCustLoginDetailRepo extends JpaRepository<GetCustLoginDetail, Integer> {
	
	
	
	  @Query(
	  value=" SELECT CASE WHEN m_cust_header.cust_group_id=0 THEN m_cust_header.cust_firm_name "
	  +
	  " ELSE COALESCE(( SELECT m_cust_group.cust_group_name FROM m_cust_group WHERE m_cust_group.cust_group_id=m_cust_header.cust_group_id "
	  +
	  " AND m_cust_header.cust_id=:custId AND m_cust_group.del_status=1 ),0) END AS cust_firm_name,"
	  + "" +
	  " m_services.serv_name,m_activities.acti_name,m_cust_detail.login_id,m_cust_detail.login_pass,"
	  +
	  " m_cust_detail.login_que1,m_cust_detail.login_ans1,m_cust_detail.login_que2,m_cust_detail.login_ans2,"
	  +
	  " m_cust_detail.login_remark,m_cust_detail.cust_detail_id,m_cust_detail.cust_id,m_cust_detail.actv_id,m_services.serv_id "
	  + "" + " FROM m_cust_detail,m_cust_header,m_activities,m_services " + "" +
	  " WHERE m_cust_detail.cust_id=m_cust_header.cust_id AND m_cust_detail.actv_id=m_activities.acti_id and "
	  +
	  " m_activities.serv_id=m_services.serv_id AND m_cust_detail.del_status=1 AND m_cust_header.del_status=1 "
	  +
	  " AND m_activities.del_status=1 AND m_services.del_status=1 AND m_cust_detail.cust_id=:custId ORDER BY m_cust_detail.cust_detail_id DESC "
	  ,nativeQuery=true)
	  
	  List<GetCustLoginDetail> getGetCustLoginDetailByCustId(@Param("custId") int
	  custId);
	 
	
	@Query(value=" CASE WHEN m_cust_header.cust_group_id=0 THEN m_cust_header.cust_firm_name \"\n" + 
			"	  \" ELSE COALESCE(( SELECT m_cust_group.cust_group_name FROM m_cust_group WHERE m_cust_group.cust_group_id=m_cust_header.cust_group_id \"\n" + 
			"	  \" AND m_cust_header.cust_id=:custId AND m_cust_group.del_status=1 ),0) END AS cust_firm_name, " + 
		
			" m_services.serv_name,m_activities.acti_name,m_cust_detail.login_id,m_cust_detail.login_pass," + 
			" m_cust_detail.login_que1,m_cust_detail.login_ans1,m_cust_detail.login_que2,m_cust_detail.login_ans2," + 
			" m_cust_detail.login_remark,m_cust_detail.cust_detail_id,m_cust_detail.cust_id,m_cust_detail.actv_id,m_services.serv_id " + 
			"" + 
			" FROM m_cust_detail,m_cust_header,m_activities,m_services " + 
			"" + 
			" WHERE m_cust_detail.cust_id=m_cust_header.cust_id AND m_cust_detail.actv_id=m_activities.acti_id and " + 
			" m_activities.serv_id=m_services.serv_id AND m_cust_detail.del_status=1 AND m_cust_header.del_status=1 " + 
			" AND m_activities.del_status=1 AND m_services.del_status=1 AND m_cust_detail.cust_id=:custId ORDER BY m_cust_detail.cust_detail_id DESC ",nativeQuery=true)
	
	List<GetCustLoginDetail> getGetCustLoginDetailByCustId1(@Param("custId") int custId);

	@Query(value=" SELECT CASE WHEN m_cust_header.cust_group_id=0 THEN m_cust_header.cust_firm_name  " + 
			"ELSE COALESCE(( SELECT m_cust_group.cust_group_name FROM m_cust_group WHERE m_cust_group.cust_group_id=m_cust_header.cust_group_id  " + 
			"	AND m_cust_header.cust_id=:custId AND m_cust_group.del_status=1 ),0) END AS cust_firm_name, " + 
			
			" m_services.serv_name,m_activities.acti_name,m_cust_detail.login_id,m_cust_detail.login_pass," + 
			" m_cust_detail.login_que1,m_cust_detail.login_ans1,m_cust_detail.login_que2,m_cust_detail.login_ans2," + 
			" m_cust_detail.login_remark,m_cust_detail.cust_detail_id,m_cust_detail.cust_id, m_cust_detail.actv_id, m_services.serv_id " + 
			"" + 
			" FROM m_cust_detail,m_cust_header,m_activities,m_services " + 
			"" + 
			" WHERE m_cust_detail.cust_id=m_cust_header.cust_id AND m_cust_detail.actv_id=m_activities.acti_id and " + 
			" m_activities.serv_id=m_services.serv_id AND m_cust_detail.del_status=1 AND m_cust_header.del_status=1 " + 
			" AND m_activities.del_status=1 AND m_services.del_status=1 AND m_cust_detail.cust_detail_id=:custDetailId ",nativeQuery=true)
	
	GetCustLoginDetail getGetCustLoginDetailByCustDetailId(@Param("custDetailId") int custDetailId);

}
