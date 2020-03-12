package com.thinkss.paycheck.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkss.paycheck.entity.ReferenceNumber;
import com.thinkss.paycheck.repository.LoanTypeRepository;
import com.thinkss.paycheck.repository.RefrenceNumberRepository;
import com.thinkss.paycheck.service.LoanService;

@Component
public class LoanReferenceNumber {
	
private static final String CODE_NUMERICS = "1234567890";
@Autowired
LoanService loanService;
	


public  StringBuilder generateRefrenceNo() {
//	Long ref = loanService.loanRefrenceNumber();
	ReferenceNumber refObj = loanService.findLoanReference();
	Long ref = refObj.getCounter();
	System.out.println(ref);
	DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
    String strDate = dateFormat.format(new Date());
    System.out.println("converted Date to String: " + strDate);
	StringBuilder numericCode = new StringBuilder(20);
	numericCode.append(ref).append(strDate);
	System.out.println(numericCode);
	loanService.updateRefrenceNumber(refObj);
	return numericCode;
	
}



	public static StringBuilder generateRefrenceNumber() {
		 String date = Calendar.getInstance().get(Calendar.DATE) +""+Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+""+Calendar.getInstance().get(Calendar.YEAR);
		 
		 	Random generator = new Random();
			StringBuilder numericCode = new StringBuilder(12);
			numericCode.append(date);
			while (numericCode.length() < 12) {
				char nextNumeric = CODE_NUMERICS.charAt(generator.nextInt(CODE_NUMERICS.length()));
				numericCode.append(nextNumeric);
			}


        System.out.println(numericCode); 
		
		return numericCode;
	}

}
