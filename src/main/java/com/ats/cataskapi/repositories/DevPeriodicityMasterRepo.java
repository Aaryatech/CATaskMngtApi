package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.cataskapi.model.DevPeriodicityMaster;

public interface DevPeriodicityMasterRepo extends JpaRepository<DevPeriodicityMaster, Integer> {

	List<DevPeriodicityMaster> findAllByDelStatus(int del);
	
}
