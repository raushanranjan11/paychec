package com.thinkss.paycheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.ErrorPageFilter;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by raushan ranjan on 8-01-2018.
 */

@EnableSwagger2
@EnableScheduling
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.thinkss.paycheck.entity","com.thinkss.paycheck.view"})
@SpringBootApplication(scanBasePackages = {"com.thinkss.paycheck", "com.thinkss.paycheck.controller"})
public class PaychecApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PaychecApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PaychecApplication.class);
	}
	
	 /*@Bean
	public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurer() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {

	            registry.addMapping(".(eot|otf|svg|ttf|woff2?)$").allowedOrigins("*");
	            registry.addMapping("/").allowedOrigins("*");
	            registry.addMapping("/**").allowedOrigins("*");
	        }
	    }; */
	
	@Bean
    public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.thinkss.paycheck.controller"))
//                .paths(PathSelectors.any())
                .paths(PathSelectors.regex("/.*"))
                .build();
    }
	/*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) 
    {
        //enabling swagger-ui part for visual documentation
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }*/
	
}


