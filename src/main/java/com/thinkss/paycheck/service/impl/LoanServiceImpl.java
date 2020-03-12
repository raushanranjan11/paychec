package com.thinkss.paycheck.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Criteria;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.thinkss.paycheck.entity.InterestRate;
import com.thinkss.paycheck.entity.Loan;
import com.thinkss.paycheck.entity.LoanStatus;
import com.thinkss.paycheck.entity.LoanType;
import com.thinkss.paycheck.entity.PaidLoan;
import com.thinkss.paycheck.entity.PreviousAppliedLoan;
import com.thinkss.paycheck.entity.ReferenceNumber;
import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.repository.InterestRateRepository;
import com.thinkss.paycheck.repository.LoanRepository;
import com.thinkss.paycheck.repository.LoanStatusRepositorty;
import com.thinkss.paycheck.repository.LoanTypeRepository;
import com.thinkss.paycheck.repository.PaidLoanRepository;
import com.thinkss.paycheck.repository.PreviousAppliedLoanRepository;
import com.thinkss.paycheck.repository.RefrenceNumberRepository;
import com.thinkss.paycheck.service.LoanService;
import com.thinkss.paycheck.view.LoanAppliedUsersView;

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
	RefrenceNumberRepository refrenceNoRepo;
	
	@Autowired
	LoanStatusRepositorty loanStatusRepositorty;

	/*
	 * @Autowired EntityManager em;
	 * 
	 * @PersistenceContext public void setEntityManager(EntityManager em) { this.em
	 * = em; }
	 */

	@PersistenceContext
	private EntityManager em;

	public LoanType findLoanTypeById(Long id) {
		LoanType loanType = loanTypeRepository.findOne(id);
		return loanType;
	}

	public Loan findLoanById(Long id) {
		Loan loan = loanRepository.findOne(id);
		return loan;
	}

	public InterestRate findInterestRateById(Long id) {
		InterestRate rate = interestRateRepository.findOne(id);
		return rate;
	}

	public Loan save(Loan loan) {
		return loanRepository.save(loan);

	}

	public PaidLoan save(PaidLoan paidLoan) {
		return paidLoanRepository.save(paidLoan);

	}

//	 public 
	public Iterable<LoanType> findAllLoanType() {
		return loanTypeRepository.findAll();

	}

	/*
	 * public Iterable<PreviousAppliedLoan> findPreviousAppliedLoan(){ // return
	 * oldLoanRepository.findAll(); return null; }
	 */

	@Transactional
	public void deleteOldLoanByUser(User user, Long id) {
		Session sess = (Session) em.getDelegate();
		Criteria criteria = sess.createCriteria(PreviousAppliedLoan.class);
		criteria.add(Restrictions.eq("id", id));
		Criteria userCrit = criteria.createCriteria("user");
		userCrit.add(Restrictions.eq("id", user.getId()));
		PreviousAppliedLoan loan = (PreviousAppliedLoan) criteria.uniqueResult();
		em.remove(loan);

		/*
		 * Session sess = (Session) em.getDelegate(); Query query = sess.
		 * createQuery("delete  from PreviousAppliedLoan  where  id = :id and user = :user"
		 * ).setParameter("id", id) .setParameter("user", user); query.executeUpdate();
		 */

	}

	public boolean findLoanByLoanRefrenceNumber(String refrenceNumber) {
		List<Loan> loanType = loanRepository.findLoanByLoanRefrenceNumber(refrenceNumber);
		boolean loanRef = false;
		if (loanType.size() > 0) {
			loanRef = true;
		} else {
			loanRef = false;
		}
		return loanRef;

		/*
		 * int nTry = 0; while (nTry < 100) {
		 * 
		 * String refrence = RefrenceNumber.generateRefrenceNumber().toString();
		 * 
		 * if (!loanRef) { pnr.setNumericCode(customerReferenceNumber); break; } else {
		 * System.out.println("REPEATED CODE: " + numericCode); nTry++; } }
		 */
//	        return loanType;
	}

	public static String generateDigit(String s) {
		int digit = 10 - doLuhn(s, true) % 10;
		// int digit = ((10 - doLuhn(s, true)) % (10));
		return "" + digit;
	}

	public static int doLuhn(String s, boolean evenPosition) {
		int sum = 0;
		for (int i = s.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(s.substring(i, i + 1));
			if (evenPosition) {
				n *= 2;
				if (n > 9) {
					n = (n % 10) + 1;
				}
			}
			sum += n;
			evenPosition = !evenPosition;
		}
		return sum;
	}

	public Long loanRefrenceNumber() {
		ReferenceNumber rN = refrenceNoRepo.findOne(Long.valueOf(1));

		return rN.getCounter();

	}

//	  public ReferenceNumber updateRefrenceNumber(ReferenceNumber rn);
	public ReferenceNumber updateRefrenceNumber(ReferenceNumber rn) {
		rn.setCounter(rn.getCounter() + 1);
		return refrenceNoRepo.save(rn);
	}

	public ReferenceNumber findLoanReference() {
		ReferenceNumber rN = refrenceNoRepo.findOne(Long.valueOf(1));

		return rN;

	}

	public List<Loan> findAll() {

		List<Loan> result = (List<Loan>) loanRepository.findAll();
		return result;
	}

	@SuppressWarnings({ "unchecked"})
	public List<LoanAppliedUsersView> findAllLoan(int start, int limit, String dir, String sort, JSONArray crit) throws JSONException {

		StringBuilder queryString = new StringBuilder();
		ArrayList<String> queryWhere = new ArrayList<String>();
		ArrayList<Object> queryParams = new ArrayList<Object>();
		queryString.append("select userLoan from LoanAppliedUsersView userLoan ");// limit
	/*	for (int i = 0; i < crit.length(); i++) {
			JSONObject obj = crit.getJSONObject(i);
			if (obj.get("property").equals("firstName")) {
				queryWhere.add(" user.firstName LIKE  ? ");
				queryParams.add("%" + (String) obj.optString("value") + "%");
			}

		}

		// construct the WHERE clause
		if (!queryWhere.isEmpty()) {
			queryString.append(" WHERE ");
			for (int i = 0; i < queryWhere.size(); i++) {
				if (i != 0) {
					queryString.append(" AND ");
				}
				queryString.append(queryWhere.get(i));
			}
		}*/

		// ORDER BY
//		queryString.append(" ORDER BY loan." + sort + " " + dir);
		queryString.append(" ORDER BY userLoan.createdDate" + " " + "ASC");

		// create the query and apply the parameters
		Query query = em.createQuery(queryString.toString());
		for (int i = 0; i < queryParams.size(); i++) {
			query.setParameter(i + 1, queryParams.get(i));
		}
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();

	}
	
	public List<LoanStatus> findLoanStatus() {

		List<LoanStatus> result = (List<LoanStatus>) loanStatusRepositorty.findAll();
		return result;
	}

}
