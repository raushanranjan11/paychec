package com.thinkss.paycheck.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;


//@JsonRootName(value = "rate")
@Entity
@Table(name = "bank_interest_rate")

public class BankInterestRate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private BigDecimal rate;
	private Country country;
	private Bank bank;
	private LoanType loanType;
	private BigDecimal processingFee;
	private Date createdDate = new Date();
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
//	@JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
//	 @FormatterPrecision(precision=6)
	@Column(name = "rate")
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	public BankInterestRate() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	/*@JsonIgnore
//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "interestRate", cascade = CascadeType.ALL)
	@OneToMany(cascade = CascadeType.ALL,	fetch = FetchType.LAZY, mappedBy = "interestRate")
//	@OrderBy("id")
	public Set<Loan> getLoan() {
		return loan;
	}
	public void setLoan(Set<Loan> loan) {
		this.loan = loan;
	}*/
	
	
	@Column(name = "processing_fee")
	public BigDecimal getProcessingFee() {
		return processingFee;
	}
	public void setProcessingFee(BigDecimal processingFee) {
		this.processingFee = processingFee;
	}
	


//	 @JsonProperty("rates")
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
	
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="country_id")
//	@OrderBy("id")
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name="bank_id")
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy", timezone="IST")
	@Column(name = "rate_created_date",nullable = false,insertable=false,columnDefinition = "date default sysdate")
	@ColumnDefault(value="'@'")
//	@Column( nullable = false) 
    @Temporal( value = TemporalType.TIMESTAMP ) 
//    @org.hibernate.annotations.Generated(value=GenerationTime.INSERT)
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	private String countryName;
	private String bankName;
	private String loanTypeName;

	@Transient
	public String getCountryName() {
		return this.getCountry().getCountryName();
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	@Transient
	public String getBankName() {
		return this.getBank().getName();
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	@Transient
	public String getLoanTypeName() {
		return this.getLoanType().getLoanName();
	}
	public void setLoanTypeName(String loanTypeName) {
		this.loanTypeName = loanTypeName;
	}
	
	
	
	
	
	
	
	

}
