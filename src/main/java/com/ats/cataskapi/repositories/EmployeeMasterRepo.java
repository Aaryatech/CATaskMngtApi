package com.ats.cataskapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.EmployeeMaster;

public interface EmployeeMasterRepo extends JpaRepository<EmployeeMaster, Integer> {

	List<EmployeeMaster> findByDelStatusAndIsActiveAndEmpTypeOrderByEmpIdDesc(int del,int isActive, int type);
	
	List<EmployeeMaster> findAllByDelStatusOrderByEmpIdDesc(int del);
	
	EmployeeMaster findByEmpIdAndDelStatus(int empId, int del);

	@Transactional
	@Modifying
	@Query(value = "UPDATE m_emp SET del_status=0, update_username=:userId WHERE emp_id=:empId", nativeQuery = true)
	int deleteEmployee(@Param("empId") int empId, @Param("userId") int userId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE m_emp SET is_active=:stat, update_username=:userId WHERE emp_id=:empId", nativeQuery = true)
	int updateEmployeeActive(@Param("empId") int empId, @Param("userId") int userId, @Param("stat") int stat);

	@Transactional
	@Modifying
	@Query(value = "UPDATE m_emp SET emp_role_id=:roleId WHERE emp_id IN (:userIdList) ", nativeQuery = true)
	int updateRoleId(@Param("roleId") int roleId, @Param("userIdList") List<String> userIdList);

	@Query(value = " SELECT * from m_emp WHERE m_emp.emp_email=:userName AND m_emp.emp_pass=BINARY(:password) and del_status=1 ", nativeQuery = true)
	EmployeeMaster loginCheck(@Param("userName") String userName, @Param("password") String password);

	List<EmployeeMaster> findByEmpRoleIdAndDelStatus(int roleId, int i);

	List<EmployeeMaster> findByEmpTypeAndDelStatus(int roleId, int i);

	List<EmployeeMaster> findByEmpTypeAndDelStatusAndIsActive(int roleId, int i, int j);

	@Query(value = "select e.emp_id, e.emp_type, e.emp_name, e.emp_nickname, e.emp_dob, e.emp_role_id,\n"
			+ "e.emp_mob,\n" + "e.emp_email,\n" + "e.emp_pass,\n" + "e.emp_desc,\n" + "e.emp_pic,\n" + "e.emp_salary,\n"
			+ "e.del_status,\n" + "e.is_active,\n" + "e.update_datetime,\n" + "e.update_username,\n" + "e.ex_int1,\n"
			+ "e.ex_int2,\n"
			+ "e.ex_var1,coalesce((select r.role_name from m_assign_role r where r.role_id=e.emp_role_id),'Not Assigned') as ex_var2 from m_emp e where e.del_status=1 order by emp_role_id,e.emp_name", nativeQuery = true)
	List<EmployeeMaster> getAllEmployeesWithRoleName();
	
	
	@Query(value = "SELECT\n" + 
			"    *\n" + 
			"FROM\n" + 
			"    `m_emp`\n" + 
			"WHERE\n" + 
			"    m_emp.emp_mob =:inputValue OR m_emp.emp_email =:inputValue AND m_emp.is_active = 1 AND m_emp.del_status = 1", nativeQuery = true)
	EmployeeMaster findUserByEmailOrContactNumber(String inputValue);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE m_emp SET emp_pass=:password ,ex_int1=1 WHERE emp_id=:userId ", nativeQuery = true)
	int chagePass(@Param("password") String password, @Param("userId") int userId);

}
