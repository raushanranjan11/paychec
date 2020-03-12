package com.thinkss.paycheck.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.thinkss.paycheck.view.BankView;

public interface BankViewRepository extends   PagingAndSortingRepository<BankView, Serializable> {
	
//	BankView findByName( String name );
	
	
	 @Query("SELECT bank FROM BankView bank WHERE " +
	            "LOWER(bank.bankName) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
	            "LOWER(bank.countryName) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
	    List<BankView> findBySearchTerm(@Param("searchTerm") String searchTerm, Sort sort);
	 
//	 List<BankView> findByBankName(@Param("searchTerm") String searchTerm, Sort sort);
	 
	 
//	 @Query("SELECT view FROM BankView view where " +
//			 "LOWER(bank.bankName) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
//	            "LOWER(bank.countryName) LIKE LOWER(CONCAT('%',:countryName, '%'))"
//			 ) 
//	 Page<BankView> findUsersByPage(Pageable pageable,@Param("bankName") String bankName,@Param("countryName") String countryName);

}
