package com.ats.cataskapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.cataskapi.model.mailnotif.TwiceWeekHours;
import com.ats.cataskapi.repositories.mailnotif.TwiceWeekHoursRepo;

@RestController
public class ScheduleController {

	@Autowired
	TwiceWeekHoursRepo twiceWeekHoursRepo;
	
	@RequestMapping(value = { "/sendWeekHoursEmail" }, method = RequestMethod.GET)
	public @ResponseBody Object sendWeekHoursEmail() {

		int count = 0;
		try {
			
			List<TwiceWeekHours> workLogList=new ArrayList<>();
			try {
				
				workLogList=twiceWeekHoursRepo.getPrev3DayWorkLog();
				final String emailSMTPserver = "smtp.gmail.com";
				final String emailSMTPPort = "587";
				final String mailStoreType = "imaps";
				final String username = "atsinfosoft@gmail.com";
				final String password ="atsinfosoft@123";

				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "587");
				props.put("mail.smtp.starttls.enable", "true");

				Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

				try {
					Store mailStore = session.getStore(mailStoreType);
					mailStore.connect(emailSMTPserver, username, password);

					String address ="handgesachin1@gmail.com";// "atsinfosoft@gmail.com";// address of to

					String subject = "Test Email";//" Login Credentials For RUSA Login  ";

					Message mimeMessage = new MimeMessage(session);
					mimeMessage.setFrom(new InternetAddress(username));
					mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
					mimeMessage.setSubject(subject);
					 StringBuilder email=new StringBuilder();
					  email.append("<html><body>"
					                    + "<table style='border:2px solid black' border=1>");
					  email.append("<tr bgcolor=\"#ffae4c\">");
					            email.append("<th style='text-align:center;' colspan=6>");
					            email.append("Work Log Data");
					            email.append("</th>");
					            email.append("</tr>");
					            
					            email.append("<tr bgcolor=\"#ffae4c\">");
					            email.append("<th>");
					            email.append("Week Day");
					            email.append("</th>");

					            email.append("<th rowspan=2>");
					           // email.append("Thursday"+workLogList.get(0).getDayname3());
					            email.append(""+workLogList.get(0).getDayname3());

					            email.append("</th>");
					            
					            email.append("<th rowspan=2>");
					           // email.append("Friday" +workLogList.get(0).getDayname2());
					            email.append(""+workLogList.get(0).getDayname2());
						           
					            email.append("</th>");
					            
					            email.append("<th rowspan=2>");
					           // email.append("Saturday" +workLogList.get(0).getDayname1());
					            email.append(""+workLogList.get(0).getDayname1());
						           
					            email.append("</th>");
					            
					            email.append("<th>");
					            email.append("");
					            email.append("</th>");
					            
					            email.append("<th>");
					            email.append("");
					            email.append("</th>");
					      
					            
					            email.append("</tr>");
					            
					            //Second TR
					            email.append("<tr bgcolor=\"#ffae4c\">");
					            email.append("<th>");
					            email.append("Employee");
					            email.append("</th>");

					/*
					 * email.append("<th rowspan=2>"); email.append(""); email.append("</th>");
					 * 
					 * email.append("<th rowspan=2>"); email.append(""); email.append("</th>");
					 * 
					 * email.append("<th rowspan=2>"); email.append(""); email.append("</th>");
					 */
					            
					            email.append("<th>");
					            email.append("Total");
					            email.append("</th>");
					            
					            email.append("<th>");
					            email.append("Average");
					            email.append("</th>");
					            
					            email.append("</tr>");
					            
					            for(int i=0;i<workLogList.size();i++) {
					            	TwiceWeekHours log=	workLogList.get(i);
					            email.append("<tr>");
					            email.append("<th>");
					            email.append(""+log.getEmpNickname());
					            email.append("</th>");

					            String bgcolor="";
					            if(log.getDay3().equals("0")) {
					            	System.err.println("In 0 ");
					            	 email.append("<th bgcolor=\"#ffae4c\">");
					            }else {
					            	email.append("<th>");
					            }
					            //email.append("<th "+bgcolor+">");
					            email.append(""+log.getDay3());
					            email.append("</th>");
					            
					            email.append("<th>");
					            email.append(""+log.getDay2());
					            email.append("</th>");
					      
					            email.append("<th>");
					            email.append(""+log.getDay1());
					            email.append("</th>");
					            
					            email.append("<th>");
					            email.append(""+log.getTotHrs());
					            email.append("</th>");
					      
					            email.append("<th>");
					            email.append(""+log.getAvgTotHrs());
					            email.append("</th>");
					      
					            email.append("</tr>");
					            }
					            email.append("</table></body></html><br>");

					 mimeMessage.setContent(""+email, "text/html");
					
				
					Transport.send(mimeMessage);
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				}catch (Exception e) {
					
					e.printStackTrace();
					
				}
				
			 
		} catch (Exception e) {
			
			System.err.println("Exce in sendWeekHoursEmail  " + e.getMessage());
			
		}

		return count;
	}
	
	
	/*
	 * SELECT a.emp_id,a.emp_nickname,COALESCE(b.today,0)as day1,COALESCE(c.today1,0)as day2, COALESCE(d.today2,0) as day3 ,



   dayname((select CURDATE() - INTERVAL 1 DAY FROM DUAL))   as dayname1,  dayname((select CURDATE() - INTERVAL 2 DAY FROM DUAL))   as dayname2 , dayname((select CURDATE() - INTERVAL 3 DAY FROM DUAL))   as dayname3     ,
 

(ADDTIME(COALESCE(b.today,0),ADDTIME(COALESCE(c.today1,0),COALESCE(d.today2,0)))) as tot_hrs,

TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC((ADDTIME(COALESCE(b.today,0),ADDTIME(COALESCE(c.today1,0),COALESCE(d.today2,0)))))/3), '%H:%i') as avg_tot_hrs




FROM (
SELECT m_emp.emp_id,m_emp.emp_nickname  FROM m_emp WHERE m_emp.del_status=1 and m_emp.is_active=1) a 
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
        FROM t_daily_work_log d
        WHERE  d.work_date= (select CURDATE() - INTERVAL 1 DAY FROM DUAL)
        
) b on a.emp_id=b.emp_id

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
        FROM t_daily_work_log d
        WHERE  d.work_date= (select CURDATE() - INTERVAL 2 DAY FROM DUAL)
    ) c on c.emp_id=a.emp_id

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
        FROM t_daily_work_log d
        WHERE  d.work_date= (select CURDATE() - INTERVAL 3 DAY FROM DUAL)
    ) d on d.emp_id=a.emp_id
        
        




	 */
}
