package com.thinkss.paycheck.shedule.job;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.mail.MessagingException;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.thinkss.paycheck.entity.Loan;
import com.thinkss.paycheck.entity.PaidLoan;
import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.repository.UserRepository;
import com.thinkss.paycheck.service.SentMailService;
import com.thinkss.paycheck.service.UserService;

@Component

public class CornJobs {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private SentMailService sentMailService;
	private static final Logger logger = LoggerFactory.getLogger(CornJobs.class);

//	 static  int j = 0;

	@Scheduled(cron = "0 8 18 * * ?")
//	@Scheduled(cron = "0/20  * * * * ?")
	public void doJobs() {
		logger.info("Job executing--------------------------------------------");
		List<User> userList = userService.findAll();

		for (User user : userList) {
			System.out.println(user.getId().equals(Long.valueOf(9)));
			System.out.println(user.getId() == 9);
			System.out.println(user.getId());
			if (user.getId() == 9) {
				List<Loan> loanList = user.getLoan();

				for (Loan loan : loanList) {
					List<PaidLoan> loanpaidList = loan.getPaidLoan();
					BigDecimal principalAmount = loan.getLoanAmount();
					BigDecimal intRate = loan.getInterestRate().getRate();
					BigDecimal processingFees = loan.getInterestRate().getProcessingFee();
					int month = loan.getInterestRate().getMonthForIntrest();
					BigDecimal amount = principalAmount.multiply(
							BigDecimal.valueOf(1).add(intRate.divide(BigDecimal.valueOf(Double.valueOf(100)), 2, 2)
									.multiply((BigDecimal.valueOf(month)).divide(BigDecimal.valueOf(12))))

					);
					BigDecimal totalAmount = amount.add(processingFees);
					BigDecimal paidLoanAmount = BigDecimal.ZERO;
					BigDecimal toPayAmount = BigDecimal.ZERO;
					BigDecimal monthlyPayment = BigDecimal.ZERO;
					int i = 0;
					if (loanpaidList.isEmpty()) {
						monthlyPayment = totalAmount.divide(BigDecimal.valueOf(Long.valueOf(month)), 2, 2);
						toPayAmount = toPayAmount.add(totalAmount.subtract(monthlyPayment));
					} else {
						for (PaidLoan paidloan : loanpaidList) {
							paidLoanAmount.add(paidloan.getPaidAmount());
							i++;
						}
						toPayAmount = toPayAmount.add(totalAmount.subtract(paidLoanAmount)
								.divide(BigDecimal.valueOf(Long.valueOf(month - i))));

					}
					if (toPayAmount.compareTo(BigDecimal.ZERO) != 0) {
						new Thread(new Runnable() {
							public void run() {
								try {
									String title = "Pay to Loan Amount";
									sentMailService.sentMailOnLoanApply(user, title.toString(), title);
									logger.info("Mail has sent  *******************888");
//								j++;
								} catch (MessagingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}).start();

					}

				}

			}
		}

	}

}
