package com.openclassrooms.paymybuddy.accounts.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.exception.AccountsAlreadyExistException;
import com.openclassrooms.paymybuddy.exception.AccountsDoesNotExistException;
import com.openclassrooms.paymybuddy.security.model.Buddy;

@Service
public class AccountsService {

	@Autowired
	private AccountsRepository accountsRepository;

	public static String accountNumber() {
		Random random = new Random();
		int number = random.nextInt(999999);
		return String.format("%06d", number);
	}

	public Accounts saveAccount(Buddy buddy) throws AccountsDoesNotExistException {
		Accounts accounts = accountsRepository.findByBuddyEmail(buddy.getEmail());
		if (accounts == null) {
			throw new AccountsDoesNotExistException();
		}
		else {
		accountsRepository.save(accounts);
		return accounts;
		}
	}

	public Accounts createAccount(Buddy buddy) throws AccountsAlreadyExistException {
		Accounts accounts = accountsRepository.findByBuddyEmail(buddy.getEmail());
		if (accounts == null) {
			accounts = new Accounts();
			accounts.setBalance(0);
			accounts.setBuddy(buddy);
			accounts.setAccountNumber(buddy.getId() + accountNumber());
			accountsRepository.save(accounts);
			return accounts;
		} else {
			throw new AccountsAlreadyExistException();
		}
	}

	public Accounts findByBuddyEmail(String email) {
		Accounts accounts = accountsRepository.findByBuddyEmail(email);
		return accounts;
	}

}
