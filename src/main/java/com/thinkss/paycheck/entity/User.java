package com.thinkss.paycheck.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

/*@Entity
@Table(name = "User")
public class User implements Serializable{

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;

    private String email;

    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    
    
    
    
}
*/

//package com.thinkss.paycheck.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by raushan ranjan on 12-01-2018.
 */

@Entity
@Table(name = "USERS")
public class User implements UserDetails {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username")
	private String username;

	 @JsonIgnore
	@Column(name = "password")
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String emailId;

	@Column(name = "phone_number")
	private String phoneNumber;

	@JsonIgnore
	@Column(name = "enabled")
	private boolean enabled;

	@JsonIgnore
	@Column(name = "last_password_reset_date")
	private Date lastPasswordResetDate;

	@Column(name = "last_login_provider")
	private boolean loginProvier;
	@Column(name = "verify_account_user")
	private boolean verifyByUser;
	@Column(name = "verify_account_admin")
	private boolean verifiedByAdmin;

	@JsonIgnore
	@Column(name = "acc_created_date")
	private Date accountCreatedDate;

	@JsonIgnore
	@Column(name = "device_id")
	private String deviceId;
	//facebook_id
	@Column(name = "facebook_id")
	private String facebookId;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private List<Authority> authorities;
	
	@JsonIgnore
	@Column(name = "otp")
	private String otp;
	
	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "otp_created_date")
	private Date otpGeneratedDate;
	
//	@Column(name = "otp_created_date")country_code
//	private Boolean verify;
	
	@Column(name = "country_code")
	private String countryCode;
	
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="IST")
	@Column(name = "birth_date")
	private Date birthDate;
	
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		// Timestamp now = new Timestamp(DateTime.now().getMillis());
		this.setLastPasswordResetDate(new Date());
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isLoginProvier() {
		return loginProvier;
	}

	public void setLoginProvier(boolean loginProvier) {
		this.loginProvier = loginProvier;
	}

	public boolean isVerifyByUser() {
		return verifyByUser;
	}

	public void setVerifyByUser(boolean verifyByUser) {
		this.verifyByUser = verifyByUser;
	}

	public boolean isVerifiedByAdmin() {
		return verifiedByAdmin;
	}

	public void setVerifiedByAdmin(boolean verifiedByAdmin) {
		this.verifiedByAdmin = verifiedByAdmin;
	}

	public Date getAccountCreatedDate() {
		return accountCreatedDate;
	}

	public void setAccountCreatedDate(Date accountCreatedDate) {
//		this.setLastPasswordResetDate(new Date());
		this.accountCreatedDate = accountCreatedDate;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	
	
	private String profilePic;
	private String kycFrontPic;
	private String kycBackPic;
	
	@Column(name = "profile_pic")
	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	@Column(name = "kyc_front_pic")
	public String getKycFrontPic() {
		return kycFrontPic;
	}

	public void setKycFrontPic(String kycFrontPic) {
		this.kycFrontPic = kycFrontPic;
	}
	
	@Column(name = "kyc_back_pic")
	public String getKycBackPic() {
		return kycBackPic;
	}

	public void setKycBackPic(String kycBackPic) {
		this.kycBackPic = kycBackPic;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Date getOtpGeneratedDate() {
		return otpGeneratedDate;
	}

	public void setOtpGeneratedDate(Date otpGeneratedDate) {
		this.otpGeneratedDate = otpGeneratedDate;
	}
	
	
	@OneToMany(cascade = CascadeType.ALL,	fetch = FetchType.LAZY, mappedBy = "user")
	private List<Loan> loan;
//	@JsonIgnore
	 // @Fetch(value = FetchMode.SUBSELECT) 
	
	public List<Loan> getLoan() {
		return loan;
	}

	public void setLoan(List<Loan> loan) {
		this.loan = loan;
	}

	@OneToMany(cascade = CascadeType.ALL,	fetch = FetchType.LAZY, mappedBy = "user")
	private List<UserBankAccount> bankAccount;
	
	
	public List<UserBankAccount> getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(List<UserBankAccount> bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	
	@OneToMany(cascade = CascadeType.ALL,	fetch = FetchType.LAZY, mappedBy = "user")
private List<DocumentDetails>  documentDetails;
	
	public List<DocumentDetails> getDocumentDetails() {
		return documentDetails;
	}

	public void setDocumentDetails(List<DocumentDetails> documentDetails) {
		this.documentDetails = documentDetails;
	}

	
	

}
