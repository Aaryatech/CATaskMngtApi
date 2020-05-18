package com.ats.cataskapi.repositories.mailnotif;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.mailnotif.TwiceWeekHours;

public interface TwiceWeekHoursRepo extends JpaRepository<TwiceWeekHours, Integer>
{
	
	@Query(value="SELECT a.emp_email, a.emp_id,a.emp_nickname,a.emp_type,a.unique_id,COALESCE(b.today,0)as day1,COALESCE(c.today1,0)as day2, COALESCE(d.today2,0) as day3 , 'NA' as day4, 'NA' AS dayname4, \n" + 
			"\n" + 
			"   dayname((select CURDATE() - INTERVAL 1 DAY FROM DUAL))   as dayname1,  dayname((select CURDATE() - INTERVAL 2 DAY FROM DUAL))   as dayname2 , dayname((select CURDATE() - INTERVAL 3 DAY FROM DUAL))   as dayname3     ,\n" + 
			"\n" + 
			"(ADDTIME(COALESCE(b.today,0),ADDTIME(COALESCE(c.today1,0),COALESCE(d.today2,0)))) as tot_hrs,\n" + 
			"\n" + 
			"TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC((ADDTIME(COALESCE(b.today,0),ADDTIME(COALESCE(c.today1,0),COALESCE(d.today2,0)))))/3), '%H:%i') as avg_tot_hrs\n" + 
			"\n" + 
			"FROM (\n" + 
			"SELECT UUID() as unique_id, m_emp.emp_id,m_emp.emp_name as emp_nickname,m_emp.emp_type,m_emp.emp_email  FROM m_emp WHERE m_emp.del_status=1 and m_emp.is_active=1 and m_emp.emp_type=:empType ) a \n" + 
			"LEFT JOIN \n" + 
			"( \n" + 
			"\n" + 
			"SELECT\n" + 
			"d.emp_id,\n" + 
			"        \n" + 
			"        CONCAT(FLOOR(SUM(d.work_hours)/60),\n" + 
			"        ':',\n" + 
			"        LPAD(MOD(SUM(d.work_hours),\n" + 
			"        60),\n" + 
			"        2,\n" + 
			"        '0')) as today \n" + 
			"        FROM t_daily_work_log d\n" + 
			"        WHERE  d.work_date= (select CURDATE() - INTERVAL 1 DAY FROM DUAL) group by d.work_date,d.emp_id \n" + 
			"        \n" + 
			") b on a.emp_id=b.emp_id\n" + 
			"\n" + 
			"LEFT JOIN \n" + 
			"( \n" + 
			"SELECT\n" + 
			"d.emp_id,\n" + 
			"        \n" + 
			"        CONCAT(FLOOR(SUM(d.work_hours)/60),\n" + 
			"        ':',\n" + 
			"        LPAD(MOD(SUM(d.work_hours),\n" + 
			"        60),\n" + 
			"        2,\n" + 
			"        '0')) as today1 \n" + 
			"        FROM t_daily_work_log d\n" + 
			"        WHERE  d.work_date= (select CURDATE() - INTERVAL 2 DAY FROM DUAL) group by d.work_date,d.emp_id \n" + 
			"    ) c on c.emp_id=a.emp_id\n" + 
			"\n" + 
			"LEFT JOIN \n" + 
			"( \n" + 
			"SELECT\n" + 
			"d.emp_id,\n" + 
			"        \n" + 
			"        CONCAT(FLOOR(SUM(d.work_hours)/60),\n" + 
			"        ':',\n" + 
			"        LPAD(MOD(SUM(d.work_hours),\n" + 
			"        60),\n" + 
			"        2,\n" + 
			"        '0')) as today2\n" + 
			"        FROM t_daily_work_log d\n" + 
			"        WHERE  d.work_date= (select CURDATE() - INTERVAL 3 DAY FROM DUAL) group by d.work_date,d.emp_id \n" + 
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
    
	@Query(value="SELECT a.emp_email, " + 
			"    a.emp_id,\n" + 
			"    a.emp_nickname,a.unique_id, a.emp_type,\n" + 
			"    COALESCE(b.today,\n" + 
			"    0)as day1,\n" + 
			"    COALESCE(c.today1,\n" + 
			"    0)as day2,\n" + 
			"    COALESCE(d.today2,\n" + 
			"    0) as day3 ,\n" + 
			"   \n" + 
			"    COALESCE(e.today3,\n" + 
			"    0) as day4 ,\n" + 
			"   \n" + 
			"   \n" + 
			"    dayname((select\n" + 
			"        CURDATE() - INTERVAL 1 DAY\n" + 
			"    FROM\n" + 
			"        DUAL))   as dayname1,\n" + 
			"    dayname((select\n" + 
			"        CURDATE() - INTERVAL 2 DAY\n" + 
			"    FROM\n" + 
			"        DUAL))   as dayname2 ,\n" + 
			"    dayname((select\n" + 
			"        CURDATE() - INTERVAL 3 DAY\n" + 
			"    FROM\n" + 
			"        DUAL))   as dayname3     ,\n" + 
			"       \n" + 
			"        dayname((select\n" + 
			"        CURDATE() - INTERVAL 4 DAY\n" + 
			"    FROM\n" + 
			"        DUAL))   as dayname4     ,\n" + 
			"       \n" + 
			"       \n" + 
			"    ADDTIME(COALESCE(b.today,0),(ADDTIME(COALESCE(c.today1,0),\n" + 
			"    ADDTIME(COALESCE(d.today2,0),  COALESCE(e.today3,0)) ) )) as tot_hrs,\n" + 
			"    \n" + 
			"    TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(ADDTIME(0,(ADDTIME(COALESCE(c.today1,0), ADDTIME(COALESCE(d.today2, 0),COALESCE(e.today3,0))))))/3),\n" + 
			"    '%H:%i:%s') as avg_tot_hrs\n" + 
			"FROM\n" + 
			"    ( SELECT\n" + 
			"        UUID() as unique_id, m_emp.emp_id,m_emp.emp_name as emp_nickname,m_emp.emp_type,m_emp.emp_email  FROM m_emp WHERE m_emp.del_status=1 and m_emp.is_active=1 and m_emp.emp_type=:empType) a  \n" + 
			"LEFT JOIN\n" + 
			"    (\n" + 
			"        SELECT\n" + 
			"            d.emp_id,\n" + 
			"            CONCAT(FLOOR(SUM(d.work_hours)/60),\n" + 
			"            ':',\n" + 
			"            LPAD(MOD(SUM(d.work_hours),\n" + 
			"            60),\n" + 
			"            2,\n" + 
			"            '0')) as today          \n" + 
			"        FROM\n" + 
			"            t_daily_work_log d        \n" + 
			"        WHERE\n" + 
			"            d.work_date= (\n" + 
			"                select\n" + 
			"                    CURDATE() - INTERVAL 1 DAY\n" + 
			"                FROM\n" + 
			"                    DUAL\n" + 
			"            ) group by d.work_date,d.emp_id          \n" + 
			"        ) b\n" + 
			"            on a.emp_id=b.emp_id  \n" + 
			"    LEFT JOIN\n" + 
			"        (\n" + 
			"            SELECT\n" + 
			"                d.emp_id,\n" + 
			"                CONCAT(FLOOR(SUM(d.work_hours)/60),\n" + 
			"                ':',\n" + 
			"                LPAD(MOD(SUM(d.work_hours),\n" + 
			"                60),\n" + 
			"                2,\n" + 
			"                '0')) as today1          \n" + 
			"            FROM\n" + 
			"                t_daily_work_log d        \n" + 
			"            WHERE\n" + 
			"                d.work_date= (\n" + 
			"                    select\n" + 
			"                        CURDATE() - INTERVAL 2 DAY\n" + 
			"                    FROM\n" + 
			"                        DUAL\n" + 
			"                ) group by d.work_date,d.emp_id    \n" + 
			"            ) c\n" + 
			"                on c.emp_id=a.emp_id  \n" + 
			"        LEFT JOIN\n" + 
			"            (\n" + 
			"                SELECT\n" + 
			"                    d.emp_id,\n" + 
			"                    CONCAT(FLOOR(SUM(d.work_hours)/60),\n" + 
			"                    ':',\n" + 
			"                    LPAD(MOD(SUM(d.work_hours),\n" + 
			"                    60),\n" + 
			"                    2,\n" + 
			"                    '0')) as today2        \n" + 
			"                FROM\n" + 
			"                    t_daily_work_log d        \n" + 
			"                WHERE\n" + 
			"                    d.work_date= (\n" + 
			"                        select\n" + 
			"                            CURDATE() - INTERVAL 3 DAY\n" + 
			"                        FROM\n" + 
			"                            DUAL\n" + 
			"                    ) group by d.work_date,d.emp_id    \n" + 
			"                ) d \n" + 
			"                    on d.emp_id=a.emp_id\n" + 
			"                   \n" + 
			"                   \n" + 
			"                   \n" + 
			"                     LEFT JOIN\n" + 
			"            (\n" + 
			"                SELECT\n" + 
			"                    d.emp_id,\n" + 
			"                    CONCAT(FLOOR(SUM(d.work_hours)/60),\n" + 
			"                    ':',\n" + 
			"                    LPAD(MOD(SUM(d.work_hours),\n" + 
			"                    60),\n" + 
			"                    2,\n" + 
			"                    '0')) as today3        \n" + 
			"                FROM\n" + 
			"                    t_daily_work_log d        \n" + 
			"                WHERE\n" + 
			"                    d.work_date= (\n" + 
			"                        select\n" + 
			"                            CURDATE() - INTERVAL 4 DAY\n" + 
			"                        FROM\n" + 
			"                            DUAL\n" + 
			"                    ) group by d.work_date,d.emp_id    \n" + 
			"                ) e \n" + 
			"                    on e.emp_id=a.emp_id ORDER BY a.emp_nickname desc",nativeQuery=true)
	List<TwiceWeekHours> getPrev4DayWorkLog(@Param("empType") int empType);
    
	

	
	//Sachin 26032020
	@Query(value="SELECT a.emp_email, a.emp_id,a.emp_nickname,a.emp_type,a.unique_id,COALESCE(b.today,0)as day1,COALESCE(c.today1,0)as day2, COALESCE(d.today2,0) as day3 , 'NA' as day4, 'NA' AS dayname4,  \n" + 
			"			 \n" + 
			"			   dayname(:prevDate1)   as dayname1, "
			+ " dayname(:prevDate2)   as dayname2 , dayname(:prevDate3)   as dayname3     , \n" + 
			"			 \n" + 
			"			(ADDTIME(COALESCE(b.today,0),ADDTIME(COALESCE(c.today1,0),COALESCE(d.today2,0)))) as tot_hrs, \n" + 
			"			 \n" + 
			"			TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC((ADDTIME(COALESCE(b.today,0),ADDTIME(COALESCE(c.today1,0),COALESCE(d.today2,0)))))/3), '%H:%i') as avg_tot_hrs \n" + 
			"			 \n" + 
			"			FROM ( \n" + 
			"			SELECT UUID() as unique_id, m_emp.emp_id,m_emp.emp_name as emp_nickname,m_emp.emp_type,m_emp.emp_email  FROM m_emp WHERE m_emp.del_status=1 and m_emp.is_active=1 and m_emp.emp_type=:empType ) a  \n" + 
			"			LEFT JOIN  \n" + 
			"			(  \n" + 
			"			 \n" + 
			"			SELECT \n" + 
			"			d.emp_id, \n" + 
			"			         \n" + 
			"			        CONCAT(FLOOR(SUM(d.work_hours)/60), \n" + 
			"			        ':', \n" + 
			"			        LPAD(MOD(SUM(d.work_hours), \n" + 
			"			        60), \n" + 
			"			        2, \n" + 
			"			        '0')) as today  \n" + 
			"			        FROM t_daily_work_log d \n" + 
			"			        WHERE  d.work_date=:prevDate1  group by d.work_date,d.emp_id  \n" + 
			"			         \n" + 
			"			) b on a.emp_id=b.emp_id \n" + 
			"			 \n" + 
			"			LEFT JOIN  \n" + 
			"			(  \n" + 
			"			SELECT \n" + 
			"			d.emp_id, \n" + 
			"			         \n" + 
			"			        CONCAT(FLOOR(SUM(d.work_hours)/60), \n" + 
			"			        ':', \n" + 
			"			        LPAD(MOD(SUM(d.work_hours), \n" + 
			"			        60), \n" + 
			"			        2, \n" + 
			"			        '0')) as today1  \n" + 
			"			        FROM t_daily_work_log d \n" + 
			"			        WHERE  d.work_date=:prevDate2 group by d.work_date,d.emp_id  \n" + 
			"			    ) c on c.emp_id=a.emp_id \n" + 
			"			 \n" + 
			"			LEFT JOIN  \n" + 
			"			(  \n" + 
			"			SELECT \n" + 
			"			d.emp_id, \n" + 
			"			         \n" + 
			"			        CONCAT(FLOOR(SUM(d.work_hours)/60), \n" + 
			"			        ':', \n" + 
			"			        LPAD(MOD(SUM(d.work_hours), \n" + 
			"			        60), \n" + 
			"			        2, \n" + 
			"			        '0')) as today2 \n" + 
			"			        FROM t_daily_work_log d \n" + 
			"			        WHERE  d.work_date=:prevDate3 group by d.work_date,d.emp_id  \n" + 
			"			    ) d on d.emp_id=a.emp_id ORDER BY a.emp_nickname desc", nativeQuery=true)
	List<TwiceWeekHours> sendLogByPostman(@Param("empType") int empType,@Param("prevDate1") String prevDate1,@Param("prevDate2") String prevDate2,@Param("prevDate3") String prevDate3); 

	
	
	
}
