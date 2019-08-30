package com.ats.cataskapi.common;

import java.util.Properties;


import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ats.cataskapi.model.Info;

/*<dependency>
<groupId>javax.mail</groupId>
<artifactId>mail</artifactId>
<version>1.4</version>
</dependency>*/
 
public class EmailUtility {
	public static Info sendEmail(String senderEmail,String senderPassword,String recipientEmail,String mailsubject,
			String defUsrName,String defPass) {
			
			Info info=new Info();
			
			try {
				
			final String emailSMTPserver = "smtp.gmail.com";
			final String emailSMTPPort = "587";
			final String mailStoreType = "imaps";
			final String username = "atsinfosoft@gmail.com";
			final String password ="atsinfosoft@123";

			System.out.println("username" + username);
			System.out.println("password" + password);

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

				String address =recipientEmail;// "atsinfosoft@gmail.com";// address of to

				String subject = mailsubject;//" Login Credentials For RUSA Login  ";

				Message mimeMessage = new MimeMessage(session);
				mimeMessage.setFrom(new InternetAddress(username));
				mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
				mimeMessage.setSubject(subject);
				mimeMessage.setText(defPass);
				
			
				Transport.send(mimeMessage);
			} catch (Exception e) {
				e.printStackTrace();
				info.setError(true);
				info.setMsg("email_exce");
			}
				
				info.setError(false);
				info.setMsg("success_email");
			}catch (Exception e) {
				
				e.printStackTrace();
				info.setError(true);
				info.setMsg("email_exce");
			}
			
			return info;
			
		}
		
		public static Info sendMsg(String userName,String pass, String phoneNo) {
				
				Info info=new Info();
				
				try {
					   
					RestTemplate restTemplate = new RestTemplate();
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
					map.add("authkey", "74499AcqeCdljW5ae561dd");
					map.add("mobiles", phoneNo);
					map.add("message", "RUSA CREDENTIAL Your User Name :" + userName +" Your Password :" + pass +" Plz Dont Share To Any One ");
					map.add("sender", "ESYRTO");
					map.add("route", "4");
					map.add("country", "91");
					String response = restTemplate.postForObject("http://control.bestsms.co.in/api/sendhttp.php", map, String.class);
					
					info.setError(false);
					info.setMsg(response);
				  
				}catch (Exception e) {
					
					info.setError(true);
					info.setMsg("email_exce");
				}
				
				return info;
				
			}


}
