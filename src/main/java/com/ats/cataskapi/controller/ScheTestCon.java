package com.ats.cataskapi.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.ats.cataskapi.common.DateConvertor;
import com.ats.cataskapi.common.EmailUtility;
import com.ats.cataskapi.common.PeriodicityDates;
import com.ats.cataskapi.model.EmpListForDashboard;
import com.ats.cataskapi.model.EmployeeListWithAvailableHours;
import com.ats.cataskapi.model.EmployeeMaster;
import com.ats.cataskapi.model.TaskCountByStatus;
import com.ats.cataskapi.model.mailnotif.TwiceWeekHours;
import com.ats.cataskapi.repositories.EmpListForDashboardRepo;
import com.ats.cataskapi.repositories.EmployeeListWithAvailableHoursRepo;
import com.ats.cataskapi.repositories.EmployeeMasterRepo;
import com.ats.cataskapi.repositories.TaskCountByStatusRepo;
import com.ats.cataskapi.repositories.mailnotif.TwiceWeekHoursRepo;

import net.bytebuddy.asm.Advice.Return;

@RestController
public class ScheTestCon {
	@Autowired
	TwiceWeekHoursRepo twiceWeekHoursRepo;

	@Autowired
	EmployeeListWithAvailableHoursRepo empListForLeave;
	@RequestMapping(value = { "/newEmailTest" }, method = RequestMethod.GET)
	public @ResponseBody List<String> sendWeekHoursEmail() {
		List<String> mailToAddress = new ArrayList<>();

		int count = 0;
		try {

			List<Integer> empTypeIds = new ArrayList<>();

			empTypeIds.add(5);
			empTypeIds.add(4);
			empTypeIds.add(3);
			empTypeIds.add(2);

			for (int j = 0; j < empTypeIds.size(); j++) {
				System.err.println("Type Id" + empTypeIds.get(j));
				if (empTypeIds.get(j) == 2) {
					System.err.println("Its Ignore 2");
					continue;
				}
				if (empTypeIds.get(j) == 4) {
					System.err.println("Its Ignore 4");
					continue;
				}
				if (empTypeIds.get(j) == 3) {
					System.err.println("Its Ignore 3");
					continue;
				}


				mailToAddress = twiceWeekHoursRepo.getEmailIds("" + empTypeIds.get(j));

				System.err.println("mailToAddress  " + mailToAddress);
				List<String> ccToAddress = new ArrayList<>();

				if (empTypeIds.get(j) == 5) {
					//ccToAddress = twiceWeekHoursRepo.getEmailIds("2,3");
				} else if (empTypeIds.get(j) == 3) {
					//ccToAddress = twiceWeekHoursRepo.getEmailIds("2");
				}

				// System.err.println("address email2 " + ccToAddress);
				// System.err.println("ad2" + ccToAddress + "\n mailToAddress" + mailToAddress);

				try {
					List<TwiceWeekHours> workLogList = new ArrayList<>();
					workLogList = twiceWeekHoursRepo.getPrev3DayWorkLog(empTypeIds.get(j));
					System.err.println("work Log List " +workLogList.toString());
					System.err.println("work Log List size" +workLogList.size());


					String date1 = DateControl.getDate(-1);
					String date2 = DateControl.getDate(-2);
					String date3 = DateControl.getDate(-3);

					for (int a = 0; a < workLogList.size(); a++) {
					//	if(workLogList.get(a).getEmpId()==44) {
						int zeroHrCount = 0;
						System.out.println("First Record  " +workLogList.get(a).getEmpEmail()+" "+workLogList.get(a).getEmpNickname());
						if (workLogList.get(a).getDay1().equals("0")) {
							System.err.println("Its zero day 1");
							// check if he was on leave
							List<EmployeeListWithAvailableHours> empLeave = empListForLeave
									.getLeaveRecordByEmpIdSac(date1, date1, workLogList.get(a).getEmpId());
							System.err.println("empLeave 1 " +empLeave.size());
							// if yes mark as leave
							// set zeroHrCount
							if (empLeave.size()>0) {
								zeroHrCount = zeroHrCount + 1;
								workLogList.get(a).setDay1("Leave");
							}
						} 
						if (workLogList.get(a).getDay2().equals("0")) {

							List<EmployeeListWithAvailableHours> empLeave = empListForLeave
									.getLeaveRecordByEmpIdSac(date2, date2, workLogList.get(a).getEmpId());
							System.err.println("empLeave 2 " +empLeave.toString());

							if (empLeave.size()>0) {
								zeroHrCount = zeroHrCount + 1;
								workLogList.get(a).setDay2("Leave");
							}

						}
						if (workLogList.get(a).getDay3().equals("0")) {
							List<EmployeeListWithAvailableHours> empLeave = empListForLeave
									.getLeaveRecordByEmpIdSac(date3, date3, workLogList.get(a).getEmpId());
							System.err.println("empLeave 3 " +empLeave.toString());

							if (empLeave.size()>0) {
								zeroHrCount = zeroHrCount + 1;
								workLogList.get(a).setDay3("Leave");
							}

						}
System.err.println("zeroHrCount "+zeroHrCount);
						if (zeroHrCount == 3) {
							// remove from mailToAddress
							System.err.println("Removing from mail to address" + workLogList.get(a).getEmpEmail());
							mailToAddress.remove(workLogList.get(a).getEmpEmail());
						}

					}
					//}
System.err.println(" work Log List end " +workLogList.toString());
System.err.println(" work Log List size end " +workLogList.size());
return mailToAddress;
				} catch (Exception e) {

					e.printStackTrace();

				}

			} // end of for loop for empTypeIds;

		} catch (Exception e) {

			System.err.println("Exce in sendWeekHoursEmail  " + e.getMessage());

		}
		return mailToAddress;

	}
	
	
	@Autowired
	TaskCountByStatusRepo taskCountByStatusRepo;
	@Autowired
	EmployeeMasterRepo empRepo;
	
	String username = "task.management@kppmca.com";
	String password = "De@8380077223";

	@Autowired
	EmpListForDashboardRepo empListForDashboardRepo;
	
	
	
	//@Scheduled(cron = "0 0 8 * * *")
	//public void dailyEmailTaskStausPartner() {
	@RequestMapping(value = { "/partTaskStat" }, method = RequestMethod.GET)
	public void dailyEmailTaskStausPartner() {
	int count = 0;
	try {

		List<Integer> empTypeIds = new ArrayList<>();

		empTypeIds.add(2);

		final String emailSMTPserver = "smtp.gmail.com";
		final String emailSMTPPort = "587";
		final String mailStoreType = "imaps";
		// final String username = "atsinfosoft@gmail.com";
		// final String password = "atsinfosoft@123";

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

		Store mailStore = session.getStore(mailStoreType);
		mailStore.connect(emailSMTPserver, username, password);

		for (int j = 0; j < empTypeIds.size(); j++) {

			System.err.println("Type Id" + empTypeIds.get(j));

			List<EmployeeMaster> empList = new ArrayList<EmployeeMaster>();
			try {
				empList = empRepo.findByDelStatusAndIsActiveAndEmpTypeOrderByEmpIdDesc(1, 1, empTypeIds.get(j)); // Fetched
																													// those
																													// employees
				// which are partner type
			} catch (Exception e) {
				System.err.println("Exce in getAllEmployees  " + e.getMessage());
			}

			try {
				int isEmpty=0;
				for (int k = 0; k < empList.size(); k++) {
					StringBuilder email = new StringBuilder();
					isEmpty=0;
					Message mimeMessage = new MimeMessage(session);
					mimeMessage.setFrom(new InternetAddress(username));
					
					int partnerId=empList.get(k).getEmpId();
					List<EmpListForDashboard> dataList = new ArrayList<>();

							try {

								dataList = empListForDashboardRepo.getManagerEmpList();

								Date date = new Date();
								SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

								for (int i = 0; i < dataList.size(); i++) {

									List<TaskCountByStatus> list = new ArrayList<TaskCountByStatus>();
									list = taskCountByStatusRepo.getTaskCountByStatus(sf.format(date), dataList.get(i).getEmpId(), partnerId);
									dataList.get(i).setList(list);
								}

							} catch (Exception e) {

								e.printStackTrace();
							}


					try {


						String subject = "Task Status Breakdown Partner ";// " Login Credentials For RUSA Login ";

						

					
						mimeMessage.setRecipients(Message.RecipientType.TO,
								InternetAddress.parse(empList.get(k).getEmpEmail()));

						// mimeMessage.setRecipients(Message.RecipientType.CC,

						mimeMessage.setSubject(subject);
						email.append("<html><body>" + "<table style='border:2px solid black' border=1>");
						email.append("<tr bgcolor=\"#ffae4c\">");
						email.append("<th style='text-align:center;' colspan=6>");
						DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

						Date date1 = Calendar.getInstance().getTime();
						dateFormat1.format(date1);
						String crDtTime = dateFormat1.format(date1);
						email.append("Task Status breakdown  " + crDtTime);

						email.append("</th>");
						email.append("</tr>");

						email.append("<tr bgcolor=\"#ffae4c\">");

						email.append("<th>");
						email.append("Manager Name");
						email.append("</th>");
						
						email.append("<th>");
						email.append("Status Wise Task");
						email.append("</th>");

						email.append("<th>");
						email.append("Overdue");
						email.append("</th>");

						email.append("<th>");
						email.append("Due Today");
						email.append("</th>");

						email.append("<th>");
						email.append("Due This Week");
						email.append("</th>");

						email.append("<th>");
						email.append("Due This Month");
						email.append("</th>");

						email.append("</tr>");
						for (int i = 0; i < dataList.size(); i++) {
							//TaskCountByStatus log = dataList.get(i);
							List<TaskCountByStatus> list=dataList.get(i).getList();
							
							for(int b=0;b<list.size();b++) {
								TaskCountByStatus task=list.get(b);
								
								if(task.getOverdeu()>0||task.getDuetoday()>0||task.getMonth()>0||task.getWeek()>0) {
									isEmpty=1;
								email.append("<tr>");
								email.append("<th style='text-align:left;'>");
								email.append("" /* + (index) + ")" */ + dataList.get(i).getEmpName());
								email.append("</th>");
								email.append("<th>");
								email.append("" + task.getStatusText());
								email.append("</th>");
								email.append("<th>");
								email.append("" + task.getOverdeu());
								email.append("</th>");

								email.append("<th>");
								email.append("" + task.getDuetoday());
								email.append("</th>");

								email.append("<th>");
								email.append("" + task.getWeek());
								email.append("</th>");

								email.append("<th>");
								email.append("" + task.getMonth());
								email.append("</th>");

								email.append("</tr>");
								
							}else {
								
							}
								//end of list for loop
							}
						}//end of datalist for loop
						
						
					
					} catch (Exception e) {
						e.printStackTrace();
					}

					email.append("</table></body></html><br>");

					mimeMessage.setContent("" + email, "text/html");
					System.err.println("isEmpty ="+isEmpty);
					if(isEmpty==1)
					Transport.send(mimeMessage);	
					
				} // for loop emplist
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} catch (Exception e) {
		System.err.println("Exce in sendWeekHoursEmail  " + e.getMessage());
	}
	// return count;
	}
	//Sachin 26-03-2020
	@RequestMapping(value = { "/sendTestEmail" }, method = RequestMethod.GET)
	public @ResponseBody String sendTestEmail(@RequestParam String empType) {
		
		try {
		List<String> mailToAddress = twiceWeekHoursRepo.getEmailIds(empType);
		String listString = String.join(", ", mailToAddress);

		EmailUtility.sendEmailNotif("Test Email Please Ignore", "Please dont reply to it, its test email", listString);

		}catch (HttpClientErrorException e) {
			return e.getResponseBodyAsString();
		}
		return "Hello";
	}
	
//Used In Software at Admin
	@RequestMapping(value = { "/sendLogEmail" }, method = RequestMethod.POST)
	public @ResponseBody String sendLogEmail(@RequestParam String fromDate) {
		System.err.println("fromdate "+fromDate);
	int count = 0;
	fromDate=DateConvertor.convertToYMD(fromDate);
	String prevDate1,prevDate2,prevDate3,prevDate4;
	System.err.println("fromdate "+fromDate);

	prevDate1=PeriodicityDates.addDaysToGivenDate(fromDate, -1);
	prevDate2=PeriodicityDates.addDaysToGivenDate(fromDate, -2);
	prevDate3=PeriodicityDates.addDaysToGivenDate(fromDate, -3);
	prevDate4=PeriodicityDates.addDaysToGivenDate(fromDate, -4);
	try {

		List<Integer> empTypeIds = new ArrayList<>();

		empTypeIds.add(5);
		empTypeIds.add(4);
		empTypeIds.add(3);
		empTypeIds.add(2);

		for (int j = 0; j < empTypeIds.size(); j++) {
			System.err.println("Type Id" + empTypeIds.get(j));
			if (empTypeIds.get(j) == 2) {
				System.err.println("Its Ignore 2");
				continue;
			}
			if (empTypeIds.get(j) == 4) {
				System.err.println("Its Ignore 4");
				continue;
			}

			List<String> mailToAddress = new ArrayList<>();

			mailToAddress = twiceWeekHoursRepo.getEmailIds("" + empTypeIds.get(j));

			System.err.println("mailToAddress  " + mailToAddress);
			List<String> ccToAddress = new ArrayList<>();

			if (empTypeIds.get(j) == 5) {
				ccToAddress = twiceWeekHoursRepo.getEmailIds("2,3");
			} else if (empTypeIds.get(j) == 3) {
				ccToAddress = twiceWeekHoursRepo.getEmailIds("2");
			}

			// System.err.println("address email2 " + ccToAddress);
			// System.err.println("ad2" + ccToAddress + "\n mailToAddress" + mailToAddress);

			try {
				List<TwiceWeekHours> workLogList = new ArrayList<>();
				workLogList = twiceWeekHoursRepo.sendLogByPostman(empTypeIds.get(j),prevDate1,prevDate2,prevDate3);
//System.err.println("--workLogList " +workLogList.toString());
				String date1 = prevDate1;//DateControl.getDateByDateAndSubDays(pre)
				String date2 =prevDate2; //DateControl.getDate(-2);
				String date3 =prevDate3;// DateControl.getDate(-3);

				

				for (int a = 0; a < workLogList.size(); a++) {
					// if(workLogList.get(a).getEmpId()==44) {
					int zeroHrCount = 0;
				
					if (workLogList.get(a).getDay1().equals("0")) {
						System.err.println("Its zero day 1");
						// check if he was on leave
						List<EmployeeListWithAvailableHours> empLeave = empListForLeave
								.getLeaveRecordByEmpIdSac(date1, date1, workLogList.get(a).getEmpId());
						// if yes mark as leave
						// set zeroHrCount
						if (empLeave.size() > 0) {
							zeroHrCount = zeroHrCount + 1;
							workLogList.get(a).setDay1("Leave");
						}
					}
					if (workLogList.get(a).getDay2().equals("0")) {

						List<EmployeeListWithAvailableHours> empLeave = empListForLeave
								.getLeaveRecordByEmpIdSac(date2, date2, workLogList.get(a).getEmpId());

						if (empLeave.size() > 0) {
							zeroHrCount = zeroHrCount + 1;
							workLogList.get(a).setDay2("Leave");
						}

					}
					if (workLogList.get(a).getDay3().equals("0")) {
						List<EmployeeListWithAvailableHours> empLeave = empListForLeave
								.getLeaveRecordByEmpIdSac(date3, date3, workLogList.get(a).getEmpId());

						if (empLeave.size() > 0) {
							zeroHrCount = zeroHrCount + 1;
							workLogList.get(a).setDay3("Leave");
						}

					}
					System.err.println("zeroHrCount " + zeroHrCount);
					if (zeroHrCount == 3) {
						// remove from mailToAddress
						System.err.println("Removing from mail to address" + workLogList.get(a).getEmpEmail());
						mailToAddress.remove(workLogList.get(a).getEmpEmail());
					}

				}

				final String emailSMTPserver = "smtp.gmail.com";
				final String emailSMTPPort = "587";
				final String mailStoreType = "imaps";
				// final String username = "atsinfosoft@gmail.com";
				// final String password = "atsinfosoft@123";

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

					String address = "handgesachin1@gmail.com";// "atsinfosoft@gmail.com";// address of to

					String subject = "Work Log Data- for "+fromDate ;// " Login Credentials For RUSA Login ";

					Message mimeMessage = new MimeMessage(session);
					mimeMessage.setFrom(new InternetAddress(username));
					System.err.println("mailToAddress.toString() " + mailToAddress.toString());
					String listString = String.join(", ", mailToAddress);
					String listString1 = String.join(", ", ccToAddress);

					mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(listString));
					mimeMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse(listString1));

					mimeMessage.setSubject(subject);
					StringBuilder email = new StringBuilder();
					email.append("<html><body>" + "<table style='border:2px solid black' border=1>");
					email.append("<tr bgcolor=\"#ffae4c\">");
					email.append("<th style='text-align:center;' colspan=6>");
					DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

					Date date = Calendar.getInstance().getTime();
					dateFormat1.format(date);
					String crDtTime = dateFormat1.format(date);
					if (empTypeIds.get(j) == 5) {
						email.append("Work Log Data   Emp " + crDtTime);
					} else if (empTypeIds.get(j) == 3) {
						email.append("Work Log Data  Manager " + crDtTime);
					} else if (empTypeIds.get(j) == 4) {
						email.append("Work Log Data  TL " + crDtTime);
					}

					email.append("</th>");
					email.append("</tr>");

					email.append("<tr bgcolor=\"#ffae4c\">");
					email.append("<th>");
					email.append("Week Day");
					email.append("</th>");

					email.append("<th rowspan=2>");
					// email.append("Thursday"+workLogList.get(0).getDayname3());
					email.append("" + workLogList.get(0).getDayname3());

					email.append("</th>");

					email.append("<th rowspan=2>");
					// email.append("Friday" +workLogList.get(0).getDayname2());
					email.append("" + workLogList.get(0).getDayname2());

					email.append("</th>");

					email.append("<th rowspan=2>");
					// email.append("Saturday" +workLogList.get(0).getDayname1());
					email.append("" + workLogList.get(0).getDayname1());

					email.append("</th>");

					email.append("<th>");
					email.append("");
					email.append("</th>");

					email.append("<th>");
					email.append("");
					email.append("</th>");

					email.append("</tr>");

					// Second TR
					email.append("<tr bgcolor=\"#ffae4c\">");
					email.append("<th>");
					email.append("Employee");
					email.append("</th>");
					email.append("<th>");
					email.append("Total");
					email.append("</th>");

					email.append("<th>");
					email.append("Average");
					email.append("</th>");

					email.append("</tr>");
					int index = 0;
					for (int i = 0; i < workLogList.size(); i++) {
						TwiceWeekHours log = workLogList.get(i);

						index++;
						email.append("<tr>");
						email.append("<th style='text-align:left;'>");
						email.append("" /* + (index) + ")" */ + log.getEmpNickname());
						email.append("</th>");

						String bgcolor = "";
						if (log.getDay3().equals("0")) {
							// System.err.println("In 0 ");
							email.append("<th bgcolor=\"#ff00\">");
						} else {
							email.append("<th>");
						}
						// email.append("<th "+bgcolor+">");
						email.append("" + log.getDay3());
						email.append("</th>");

						if (log.getDay2().equals("0")) {
							// System.err.println("In 0 ");
							email.append("<th bgcolor=\"#ff00\">");
						} else {
							email.append("<th>");
						}
						email.append("" + log.getDay2());
						email.append("</th>");

						if (log.getDay1().equals("0")) {
							// System.err.println("In 0 ");
							email.append("<th bgcolor=\"#ff00\">");
						} else {
							email.append("<th>");
						}
						email.append("" + log.getDay1());
						email.append("</th>");

						email.append("<th>");
						email.append("" + log.getTotHrs());
						email.append("</th>");

						email.append("<th>");
						email.append("" + log.getAvgTotHrs());
						email.append("</th>");

						email.append("</tr>");

					}
					email.append("</table></body></html><br>");

					mimeMessage.setContent("" + email, "text/html");
//System.err.println("email  \n "+email.toString());
				Transport.send(mimeMessage);

				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception e) {

				e.printStackTrace();

			}

		} // end of for loop for empTypeIds;

	} catch (Exception e) {

		System.err.println("Exce in sendWeekHoursEmail  " + e.getMessage());

	}
	return prevDate4;
	}

}
