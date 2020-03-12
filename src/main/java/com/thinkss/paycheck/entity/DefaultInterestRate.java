package com.thinkss.paycheck.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "default_loan_type_bank")
public class DefaultInterestRate {
	
//	private long id;
	@EmbeddedId
    private BankLoanTypeId id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bankId")
	private Bank bank;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("loanTypeId")
	private LoanType loanType;
	
	// additional
	@Column(name = "rate", nullable = false, columnDefinition = "decimal default 0")
	private BigDecimal interestRate;
	
	
	
	
	
	@Embeddable
	public static class BankLoanTypeId  implements Serializable{

		@Column(name = "bank_id")
		protected  long bankId;
		
		@Column(name = "loanType_id")
		protected  long loanTypeId;
		public BankLoanTypeId(long bankId, long loanTypeId) {
			super();
			this.bankId = bankId;
			this.loanTypeId = loanTypeId;
		}
		public BankLoanTypeId() {
			super();
			// TODO Auto-generated constructor stub
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (bankId ^ (bankId >>> 32));
			result = prime * result + (int) (loanTypeId ^ (loanTypeId >>> 32));
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BankLoanTypeId other = (BankLoanTypeId) obj;
			if (bankId != other.bankId)
				return false;
			if (loanTypeId != other.loanTypeId)
				return false;
			return true;
		}
		
		
		

	}

	
	
	
	
	
	
	
}
