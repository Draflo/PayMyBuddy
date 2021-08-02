package com.openclassrooms.paymybuddy.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.openclassrooms.paymybuddy.security.model.User;
import com.openclassrooms.paymybuddy.security.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	private String loginPage(Model model) {
		return "login";
	}
	
	@GetMapping("/users")
	public Iterable<User> getAll() {
		Iterable<User> findAll = userService.findAll();
		return findAll;
	}
	
	@PostMapping("/createUser")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User saveUser = userService.saveUser(user);
		return ResponseEntity.created(null).body(saveUser);
	}
	

}
