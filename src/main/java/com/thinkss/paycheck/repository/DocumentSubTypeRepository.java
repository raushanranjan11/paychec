package com.thinkss.paycheck.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.thinkss.paycheck.entity.DocumentSubType;
import com.thinkss.paycheck.entity.DocumentType;

public interface DocumentSubTypeRepository extends CrudRepository<DocumentSubType, Long> {
	
	DocumentSubType findByName( String name );
	List<DocumentSubType> findBydocumentType( DocumentType documentType );

}
