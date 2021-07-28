package com.openclassrooms.paymybuddy.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.security.model.User;
import com.openclassrooms.paymybuddy.security.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}
	
	public User saveUser (User user) {
		return userRepository.save(user);
	}

}
