package com.thinkss.paycheck.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NaturalIdCache;
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
/*@NaturalIdCache
@Cache(
    usage = CacheConcurrencyStrategy.READ_WRITE
)*/
public class LoanType implements Serializable{

	
	private Long id;
	private String loanName;
	private String loanDescription;
	private BigDecimal loanAmountLimit;
	
//	@Value("${jwt.header}")
	private String loanImages;
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
	
	@JsonIgnore
	@Column(name = "loan_image")
	public String getLoanImages() {
		return loanImages;
	}
	public void setLoanImages(String loanImages) {
		this.loanImages = loanImages;
//		this.loanImage = StaticIP.IP+"/paychec"+loanImage;
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

		@JsonIgnore
		 @JsonProperty("rates")
		@OneToMany(cascade = CascadeType.ALL,	fetch = FetchType.LAZY, mappedBy = "loanType")
		 @OrderBy("id")
		public Set<InterestRate> getInterestRate() {
			return interestRate;
		}
		public void setInterestRate(Set<InterestRate> interestRate) {
			this.interestRate = interestRate;
		}
		
		
		
		private  Set<Bank> bank = new HashSet<Bank>();

		@JsonIgnore
		@ManyToMany
	    @JoinTable(
//		            name = "bank_loan_type",
	    		   name = "loan_type_bank",
		            joinColumns = @JoinColumn(name = "loan_type_id"),
		            inverseJoinColumns = @JoinColumn(  name = "bank_id"))
		public Set<Bank> getBank() {
			return bank;
		}
		public void setBank(Set<Bank> bank) {
			this.bank = bank;
		}
		
		
		
		/*private List<DefaultInterestRate> rate = new ArrayList<DefaultInterestRate>();
		
		
		@JsonIgnore
		@OneToMany(
		        mappedBy = "loanType",
		        cascade = CascadeType.ALL,
		        orphanRemoval = true
		    )
		public List<DefaultInterestRate> getRate() {
			return rate;
		}
		public void setRate(List<DefaultInterestRate> rate) {
			this.rate = rate;
		}*/

		private BigDecimal interestRateDefault;

		
		//@Column(name = "rate", nullable = false, columnDefinition = "decimal default 0")
		@Column(name = "default_interest_rate",insertable=false,updatable = false,nullable = false, columnDefinition = "decimal default 0")
		@ColumnDefault("'0.0'")
		public BigDecimal getInterestRateDefault() {
			return interestRateDefault;
		}
		public void setInterestRateDefault(BigDecimal interestRateDefault) {
			this.interestRateDefault = interestRateDefault;
		}
		
		@Transient
		public String getLoanImage() {
			return loanImage = this.getLoanImages();
		}
		public void setLoanImage(String loanImage) {
//			this.loanImage = loanImage;
			this.loanImage = loanImages;
		}
		@Override
		public String toString() {
			return "LoanType [id=" + id + ", loanName=" + loanName + ", loanDescription=" + loanDescription
					+ ", loanAmountLimit=" + loanAmountLimit + ", loanImages=" + loanImages + ", loanImage=" + loanImage
					+ ", interestRate=" + interestRate + ", bank=" + bank + ", interestRateDefault="
					+ interestRateDefault + "]";
		}
		
		/*
		@Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        LoanType loantype = (LoanType) o;
	        return Objects.equals(loanName, loantype.loanName);
	    }
	 
	    @Override
	    public int hashCode() {
	        return Objects.hash(loanName);
	    }
		*/
		
		
		
	
}
