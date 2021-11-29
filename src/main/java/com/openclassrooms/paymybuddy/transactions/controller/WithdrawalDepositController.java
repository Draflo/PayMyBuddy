package com.openclassrooms.paymybuddy.transactions.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.accounts.repository.BankAccountRepository;
import com.openclassrooms.paymybuddy.exception.BankAccountDoesNotExist;
import com.openclassrooms.paymybuddy.exception.InsufficientFundsException;
import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.repository.BuddyRepository;
import com.openclassrooms.paymybuddy.transactions.model.WithdrawalDeposit;
import com.openclassrooms.paymybuddy.transactions.service.WithdrawalDepositService;

@Controller
public class WithdrawalDepositController {
	
	@Autowired
	private WithdrawalDepositService withdrawalDepositService;
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	private BuddyRepository buddyRepository;
	
	@GetMapping("/myWD")
	public String myTransactions (Model model, Authentication authentication) {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		List<WithdrawalDeposit> myWD = withdrawalDepositService.findByUsersUsername(username);
		model.addAttribute("myWD", myWD);
		return "myWD";
	}

	@GetMapping("/withdrawal")
	public String withdrawForm(Model model, Authentication authentication) {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		Buddy myself = buddyRepository.findByUsersUsername(username);
		Accounts myAccounts = accountsRepository.findByBuddyEmail(myself.getEmail());
		WithdrawalDeposit withdrawalDeposit = new WithdrawalDeposit();
		model.addAttribute("balance", myAccounts.getBalance());
		model.addAttribute("withdrawaldeposit", withdrawalDeposit);
		return "withdrawal";
	}
	
	@GetMapping("/deposit")
	public String depositForm(Model model, Authentication authentication) {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		Buddy myself = buddyRepository.findByUsersUsername(username);
		Accounts myAccounts = accountsRepository.findByBuddyEmail(myself.getEmail());
		WithdrawalDeposit withdrawalDeposit = new WithdrawalDeposit();
		model.addAttribute("balance", myAccounts.getBalance());
		model.addAttribute("withdrawaldeposit", withdrawalDeposit);
		return "deposit";
	}
	
	@PostMapping("/withdrawal")
	public String withdrawal (@ModelAttribute("withdrawaldeposit") WithdrawalDeposit WDInfo, Model model, Authentication authentication) throws InsufficientFundsException, BankAccountDoesNotExist {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		Buddy myself = buddyRepository.findByUsersUsername(username);
		Accounts myAccounts = accountsRepository.findByBuddyEmail(myself.getEmail());
		model.addAttribute("balance", myAccounts.getBalance());
		Double amount = WDInfo.getAmount();
		WithdrawalDeposit withdrawalDeposit = new WithdrawalDeposit();
		withdrawalDeposit.setMyAccounts(myAccounts);
		withdrawalDeposit.setMyBankAccount(bankAccountRepository.findByAccountsBuddyEmail(myself.getEmail()));
		withdrawalDeposit.setTransactionDate(LocalDate.now());
		withdrawalDeposit.setAmount(amount);
		withdrawalDepositService.validation(myAccounts, withdrawalDeposit);
		withdrawalDepositService.balanceUpdate(myAccounts, withdrawalDeposit.getMyBankAccount() , withdrawalDeposit);
		withdrawalDepositService.saveWithdrawalDeposit(withdrawalDeposit);
		return "redirect:/myAccount";
	}
	
	@PostMapping("/deposit")
	public String deposit (@ModelAttribute("deposit") WithdrawalDeposit WDInfo, Model model, Authentication authentication) throws InsufficientFundsException, BankAccountDoesNotExist {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		Buddy myself = buddyRepository.findByUsersUsername(username);
		Accounts myAccounts = accountsRepository.findByBuddyEmail(myself.getEmail());
		model.addAttribute("balance", myAccounts.getBalance());
		Double amount = -(WDInfo.getAmount());
		WithdrawalDeposit withdrawalDeposit = new WithdrawalDeposit();
		withdrawalDeposit.setMyAccounts(myAccounts);
		withdrawalDeposit.setMyBankAccount(bankAccountRepository.findByAccountsBuddyEmail(myself.getEmail()));
		withdrawalDeposit.setTransactionDate(LocalDate.now());
		withdrawalDeposit.setAmount(amount);
		withdrawalDepositService.balanceUpdate(myAccounts, withdrawalDeposit.getMyBankAccount() , withdrawalDeposit);
		withdrawalDepositService.saveWithdrawalDeposit(withdrawalDeposit);
		return "redirect:/myAccount";
	}
}
