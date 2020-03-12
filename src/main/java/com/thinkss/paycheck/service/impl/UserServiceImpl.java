package com.thinkss.paycheck.service.impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.repository.UserRepository;
import com.thinkss.paycheck.service.UserService;
import com.thinkss.paycheck.view.BankView;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by raushan ranjan on 11-01-2018.
 */

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@PersistenceContext
	private EntityManager em;

	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		User u = userRepository.findByUsername(username);
		return u;
	}

	public User findById(Long id) throws AccessDeniedException {
		User u = userRepository.findOne(id);
		return u;
	}

	public List<User> findAll() throws AccessDeniedException {
		List<User> result = (List<User>) userRepository.findAll();
		return result;
	}

	public User save(User user) {

		return userRepository.save(user);
	}

	public User findByFacebookId(String facebookId) throws UsernameNotFoundException {
		User u = userRepository.findByFacebookId(facebookId);
		return u;
	}

	public User findByEmailId(String email) {
		User u = userRepository.findByEmailId(email);
		return u;
	}

	public User findUserByEmailId(String email) {
		User u = userRepository.findByEmailId(email);
		return u;
	}

	@SuppressWarnings({ "unchecked", "unchecked" })
	public List<User> findAllUser(int start, int limit, String dir, String sort, JSONArray crit) throws JSONException {

		StringBuilder queryString = new StringBuilder();
		ArrayList<String> queryWhere = new ArrayList<String>();
		ArrayList<Object> queryParams = new ArrayList<Object>();
		queryString.append("select user from User user ");// limit
		for (int i = 0; i < crit.length(); i++) {
			JSONObject obj = crit.getJSONObject(i);
			if (obj.get("property").equals("firstName")) {
				queryWhere.add(" user.firstName LIKE  ? ");
				queryParams.add("%" + (String) obj.optString("value") + "%");
			}
			/*
			 * if(obj.get("property").equals("countryName")) {
			 * queryWhere.add(" user.countryName LIKE  ? ");
			 * queryParams.add("%"+(String)obj.optString("value")+"%"); }
			 */
		}

		// construct the WHERE clause
		if (!queryWhere.isEmpty()) {
			queryString.append(" WHERE ");
			for (int i = 0; i < queryWhere.size(); i++) {
				if (i != 0) {
					queryString.append(" AND ");
				}
				queryString.append(queryWhere.get(i));
			}
		}

		// ORDER BY
		queryString.append(" ORDER BY user." + sort + " " + dir);

		// create the query and apply the parameters
		Query query = em.createQuery(queryString.toString());
		for (int i = 0; i < queryParams.size(); i++) {
			query.setParameter(i + 1, queryParams.get(i));
		}
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();

	}

}
