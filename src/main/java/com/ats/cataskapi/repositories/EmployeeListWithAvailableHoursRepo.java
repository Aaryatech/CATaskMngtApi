package com.ats.cataskapi.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.EmployeeListWithAvailableHours;

public interface EmployeeListWithAvailableHoursRepo extends JpaRepository<EmployeeListWithAvailableHours, Integer> {

	@Query(value = "select l.leave_id,l.cal_yr_id,l.emp_id,l.leave_duration,l.leave_fromdt,l.leave_todt,l.leave_num_days,"
			+ "e.emp_name from  t_leave_apply l,m_emp e where e.emp_id=l.emp_id and e.del_status=1 and l.del_status=1 and ((l.leave_fromdt>=:fromDate and "
			+ "l.leave_fromdt<=:toDate) or (l.leave_todt>=:fromDate and l.leave_todt<=:toDate) or "
			+ "(l.leave_fromdt<=:fromDate and l.leave_todt>=:toDate) or (l.leave_fromdt>=:fromDate "
			+ "and l.leave_todt<=:toDate))", nativeQuery = true)
	List<EmployeeListWithAvailableHours> getLeaveRecord(@Param("fromDate") String fromDate,@Param("toDate") String toDate);
	
	@Query(value = "select count(*) as count from m_emp where del_status=1", nativeQuery = true)
	int getEmpCount();

	@Query(value = "select\n" + 
			"        l.leave_id,\n" + 
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
	List<EmployeeListWithAvailableHours> getLeaveRecord(@Param("fromDate")String fromDate,@Param("toDate") String toDate,@Param("ids")  ArrayList<String> ids);

	@Query(value = "select\n" + 
			"        l.leave_id,\n" + 
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
	List<EmployeeListWithAvailableHours> getLeaveRecordByEmpId(@Param("fromDate")String fromDate,@Param("toDate") String toDate,@Param("ids")  int ids);

}
