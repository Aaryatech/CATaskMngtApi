package com.ats.cataskapi.model;

import java.util.List;

public class LeaveDetailWithFreeHours {

	private float totalFreeHours;
	private float totalBsyHours;
	private float totalAvailableHours;
	private List<EmployeeListWithAvailableHours> list;
	public float getTotalFreeHours() {
		return totalFreeHours;
	}
	public void setTotalFreeHours(float totalFreeHours) {
		this.totalFreeHours = totalFreeHours;
	}
	public float getTotalBsyHours() {
		return totalBsyHours;
	}
	public void setTotalBsyHours(float totalBsyHours) {
		this.totalBsyHours = totalBsyHours;
	}
	public List<EmployeeListWithAvailableHours> getList() {
		return list;
	}
	public void setList(List<EmployeeListWithAvailableHours> list) {
		this.list = list;
	}
	public float getTotalAvailableHours() {
		return totalAvailableHours;
	}
	public void setTotalAvailableHours(float totalAvailableHours) {
		this.totalAvailableHours = totalAvailableHours;
	}
	@Override
	public String toString() {
		return "LeaveDetailWithFreeHours [totalFreeHours=" + totalFreeHours + ", totalBsyHours=" + totalBsyHours
				+ ", totalAvailableHours=" + totalAvailableHours + ", list=" + list + "]";
	}
	 
	
	
}
