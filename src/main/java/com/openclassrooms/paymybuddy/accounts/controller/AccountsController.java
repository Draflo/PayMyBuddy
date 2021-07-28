package com.openclassrooms.paymybuddy.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.service.AccountsService;
import com.openclassrooms.paymybuddy.security.model.Buddy;

@RestController
public class AccountsController {
	
	@Autowired
	private AccountsService accountsService;
	
	@PostMapping("/createAccount")
	public ResponseEntity<Accounts> createAccount(@RequestBody Buddy owner, Accounts accounts) {
		Accounts saveAccounts = accountsService.saveAccount(owner);
		return ResponseEntity.created(null).body(saveAccounts);
	}

}
