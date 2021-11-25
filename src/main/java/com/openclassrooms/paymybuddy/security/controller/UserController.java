package com.openclassrooms.paymybuddy.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.model.Users;
import com.openclassrooms.paymybuddy.security.service.BuddyService;
import com.openclassrooms.paymybuddy.security.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BuddyService buddyService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static List<Buddy> usersList = new ArrayList<Buddy>();
	
	@GetMapping("/login")
	private String loginPage(Model model) {
		return "login";
	}
	
	@GetMapping("/")
	private String welcomePage(Model model) {
		return "welcomePage";
	}
	
	@GetMapping("/users")
	public String getAllUsers(Model model) {
		Iterable<Users> recup = userService.findAll();
		for (Users users : recup) {
			Buddy infoBuddy = buddyService.findByUsersUsername(users.getUsername());
			usersList.add(infoBuddy);
		}
		model.addAttribute("usersList", usersList);
		return "users";
	}
	
	@GetMapping("/createUser")
	public String showCreateUserForm(Model model) {
		Users user = new Users();
		model.addAttribute("user", user);
		return "createUser";
	}
	
	@Transactional
	@PostMapping("/createUser")
	public String createUser(Model model, @ModelAttribute("user") Users user) {
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
