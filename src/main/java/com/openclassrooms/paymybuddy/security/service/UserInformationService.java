package com.openclassrooms.paymybuddy.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
	
	@Override
	public UserInformation loadUserByUsername(String username) throws UsernameNotFoundException {
		final Users user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		final Buddy buddy = buddyRepository.findByUsersUsername(username);
		UserInformation userInformation = new UserInformation(user, buddy, null, null);
		return userInformation;
	}

}
