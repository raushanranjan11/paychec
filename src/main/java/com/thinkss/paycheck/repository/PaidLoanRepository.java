package com.thinkss.paycheck.repository;

import org.springframework.data.repository.CrudRepository;

import com.thinkss.paycheck.entity.PaidLoan;


public interface PaidLoanRepository extends CrudRepository<PaidLoan, Long> {

}
