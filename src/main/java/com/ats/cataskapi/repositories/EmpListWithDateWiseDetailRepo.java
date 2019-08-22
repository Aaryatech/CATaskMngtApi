package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.EmpListWithDateWiseDetail;

public interface EmpListWithDateWiseDetailRepo extends JpaRepository<EmpListWithDateWiseDetail, Integer>{

	@Query(value="select emp_id, emp_name from m_emp where del_status=1",nativeQuery=true)
	List<EmpListWithDateWiseDetail> getEmployeeList();

	@Query(value="select count(*) from t_leave_apply where   :date between leave_fromdt and  leave_todt and emp_id=:empId and leave_duration=:type",nativeQuery=true)
	int getcountOfLeave(@Param("date") String date,@Param("empId") int empId, @Param("type") int type);
	

}
