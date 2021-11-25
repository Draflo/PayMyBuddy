package com.openclassrooms.paymybuddy.accounts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;

@Service
public class ConnectionService {

	@Autowired
	private AccountsRepository accountsRepository;

	public Accounts addConnection(final String email, Accounts myAccounts) throws Exception {
		Accounts accountsToAdd = accountsRepository.findByBuddyEmail(email);

		if (accountsToAdd != null) {
			myAccounts.addConnection(accountsToAdd);
			accountsRepository.save(myAccounts);
			return myAccounts;
		} else {
			throw new Exception();
		}

	}
}
