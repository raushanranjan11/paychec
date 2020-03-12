package com.thinkss.paycheck.view;

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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkss.paycheck.entity.DocumentDetails;
import com.thinkss.paycheck.entity.DocumentType;
import com.thinkss.paycheck.entity.User;

@Entity
@Immutable
@Table(name = "loan_appled_user_view")
public class LoanAppliedUsersView implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "loan_id", updatable = false, nullable = false)
	private Long loanId;
	@Column(name = "loan_amount")
	private BigDecimal loanAmount;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy", timezone="IST")
	@Column(name = "loan_created_date")
	private Date createdDate = new Date();
	
	@Column(name = "rate_id", updatable = false, nullable = false)
	private Long rateId;
	@Column(name = "interest_rate ")
	private BigDecimal rate;
	

	@Column(name = "bank_id")
	private Long bankId;
	
	@Column(name = "country_id")
	private Long countryId;
	
	@Column(name = "status_id")
	private Long loanStatus;
	
	
//	@Column(name = "user_id ")
//	private String userId;
	
//	@Column(name = "user_id ")
//	@JsonIgnore
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name = "last_name")
	private String lastName;
  
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "loan_refrence")
	private Long loanRef;
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getRateId() {
		return rateId;
	}

	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	/*public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
*/
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Long getLoanRef() {
		return loanRef;
	}

	public void setLoanRef(Long loanRef) {
		this.loanRef = loanRef;
	}
	
	@Transient
//	@JsonIgnore
	/*@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="user_id")*/
	private List<DocumentDetails> documentDetails;


	public List<DocumentDetails> getDocumentDetails() {
//		System.out.println(this.getUser().getDocumentDetails().toString());
		return this.getUser().getDocumentDetails();
//		return documentDetails;
	}
	/*public void setDocumentDetails(List<DocumentDetails> documentDetails) {
		this.documentDetails = this.user.getDocumentDetails();
	}*/
	public Long getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(Long loanStatus) {
		this.loanStatus = loanStatus;
	}
	@Transient
	private List<DocumentType> documentType;


	public List<DocumentType> getDocumentType() {
		return documentType;
//		return this.getUser().getDocumentDetails();
	}
	public void setDocumentType(List<DocumentType> documentType) {
		this.documentType = documentType;
	}

 
	
	
	

	
	
}
