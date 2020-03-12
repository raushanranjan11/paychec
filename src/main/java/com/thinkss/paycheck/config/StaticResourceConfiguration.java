package com.thinkss.paycheck.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
 

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
	        registry.addResourceHandler("/resources/image/**","/resources/**")
	        .addResourceLocations("file:/opt/image/" ,"static/"  );
	        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
	        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	    
	    }

	 /*Customize serialization like avoid serilaizing for lazy loading and transient variable*/

	 
	 public MappingJackson2HttpMessageConverter jacksonMessageConverter(){
	        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

	        ObjectMapper mapper = new ObjectMapper();
	        //Registering Hibernate4Module to support lazy objects
	        
	        Hibernate4Module module =  new Hibernate4Module();
	        module.disable(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION);
	        mapper.registerModule(module);

	        messageConverter.setObjectMapper(mapper);
	        return messageConverter;

	    }
	 

	    @Override
	    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	        //Here we add our custom-configured HttpMessageConverter
	        converters.add(jacksonMessageConverter());
	        super.configureMessageConverters(converters);
	    }

	   /* @Override
		public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
	        for(HttpMessageConverter converter: converters) {
	            if(converter instanceof MappingJackson2HttpMessageConverter) {
	                ObjectMapper mapper = ((MappingJackson2HttpMessageConverter)converter).getObjectMapper();
	                mapper.setSerializationInclusion(Include.NON_NULL);
	            }
	        }
	    }*/
	    
	    
	    /*@Bean
		public WebMvcConfigurer corsConfigurer() {
		    return new WebMvcConfigurer() {
		        @Override
		        public void addCorsMappings(CorsRegistry registry) {

		            registry.addMapping(".(eot|otf|svg|ttf|woff2?)$").allowedOrigins("*");
		            registry.addMapping("/").allowedOrigins("*");
		            registry.addMapping("/**").allowedOrigins("*");
		        }
		    };
*/
	    
	    @Bean
	    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
	        ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
	        arrayHttpMessageConverter.setSupportedMediaTypes(getSupportedMediaTypes());
	        return arrayHttpMessageConverter;
	    }
	    
	    private List<MediaType> getSupportedMediaTypes() {
	        List<MediaType> list = new ArrayList<MediaType>();
	        list.add(MediaType.IMAGE_JPEG);
	        list.add(MediaType.IMAGE_PNG);
	        list.add(MediaType.APPLICATION_OCTET_STREAM);
	        return list;
	    }

}
