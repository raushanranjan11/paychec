package com.thinkss.paycheck.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.thinkss.paycheck.entity.Loan;
import com.thinkss.paycheck.entity.PreviousAppliedLoan;
import com.thinkss.paycheck.entity.User;


public interface LoanRepository extends CrudRepository<Loan, Long> {
	
	
	 @Query("select s from Loan s where s.loanRefrenceNumber = ?")
	    List<Loan> findLoanByLoanRefrenceNumber(@Param("loanRefrenceNumber")  String loanRefrenceNumber);
	 
	

}
