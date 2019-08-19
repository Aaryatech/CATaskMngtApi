package com.ats.cataskapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.EmployeeMaster;

public interface EmployeeMasterRepo extends JpaRepository<EmployeeMaster, Integer> {

	List<EmployeeMaster> findAllByDelStatusOrderByEmpIdDesc(int del);

	EmployeeMaster findByEmpIdAndDelStatus(int empId, int del);

	@Transactional
	@Modifying
	@Query(value="UPDATE m_emp SET del_status=0, update_username=:userId WHERE emp_id=:empId",nativeQuery=true)
	int deleteEmployee(@Param("empId") int empId, @Param("userId") int userId);
	
	//UPDATE m_emp SET del_status=0 WHERE emp_id IN (:emp_id) 
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE m_emp SET emp_role_id=:roleId WHERE emp_id IN (:userIdList) ", nativeQuery = true)
	int updateRoleId(@Param("roleId") int roleId, @Param("userIdList") List<String> userIdList);

	@Query(value = " SELECT * from m_emp WHERE m_emp.emp_email=:userName AND m_emp.emp_pass=BINARY(:password) and del_status=1 ", nativeQuery = true)
	EmployeeMaster loginCheck(@Param("userName") String userName, @Param("password") String password);


}
