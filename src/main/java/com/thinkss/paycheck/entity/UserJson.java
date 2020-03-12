package com.thinkss.paycheck.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by raushan ranjan on 11-01-2018.
 */

// @JsonInclude(JsonInclude.Include.NON_ABSENT)
public class UserJson {
	private String access_token;
	private Long expires_in;
	
	
	private Long id;
	private Boolean status;
	private String firstName;
	private Boolean isfacebookAccount;
	private Boolean idProof;
	private Boolean verifiedUser;
	private String profilePic;
	
//	 @JsonInclude(JsonInclude.Include.NON_NULL) 
	    private String kycFrontPic;
//	    @JsonInclude(JsonInclude.Include.NON_NULL) 
		private String kycBackPic;
		

	public UserJson() {
		this.access_token = null;
		this.expires_in = null;
	}

	@JsonCreator
	public UserJson(String access_token, long expires_in) {
		this.access_token = access_token;
		this.expires_in = expires_in;

	}

	@JsonCreator
	public UserJson(
			//String access_token, long expires_in, long id, Boolean status, String firstName
			@JsonProperty("access_token")String access_token,@JsonProperty("expires_in") long expires_in, @JsonProperty("id")Long id, @JsonProperty("status")Boolean status, @JsonProperty("firstName")String firstName) {
		this.access_token = access_token;
		this.expires_in = expires_in;
		this.firstName = firstName;
		this.status = status;
		this.id = id;
	}

	@JsonCreator
	public UserJson(@JsonProperty("access_token")String access_token,@JsonProperty("expires_in") long expires_in, @JsonProperty("id")Long id, @JsonProperty("status")Boolean status, @JsonProperty("firstName")String firstName,
			@JsonProperty("isfacebookAccount")Boolean isfacebookAccount, @JsonProperty("idProof")Boolean idProof, @JsonProperty("verifiedUser")Boolean verifiedUser, @JsonProperty("profilePic")String profilePic,
			@JsonProperty("kycBackPic")String kycBackPic,
			@JsonProperty("kycFrontPic")String kycFrontPic) {
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
		
		this.kycFrontPic = kycFrontPic;
		this.kycBackPic = kycBackPic;
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

	
	public String getKycFrontPic() {
		return kycFrontPic;
	}
	public void setKycFrontPic(String kycFrontPic) {
		this.kycFrontPic = kycFrontPic;
	}
	public String getKycBackPic() {
		return kycBackPic;
	}
	public void setKycBackPic(String kycBackPic) {
		this.kycBackPic = kycBackPic;
	}
}