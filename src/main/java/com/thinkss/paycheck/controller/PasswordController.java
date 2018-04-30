package com.thinkss.paycheck.controller;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thinkss.paycheck.bean.Registration;
import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.entity.UserSignInToken;
import com.thinkss.paycheck.exception.SessionExpiredException;
import com.thinkss.paycheck.service.UserService;
import com.thinkss.paycheck.service.impl.CustomUserDetailsService;
import com.thinkss.paycheck.util.GenerateOTP;
import com.thinkss.paycheck.util.SentMail;

@RestController
@RequestMapping(value = "/password", produces = MediaType.APPLICATION_JSON_VALUE)
public class PasswordController {
	
	private static final Logger logger = LoggerFactory.getLogger(PasswordController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@RequestMapping(value = "/forgot", method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<?> getForgetPassword(@RequestBody Registration users, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		User currentUser = userService.findByEmailId(users.getEmailId());
		if (currentUser != null) {
			StringBuilder otp = GenerateOTP.generateOtp();
			currentUser.setOtp(otp.toString());
			currentUser.setOtpGeneratedDate(new Date());

			userService.save(currentUser);

			String title = "OTP";
			SentMail.sendMail(currentUser, otp.toString(), title);
			return ResponseEntity.ok(new UserSignInToken(true, "OTP has been sent to mail "));
		} else {
			return ResponseEntity.ok(new UserSignInToken(false, "User Not found "));

		}
	}

	/* Create password with user id and OTP */
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<?> changePassword(
//			@RequestBody User user,
			@RequestBody Registration user,
			
			HttpServletRequest request) {//Registration
		Map<String, Object> map = new HashMap<String, Object>();
		// User currentUser = userService.findById(id);
		User currentUser = userService.findByEmailId(user.getEmailId());// Id(id);
		System.out.println(user.getEmailId());
		System.out.println(user.getPassword());
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

		if (currentUser != null) {
			currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
//			currentUser.setPassword(user.getPassword());
			if (currentUser.getOtp().equals(user.getOtp())) {
				// currentUser.setLoginProvier(false);
				currentUser.setVerifyByUser(true);
				userService.save(currentUser);
				return ResponseEntity.ok(new UserSignInToken(true, "Password is updated "));
			} else {
				return ResponseEntity.ok(new UserSignInToken(false, "OTP not Matched "));
			}
		} else {
			return ResponseEntity.ok(new UserSignInToken(false, "User Not found "));

		}

	}

	@RequestMapping(value = "/verifyOtp", method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<?> verifyOtpOnSignUp(@RequestBody Registration registration, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		User currentUser = userService.findByEmailId(registration.getEmailId());
		System.out.println(currentUser.getOtpGeneratedDate());

		Date startDate = currentUser.getOtpGeneratedDate();// Set start date
		Date endDate = new Date();// Set end date
		long duration = endDate.getTime() - startDate.getTime();
		long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);

		if (currentUser.getOtp().equals(registration.getOtp())) {
			if (diffInMinutes < 10) {
				currentUser.setVerifyByUser(true);
				userService.save(currentUser);
				// checking time diffrence
				map.put("success", true);
//				map.put("id", currentUser.getId());
			} else {
				try {
					throw new SessionExpiredException("Session Expired");

				} catch (SessionExpiredException se) {
					System.out.println(se.getMessage());
					map.put("message", se.getMessage());
				}
			}

		} else {

			map.put("message", "OTP Not matched");
		}

		return ResponseEntity.ok(map);
	}

}
