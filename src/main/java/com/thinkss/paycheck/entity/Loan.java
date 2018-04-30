package com.thinkss.paycheck.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;



@Entity
@Table(name = "loan")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Loan implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long loanId;
	private BigDecimal loanAmount;
//	private float LoanInterestRate;

//	private String loanStartMonth;
//	private String loanValidMonth;
	private User user;
	private InterestRate interestRate;
	private Date loanCreatedDate;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getLoanId() {
		return loanId;
	}
	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}
	@Column(name = "loan_amount")
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	/*@Column(name = "loan_intrest_rate")
	public float getLoanInterestRate() {
		return LoanInterestRate;
	}
	public void setLoanInterestRate(float loanInterestRate) {
		LoanInterestRate = loanInterestRate;
	}*/
	/*@Column(name = "loan_start_month")
	public String getLoanStartMonth() {
		return loanStartMonth;
	}
	public void setLoanStartMonth(String loanStartMonth) {
		this.loanStartMonth = loanStartMonth;
	}
	@Column(name = "loan_valid_month")
	public String getLoanValidMonth() {
		return loanValidMonth;
	}
	public void setLoanValidMonth(String loanValidMonth) {
		this.loanValidMonth = loanValidMonth;
	}*/
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Loan() {
		super();
		// TODO Auto-generated constructor stub
	}
	
 
//	   @JsonProperty("rate")
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="rate_id")
	@OrderBy("id")
	public InterestRate getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(InterestRate interestRate) {
		this.interestRate = interestRate;
	}
	

	private BigDecimal loanProceesingAmount;
	private Integer loanTenure;
	
	/*@Column(name = "loan_process_fee")
	public BigDecimal getLoanProceesingAmount() {
		return loanProceesingAmount;
	}
	public void setLoanProceesingAmount(BigDecimal loanProceesingAmount) {
		this.loanProceesingAmount = loanProceesingAmount;
	}*/
	/*@Column(name = "loan_tenure_month")
	public Integer getLoanTenure() {
		return loanTenure;
	}
	public void setLoanTenure(Integer loanTenure) {
		this.loanTenure = loanTenure;
	}*/
	
	private UserBankAccount userBankAccount;
	
	@JsonIgnore
	@OneToOne//original
	(cascade =CascadeType.ALL,
	//{CascadeType.PERSIST, CascadeType.MERGE},
	fetch = FetchType.LAZY)
	@JoinColumn(name="bank_account_id")
	
//	@OneToOne
//	@PrimaryKeyJoinColumn
	public UserBankAccount getUserBankAccount() {
		return userBankAccount;
	}
	public void setUserBankAccount(UserBankAccount userBankAccount) {
//		UserBankAccount.java = userBankAccount;
		this.userBankAccount = userBankAccount;
	}
	
	
	
/*	private BigDecimal  loanIntrestRate;
	@Transient
	public BigDecimal getLoanIntrestRate() {
//		return loanIntrestRate;
		return this.getInterestRate().getRate();
	}
	public void setLoanIntrestRate(BigDecimal loanIntrestRate) {
		this.loanIntrestRate = loanIntrestRate;
	}*/
	
/*	@Transient
	public BigDecimal getLoanProceesingAmount() {
//		return loanProceesingAmount;
		return this.getInterestRate().getProcessingFee();
	}
	
	public void setLoanProceesingAmount(BigDecimal loanProceesingAmount) {
		this.loanProceesingAmount = loanProceesingAmount;
	}*/
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy", timezone="IST")
	@Column(name = "created_date")
	public Date getLoanCreatedDate() {
		return loanCreatedDate;
	}
	public void setLoanCreatedDate(Date loanCreatedDate) {
		this.loanCreatedDate = loanCreatedDate;
	}
	
	private List<PaidLoan>  paidLoan;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,	fetch = FetchType.LAZY, mappedBy = "loan")
	public List<PaidLoan> getPaidLoan() {
		return paidLoan;
	}

	
	public void setPaidLoan(List<PaidLoan> paidLoan) {
		this.paidLoan = paidLoan;
	}
	
	
	private BigDecimal paidAmount ;
	private BigDecimal unPaidAmount;
	@Transient
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	@Transient
	public BigDecimal getUnPaidAmount() {
		return unPaidAmount;
	}
	public void setUnPaidAmount(BigDecimal unPaidAmount) {
		this.unPaidAmount = unPaidAmount;
	}
	
	
	
}
