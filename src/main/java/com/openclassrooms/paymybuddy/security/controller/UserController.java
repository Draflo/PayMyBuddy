package com.openclassrooms.paymybuddy.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.security.model.Users;
import com.openclassrooms.paymybuddy.security.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/login")
	private String loginPage(Model model) {
		return "login";
	}
	
	@GetMapping("/")
	private String welcomePage(Model model) {
		return "welcomePage";
	}
	
	@GetMapping("/users")
	public Iterable<Users> getAll() {
		Iterable<Users> findAll = userService.findAll();
		return findAll;
	}
	
	@GetMapping("/createUser")
	public String showCreateUserForm(Model model) {
		Users user = new Users();
		model.addAttribute("user", user);
		return "createUser";
	}
	
	@Transactional
	@PostMapping("/createUser")
	public String saveUser(Model model, @ModelAttribute("user") Users user) {
		String username = user.getUsername();
		String password = user.getPassword();
		Users newUser = new Users();
		newUser.setUsername(username);
		newUser.setPassword(passwordEncoder.encode(password));
		newUser.setEnabled(true);
		userService.saveUser(newUser);
		
		return "redirect:/login";
		
	}
	
	

}
