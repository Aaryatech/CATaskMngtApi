package com.ats.cataskapi.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.BugetedMinAndWorkedMinByEmpIds; 

public interface BugetedMinAndWorkedMinByEmpIdsRepo extends JpaRepository<BugetedMinAndWorkedMinByEmpIds, Integer>{

	@Query(value = "select\n" + 
			"        UUID() as id,\n" + 
			"        sum(coalesce((select\n" + 
			"            sum(emp_bud_hr) \n" + 
			"        from\n" + 
			"            t_tasks \n" + 
			"        where\n" + 
			"            task_end_date between :fromDate and :toDate \n" + 
			"            and FIND_IN_SET(e.emp_id,task_emp_ids) \n" + 
			"            and is_active=1 \n" + 
			"            and del_status=1 ),\n" + 
			"        0)) as all_work,\n" + 
			"        sum(coalesce((select\n" + 
			"            sum(wl.work_hours)\n" + 
			"        from\n" + 
			"            t_daily_work_log wl \n" + 
			"        where\n" + 
			"            wl.work_date between :fromDate and :toDate  \n" + 
			"            and wl.emp_id=e.emp_id),\n" + 
			"        0)) as act_work \n" + 
			"    from\n" + 
			"        m_emp e \n" + 
			"    where\n" + 
			"        e.del_status=1 \n" + 
			"        and e.emp_id in (:ids)", nativeQuery = true)
	 BugetedMinAndWorkedMinByEmpIds bugetedMinAndWorkedMinByEmpIds(@Param("fromDate")String fromDate,@Param("toDate") String toDate,
			@Param("ids") ArrayList<String> ids);
}
