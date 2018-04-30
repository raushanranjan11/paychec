package com.thinkss.paycheck.service;



import com.thinkss.paycheck.entity.InterestRate;
import com.thinkss.paycheck.entity.Loan;
import com.thinkss.paycheck.entity.LoanType;
import com.thinkss.paycheck.entity.PaidLoan;
import com.thinkss.paycheck.entity.User;

public interface LoanService {
	
	 public LoanType findLoanTypeById( Long id ) ;
	 
	 public Loan findLoanById( Long id );
	 
	 public Loan save(Loan loan);
	 
	 public PaidLoan save(PaidLoan paidLoan);
	 
	 public InterestRate findInterestRateById( Long id );
	 
	 public Iterable<LoanType> findAllLoanType();
	 
//	 public Iterable<PreviousAppliedLoan> findPreviousAppliedLoan();
	 
	 public void deleteOldLoanByUser( User user,  Long id);

}
