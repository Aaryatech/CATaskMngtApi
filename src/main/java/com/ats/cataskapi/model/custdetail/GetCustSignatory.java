package com.ats.cataskapi.model.custdetail;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GetCustSignatory {
	
	@Id
	private int signId;
	
	private String custFirmName;
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
	
	public int getSignId() {
		return signId;
	}
	public void setSignId(int signId) {
		this.signId = signId;
	}
	public String getCustFirmName() {
		return custFirmName;
	}
	public void setCustFirmName(String custFirmName) {
		this.custFirmName = custFirmName;
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
	
	@Override
	public String toString() {
		return "GetCustSignatory [signId=" + signId + ", custFirmName=" + custFirmName + ", signfName=" + signfName
				+ ", signlName=" + signlName + ", signRegNo=" + signRegNo + ", signDesign=" + signDesign + ", signPhno="
				+ signPhno + ", contactName=" + contactName + ", contactEmail=" + contactEmail + ", contactPhno="
				+ contactPhno + ", custRemark=" + custRemark + ", custId=" + custId + "]";
	}
	

	
	
	/*SELECT m_cust_header.cust_firm_name,m_cust_signatory.sign_f_name,m_cust_signatory.sign_l_name,
m_cust_signatory.sign_reg_no,m_cust_signatory.sign_design,m_cust_signatory.sign_phno,
m_cust_signatory.contact_name,m_cust_signatory.contact_email,m_cust_signatory.contact_phno,
m_cust_signatory.cust_id,m_cust_signatory.sign_id

FROM m_cust_signatory,m_cust_header 
WHERE 
m_cust_signatory.cust_id=m_cust_header.cust_id AND m_cust_signatory.del_status=1 AND m_cust_header.del_status=1
*/
	
	
	

}
