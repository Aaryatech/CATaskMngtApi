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

	@Query(value="select\n" + 
			"        w.* \n" + 
			"    from\n" + 
			"        m_holiday w \n" + 
			"    where\n" + 
			"        w.del_status=1  \n" + 
			"        and holiday_fromdt>=:fromDate \n" + 
			"        and holiday_todt<=:toDate",nativeQuery=true)
	List<Holiday> getHolidayByEmpIdAndFromDateTodate(  @Param("fromDate") String fromDate,@Param("toDate") String toDate);

}
