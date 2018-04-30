package com.thinkss.paycheck.bean;

 



import java.math.BigDecimal;

public class PaidLoanBean {

	private BigDecimal paidAmount;
//	private Date paidDate;
//	private String loanTypeId;
//	private String rateId;
	private String loanId;
	
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	 
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	
	
}
