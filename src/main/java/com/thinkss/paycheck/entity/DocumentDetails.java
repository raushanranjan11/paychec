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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "document_detail")
public class DocumentDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private DocumentType dcumentType;
	private DocumentSubType documentSubType;
	private User user;
	private String ImageSource;
	
	
	
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
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="document_type_id")
	public DocumentType getDcumentType() {
		return dcumentType;
	}
	public void setDcumentType(DocumentType dcumentType) {
		this.dcumentType = dcumentType;
	}
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="document_sub_type_id")
	public DocumentSubType getDocumentSubType() {
		return documentSubType;
	}
	public void setDocumentSubType(DocumentSubType documentSubType) {
		this.documentSubType = documentSubType;
	}
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="user_id")
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
	
	
	
	

}
