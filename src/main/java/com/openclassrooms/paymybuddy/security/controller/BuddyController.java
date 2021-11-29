package com.openclassrooms.paymybuddy.security.controller;

import java.security.Principal;
import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.service.BuddyService;
import com.openclassrooms.paymybuddy.security.service.UserService;

@Controller
public class BuddyController {

	@Autowired
	private BuddyService buddyService;

	@Autowired
	private UserService userService;

	@Value("${error.message")
	private String errorMessage;

	@GetMapping("/home")
	public String welcomePage(Model model) {
		return "home";
	}

	@GetMapping("/createBuddy")
	public String showCreateBuddyForm(Model model) {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		Buddy findByUsersUsername = buddyService.findByUsersUsername(username);
		if (findByUsersUsername != null) {
			return "redirect:/home";
		} else {
			Buddy buddy = new Buddy();
			model.addAttribute("buddy", buddy);
			return "createBuddy";
		}
	}

	@Transactional
	@PostMapping("/createBuddy")
	public String saveBuddy(Model model, @ModelAttribute("buddy") Buddy buddy, Principal principal) {
		String firstName = buddy.getFirstName();
		String lastName = buddy.getLastName();
		String birthdate = buddy.getBirthdate();
		String email = buddy.getEmail();
		Buddy newBuddy = new Buddy();
		newBuddy.setFirstName(firstName);
		newBuddy.setLastName(lastName);
		newBuddy.setBirthdate(birthdate);
		newBuddy.setEmail(email);
		newBuddy.setUsers(userService.findByUsername(principal.getName()));
		String message = "This email is already taken";
		try {
			buddyService.saveBuddy(newBuddy);
		} catch (SQLIntegrityConstraintViolationException e) {
			model.addAttribute("error", message);
			return "home";
		}
		return "redirect:/createAccount";
	}

}
