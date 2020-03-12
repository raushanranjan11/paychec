package com.thinkss.paycheck.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "document_type")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DocumentType  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private List<DocumentSubType> documentList;
	
	private List<Object> items  = new ArrayList<Object>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,	fetch = FetchType.LAZY, mappedBy = "documentType")
	public List<DocumentSubType> getDocumentList() {
		return documentList;
	}
	public void setDocumentList(List<DocumentSubType> documentList) {
		this.documentList = documentList;
	}
	@Transient
	public List<Object> getItems() {
		return items;
	}
	public void setItems(List<Object> items) {
		this.items = items;
	}
	/*@Override
	public String toString() {
		return "DocumentType [id=" + id + ", name=" + name + ", documentList=" + documentList + ", items=" + items
				+ "]";
	}*/
	
	private List<DocumentSubType> subTypeList;

	@Transient
	public List<DocumentSubType> getSubTypeList() {
		return subTypeList;
	}
	public void setSubTypeList(List<DocumentSubType> subTypeList) {
		this.subTypeList = subTypeList;
	}
	
	
	
	
	
	

}
