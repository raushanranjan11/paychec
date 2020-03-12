package com.thinkss.paycheck.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "loan_status")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LoanStatus implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	
	private String loanStaus;
	
	
	private List<LoanStatusHistory> LoanStatusHistory;
	
	
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "status_name")
	public String getLoanStaus() {
		return loanStaus;
	}
	public void setLoanStaus(String loanStaus) {
		this.loanStaus = loanStaus;
	}
	
	/*@JsonIgnore
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="rate_id")
//	@OrderBy("id")
	private Loan loan;
	public Loan getLoan() {
		return loan;
	}
	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	*/
	
	
	
	@JsonIgnore
//	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL,	fetch = FetchType.LAZY, mappedBy = "status")
	public List<LoanStatusHistory> getLoanStatusHistory() {
		return LoanStatusHistory;
	}
	public void setLoanStatusHistory(List<LoanStatusHistory> loanStatusHistory) {
		LoanStatusHistory = loanStatusHistory;
	}


	
	
	

	
	
	
	
	
	
	
	
	
	
	
}
