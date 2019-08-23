package com.ats.cataskapi.communication.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.communication.model.GetAllCommunicationByTaskId;
 
public interface GetAllCommunicationByTaskIdRepo extends JpaRepository<GetAllCommunicationByTaskId, Integer> {
	
	@Query(value="SELECT\n" + 
			"    t_comunication.commun_id,\n" + 
			"    t_comunication.task_id,\n" + 
			"    t_comunication.emp_id,\n" + 
			"    t_comunication.commun_text,\n" + 
			"    t_comunication.update_datetime,\n" + 
			"    t_comunication.update_user,\n" + 
			"    t_comunication.ex_int1,\n" + 
			"    t_comunication.ex_int2,\n" + 
			"    t_comunication.ex_var1,\n" + 
			"    t_comunication.ex_var2,\n" + 
			"    m_emp.emp_name,\n" + 
			"    t_tasks.task_text,t_comunication.del_status \n" + 
			"FROM\n" + 
			"    t_comunication,\n" + 
			"    m_emp,\n" + 
			"    t_tasks\n" + 
			"WHERE\n" + 
			"    t_comunication.task_id = t_tasks.task_id AND m_emp.emp_id = t_comunication.emp_id AND t_comunication.task_id=:taskId AND t_comunication.del_status=1 ORDER BY t_comunication.update_datetime ASC ",nativeQuery=true)
	List<GetAllCommunicationByTaskId> getCommunicationListByTaskId(@Param("taskId") int taskId);

	 
	
	
	

}
