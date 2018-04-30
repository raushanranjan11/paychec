package com.thinkss.paycheck.controller;

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
import org.springframework.web.client.RestTemplate;

import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.entity.UserSignInToken;
import com.thinkss.paycheck.entity.UserTokenState;
import com.thinkss.paycheck.repository.UserRepository;
import com.thinkss.paycheck.security.TokenHelper;
import com.thinkss.paycheck.security.auth.JwtAuthenticationRequest;
import com.thinkss.paycheck.service.UserService;
import com.thinkss.paycheck.util.GenerateOTP;
import com.thinkss.paycheck.util.SentMail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.Date;

/**
 * Created by raushan ranjan on 12-01-2018.
 */

@RestController
@RequestMapping(value = "/paychecAuth", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	TokenHelper tokenHelper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;



	@RequestMapping(value = "/logins", method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<?> createAuthenticationTokens(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response, Device device) throws AuthenticationException, IOException {

		System.out				.println("sign User  " + authenticationRequest.getEmailId() + " " + authenticationRequest.getPassword());
		ResponseEntity<?> responseEntity = null;
		if (authenticationRequest.getEmailId() != null && authenticationRequest.getEmailId() != "") {

			User registerWithEmail = userRepository.findByEmailId(authenticationRequest.getEmailId());

			if (registerWithEmail != null) {
				if (registerWithEmail.isLoginProvier()) {
					System.out.println("Facebook login");
					if (registerWithEmail.getPassword() == null) {
//						System.out.println("^^^^^^^^^^^^^^^^^");
						boolean idProof = false;
						if ((registerWithEmail.getKycFrontPic() != null && registerWithEmail.getKycBackPic() != null)  && 
								( registerWithEmail.getKycFrontPic() == "" && registerWithEmail.getKycBackPic() == "")
								) {
							idProof = true;
						}
						return ResponseEntity.ok(new UserSignInToken(false, registerWithEmail.isLoginProvier(), idProof,
								registerWithEmail.isVerifyByUser(),
								"You earlier logged in with Facebook.Please reset password",
								registerWithEmail.getProfilePic()));

					} else {
						try {
							// Perform the security
							final Authentication authentication = authenticationManager
									.authenticate(new UsernamePasswordAuthenticationToken(
											 authenticationRequest.getEmailId(),
//											authenticationRequest.getUsername(), 
											authenticationRequest.getPassword()));
//							System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^6");
							SecurityContextHolder.getContext().setAuthentication(authentication);
//							System.out.println("Token Creation------------in facebook------------");

							// token creation
							User user = (User) authentication.getPrincipal();
							if (authentication.isAuthenticated()) {
//								System.out.println("              is   AUTHENTICATED                      ");

//								System.out.println(SecurityContextHolder.getContext().getAuthentication());
								user.setDeviceId(authenticationRequest.getDeviceId());//  save Device Id
								userService.save(user);
								
								responseEntity = verifiedUser(user, device);

							}
						} catch (AuthenticationException ex) {
							return ResponseEntity.ok(new UserSignInToken(false, false, "Password  not matched"));
						}
					}
				} else {

					System.out.println("Not facebook account ,Logged in with Email");

					try {
						// Perform the security
						final Authentication authentication = authenticationManager
								.authenticate(new UsernamePasswordAuthenticationToken(
										 authenticationRequest.getEmailId(),
//										authenticationRequest.getUsername(),
										authenticationRequest.getPassword()));
						SecurityContextHolder.getContext().setAuthentication(authentication);
//						System.out.println("Token Creation------------------------");

						// token creation
						User user = (User) authentication.getPrincipal();
						if (authentication.isAuthenticated()) {
							user.setDeviceId(authenticationRequest.getDeviceId());//  save Device Id
							userService.save(user);
							responseEntity = verifiedUser(user, device);
							// SecurityContextHolder.getContext().setAuthentication(authentication);
						}
						// SecurityContextHolder.getContext().setAuthentication(authentication);
						System.out.println(SecurityContextHolder.getContext().getAuthentication());

					} catch (AuthenticationException ex) {
						return ResponseEntity.ok(new UserSignInToken(false, false, "Password  not matched"));

					}
//					responseEntity = verifiedUser(registerWithEmail, device);
				}

			} else {
				return ResponseEntity.ok(
						new UserSignInToken(false, false, false, authenticationRequest.getEmailId() + " not registered"));
			}
		}
		/*else {
			return ResponseEntity.ok(
					new UserSignInToken(false, "Email " + authenticationRequest.getEmailId() + " not registered."));

		}*/
		
		return responseEntity;

	}

	public ResponseEntity<?> verifiedUser(User user, Device device) {
		String jws = tokenHelper.generateToken(user.getUsername(), device);
		int expiresIn = tokenHelper.getExpiredIn(device);
		boolean idProof = false;

		if (user.isVerifyByUser()) {

			/*if (user.getKycFrontPic() != null && user.getKycBackPic() != null) {
				idProof = true;
			}*/
			if ((user.getKycFrontPic() != null && user.getKycBackPic() != null)  && 
					( user.getKycFrontPic() == "" && user.getKycBackPic() == "")
					) {
//				System.out.println("^^^^^^^^^^^^^^^idProof^^^^^^^^^^^^^^^^^^");
				idProof = true;
			}
			return ResponseEntity.ok(new UserSignInToken(jws, expiresIn, user.getId(), true, user.getFirstName(),
					user.isLoginProvier(), idProof, true, "Account verified", user.getProfilePic()));

		} else {
//			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			StringBuilder otp = GenerateOTP.generateOtp();
			user.setOtp(otp.toString());
			user.setOtpGeneratedDate(new Date());
			String title = "OTP";
			userService.save(user);

			SentMail.sendMail(user, otp.toString(), title);

			if (user.getKycFrontPic() != null && user.getKycBackPic() != null) {
				idProof = true;
			}
			return ResponseEntity.ok(
					new UserSignInToken(jws, expiresIn, user.getId(), true, user.getFirstName(), user.isLoginProvier(),
							idProof, false, "Account not verfied ,Please check mail for OTP", user.getProfilePic()));

		}
		

	}

}