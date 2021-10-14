package com.openclassrooms.paymybuddy.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.security.model.Users;
import com.openclassrooms.paymybuddy.security.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public Iterable<Users> findAll() {
		return userRepository.findAll();
	}
	
	public Users saveUser (Users user) {
		return userRepository.save(user);
	}
	
	public Users findByUsername(String username) {
		Users user = userRepository.findByUsername(username);
		return user;
	}

}
