package com.ats.cataskapi.model.reportv2;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class OverDueTaskReport { //Same Bean used for emp work diary report.

	@Id
	private String uniqueId; //for Work diary query unique id will be work log id
	
	private int taskId;
	
	private String taskText;
	
	private String  servName;
	
	private String  actiName;
	
	private String  periodicityName;
 	
	private Date  taskStatutoryDueDate;
	
	private Date  taskCompletionDate;
	
	private Date taskWorkDate; //ie taskEndDate in Db Table
	
	private String empBudHr;
	
	private String  mngrBudHr;
	
	private String  custFirmName;
 	
	private String  workHours; //ie total hours worked by selected Emp
	
	private String  admin; //comma sep names
	
	private String  partner;//comma sep names
	
	private String  manager;//comma sep names
	
	private String  teamLeader;//comma sep names
	
 	private String  employee;//comma sep names
	private String  ownPartner; //m_cust Header ownEmpId name
	private String  statusText; //m_cust Header ownEmpId name
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTaskText() {
		return taskText;
	}
	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}
	public String getServName() {
		return servName;
	}
	public void setServName(String servName) {
		this.servName = servName;
	}
	public String getActiName() {
		return actiName;
	}
	public void setActiName(String actiName) {
		this.actiName = actiName;
	}
	public String getPeriodicityName() {
		return periodicityName;
	}
	public void setPeriodicityName(String periodicityName) {
		this.periodicityName = periodicityName;
	}
	@JsonFormat(locale = "hi",timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getTaskStatutoryDueDate() {
		return taskStatutoryDueDate;
	}
	public void setTaskStatutoryDueDate(Date taskStatutoryDueDate) {
		this.taskStatutoryDueDate = taskStatutoryDueDate;
	}
	@JsonFormat(locale = "hi",timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getTaskCompletionDate() {
		return taskCompletionDate;
	}
	public void setTaskCompletionDate(Date taskCompletionDate) {
		this.taskCompletionDate = taskCompletionDate;
	}
	@JsonFormat(locale = "hi",timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getTaskWorkDate() {
		return taskWorkDate;
	}
	public void setTaskWorkDate(Date taskWorkDate) {
		this.taskWorkDate = taskWorkDate;
	}
	public String getEmpBudHr() {
		return empBudHr;
	}
	public void setEmpBudHr(String empBudHr) {
		this.empBudHr = empBudHr;
	}
	public String getMngrBudHr() {
		return mngrBudHr;
	}
	public void setMngrBudHr(String mngrBudHr) {
		this.mngrBudHr = mngrBudHr;
	}
	public String getCustFirmName() {
		return custFirmName;
	}
	public void setCustFirmName(String custFirmName) {
		this.custFirmName = custFirmName;
	}
	public String getWorkHours() {
		return workHours;
	}
	public void setWorkHours(String workHours) {
		this.workHours = workHours;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getTeamLeader() {
		return teamLeader;
	}
	public void setTeamLeader(String teamLeader) {
		this.teamLeader = teamLeader;
	}
	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public String getOwnPartner() {
		return ownPartner;
	}
	public void setOwnPartner(String ownPartner) {
		this.ownPartner = ownPartner;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	@Override
	public String toString() {
		return "OverDueTaskReport [uniqueId=" + uniqueId + ", taskId=" + taskId + ", taskText=" + taskText
				+ ", servName=" + servName + ", actiName=" + actiName + ", periodicityName=" + periodicityName
				+ ", taskStatutoryDueDate=" + taskStatutoryDueDate + ", taskCompletionDate=" + taskCompletionDate
				+ ", taskWorkDate=" + taskWorkDate + ", empBudHr=" + empBudHr + ", mngrBudHr=" + mngrBudHr
				+ ", custFirmName=" + custFirmName + ", workHours=" + workHours + ", admin=" + admin + ", partner="
				+ partner + ", manager=" + manager + ", teamLeader=" + teamLeader + ", employee=" + employee
				+ ", ownPartner=" + ownPartner + ", statusText=" + statusText + "]";
	}

	
	
	
	/*
	 SELECT
        b.*,
        
        coalesce((c.admin),
        '-')as admin,
        coalesce((c.partner),
        '-') as partner ,
        coalesce((c.manager),
        '-') as manager,
        coalesce((c.team_leader),
        '-') as team_leader,
        coalesce((c.employee),
        '-') as  employee 
    from
        (SELECT
        UUID() as unique_id,
            t_tasks.task_id,
            t_tasks.task_text,
        
            m_services.serv_name,
            m_activities.acti_name,
            dm_periodicity.periodicity_name,
            t_tasks.task_statutory_due_date,
            t_tasks.task_completion_date ,
            t_tasks.task_end_date AS task_work_date,
         
            CONCAT(FLOOR( t_tasks.emp_bud_hr/60),
            ':',
            LPAD(MOD(t_tasks.emp_bud_hr,
            60),
            2,
            '0'))  as emp_bud_hr,
            CONCAT(FLOOR( t_tasks.mngr_bud_hr/60),
            ':',
            LPAD(MOD(t_tasks.mngr_bud_hr,
            60),
            2,
            '0')) as mngr_bud_hr,
        0 as work_hours,
            m_cust_header.cust_firm_name,m_emp.emp_name as own_partner,
         dm_status_mst.status_text
        FROM
            m_services,
            m_activities,
            dm_periodicity,
            t_tasks,
            m_cust_header,
         dm_status_mst,
         m_emp
        WHERE
            m_services.serv_id = t_tasks.serv_id 
            AND m_activities.acti_id = t_tasks.actv_id 
            AND dm_periodicity.periodicity_id = t_tasks.periodicity_id 
            AND t_tasks.task_status != 9 
            AND t_tasks.del_status = 1 
            AND t_tasks.is_active = 1  
            AND   t_tasks.cust_id = m_cust_header.cust_id      
            AND  t_tasks.task_statutory_due_date between '2020-03-01' and '2020-09-15'
         and dm_status_mst.status_value=t_tasks.task_status 
            AND FIND_IN_SET(1,t_tasks.task_emp_ids)  
         and m_emp.emp_id=m_cust_header.owner_emp_id
        group by
            t_tasks.task_id) b   
  
    LEFT JOIN
        (
            select
                a.task_id,
                group_concat(CASE a.emp_type 
                    WHEN '1' THEN a.emp_name  
                END) admin,
                group_concat(CASE a.emp_type 
                    WHEN '2' THEN a.emp_name  
                END) partner,
                group_concat(CASE a.emp_type 
                    WHEN '3' THEN a.emp_name  
                END) manager,
                group_concat(CASE a.emp_type 
                    WHEN '4' THEN a.emp_name 
                END) team_leader,
                group_concat(CASE a.emp_type 
                    WHEN '5' THEN a.emp_name 
                END) employee   
            from
                (select
                    t_tasks.task_id,
                    m_emp.emp_id,
                    m_emp.emp_type,
                    m_emp.emp_name 
                from
                    m_emp,
                    t_tasks 
                where
                    FIND_IN_SET(m_emp.emp_id,t_tasks.task_emp_ids) 
                    and t_tasks.task_id IN  (
                        SELECT
                            t_tasks.task_id     
                        FROM
                            t_tasks   
                        WHERE
                            t_tasks.task_status != 9 
                            AND t_tasks.del_status = 1 
                            AND t_tasks.is_active = 1 
                            AND  t_tasks.task_statutory_due_date between  '2020-03-01' and '2020-09-15'
                            AND FIND_IN_SET(1,t_tasks.task_emp_ids) 
                        group by
                            t_tasks.task_id     
                    )     
                ) a 
            group by
                a.task_id) c 
                ON b.task_id=c.task_id
	 */
	
	/* Emp Diary
	 
	 SELECT
        b.*,
        
        coalesce((c.admin),
        '-')as admin,
        coalesce((c.partner),
        '-') as partner ,
        coalesce((c.manager),
        '-') as manager,
        coalesce((c.team_leader),
        '-') as team_leader,
        coalesce((c.employee),
        '-') as  employee 
    from
        (SELECT
         t_daily_work_log.work_log_id as unique_id,
            t_tasks.task_id,
            t_tasks.task_text,
        
            m_services.serv_name,
            m_activities.acti_name,
            dm_periodicity.periodicity_name,
            t_tasks.task_statutory_due_date,
            t_tasks.task_completion_date ,
            t_tasks.task_end_date AS task_work_date,
         0 as emp_bud_hr,
	 0 as mngr_bud_hr,

        
         
           COALESCE( (CONCAT(         FLOOR(             SUM(t_daily_work_log.work_hours) / 60         ),
                '.',
                LPAD(  MOD(             SUM(t_daily_work_log.work_hours),
                60),
                2,
                '0')                  )),
                0) AS work_hours,
         
         
            m_cust_header.cust_firm_name,m_emp.emp_name as own_partner,
         dm_status_mst.status_text
        FROM
            m_services,
            m_activities,
            dm_periodicity,
            t_tasks,
            m_cust_header,
         dm_status_mst,
         m_emp,t_daily_work_log
        WHERE
            m_services.serv_id = t_tasks.serv_id 
            AND m_activities.acti_id = t_tasks.actv_id 
            AND dm_periodicity.periodicity_id = t_tasks.periodicity_id 
            AND t_tasks.del_status = 1 
            AND t_tasks.is_active = 1  
            AND   t_tasks.cust_id = m_cust_header.cust_id      
            AND  t_daily_work_log.work_date between '2020-03-01' and '2020-09-15'
         and t_daily_work_log.task_id=t_tasks.task_id
         and t_daily_work_log.emp_id=1
         and dm_status_mst.status_value=t_tasks.task_status 
            AND FIND_IN_SET(1,t_tasks.task_emp_ids)  
         and m_emp.emp_id=m_cust_header.owner_emp_id
        GROUP by t_daily_work_log.work_log_id ) b   
  
    LEFT JOIN
        (
            select
                a.task_id,
                group_concat(CASE a.emp_type 
                    WHEN '1' THEN a.emp_name  
                END) admin,
                group_concat(CASE a.emp_type 
                    WHEN '2' THEN a.emp_name  
                END) partner,
                group_concat(CASE a.emp_type 
                    WHEN '3' THEN a.emp_name  
                END) manager,
                group_concat(CASE a.emp_type 
                    WHEN '4' THEN a.emp_name 
                END) team_leader,
                group_concat(CASE a.emp_type 
                    WHEN '5' THEN a.emp_name 
                END) employee   
            from
                (select
                    t_tasks.task_id,
                    m_emp.emp_id,
                    m_emp.emp_type,
                    m_emp.emp_name 
                from
                    m_emp,
                    t_tasks 
                where
                    FIND_IN_SET(m_emp.emp_id,t_tasks.task_emp_ids) 
                    and t_tasks.task_id IN  (
                        SELECT
                            t_tasks.task_id     
                        FROM
                            t_tasks   
                        WHERE
                             t_tasks.del_status = 1 
                            AND t_tasks.is_active = 1 
                            AND FIND_IN_SET(1,t_tasks.task_emp_ids) 
                        group by
                            t_tasks.task_id     
                    )     
                ) a 
            group by
                a.task_id) c 
                ON b.task_id=c.task_id
	 */
}
