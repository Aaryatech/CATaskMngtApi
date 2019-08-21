package com.ats.cataskapi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "m_cust_header")
public class CustomerHeaderMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int custId;
	private int ownerEmpId;
	 
	private int custType;
	private int custGroupId;
	private String custFirmName;
	private int custAssesseeTypeId;
	private String custAssesseeName;
	private String custPanNo;
	private String custEmailId;
	private String custPhoneNo;
	private String custAddr1;
	private String custAddr2;
	private String custCity;
	private int custPinCode;
	private String custBusinNatute;
	private int custIsDscAvail;
	private String custFolderId;
	private String custFileNo;
	private Date custDob;
	private String custAadhar;
	private int delStatus;
	private int isActive;
	private String updateDatetime;
	private int updateUsername;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;
	
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	
	public int getOwnerEmpId() {
		return ownerEmpId;
	}
	public void setOwnerEmpId(int ownerEmpId) {
		this.ownerEmpId = ownerEmpId;
	}
	 
	public int getCustType() {
		return custType;
	}
	public void setCustType(int custType) {
		this.custType = custType;
	}
	public int getCustGroupId() {
		return custGroupId;
	}
	public void setCustGroupId(int custGroupId) {
		this.custGroupId = custGroupId;
	}
	public String getCustFirmName() {
		return custFirmName;
	}
	public void setCustFirmName(String custFirmName) {
		this.custFirmName = custFirmName;
	}
	public int getCustAssesseeTypeId() {
		return custAssesseeTypeId;
	}
	public void setCustAssesseeTypeId(int custAssesseeTypeId) {
		this.custAssesseeTypeId = custAssesseeTypeId;
	}
	public String getCustAssesseeName() {
		return custAssesseeName;
	}
	public void setCustAssesseeName(String custAssesseeName) {
		this.custAssesseeName = custAssesseeName;
	}
	public String getCustPanNo() {
		return custPanNo;
	}
	public void setCustPanNo(String custPanNo) {
		this.custPanNo = custPanNo;
	}
	public String getCustEmailId() {
		return custEmailId;
	}
	public void setCustEmailId(String custEmailId) {
		this.custEmailId = custEmailId;
	}
	public String getCustPhoneNo() {
		return custPhoneNo;
	}
	public void setCustPhoneNo(String custPhoneNo) {
		this.custPhoneNo = custPhoneNo;
	}
	public String getCustAddr1() {
		return custAddr1;
	}
	public void setCustAddr1(String custAddr1) {
		this.custAddr1 = custAddr1;
	}
	public String getCustAddr2() {
		return custAddr2;
	}
	public void setCustAddr2(String custAddr2) {
		this.custAddr2 = custAddr2;
	}
	public String getCustCity() {
		return custCity;
	}
	public void setCustCity(String custCity) {
		this.custCity = custCity;
	}
	public int getCustPinCode() {
		return custPinCode;
	}
	public void setCustPinCode(int custPinCode) {
		this.custPinCode = custPinCode;
	}
	public String getCustBusinNatute() {
		return custBusinNatute;
	}
	public void setCustBusinNatute(String custBusinNatute) {
		this.custBusinNatute = custBusinNatute;
	}
	public int getCustIsDscAvail() {
		return custIsDscAvail;
	}
	public void setCustIsDscAvail(int custIsDscAvail) {
		this.custIsDscAvail = custIsDscAvail;
	}
	public String getCustFolderId() {
		return custFolderId;
	}
	public void setCustFolderId(String custFolderId) {
		this.custFolderId = custFolderId;
	}
	public String getCustFileNo() {
		return custFileNo;
	}
	public void setCustFileNo(String custFileNo) {
		this.custFileNo = custFileNo;
	}
	@JsonFormat(locale = "hi",timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getCustDob() {
		return custDob;
	}
	public void setCustDob(Date custDob) {
		this.custDob = custDob;
	}
	public String getCustAadhar() {
		return custAadhar;
	}
	public void setCustAadhar(String custAadhar) {
		this.custAadhar = custAadhar;
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
		return "CustomerHeaderMaster [custId=" + custId + ", ownerEmpId=" + ownerEmpId + ", custType=" + custType
				+ ", custGroupId=" + custGroupId + ", custFirmName=" + custFirmName + ", custAssesseeTypeId="
				+ custAssesseeTypeId + ", custAssesseeName=" + custAssesseeName + ", custPanNo=" + custPanNo
				+ ", custEmailId=" + custEmailId + ", custPhoneNo=" + custPhoneNo + ", custAddr1=" + custAddr1
				+ ", custAddr2=" + custAddr2 + ", custCity=" + custCity + ", custPinCode=" + custPinCode
				+ ", custBusinNatute=" + custBusinNatute + ", custIsDscAvail=" + custIsDscAvail + ", custFolderId="
				+ custFolderId + ", custFileNo=" + custFileNo + ", custDob=" + custDob + ", custAadhar=" + custAadhar
				+ ", delStatus=" + delStatus + ", isActive=" + isActive + ", updateDatetime=" + updateDatetime
				+ ", updateUsername=" + updateUsername + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1="
				+ exVar1 + ", exVar2=" + exVar2 + "]";
	}
	 
}
