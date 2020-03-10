package com.ats.cataskapi.repositories;

 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

 import com.ats.cataskapi.model.FinancialYear;

public interface FinancialYearRepo  extends JpaRepository<FinancialYear, Integer> {

	
	@Query(value="SELECT *\n" + 
			"FROM dm_fin_year\n" + 
			"WHERE :finDate between dm_fin_year.fin_start_date AND dm_fin_year.fin_end_date ",nativeQuery=true)
	FinancialYear getFinYearBetDate(@Param("finDate") String  finDate);
	

	@Query(value="SELECT *\n" + 
			"FROM dm_fin_year\n" + 
			"WHERE  dm_fin_year.is_current=1",nativeQuery=true)
	FinancialYear getCurrYear();
	
	@Query(value="SELECT *\n" + 
			"FROM dm_fin_year\n" + 
			"WHERE  dm_fin_year.is_current=9",nativeQuery=true)
	FinancialYear getNextFinYear();

	List<FinancialYear> findByDelStatus(int i);
}
