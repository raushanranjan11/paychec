package com.thinkss.paycheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by raushan ranjan on 8-01-2018.
 */

@Configuration
@ComponentScan(basePackages="com.thinkss.paycheck.controller")
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class PaychecApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PaychecApplication.class, args);
	}
	
	@Override
	/*protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PaychecApplication.class);
	}*/
	
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder;
		return builder.sources(PaychecApplication.class);
	}
	
	
	
}


