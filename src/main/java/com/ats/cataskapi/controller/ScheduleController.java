package com.ats.cataskapi.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ats.cataskapi.common.EmailUtility;
import com.ats.cataskapi.model.EmpListForDashboard;
import com.ats.cataskapi.model.EmployeeListWithAvailableHours;
import com.ats.cataskapi.model.EmployeeMaster;
import com.ats.cataskapi.model.TaskCountByStatus;
import com.ats.cataskapi.model.mailnotif.EmpHoursUpdate;
import com.ats.cataskapi.model.mailnotif.TwiceWeekHours;
import com.ats.cataskapi.repositories.EmpListForDashboardRepo;
import com.ats.cataskapi.repositories.EmployeeListWithAvailableHoursRepo;
import com.ats.cataskapi.repositories.EmployeeMasterRepo;
import com.ats.cataskapi.repositories.TaskCountByStatusRepo;
import com.ats.cataskapi.repositories.mailnotif.EmpHoursUpdateRepo;
import com.ats.cataskapi.repositories.mailnotif.TwiceWeekHoursRepo;

@Component
public class ScheduleController {

	String username = "task.management@kppmca.com";
	String password = "De@8380077223";

	@Autowired
	TwiceWeekHoursRepo twiceWeekHoursRepo;

	@Autowired
	EmployeeListWithAvailableHoursRepo empListForLeave;

	// @RequestMapping(value = { "/sendWeekHoursEmail" }, method =
	// RequestMethod.GET)
	// @Scheduled(cron = "0 0 0,21 * THU *")
	@Scheduled(cron = "0 0 6 * * 4") //M T W work Log email on Thursday
	public void sendWeekHoursEmail() {

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
					workLogList = twiceWeekHoursRepo.getPrev3DayWorkLog(empTypeIds.get(j));

					String date1 = DateControl.getDate(-1);
					String date2 = DateControl.getDate(-2);
					String date3 = DateControl.getDate(-3);

					

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

						String subject = "Work Log Data-";// " Login Credentials For RUSA Login ";

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

	}

	// @RequestMapping(value = { "/sendWeekHoursEmailThurs" }, method =
	// RequestMethod.GET)
	@Scheduled(cron = "0 0 6 * * 1")
	// public @ResponseBody Object sendWeekHoursEmailThurs() {
	public void sendWeekHoursEmailThurs() { //T F S Sun work Log email on Monday 6 am
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
					workLogList = twiceWeekHoursRepo.getPrev4DayWorkLog(empTypeIds.get(j));
					
					String date1 = DateControl.getDate(-1);
					String date2 = DateControl.getDate(-2);
					String date3 = DateControl.getDate(-3);
					
					String date4 = DateControl.getDate(-4);
					
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
						
						if (workLogList.get(a).getDay4().equals("0")) {
							List<EmployeeListWithAvailableHours> empLeave = empListForLeave
									.getLeaveRecordByEmpIdSac(date4, date4, workLogList.get(a).getEmpId());

							if (empLeave.size() > 0) {
								zeroHrCount = zeroHrCount + 1;
								workLogList.get(a).setDay4("Leave");
							}

						}
						
						System.err.println("zeroHrCount " + zeroHrCount);
						if (zeroHrCount == 4) {
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

						String subject = "Work Log Data -";// " Login Credentials For RUSA Login ";

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
						email.append("<th style='text-align:center;' colspan=7>");
						DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

						Date date = Calendar.getInstance().getTime();
						dateFormat1.format(date);
						String crDtTime = dateFormat1.format(date);
						if (empTypeIds.get(j) == 5) {
							email.append("Work Log Data Emp " + crDtTime);
						} else if (empTypeIds.get(j) == 3) {
							email.append("Work Log Data Manager " + crDtTime);
						} else if (empTypeIds.get(j) == 4) {
							email.append("Work Log Data TL " + crDtTime);
						}

						email.append("</th>");
						email.append("</tr>");

						email.append("<tr bgcolor=\"#ffae4c\">");
						email.append("<th>");
						email.append("Week Day");
						email.append("</th>");

						email.append("<th rowspan=2>");
						// email.append("Thursday"+workLogList.get(0).getDayname3());
						email.append("" + workLogList.get(0).getDayname4());
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

							if (log.getDay4().equals("0")) {
								// System.err.println("In 0 ");
								email.append("<th bgcolor=\"#ff00\">");
							} else {
								email.append("<th>");
							}
							// email.append("<th "+bgcolor+">");
							email.append("" + log.getDay4());
							email.append("</th>");

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

		// return count;
	}

	@Autowired
	EmpHoursUpdateRepo empHoursUpdateRepo;

	// @RequestMapping(value = { "/sendEmailForNotUpdatingWork" }, method =
	// RequestMethod.GET)
	@Scheduled(cron = "0 0 7 * * *")  //Daily email to those not updating 50 % work.. 7 am
	public void sendEmailForNotUpdatingWork() { 

		List<Integer> empTypeIds = new ArrayList<>();

		empTypeIds.add(5);
		empTypeIds.add(3);

		for (int i = 0; i < empTypeIds.size(); i++) {

			List<EmpHoursUpdate> empList = empHoursUpdateRepo.getEmpsNotUpdated50Perwork(empTypeIds.get(i));

			System.err.println("empList " + empList.toString());
			for (int j = 0; j < empList.size(); j++) {

				String hrs = EmailUtility.convertMinToHours("" + empList.get(j).getWorkHrs());
				System.err.println("Minutes " + empList.get(j).getWorkHrs() + "Hours" + hrs);

				String mailSubjet = "Regarding Task List  Update";
				String msgContent = "It has been noted that your task list not updated, you  have  updated " + hrs
						+ " hours for last two days. \n"
						+ "Kindly update the same at the earliest and if you are on leave  at this period kindly ignore.";
				EmailUtility.sendEmailNotif(mailSubjet, msgContent, empList.get(j).getEmpEmail());

				/*
				 * if (j == 3) { break; }
				 */
			}

		}

		// return "done";

	}

	@Autowired
	TaskCountByStatusRepo taskCountByStatusRepo;
	@Autowired
	EmployeeMasterRepo empRepo;

	@Scheduled(cron = "0 0 8 * * *") //Task status BD for Emp and Mng Daily 8 am
	public void dailyEmailTaskStaus() {

		int count = 0;
		try {

			List<Integer> empTypeIds = new ArrayList<>();

			empTypeIds.add(5);
			empTypeIds.add(3);

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

					for (int k = 0; k < empList.size(); k++) {

						List<TaskCountByStatus> list = new ArrayList<TaskCountByStatus>();

						Date date = new Date();
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

						list = taskCountByStatusRepo.getTaskCountByStatus(sf.format(date), empList.get(k).getEmpId(),
								empList.get(k).getEmpId());

						try {

							String address = "harshal.pahade@kppmca.in";// "atsinfosoft@gmail.com";// address of to

							String subject = "Task Status Breakdown ";// " Login Credentials For RUSA Login ";

							Message mimeMessage = new MimeMessage(session);
							mimeMessage.setFrom(new InternetAddress(username));

							/*
							 * mimeMessage.setRecipients(Message.RecipientType.TO,
							 * InternetAddress.parse(address));
							 */
							mimeMessage.setRecipients(Message.RecipientType.TO,
									InternetAddress.parse(empList.get(k).getEmpEmail()));

							// mimeMessage.setRecipients(Message.RecipientType.CC,

							mimeMessage.setSubject(subject);
							StringBuilder email = new StringBuilder();
							email.append("<html><body>" + "<table style='border:2px solid black' border=1>");
							email.append("<tr bgcolor=\"#ffae4c\">");
							email.append("<th style='text-align:center;' colspan=5>");
							DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

							Date date1 = Calendar.getInstance().getTime();
							dateFormat1.format(date1);
							String crDtTime = dateFormat1.format(date1);
							email.append("Task Status " + crDtTime);

							email.append("</th>");
							email.append("</tr>");

							email.append("<tr bgcolor=\"#ffae4c\">");

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

							for (int i = 0; i < list.size(); i++) {
								TaskCountByStatus log = list.get(i);

								if (log.getDuetoday() > 0 || log.getWeek() > 0 || log.getOverdeu() > 0
										|| log.getMonth() > 0) {

									email.append("<tr>");
									email.append("<th style='text-align:left;'>");
									email.append("" /* + (index) + ")" */ + log.getStatusText());
									email.append("</th>");

									email.append("<th>");
									email.append("" + log.getOverdeu());
									email.append("</th>");

									email.append("<th>");
									email.append("" + log.getDuetoday());
									email.append("</th>");

									email.append("<th>");
									email.append("" + log.getWeek());
									email.append("</th>");

									email.append("<th>");
									email.append("" + log.getMonth());
									email.append("</th>");

									email.append("</tr>");
								}
							}
							email.append("</table></body></html><br>");

							mimeMessage.setContent("" + email, "text/html");

							Transport.send(mimeMessage);

						} catch (Exception e) {
							e.printStackTrace();
						}

						//break; removed on 13-02-2020
					} // for loop emplist
				} catch (Exception e) {
					e.printStackTrace();

				}
			}

		} catch (Exception e) {

			System.err.println("Exce in dailyEmailTaskStaus Employee, Mng   " + e.getMessage());

		}

		// return count;
	}

	// 13-01-2020 Sachin

	// @RequestMapping(value = { "/sendWeekHoursEmail" }, method =
	// RequestMethod.GET)
	// @Scheduled(cron = "0 0 0,21 * THU *")
	@Scheduled(cron = "0 0 20 * * 4") // to be at night 8 pm
	public void sendWeekHoursEmailMng() { //DWL for M T W on THUR 8 pm Night
		System.err.println("THURS 8 PM");
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

				List<String> mailToAddress = new ArrayList<>();
				List<String> ccToAddress = new ArrayList<>();

				if (empTypeIds.get(j) == 5) {
					mailToAddress = twiceWeekHoursRepo.getEmailIds("3");
					ccToAddress = twiceWeekHoursRepo.getEmailIds("2");
				}

				else if (empTypeIds.get(j) == 3) {
					mailToAddress = twiceWeekHoursRepo.getEmailIds("2");
				}
				System.err.println("mailToAddress  " + mailToAddress);

				/*
				 * if (empTypeIds.get(j) == 5) { ccToAddress =
				 * twiceWeekHoursRepo.getEmailIds("2,3"); } else if (empTypeIds.get(j) == 3) {
				 * ccToAddress = twiceWeekHoursRepo.getEmailIds("2"); }
				 */

				// System.err.println("address email2 " + ccToAddress);
				// System.err.println("ad2" + ccToAddress + "\n mailToAddress" + mailToAddress);

				try {
					List<TwiceWeekHours> workLogList = new ArrayList<>();
					workLogList = twiceWeekHoursRepo.getPrev3DayWorkLog(empTypeIds.get(j));
					
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
						/*
						 * System.err.println("zeroHrCount "+zeroHrCount); if (zeroHrCount == 3) { //
						 * remove from mailToAddress System.err.println("Removing from mail to address"
						 * + workLogList.get(a).getEmpEmail());
						 * mailToAddress.remove(workLogList.get(a).getEmpEmail()); }
						 */

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

						String subject = "Work Log Data-";// " Login Credentials For RUSA Login ";

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

	}

	// @RequestMapping(value = { "/sendWeekHoursEmailThurs" }, method =
	// RequestMethod.GET)
	@Scheduled(cron = "0 0 20 * * 1") // For Monday 7 pm
	// public @ResponseBody Object sendWeekHoursEmailThurs() {
	public void sendWeekHoursEmailThurs8PMMonday() { //DWL for T F S Su on Monday 8 pm Night
		System.err.println("Mon 8 PM"); 
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

				List<String> mailToAddress = new ArrayList<>();
				List<String> ccToAddress = new ArrayList<>();

				if (empTypeIds.get(j) == 5) {
					mailToAddress = twiceWeekHoursRepo.getEmailIds("3");
					ccToAddress = twiceWeekHoursRepo.getEmailIds("2");
				} else if (empTypeIds.get(j) == 3) {
					mailToAddress = twiceWeekHoursRepo.getEmailIds("2");
				}
				System.err.println("mailToAddress  " + mailToAddress);

				/*
				 * if (empTypeIds.get(j) == 5) { ccToAddress =
				 * twiceWeekHoursRepo.getEmailIds("2,3"); } else if (empTypeIds.get(j) == 3) {
				 * ccToAddress = twiceWeekHoursRepo.getEmailIds("2"); }
				 */

				// System.err.println("address email2 " + ccToAddress);
				// System.err.println("ad2" + ccToAddress + "\n mailToAddress" + mailToAddress);

				try {
					List<TwiceWeekHours> workLogList = new ArrayList<>();
					workLogList = twiceWeekHoursRepo.getPrev4DayWorkLog(empTypeIds.get(j));
					
					
					String date1 = DateControl.getDate(-1);
					String date2 = DateControl.getDate(-2);
					String date3 = DateControl.getDate(-3);
					String date4 = DateControl.getDate(-4);

					for (int a = 0; a < workLogList.size(); a++) {
					//	if(workLogList.get(a).getEmpId()==44) {
						int zeroHrCount = 0;
						//System.out.println("First Record  " +workLogList.get(a).getEmpEmail()+" "+workLogList.get(a).getEmpNickname());
						if (workLogList.get(a).getDay1().equals("0")) {
						//	System.err.println("Its zero day 1");
							// check if he was on leave
							List<EmployeeListWithAvailableHours> empLeave = empListForLeave
									.getLeaveRecordByEmpIdSac(date1, date1, workLogList.get(a).getEmpId());
							//System.err.println("empLeave 1 " +empLeave.size());
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
							//System.err.println("empLeave 2 " +empLeave.toString());

							if (empLeave.size()>0) {
								zeroHrCount = zeroHrCount + 1;
								workLogList.get(a).setDay2("Leave");
							}

						}
						if (workLogList.get(a).getDay3().equals("0")) {
							List<EmployeeListWithAvailableHours> empLeave = empListForLeave
									.getLeaveRecordByEmpIdSac(date3, date3, workLogList.get(a).getEmpId());
							//System.err.println("empLeave 3 " +empLeave.toString());

							if (empLeave.size()>0) {
								zeroHrCount = zeroHrCount + 1;
								workLogList.get(a).setDay3("Leave");
							}

						}
						
						if (workLogList.get(a).getDay4().equals("0")) {
							List<EmployeeListWithAvailableHours> empLeave = empListForLeave
									.getLeaveRecordByEmpIdSac(date4, date4, workLogList.get(a).getEmpId());

							if (empLeave.size()>0) {
								zeroHrCount = zeroHrCount + 1;
								workLogList.get(a).setDay4("Leave");
							}

						}
						/*
						 * System.err.println("zeroHrCount "+zeroHrCount); if (zeroHrCount == 3) { //
						 * remove from mailToAddress System.err.println("Removing from mail to address"
						 * + workLogList.get(a).getEmpEmail());
						 * mailToAddress.remove(workLogList.get(a).getEmpEmail()); }
						 */

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

						String subject = "Work Log Data -";// " Login Credentials For RUSA Login ";

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
						email.append("<th style='text-align:center;' colspan=7>");
						DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

						Date date = Calendar.getInstance().getTime();
						dateFormat1.format(date);
						String crDtTime = dateFormat1.format(date);
						if (empTypeIds.get(j) == 5) {
							email.append("Work Log Data Emp " + crDtTime);
						} else if (empTypeIds.get(j) == 3) {
							email.append("Work Log Data Manager " + crDtTime);
						} else if (empTypeIds.get(j) == 4) {
							email.append("Work Log Data TL " + crDtTime);
						}

						email.append("</th>");
						email.append("</tr>");

						email.append("<tr bgcolor=\"#ffae4c\">");
						email.append("<th>");
						email.append("Week Day");
						email.append("</th>");

						email.append("<th rowspan=2>");
						// email.append("Thursday"+workLogList.get(0).getDayname3());
						email.append("" + workLogList.get(0).getDayname4());
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

							if (log.getDay4().equals("0")) {
								// System.err.println("In 0 ");
								email.append("<th bgcolor=\"#ff00\">");
							} else {
								email.append("<th>");
							}
							// email.append("<th "+bgcolor+">");
							email.append("" + log.getDay4());
							email.append("</th>");

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

		// return count;
	}

	@Autowired
	EmpListForDashboardRepo empListForDashboardRepo;
	//Sachin 13-02-2020
	@Scheduled(cron = "0 0 8 * * *")
	public void dailyEmailTaskBdStausPartner() { //Daily Partner Task Status BD 8 AM new Req Harshal Sir
		try {

			List<Integer> empTypeIds = new ArrayList<>();

			empTypeIds.add(2);

			final String emailSMTPserver = "smtp.gmail.com";
			final String emailSMTPPort = "587";
			final String mailStoreType = "imaps";

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
			System.err.println("Exce in send Partner Task Status BD  " + e.getMessage());
		}
		// return count;
		}
	

	// return count;

	/*
	 * SELECT a.emp_id,a.emp_nickname,COALESCE(b.today,0)as
	 * day1,COALESCE(c.today1,0)as day2, COALESCE(d.today2,0) as day3 ,
	 * 
	 * 
	 * 
	 * dayname((select CURDATE() - INTERVAL 1 DAY FROM DUAL)) as dayname1,
	 * dayname((select CURDATE() - INTERVAL 2 DAY FROM DUAL)) as dayname2 ,
	 * dayname((select CURDATE() - INTERVAL 3 DAY FROM DUAL)) as dayname3 ,
	 * 
	 * 
	 * (ADDTIME(COALESCE(b.today,0),ADDTIME(COALESCE(c.today1,0),COALESCE(d.today2,0
	 * )))) as tot_hrs,
	 * 
	 * TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC((ADDTIME(COALESCE(b.today,0),ADDTIME(
	 * COALESCE(c.today1,0),COALESCE(d.today2,0)))))/3), '%H:%i') as avg_tot_hrs
	 * 
	 * 
	 * 
	 * 
	 * FROM ( SELECT m_emp.emp_id,m_emp.emp_nickname FROM m_emp WHERE
	 * m_emp.del_status=1 and m_emp.is_active=1) a LEFT JOIN (
	 * 
	 * SELECT d.emp_id,
	 * 
	 * 
	 * 
	 * CONCAT(FLOOR(d.work_hours/60), ':', LPAD(MOD(d.work_hours, 60), 2, '0')) as
	 * today FROM t_daily_work_log d WHERE d.work_date= (select CURDATE() - INTERVAL
	 * 1 DAY FROM DUAL)
	 * 
	 * ) b on a.emp_id=b.emp_id
	 * 
	 * LEFT JOIN ( SELECT d.emp_id,
	 * 
	 * 
	 * 
	 * CONCAT(FLOOR(d.work_hours/60), ':', LPAD(MOD(d.work_hours, 60), 2, '0')) as
	 * today1 FROM t_daily_work_log d WHERE d.work_date= (select CURDATE() -
	 * INTERVAL 2 DAY FROM DUAL) ) c on c.emp_id=a.emp_id
	 * 
	 * LEFT JOIN ( SELECT d.emp_id,
	 * 
	 * CONCAT(FLOOR(d.work_hours/60), ':', LPAD(MOD(d.work_hours, 60), 2, '0')) as
	 * today2 FROM t_daily_work_log d WHERE d.work_date= (select CURDATE() -
	 * INTERVAL 3 DAY FROM DUAL) ) d on d.emp_id=a.emp_id
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

}
