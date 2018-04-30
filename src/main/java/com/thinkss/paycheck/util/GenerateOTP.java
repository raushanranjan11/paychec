package com.thinkss.paycheck.util;

import java.util.Random;

public class GenerateOTP {

	
private static final String CODE_ALPHA_NUMERICS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	
	public static StringBuilder generateOtp() {

		Random generator = new Random();
		StringBuilder numericCode = new StringBuilder(4);
		while (numericCode.length() < 6) {
			char nextChar = CODE_ALPHA_NUMERICS.charAt(generator.nextInt(CODE_ALPHA_NUMERICS.length()));
			numericCode.append(nextChar);
		}
		return numericCode;
	}
}
