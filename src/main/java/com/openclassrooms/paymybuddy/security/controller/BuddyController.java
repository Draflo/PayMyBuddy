package com.openclassrooms.paymybuddy.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.service.BuddyService;

@Controller
public class BuddyController {

	@Autowired
	private BuddyService buddyService;
	
	@Value("${error.message")
	private String errorMessage;
	
	@GetMapping("/welcome")
	public String welcomePage(Model model) {
		model.addAttribute("welcome", "Welcome to the Home Page" );
		return "Buddy";
	}
	
	@PostMapping("/createBuddy")
	public ResponseEntity<Buddy> createBuddy(@RequestBody Buddy buddy) {
		Buddy saveBuddy = buddyService.saveBuddy(buddy);
		return ResponseEntity.created(null).body(saveBuddy);
	}
	
	@GetMapping("/buddys")
	public Iterable<Buddy> getAll() {
		Iterable<Buddy> findAll = buddyService.findAll();
		return findAll;
	}
}
