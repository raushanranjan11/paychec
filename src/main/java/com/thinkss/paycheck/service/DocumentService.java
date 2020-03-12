package com.thinkss.paycheck.service;

import java.util.List;

import com.thinkss.paycheck.entity.DocumentDetails;
import com.thinkss.paycheck.entity.DocumentSubType;
import com.thinkss.paycheck.entity.DocumentType;
import com.thinkss.paycheck.entity.User;

public interface DocumentService {
//	 public User save(User user)
	
	public DocumentType findById(Long id );
	public DocumentDetails findDocumentDetailByUser(User user, DocumentType documentTypes,DocumentSubType documentSubType);
	
	public List<DocumentSubType> findByDocumentType(DocumentType documentType);
	public DocumentDetails findDocumentDetailByUser(User user, DocumentType documentTypes) ;

}
