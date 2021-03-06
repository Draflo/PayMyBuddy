package com.openclassrooms.paymybuddy.transactions.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.exception.ConnectionDoesNotExistException;
import com.openclassrooms.paymybuddy.exception.InsufficientFundsException;
import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.service.BuddyService;
import com.openclassrooms.paymybuddy.transactions.model.Transaction;
import com.openclassrooms.paymybuddy.transactions.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private BuddyService buddyService;

	@Transactional
	public Transaction saveTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	@Transactional
	public Transaction validation(Accounts myAccounts, Transaction transactionInfo) throws InsufficientFundsException, ConnectionDoesNotExistException {
		if (myAccounts.getBalance() < (transactionInfo.getAmount())) {
			System.err.println("You don't have enough funds for this transfer");
			throw new InsufficientFundsException("InsufficientFunds", "You don't have enough funds for this transfer");
		}
		Set<Accounts> connections = transactionInfo.getSenderAccounts().getConnections();
		if (!connections.contains(transactionInfo.getReceiverAccounts())) {
			System.err.println(
					"This buddy is not one of your friend. Add him to your friend so you can send him money ! ");
			throw new ConnectionDoesNotExistException();
		}
		return null;
	}

	@Transactional
	public Transaction balanceUpdate(Accounts myAccounts, Accounts beneficiary, Transaction transactionInfo) {
		myAccounts.setBalance(myAccounts.getBalance() - transactionInfo.getAmount());
		beneficiary.setBalance(beneficiary.getBalance() + (transactionInfo.getAmount() -transactionInfo.getFee() ));
		accountsRepository.save(myAccounts);
		accountsRepository.save(beneficiary);
		return null;
	}

	public List<Transaction> findByUsersUsername(String username) {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		username = loggedInUser.getName();
		Buddy findByUsersUsername = buddyService.findByUsersUsername(username);
		List<Transaction> myTransactions = transactionRepository.findByUsersUsername(findByUsersUsername.getAccounts().getId());
		return myTransactions;
	}

}
