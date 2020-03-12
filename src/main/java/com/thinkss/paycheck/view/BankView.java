package com.thinkss.paycheck.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Immutable
@Table(name = "bank_latest_interest_rate")
public class BankView implements Serializable{

	
	@Column(name = "bank_id")
	private Long bankId;
	@Column(name = "bank_name")
	private String bankName;
	@Column(name = "rate")
	private BigDecimal rate;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "rate_id", updatable = false, nullable = false)
	private Long rateId;
	@Column(name = "country_id")
	private Long countryId;
	@Column(name = "country_name")
	private String countryName;
	@Column(name = "loan_id")
	private Long loanTypeId;
	@Column(name = "loan_name")
	private String loanTypeName;
	@Column(name = "processing_fee")
	private BigDecimal processingFee;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy", timezone="IST")
	@Column(name = "rate_created_date")
	private Date createdDate = new Date();
	
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public Long getRateId() {
		return rateId;
	}
	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Long getLoanTypeId() {
		return loanTypeId;
	}
	public void setLoanTypeId(Long loanTypeId) {
		this.loanTypeId = loanTypeId;
	}
	public String getLoanTypeName() {
		return loanTypeName;
	}
	public void setLoanTypeName(String loanTypeName) {
		this.loanTypeName = loanTypeName;
	}
	public BigDecimal getProcessingFee() {
		return processingFee;
	}
	public void setProcessingFee(BigDecimal processingFee) {
		this.processingFee = processingFee;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	
	
	
	
	
}
