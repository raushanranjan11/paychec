package com.thinkss.paycheck.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkss.paycheck.entity.DocumentDetails;
import com.thinkss.paycheck.entity.DocumentSubType;
import com.thinkss.paycheck.entity.DocumentType;
import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.repository.DocumentDetailRepository;
import com.thinkss.paycheck.repository.DocumentSubTypeRepository;
import com.thinkss.paycheck.repository.DocumentTypeRepository;
import com.thinkss.paycheck.repository.UserRepository;
import com.thinkss.paycheck.service.DocumentService;


@Service
public class DocumentServiceImpl implements DocumentService  {
	
	 @Autowired
	    private DocumentSubTypeRepository documentSubTypeRepository;
	 
	 @Autowired
		private DocumentTypeRepository documentTypeRepository;
	 @Autowired
		private DocumentDetailRepository documentDetailRepository;
	 
	 @PersistenceContext
     private EntityManager em;
	 
	 
	 
	@Override
	public DocumentType findById(Long id) {
		// TODO Auto-generated method stub
		return documentTypeRepository.findOne(id);
	}
	
	public DocumentDetails findDocumentDetailByUser(User user, DocumentType documentTypes,DocumentSubType documentSubType) {
		System.out.println("  "+ user.getId() +"          "+ documentTypes.getId() + "      "+ documentSubType.getId());
		Query query = em.createQuery(
				"select di from DocumentDetails di where user = :user and dcumentType = :dcumentType and documentSubType =:documentSubType  ")
				.setParameter("user", user).setParameter("dcumentType", documentTypes)
				.setParameter("documentSubType", documentSubType);
		
		DocumentDetails dd = null;
		try{
		dd = (DocumentDetails)query.getSingleResult();
		}catch (NoResultException nre){
		//Ignore this because as per your logic this is ok!
		}

		
		return dd;
		
		
	}
	
	public List<DocumentSubType> findByDocumentType(DocumentType documentType){
		Query query = em.createQuery(
				"select dst from DocumentSubType dst where  documentType = :documentType ").
				setParameter("documentType", documentType);
		return  query.getResultList();
	}
	
	 



	 

}
