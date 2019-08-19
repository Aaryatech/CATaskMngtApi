package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.LeaveDetail;
 

public interface LeaveDetailRepo extends JpaRepository<LeaveDetail, Integer>{
 
	
	@Query(value = "SELECT l.*, e.emp_name, e.emp_nickname FROM t_leave_apply AS l, m_emp AS e WHERE l.emp_id =:empId AND "
			+ "l.emp_id =e.emp_id AND l.del_status=1 ", nativeQuery = true)
	List<LeaveDetail> getLeaveListByEmp(@Param("empId") int empId);

			
}