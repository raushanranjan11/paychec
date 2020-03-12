package com.thinkss.paycheck;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import com.thinkss.paycheck.entity.Bank;
import com.thinkss.paycheck.entity.BankInterestRate;
import com.thinkss.paycheck.entity.Country;
import com.thinkss.paycheck.entity.LoanType;
import com.thinkss.paycheck.repository.BankInterestRateRepository;
import com.thinkss.paycheck.repository.BankRepository;
import com.thinkss.paycheck.repository.BankViewRepository;
import com.thinkss.paycheck.repository.CountryRepository;
import com.thinkss.paycheck.repository.LoanTypeRepository;
import com.thinkss.paycheck.service.LoanService;
import com.thinkss.paycheck.service.impl.BankServiceImpl;
import com.thinkss.paycheck.service.impl.LoanServiceImpl;

/*@RunWith(SpringRunner.class)*/
@SpringBootTest
@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class PaychecApplicationTests {

	@Mock
	private BankRepository bankRepository;
	
	@Mock
	private BankInterestRateRepository rateRepository;
	
	@Mock
	private BankViewRepository bankViewRepository;
	@Mock
	private CountryRepository countryRepository;
	
	@Mock
	LoanTypeRepository loanTypeRepository;
	
	@InjectMocks
	LoanServiceImpl loanService;
	@InjectMocks
	BankServiceImpl bankService;
	private final transient EntityManager mockedEm =mock(EntityManager.class);
	private final transient Query mockedQuery = mock(Query.class);
//	@Test
//	@Test(expected=NullPointerException.class)
	/*public void findBankInterestRateTest(){
		Bank bank1 = new Bank();
		bank1.setName("SBI");
		bank1.setId(Long.valueOf(1));
		LoanType loanType = new LoanType();
		loanType.setId(Long.valueOf(1));
		loanType.setInterestRateDefault(BigDecimal.valueOf(2));
		Set<LoanType> listOfLoanType = new HashSet<LoanType>();
		bank1.setLoanType(listOfLoanType);
		Bank bank2= new Bank();
		bank2.setName("ICICI");
		bank2.setId(Long.valueOf(2));
		Set<Bank> listOfBank = new HashSet<Bank>();
		listOfBank.add(bank1);
//		listOfBank.add(bank2);
		Country country = new Country();
		country.setId(Long.valueOf(1));
		country.setCountryName("INDIA");
//		country.setBank(listOfBank);
		BankInterestRate bIntrestRate = new BankInterestRate();
		bIntrestRate.setId(Long.valueOf(1));
		bIntrestRate.setLoanType(loanType);
		bIntrestRate.setBank(bank1);
		bIntrestRate.setCountry(country);
		bIntrestRate.setRate(BigDecimal.valueOf(5));
		bIntrestRate.setProcessingFee(BigDecimal.valueOf(5));
		bIntrestRate.setCreatedDate(new Date());
		
		when(bankRepository.findByName(anyString())).thenReturn(bank1);
		when(countryRepository.findOne(anyLong())).thenReturn(country);
		when(loanTypeRepository.findOne(anyLong())).thenReturn(loanType);
		//test
		BankInterestRate rate =  bankService.findBankInterestRate(country,bank1,loanType);
		assertEquals(bIntrestRate,rate);
	}*/
	
	@Test
	public void findLoanTypeByIdTest(){
		LoanType loanType = new LoanType();
		loanType.setId(Long.valueOf(1));
		loanType.setInterestRateDefault(BigDecimal.valueOf(2));
		when(loanTypeRepository.findOne(anyLong())).thenReturn(loanType);
		LoanType loanType1 = loanService.findLoanTypeById(Long.valueOf(1));
		assertEquals(loanType1,loanType);
	}

}
