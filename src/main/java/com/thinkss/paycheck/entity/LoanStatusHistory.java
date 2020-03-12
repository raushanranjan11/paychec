package com.thinkss.paycheck.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "loan_status_history")
public class LoanStatusHistory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	
	private Date updateDate;
	
	
	
	private LoanStatus status;
	
	
	private Loan loan ;
	
	
	private String loanStatus;
	
	private String message;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy hh:mm", timezone="IST")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update")
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="loan_staus_id")
	/*public LoanStatus getLoanStatus() {
		return status;
	}
	public void setLoanStatus(LoanStatus status) {
		this.status = status;
	}*/
	public LoanStatus getStatus() {
		return status;
	}
	public void setStatus(LoanStatus status) {
		this.status = status;
	}

	

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="loan_id")
	public Loan getLoan() {
		return loan;
	}
	
	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	@Transient
	public String getLoanStatus() {
//		return lStatus;
		return this.status.getLoanStaus();
	}
	public void setlStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	
	
	//message
	@Column(name = "message")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
	
	
	
	
	
	
	
}
