package com.thinkss.paycheck.repository;

import org.springframework.data.repository.CrudRepository;

import com.thinkss.paycheck.entity.DocumentSubType;

public interface DocumentSubTypeRepository extends CrudRepository<DocumentSubType, Long> {
	
	DocumentSubType findByName( String name );

}
