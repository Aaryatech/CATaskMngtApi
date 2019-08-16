package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.GetWeeklyOff;
 

public interface GetWeeklyOffRepo extends JpaRepository<GetWeeklyOff, Integer> {

	@Query(value = " SELECT c.company_name,w.*,l.loc_name FROM weekly_off w, m_company c,m_location l "
			+ "WHERE w.del_status=1 AND c.company_id=w.company_id AND w.loc_id=l.loc_id AND c.company_id=:companyId AND w.loc_id IN(:locIdList) ", nativeQuery = true)

	List<GetWeeklyOff> getListByCompanyId(@Param("companyId") int companyId,
			@Param("locIdList") List<Integer> locIdList);

}
