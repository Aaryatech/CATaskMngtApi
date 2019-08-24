package com.ats.cataskapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.cataskapi.model.DailyWorkLog;

@Repository
public interface DailyWorkLogRepo extends JpaRepository<DailyWorkLog, Integer> {
	
	List<DailyWorkLog> findByDelStatusAndEmpId(int del, int empId);

	DailyWorkLog findByWorkLogId(int logId);

	@Transactional
	@Modifying
	@Query(value="UPDATE t_daily_work_log SET del_status=0, update_username=:userId  WHERE work_log_id=:logId",nativeQuery=true)
	int deleteworkLogId(@Param("logId") int logId, @Param("userId") int userId);
	
}
