package com.thinkss.paycheck.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thinkss.paycheck.entity.PreviousAppliedLoan;
import com.thinkss.paycheck.entity.User;

@Repository
@Transactional
//public interface PreviousAppliedLoanRepository extends CrudRepository<PreviousAppliedLoan, Long>  {
public interface PreviousAppliedLoanRepository extends JpaRepository<PreviousAppliedLoan, Long>  {
	
	 @Query("select s from PreviousAppliedLoan s where s.user = ?")
	    List<PreviousAppliedLoan> findOldLoanByUser(@Param("user")  User user);
	
	 
	 @Modifying
	 @Query("delete from  PreviousAppliedLoan s where s.user = ?1 AND s.id = ?2")
	 public void deleteOldLoanByUser(@Param("user")  User user,@Param("id")  Long id);
	 
	/* @Query("select s from PreviousAppliedLoan s where s.user =:user")
	    List<PreviousAppliedLoan> findOldLoanByUser(@Param("user")  User user);
	 
	 @Query("delete s from PreviousAppliedLoan s where s.user =:user and s.id =: id")
	    void deleteOldLoanByUser(@Param("user")  User user,@Param("id")  Long id);*/

}
