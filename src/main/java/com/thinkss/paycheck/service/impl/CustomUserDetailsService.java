package com.thinkss.paycheck.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.entity.UserSignInToken;
import com.thinkss.paycheck.repository.UserRepository;

/**
 * Created by raushan ranjan on 12-01-2018.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

	protected final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		// User user =userRepository.findByEmailId(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
			// throw new UsernameNotFoundException(String.format("No user found with email
			// '%s'.", username));
		} else {
			return user;
		}
	}

	public void changePassword(String oldPassword, String newPassword) {

		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();

		if (authenticationManager != null) {
			LOGGER.debug("Re-authenticating user '" + username + "' for password change request.");

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
		} else {
			LOGGER.debug("No authentication manager set. can't change Password!");

			return;
		}

		LOGGER.debug("Changing password for user '" + username + "'");

		User user = (User) loadUserByUsername(username);

		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);

	}

	public boolean changeOldPassword(String oldPassword, String newPassword) {

		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
//		String username = currentUser.getName();
		String username = currentUser.getName();
		System.out.println(username);

		if (authenticationManager != null) {
			LOGGER.debug("Re-authenticating user '" + username + "' for password change request.");
			boolean auth = false;
			try {
				final Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));

				if (authentication.isAuthenticated()) {
					User user = (User) loadUserByUsername(username);

					user.setPassword(passwordEncoder.encode(newPassword));
					userRepository.save(user);

					auth = true;
				}
			} catch (AuthenticationException ex) {
				auth = false;
			}
			return auth;
		} else {
			LOGGER.debug("No authentication manager set. can't change Password!");
			return false;
		}

	}
}
