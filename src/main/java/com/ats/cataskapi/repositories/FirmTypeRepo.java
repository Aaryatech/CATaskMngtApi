package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.cataskapi.model.FirmType;

public interface FirmTypeRepo extends JpaRepository<FirmType, Integer> {
			
		
	List<FirmType> findAllBydelStatus(int del);

}
