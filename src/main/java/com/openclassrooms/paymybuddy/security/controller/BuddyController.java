package com.openclassrooms.paymybuddy.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.service.BuddyService;

@Controller
public class BuddyController {

	private static List<Buddy> buddies = new ArrayList<Buddy>();
	
	@Autowired
	private BuddyService buddyService;
	
	@Value("${error.message")
	private String errorMessage;
	
	@GetMapping("/home")
	public String welcomePage(Model model) {
		return "home";
	}
	
	@GetMapping("/createBuddy")
	public String showCreateBuddyForm(Model model) {
		Buddy buddy = new Buddy();
		model.addAttribute("buddy", buddy);
		return "createBuddy";
	}
	
	@PostMapping("/createBuddy")
	public String saveBuddy(Model model, @ModelAttribute("buddy") Buddy buddy) {
		String firstName = buddy.getFirstName();
		String lastName = buddy.getLastName();
		String birthdate = buddy.getBirthdate();
		String email = buddy.getEmail();
		Buddy newBuddy = new Buddy();
		newBuddy.setFirstName(firstName);
		newBuddy.setLastName(lastName);
		newBuddy.setBirthdate(birthdate);
		newBuddy.setEmail(email);

		buddyService.saveBuddy(newBuddy);
		buddies.add(newBuddy);
		return "redirect:/buddies";
	}
	
	@GetMapping("/friendList")
	public String friendList(Model model) {
		model.addAttribute("friendList", buddies);
		return "friendList";
	}
}
