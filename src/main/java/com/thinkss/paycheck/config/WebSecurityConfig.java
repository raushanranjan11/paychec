package com.thinkss.paycheck.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.thinkss.paycheck.security.TokenHelper;
//import com.thinkss.paycheck.security.auth.JWTLoginFilter;
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

import javax.sql.DataSource;

/**
 * Created by raushan ranjan on 11-01-2018.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Autowired
	private CustomUserDetailsService jwtUserDetailsService;

	@Autowired
	TokenHelper tokenHelper;

	@Autowired
	CustomSuccessHandler customSuccessHandler;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}


	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		System.out.println(
				"************************************authenticationManagerBean*******************************");
		return super.authenticationManagerBean();
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
		System.out.println(
				"******************************AuthenticationManagerBuilder*************************************");
		/*
		 * auth. jdbcAuthentication() .usersByUsernameQuery(usersQuery)
		 * .authoritiesByUsernameQuery(rolesQuery) .dataSource(dataSource)
		 * .passwordEncoder(passwordEncoder());
		 */
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers(HttpMethod.GET,
				"/webjars/**", "/*.html", "/favicon.ico",
				"/**/*.html", "/**/*.png", "/**/*.PNG", "/**/*.JPG", "/**/*.jpg", "/**.jpg", "/**/*.pdf", "/**/*.woff",
				"/**/*.woff2", "/**/*.ttf", "/**/*.PDF", "/**/*.doc", "/**/*.css", "/*.js", "/country/**", "/**/*.js",
				"/admin/*.json"// ,"/classic/resources/**"
				//, "/classic/resources/**"
				, "/resources/**",
				 "/swagger-resources/**",
			        "/swagger-ui.html",
			        "/v2/api-docs"
				//"/*.woff",
				//"/*.woff2", "/*.ttf" ,"/font-awesome/**"
//				antMatchers("/swagger-resources/**").permitAll()

		).permitAll()

				.antMatchers("/paychec/*").permitAll().antMatchers("/").permitAll().antMatchers("/login").permitAll()
				.antMatchers("/getProperty").permitAll().antMatchers("/registration").permitAll().antMatchers("/home")
				.hasAuthority("ADMIN").anyRequest().authenticated()

				.and().csrf().disable().formLogin().loginPage("/login").failureUrl("/login?error=true")
//		.defaultSuccessUrl("/home")
				.successHandler(customSuccessHandler).usernameParameter("email").passwordParameter("password")
				/*
				 * .successHandler((req,res,auth)->{ //Success handler invoked after successful
				 * authentication for (GrantedAuthority authority : auth.getAuthorities()) {
				 * System.out.println(authority.getAuthority()); }
				 * System.out.println(auth.getName()); // au res.sendRedirect("/home"); //
				 * Redirect user to index/home page })
				 */
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").and()
				.exceptionHandling().accessDeniedPage("/access-denied")
				.and()
				// And filter other requests to check the presence of JWT in header
				.addFilterBefore(new TokenAuthenticationFilter(tokenHelper, jwtUserDetailsService),
						BasicAuthenticationFilter.class);

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/admin/**",
				"/classic/resources/**", "/images/**");

		web.ignoring().antMatchers(HttpMethod.POST, "/paychecAuth/login", "/registrationWithFacebook", "/registration",
				"/password/**", "/oldLoan/**", "/country/**","/location/**"

		);

		web.ignoring().antMatchers(HttpMethod.GET, "/", "/login", "/home", "getProperty", "/resources/**"
		// "/currentUser"
				//,"*.woff"

		);

	}

}
