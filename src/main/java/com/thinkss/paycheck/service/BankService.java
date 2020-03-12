package com.thinkss.paycheck.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.thinkss.paycheck.entity.Bank;
import com.thinkss.paycheck.entity.BankInterestRate;
import com.thinkss.paycheck.entity.Country;
import com.thinkss.paycheck.entity.LoanType;
import com.thinkss.paycheck.view.BankView;

public interface BankService {

	
	 
	 public BankInterestRate findBankInterestRate( Country country, Bank bank,LoanType loanType ) ;
	 
	 public LoanType findBankLoanType( Country country, Bank bank) ;
	 
	 public BankInterestRate save(BankInterestRate bankInterestRate);
	 
	 public List<BankInterestRate> findBankInterestRate( Country country, LoanType loanType ) ;
	 public List<BankView> findLatestBankInterestRate( Country country, LoanType loanType );
	 public List<BankView> findAllBankRate();
	 public List<BankView> findAllBankRate(int start,int limit,String dir,String sort);
	 public List<Country> findAllCountry();
	 
	 public Page<BankView> findAllBankView(Pageable pageable);
	 
	 public List<BankView> findAllBankRate(int start,int limit,String dir,String sort,JSONArray crit) throws JSONException;

}
