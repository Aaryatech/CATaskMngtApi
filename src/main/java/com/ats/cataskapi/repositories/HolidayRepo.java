package com.ats.cataskapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.Holiday;
 

public interface HolidayRepo extends JpaRepository<Holiday, Integer> {

	Holiday findByHolidayIdAndDelStatus(int holidayId, int i);

	@Transactional
	@Modifying
	@Query("update Holiday set del_status=0  WHERE holiday_id=:holidayId")
	int deleteHoliday(@Param("holidayId") int holidayId);

	@Query(value="select w.* from m_holiday w,emp_info e where  FIND_IN_SET(e.loc_id,w.loc_id)  and e.emp_id=:empId and w.del_status=1 and w.cal_yr_id=(select cal_yr_id from dm_cal_year where is_current=1) "
			+ "and holiday_fromdt>=:fromDate and holiday_todt<=:toDate",nativeQuery=true)
	List<Holiday> getHolidayByEmpIdAndFromDateTodate(@Param("empId")int empId, @Param("fromDate") String fromDate,@Param("toDate") String toDate);

}
