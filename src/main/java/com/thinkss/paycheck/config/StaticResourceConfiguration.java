package com.thinkss.paycheck.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class StaticResourceConfiguration  extends WebMvcConfigurerAdapter {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	}

	/**
	 * {@inheritDoc}
	 * <p>This implementation is empty.
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
	}
	
	
	 @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 super.addResourceHandlers(registry);
	        registry.addResourceHandler("/resources/image/**")
	        .addResourceLocations("file:/opt/image/");
//	        .setCachePeriod(0); 
	    }

}
