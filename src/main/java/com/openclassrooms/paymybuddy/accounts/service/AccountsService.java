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
	
	public Accounts saveAccounts(final Buddy owner) {
		Accounts accounts = accountsRepository.findByOwnerEmail(owner.getEmail());
		if (accounts != null) {
			return null;
		}
		
		accounts = new Accounts();
		accounts.setAccountNumber(owner.getId() + "Later");
		accounts.setOwner(owner);
		accountsRepository.save(accounts);
		return accounts;
	}
}
