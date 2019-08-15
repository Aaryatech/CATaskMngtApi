package com.ats.cataskapi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "m_emp")
public class EmployeeMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int empId;
	private int empType;
	private String empName;
	private Date empDob;
	private int empRoleId;
	private String empMob;
	private String empEmail;
	private String empPass;
	private String empDesc;
	private String empPic;
	private int delStatus;
	private String updateDatetime;
	private int updateUsername;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;
	private String empSalary;
	
	
	public String getEmpSalary() {
		return empSalary;
	}
	public void setEmpSalary(String empSalary) {
		this.empSalary = empSalary;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getEmpType() {
		return empType;
	}
	public void setEmpType(int empType) {
		this.empType = empType;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	@JsonFormat(locale = "hi",timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getEmpDob() {
		return empDob;
	}
	public void setEmpDob(Date empDob) {
		this.empDob = empDob;
	}
	public int getEmpRoleId() {
		return empRoleId;
	}
	public void setEmpRoleId(int empRoleId) {
		this.empRoleId = empRoleId;
	}
	public String getEmpMob() {
		return empMob;
	}
	public void setEmpMob(String empMob) {
		this.empMob = empMob;
	}
	public String getEmpEmail() {
		return empEmail;
	}
	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}
	
	public String getEmpPass() {
		return empPass;
	}
	public void setEmpPass(String empPass) {
		this.empPass = empPass;
	}
	public String getEmpDesc() {
		return empDesc;
	}
	public void setEmpDesc(String empDesc) {
		this.empDesc = empDesc;
	}
	public String getEmpPic() {
		return empPic;
	}
	public void setEmpPic(String empPic) {
		this.empPic = empPic;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	public String getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	public int getUpdateUsername() {
		return updateUsername;
	}
	public void setUpdateUsername(int updateUsername) {
		this.updateUsername = updateUsername;
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
	@Override
	public String toString() {
		return "EmployeeMaster [empId=" + empId + ", empType=" + empType + ", empName=" + empName + ", empDob=" + empDob
				+ ", empRoleId=" + empRoleId + ", empMob=" + empMob + ", empEmail=" + empEmail + ", empPass=" + empPass
				+ ", empDesc=" + empDesc + ", empPic=" + empPic + ", delStatus=" + delStatus + ", updateDatetime="
				+ updateDatetime + ", updateUsername=" + updateUsername + ", exInt1=" + exInt1 + ", exInt2=" + exInt2
				+ ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", empSalary=" + empSalary + "]";
	}
		
}
