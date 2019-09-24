package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ats.cataskapi.model.EmpIdNameList;

public interface EmpIdNameListRepository extends JpaRepository<EmpIdNameList, Integer>{

	@Query(value="select uuid() as id,emp_id,emp_name from m_emp where del_status=1 and emp_type!=2",nativeQuery=true)
	List<EmpIdNameList> getempList();
	
	@Query(value="select uuid() as id,emp_id,emp_name from m_emp where del_status=1 and emp_type=2",nativeQuery=true) 
	List<EmpIdNameList> getpartnerList();
	
	

}
