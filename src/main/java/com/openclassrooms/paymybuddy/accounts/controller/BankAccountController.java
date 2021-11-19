package com.openclassrooms.paymybuddy.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.model.BankAccount;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.accounts.service.BankAccountService;
import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.service.BuddyService;

@Controller
public class BankAccountController {
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@Autowired
	private BuddyService buddyService;
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@GetMapping("/addABankAccount")
	public String showBankAccountForm(Model model) {
		BankAccount ba = new BankAccount();
		model.addAttribute("bankAccount", ba);
		return "addABankAccount";
	}
	
	@PostMapping("/addABankAccount")
	public String saveBankAccount(Model model, Authentication authentication, @ModelAttribute("bankAccount") BankAccount bankAccount)
			throws Exception {
		String IBAN = bankAccount.getIBAN();
		BankAccount newBA = new BankAccount();
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		Buddy buddy = buddyService.findByUsersUsername(username);
		Accounts myAccounts = accountsRepository.findByBuddyEmail(buddy.getEmail());
		newBA.setAccounts(myAccounts);
		newBA.setIBAN(IBAN);
		bankAccountService.saveBankAccount(bankAccount);
		return "redirect:/myAccount";
	}
		
			

}
