package com.ats.cataskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.cataskapi.model.PartnerEmployeeHrs;

public interface PartnerEmployeeHrsRepository extends JpaRepository<PartnerEmployeeHrs, Integer>{

	@Query(value="select uuid() as id,c.owner_emp_id as partner_id,coalesce((select emp_name from m_emp where emp_id=c.owner_emp_id),'-') as partner_name, e.emp_id,e.emp_name,"
			+ "sum(wl.work_hours) as total_min,CONCAT(FLOOR(sum(wl.work_hours)/60), '.', LPAD(MOD(sum(wl.work_hours),60),2,'0')) as total_hrs from m_emp e, "
			+ "t_daily_work_log wl,t_tasks t,m_cust_header c  where  wl.work_date between :fromDate and :toDate and e.del_status=1 "
			+ "and e.emp_type!=2  and wl.emp_id=e.emp_id and wl.task_id=t.task_id and c.cust_id=t.cust_id "
			+ "group by e.emp_id,c.owner_emp_id order by partner_id",nativeQuery=true)
	List<PartnerEmployeeHrs> employeepartnerwiseworkreport(@Param("fromDate") String fromDate,@Param("toDate") String toDate);

	@Query(value="select uuid() as id,e.emp_id as partner_id,e.emp_name as partner_name,wl.emp_id,coalesce((select emp_name from m_emp where emp_id=wl.emp_id),'-') as emp_name,"
			+ "sum(wl.work_hours) as total_min,CONCAT(FLOOR(sum(wl.work_hours)/60), '.', LPAD(MOD(sum(wl.work_hours),60),2,'0')) as total_hrs from m_emp e,t_daily_work_log wl, "
			+ "t_tasks t  where e.del_status=1 and e.emp_type=2  and wl.work_date between :fromDate and :toDate "
			+ "and wl.emp_id in (select emp_id from m_emp where emp_type!=2 and e.del_status=1) and t.task_id=wl.task_id  "
			+ "and FIND_IN_SET(e.emp_id,task_emp_ids) group by e.emp_id,wl.emp_id order by partner_id",nativeQuery=true)
	List<PartnerEmployeeHrs> employeeexcutionpartnerwiseworkreport(@Param("fromDate") String fromDate,@Param("toDate") String toDate);

	@Query(value="select emp_id from m_emp where del_status=1 and emp_type=2",nativeQuery=true)
	List<Integer> getpartnerList();

	@Query(value="select emp_id from m_emp where del_status=1 and emp_type!=2",nativeQuery=true)
	List<Integer> getempList();

}
