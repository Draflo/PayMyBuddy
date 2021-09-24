package com.openclassrooms.paymybuddy.accounts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.security.model.Buddy;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class AccountsService {

	@Autowired
	private AccountsRepository accountsRepository;
	
	
	
	public Accounts saveAccount(Buddy buddy) {
		Accounts accounts = accountsRepository.findByBuddyEmail(buddy.getEmail());
		if (accounts == null) {
			accounts = new Accounts();
			return accounts;
		}
		accountsRepository.save(accounts);
		return accounts;
	}
	
	public Accounts findByBuddyEmail(String email) {
		Accounts accounts = accountsRepository.findByBuddyEmail(email);
		return accounts;
	}
	
//	public Accounts findByUsername(String username) {
//		Accounts accounts = accountsRepository.findByUsername(username);
//		return accounts;
//	}
}
