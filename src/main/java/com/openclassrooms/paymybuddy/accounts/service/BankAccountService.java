package com.openclassrooms.paymybuddy.accounts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.accounts.model.BankAccount;
import com.openclassrooms.paymybuddy.accounts.repository.BankAccountRepository;
import com.openclassrooms.paymybuddy.exception.AccountsAlreadyExistException;
import com.openclassrooms.paymybuddy.security.model.Buddy;

@Service
public class BankAccountService {
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	public BankAccount createBankAccount(Buddy buddy, BankAccount BA) throws AccountsAlreadyExistException {
		BankAccount bankAccount = bankAccountRepository.findByAccountsBuddyEmail(buddy.getEmail());
		if (bankAccount == null) {
			bankAccount = new BankAccount();
			bankAccount.setAccounts(buddy.getAccounts());
			bankAccount.setIBAN(BA.getIBAN());
			bankAccountRepository.save(bankAccount);
			return bankAccount;
		} else {
			throw new AccountsAlreadyExistException();
		}
	}
	
	public BankAccount findByBuddyEmail(String email) {
		BankAccount bankAccount = bankAccountRepository.findByAccountsBuddyEmail(email);
		return bankAccount;
	}

}
