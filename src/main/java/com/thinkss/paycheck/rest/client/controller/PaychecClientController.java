package com.thinkss.paycheck.rest.client.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.thinkss.paycheck.controller.UserRegistraionController;
import com.thinkss.paycheck.security.TokenHelper;
import com.thinkss.paycheck.service.SentMailService;
import com.thinkss.paycheck.util.SentMail;

/**
 * Created by raushan ranjan on 08-02-2018.
 */
@RestController
@RequestMapping( value = "/client", produces = MediaType.APPLICATION_JSON_VALUE )
public class PaychecClientController {
	
	private static final Logger log = LoggerFactory.getLogger(PaychecClientController.class);
	
//	static final String URL_KYC = "http://localhost:8080/paychec/loan/all";
	static final String URL_KYC = "http://localhost:8080/paychec";

	@Autowired
    TokenHelper tokenHelper;
	
	@Autowired
	SentMailService sentMailService;
	
	@RequestMapping(value = "/kyc", method = RequestMethod.GET, consumes = { "application/json" })
	public ResponseEntity<?> uploadKYC(  HttpServletRequest request )  {
		
		String authToken = tokenHelper.getToken(request);
		System.out.println("       ----------------------Rest Template-----------------------      ");
		System.out.println(authToken);
		 // HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        // Request to return JSON format
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("my_other_key", "my_other_value");
        headers.set("access_token", authToken);
        // HttpEntity<String>: To get result as String.
        HttpEntity<String> entity = new HttpEntity<String>(headers);
		
     // RestTemplate
        RestTemplate restTemplate = new RestTemplate();
 
        // Send request with GET method, and Headers.
        ResponseEntity<String> response = restTemplate.exchange(URL_KYC, HttpMethod.GET, entity, String.class);
 
        String result = response.getBody();
        System.out.println(result);
		
		return null;
	}
	
	@RequestMapping(value = "/kycs", method = RequestMethod.GET, consumes = { "application/json" })
	public ResponseEntity<?> uploadKYCs(  HttpServletRequest request )  {
		
		String authToken = tokenHelper.getToken(request);
		
		 	log.debug("===========================request begin================================================");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("emailId", "raushanranjan85@gmail.com");
        map.add("password", "raushan");
        map.add("deviceId", "82268A11-AF53-456D-AA97-FC86EE52FB6C");
        
        
        
        JSONObject obj = new JSONObject();
        try {
			obj.put("emailId", "raushanranjan85@gmail.com");
			obj.put("password", "raushan");
		    obj.put("deviceId", "82268A11-AF53-456D-AA97-FC86EE52FB6C");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        RestTemplate restTemplate = new RestTemplate();
        
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        // create request body
        String input = "{\"password\":\"raushan\",\"emailId\":\"raushanranjan85@gmail.com\",\"deviceId\":\"82268A11-AF53-456D-AA97-FC86EE52FB6C\"}";
//      HttpHeaders headers = new HttpHeaders();
//      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//      headers.set(ApplicationConstants.API_KEY, ApplicationConstants.TEST_API_KEY_VALUE);
//      headers.set(ApplicationConstants.AUTH_TOKEN, ApplicationConstants.TEST_API_TOKEN_VALUE);
//        HttpEntity<String> entity = new HttpEntity<String>(input, headers);
        HttpEntity<String> entity = new HttpEntity<String>(obj.toString(), headers);
//        ResponseEntity<String> result = restTemplate.exchange(URL_KYC+"/paychecAuth/logins", HttpMethod.POST, entity, String.class);
 
//        System.out.println(result);
//        System.out.println(result.getBody());
        
        try {
//			SentMail.sendEmailwithProperty();
//        	sentMailService.sentMail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
