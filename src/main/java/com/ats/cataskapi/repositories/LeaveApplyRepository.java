package com.ats.cataskapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ats.cataskapi.model.LeaveApply;
 


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
	int updateLeaveApply(int leaveId,int trailId);

	
	
	@Transactional
	@Modifying
	@Query("update LeaveApply set ex_int1=:status  WHERE leave_id=:leaveId")
	int updateLeaveStatus(int leaveId,int status);

	


}
