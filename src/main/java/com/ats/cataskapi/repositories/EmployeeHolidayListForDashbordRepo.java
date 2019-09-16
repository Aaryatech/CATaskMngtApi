package com.ats.cataskapi.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.EmployeeHolidayListForDashbord; 

public interface EmployeeHolidayListForDashbordRepo extends JpaRepository<EmployeeHolidayListForDashbord, Integer>{

	@Query(value = "select\n" + 
			"        UUID() as leave_id,\n" + 
			"        l.cal_yr_id,\n" + 
			"        l.emp_id,\n" + 
			"        l.leave_duration,\n" + 
			"        l.leave_fromdt,\n" + 
			"        l.leave_todt,\n" + 
			"        l.leave_num_days,\n" + 
			"        e.emp_name \n" + 
			"    from\n" + 
			"        t_leave_apply l,\n" + 
			"        m_emp e \n" + 
			"    where\n" + 
			"        e.emp_id=l.emp_id \n" + 
			"        and e.del_status=1 \n" + 
			"        and l.del_status=1 \n" + 
			"        and (\n" + 
			"            (\n" + 
			"                l.leave_fromdt>=:fromDate \n" + 
			"                and l.leave_fromdt<=:toDate\n" + 
			"            ) \n" + 
			"            or (\n" + 
			"                l.leave_todt>=:fromDate \n" + 
			"                and l.leave_todt<=:toDate\n" + 
			"            ) \n" + 
			"            or (\n" + 
			"                l.leave_fromdt<=:fromDate\n" + 
			"                and l.leave_todt>=:toDate\n" + 
			"            ) \n" + 
			"            or (\n" + 
			"                l.leave_fromdt>=:fromDate \n" + 
			"                and l.leave_todt<=:toDate\n" + 
			"            )\n" + 
			"        ) and e.emp_id in (:ids)", nativeQuery = true)
	List<EmployeeHolidayListForDashbord> getLeaveRecordForManagerDashboard(@Param("fromDate")String fromDate,@Param("toDate") String toDate,
			@Param("ids") ArrayList<String> ids);
}
