package com.thinkss.paycheck.bean;

import java.math.BigDecimal;


public class PreviousAppliedLoanBean {

	
	private Long loanTypeId;
	private BigDecimal loanAmount;
	private BigDecimal interestRate;
	private Integer numberOfMonth;
	private BigDecimal monthlyPayments;
	private String []document;
	
	
	
	
	public String[] getDocument() {
		return document;
	}
	public void setDocument(String[] document) {
		this.document = document;
	}
	/*public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}*/
	public Long getLoanTypeId() {
		return loanTypeId;
	}
	public void setLoanTypeId(Long loanTypeId) {
		this.loanTypeId = loanTypeId;
	}
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	public BigDecimal getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
	public Integer getNumberOfMonth() {
		return numberOfMonth;
	}
	public void setNumberOfMonth(Integer numberOfMonth) {
		this.numberOfMonth = numberOfMonth;
	}
	public BigDecimal getMonthlyPayments() {
		return monthlyPayments;
	}
	public void setMonthlyPayments(BigDecimal monthlyPayments) {
		this.monthlyPayments = monthlyPayments;
	}
	
	
	
}
