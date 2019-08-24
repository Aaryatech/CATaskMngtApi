package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.ShowCustActiMapped;

public interface ShowCustActiMappedRepo extends JpaRepository<ShowCustActiMapped, Integer> {

	@Query(value = "SELECT\n" + 
			"        m_cust_acti_map.mapping_id,\n" + 
			"        m_cust_acti_map.actv_start_date,\n" + 
			"        m_cust_acti_map.actv_end_date,\n" + 
			"        m_cust_acti_map.actv_statutory_days,\n" + 
			"        m_cust_acti_map.actv_man_budg_hr,\n" + 
			"        m_cust_acti_map.actv_emp_budg_hr,\n" + 
			"        m_cust_acti_map.actv_billing_amt,\n" + 
			"       CASE \n" + 
			"            WHEN m_cust_header.cust_group_id=0 \n" + 
			"            	THEN m_cust_header.cust_firm_name    \n" + 
			"           		 ELSE COALESCE(( SELECT\n" + 
			"              		  m_cust_group.cust_group_name \n" + 
			"          	  FROM\n" + 
			"               	 m_cust_group \n" + 
			"           	 WHERE\n" + 
			"                m_cust_group.cust_group_id=m_cust_header.cust_group_id      \n" + 
			"                AND m_cust_header.cust_id=:custId\n" + 
			"                AND m_cust_group.del_status=1 ),\n" + 
			"            0) \n" + 
			"        END AS cust_group_name,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name      \n" + 
			"    FROM\n" + 
			"        m_cust_acti_map,\n" + 
			"        dm_periodicity,\n" + 
			"        m_services,\n" + 
			"        m_activities,\n" + 
			"        m_cust_header      \n" + 
			"    WHERE\n" + 
			"        m_cust_header.cust_id=:custId \n" + 
			"        AND  m_cust_acti_map.cust_id=m_cust_header.cust_id \n" + 
			"        AND  m_cust_acti_map.del_status=1 \n" + 
			"        AND  m_cust_acti_map.actv_id=m_activities.acti_id \n" + 
			"        AND  m_services.serv_id=m_activities.serv_id \n" + 
			"        AND  m_activities.periodicity_id=dm_periodicity.periodicity_id ORDER BY  m_cust_acti_map.mapping_id DESC", nativeQuery=true)
	List<ShowCustActiMapped> getAllCustActiMapList(@Param("custId") int custId);
}


/*SELECT\n" + 
"	m_cust_acti_map.mapping_id,\n" + 
"	m_cust_acti_map.actv_start_date,\n" + 
"    m_cust_acti_map.actv_end_date,\n" + 
"    m_cust_acti_map.actv_statutory_days,\n" + 
"    m_cust_acti_map.actv_man_budg_hr,\n" + 
"    m_cust_acti_map.actv_emp_budg_hr,\n" + 
"    m_cust_acti_map.actv_billing_amt,\n" + 
"    m_cust_group.cust_group_name,\n" + 
"    m_services.serv_name,\n" + 
"    m_activities.acti_name,\n" + 
"    dm_periodicity.periodicity_name\n" + 
"    \n" + 
"FROM m_cust_acti_map,\n" + 
"	m_cust_group,\n" + 
"    dm_periodicity,\n" + 
"	m_services,\n" + 
"	m_activities,\n" + 
"    m_cust_header\n" + 
"    \n" + 
"WHERE m_cust_header.cust_id=:custId AND\n" + 
"	m_cust_acti_map.cust_id=m_cust_header.cust_id AND\n" + 
"	m_cust_header.cust_group_id=m_cust_group.cust_group_id AND\n" + 
"	m_cust_acti_map.del_status=1 AND\n" + 
"	m_cust_acti_map.actv_id=m_activities.acti_id AND\n" + 
"    m_services.serv_id=m_activities.serv_id AND\n" + 
"    m_activities.periodicity_id=dm_periodicity.periodicity_id*/