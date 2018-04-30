package com.thinkss.paycheck.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.thinkss.paycheck.security.TokenHelper;
import com.thinkss.paycheck.security.auth.RestAuthenticationEntryPoint;
import com.thinkss.paycheck.security.auth.TokenAuthenticationFilter;
import com.thinkss.paycheck.service.impl.CustomUserDetailsService;

//import com.artivatic.security.TokenHelper;
//import com.artivatic.security.auth.RestAuthenticationEntryPoint;
//import com.artivatic.security.auth.TokenAuthenticationFilter;
//import com.artivatic.service.impl.CustomUserDetailsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by raushan ranjan on 11-01-2018.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private CustomUserDetailsService jwtUserDetailsService;

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Autowired
	TokenHelper tokenHelper;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("___________HTP___11___");

		List<RequestMatcher> csrfMethods = new ArrayList<>();
		Arrays.asList("POST", "PUT", "PATCH", "DELETE")
				.forEach(method -> csrfMethods.add(new AntPathRequestMatcher("/**", method)));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//				 .exceptionHandling().authenticationEntryPoint( restAuthenticationEntryPoint
				// ).and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/*", "/webjars/**", "/*.html", "/favicon.ico",
						// "/paychec/image/profile_pic/*.jpg",
						"/**/*.html", "/**/*.png", "/**/*.PNG", "/**/*.JPG", "/**/*.jpg", "/**.jpg", "/**/*.pdf",
						"/**/*.PDF", "/**/*.doc", "/**/*.css", "/**/*.js")
				.permitAll().antMatchers("/paychecAuth/**").permitAll().anyRequest().authenticated().and()
				.addFilterBefore(new TokenAuthenticationFilter(tokenHelper, jwtUserDetailsService),
						BasicAuthenticationFilter.class);
		http.csrf().disable();

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		System.out.println("___________HTP___22___");

		logger.info("***********************");
		// TokenAuthenticationFilter will ignore the below paths
		web.ignoring().antMatchers(HttpMethod.POST, "/paychecAuth/logins", "/registrationWithFacebook", "/registration",
//				"/password/forgot","/password/verifyOtp"
//				,"/password/create"
				"/password/**",
				"/oldLoan/**"
		 
		 

		);

//		web.ignoring().antMatchers(HttpMethod.GET,
//				// "/resources/*.PNG",
//				// "/resources/image/*.PNG",
//				// "/resources/image/*.jpg",
//				// "/resources/image/kyc_document/back/*.PNG",
//				"/image/**/*.jpg", "/**/*.png", "/**/*.PNG", "/**/*.JPG", "/**/*.jpg", "/**.jpg", "/**/*.pdf",
//				"/**/*.PDF", "/**", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js");
//		
//		

	}
}
