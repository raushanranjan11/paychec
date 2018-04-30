package com.thinkss.paycheck.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by raushan ranjan on 11-01-2018.
 */

 @JsonInclude(JsonInclude.Include.NON_ABSENT)
public class UserTokenState {
	private String access_token;
	private Long expires_in;
	private Long id;
	private Boolean status;
	private String firstName;
	private Boolean isfacebookAccount;
	private Boolean idProof;
	private Boolean verifiedUser;
	private String profilePic;

	public UserTokenState() {
		this.access_token = null;
		this.expires_in = null;
	}

	@JsonCreator
	public UserTokenState(String access_token, long expires_in) {
		this.access_token = access_token;
		this.expires_in = expires_in;

	}

	@JsonCreator
	public UserTokenState(
			//String access_token, long expires_in, long id, Boolean status, String firstName
			@JsonProperty("access_token")String access_token,@JsonProperty("expires_in") long expires_in, @JsonProperty("id")Long id, @JsonProperty("status")Boolean status, @JsonProperty("firstName")String firstName) {
		this.access_token = access_token;
		this.expires_in = expires_in;
		this.firstName = firstName;
		this.status = status;
		this.id = id;
	}

	@JsonCreator
	public UserTokenState(@JsonProperty("access_token")String access_token,@JsonProperty("expires_in") long expires_in, @JsonProperty("id")Long id, @JsonProperty("status")Boolean status, @JsonProperty("firstName")String firstName,
			@JsonProperty("isfacebookAccount")Boolean isfacebookAccount, @JsonProperty("idProof")Boolean idProof, @JsonProperty("verifiedUser")Boolean verifiedUser, @JsonProperty("profilePic")String profilePic) {
		super();
		this.access_token = access_token;
		this.expires_in = expires_in;
		this.id = id;
		this.status = status;
		this.firstName = firstName;
		this.isfacebookAccount = isfacebookAccount;
		this.idProof = idProof;
		this.verifiedUser = verifiedUser;
		this.profilePic = profilePic;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean isStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Boolean isIsfacebookAccount() {
		return isfacebookAccount;
	}

	public void setIsfacebookAccount(Boolean isfacebookAccount) {
		this.isfacebookAccount = isfacebookAccount;
	}

	public Boolean isIdProof() {
		return idProof;
	}

	public void setIdProof(Boolean idProof) {
		this.idProof = idProof;
	}

	public Boolean isVerifiedUser() {
		return verifiedUser;
	}

	public void setVerifiedUser(Boolean verifiedUser) {
		this.verifiedUser = verifiedUser;
	}
	
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

}