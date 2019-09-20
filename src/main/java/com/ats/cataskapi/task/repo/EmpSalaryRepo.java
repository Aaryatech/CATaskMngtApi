package com.ats.cataskapi.task.repo;

import java.util.ArrayList;
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

	@Query(value="select s.* from t_emp_salary s where fin_year_id=(select fin_year_id from dm_fin_year where :date between fin_start_date and fin_end_date) and emp_id=:empId",nativeQuery=true)
	EmpSalary getrecordByEmpIdAndDate(@Param("date")String date,@Param("empId") int empId);
	
	@Query(value="select * from t_emp_salary where emp_id in (:arryids) and fin_year_id=:yearId",nativeQuery=true)
	List<EmpSalary> getreocrdByempIdAndYearId(@Param("yearId")int yearId,@Param("arryids") ArrayList<String> arryids);
}
