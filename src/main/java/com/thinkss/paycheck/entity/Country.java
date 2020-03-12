package com.thinkss.paycheck.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "country")
public class Country implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "name")
	private String countryName;
	
	@Column(name = "iso_code")
	private String isoCode;
	
	@Column(name = "iso3_code")
	private Long iso3Code;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getIsoCode() {
		return isoCode;
	}
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}
	public Long getIso3Code() {
		return iso3Code;
	}
	public void setIso3Code(Long iso3Code) {
		this.iso3Code = iso3Code;
	}
	
/*@ManyToMany
//	@JoinTable(name = "STUDENT_COURSE", 
//			joinColumns = { @JoinColumn(name = "STUDENT_ID") }, inverseJoinColumns = { @JoinColumn(name = "COURSE_ID") })
	 @JoinTable(
	            name = "country_banks",
	            joinColumns = @JoinColumn(name = "country_id"),
	            inverseJoinColumns = @JoinColumn(  name = "bank_id"))
	            */
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY,mappedBy = "country")
	@OrderBy("name")
	private Set<Bank> bank = new HashSet<Bank>();
	public Set<Bank> getBank() {
		return bank;
	}
	public void setBank(Set<Bank> bank) {
		this.bank = bank;
	}
	
	
	
	
	
}
