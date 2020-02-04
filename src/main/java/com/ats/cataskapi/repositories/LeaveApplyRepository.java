package com.ats.cataskapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.LeaveApply;
import com.ats.cataskapi.model.ManagerListWithEmpIds;

public interface LeaveApplyRepository extends JpaRepository<LeaveApply, Integer> {

	List<LeaveApply> findByDelStatus(int i);

	@Transactional
	@Modifying
	@Query("update LeaveApply set del_status=0  WHERE leave_id=:leaveId")
	int deleteLeaveApply(int leaveId);

	LeaveApply findByLeaveIdAndDelStatus(int leaveId, int i);

	@Transactional
	@Modifying
	@Query("update LeaveApply set ex_int2=:trailId  WHERE leave_id=:leaveId")
	int updateLeaveApply(int leaveId, int trailId);

	@Transactional
	@Modifying
	@Query("update LeaveApply set ex_int1=:status  WHERE leave_id=:leaveId")
	int updateLeaveStatus(int leaveId, int status);

	@Query(value=" select\n" + 
			"        COUNT(l.leave_id)\n" + 
			"        \n" + 
			"    from\n" + 
			"        t_leave_apply l\n" + 
			"       \n" + 
			"    where\n" + 
			"         \n" + 
			"         l.del_status=1          \n" + 
			"        and (\n" + 
			"            (\n" + 
			"                l.leave_fromdt>=:fromDate                  \n" + 
			"                and l.leave_fromdt<=:toDate             \n" + 
			"            )              \n" + 
			"            or (\n" + 
			"                l.leave_todt>=:fromDate                  \n" + 
			"                and l.leave_todt<=:toDate             \n" + 
			"            )              \n" + 
			"            or (\n" + 
			"                l.leave_fromdt<=:fromDate                 \n" + 
			"                and l.leave_todt>=:toDate             \n" + 
			"            )              \n" + 
			"            or (\n" + 
			"                l.leave_fromdt>=:fromDate                  \n" + 
			"                and l.leave_todt<=:toDate             \n" + 
			"            )         \n" + 
			"        )",nativeQuery=true)
	int getCountofLeaveFdTd(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

}
