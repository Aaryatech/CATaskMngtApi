package com.ats.cataskapi.task.model;

import java.util.List;

import com.ats.cataskapi.model.CustmrActivityMap;

 
public class TempTaskSave {
	
	List<Task> tskList; 
	CustmrActivityMap cmpList;
	public List<Task> getTskList() {
		return tskList;
	}
	public void setTskList(List<Task> tskList) {
		this.tskList = tskList;
	}
	public CustmrActivityMap getCmpList() {
		return cmpList;
	}
	public void setCmpList(CustmrActivityMap cmpList) {
		this.cmpList = cmpList;
	}
	@Override
	public String toString() {
		return "TempTaskSave [tskList=" + tskList + ", cmpList=" + cmpList + ", getTskList()=" + getTskList()
				+ ", getCmpList()=" + getCmpList() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	
	
	
	
	
	

}
