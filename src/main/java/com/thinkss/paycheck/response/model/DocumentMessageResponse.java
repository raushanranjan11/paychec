package com.thinkss.paycheck.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;

//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class DocumentMessageResponse {

	private Boolean status;
	private String message;
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public DocumentMessageResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DocumentMessageResponse(Boolean status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	

}
