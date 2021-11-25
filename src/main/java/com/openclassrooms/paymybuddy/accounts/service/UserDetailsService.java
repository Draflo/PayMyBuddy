package com.openclassrooms.paymybuddy.accounts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.accounts.model.MyUserPrincipal;
import com.openclassrooms.paymybuddy.security.model.Users;
import com.openclassrooms.paymybuddy.security.repository.UserRepository;

@Service("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new MyUserPrincipal(user);
	}
	
	

}
