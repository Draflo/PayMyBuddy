package com.openclassrooms.paymybuddy.transactions.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.exception.ConnectionDoesNotExistException;
import com.openclassrooms.paymybuddy.exception.InsufficientFundsException;
import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.repository.BuddyRepository;
import com.openclassrooms.paymybuddy.transactions.model.Transaction;
import com.openclassrooms.paymybuddy.transactions.service.TransactionService;

@Controller
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private BuddyRepository buddyRepository;

	@Autowired
	private AccountsRepository accountsRepository;

	@GetMapping("/transfer")
	public String transferForm(Model model, Authentication authentication) {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		Buddy myself = buddyRepository.findByUsersUsername(username);
		Accounts myAccounts = accountsRepository.findByBuddyEmail(myself.getEmail());
		Transaction transaction = new Transaction();
		model.addAttribute("balance", myAccounts.getBalance());
		model.addAttribute("transaction", transaction);
		model.addAttribute("connections", myAccounts.getConnections());
		return "transfer";
	}

	@PostMapping("/transfer")
	public String transfer(@Valid @ModelAttribute("transaction") Transaction transactionInfo, Model model,
			Authentication authentication, BindingResult bindingResult) {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		Buddy myself = buddyRepository.findByUsersUsername(username);
		Accounts myAccounts = accountsRepository.findByBuddyEmail(myself.getEmail());
		model.addAttribute("balance", myAccounts.getBalance());
		String description = transactionInfo.getDescription();
		Double amount = transactionInfo.getAmount();
		Accounts receiverAccounts = transactionInfo.getReceiverAccounts();
		Transaction transaction = new Transaction();
		transaction.setSenderAccounts(myAccounts);
		transaction.setReceiverAccounts(receiverAccounts);
		transaction.setTransactionDate(LocalDate.now());
		transaction.setAmount(amount);
		transaction.setDescription(description);
		transaction.setFee(0.05);
		try {
			transactionService.validation(myAccounts, transaction);
		} catch (InsufficientFundsException e) {
			bindingResult.rejectValue("amount", e.getErrorCode(), e.getDefaultMessage());
			return "transfer";
		} catch (ConnectionDoesNotExistException e) {
			e.printStackTrace();
		}
		transactionService.balanceUpdate(myAccounts, transaction.getReceiverAccounts(), transaction);
		transactionService.saveTransaction(transaction);
		return "redirect:/myAccount";
	}

}
