package com.demo.spring.springsecurity.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.springsecurity.model.User;
import com.demo.spring.springsecurity.service.UserService;

@RestController
public class UserController {

	@Autowired private UserService userService;
	
	@GetMapping(value="/users")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> getAllusers() {
		List<User> users = userService.findAll();
		return new ResponseEntity<>(users , HttpStatus.OK);
	}
	
	@GetMapping(value="/getuser")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<User> getuser(Principal principal) {
		User user = userService.getUserByEmail(principal.getName());
		return new ResponseEntity<>(user , HttpStatus.OK);
	}
	
}
