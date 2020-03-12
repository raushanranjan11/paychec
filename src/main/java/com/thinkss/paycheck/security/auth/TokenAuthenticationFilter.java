package com.thinkss.paycheck.security.auth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import com.thinkss.paycheck.security.TokenHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by raushan ranjan on 12-01-2018.
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private TokenHelper tokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	public TokenAuthenticationFilter(TokenHelper tokenHelper, UserDetailsService userDetailsService) {
		this.tokenHelper = tokenHelper;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String username;
		String authToken = tokenHelper.getToken(request);
		// System.out.println(" In filter ");
//		System.out.println("           In filter         " + request);

		if (authToken != null) {
			// get username from token
			username = tokenHelper.getUsernameFromToken(authToken);
			System.out.println("             " + username);
			if (username != null) {
				// get user
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if (tokenHelper.validateToken(authToken, userDetails)) {
					// create authentication
					TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
					authentication.setToken(authToken);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}
		/*else{
			System.out.println("Not Auhenticate");
		}*/
		/*
		 * else { System.out.println("           In filter         "+
		 * request.getParameter("email")); System.out.println(
		 * "           In filter         "+ request.getParameter("password"));
		 * 
		 * final Authentication authentication =
		 * authenticationManager.authenticate( new
		 * UsernamePasswordAuthenticationToken(request.getParameter("email"),
		 * request.getParameter("password")));
		 * SecurityContextHolder.getContext().setAuthentication(authentication);
		 * 
		 * System.out.println("           In filter         "+
		 * request.FORM_AUTH);
		 * 
		 * }
		 */
		chain.doFilter(request, response);
	}

}