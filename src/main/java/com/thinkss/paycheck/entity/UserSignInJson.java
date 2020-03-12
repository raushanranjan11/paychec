package com.thinkss.paycheck.entity;

 
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//NON_NULL
//@JsonInclude(JsonInclude.Include.NON_ABSENT)// use for remove all default//
public class UserSignInJson {
	
		private String access_token;
	    private Integer expires_in;
	    private Long id;
	    private Boolean   status ;
	    private String firstName;
	    private Boolean   isfacebookAccount;
	    private Boolean   idProof ;
	    private Boolean   verifiedUser;
	    private String message;
	    private String profilePic;
	    
//	    @JsonInclude(JsonInclude.Include.NON_NULL) 
	    private String kycFrontPic;
//	    @JsonInclude(JsonInclude.Include.NON_NULL) 
		private String kycBackPic;
		
		
		
		
		public String getAccess_token() {
			return access_token;
		}
		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}
		public Integer getExpires_in() {
			return expires_in;
		}
		public void setExpires_in(Integer expires_in) {
		
				
			this.expires_in = expires_in;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Boolean   isStatus() {
			return status;
		}
		public void setStatus(Boolean   status) {
			this.status = status;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public Boolean   isIsfacebookAccount() {
			return isfacebookAccount;
		}
		public void setIsfacebookAccount(Boolean   isfacebookAccount) {
			this.isfacebookAccount = isfacebookAccount;
		}
		public Boolean   isIdProof() {
			return idProof;
		}
		public void setIdProof(Boolean   idProof) {
			this.idProof = idProof;
		}
		public Boolean   isVerifiedUser() {
			return verifiedUser;
		}
		public void setVerifiedUser(Boolean   verifiedUser) {
			this.verifiedUser = verifiedUser;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
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
		public Boolean getStatus() {
			return status;
		}
		public Boolean getIsfacebookAccount() {
			return isfacebookAccount;
		}
		public Boolean getIdProof() {
			return idProof;
		}
		public Boolean getVerifiedUser() {
			return verifiedUser;
		}
		
	/*	public UserSignInToken(String access_token, int expires_in, Long id, Boolean   status, String firstName,
				Boolean   isfacebookAccount, Boolean   idProof, Boolean   verifiedUser, String message, String profilePic) {
			super();
			this.access_token = access_token;
			this.expires_in = expires_in;
			this.id = id;
			this.status = status;
			this.firstName = firstName;
			this.isfacebookAccount = isfacebookAccount;
			this.idProof = idProof;
			this.verifiedUser = verifiedUser;
			this.message = message;
			this.profilePic = profilePic;
		}
	 
	public UserSignInToken(Boolean  status, Boolean  verifiedUser, String message) {
			super();
			this.status = status;
			this.verifiedUser = verifiedUser;
			this.message = message;
		}
		public UserSignInToken(Boolean  status,Boolean  isfacebookAccount, Boolean  idProof, Boolean  verifiedUser, String message,
				String profilePic) {
			super();
			this.status = status;
			this.isfacebookAccount = isfacebookAccount;
			this.idProof = idProof;
			this.verifiedUser = verifiedUser;
			this.message = message;
			this.profilePic = profilePic;
		}
		public UserSignInToken(Boolean  status,Boolean  idProof, Boolean  verifiedUser, String message) {
			super();
			this.status = status;
			this.idProof = idProof;
			this.verifiedUser = verifiedUser;
			this.message = message;
		}
		
		public UserSignInToken( Boolean  status, String profilePic, String message) {
			super();
			this.status = status;
			this.profilePic = profilePic;
			this.message = message;
		}
		public UserSignInToken( Boolean  status, String message) {
			super();
			this.status = status;
			this.message = message;
		}
		public UserSignInToken( Boolean  status, String kycFrontPic, String kycBackPic, String message) {
			super();
			this.status = status;
			this.kycFrontPic = kycFrontPic;
			this.kycBackPic = kycBackPic;
			this.message = message;
		}*/
		
	
		@JsonCreator
		public UserSignInJson( @JsonProperty("status")Boolean  status, @JsonProperty("message") String message) {
			this.status = status;
			this.message = message;
		}
		@JsonCreator
		public UserSignInJson(@JsonProperty("status")Boolean  status, @JsonProperty("verifiedUser")Boolean  verifiedUser, @JsonProperty("message")String message) {
			this.status = status;
			this.verifiedUser = verifiedUser;
			this.message = message;
		}
		@JsonCreator
		public UserSignInJson( @JsonProperty("status")Boolean  status, @JsonProperty("profilePic") String profilePic, @JsonProperty("message") String message) {
			this.status = status;
			this.profilePic = profilePic;
			this.message = message;
		}
		@JsonCreator
		public UserSignInJson(
				@JsonProperty("status")Boolean  status,@JsonProperty("idProof")Boolean idProof, @JsonProperty("verifiedUser")Boolean  verifiedUser, @JsonProperty("message")String message
				) {
			this.status = status;
			this.idProof = idProof;
			this.verifiedUser = verifiedUser;
			this.message = message;
		}
		 
		@JsonCreator
		public UserSignInJson( @JsonProperty("status")Boolean  status, @JsonProperty("kycFrontPic") String kycFrontPic, @JsonProperty("kycBackPic") String kycBackPic, @JsonProperty("message") String message) {
			this.status = status;
			this.kycFrontPic = kycFrontPic;
			this.kycBackPic = kycBackPic;
			this.message = message;
		}
		
		@JsonCreator
		public UserSignInJson(@JsonProperty("status")Boolean  status,@JsonProperty("isfacebookAccount")Boolean  isfacebookAccount, @JsonProperty("idProof")Boolean  idProof, @JsonProperty("verifiedUser")Boolean  verifiedUser, @JsonProperty("message")String message ,
				@JsonProperty("profilePic")String profilePic) {
			this.status = status;
			this.isfacebookAccount = isfacebookAccount;
			this.idProof = idProof;
			this.verifiedUser = verifiedUser;
			this.message = message;
			this.profilePic = profilePic;
		}
		@JsonCreator
		
//		@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
		public UserSignInJson(@JsonProperty("access_token")String access_token,@JsonProperty("expires_in") int expires_in, @JsonProperty("id")Long id, @JsonProperty("status")Boolean   status, @JsonProperty("firstName")String firstName,
				@JsonProperty("isfacebookAccount")Boolean   isfacebookAccount, @JsonProperty("idProof")Boolean   idProof, 
				@JsonProperty("verifiedUser")Boolean   verifiedUser, @JsonProperty("message")String message,
				@JsonProperty("profilePic")String profilePic,@JsonProperty("kycBackPic")String kycBackPic,
				@JsonProperty("kycFrontPic")String kycFrontPic) {
			this.access_token = access_token;
			this.expires_in = expires_in;
			this.id = id;
			this.status = status;
			this.firstName = firstName;
			this.isfacebookAccount = isfacebookAccount;
			this.idProof = idProof;
			this.verifiedUser = verifiedUser;
			this.message = message;
			this.profilePic = profilePic;
			
			this.kycFrontPic = kycFrontPic;
			this.kycBackPic = kycBackPic;
		}
	    

}
