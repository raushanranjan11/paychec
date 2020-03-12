package com.thinkss.paycheck.shedule.job;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
 
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
 
    public void sendSpam() {
 
        LOGGER.info("Should send emails");
    }
    
 
}