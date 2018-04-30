package com.thinkss.paycheck.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.thinkss.paycheck.entity.InterestRate;
import com.thinkss.paycheck.entity.Loan;
import com.thinkss.paycheck.entity.LoanType;
import com.thinkss.paycheck.entity.PaidLoan;
import com.thinkss.paycheck.entity.PreviousAppliedLoan;
import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.repository.InterestRateRepository;
import com.thinkss.paycheck.repository.LoanRepository;
import com.thinkss.paycheck.repository.LoanTypeRepository;
import com.thinkss.paycheck.repository.PaidLoanRepository;
import com.thinkss.paycheck.repository.PreviousAppliedLoanRepository;
import com.thinkss.paycheck.service.LoanService;


@Service
public class LoanServiceImpl implements LoanService {
	
	@Autowired
	LoanRepository loanRepository;

	@Autowired
	InterestRateRepository interestRateRepository;

	@Autowired
	PaidLoanRepository paidLoanRepository;

	@Autowired
	LoanTypeRepository loanTypeRepository;
	@Autowired
	PreviousAppliedLoanRepository oldLoanRepository;
	
	
	@Autowired
    EntityManager em;
	 @PersistenceContext
	    public void setEntityManager(EntityManager em) {
	        this.em = em;
	    }
	
	
	 public LoanType findLoanTypeById( Long id )  {
		LoanType loanType = loanTypeRepository.findOne( id );
        return loanType;
    }
	 
	 public Loan findLoanById( Long id )  {
		 Loan loan = loanRepository.findOne( id );
	        return loan;
	    }
	 
	 public InterestRate findInterestRateById( Long id )  {
		 InterestRate rate = interestRateRepository.findOne( id );
	        return rate;
	    }
	 
	 public Loan save(Loan loan) {
		 return loanRepository.save(loan);
		 
	 }
	 public PaidLoan save(PaidLoan paidLoan) {
		 return paidLoanRepository.save(paidLoan);
		 
	 }
	 
//	 public 
	 public Iterable<LoanType> findAllLoanType()  {
		 return loanTypeRepository.findAll();
		 
	 }
	 
	/* public Iterable<PreviousAppliedLoan> findPreviousAppliedLoan(){
//		 return oldLoanRepository.findAll();
		 return null;
	 }*/

	 @Transactional
	 public void deleteOldLoanByUser( User user,  Long id) {
	 Session sess = (Session) em.getDelegate();
         Criteria criteria = sess.createCriteria(PreviousAppliedLoan.class);
         criteria.add(Restrictions.eq("id", id));
         Criteria criteria1 = criteria.createCriteria("user");
 		 criteria1.add(Restrictions.eq("id", user.getId()));
 		PreviousAppliedLoan loan = (PreviousAppliedLoan) criteria.uniqueResult();
 		em.remove(loan);
 		
		 /*Session sess = (Session) em.getDelegate();
		 Query query = sess.createQuery("delete  from PreviousAppliedLoan  where  id = :id and user = :user").setParameter("id", id)
					.setParameter("user", user);
			 query.executeUpdate();*/
         
         
         
	 }
}
