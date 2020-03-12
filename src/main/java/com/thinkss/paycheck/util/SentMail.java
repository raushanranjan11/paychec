package com.thinkss.paycheck.util;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import javax.management.ReflectionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.thinkss.paycheck.entity.User;
//                   not using 
@Service
public class SentMail {
	
	@Autowired
	private static JavaMailSender sender;

	public static void sendMail(User user, String txtmessage, String title) {
		final String username = "raushan.jnv@gmail.com";
		final String password = "636317636317636317";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			// message.setFrom(new InternetAddress("raushan.jnv@gmail.com"));
			message.setFrom(new InternetAddress("PayChec"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmailId()));
			message.setSubject(title);
			StringBuilder html = new StringBuilder();
			html.append("<!DOCTYPE html><html><head><meta charset=\'utf-8\'></head><body >");

			html.append(
					"<div style ='background:white; height:450px; width:600px; position: fixed;top: 40%;left: 50%;-webkit-transform: translate(-50%, -50%);  transform: translate(-50%, -50%);'>");
			html.append(
					"<div style='background-image: url(http://185.78.163.41:8080/paychec/img/logo_dummy@2x.png); height: 430px; width: 600px;'>");
			html.append("<div style = 'padding-top:50px;'>");
			html.append("<p style='padding-left: 20px;'>Dear " + user.getFirstName() + ",</p>  ");///image/logo_dummy@2x.png
			html.append("<p style='margin-left: 3.5em'>   Your Paychec OTP " + txtmessage
					+ " (it will Expire in 10 minutes)  </p> </div> </div>");

			html.append(
					"<div style ='background:#4286f4; height:30px;'><p style='display:block;text-align: center;margin:auto; padding-top:5px;'>&copy; Copyright 2018 - all rights reserved to www.paychec.com </p> </div>");
			html.append(" </div>");

			html.append(" </body></html>");
			message.setContent(html.toString(), "text/html");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<String> getEndPoints()
			throws MalformedObjectNameException, NullPointerException, UnknownHostException, AttributeNotFoundException,
			InstanceNotFoundException, MBeanException, ReflectionException {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		Set<ObjectName> objs = mbs.queryNames(new ObjectName("*:type=Connector,*"),
				Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
		String hostname = InetAddress.getLocalHost().getHostName();
		InetAddress[] addresses = InetAddress.getAllByName(hostname);
		ArrayList<String> endPoints = new ArrayList<String>();
		for (Iterator<ObjectName> i = objs.iterator(); i.hasNext();) {
			ObjectName obj = i.next();
			String scheme = mbs.getAttribute(obj, "scheme").toString();
			String port = obj.getKeyProperty("port");
			for (InetAddress addr : addresses) {
				String host = addr.getHostAddress();
				String ep = scheme + "://" + host + ":" + port;
				endPoints.add(ep);
			}
		}
		return endPoints;
	}
	
	public  static void sendEmailwithProperty() throws Exception{
		
		System.out.println("*************************************");
		
       	/*MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo("raushanranjan85@gmail.com");
        helper.setText("How are you?");
        helper.setSubject("Hi");
        
        sender.send(message);*/
//		System.out.println(sender.);
		System.out.println(sender); 
		 MimeMessage message = sender.createMimeMessage();
		 System.out.println("^^^^^^^^^^^^^^^^^");
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo("raushanranjan85@gmail.com");
        helper.setText("<html><body>Here is a cat picture! <img src='cid:id101'/><body></html>", true);
        helper.setSubject("Hi");
        sender.send(message);
//        ClassPathResource file = new ClassPathResource("cat.jpg");
//        helper.addInline("id101", file);




}


}
