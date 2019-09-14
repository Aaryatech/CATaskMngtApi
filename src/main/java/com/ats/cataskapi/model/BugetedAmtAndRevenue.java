package com.ats.cataskapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class BugetedAmtAndRevenue {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="bugeted_hrs")
	private float bugetedHrs;
	
	@Column(name="actual_hrs")
	private float actualHrs;
	
	@Column(name="bugeted_rev")
	private float bugetedRev;
	
	@Column(name="actul_rev")
	private float actulRev ;

	@Transient
	float bugetedCost;
	
	@Transient
	float actualCost;
	
	public float getBugetedHrs() {
		return bugetedHrs;
	}

	public void setBugetedHrs(float bugetedHrs) {
		this.bugetedHrs = bugetedHrs;
	}

	public float getActualHrs() {
		return actualHrs;
	}

	public void setActualHrs(float actualHrs) {
		this.actualHrs = actualHrs;
	}

	public float getBugetedRev() {
		return bugetedRev;
	}

	public void setBugetedRev(float bugetedRev) {
		this.bugetedRev = bugetedRev;
	}

	public float getActulRev() {
		return actulRev;
	}

	public void setActulRev(float actulRev) {
		this.actulRev = actulRev;
	}

	public float getBugetedCost() {
		return bugetedCost;
	}

	public void setBugetedCost(float bugetedCost) {
		this.bugetedCost = bugetedCost;
	}

	public float getActualCost() {
		return actualCost;
	}

	public void setActualCost(float actualCost) {
		this.actualCost = actualCost;
	}

	@Override
	public String toString() {
		return "BugetedAmtAndRevenue [bugetedHrs=" + bugetedHrs + ", actualHrs=" + actualHrs + ", bugetedRev="
				+ bugetedRev + ", actulRev=" + actulRev + ", bugetedCost=" + bugetedCost + ", actualCost=" + actualCost
				+ "]";
	}

	 
	
	

}
