package com.thinkss.paycheck.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.thinkss.paycheck.entity.User;


/**
 * Created by raushan ranjan on 12-01-2018.
 */
public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll ();
    public User save(User user);
    public User findByFacebookId( String facebookId );
    public User findByEmailId( String email );
    public User findUserByEmailId(String email);
    public List<User> findAllUser (int start,int limit,String dir,String sort,JSONArray crit) throws JSONException;
//    public User findUserByEmailId(String email) 
//    public User findBy
}
