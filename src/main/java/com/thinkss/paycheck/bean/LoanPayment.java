package com.thinkss.paycheck.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkss.paycheck.entity.InterestRate;
import com.thinkss.paycheck.entity.User;

public class LoanPayment {

	
	private BigDecimal loanAmount;
	private BigDecimal PayAmount;
	
	private int duration;

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getPayAmount() {
		return PayAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		PayAmount = payAmount;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	
	
	 
}
