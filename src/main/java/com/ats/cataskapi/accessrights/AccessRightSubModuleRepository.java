package com.ats.cataskapi.accessrights;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccessRightSubModuleRepository extends JpaRepository<AccessRightSubModule, Integer>{

	@Query(value=" SELECT * FROM m_module_sub WHERE  is_delete =1 AND module_id=:moduleId ORDER BY order_by ASC; ",nativeQuery=true)
	List<AccessRightSubModule> getSubModule(@Param("moduleId") int moduleId);

}
