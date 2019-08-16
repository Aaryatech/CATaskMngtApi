package com.ats.cataskapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.cataskapi.model.CalenderYear;
 
 

public interface CalculateYearRepository extends JpaRepository<CalenderYear, Integer> {

	CalenderYear findByCalYrId(int calYrId);

	CalenderYear findByIsCurrent(int i);

}
