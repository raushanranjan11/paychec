package com.thinkss.paycheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.thinkss.paycheck.entity.Bank;
import com.thinkss.paycheck.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {


	Country findByIsoCode( String isoCode );
	
	Iterable <Country>  findAllByOrderByCountryNameAsc();
	
	
	
	

}
