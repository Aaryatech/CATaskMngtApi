package com.ats.cataskapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.EmployeeMaster;

public interface EmployeeMasterRepo extends JpaRepository<EmployeeMaster, Integer> {

	List<EmployeeMaster> findAllBydelStatus(int del);

	EmployeeMaster findByEmpIdAndDelStatus(int empId, int del);

	@Transactional
	@Modifying
	@Query(value="UPDATE m_emp SET del_status=0 WHERE emp_id=:empId",nativeQuery=true)
	int deleteEmployee(@Param("empId") int empId);
	
	//UPDATE m_emp SET del_status=0 WHERE emp_id IN (:emp_id) 
	

}
