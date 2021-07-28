package com.openclassrooms.paymybuddy.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.service.BuddyService;

@RestController
public class BuddyController {

	@Autowired
	private BuddyService buddyService;
	
	@GetMapping("/welcome")
	public String welcomePage() {
		return "Welcome to PayMyBuddy";
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
