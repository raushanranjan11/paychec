package com.thinkss.paycheck.repository;

import org.springframework.data.repository.CrudRepository;

import com.thinkss.paycheck.entity.Loan;


public interface LoanRepository extends CrudRepository<Loan, Long> {

}
