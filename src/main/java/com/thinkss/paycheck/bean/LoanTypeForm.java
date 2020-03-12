package com.thinkss.paycheck.bean;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class LoanTypeForm {
	
	private String loanName;
	private String loanDescription;
	private BigDecimal loanAmountLimit;
//	private String loanImages;

	
	private MultipartFile  loanImages;
	
	 

	 

	public MultipartFile getLoanImages() {
		return loanImages;
	}

	public void setLoanImages(MultipartFile loanImages) {
		this.loanImages = loanImages;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getLoanDescription() {
		return loanDescription;
	}

	public void setLoanDescription(String loanDescription) {
		this.loanDescription = loanDescription;
	}

	public BigDecimal getLoanAmountLimit() {
		return loanAmountLimit;
	}

	public void setLoanAmountLimit(BigDecimal loanAmountLimit) {
		this.loanAmountLimit = loanAmountLimit;
	}
	
	

}
