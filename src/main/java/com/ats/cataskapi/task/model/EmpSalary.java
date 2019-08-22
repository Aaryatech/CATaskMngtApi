package com.ats.cataskapi.task.model;


import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
 
@Entity
@Table(name = "t_emp_salary")
public class EmpSalary {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private int salaryId;

	private int finYearId;

	private int empId;

	private float Jan;

	private float Feb;

	private float Mar;

	private float Apr;

	private float May;

	private float Jun;

	private float Jul;

	private float Aug;

	private float Sep;

	private float Oct;

	private float Nov;

	private float Dece;

	private int updateUsername;

	private String updateDatetime;

	public float getJan() {
		return Jan;
	}

	public void setJan(float jan) {
		Jan = jan;
	}

	public float getFeb() {
		return Feb;
	}

	public void setFeb(float feb) {
		Feb = feb;
	}

	public float getMar() {
		return Mar;
	}

	public void setMar(float mar) {
		Mar = mar;
	}

	public float getApr() {
		return Apr;
	}

	public void setApr(float apr) {
		Apr = apr;
	}

	public float getMay() {
		return May;
	}

	public void setMay(float may) {
		May = may;
	}

	public float getJun() {
		return Jun;
	}

	public void setJun(float jun) {
		Jun = jun;
	}

	public float getJul() {
		return Jul;
	}

	public void setJul(float jul) {
		Jul = jul;
	}

	public float getAug() {
		return Aug;
	}

	public void setAug(float aug) {
		Aug = aug;
	}

	public float getSep() {
		return Sep;
	}

	public void setSep(float sep) {
		Sep = sep;
	}

	public float getOct() {
		return Oct;
	}

	public void setOct(float oct) {
		Oct = oct;
	}

	public float getNov() {
		return Nov;
	}

	public void setNov(float nov) {
		Nov = nov;
	}

	public float getDece() {
		return Dece;
	}

	public void setDece(float dece) {
		Dece = dece;
	}

	public int getUpdateUsername() {
		return updateUsername;
	}

	public void setUpdateUsername(int updateUsername) {
		this.updateUsername = updateUsername;
	}

	public String getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	private int delStatus;
	
	private int exInt1;
	
	private String exVar1;
	
 
	public int getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(int salaryId) {
		this.salaryId = salaryId;
	}

	public int getFinYearId() {
		return finYearId;
	}

	public void setFinYearId(int finYearId) {
		this.finYearId = finYearId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	@Override
	public String toString() {
		return "EmpSalary [salaryId=" + salaryId + ", finYearId=" + finYearId + ", empId=" + empId + ", Jan=" + Jan
				+ ", Feb=" + Feb + ", Mar=" + Mar + ", Apr=" + Apr + ", May=" + May + ", Jun=" + Jun + ", Jul=" + Jul
				+ ", Aug=" + Aug + ", Sep=" + Sep + ", Oct=" + Oct + ", Nov=" + Nov + ", Dece=" + Dece
				+ ", updateUsername=" + updateUsername + ", updateDatetime=" + updateDatetime + ", delStatus="
				+ delStatus + ", exInt1=" + exInt1 + ", exVar1=" + exVar1 + "]";
	}

	
	
}

 
	
