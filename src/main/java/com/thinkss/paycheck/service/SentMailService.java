package com.thinkss.paycheck.service;

import javax.mail.MessagingException;

import com.thinkss.paycheck.entity.User;

public interface SentMailService {

	
	 public void sentMail(User user, String txtmessage, String title )throws MessagingException  ;
	 public void sentMailOnLoanApply(User user, String txtmessage, String title) throws MessagingException  ;
}
