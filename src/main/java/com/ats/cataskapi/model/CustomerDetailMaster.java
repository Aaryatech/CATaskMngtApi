package com.ats.cataskapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "m_cust_detail")
public class CustomerDetailMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
		private int custDetailId;
		private int custId;
		private String actvId;
		private String loginId;
		private String loginPass;
		private String loginQue1;
		private String loginAns1;
		private String loginQue2;
		private String loginAns2;
		private String loginRemark;
		
		private String signfName;
		private String signlName;
		private String signRegNo;
		private String signDesign;
		private String signPhno;
		private String contactName;
		private String contactEmail;
		private String contactPhno;		
		private String custRemark;
		
		private int delStatus;
		private String updateDatetime;
		private int updateUsername;
		private int exInt1;	
		private int exInt2;
		private String exVar1;
		private String exVar2;
		public int getCustDetailId() {
			return custDetailId;
		}
		public void setCustDetailId(int custDetailId) {
			this.custDetailId = custDetailId;
		}
		public int getCustId() {
			return custId;
		}
		public void setCustId(int custId) {
			this.custId = custId;
		}
		public String getActvId() {
			return actvId;
		}
		public void setActvId(String actvId) {
			this.actvId = actvId;
		}
		public String getLoginId() {
			return loginId;
		}
		public void setLoginId(String loginId) {
			this.loginId = loginId;
		}
		public String getLoginPass() {
			return loginPass;
		}
		public void setLoginPass(String loginPass) {
			this.loginPass = loginPass;
		}
		public String getLoginQue1() {
			return loginQue1;
		}
		public void setLoginQue1(String loginQue1) {
			this.loginQue1 = loginQue1;
		}
		public String getLoginAns1() {
			return loginAns1;
		}
		public void setLoginAns1(String loginAns1) {
			this.loginAns1 = loginAns1;
		}
		public String getLoginQue2() {
			return loginQue2;
		}
		public void setLoginQue2(String loginQue2) {
			this.loginQue2 = loginQue2;
		}
		public String getLoginAns2() {
			return loginAns2;
		}
		public void setLoginAns2(String loginAns2) {
			this.loginAns2 = loginAns2;
		}
		public String getLoginRemark() {
			return loginRemark;
		}
		public void setLoginRemark(String loginRemark) {
			this.loginRemark = loginRemark;
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
			return "CustomerDetailMaster [custDetailId=" + custDetailId + ", custId=" + custId + ", actvId=" + actvId
					+ ", loginId=" + loginId + ", loginPass=" + loginPass + ", loginQue1=" + loginQue1 + ", loginAns1="
					+ loginAns1 + ", loginQue2=" + loginQue2 + ", loginAns2=" + loginAns2 + ", loginRemark="
					+ loginRemark + ", signfName=" + signfName + ", signlName=" + signlName + ", signRegNo=" + signRegNo
					+ ", signDesign=" + signDesign + ", signPhno=" + signPhno + ", contactName=" + contactName
					+ ", contactEmail=" + contactEmail + ", contactPhno=" + contactPhno + ", custRemark=" + custRemark
					+ ", delStatus=" + delStatus + ", updateDatetime=" + updateDatetime + ", updateUsername="
					+ updateUsername + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2="
					+ exVar2 + "]";
		}
		
}
