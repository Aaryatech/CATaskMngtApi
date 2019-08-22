package com.ats.cataskapi.model;

import java.util.List;

public class EmpListWithDateList {
	
	List<EmpListWithDateWiseDetail> empListWithDateWiseDetailLst;
	List<String> dateslist;
	public List<EmpListWithDateWiseDetail> getEmpListWithDateWiseDetailLst() {
		return empListWithDateWiseDetailLst;
	}
	public void setEmpListWithDateWiseDetailLst(List<EmpListWithDateWiseDetail> empListWithDateWiseDetailLst) {
		this.empListWithDateWiseDetailLst = empListWithDateWiseDetailLst;
	}
	public List<String> getDateslist() {
		return dateslist;
	}
	public void setDateslist(List<String> dateslist) {
		this.dateslist = dateslist;
	}
	@Override
	public String toString() {
		return "EmpListWithDateList [empListWithDateWiseDetailLst=" + empListWithDateWiseDetailLst + ", dateslist="
				+ dateslist + "]";
	}
	
	

}
