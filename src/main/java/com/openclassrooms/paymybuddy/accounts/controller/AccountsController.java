package com.openclassrooms.paymybuddy.accounts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.service.AccountsService;
import com.openclassrooms.paymybuddy.exception.AccountsAlreadyExistException;
import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.service.BuddyService;
import com.openclassrooms.paymybuddy.transactions.model.Transaction;
import com.openclassrooms.paymybuddy.transactions.service.TransactionService;

@Controller
public class AccountsController {

	@Autowired
	private AccountsService accountsService;
	
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private BuddyService buddyService;

	@RequestMapping(value = "/createAccount", method = { RequestMethod.GET, RequestMethod.POST })
	public String createAccount(Model model, Authentication authentication) throws AccountsAlreadyExistException {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		Buddy findByUsersUsername = buddyService.findByUsersUsername(username);
		accountsService.createAccount(findByUsersUsername);
		Accounts createdAccounts = accountsService.findByBuddyEmail(findByUsersUsername.getEmail());
		model.addAttribute("balance", createdAccounts.getBalance());
		model.addAttribute("numberAcc", createdAccounts.getAccountNumber());
		return "createdAccount";
	}

	@GetMapping("/myAccount")
	public String myAccount(Model model, Authentication authentication) {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		Buddy findByUsersUsername = buddyService.findByUsersUsername(username);
		Accounts accounts = accountsService.findByBuddyEmail(findByUsersUsername.getEmail());
		List<Transaction> myTransactions = transactionService.findByUsersUsername(username);
		model.addAttribute("myTransactions", myTransactions);
		model.addAttribute("balance", accounts.getBalance());
		model.addAttribute("numberAcc", accounts.getAccountNumber());
		return "myAccount";
	}

}
