package com.thinkss.paycheck.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.thinkss.paycheck.entity.Loan;
import com.thinkss.paycheck.entity.LoanStatusHistory;

public interface LoanStatusHistoryRepository extends JpaRepository<LoanStatusHistory, Long>{
	
	
	@Query("select lsh from LoanStatusHistory lsh where lsh.loan = ?")
	 List<LoanStatusHistory> findLoanStatusHistoryByLoan(@Param("loan")  Loan loan);
	 // List<Loan> findLoanByloanStatusHistory(@Param("loanRefrenceNumber")  String loanRefrenceNumber);

}
