package com.thinkss.paycheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.thinkss.paycheck.entity.BankInterestRate;



public interface BankInterestRateRepository extends JpaRepository<BankInterestRate, Long> {

}