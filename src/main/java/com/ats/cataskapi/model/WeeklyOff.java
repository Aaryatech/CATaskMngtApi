package com.ats.cataskapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "weekly_off")
public class WeeklyOff {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "wo_id")
	private int woId;

	@Column(name = "company_id ")
	private int companyId;

	@Column(name = "wo_type")
	private String woType;

	@Column(name = "loc_id")
	private String locId;

	@Column(name = "wo_presently")
	private String woPresently;

	@Column(name = "wo_day")
	private String woDay;

	@Column(name = "wo_remarks")
	private String woRemarks;

	@Column(name = "wo_is_used")
	private int woIsUsed;

	@Column(name = "del_status")
	private int delStatus;

	@Column(name = "is_active")
	private int isActive;

	@Column(name = "maker_user_id ")
	private int makerUserId;

	@Column(name = "maker_enter_datetime")
	private String makerEnterDatetime;

	@Column(name = "ex_int1")
	private int exInt1;

	@Column(name = "ex_int2")
	private int exInt2;

	@Column(name = "ex_int3")
	private int exInt3;

	@Column(name = "ex_var1")
	private String exVar1;

	@Column(name = "ex_var2")
	private String exVar2;

	@Column(name = "ex_var3")
	private String exVar3;

	@Transient
	private boolean error;

	public int getWoId() {
		return woId;
	}

	public void setWoId(int woId) {
		this.woId = woId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getWoType() {
		return woType;
	}

	public void setWoType(String woType) {
		this.woType = woType;
	}

	public String getLocId() {
		return locId;
	}

	public void setLocId(String locId) {
		this.locId = locId;
	}

	public String getWoPresently() {
		return woPresently;
	}

	public void setWoPresently(String woPresently) {
		this.woPresently = woPresently;
	}

	public String getWoDay() {
		return woDay;
	}

	public void setWoDay(String woDay) {
		this.woDay = woDay;
	}

	public String getWoRemarks() {
		return woRemarks;
	}

	public void setWoRemarks(String woRemarks) {
		this.woRemarks = woRemarks;
	}

	public int getWoIsUsed() {
		return woIsUsed;
	}

	public void setWoIsUsed(int woIsUsed) {
		this.woIsUsed = woIsUsed;
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

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "WeeklyOff [woId=" + woId + ", companyId=" + companyId + ", woType=" + woType + ", locId=" + locId
				+ ", woPresently=" + woPresently + ", woDay=" + woDay + ", woRemarks=" + woRemarks + ", woIsUsed="
				+ woIsUsed + ", delStatus=" + delStatus + ", isActive=" + isActive + ", makerUserId=" + makerUserId
				+ ", makerEnterDatetime=" + makerEnterDatetime + ", exInt1=" + exInt1 + ", exInt2=" + exInt2
				+ ", exInt3=" + exInt3 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + ", error="
				+ error + "]";
	}

}
