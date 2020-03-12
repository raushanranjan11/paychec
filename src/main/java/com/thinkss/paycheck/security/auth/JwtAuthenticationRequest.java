package com.thinkss.paycheck.security.auth;

/**
 * Created by raushan ranjan on 12-01-2018.
 */
public class JwtAuthenticationRequest {
	private String username;
	private String password;
	private String emailId;

	private String deviceId;

	public JwtAuthenticationRequest() {
		super();
	}

	public JwtAuthenticationRequest(String emailId, String password) {
		// this.setUsername(username)
		this.setEmailId(emailId);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		return "JwtAuthenticationRequest [username=" + username + ", password=" + password + ", emailId=" + emailId
				+ ", deviceId=" + deviceId + "]";
	}

}
