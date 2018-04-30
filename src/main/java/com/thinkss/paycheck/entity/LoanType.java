package com.thinkss.paycheck.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thinkss.paycheck.util.StaticIP;

@Entity
@Table(name = "loan_type")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LoanType implements Serializable{

	
	private Long id;
	private String loanName;
	private String loanDescription;
	private BigDecimal loanAmountLimit;
	
//	@Value("${jwt.header}")
	private String loanImage;
	
//	private InterestRate interestRate;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "loan_name")
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	@Column(name = "loan_description")
	public String getLoanDescription() {
		return loanDescription;
	}
	public void setLoanDescription(String loanDescription) {
		this.loanDescription = loanDescription;
	}
	@Column(name = "loan_limit")
	public BigDecimal getLoanAmountLimit() {
		return loanAmountLimit;
	}
	public void setLoanAmountLimit(BigDecimal loanAmountLimit) {
		this.loanAmountLimit = loanAmountLimit;
	}
	@Column(name = "loan_image")
	public String getLoanImage() {
		return loanImage;
	}
	public void setLoanImage(String loanImage) {
		this.loanImage = loanImage;
//		this.loanImage = StaticIP.IP+"/"+loanImage;
	}
	public LoanType() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/*@JsonIgnore
//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "interestRate", cascade = CascadeType.ALL)
	@OneToMany(cascade = CascadeType.ALL,	fetch = FetchType.LAZY, mappedBy = "interestRate")*/
	/* @JsonProperty("rate")
		@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
		@NotFound(action=NotFoundAction.IGNORE)
		@JoinColumn(name="interest_id")*/
	/*	public InterestRate getInterestRate() {
			return interestRate;
		}
		public void setInterestRate(InterestRate interestRate) {
			this.interestRate = interestRate;
		}
		*/
		private Set<InterestRate> interestRate;

		 @JsonProperty("rates")
		@OneToMany(cascade = CascadeType.ALL,	fetch = FetchType.LAZY, mappedBy = "loanType")
		 @OrderBy("id")
		public Set<InterestRate> getInterestRate() {
			return interestRate;
		}
		public void setInterestRate(Set<InterestRate> interestRate) {
			this.interestRate = interestRate;
		}
		
		
		
	
}
