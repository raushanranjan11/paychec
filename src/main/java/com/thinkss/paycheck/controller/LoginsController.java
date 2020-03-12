package com.thinkss.paycheck.controller;

import java.io.IOException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.entity.UserSignInJson;
import com.thinkss.paycheck.entity.UserSignInToken;
import com.thinkss.paycheck.repository.UserRepository;
import com.thinkss.paycheck.security.TokenHelper;
import com.thinkss.paycheck.security.auth.JwtAuthenticationRequest;
import com.thinkss.paycheck.service.SentMailService;
import com.thinkss.paycheck.service.UserService;
import com.thinkss.paycheck.util.GenerateOTP;
import com.thinkss.paycheck.util.SentMail;

@RestController
@RequestMapping(value = "/paychecAuth", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginsController {

	private static final Logger logger = LoggerFactory.getLogger(LoginsController.class);

	@Autowired
	TokenHelper tokenHelper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private SentMailService sentMailService;

	// paychec mobile app  login
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<?> createAuthenticationTokens(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response, Device device) throws AuthenticationException, IOException {
		logger.info("Authentication Request    " + authenticationRequest.toString());
		UserSignInJson userSignInJson = null;
		if (authenticationRequest.getEmailId() != null && authenticationRequest.getEmailId() != "") {
			User registerWithEmail = userRepository.findByEmailId(authenticationRequest.getEmailId());
			if (registerWithEmail != null) {
				if (registerWithEmail.isLoginProvier()) {
					if (registerWithEmail.getPassword() == null) {
						boolean idProof = false;
						idProof = isIdProof(registerWithEmail);
						return ResponseEntity.ok(new UserSignInJson(false, registerWithEmail.isLoginProvier(), idProof,
								registerWithEmail.isVerifyByUser(),
								"You earlier logged in with Facebook.Please reset password",
								registerWithEmail.getProfilePic()));

					} else {
						try {
							final Authentication authentication = authenticationManager.authenticate(
									new UsernamePasswordAuthenticationToken(authenticationRequest.getEmailId(),
											authenticationRequest.getPassword()));
							SecurityContextHolder.getContext().setAuthentication(authentication);

							User user = (User) authentication.getPrincipal();
							if (authentication.isAuthenticated()) {
								user.setDeviceId(authenticationRequest.getDeviceId()); 
								userService.save(user);
								userSignInJson = verifiedUser(user, device);

							}
						} catch (AuthenticationException ex) {
							return ResponseEntity.ok(new UserSignInJson(false, false, "Password  not matched"));
						}
					}
				} else {

					logger.info("Not facebook account ,Logged in with Email");
					try {
						// Perform the security
						final Authentication authentication = authenticationManager.authenticate(
								new UsernamePasswordAuthenticationToken(authenticationRequest.getEmailId(),
										authenticationRequest.getPassword()));
						SecurityContextHolder.getContext().setAuthentication(authentication);
						User user = (User) authentication.getPrincipal();
						if (authentication.isAuthenticated()) {
							user.setDeviceId(authenticationRequest.getDeviceId());
							userService.save(user);

							userSignInJson = verifiedUser(user, device);
						}
						System.out.println(SecurityContextHolder.getContext().getAuthentication());

					} catch (AuthenticationException ex) {
						return ResponseEntity.ok(new UserSignInJson(false, false, "Password  not matched"));

					}
				}

			} else {
				userSignInJson = new UserSignInJson(false, false, false,
						authenticationRequest.getEmailId() + " not registered");
			}
		} else {
			userSignInJson = new UserSignInJson(false, "Please enter Email");
		}

		return ResponseEntity.ok(userSignInJson);

	}

	public UserSignInJson verifiedUser(User user, Device device) {
		String jws = tokenHelper.generateToken(user.getUsername(), device);
		int expiresIn = tokenHelper.getExpiredIn(device);
		boolean idProof = false;

		if (user.isVerifyByUser()) {
			idProof = isIdProof(user);
			return new UserSignInJson(jws, expiresIn, user.getId(), true, user.getFirstName(), user.isLoginProvier(),
					idProof, true, "Account verified", user.getProfilePic(), user.getKycBackPic(),
					user.getKycFrontPic());

		} else {
			StringBuilder otp = GenerateOTP.generateOtp();
			user.setOtp(otp.toString());
			user.setOtpGeneratedDate(new Date());
			String title = "OTP";
			userService.save(user);
			/*try {
				sentMailService.sentMail(user, otp.toString(), title);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			new Thread(new Runnable() {
			    public void run() {
			try {
				sentMailService.sentMail(user, otp.toString(), title);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}).start();
			idProof = isIdProof(user);
			return new UserSignInJson(jws, expiresIn, user.getId(), true, user.getFirstName(), user.isLoginProvier(),
					idProof, false, "Account not verfied ,Please check mail for OTP", user.getProfilePic(),
					user.getKycBackPic(), user.getKycFrontPic());

		}

	}

	public boolean isIdProof(User user) {
		boolean idProof = false;
		if (user.getKycFrontPic() != null && user.getKycBackPic() != null
		// && ( user.getKycFrontPic() == "" && user.getKycBackPic() == "")
		) {
			idProof = true;
		}
		return idProof;

	}
}
