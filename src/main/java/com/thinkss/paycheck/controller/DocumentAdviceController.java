package com.thinkss.paycheck.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.thinkss.paycheck.exception.FileExceedException;

@ControllerAdvice
public class DocumentAdviceController extends ResponseEntityExceptionHandler {

	
	
//	@ExceptionHandler(FileExceedException.class)
//	public String exception(Exception e) {
//		
//		System.out.println("^^^^^^^^^^^^^^^^^^^6");
//
//		return "error";
//	}
	
//	@ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<?> handleMultipart(MultipartException exception) {
    	System.out.println(exception.getMessage());
		Map<String ,Object> map  = new HashMap<String ,Object>();
		map.put("status", false);
		map.put("message", exception.getMessage());
		return ResponseEntity.ok(map);
    }
}
