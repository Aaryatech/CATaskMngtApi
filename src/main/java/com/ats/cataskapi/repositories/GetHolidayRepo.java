package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.GetHoliday;
 

public interface GetHolidayRepo extends JpaRepository<GetHoliday, Integer> {

	@Query(value = "SELECT\n" + 
			"        h.holiday_id,\n" + 
			"        h.holiday_fromdt,\n" + 
			"        h.holiday_todt,\n" + 
			"        h.holiday_remark,\n" + 
			"        h.ex_var1,\n" + 
			"        h.ex_var2,\n" + 
			"        h.cal_yr_id,\n" + 
			"        h.company_id,\n" + 
			"        h.loc_id,\n" + 
			"        '' as company_name,\n" + 
			"        '' as loc_name,\n" + 
			"        cy.cal_yr_from_date,\n" + 
			"        cy.cal_yr_to_date \n" + 
			"    FROM\n" + 
			"        m_holiday h,  \n" + 
			"        dm_cal_year cy \n" + 
			"    WHERE\n" + 
			"        h.company_id=:companyId    \n" + 
			"        AND h.del_status=1 \n" + 
			"        AND h.is_active=1  \n" + 
			"        AND cy.cal_yr_id=h.cal_yr_id and cy.is_current=1", nativeQuery = true)

	List<GetHoliday> getHolidayListByCompany(@Param("companyId") int companyId);

}
