package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.cataskapi.model.AssesseeTypeMaster;

public interface AssesseeTypeMasterRepo extends JpaRepository<AssesseeTypeMaster, Integer> {
	
	List<AssesseeTypeMaster> findByDelStatus(int delStatus);

}
