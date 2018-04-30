package com.thinkss.paycheck.repository;

import org.springframework.data.repository.CrudRepository;

import com.thinkss.paycheck.entity.Authority;

/**
 * Created by raushan ranjan on 12-01-2018.
 */

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
	
	Authority findByName( String name );

}
