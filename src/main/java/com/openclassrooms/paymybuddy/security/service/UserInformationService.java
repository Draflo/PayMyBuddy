package com.openclassrooms.paymybuddy.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.model.UserInformation;
import com.openclassrooms.paymybuddy.security.model.Users;
import com.openclassrooms.paymybuddy.security.repository.BuddyRepository;
import com.openclassrooms.paymybuddy.security.repository.UserRepository;

@Service
public class UserInformationService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BuddyRepository buddyRepository;
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@Override
	public UserInformation loadUserByUsername(String username) throws UsernameNotFoundException {
		final Users user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		final Buddy buddy = buddyRepository.findByUsersUsername(username);
		final Accounts accounts = accountsRepository.findByBuddyEmail(buddy.getEmail());
		UserInformation userInformation = new UserInformation(user, buddy, accounts, null);
		return userInformation;
	}

}
