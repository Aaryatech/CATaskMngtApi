package com.ats.cataskapi.model.custdetail;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GetCustLoginDetail {

	@Id
	private int custDetailId;

	private int custId;

	private String custFirmName;
	private String servName;
	private String actiName;
	private String loginId;

	private String loginPass;
	private String loginQue1;
	private String loginAns1;
	private String loginQue2;
	private String loginAns2;
	private String loginRemark;
	
	/***/
	
	private String signfName;
	private String signlName;
	private String signRegNo;
	private String signDesign;
	private String signPhno;
	private String contactName;
	private String contactEmail;
	private String contactPhno;		
	private String custRemark;
	
	/***/

	private int actvId;
	private int servId;

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

	public String getCustFirmName() {
		return custFirmName;
	}

	public void setCustFirmName(String custFirmName) {
		this.custFirmName = custFirmName;
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

	public int getActvId() {
		return actvId;
	}

	public void setActvId(int actvId) {
		this.actvId = actvId;
	}

	public int getServId() {
		return servId;
	}

	public void setServId(int servId) {
		this.servId = servId;
	}

	@Override
	public String toString() {
		return "GetCustLoginDetail [custDetailId=" + custDetailId + ", custId=" + custId + ", custFirmName="
				+ custFirmName + ", servName=" + servName + ", actiName=" + actiName + ", loginId=" + loginId
				+ ", loginPass=" + loginPass + ", loginQue1=" + loginQue1 + ", loginAns1=" + loginAns1 + ", loginQue2="
				+ loginQue2 + ", loginAns2=" + loginAns2 + ", loginRemark=" + loginRemark + ", signfName=" + signfName
				+ ", signlName=" + signlName + ", signRegNo=" + signRegNo + ", signDesign=" + signDesign + ", signPhno="
				+ signPhno + ", contactName=" + contactName + ", contactEmail=" + contactEmail + ", contactPhno="
				+ contactPhno + ", custRemark=" + custRemark + ", actvId=" + actvId + ", servId=" + servId + "]";
	}

	

	/*
	 * SELECT CASE WHEN m_cust_header.cust_group_id=0 THEN
	 * m_cust_header.cust_firm_name ELSE COALESCE(( SELECT
	 * m_cust_group.cust_group_name FROM m_cust_group WHERE
	 * m_cust_group.cust_group_id=m_cust_header.cust_group_id AND
	 * m_cust_header.cust_id=1 AND m_cust_group.del_status=1 LIMIT 1),0) END AS
	 * cust_firm_name,
	 * 
	 * m_services.serv_name,m_activities.acti_name,m_cust_detail.login_id,
	 * m_cust_detail.login_pass,
	 * m_cust_detail.login_que1,m_cust_detail.login_ans1,m_cust_detail.login_que2,
	 * m_cust_detail.login_ans2,m_cust_detail.login_remark,
	 * m_cust_detail.cust_detail_id,m_cust_detail.cust_id
	 * 
	 * FROM m_cust_detail,m_cust_header,m_activities,m_services
	 * 
	 * WHERE m_cust_detail.cust_id=m_cust_header.cust_id AND
	 * m_cust_detail.actv_id=m_activities.acti_id and
	 * m_activities.serv_id=m_services.serv_id AND m_cust_detail.del_status=1 AND
	 * m_cust_header.del_status=1 AND m_activities.del_status=1 AND
	 * m_services.del_status=1 AND m_cust_detail.cust_id=1 ORDER BY
	 * m_cust_detail.cust_detail_id DESC
	 */

}
