package com.openclassrooms.paymybuddy.accounts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.security.model.UserInformation;

@Service
public class ConnectionService {

	@Autowired
	private AccountsRepository accountsRepository;
	
	private Authentication authentication;
	
	public Accounts addConnection(final String email) throws Exception {
		Accounts accountsToAdd = accountsRepository.findByBuddyEmail(email);
		
		if (accountsToAdd != null) {
			UserInformation userInformation = (UserInformation)authentication.getPrincipal();
			Accounts myAccounts = accountsRepository.findByBuddyEmail(userInformation.getEmail());
			
			if (myAccounts != null) {
				myAccounts.addConnection(accountsToAdd);
				accountsRepository.save(myAccounts);
				return myAccounts;
			} 
			else {
				throw new Exception();
			}		
		} 
		else {
			return null;
		}
		
	}
}
