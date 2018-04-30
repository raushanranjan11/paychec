package com.thinkss.paycheck.service.impl;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.service.SentMailService;

@Service
public class SentMailServiceImpl implements SentMailService{
	
	@Autowired
	public JavaMailSender sender;
	
	 public void sentMail(User user, String txtmessage, String title) throws MessagingException {// User user, String txtmessage, String title
		 
		 System.out.println("*************************************");
	        try {
	        	 MimeMessage message = sender.createMimeMessage();
	        	 MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        	 message.setHeader("Content-Type", "text/html");

//				message.setFrom(new InternetAddress("PayChec"));
				helper.setTo("raushanranjan85@gmail.com");
				helper.setSubject(title);
				StringBuilder html = new StringBuilder();
				html.append("<!DOCTYPE html><html><head><meta charset=\'utf-8\'></head><body >");

				html.append(
						"<div style ='background:white; height:450px; width:600px; position: fixed;top: 40%;left: 50%;-webkit-transform: translate(-50%, -50%);  transform: translate(-50%, -50%);'>");
				html.append(
						"<div style='background-image: url(http://185.78.163.41:8080/paychec/img/logo_dummy@2x.png); height: 430px; width: 600px;'>");
				html.append("<div style = 'padding-top:50px;'>");
				html.append("<p style='padding-left: 20px;'>Dear " +  user.getFirstName() + ",</p>  ");///image/logo_dummy@2x.png
				html.append("<p style='margin-left: 3.5em'>   Your Paychec OTP " + txtmessage
						+ " (it will Expire in 10 minutes)  </p> </div> </div>");

				html.append(
						"<div style ='background:#4286f4; height:30px;'><p style='display:block;text-align: center;margin:auto; padding-top:5px;'>&copy; Copyright 2018 - all rights reserved to www.paychec.com </p> </div>");
				html.append(" </div>");

				html.append(" </body></html>");
				message.setContent(html.toString(), "text/html");
				helper.setText(html.toString());
				sender.send(message);

				System.out.println("Done");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

	 }
	 

}
