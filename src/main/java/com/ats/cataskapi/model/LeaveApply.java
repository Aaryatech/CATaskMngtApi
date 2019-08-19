package com.ats.cataskapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name = "t_leave_apply")
public class LeaveApply {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="leave_id")
	private int leaveId;
	
	@Column(name="cal_yr_id")
	private int calYrId;
	
	@Column(name="emp_id")
	private int empId;
	
	@Column(name="lv_type_id")
	private int lvTypeId;
	
	@Column(name="leave_duration")
	private String leaveDuration;
	
	@Column(name="leave_fromdt")
	private String leaveFromdt;
	
	@Column(name="leave_todt")
	private String leaveTodt ;
	
	@Column(name="leave_num_days")
	private float leaveNumDays;
	
	@Column(name="leave_emp_reason")
	private String leaveEmpReason;
	
	@Column(name="final_status")
	private int finalStatus;
	
	@Column(name="circulated_to")
	private String circulatedTo;
		
	@Column(name="del_status")
	private int delStatus;
	
	@Column(name="is_active")
	private int isActive;
	
	@Column(name="maker_user_id ")
	private int makerUserId ;
	
	@Column(name="maker_enter_datetime")
	private String makerEnterDatetime;
	
	@Column(name="ex_int1")
	private int exInt1;
	
	@Column(name="ex_int2")
	private int exInt2;
	
	@Column(name="ex_int3")
	private int exInt3;
	
	@Column(name="ex_var1")
	private String exVar1; 
	
	@Column(name="ex_var2")
	private String exVar2; 
	
	@Column(name="ex_var3")
	private String exVar3;
	
	@Transient
	private boolean error;


	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}

	public int getCalYrId() {
		return calYrId;
	}

	public void setCalYrId(int calYrId) {
		this.calYrId = calYrId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getLvTypeId() {
		return lvTypeId;
	}

	public void setLvTypeId(int lvTypeId) {
		this.lvTypeId = lvTypeId;
	}

	public String getLeaveDuration() {
		return leaveDuration;
	}

	public void setLeaveDuration(String leaveDuration) {
		this.leaveDuration = leaveDuration;
	}

	public String getLeaveFromdt() {
		return leaveFromdt;
	}

	public void setLeaveFromdt(String leaveFromdt) {
		this.leaveFromdt = leaveFromdt;
	}

	public String getLeaveTodt() {
		return leaveTodt;
	}

	public void setLeaveTodt(String leaveTodt) {
		this.leaveTodt = leaveTodt;
	}

	public float getLeaveNumDays() {
		return leaveNumDays;
	}

	public void setLeaveNumDays(float leaveNumDays) {
		this.leaveNumDays = leaveNumDays;
	}

	public String getLeaveEmpReason() {
		return leaveEmpReason;
	}

	public void setLeaveEmpReason(String leaveEmpReason) {
		this.leaveEmpReason = leaveEmpReason;
	}

	public int getFinalStatus() {
		return finalStatus;
	}

	public void setFinalStatus(int finalStatus) {
		this.finalStatus = finalStatus;
	}

	public String getCirculatedTo() {
		return circulatedTo;
	}

	public void setCirculatedTo(String circulatedTo) {
		this.circulatedTo = circulatedTo;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getMakerUserId() {
		return makerUserId;
	}

	public void setMakerUserId(int makerUserId) {
		this.makerUserId = makerUserId;
	}

	public String getMakerEnterDatetime() {
		return makerEnterDatetime;
	}

	public void setMakerEnterDatetime(String makerEnterDatetime) {
		this.makerEnterDatetime = makerEnterDatetime;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public int getExInt2() {
		return exInt2;
	}

	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}

	public int getExInt3() {
		return exInt3;
	}

	public void setExInt3(int exInt3) {
		this.exInt3 = exInt3;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getExVar2() {
		return exVar2;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}

	public String getExVar3() {
		return exVar3;
	}

	public void setExVar3(String exVar3) {
		this.exVar3 = exVar3;
	}

	@Override
	public String toString() {
		return "LeaveApply [leaveId=" + leaveId + ", calYrId=" + calYrId + ", empId=" + empId + ", lvTypeId=" + lvTypeId
				+ ", leaveDuration=" + leaveDuration + ", leaveFromdt=" + leaveFromdt + ", leaveTodt=" + leaveTodt
				+ ", leaveNumDays=" + leaveNumDays + ", leaveEmpReason=" + leaveEmpReason + ", finalStatus="
				+ finalStatus + ", circulatedTo=" + circulatedTo + ", delStatus=" + delStatus + ", isActive=" + isActive
				+ ", makerUserId=" + makerUserId + ", makerEnterDatetime=" + makerEnterDatetime + ", exInt1=" + exInt1
				+ ", exInt2=" + exInt2 + ", exInt3=" + exInt3 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ ", exVar3=" + exVar3 + "]";
	}
	
	

}
