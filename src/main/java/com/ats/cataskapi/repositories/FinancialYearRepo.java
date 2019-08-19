package com.ats.cataskapi.repositories;

 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

 import com.ats.cataskapi.model.FinancialYear;

public interface FinancialYearRepo  extends JpaRepository<FinancialYear, Integer> {

	
	@Query(value="SELECT * from dm_fin_year  where dm_fin_year.fin_start_date >:finDate AND dm_fin_year.fin_end_date<:finDate ",nativeQuery=true)
	FinancialYear getFinYearBetDate(@Param("finDate") String  finDate);
}
