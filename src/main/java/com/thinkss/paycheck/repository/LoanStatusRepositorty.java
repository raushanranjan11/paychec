package com.thinkss.paycheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thinkss.paycheck.entity.LoanStatus;


public interface LoanStatusRepositorty extends JpaRepository< LoanStatus, Long> {

}
