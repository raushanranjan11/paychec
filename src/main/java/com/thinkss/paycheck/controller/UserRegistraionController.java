package com.thinkss.paycheck.controller;

import org.springframework.web.bind.annotation.RequestBody;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thinkss.paycheck.bean.RegisterWithFacebook;
import com.thinkss.paycheck.bean.Registration;
import com.thinkss.paycheck.entity.DocumentDetails;
import com.thinkss.paycheck.entity.DocumentSubType;
import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.entity.UserSignInToken;
import com.thinkss.paycheck.entity.UserTokenState;
import com.thinkss.paycheck.repository.AuthorityRepository;
import com.thinkss.paycheck.repository.DocumentDetailRepository;
import com.thinkss.paycheck.repository.DocumentSubTypeRepository;
import com.thinkss.paycheck.repository.UserRepository;
import com.thinkss.paycheck.security.TokenHelper;
import com.thinkss.paycheck.service.UserService;
import com.thinkss.paycheck.util.GenerateOTP;
import com.thinkss.paycheck.util.SentMail;

/**
 * Created by raushan ranjan on 12-01-2018.
 */
@RestController
public class UserRegistraionController {

	private static final Logger logger = LoggerFactory.getLogger(UserRegistraionController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	AuthorityRepository authorityRepository;

	@Autowired
	private DocumentSubTypeRepository documentSubTypeRepository;

	@Autowired
	private DocumentDetailRepository documentDetailRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	TokenHelper tokenHelper;

	@RequestMapping("/")
	public String hello() {
		return "Hi Paycheck";
	}

	@RequestMapping("/hello")
	public String hello1() {
		return "Hello Paycheck";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String addNewUser() {

		return "Saved";
	}

	@RequestMapping(value = "/registrationWithFacebook", method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<?> createSignup(@RequestBody RegisterWithFacebook registration, HttpServletResponse response,
			Device device) throws ParseException {

		User matchedUser = userService.findByFacebookId(registration.getFacebookId());
		
		User registerWithEmail = userRepository.findByEmailId(registration.getEmailId());
		System.out.println(registerWithEmail);
//		if (matchedUser != null || registerWithEmail !=null) {
			if (matchedUser != null ) {

			// consider Already Authenticated for facebook
			String jws = tokenHelper.generateToken(matchedUser.getUsername(), device);
			int expiresIn = tokenHelper.getExpiredIn(device);

			boolean idProof = false;
			if (matchedUser.getKycFrontPic() != null && matchedUser.getKycBackPic() != null) {
				idProof = true;
			}
			matchedUser.setProfilePic(registration.getProfilePic());
			System.out.println("^^^^^^^^             " + registration.getProfilePic());
			userService.save(matchedUser);

			return ResponseEntity.ok(new UserTokenState(jws, expiresIn, matchedUser.getId(), true,
					matchedUser.getFirstName(), matchedUser.isLoginProvier(), idProof, matchedUser.isVerifyByUser(),
					matchedUser.getProfilePic()));

		} 
			else if (registerWithEmail != null ) {
				
				registerWithEmail.setFacebookId(registration.getFacebookId());
				registerWithEmail.setDeviceId(registration.getDeviceId());
				registerWithEmail.setProfilePic(registration.getProfilePic());
				registerWithEmail.setPhoneNumber(registration.getPhoneNumber());
				registerWithEmail.setFirstName(registration.getFirstName());
				registerWithEmail.setLastName(registration.getLastName());
				registerWithEmail.setLoginProvier(true);// facebook login true
				registerWithEmail.setVerifyByUser(true);// facebook verified User
//				registerWithEmail.setBirthDate(registration.getBirthDate());
				 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		         Date birthDate = df.parse(registration.getBirthDate());
		         registerWithEmail.setBirthDate(birthDate);
				
				User facebookSavedUser = userRepository.save(registerWithEmail);

				// consider Already Authenticated for facebook
				String jws = tokenHelper.generateToken(registerWithEmail.getUsername(), device);
				int expiresIn = tokenHelper.getExpiredIn(device);

				boolean idProof = false;
				if (registerWithEmail.getKycFrontPic() != null && registerWithEmail.getKycBackPic() != null) {
					idProof = true;
				}
				registerWithEmail.setProfilePic(registration.getProfilePic());
				System.out.println("^^^^^^^^             " + registration.getProfilePic());
//				userService.save(matchedUser);

				return ResponseEntity.ok(new UserTokenState(jws, expiresIn, facebookSavedUser.getId(), true,
						facebookSavedUser.getFirstName(), facebookSavedUser.isLoginProvier(), idProof, facebookSavedUser.isVerifyByUser(),
						facebookSavedUser.getProfilePic()));

			} 
			
			
			else {

			User user = new User();
			user.setPhoneNumber(registration.getPhoneNumber());
			user.setFirstName(registration.getFirstName());
			user.setLastName(registration.getLastName());
			user.setEmailId(registration.getEmailId());
			user.setUsername(registration.getEmailId());// setting user name
			user.setEnabled(true);
			user.setFacebookId(registration.getFacebookId());
			user.setDeviceId(registration.getDeviceId());
			user.setProfilePic(registration.getProfilePic());
			user.setLoginProvier(true);// facebook login true
			user.setVerifyByUser(true);// facebook verified User
			user.setAccountCreatedDate(new Date());
//			user.setBirthDate(registration.getBirthDate());
			 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	         Date birthDate = df.parse(registration.getBirthDate());
			user.setBirthDate(birthDate);
			// user.setCountryCode(registration.getCountryCode());
			user.setAuthorities(Arrays.asList(authorityRepository.findByName("ROLE_USER")));
			User facebookSavedUser = userRepository.save(user);

			// consider Already Authenticated for facebook
			String jws = tokenHelper.generateToken(facebookSavedUser.getUsername(), device);
			int expiresIn = tokenHelper.getExpiredIn(device);
			System.out.println(facebookSavedUser.getId());

			/*
			 * return ResponseEntity.ok(new UserTokenState(jws, expiresIn,
			 * facebookSavedUser.getId(), true, facebookSavedUser.getFirstName()));
			 */

			return ResponseEntity.ok(new UserTokenState(jws, expiresIn, facebookSavedUser.getId(), true,
					facebookSavedUser.getFirstName(), facebookSavedUser.isLoginProvier(), false,
					facebookSavedUser.isVerifyByUser(), facebookSavedUser.getProfilePic()));
		}
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<?> createSignups(@RequestBody Registration registration, HttpServletResponse response,
			Device device) throws ParseException {

		System.out.println(registration.getBirthDate());
		User user = new User();
		user.setPhoneNumber(registration.getPhoneNumber());
		user.setUsername(registration.getEmailId());// user name
		user.setFirstName(registration.getFirstName());
		user.setLastName(registration.getLastName());
		user.setEmailId(registration.getEmailId());
		user.setEnabled(true);
		user.setAccountCreatedDate(new Date());
		user.setDeviceId(registration.getDeviceId());
		user.setCountryCode(registration.getCountryCode());
		/*SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date validFrom = formatter.parse((String) jsonObj.get("validFromDate"));*/
		 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
         Date birthDate = df.parse(registration.getBirthDate());
		user.setBirthDate(birthDate);

		// user.setPassword(passwordEncoder.encode(registration.getPassword()));
		// user.setAuthorities(Arrays.asList(authorityRepository.findByName("ROLE_USER")));
		StringBuilder otp = GenerateOTP.generateOtp();
		user.setOtp(otp.toString());
		user.setOtpGeneratedDate(new Date());
		user.setPassword(passwordEncoder.encode(registration.getPassword()));
		user.setAuthorities(Arrays.asList(authorityRepository.findByName("ROLE_USER")));

		User registerWithEmail = userRepository.findByEmailId(registration.getEmailId());
		User registerWithPhone = userRepository.findByPhoneNumber(registration.getPhoneNumber());
		String title = "OTP";
		System.out.println("Email user" + registerWithEmail);
		System.out.println("Phone User" + registerWithPhone);
		Map<String, Object> status = new HashMap<String, Object>();

		if (registerWithEmail != null) {
			if (!registerWithEmail.isVerifyByUser()) {
				return ResponseEntity.ok(
						new UserSignInToken(false, "Email address is already registered. Please verify your account."));
			} else {
				return ResponseEntity.ok(
						new UserSignInToken(false, "Email " + registerWithEmail.getEmailId() + " already registered."));

			}

		} else if (registerWithPhone != null) {
			if (!registerWithPhone.isVerifyByUser()) {
				return ResponseEntity
						.ok(new UserSignInToken(false, "Mobile no. already registered.Please verify your account. "));
			} else {
				return ResponseEntity.ok(new UserSignInToken(false,
						"Mobile no. " + registerWithPhone.getPhoneNumber() + " already registered."));
			}
		} else {
			User savedUser = userRepository.save(user);
			if (savedUser != null) {
				DocumentSubType documentSubType = null;

				if (savedUser.getEmailId() != null || !savedUser.getEmailId().equals("")) {// setting document details
					documentSubType = documentSubTypeRepository.findByName("Email");
					DocumentDetails documentDetails = new DocumentDetails();
					documentDetails.setDcumentType(documentSubType.getDocumentType());
					documentDetails.setDocumentSubType(documentSubType);
					documentDetails.setUser(savedUser);
					documentDetails.setImageSource(savedUser.getEmailId());
					documentDetailRepository.save(documentDetails);
				}
			}
			SentMail.sendMail(savedUser, otp.toString(), title);
			status.put("status", true);
			status.put("verify", savedUser.isVerifyByUser());
			status.put("id", savedUser.getId());
			status.put("message", "Account has been created,Plese verify your account.OTP has been sent to your mail");
			String jws = tokenHelper.generateToken(savedUser.getUsername(), device);
			int expiresIn = tokenHelper.getExpiredIn(device);
			status.put("expires_in", expiresIn);
			status.put("access_token", jws);
//			String message = "Account has been created,Plese verify your account.OTP has been sent to your mail";
//			new UserSignInToken(jws, expiresIn, savedUser.getId(), true, savedUser.isVerifyByUser(), message);

		}
		return ResponseEntity.ok(status);

	}

}
