package com.thinkss.paycheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.thinkss.paycheck.entity.User;

/**
 * Created by raushan ranjan on 12-01-2018.
 */

public interface UserRepository extends JpaRepository<User, Long> {
	
	 User findByUsername( String username );
	 User findByFacebookId( String facebookId );
	 User findByEmailId( String email );
	 User findByPhoneNumber( String phoneNumber );

}