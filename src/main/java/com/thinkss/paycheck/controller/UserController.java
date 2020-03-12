package com.thinkss.paycheck.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thinkss.paycheck.bean.Registration;
import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.entity.UserSignInToken;
import com.thinkss.paycheck.service.UserService;
import com.thinkss.paycheck.service.impl.CustomUserDetailsService;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	/* changing password with old and new passwords */

	@RequestMapping(value = "/passwordChange", method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) throws JSONException {

		boolean isChanged = userDetailsService.changeOldPassword(passwordChanger.oldPassword,
				passwordChanger.newPassword);

		if (isChanged) {
			return ResponseEntity.ok(new UserSignInToken(true, "Password is updated "));
		} else {
			return ResponseEntity.ok(new UserSignInToken(false, "Your current password is wrong."));
		}

	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}

	@RequestMapping(value = "/{id}/update", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUserProfile(@PathVariable("id") long id, @RequestBody Registration user)
			throws ParseException {
		System.out.println("Updating User " + id);
		Map<String, Object> map = new HashMap<String, Object>();
		User currentUser = userService.findById(id);

		if (currentUser == null) {
			return ResponseEntity.ok(new UserSignInToken(false, "User Not found "));
		} else {
			if (user.getFirstName() != null && user.getFirstName() != "")
				currentUser.setFirstName(user.getFirstName());
			if (user.getLastName() != null && user.getLastName() != "")
				currentUser.setLastName(user.getLastName());
			if (user.getPhoneNumber() != null && user.getPhoneNumber() != "")
				currentUser.setPhoneNumber(user.getPhoneNumber());
			if (user.getEmailId() != null && user.getEmailId() != "")
				currentUser.setEmailId(user.getEmailId());

			if (user.getBirthDate() != null && user.getBirthDate() != "") {
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date birthDate = df.parse(user.getBirthDate());
				currentUser.setBirthDate(birthDate);
			}

			User u = userService.save(currentUser);
			map.put("status", true);
			map.put("user", u);
			return ResponseEntity.ok(map);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		User currentUser = userService.findById(id);
		map.put("status", true);
		map.put("user", currentUser);
		return ResponseEntity.ok(map);
	}
}
