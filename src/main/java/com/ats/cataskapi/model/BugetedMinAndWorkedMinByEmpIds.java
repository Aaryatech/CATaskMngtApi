package com.ats.cataskapi.model;
 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BugetedMinAndWorkedMinByEmpIds {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private String id; 
	private long allWork; 
	private long actWork;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getAllWork() {
		return allWork;
	}
	public void setAllWork(long allWork) {
		this.allWork = allWork;
	}
	public long getActWork() {
		return actWork;
	}
	public void setActWork(long actWork) {
		this.actWork = actWork;
	}
	@Override
	public String toString() {
		return "BugetedMinAndWorkedMinByEmpIds [id=" + id + ", allWork=" + allWork + ", actWork=" + actWork + "]";
	}
	
	

}
