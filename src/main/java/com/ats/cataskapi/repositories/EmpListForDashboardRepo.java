package com.ats.cataskapi.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.EmpListForDashboard;

public interface EmpListForDashboardRepo extends JpaRepository<EmpListForDashboard, Integer>{

	@Query(value="select emp_id, emp_name from m_emp where emp_id in (:ids) and del_status=1 and m_emp.emp_type  ",nativeQuery=true)
	List<EmpListForDashboard> getempList(@Param("ids") ArrayList<String> ids);//new added where emp_type not in (1,2) sachin 03-04-2020

	@Query(value="select e.emp_id,e.emp_name from m_emp e where e.del_status=1 and e.emp_type=3",nativeQuery=true)
	List<EmpListForDashboard> getManagerEmpList();

}
