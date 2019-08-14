package com.ats.cataskapi.accessrights;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

 

public interface AssignRoleDetailListRepository extends JpaRepository<AssignRoleDetailList, Integer> {

	List<AssignRoleDetailList> findByDelStatusOrderByRoleIdDesc(int delStatus);

	@Query(value = " SELECT r.* from m_assign_role_rusa r, t_user_login_rusa u where u.user_id=:usrId AND r.role_id=u.role_id", nativeQuery = true)
	AssignRoleDetailList getRoleJson(@Param("usrId") int usrId);

	@Modifying
	@Transactional	@Query("Update AssignRoleDetailList  SET del_status=1 WHERE role_id=:roleId")
	int deleteRole(@Param("roleId") int roleId);

	AssignRoleDetailList findByRoleId(int roleId);
	
	List<AssignRoleDetailList> findByDelStatusAndRoleNameIn(int delStatus,List<String> roleNameList);
	
	List<AssignRoleDetailList> findByDelStatusAndRoleIdIn(int delStatus,List<Integer> roleIdList);


}
