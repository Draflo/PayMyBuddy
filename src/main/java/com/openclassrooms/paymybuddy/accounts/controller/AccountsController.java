package com.openclassrooms.paymybuddy.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.service.AccountsService;
import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.model.UserInformation;
import com.openclassrooms.paymybuddy.security.service.BuddyService;

@Controller
public class AccountsController {
	
	@Autowired
	private AccountsService accountsService;
	
	@Autowired
	private BuddyService buddyService;
	
	
	
	
	@RequestMapping(value = "/createAccount", method = {RequestMethod.GET, RequestMethod.POST})
	public String saveAccounts(Model model, Authentication authentication) {
		UserInformation userInformation = (UserInformation)authentication.getPrincipal();
		Buddy findByUsersUsername = buddyService.findByUsersUsername(userInformation.getUsername());
		accountsService.saveAccount(findByUsersUsername);
		Accounts createdAccounts = accountsService.findByBuddyEmail(findByUsersUsername.getEmail());
		model.addAttribute("balance", createdAccounts.getBalance());
		model.addAttribute("numberAcc", createdAccounts.getAccountNumber());
		return "redirect:/createdAccount";
	}
	
	@GetMapping("/myAccount")
	public String myAccount (Model model, Authentication authentication) {
		UserInformation userInformation  = (UserInformation)authentication.getPrincipal();
		Accounts accounts = accountsService.findByBuddyEmail(userInformation.getEmail());
		model.addAttribute("balance", accounts.getBalance());
		model.addAttribute("numberAcc", accounts.getAccountNumber());
		return "myAccount";
	}

}
