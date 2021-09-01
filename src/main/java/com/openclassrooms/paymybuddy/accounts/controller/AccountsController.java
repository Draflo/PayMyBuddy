package com.openclassrooms.paymybuddy.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.service.AccountsService;

@RestController
public class AccountsController {
	
	@Autowired
	private AccountsService accountsService;
	
	@PostMapping("/createAccount")
	public String saveAccounts(Model model, @ModelAttribute("accounts") Accounts accounts) {
		accounts.setBalance(0);
		accountsService.saveAccount(null);
		return "redirect:/home";
	}

}
