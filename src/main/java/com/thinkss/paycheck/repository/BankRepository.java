package com.thinkss.paycheck.repository;

import org.springframework.data.repository.CrudRepository;

import com.thinkss.paycheck.entity.Bank;
import com.thinkss.paycheck.entity.Country;
import com.thinkss.paycheck.entity.LoanType;
import com.thinkss.paycheck.entity.User;

public interface BankRepository extends   CrudRepository<Bank, Long> {
	
	Bank findByName( String name );
	Iterable <Bank>  findAllByOrderByNameAsc();
	Bank findByLoanTypeAndId(LoanType loanType,Long id);

}
