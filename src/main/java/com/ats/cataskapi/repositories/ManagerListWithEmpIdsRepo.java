package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.ManagerListWithEmpIds;

public interface ManagerListWithEmpIdsRepo extends JpaRepository<ManagerListWithEmpIds, Integer>{

	@Query(value="select\n" + 
			"        e.emp_id,\n" + 
			"        e.emp_name, \n" + 
			"        coalesce((select\n" + 
			"             GROUP_CONCAT(DISTINCT task_emp_ids)\n" + 
			"        from\n" + 
			"            t_tasks \n" + 
			"        where\n" + 
			"            task_end_date between :fromDate and :toDate \n" + 
			"            and FIND_IN_SET(e.emp_id,task_emp_ids) and FIND_IN_SET(:empId,task_emp_ids) \n" + 
			"            and is_active=1 \n" + 
			"            and del_status=1 ),\n" + 
			"        0) as member_ids\n" + 
			"    from\n" + 
			"        m_emp e \n" + 
			"    where\n" + 
			"        e.del_status=1 \n" + 
			"        and e.emp_type=3",nativeQuery=true)
	List<ManagerListWithEmpIds> managerListWithEmpIds(@Param("fromDate") String fromDate,@Param("toDate") String toDate,
			@Param("empId") int empId);
	
	

}
