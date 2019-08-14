package com.ats.cataskapi.model.custdetail;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="m_cust_signatory")
public class CustSignatoryMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int signId;
	
	
	private String signfName;
	private String signlName;
	private String signRegNo;
	private String signDesign;
	private String signPhno;
	private String contactName;
	private String contactEmail;
	private String contactPhno;
	
	private String custRemark;
	
	private int custId;
	
	private int delStatus;
	private String updateDatetime;
	private int updateUsername;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;
	public int getSignId() {
		return signId;
	}
	public void setSignId(int signId) {
		this.signId = signId;
	}
	public String getSignfName() {
		return signfName;
	}
	public void setSignfName(String signfName) {
		this.signfName = signfName;
	}
	public String getSignlName() {
		return signlName;
	}
	public void setSignlName(String signlName) {
		this.signlName = signlName;
	}
	public String getSignRegNo() {
		return signRegNo;
	}
	public void setSignRegNo(String signRegNo) {
		this.signRegNo = signRegNo;
	}
	public String getSignDesign() {
		return signDesign;
	}
	public void setSignDesign(String signDesign) {
		this.signDesign = signDesign;
	}
	public String getSignPhno() {
		return signPhno;
	}
	public void setSignPhno(String signPhno) {
		this.signPhno = signPhno;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getContactPhno() {
		return contactPhno;
	}
	public void setContactPhno(String contactPhno) {
		this.contactPhno = contactPhno;
	}
	public String getCustRemark() {
		return custRemark;
	}
	public void setCustRemark(String custRemark) {
		this.custRemark = custRemark;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
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
		return "CustSignatoryMaster [signId=" + signId + ", signfName=" + signfName + ", signlName=" + signlName
				+ ", signRegNo=" + signRegNo + ", signDesign=" + signDesign + ", signPhno=" + signPhno
				+ ", contactName=" + contactName + ", contactEmail=" + contactEmail + ", contactPhno=" + contactPhno
				+ ", custRemark=" + custRemark + ", custId=" + custId + ", delStatus=" + delStatus + ", updateDatetime="
				+ updateDatetime + ", updateUsername=" + updateUsername + ", exInt1=" + exInt1 + ", exInt2=" + exInt2
				+ ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
	}

}
