package com.ats.cataskapi.repositories.mailnotif;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.mailnotif.TwiceWeekHours;

public interface TwiceWeekHoursRepo extends JpaRepository<TwiceWeekHours, Integer>
{
	
	@Query(value="SELECT a.emp_id,a.emp_nickname,a.emp_type,a.unique_id,COALESCE(b.today,0)as day1,COALESCE(c.today1,0)as day2, COALESCE(d.today2,0) as day3 ,\n" + 
			"\n" + 
			"   dayname((select CURDATE() - INTERVAL 1 DAY FROM DUAL))   as dayname1,  dayname((select CURDATE() - INTERVAL 2 DAY FROM DUAL))   as dayname2 , dayname((select CURDATE() - INTERVAL 3 DAY FROM DUAL))   as dayname3     ,\n" + 
			"\n" + 
			"(ADDTIME(COALESCE(b.today,0),ADDTIME(COALESCE(c.today1,0),COALESCE(d.today2,0)))) as tot_hrs,\n" + 
			"\n" + 
			"TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC((ADDTIME(COALESCE(b.today,0),ADDTIME(COALESCE(c.today1,0),COALESCE(d.today2,0)))))/3), '%H:%i') as avg_tot_hrs\n" + 
			"\n" + 
			"FROM (\n" + 
			"SELECT UUID() as unique_id, m_emp.emp_id,m_emp.emp_nickname,m_emp.emp_type  FROM m_emp WHERE m_emp.del_status=1 and m_emp.is_active=1 and m_emp.emp_type=:empType ) a \n" + 
			"LEFT JOIN \n" + 
			"( \n" + 
			"\n" + 
			"SELECT\n" + 
			"d.emp_id,\n" + 
			"        \n" + 
			"        CONCAT(FLOOR(d.work_hours/60),\n" + 
			"        ':',\n" + 
			"        LPAD(MOD(d.work_hours,\n" + 
			"        60),\n" + 
			"        2,\n" + 
			"        '0')) as today \n" + 
			"        FROM t_daily_work_log d\n" + 
			"        WHERE  d.work_date= (select CURDATE() - INTERVAL 1 DAY FROM DUAL)\n" + 
			"        \n" + 
			") b on a.emp_id=b.emp_id\n" + 
			"\n" + 
			"LEFT JOIN \n" + 
			"( \n" + 
			"SELECT\n" + 
			"d.emp_id,\n" + 
			"        \n" + 
			"        CONCAT(FLOOR(d.work_hours/60),\n" + 
			"        ':',\n" + 
			"        LPAD(MOD(d.work_hours,\n" + 
			"        60),\n" + 
			"        2,\n" + 
			"        '0')) as today1 \n" + 
			"        FROM t_daily_work_log d\n" + 
			"        WHERE  d.work_date= (select CURDATE() - INTERVAL 2 DAY FROM DUAL)\n" + 
			"    ) c on c.emp_id=a.emp_id\n" + 
			"\n" + 
			"LEFT JOIN \n" + 
			"( \n" + 
			"SELECT\n" + 
			"d.emp_id,\n" + 
			"        \n" + 
			"        CONCAT(FLOOR(d.work_hours/60),\n" + 
			"        ':',\n" + 
			"        LPAD(MOD(d.work_hours,\n" + 
			"        60),\n" + 
			"        2,\n" + 
			"        '0')) as today2\n" + 
			"        FROM t_daily_work_log d\n" + 
			"        WHERE  d.work_date= (select CURDATE() - INTERVAL 3 DAY FROM DUAL)\n" + 
			"    ) d on d.emp_id=a.emp_id ORDER BY a.emp_nickname desc", nativeQuery=true)
	List<TwiceWeekHours> getPrev3DayWorkLog(@Param("empType") int empType); 


	
	@Query(value="(     SELECT\n" + 
			"            DISTINCT c.emp_email     \n" + 
			"        FROM\n" + 
			"           \n" + 
			"            m_emp c     \n" + 
			"        WHERE\n" + 
			"           c.del_status=1 and c.is_active=1 and c.emp_type IN (:empType))",nativeQuery=true)
	List<String> getEmailIds(@Param("empType") String empType);
           		
}
