package com.ats.cataskapi.model.mailnotif;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TwiceWeekHours {

	@Id
	private String uniqueId;
	
	private int empId;
	private String empNickname;
	private int empType;
	private String empEmail;
	
	private String day1;
	private String day2;
	private String day3;
	private String day4;
	
	private String dayname1;
	private String dayname2;
	private String dayname3;
	private String dayname4;
	
	private String totHrs;
	private String avgTotHrs;
	
	public int getEmpType() {
		return empType;
	}
	public void setEmpType(int empType) {
		this.empType = empType;
	}
	
	
	
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpNickname() {
		return empNickname;
	}
	public void setEmpNickname(String empNickname) {
		this.empNickname = empNickname;
	}
	public String getDay1() {
		return day1;
	}
	public void setDay1(String day1) {
		this.day1 = day1;
	}
	public String getDay2() {
		return day2;
	}
	public void setDay2(String day2) {
		this.day2 = day2;
	}
	public String getDay3() {
		return day3;
	}
	public void setDay3(String day3) {
		this.day3 = day3;
	}
	public String getDayname1() {
		return dayname1;
	}
	public void setDayname1(String dayname1) {
		this.dayname1 = dayname1;
	}
	public String getDayname2() {
		return dayname2;
	}
	public void setDayname2(String dayname2) {
		this.dayname2 = dayname2;
	}
	public String getDayname3() {
		return dayname3;
	}
	public void setDayname3(String dayname3) {
		this.dayname3 = dayname3;
	}
	public String getTotHrs() {
		return totHrs;
	}
	public void setTotHrs(String totHrs) {
		this.totHrs = totHrs;
	}
	public String getAvgTotHrs() {
		return avgTotHrs;
	}
	public void setAvgTotHrs(String avgTotHrs) {
		this.avgTotHrs = avgTotHrs;
	}
	public String getDay4() {
		return day4;
	}
	public void setDay4(String day4) {
		this.day4 = day4;
	}
	public String getDayname4() {
		return dayname4;
	}
	public void setDayname4(String dayname4) {
		this.dayname4 = dayname4;
	}
	
	
	
	public String getEmpEmail() {
		return empEmail;
	}
	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}
	@Override
	public String toString() {
		return "TwiceWeekHours [uniqueId=" + uniqueId + ", empId=" + empId + ", empNickname=" + empNickname
				+ ", empType=" + empType + ", empEmail=" + empEmail + ", day1=" + day1 + ", day2=" + day2 + ", day3="
				+ day3 + ", day4=" + day4 + ", dayname1=" + dayname1 + ", dayname2=" + dayname2 + ", dayname3="
				+ dayname3 + ", dayname4=" + dayname4 + ", totHrs=" + totHrs + ", avgTotHrs=" + avgTotHrs + "]";
	}
	
	
	
	
	/* 6:27 pm for 4 day query.
	 SELECT
        a.emp_id,
        a.emp_nickname,
        COALESCE(b.today,
        0)as day1,
        COALESCE(c.today1,
        0)as day2,
        COALESCE(d.today2,
        0) as day3 ,
        
        COALESCE(e.today3,
        0) as day4 ,
        
        
        dayname((select
            CURDATE() - INTERVAL 1 DAY 
        FROM
            DUAL))   as dayname1,
        dayname((select
            CURDATE() - INTERVAL 2 DAY 
        FROM
            DUAL))   as dayname2 ,
        dayname((select
            CURDATE() - INTERVAL 3 DAY 
        FROM
            DUAL))   as dayname3     ,
            
            dayname((select
            CURDATE() - INTERVAL 4 DAY 
        FROM
            DUAL))   as dayname4     ,
            
            
        ADDTIME(COALESCE(b.today,(ADDTIME(COALESCE(b.today1,
        0),
        ADDTIME(COALESCE(c.today2,
        0),
        COALESCE(d.today3,
        0))))) as tot_hrs,
        TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(ADDTIME(COALESCE(b.today,0),(ADDTIME(COALESCE(b.today1,0), ADDTIME(COALESCE(c.today2, 0),COALESCE(d.today3,0))))))/4),
        '%H:%i') as avg_tot_hrs  
    FROM
        ( SELECT
            m_emp.emp_id,
            m_emp.emp_nickname  
        FROM
            m_emp 
        WHERE
            m_emp.del_status=1 
            and m_emp.is_active=1) a  
    LEFT JOIN
        (
            SELECT
                d.emp_id,
                CONCAT(FLOOR(d.work_hours/60),
                ':',
                LPAD(MOD(d.work_hours,
                60),
                2,
                '0')) as today          
            FROM
                t_daily_work_log d         
            WHERE
                d.work_date= (
                    select
                        CURDATE() - INTERVAL 1 DAY 
                    FROM
                        DUAL
                )          
            ) b 
                on a.emp_id=b.emp_id  
        LEFT JOIN
            (
                SELECT
                    d.emp_id,
                    CONCAT(FLOOR(d.work_hours/60),
                    ':',
                    LPAD(MOD(d.work_hours,
                    60),
                    2,
                    '0')) as today1          
                FROM
                    t_daily_work_log d         
                WHERE
                    d.work_date= (
                        select
                            CURDATE() - INTERVAL 2 DAY 
                        FROM
                            DUAL
                    )     
                ) c 
                    on c.emp_id=a.emp_id  
            LEFT JOIN
                (
                    SELECT
                        d.emp_id,
                        CONCAT(FLOOR(d.work_hours/60),
                        ':',
                        LPAD(MOD(d.work_hours,
                        60),
                        2,
                        '0')) as today2         
                    FROM
                        t_daily_work_log d         
                    WHERE
                        d.work_date= (
                            select
                                CURDATE() - INTERVAL 3 DAY 
                            FROM
                                DUAL
                        )     
                    ) d 
                        on d.emp_id=a.emp_id
                        
                        
                        
                         LEFT JOIN
                (
                    SELECT
                        d.emp_id,
                        CONCAT(FLOOR(d.work_hours/60),
                        ':',
                        LPAD(MOD(d.work_hours,
                        60),
                        2,
                        '0')) as today3         
                    FROM
                        t_daily_work_log d         
                    WHERE
                        d.work_date= (
                            select
                                CURDATE() - INTERVAL 4 DAY 
                            FROM
                                DUAL
                        )     
                    ) e
                        on e.emp_id=a.emp_id
	 */
	
	
}
