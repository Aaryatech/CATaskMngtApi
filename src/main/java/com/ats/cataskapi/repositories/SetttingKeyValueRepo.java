package com.ats.cataskapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.cataskapi.model.SetttingKeyValue;

public interface SetttingKeyValueRepo extends JpaRepository<SetttingKeyValue, Integer> {

	SetttingKeyValue findBySettingKeyAndDelStatus(String string, int i);
	
	
	

}
