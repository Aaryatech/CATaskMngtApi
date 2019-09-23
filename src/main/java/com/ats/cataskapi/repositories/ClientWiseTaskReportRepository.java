package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.ClientWiseTaskReport;

public interface ClientWiseTaskReportRepository extends JpaRepository<ClientWiseTaskReport, Integer>{

	@Query(value="SELECT\n" + 
			"    b.*, \n" + 
			"    COALESCE((c.partner),\n" + 
			"    '-') AS partner,\n" + 
			"    COALESCE((c.manager),\n" + 
			"    '-') AS manager,\n" + 
			"    COALESCE((c.team_leader),\n" + 
			"    '-') AS team_leader,\n" + 
			"    COALESCE((c.employee),\n" + 
			"    '-') AS employee, \n" + 
			"    COALESCE((c.employee_ids),\n" + 
			"    '-') AS employee_ids,\n" + 
			"    COALESCE((c.tl_ids),\n" + 
			"    '-') AS tl_ids,\n" + 
			"    COALESCE((c.manger_ids),\n" + 
			"    '-') AS manger_ids,\n" + 
			"    COALESCE((c.partner_ids),\n" + 
			"    '-') AS partner_ids \n" + 
			"FROM\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        t_tasks.task_text,\n" + 
			"        m_services.serv_name,\n" + 
			"        m_activities.acti_name,\n" + 
			"        dm_periodicity.periodicity_name,\n" + 
			"        t_tasks.task_statutory_due_date,\n" + 
			"        t_tasks.task_start_date,\n" + 
			"        t_tasks.task_end_date,\n" + 
			"        t_tasks.emp_bud_hr,\n" + 
			"        t_tasks.mngr_bud_hr,\n" + 
			"      m_cust_header.cust_firm_name ,t_tasks.task_status, t_tasks.billing_amt as revenue,t_tasks.ex_int1 as google_drive_link, \n" + 
			"COALESCE(\n" + 
			"    (\n" + 
			"    SELECT\n" + 
			"        m_emp.emp_name\n" + 
			"    FROM\n" + 
			"        m_emp\n" + 
			"    WHERE\n" + 
			"        m_cust_header.owner_emp_id = m_emp.emp_id\n" + 
			"),\n" + 
			"'NA'\n" + 
			") AS owner_partner,\n" + 
			"t_tasks.task_emp_ids\n" + 
			"FROM\n" + 
			"    m_services,\n" + 
			"    m_activities,\n" + 
			"    dm_periodicity,\n" + 
			"    t_tasks,\n" + 
			"    m_cust_header\n" + 
			"WHERE\n" + 
			"    m_services.serv_id = t_tasks.serv_id AND m_activities.acti_id = t_tasks.actv_id AND dm_periodicity.periodicity_id = t_tasks.periodicity_id  AND t_tasks.del_status = 1 AND t_tasks.is_active = 1 AND t_tasks.cust_id = m_cust_header.cust_id AND  t_tasks.task_end_date BETWEEN :fromDate AND :toDate and t_tasks.cust_id in (:custId)\n" + 
			"GROUP BY\n" + 
			"    t_tasks.task_id\n" + 
			") b\n" + 
			"LEFT JOIN(\n" + 
			"    SELECT\n" + 
			"        a.task_id,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '2' THEN a.emp_name\n" + 
			"END\n" + 
			") partner,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '3' THEN a.emp_name\n" + 
			"END\n" + 
			") manager,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '4' THEN a.emp_name\n" + 
			"END\n" + 
			") team_leader,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '5' THEN a.emp_name\n" + 
			"END\n" + 
			") employee, \n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '5' THEN a.emp_id\n" + 
			"END\n" + 
			") employee_ids,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '4' THEN a.emp_id\n" + 
			"END\n" + 
			") tl_ids,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '3' THEN a.emp_id\n" + 
			"END\n" + 
			") manger_ids,\n" + 
			"GROUP_CONCAT(\n" + 
			"    CASE a.emp_type WHEN '2' THEN a.emp_id\n" + 
			"END\n" + 
			") partner_ids\n" + 
			"   \n" + 
			"FROM\n" + 
			"    (SELECT n.task_id,n.emp_id,n.emp_type,n.emp_name\n" + 
			"FROM\n" + 
			"(SELECT\n" + 
			"        t_tasks.task_id,\n" + 
			"        m_emp.emp_id,\n" + 
			"        m_emp.emp_type,\n" + 
			"        m_emp.emp_name\n" + 
			"       \n" + 
			"    FROM\n" + 
			"        m_emp,\n" + 
			"        t_tasks\n" + 
			"    WHERE\n" + 
			"        FIND_IN_SET(\n" + 
			"            m_emp.emp_id,\n" + 
			"            t_tasks.task_emp_ids\n" + 
			"        )\n" + 
			"       \n" + 
			" ) n  ) a\n" + 
			"GROUP BY\n" + 
			"    a.task_id\n" + 
			") c\n" + 
			"ON\n" + 
			"    b.task_id = c.task_id",nativeQuery=true)
	List<ClientWiseTaskReport> getclientWiseTaskReport(@Param("fromDate") String fromDate,@Param("toDate") String toDate, 
			@Param("custId") List<Integer> clntIds);

	@Query(value = "select GROUP_CONCAT(DISTINCT task_emp_ids)  from t_tasks where task_end_date BETWEEN :fromDate AND :toDate and t_tasks.cust_id in (:custId) "
			+ "and t_tasks.del_status = 1 AND t_tasks.is_active = 1", nativeQuery = true)
	String getEmpListByTaskId(@Param("fromDate") String fromDate,@Param("toDate") String toDate, 
			@Param("custId") List<Integer> clntIds);

}
