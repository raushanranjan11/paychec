package com.thinkss.paycheck.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "document_detail")
public class DocumentDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private DocumentType dcumentType;
	private DocumentSubType documentSubType;
	private User user;
	private String ImageSource;

	/*
	 * private boolean facebookActive; private boolean linkedActive; private boolean
	 * emailActive; private boolean bankstatActive; private boolean emiratIdActive;
	 * private boolean salaryCertiActive; private boolean passportActive; private
	 * boolean uaeResActive; private boolean uaeOtherActive;//is_Active
	 */ private boolean active;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "document_type_id")
	public DocumentType getDcumentType() {
		return dcumentType;
	}

	public void setDcumentType(DocumentType dcumentType) {
		this.dcumentType = dcumentType;
	}

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "document_sub_type_id")
	public DocumentSubType getDocumentSubType() {
		return documentSubType;
	}

	public void setDocumentSubType(DocumentSubType documentSubType) {
		this.documentSubType = documentSubType;
	}

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "source")
	public String getImageSource() {
		return ImageSource;
	}

	public void setImageSource(String imageSource) {
		ImageSource = imageSource;
	}

	@Override
	public String toString() {
		return "DocumentDetails [id=" + id + ", dcumentType=" + dcumentType + ", documentSubType=" + documentSubType
				+ ", user=" + user + ", ImageSource=" + ImageSource + "]";
	}

	/*
	 * @Column(name = "facebook_active") public boolean isFacebookActive() { return
	 * facebookActive; } public void setFacebookActive(boolean facebookActive) {
	 * this.facebookActive = facebookActive; }
	 * 
	 * @Column(name = "linked_active") public boolean isLinkedActive() { return
	 * linkedActive; } public void setLinkedActive(boolean linkedActive) {
	 * this.linkedActive = linkedActive; }
	 * 
	 * @Column(name = "email_active") public boolean isEmailActive() { return
	 * emailActive; } public void setEmailActive(boolean emailActive) {
	 * this.emailActive = emailActive; }
	 * 
	 * @Column(name = "bank_stat_active") public boolean isBankstatActive() { return
	 * bankstatActive; }
	 * 
	 * public void setBankstatActive(boolean bankstatActive) { this.bankstatActive =
	 * bankstatActive; }
	 * 
	 * @Column(name = "emirat_id_active") public boolean isEmiratIdActive() { return
	 * emiratIdActive; } public void setEmiratIdActive(boolean emiratIdActive) {
	 * this.emiratIdActive = emiratIdActive; }
	 * 
	 * @Column(name = "salary_certi_active") public boolean isSalaryCertiActive() {
	 * return salaryCertiActive; } public void setSalaryCertiActive(boolean
	 * salaryCertiActive) { this.salaryCertiActive = salaryCertiActive; }
	 * 
	 * @Column(name = "passport_active") public boolean isPassportActive() { return
	 * passportActive; } public void setPassportActive(boolean passportActive) {
	 * this.passportActive = passportActive; }
	 * 
	 * @Column(name = "uae_resi_active") public boolean isUaeResActive() { return
	 * uaeResActive; } public void setUaeResActive(boolean uaeResActive) {
	 * this.uaeResActive = uaeResActive; }
	 * 
	 * @Column(name = "uae_other_active") public boolean isUaeOtherActive() { return
	 * uaeOtherActive; } public void setUaeOtherActive(boolean uaeOtherActive) {
	 * this.uaeOtherActive = uaeOtherActive; }
	 */
	@Column(name = "is_Active")
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	private String text;

	@Transient
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private Long documentSubTypeId;

	@Transient
	public Long getDocumentSubTypeId() {
		return this.getDocumentSubType().getId();
	}

	/*
	 * public void setDocumentSubTypeId(Long documentSubTypeId) {
	 * this.documentSubTypeId = documentSubTypeId; }
	 */

}
