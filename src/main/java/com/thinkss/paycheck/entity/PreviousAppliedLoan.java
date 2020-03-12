package com.thinkss.paycheck.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "previous_applied_loan")
public class PreviousAppliedLoan implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private LoanType loanType;
	private BigDecimal loanAmount;
	private BigDecimal interestRate;
	private Integer numberOfMonth;
	private BigDecimal monthlyPayments;
	private User user;
//	private String documentList;
	
	private String loanName;
	private List<String> documentList;
	
	
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/*@Column(name = "loan_type_id")
	public LoanType getLoanType() {
		return loanType;
	}
	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}
	
	private LoanType loanType;*/


//	 @JsonProperty("rates")
	@JsonIgnore
		@ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
		@NotFound(action=NotFoundAction.IGNORE)
		@JoinColumn(name="loan_type_id")
	public LoanType getLoanType() {
		return loanType;
	}
	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}
	
	@Column(name = "loan_amount")
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	@Column(name = "loan_interest")
	public BigDecimal getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
	@Column(name = "loan_month")
	public Integer getNumberOfMonth() {
		return numberOfMonth;
	}
	public void setNumberOfMonth(Integer numberOfMonth) {
		this.numberOfMonth = numberOfMonth;
	}
	
	@Column(name = "monthly_paid_loan")
	public BigDecimal getMonthlyPayments() {
		return monthlyPayments;
	}
	public void setMonthlyPayments(BigDecimal monthlyPayments) {
		this.monthlyPayments = monthlyPayments;
	}
	
//	@json
	/*@Column(name = "document")
	public String getDocumentList() {
		return documentList;
	}
	public void setDocumentList(String documentList) {
		this.documentList = documentList;
	}
	*/
	
	
	@Transient
	public String getLoanName() {
//		return loanName;
		return this.getLoanType().getLoanName();
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	@Column(name = "document")
	@ElementCollection(targetClass=String.class,
	fetch = FetchType.EAGER
	)
	public List<String> getDocumentList() {
		return documentList;
	}
	public void setDocumentList(List<String> documentList) {
		this.documentList = documentList;
	}
	
	
	
	
	

}
