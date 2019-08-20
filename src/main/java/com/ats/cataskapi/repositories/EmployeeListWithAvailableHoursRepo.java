package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.EmployeeListWithAvailableHours;

public interface EmployeeListWithAvailableHoursRepo extends JpaRepository<EmployeeListWithAvailableHours, Integer> {

	@Query(value = "select l.leave_id,l.cal_yr_id,l.emp_id,l.leave_duration,l.leave_fromdt,l.leave_todt,l.leave_num_days,"
			+ "e.emp_name from  t_leave_apply l,m_emp e where e.emp_id=l.emp_id and ((l.leave_fromdt>=:fromDate and "
			+ "l.leave_fromdt<=:toDate) or (l.leave_todt>=:fromDate and l.leave_todt<=:toDate) or "
			+ "(l.leave_fromdt<=:fromDate and l.leave_todt>=:toDate) or (l.leave_fromdt>=:fromDate "
			+ "and l.leave_todt<=:toDate))", nativeQuery = true)
	List<EmployeeListWithAvailableHours> getLeaveRecord(@Param("fromDate") String fromDate,@Param("toDate") String toDate);
	
	@Query(value = "select count(*) as count from m_emp where del_status=1", nativeQuery = true)
	int getEmpCount();

}
