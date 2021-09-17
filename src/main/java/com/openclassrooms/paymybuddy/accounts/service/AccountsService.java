package com.openclassrooms.paymybuddy.accounts.service;

import java.util.Random;

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
	
	public static String accountNumber() {
		Random random = new Random();
		int number = random.nextInt(999999);
		return String.format("%06d", number);
	}
	
	public Accounts saveAccount(Buddy buddy) {
		Accounts accounts = accountsRepository.findByBuddyEmail(buddy.getEmail());
		if (accounts != null) {
			return null;
		}
		
		accounts = new Accounts();
		accounts.setAccountNumber(buddy.getId() + accountNumber());
		accounts.setBuddy(buddy);
		accountsRepository.save(accounts);
		return accounts;
	}
	
	public Accounts findByOwnerEmail(String email) {
		Accounts accounts = accountsRepository.findByBuddyEmail(email);
		return accounts;
	}
	
//	public Accounts findByUsername(String username) {
//		Accounts accounts = accountsRepository.findByUsername(username);
//		return accounts;
//	}
}
