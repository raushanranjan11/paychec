package com.thinkss.paycheck.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.thinkss.paycheck.entity.DocumentDetails;
import com.thinkss.paycheck.entity.DocumentSubType;
import com.thinkss.paycheck.entity.DocumentType;
import com.thinkss.paycheck.entity.User;

public interface DocumentDetailRepository extends CrudRepository<DocumentDetails, Long> {
	
	DocumentDetails findByDocumentSubTypeAndUser( DocumentSubType documentSubType ,User user );//DocumentSubType documentSubType;
	//private User user;
	List<DocumentDetails> findByDcumentTypeAndUser( DocumentType documentType ,User user );
	
	DocumentDetails findByDcumentTypeAndDocumentSubTypeAndUser( DocumentType dcumentType,DocumentSubType documentSubType ,User user );
	List<DocumentDetails> findByUser( User user );

}
