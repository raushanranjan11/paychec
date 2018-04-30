package com.thinkss.paycheck.bean;

import java.io.Serializable;

public class LoanTypeBean implements Serializable{

	private Long id;
	private String loanName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	
	
}
