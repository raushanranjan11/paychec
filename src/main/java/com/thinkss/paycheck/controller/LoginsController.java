package com.thinkss.paycheck.controller;

import java.io.IOException;
import java.util.Date;

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

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<?> createAuthenticationTokens(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response, Device device) throws AuthenticationException, IOException {

		System.out.println("sign User  " + authenticationRequest.getEmailId() + " " + authenticationRequest.getPassword());
		
		UserSignInJson userSignInJson = null;
		if (authenticationRequest.getEmailId() != null && authenticationRequest.getEmailId() != "") {

			User registerWithEmail = userRepository.findByEmailId(authenticationRequest.getEmailId());

			if (registerWithEmail != null) {
				if (registerWithEmail.isLoginProvier()) {
					System.out.println("Facebook login");
					if (registerWithEmail.getPassword() == null) {
						boolean idProof = false;
						idProof = isIdProof(registerWithEmail);
						return ResponseEntity.ok(new UserSignInJson(false, registerWithEmail.isLoginProvier(), idProof,
								registerWithEmail.isVerifyByUser(),
								"You earlier logged in with Facebook.Please reset password",
								registerWithEmail.getProfilePic()));

					} else {
						try {
							// Perform the security
							final Authentication authentication = authenticationManager.authenticate(
									new UsernamePasswordAuthenticationToken(authenticationRequest.getEmailId(),
											authenticationRequest.getPassword()));
							SecurityContextHolder.getContext().setAuthentication(authentication);
							// System.out.println("Token Creation------------in facebook------------");

							User user = (User) authentication.getPrincipal();
							if (authentication.isAuthenticated()) {

								user.setDeviceId(authenticationRequest.getDeviceId());// save Device Id
								userService.save(user);
								userSignInJson = verifiedUser(user, device);

							}
						} catch (AuthenticationException ex) {
							return ResponseEntity.ok(new UserSignInJson(false, false, "Password  not matched"));
						}
					}
				} else {

					System.out.println("Not facebook account ,Logged in with Email");

					try {
						// Perform the security
						final Authentication authentication = authenticationManager.authenticate(
								new UsernamePasswordAuthenticationToken(authenticationRequest.getEmailId(),
										authenticationRequest.getPassword()));
						SecurityContextHolder.getContext().setAuthentication(authentication);

						// token creation
						User user = (User) authentication.getPrincipal();
						if (authentication.isAuthenticated()) {
							user.setDeviceId(authenticationRequest.getDeviceId());// save Device Id
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
					idProof, true, "Account verified", user.getProfilePic());

		} else {
			StringBuilder otp = GenerateOTP.generateOtp();
			user.setOtp(otp.toString());
			user.setOtpGeneratedDate(new Date());
			String title = "OTP";
			userService.save(user);

			SentMail.sendMail(user, otp.toString(), title);
			idProof = isIdProof(user);
			return new UserSignInJson(jws, expiresIn, user.getId(), true, user.getFirstName(), user.isLoginProvier(),
					idProof, false, "Account not verfied ,Please check mail for OTP", user.getProfilePic());

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
