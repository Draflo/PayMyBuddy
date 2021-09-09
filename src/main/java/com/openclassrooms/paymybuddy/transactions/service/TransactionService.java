package com.openclassrooms.paymybuddy.transactions.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.paymybuddy.transactions.model.Transaction;
import com.openclassrooms.paymybuddy.transactions.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Transactional
	public String saveTransaction(Transaction transaction) {
		return null;
	}
	
	public List<Transaction> findByOwner(String owner) {
		List<Transaction> myTransactions = transactionRepository.findByOwner(owner);
		return myTransactions;
	}
	
}
