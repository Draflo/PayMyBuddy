package com.openclassrooms.paymybuddy.accounts.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
	
	public static String accountNumber() {
		Random random = new Random();
		int number = random.nextInt(999999);
		return String.format("%06d", number);
	}
	
	
	@RequestMapping(value = "/createAccount", method = {RequestMethod.GET, RequestMethod.POST})
	public String saveAccounts(Model model, @ModelAttribute("accounts") Accounts accounts, Authentication authentication) {
		UserInformation userInformation = (UserInformation)authentication.getPrincipal();
		accounts.setBalance(0);
		accounts.setBuddy(userInformation.getBuddy());
		accounts.setAccountNumber(userInformation.getBuddy().getId() + accountNumber());
		accountsService.saveAccount(userInformation.getBuddy());
		model.addAttribute("balance", accounts.getBalance());
		model.addAttribute("numberAcc", accounts.getAccountNumber());
		return "redirect:/createdAccount";
	}
	
	@GetMapping("/myAccount")
	public String myAccount (Model model, @ModelAttribute("accounts") Accounts accounts, Authentication authentication) {
		UserInformation userInformation  = (UserInformation)authentication.getPrincipal();
		accountsService.findByBuddyEmail(userInformation.getEmail());
		model.addAttribute("balance", accounts.getBalance());
		model.addAttribute("numberAcc", accounts.getAccountNumber());
		return "myAccount";
	}

}
