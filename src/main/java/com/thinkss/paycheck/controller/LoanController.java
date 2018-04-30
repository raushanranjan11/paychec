package com.thinkss.paycheck.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thinkss.paycheck.bean.LoanApplyBean;
import com.thinkss.paycheck.bean.LoanTypeBean;
import com.thinkss.paycheck.bean.PaidLoanBean;
import com.thinkss.paycheck.entity.InterestRate;
import com.thinkss.paycheck.entity.Loan;
import com.thinkss.paycheck.entity.LoanType;
import com.thinkss.paycheck.entity.PaidLoan;
import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.entity.UserBankAccount;
import com.thinkss.paycheck.entity.UserSignInToken;
import com.thinkss.paycheck.repository.LoanRepository;
import com.thinkss.paycheck.repository.LoanTypeRepository;
import com.thinkss.paycheck.security.TokenHelper;
import com.thinkss.paycheck.service.LoanService;
import com.thinkss.paycheck.service.UserService;

@RestController
@RequestMapping(value = "/loan", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoanController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

	@Autowired
	TokenHelper tokenHelper;

	@Autowired
	LoanRepository loanRepository;

	@Autowired
	LoanTypeRepository loanTypeRepository;

	@Autowired
	private UserService userService;
	@Autowired
	LoanService loanService;

	/* this api is use to pay amount by user */
	@RequestMapping(value = "/user/{id}/pay", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> payToLoan(@PathVariable("id") long id, @RequestBody PaidLoanBean paidLoanBean) {

		User user = userService.findById(id);
		if (user != null) {
			PaidLoan paidLoan = new PaidLoan();
			if (paidLoanBean.getPaidAmount() != null) {
				paidLoan.setPaidAmount(paidLoanBean.getPaidAmount());
			}
			// Loan loan = loanRepository.findOne(Long.valueOf(paidLoanBean.getLoanId()));
			Loan loan = loanService.findLoanById(Long.valueOf(paidLoanBean.getLoanId()));
			paidLoan.setLoan(loan);

			paidLoan.setPaidDate(new Date());
			paidLoan.setUser(user);
			// PaidLoan savepaidLoan = paidLoanRepository.save(paidLoan);
			PaidLoan savepaidLoan = loanService.save(paidLoan);
			if (savepaidLoan != null) {
				return ResponseEntity.ok(new UserSignInToken(true, "Your Amount has been  paid."));

			} else {
				return ResponseEntity.ok(new UserSignInToken(false, "Your Amount has been not paid."));
			}

		} else {
			return ResponseEntity.ok(new UserSignInToken(false, "User Not found "));
		}

	}

	/*
	 * api is use for user to get their list of loan with their paid or unpaid
	 * amount
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getloanDetail(@PathVariable("id") long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.findById(id);
		if (user != null) {

			List<Loan> loanList = user.getLoan();
			for (Loan loan : loanList) {
				List<PaidLoan> paidList = loan.getPaidLoan();
				BigDecimal amount = BigDecimal.ZERO;
				for (PaidLoan paid : paidList) {
					amount = amount.add(paid.getPaidAmount());
				}
				System.out.println("Amount    " + amount);
				loan.setPaidAmount(amount);

				loan.setUnPaidAmount(loan.getLoanAmount().subtract(amount));

			}
			map.put("status", true);
			map.put("loanList", loanList);
			return ResponseEntity.ok(map);
		} else {
			return ResponseEntity.ok(new UserSignInToken(false, "User Not found "));
		}
	}

	@RequestMapping(value = "/user/{id}/appliedWithAll", method = RequestMethod.GET)
	public ResponseEntity<?> getAllLoanwithAppliedLoan(@PathVariable("id") long id) {

		Iterable<LoanType> listOfLoan = loanTypeRepository.findAll();
		Collection<LoanType> collection = new ArrayList<>();
		listOfLoan.forEach(collection::add);
		System.out.println(collection.size());

		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.findById(id);
		map.put("appliedLoan", user.getLoan());
		map.put("allLoan", listOfLoan);
		return ResponseEntity.ok(map);

	}

	@RequestMapping(value = "/user/{id}/apply", method = RequestMethod.POST)
	public ResponseEntity<?> loanApply(@PathVariable("id") long id, @RequestBody LoanApplyBean loanApplyBean)
			throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.findById(id);
		if (user != null) {

			Loan loan = new Loan();
			loan.setLoanAmount(loanApplyBean.getLoanAmount());

			String rateId = loanApplyBean.getRateId();

			// InterestRate rate = interestRateRepository.findOne(Long.valueOf(rateId));
			InterestRate rate = loanService.findInterestRateById(Long.valueOf(rateId));

			// System.out.println(rate.getRate());
			loan.setInterestRate(rate);
			// System.out.println(loanApplyBean.getLoanCreatedDate());
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date createdDate = formatter.parse(loanApplyBean.getLoanCreatedDate());
			// System.out.println(createdDate);
			loan.setLoanCreatedDate(createdDate);// setting loan created date// it should not come from front end

			UserBankAccount bankAccount = new UserBankAccount();
			bankAccount.setBankAccountNumber(loanApplyBean.getAccountNumber());

			bankAccount.setUser(user);
			loan.setUserBankAccount(bankAccount);

			loan.setUser(user);
			try {
				// Loan appliedLoan = loanRepository.save(loan);
				Loan appliedLoan = loanService.save(loan);
				// System.out.println("********************");

				map.put("status", true);
				map.put("loanId", appliedLoan.getLoanId());
				return ResponseEntity.ok(map);

			} catch (Exception e) {
				return ResponseEntity.ok(new UserSignInToken(false, e.getMessage()));
			}
		} else {
			return ResponseEntity.ok(new UserSignInToken(false, "User Not found "));
		}

	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> getAllLoan() {

		Iterable<LoanType> listOfLoan = loanTypeRepository.findAll();
		Collection<LoanType> collection = new ArrayList<>();
		listOfLoan.forEach(collection::add);
		System.out.println(collection.size());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allLoan", listOfLoan);
		return ResponseEntity.ok(map);

	}
	
	
	

	/* api is use for user to get their list of loan with their paid or unpaid amount*/
	@RequestMapping(value = "/user/{id}/paidUnPaid", method = RequestMethod.GET)
	public ResponseEntity<?> getPaidUnPaidAmount(@PathVariable("id") long id) {
		Map<String, Object> map = new HashMap<String, Object>();
//		User user = payCheckDao.findUserById(id);
		User user = userService.findById(id);
		if (user != null) {

			List<Loan> loanList = user.getLoan();
//			BigDecimal amount  = BigDecimal.ZERO;
			for (Loan loan : loanList) {
				List<PaidLoan> paidList = loan.getPaidLoan();
				BigDecimal amount  = BigDecimal.ZERO;
				for (PaidLoan paid : paidList) {
					amount = amount.add(paid.getPaidAmount());
				}
				System.out.println("Amount    "+amount);
				loan.setPaidAmount(amount);
				
				loan.setUnPaidAmount(loan.getLoanAmount().subtract(amount));

			}
			map.put("status", true);
			map.put("loanList", loanList);
			return ResponseEntity.ok(map);
		} else {
			return ResponseEntity.ok(new UserSignInToken(false, "User Not found "));
		}

	}
	

	@RequestMapping(value = "/user/{id}/count", method = RequestMethod.GET)
	public ResponseEntity<?>  getloanCount(@PathVariable("id") long id) {//  will change
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.findById(id);
		List<Loan> loan  = new ArrayList<Loan>();
		boolean status = false;
		if (user != null) {
			loan  = user.getLoan();
//			map.put("loanList", loanList);
			status = true;
			map.put("status", true);
			int fakeData = 2000;
			System.out.println("size   "+loan.size());
			if(loan.size() >0) {
			map.put("total", fakeData + loan.size());
			}else {
				map.put("total",loan.size());
			}
		}
		else {
			map.put("status", false);
			map.put("total",loan.size());
		}
		
		return ResponseEntity.ok(map);
		}
	
	
	@RequestMapping(value = "/type", method = RequestMethod.GET)
	public ResponseEntity<?>  getLoanType() { 
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("********************");
		Iterable<LoanType> listOfLoan = loanTypeRepository.findAll();
		Collection<LoanType> collection = new ArrayList<>();
		listOfLoan.forEach(collection::add);
		System.out.println(collection);
		List<LoanTypeBean> list = new ArrayList<>();
				for(LoanType l :collection) {
				LoanTypeBean loanType = new LoanTypeBean();
				loanType.setId( l.getId());
				loanType.setLoanName(l.getLoanName());
				list.add(loanType);
				}
			map.put("loanType", list);
			map.put("status", true);
			
		return ResponseEntity.ok(map);
	}

}
