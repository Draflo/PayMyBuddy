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
	
	public Accounts saveAccount(final Buddy owner) {
		Accounts accounts = accountsRepository.findByOwnerEmail(owner.getEmail());
		if (accounts != null) {
			return null;
		}
		
		accounts = new Accounts();
		accounts.setAccountNumber(owner.getId() + accountNumber());
		accounts.setOwner(owner);
		accountsRepository.save(accounts);
		return accounts;
	}
	
	public Accounts findByOwnerEmail(String email) {
		Accounts accounts = accountsRepository.findByOwnerEmail(email);
		return accounts;
	}
}
