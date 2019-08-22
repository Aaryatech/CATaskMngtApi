package com.ats.cataskapi.task.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.task.model.EmpSalary;

public interface EmpSalaryRepo extends JpaRepository<EmpSalary, Integer> {

	EmpSalary findByEmpIdAndFinYearIdAndDelStatus(int empId, int finYearId, int i);
	
	//Update Salary Methods
	
	 
	@Transactional
	@Modifying
	@Query(value="UPDATE t_emp_salary SET  Jan=:empSalary,update_username=:userId,update_datetime=:curDateTime  WHERE salary_id=:salId",nativeQuery=true)
	int updateSalRecJan( @Param("curDateTime") String curDateTime,@Param("userId") int userId,@Param("empSalary") float empSalary,@Param("salId") int salId);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE t_emp_salary SET  Feb=:empSalary,update_username=:userId,update_datetime=:curDateTime  WHERE salary_id=:salId",nativeQuery=true)
	int updateSalRecFeb( @Param("curDateTime") String curDateTime,@Param("userId") int userId,@Param("empSalary") float empSalary,@Param("salId") int salId);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE t_emp_salary SET  Mar=:empSalary,update_username=:userId,update_datetime=:curDateTime  WHERE salary_id=:salId",nativeQuery=true)
	int updateSalRecMar( @Param("curDateTime") String curDateTime,@Param("userId") int userId,@Param("empSalary") float empSalary,@Param("salId") int salId);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE t_emp_salary SET  Apr=:empSalary,update_username=:userId,update_datetime=:curDateTime  WHERE salary_id=:salId",nativeQuery=true)
	int updateSalRecApr( @Param("curDateTime") String curDateTime,@Param("userId") int userId,@Param("empSalary") float empSalary,@Param("salId") int salId);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE t_emp_salary SET May=:empSalary,update_username=:userId,update_datetime=:curDateTime  WHERE salary_id=:salId",nativeQuery=true)
	int updateSalRecMay( @Param("curDateTime") String curDateTime,@Param("userId") int userId,@Param("empSalary") float empSalary,@Param("salId") int salId);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE t_emp_salary SET  Jun=:empSalary,update_username=:userId,update_datetime=:curDateTime  WHERE salary_id=:salId",nativeQuery=true)
	int updateSalRecJun( @Param("curDateTime") String curDateTime,@Param("userId") int userId,@Param("empSalary") float empSalary,@Param("salId") int salId);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE t_emp_salary SET  Jul=:empSalary,update_username=:userId,update_datetime=:curDateTime  WHERE salary_id=:salId",nativeQuery=true)
	int updateSalRecJul( @Param("curDateTime") String curDateTime,@Param("userId") int userId,@Param("empSalary") float empSalary,@Param("salId") int salId);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE t_emp_salary SET Aug=:empSalary,update_username=:userId,update_datetime=:curDateTime  WHERE salary_id=:salId",nativeQuery=true)
	int updateSalRecAug( @Param("curDateTime") String curDateTime,@Param("userId") int userId,@Param("empSalary") float empSalary,@Param("salId") int salId);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE t_emp_salary SET  Sep=:empSalary,update_username=:userId,update_datetime=:curDateTime  WHERE salary_id=:salId",nativeQuery=true)
	int updateSalRecSep( @Param("curDateTime") String curDateTime,@Param("userId") int userId,@Param("empSalary") float empSalary,@Param("salId") int salId);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE t_emp_salary SET  Oct=:empSalary,update_username=:userId,update_datetime=:curDateTime  WHERE salary_id=:salId",nativeQuery=true)
	int updateSalRecOct( @Param("curDateTime") String curDateTime,@Param("userId") int userId,@Param("empSalary") float empSalary,@Param("salId") int salId);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE t_emp_salary SET  Nov=:empSalary,update_username=:userId,update_datetime=:curDateTime  WHERE salary_id=:salId",nativeQuery=true)
	int updateSalRecNov( @Param("curDateTime") String curDateTime,@Param("userId") int userId,@Param("empSalary") float empSalary,@Param("salId") int salId);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE t_emp_salary SET  Dece=:empSalary,update_username=:userId,update_datetime=:curDateTime  WHERE salary_id=:salId",nativeQuery=true)
	int updateSalRecDece( @Param("curDateTime") String curDateTime,@Param("userId") int userId,@Param("empSalary") float empSalary,@Param("salId") int salId);

	List<EmpSalary> findByFinYearIdAndDelStatus(int year, int i);
	

}
