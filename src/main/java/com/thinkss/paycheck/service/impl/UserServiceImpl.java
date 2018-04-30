package com.thinkss.paycheck.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.repository.UserRepository;
import com.thinkss.paycheck.service.UserService;

import java.util.List;

/**
 * Created by raushan ranjan on 11-01-2018.
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername( String username ) throws UsernameNotFoundException {
        User u = userRepository.findByUsername( username );
        return u;
    }

    public User findById( Long id ) throws AccessDeniedException {
        User u = userRepository.findOne( id );
        return u;
    }

    public List<User> findAll() throws AccessDeniedException {
        List<User> result = (List<User>) userRepository.findAll();
        return result;
    }
    
    public User save(User user){
       
        return userRepository.save(user);
    }
    
    public User findByFacebookId( String facebookId ) throws UsernameNotFoundException {
        User u = userRepository.findByFacebookId( facebookId );
        return u;
    }
    
    public User findByEmailId( String email ) {
    	 User u = userRepository.findByEmailId( email );
         return u;
    }

}
