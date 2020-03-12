package com.thinkss.paycheck.service;



import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.thinkss.paycheck.entity.InterestRate;
import com.thinkss.paycheck.entity.Loan;
import com.thinkss.paycheck.entity.LoanStatus;
import com.thinkss.paycheck.entity.LoanType;
import com.thinkss.paycheck.entity.PaidLoan;
import com.thinkss.paycheck.entity.ReferenceNumber;
import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.view.LoanAppliedUsersView;

public interface LoanService {
	
	 public LoanType findLoanTypeById( Long id ) ;
	 
	 public Loan findLoanById( Long id );
	 
	 public Loan save(Loan loan);
	 
	 public PaidLoan save(PaidLoan paidLoan);
	 
	 public InterestRate findInterestRateById( Long id );
	 
	 public Iterable<LoanType> findAllLoanType();
	 
//	 public Iterable<PreviousAppliedLoan> findPreviousAppliedLoan();
	 
	 public void deleteOldLoanByUser( User user,  Long id);
	 public boolean findLoanByLoanRefrenceNumber( String refrenceNumber );
	 
	 public Long loanRefrenceNumber();
	 public ReferenceNumber updateRefrenceNumber(ReferenceNumber rn);
	 public ReferenceNumber findLoanReference();
	 
	 public  List<Loan> findAll ();
	 public List<LoanAppliedUsersView> findAllLoan(int start,int limit,String dir,String sort,JSONArray crit)throws JSONException;
	 public List<LoanStatus> findLoanStatus();

}
