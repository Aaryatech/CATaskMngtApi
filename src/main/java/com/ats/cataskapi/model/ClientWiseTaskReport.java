package com.ats.cataskapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class ClientWiseTaskReport {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="task_id")
	private int tasId;
	
	@Column(name="task_text")
	private String taskText;
	
	@Column(name="serv_name")
	private String servName;

	@Column(name="acti_name")
	private String actiName;
	
	@Column(name="periodicity_name")
	private String periodicityName;
	
	@Column(name="task_statutory_due_date")
	private String taskStatutoryDueDate;
	
	@Column(name="task_start_date")
	private String taskStartDate;
	
	@Column(name="task_end_date")
	private String taskEndDate;
	
	@Column(name="emp_bud_hr")
	private float empBudHr;
	
	@Column(name="mngr_bud_hr")
	private float mngrBudHr;
	
	@Column(name="cust_firm_name")
	private String custFirmName;
	
	@Column(name="owner_partner")
	private String ownerPartner;
	
	@Column(name="task_emp_ids")
	private String taskEmpIds;
	
	@Column(name="partner")
	private String partner;
	
	@Column(name="manager")
	private String manager;
	
	@Column(name="team_leader")
	private String teamLeader;
	
	@Column(name="employee")
	private String employee;
	  
	@Column(name="employee_ids")
	private String employeeIds;
	
	@Column(name="tl_ids")
	private String tlIds;
	
	@Column(name="manger_ids")
	private String mangerIds;
	
	@Column(name="partner_ids")
	private String partnerIds;
	
	@Transient
	String empBugetedHrs;
	
	@Transient
	float empBugetedCost;
	
	@Transient
	String empActualHrs;
	
	@Transient
	float empActualCost;
	
	@Transient
	String mngrBugetedHrs;
	
	@Transient
	float mngrBugetedCost;
	
	@Transient
	String mngrActualHrs;
	
	@Transient
	float mngrActualCost;
	
	@Transient
	String tlActualHrs;
	
	@Transient
	float tlActualCost;

	public int getTasId() {
		return tasId;
	}

	public void setTasId(int tasId) {
		this.tasId = tasId;
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

	public String getTaskStatutoryDueDate() {
		return taskStatutoryDueDate;
	}

	public void setTaskStatutoryDueDate(String taskStatutoryDueDate) {
		this.taskStatutoryDueDate = taskStatutoryDueDate;
	}

	public String getTaskStartDate() {
		return taskStartDate;
	}

	public void setTaskStartDate(String taskStartDate) {
		this.taskStartDate = taskStartDate;
	}

	public String getTaskEndDate() {
		return taskEndDate;
	}

	public void setTaskEndDate(String taskEndDate) {
		this.taskEndDate = taskEndDate;
	}

	public float getEmpBudHr() {
		return empBudHr;
	}

	public void setEmpBudHr(float empBudHr) {
		this.empBudHr = empBudHr;
	}

	public float getMngrBudHr() {
		return mngrBudHr;
	}

	public void setMngrBudHr(float mngrBudHr) {
		this.mngrBudHr = mngrBudHr;
	}

	public String getCustFirmName() {
		return custFirmName;
	}

	public void setCustFirmName(String custFirmName) {
		this.custFirmName = custFirmName;
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

	public String getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(String employeeIds) {
		this.employeeIds = employeeIds;
	}

	public String getTlIds() {
		return tlIds;
	}

	public void setTlIds(String tlIds) {
		this.tlIds = tlIds;
	}

	public String getMangerIds() {
		return mangerIds;
	}

	public void setMangerIds(String mangerIds) {
		this.mangerIds = mangerIds;
	}

	public String getPartnerIds() {
		return partnerIds;
	}

	public void setPartnerIds(String partnerIds) {
		this.partnerIds = partnerIds;
	}

	public String getOwnerPartner() {
		return ownerPartner;
	}

	public void setOwnerPartner(String ownerPartner) {
		this.ownerPartner = ownerPartner;
	}

	public String getTaskEmpIds() {
		return taskEmpIds;
	}

	public void setTaskEmpIds(String taskEmpIds) {
		this.taskEmpIds = taskEmpIds;
	}

	public String getEmpBugetedHrs() {
		return empBugetedHrs;
	}

	public void setEmpBugetedHrs(String empBugetedHrs) {
		this.empBugetedHrs = empBugetedHrs;
	}

	public float getEmpBugetedCost() {
		return empBugetedCost;
	}

	public void setEmpBugetedCost(float empBugetedCost) {
		this.empBugetedCost = empBugetedCost;
	}

	public String getEmpActualHrs() {
		return empActualHrs;
	}

	public void setEmpActualHrs(String empActualHrs) {
		this.empActualHrs = empActualHrs;
	}

	public float getEmpActualCost() {
		return empActualCost;
	}

	public void setEmpActualCost(float empActualCost) {
		this.empActualCost = empActualCost;
	}

	public String getMngrBugetedHrs() {
		return mngrBugetedHrs;
	}

	public void setMngrBugetedHrs(String mngrBugetedHrs) {
		this.mngrBugetedHrs = mngrBugetedHrs;
	}

	public float getMngrBugetedCost() {
		return mngrBugetedCost;
	}

	public void setMngrBugetedCost(float mngrBugetedCost) {
		this.mngrBugetedCost = mngrBugetedCost;
	}

	public String getMngrActualHrs() {
		return mngrActualHrs;
	}

	public void setMngrActualHrs(String mngrActualHrs) {
		this.mngrActualHrs = mngrActualHrs;
	}

	public float getMngrActualCost() {
		return mngrActualCost;
	}

	public void setMngrActualCost(float mngrActualCost) {
		this.mngrActualCost = mngrActualCost;
	}

	public String getTlActualHrs() {
		return tlActualHrs;
	}

	public void setTlActualHrs(String tlActualHrs) {
		this.tlActualHrs = tlActualHrs;
	}

	public float getTlActualCost() {
		return tlActualCost;
	}

	public void setTlActualCost(float tlActualCost) {
		this.tlActualCost = tlActualCost;
	}

	@Override
	public String toString() {
		return "ClientWiseTaskReport [tasId=" + tasId + ", taskText=" + taskText + ", servName=" + servName
				+ ", actiName=" + actiName + ", periodicityName=" + periodicityName + ", taskStatutoryDueDate="
				+ taskStatutoryDueDate + ", taskStartDate=" + taskStartDate + ", taskEndDate=" + taskEndDate
				+ ", empBudHr=" + empBudHr + ", mngrBudHr=" + mngrBudHr + ", custFirmName=" + custFirmName
				+ ", ownerPartner=" + ownerPartner + ", taskEmpIds=" + taskEmpIds + ", partner=" + partner
				+ ", manager=" + manager + ", teamLeader=" + teamLeader + ", employee=" + employee + ", employeeIds="
				+ employeeIds + ", tlIds=" + tlIds + ", mangerIds=" + mangerIds + ", partnerIds=" + partnerIds
				+ ", empBugetedHrs=" + empBugetedHrs + ", empBugetedCost=" + empBugetedCost + ", empActualHrs="
				+ empActualHrs + ", empActualCost=" + empActualCost + ", mngrBugetedHrs=" + mngrBugetedHrs
				+ ", mngrBugetedCost=" + mngrBugetedCost + ", mngrActualHrs=" + mngrActualHrs + ", mngrActualCost="
				+ mngrActualCost + ", tlActualHrs=" + tlActualHrs + ", tlActualCost=" + tlActualCost + "]";
	}
	
	
	
	
	

}
