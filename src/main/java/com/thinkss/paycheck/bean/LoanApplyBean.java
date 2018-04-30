package com.thinkss.paycheck.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class LoanApplyBean {
	
	
	private BigDecimal loanProceesingAmount;
	private Integer loanTenure;
	private BigDecimal loanAmount;
//	private float loanInterestRate;
	private String accountNumber;
	private String loanCreatedDate;
	
	private String rateId;
	
	@NotNull
	@NotBlank
	public BigDecimal getLoanProceesingAmount() {
		return loanProceesingAmount;
	}
	public void setLoanProceesingAmount(BigDecimal loanProceesingAmount) {
		this.loanProceesingAmount = loanProceesingAmount;
	}
	@NotNull
	@NotBlank
	public Integer getLoanTenure() {
		return loanTenure;
	}
	public void setLoanTenure(Integer loanTenure) {
		this.loanTenure = loanTenure;
	}
	@NotNull
	@NotBlank
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	 
	@NotNull
	@NotBlank
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	/*@NotNull
	@NotBlank
	public float getLoanInterestRate() {
		return loanInterestRate;
	}
	public void setLoanInterestRate(float loanInterestRate) {
		this.loanInterestRate = loanInterestRate;
	}*/
	
	@NotNull
	@NotBlank
	public String getLoanCreatedDate() {
		return loanCreatedDate;
	}
	public void setLoanCreatedDate(String loanCreatedDate) {
		this.loanCreatedDate = loanCreatedDate;
	}
	public String getRateId() {
		return rateId;
	}
	public void setRateId(String rateId) {
		this.rateId = rateId;
	}
	
	
	
	
	

}
