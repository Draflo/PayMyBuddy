package com.openclassrooms.paymybuddy.transactions.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.transactions.model.Transaction;
import com.openclassrooms.paymybuddy.transactions.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Transactional
	public Transaction saveTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}
	
	public Transaction validation(Accounts myAccounts, Transaction transactionInfo) {
		if (myAccounts.getBalance() < (transactionInfo.getAmount() + transactionInfo.getFee())) {
			System.err.println( "You don't have enough funds for this transfer");
			return null;
		}
		Set<Accounts> connections = transactionInfo.getSenderAccounts().getConnections();
		if (!connections.contains(transactionInfo.getReceiverAccounts())) {
			System.err.println("This buddy is not one of your friend. Add him to your friend "
					+ "so you can send him money ! ");
			return null;
		}
		Transaction validated = transactionRepository.save(validation(myAccounts, transactionInfo));
		return validated;
	}
	
	public List<Transaction> findByUsersUsername(String username) {
		List<Transaction> myTransactions = transactionRepository.findByUsersUsername(username);
		return myTransactions;
	}
	
}
