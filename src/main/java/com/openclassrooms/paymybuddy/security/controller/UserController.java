package com.openclassrooms.paymybuddy.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.security.model.User;
import com.openclassrooms.paymybuddy.security.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	private String loginPage(Model model) {
		return "login";
	}
	
	@GetMapping("/")
	private String welcomePage(Model model) {
		return "welcomePage";
	}
	
	@GetMapping("/users")
	public Iterable<User> getAll() {
		Iterable<User> findAll = userService.findAll();
		return findAll;
	}
	
	@GetMapping("/createUser")
	public String showCreateUserForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "createUser";
	}
	
	@PostMapping("/createUser")
	public String saveUser(Model model, @ModelAttribute("user") User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setPassword(password);
		userService.saveUser(newUser);
		
		return "redirect:/login";
		
	}
	
	

}
