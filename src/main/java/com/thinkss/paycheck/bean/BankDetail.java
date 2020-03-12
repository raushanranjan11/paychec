package com.thinkss.paycheck.bean;

import java.math.BigDecimal;
import java.util.List;

import com.thinkss.paycheck.entity.LoanType;

public class BankDetail {
	
	public class LoanTypes {
		
		private long loanTypeId;
		private String LoanName;
		private BigDecimal rate;
		
		
		public long getLoanTypeId() {
			return loanTypeId;
		}
		public void setLoanTypeId(long loanTypeId) {
			this.loanTypeId = loanTypeId;
		}
		public String getLoanName() {
			return LoanName;
		}
		public void setLoanName(String loanName) {
			LoanName = loanName;
		}
		public BigDecimal getRate() {
			return rate;
		}
		public void setRate(BigDecimal rate) {
			this.rate = rate;
		}
		public LoanTypes(long loanTypeId, String loanName, BigDecimal rate) {
			super();
			this.loanTypeId = loanTypeId;
			LoanName = loanName;
			this.rate = rate;
		}
		public LoanTypes() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		
		
	}
	
	private long bankId;
	private String bankName;
	private List<LoanTypes> loanTypeList;
	public long getBankId() {
		return bankId;
	}
	public void setBankId(long bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public List<LoanTypes> getLoanTypeList() {
		return loanTypeList;
	}
	public void setLoanTypeList(List<LoanTypes> loanTypeList) {
		this.loanTypeList = loanTypeList;
	}

	
}
