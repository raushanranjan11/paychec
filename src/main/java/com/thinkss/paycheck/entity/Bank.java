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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thinkss.paycheck.util.StaticIP;


@Entity
//@JsonInclude(Include.NON_DEFAULT)
//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(Include.NON_EMPTY)
//@JsonIgnoreProperties(ignoreUnknown = true)  
//@JsonTypeInfo(include=As.WRAPPER_OBJECT, use=com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME)
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@banklogo")
@Table(name = "bank_name")
public class Bank implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "name")
	private String name;
	
//	@JsonInclude(Include.NON_ABSENT)
	@Column(name = "logo")
	private String banklogo;
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBanklogo() {
//		return StaticIP.IP.concat(banklogo);//banklogo;
		if(banklogo != null) {
			return StaticIP.IP.concat(banklogo);
		}
		return banklogo;
	}

	public void setBanklogo(String banklogo) {
		this.banklogo = banklogo;
	}

	
	
	
//	@OneToMany(cascade=CascadeType.ALL)//,fetch = FetchType.LAZY, mappedBy = "code"
//  @JoinTable(
//          name = "country_bank",
//          joinColumns = @JoinColumn(name = "bank_id"),
//          inverseJoinColumns = @JoinColumn(name = "country_id"))
	
//	@CollectionOfElements
//	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "bank")
//	@Transient
	
	
	@JsonIgnore
	 @NotFound(action=NotFoundAction.IGNORE)
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	 @JoinTable(
//	            name = "country_banks",
			 name = "bank_country",
	            joinColumns = @JoinColumn(name = "bank_id"),
	            inverseJoinColumns = @JoinColumn(  name = "country_id"))
	private Set<Country> country = new HashSet<Country>();
	
	public Set<Country> getCountry() {
		return country;
	}

	public void setCountry(Set<Country> country) {
		this.country = country;
	}
	
	
	//---------------------
	
	/**@ManyToMany
    @JoinTable(
	            name = "bank_loan_type",
	            joinColumns = @JoinColumn(name = "bank_id"),
	            inverseJoinColumns = @JoinColumn(  name = "loan_type_id"))
	private Set<LoanType> loanType = new HashSet<LoanType>();
	public Set<LoanType> getLoanType() {
		return loanType;
	}
	public void setLoanType(Set<LoanType> loanType) {
		this.loanType = loanType;
	}*/
	//---------------------------------
	
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToMany(cascade = CascadeType.ALL,	fetch = FetchType.LAZY, mappedBy = "bank")
	private Set<BankInterestRate> bankInterestRate = new HashSet<BankInterestRate>();


	
	public Set<BankInterestRate> getBankInterestRate() {
		return bankInterestRate;
	}

	public void setBankInterestRate(Set<BankInterestRate> bankInterestRate) {
		this.bankInterestRate = bankInterestRate;
	}
	
	
	
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToMany(fetch = FetchType.LAZY,mappedBy = "bank",cascade = CascadeType.ALL)
	private Set<LoanType> loanType = new HashSet<LoanType>();
	public Set<LoanType> getLoanType() {
		return loanType;
	}
	public void setLoanType(Set<LoanType> loanType) {
		this.loanType = loanType;
	}
	
	/*public void addLoanType(LoanType loanTypes) {
	     if (loanTypes != null) {
	        if (loanType == null) {
	        	loanType = new HashSet<LoanType>();          
	        }
	        loanType.add(loanTypes);
	        loanTypes.setBank(bank); //(this);
	     }
	  }
	*/
	
	
	
	
	/*@JsonIgnore
	@OneToMany(
	        mappedBy = "bank",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	private List<DefaultInterestRate> rate = new ArrayList<DefaultInterestRate>();
	public List<DefaultInterestRate> getRate() {
		return rate;
	}
	public void setRate(List<DefaultInterestRate> rate) {
		this.rate = rate;
	}
*/
	/*@Transient
	private BigDecimal rate;



	public BigDecimal getRate() {
		return rate;
		
//		return this.bankInterestRate.
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}*/
	
	/*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank loantype = (Bank) o;
        return Objects.equals(name, loantype.name);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }*/
	
	
	@Column(name = "is_default_rate",insertable=false,updatable = false,nullable = false, columnDefinition = "int default 0")
	@ColumnDefault("'0'")
	private Boolean isDefaultRate;



	public Boolean getIsDefaultRate() {
		return isDefaultRate;
	}

	public void setIsDefaultRate(Boolean isDefaultRate) {
		this.isDefaultRate = isDefaultRate;
	}
	
	
	
	
}
