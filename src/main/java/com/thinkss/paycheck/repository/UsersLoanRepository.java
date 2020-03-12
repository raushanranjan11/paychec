package com.thinkss.paycheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thinkss.paycheck.view.LoanAppliedUsersView;


public interface UsersLoanRepository extends JpaRepository<LoanAppliedUsersView, Long> {

}
