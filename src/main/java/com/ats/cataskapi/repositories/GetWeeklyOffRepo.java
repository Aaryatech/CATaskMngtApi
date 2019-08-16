package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.GetWeeklyOff;
 

public interface GetWeeklyOffRepo extends JpaRepository<GetWeeklyOff, Integer> {

	@Query(value = "SELECT\n" + 
			"        '' as company_name,\n" + 
			"        w.*,\n" + 
			"        '' as loc_name \n" + 
			"    FROM\n" + 
			"        weekly_off w \n" + 
			"    WHERE\n" + 
			"        w.del_status=1  ", nativeQuery = true)

	List<GetWeeklyOff> getListByCompanyId();

}
