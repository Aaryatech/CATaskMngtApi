package com.ats.cataskapi.common;

import java.util.Date;

public class DateValues {
	
	Date date;
	String value;
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "DateValues [date=" + date + ", value=" + value + "]";
	}

	/*
	 * SELECT b.*,x.work_hours, coalesce((c.admin), '-')as admin,
	 * coalesce((c.partner), '-') as partner , coalesce((c.manager), '-') as
	 * manager, coalesce((c.team_leader), '-') as team_leader,
	 * coalesce((c.employee), '-') as employee from (SELECT t_tasks.task_id,
	 * t_tasks.task_text, t_tasks.ex_var1, m_services.serv_name,
	 * m_activities.acti_name, dm_periodicity.periodicity_name,
	 * t_tasks.task_statutory_due_date, t_tasks.task_completion_date as
	 * task_start_date, t_tasks.update_datetime AS task_end_date, CONCAT(FLOOR(
	 * t_tasks.emp_bud_hr/60), ':', LPAD(MOD(t_tasks.emp_bud_hr, 60), 2, '0')) as
	 * emp_bud_hr, CONCAT(FLOOR( t_tasks.mngr_bud_hr/60), ':',
	 * LPAD(MOD(t_tasks.mngr_bud_hr, 60), 2, '0')) as mngr_bud_hr,
	 * m_cust_header.cust_firm_name
	 * 
	 * FROM m_services, m_activities, dm_periodicity, t_tasks, m_cust_header WHERE
	 * m_services.serv_id = t_tasks.serv_id AND m_activities.acti_id =
	 * t_tasks.actv_id AND dm_periodicity.periodicity_id = t_tasks.periodicity_id
	 * AND t_tasks.task_status = 9 AND t_tasks.del_status = 1 AND t_tasks.is_active
	 * = 1 AND t_tasks.cust_id = m_cust_header.cust_id AND
	 * t_tasks.task_completion_date between '2020-03-01' and '2020-03-31' AND
	 * FIND_IN_SET(84,t_tasks.task_emp_ids) group by t_tasks.task_id) b
	 * 
	 * left join (
	 * 
	 * SELECT t_daily_work_log.task_id, COALESCE( (CONCAT( FLOOR(
	 * SUM(t_daily_work_log.work_hours) / 60 ), '.', LPAD( MOD(
	 * SUM(t_daily_work_log.work_hours), 60), 2, '0') )), 0) AS work_hours from
	 * t_daily_work_log,t_tasks WHERE t_tasks.task_status = 9 AND t_tasks.del_status
	 * = 1 AND t_tasks.is_active = 1 AND t_daily_work_log.task_id=t_tasks.task_id
	 * and t_daily_work_log.emp_id=84 AND t_tasks.task_completion_date between
	 * '2020-03-01' and '2020-03-31' AND FIND_IN_SET(84,t_tasks.task_emp_ids) group
	 * by t_daily_work_log.task_id ) x on b.task_id=x.task_id
	 * 
	 * LEFT JOIN ( select a.task_id, group_concat(CASE a.emp_type WHEN '1' THEN
	 * a.emp_name END) admin, group_concat(CASE a.emp_type WHEN '2' THEN a.emp_name
	 * END) partner, group_concat(CASE a.emp_type WHEN '3' THEN a.emp_name END)
	 * manager, group_concat(CASE a.emp_type WHEN '4' THEN a.emp_name END)
	 * team_leader, group_concat(CASE a.emp_type WHEN '5' THEN a.emp_name END)
	 * employee from (select t_tasks.task_id, m_emp.emp_id, m_emp.emp_type,
	 * m_emp.emp_name from m_emp, t_tasks where
	 * FIND_IN_SET(m_emp.emp_id,t_tasks.task_emp_ids) and t_tasks.task_id IN (
	 * SELECT t_tasks.task_id FROM t_tasks WHERE t_tasks.task_status = 9 AND
	 * t_tasks.del_status = 1 AND t_tasks.is_active = 1 AND
	 * t_tasks.task_completion_date between '2020-03-01' and '2020-03-31' AND
	 * FIND_IN_SET(84,t_tasks.task_emp_ids) group by t_tasks.task_id ) ) a group by
	 * a.task_id) c ON b.task_id=c.task_id
	 */
}
