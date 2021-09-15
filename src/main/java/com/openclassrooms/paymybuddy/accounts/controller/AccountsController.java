package com.openclassrooms.paymybuddy.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.service.AccountsService;
import com.openclassrooms.paymybuddy.security.model.UserInformation;

@Controller
public class AccountsController {
	
	@Autowired
	private AccountsService accountsService;
	
	@Autowired
	private UserInformation userInformation;
	
	
	@RequestMapping(value = "/createAccount", method = {RequestMethod.GET, RequestMethod.POST})
	public String saveAccounts(Model model, @ModelAttribute("accounts") Accounts accounts) {
		accounts.setBalance(0);
		accountsService.saveAccount(userInformation.getBuddy());
		model.addAttribute("balance", accounts.getBalance());
		model.addAttribute("numberAcc", accounts.getAccountNumber());
		return "redirect:/createdAccount";
	}
	
	@GetMapping("/myAccount")
	public String myAccount (Model model) {
		Accounts accounts = accountsService.findByOwnerEmail(null);
		model.addAttribute("balance", accounts.getBalance());
		model.addAttribute("numberAcc", accounts.getAccountNumber());
		return "myAccount";
	}

}
