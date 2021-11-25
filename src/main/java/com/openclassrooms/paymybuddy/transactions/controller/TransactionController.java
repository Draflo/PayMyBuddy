package com.openclassrooms.paymybuddy.transactions.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.security.model.UserInformation;
import com.openclassrooms.paymybuddy.transactions.model.Transaction;
import com.openclassrooms.paymybuddy.transactions.service.TransactionService;

@Controller
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@GetMapping("/myTransactions")
	public String myTransactions (Model model, Authentication authentication) {
		UserInformation userInformation = (UserInformation) authentication.getPrincipal();
		List<Transaction> myTransactions = transactionService.findByUsersUsername(userInformation.getUsername());
		model.addAttribute("myTransactions", myTransactions);
		return "myTransactions";
	}
	
	@GetMapping("/transfer")
	public String transferForm(Model model) {
		Transaction transaction = new Transaction();
		model.addAttribute("transaction", transaction);
		return "transfer";
	}
	
	@PostMapping("/transfer")
	public String transfer (@ModelAttribute("transaction") Transaction transactionInfo, Model model, Authentication authentication, @RequestParam String email) {
		UserInformation userInformation = (UserInformation) authentication.getPrincipal();
		Accounts myAccounts = accountsRepository.findByBuddyEmail(userInformation.getEmail());
		model.addAttribute("balance", myAccounts.getBalance());
		String description = transactionInfo.getDescription();
		Double amount = transactionInfo.getAmount();
		Transaction transaction = new Transaction();
		transaction.setSenderAccounts(myAccounts);
		transaction.setReceiverAccounts(accountsRepository.findByBuddyEmail(email));
		transaction.setTransactionDate(LocalDate.now());
		transaction.setAmount(amount);
		transaction.setDescription(description);
		transactionService.validation(myAccounts, transaction);
		transactionService.balanceUpdate(myAccounts, transaction.getReceiverAccounts(), transaction);
		return "home";
	}

}
