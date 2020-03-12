package com.thinkss.paycheck.bean;

import java.io.Serializable;
import java.util.List;

public class DocumentTypeBean implements Serializable{
	
	private Long id;
	private String name;
	private List<DocumentSubTypeBean> documentSubTypeBean ;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<DocumentSubTypeBean> getDocumentSubTypeBean() {
		return documentSubTypeBean;
	}
	public void setDocumentSubTypeBean(List<DocumentSubTypeBean> documentSubTypeBean) {
		this.documentSubTypeBean = documentSubTypeBean;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
