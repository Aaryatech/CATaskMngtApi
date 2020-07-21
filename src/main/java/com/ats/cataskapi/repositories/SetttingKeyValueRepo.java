package com.ats.cataskapi.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.SetttingKeyValue;

public interface SetttingKeyValueRepo extends JpaRepository<SetttingKeyValue, Integer> {

	SetttingKeyValue findBySettingKeyAndDelStatus(String string, int i);
	
	

	@Transactional
	@Modifying
	@Query(value="UPDATE\n" + 
			"    t_setting_key_value\n" + 
			"SET\n" + 
			"    t_setting_key_value.int_value=:days \n" + 
			"WHERE\n" + 
			"    t_setting_key_value.setting_key =:key",nativeQuery=true)
	public int updateDaysLimit(@Param("days") int days, @Param("key") String  key);
	
	

	@Transactional
	@Modifying
	@Query(value="UPDATE\n" + 
			"    t_setting_key_value\n" + 
			"SET\n" + 
			"    t_setting_key_value.string_value =:statDate \n" + 
			"WHERE\n" + 
			"    t_setting_key_value.setting_key =:key",nativeQuery=true)
	public int updateDateLimit(@Param("statDate") String statDate, @Param("key") String  key);
	
	
	 
	
	
	

}
