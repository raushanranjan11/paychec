package com.thinkss.paycheck.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageBean {
	private String message;
	private Boolean   status;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	@JsonCreator
	 public MessageBean( @JsonProperty("status")Boolean  status, @JsonProperty("message") String message) {
		this.status = status;
		this.message = message;
	}
}
