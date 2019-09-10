package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.EmpListForDashboard;

public interface EmpListForDashboardRepo extends JpaRepository<EmpListForDashboard, Integer>{

	@Query(value="select emp_id, emp_name from m_emp where emp_id in (:ids)",nativeQuery=true)
	List<EmpListForDashboard> getempList(@Param("ids") String[] ids);

}
