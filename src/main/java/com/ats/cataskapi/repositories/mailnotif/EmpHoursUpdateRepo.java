package com.ats.cataskapi.repositories.mailnotif;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.mailnotif.EmpHoursUpdate;

public interface EmpHoursUpdateRepo extends JpaRepository<EmpHoursUpdate, Integer> {
	
	@Query(value="Select m_emp.emp_id,m_emp.emp_email,m_emp.emp_type,coalesce(b.flag,2)+coalesce((select 1 from dual where b.flag=1 and b.work_hrs ),0) flag ,coalesce(b.work_hrs,0) work_hrs,coalesce(b.row_count,0) row_count  from m_emp left join (\n" + 
			"			SELECT a.emp_id, a.work_hrs,a.work_date,SUM(a.flag) as flag,count(*) as row_count FROM  ( SELECT SUM(t_daily_work_log.work_hours) as work_hrs,t_daily_work_log.work_date, t_daily_work_log.emp_id ,COALESCE((SELECT 1 from DUAL WHERE SUM(t_daily_work_log.work_hours) <210 ),0) as flag FROM t_daily_work_log \n" + 
			"			WHERE t_daily_work_log.work_date BETWEEN (SELECT\n" + 
			"			  CURDATE() - INTERVAL 2 DAY  FROM DUAL) and (select  CURDATE() - INTERVAL 1 DAY FROM DUAL) \n" + 
			"			  and t_daily_work_log.del_status=1  \n" + 
			"			  GROUP by t_daily_work_log.work_date,t_daily_work_log.emp_id \n" + 
			"			  ORDER by t_daily_work_log.emp_id ) a  group by emp_id ) b on m_emp.emp_id=b.emp_id  WHERE m_emp.emp_type=:empType  and m_emp.del_status=1 and m_emp.is_active=1 HAVING flag>1 \n" + 
			"	",nativeQuery=true)
	List<EmpHoursUpdate> getEmpsNotUpdated50Perwork(@Param("empType") int empType);
	
}
