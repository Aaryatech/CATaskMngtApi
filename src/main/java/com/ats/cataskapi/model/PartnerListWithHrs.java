package com.ats.cataskapi.model;

public class PartnerListWithHrs {
	
	private int partnerId;
	private String partnerName; 
	private String totalHrs;
	public int getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getTotalHrs() {
		return totalHrs;
	}
	public void setTotalHrs(String totalHrs) {
		this.totalHrs = totalHrs;
	}
	@Override
	public String toString() {
		return "PartnerListWithHrs [partnerId=" + partnerId + ", partnerName=" + partnerName + ", totalHrs=" + totalHrs
				+ "]";
	}
	
	

}
