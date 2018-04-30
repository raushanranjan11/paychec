package com.thinkss.paycheck.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;


//@JsonRootName(value = "rate")
@Entity
@Table(name = "loan_interest")

public class InterestRate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private BigDecimal rate;
	private String name;
	private Set<Loan> loan;
	
	private BigDecimal processingFee;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "rate")
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	/*@Column(name = "rate_name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}*/
	public InterestRate() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@JsonIgnore
//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "interestRate", cascade = CascadeType.ALL)
	@OneToMany(cascade = CascadeType.ALL,	fetch = FetchType.LAZY, mappedBy = "interestRate")
//	@OrderBy("id")
	public Set<Loan> getLoan() {
		return loan;
	}
	public void setLoan(Set<Loan> loan) {
		this.loan = loan;
	}
	
	
	@Column(name = "processing_fee")
	public BigDecimal getProcessingFee() {
		return processingFee;
	}
	public void setProcessingFee(BigDecimal processingFee) {
		this.processingFee = processingFee;
	}
	
	private LoanType loanType;


	 @JsonProperty("rates")
	@JsonIgnore
		@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
		@NotFound(action=NotFoundAction.IGNORE)
		@JoinColumn(name="loan_type_id")
	public LoanType getLoanType() {
		return loanType;
	}
	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}
	
	
	private Integer monthForIntrest;


	@Column(name = "month")
	public Integer getMonthForIntrest() {
		return monthForIntrest;
	}
	public void setMonthForIntrest(Integer monthForIntrest) {
		this.monthForIntrest = monthForIntrest;
	}
	
	
	
	
	private List<PaidLoan> paidLoan;


	/*@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,	fetch = FetchType.LAZY, mappedBy = "interestRate")
	public List<PaidLoan> getPaidLoan() {
		return paidLoan;
	}

	public void setPaidLoan(List<PaidLoan> paidLoan) {
		this.paidLoan = paidLoan;
	}*/
	
	
	
	

}
