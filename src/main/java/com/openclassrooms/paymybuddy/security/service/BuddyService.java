package com.openclassrooms.paymybuddy.security.service;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.repository.BuddyRepository;

@Service
public class BuddyService {
	
	@Autowired
	private BuddyRepository buddyRepository;
	
	public Buddy saveBuddy(Buddy buddy) throws SQLIntegrityConstraintViolationException {
		return buddyRepository.save(buddy);
	}

	public Iterable<Buddy> findAll() {
		return buddyRepository.findAll();
	}
	
	public Buddy findByUsersUsername(String username) {
		return buddyRepository.findByUsersUsername(username);
	}
	
	public Buddy findByAccountsId(Long id) {
		return buddyRepository.findByAccountsId(id);
	}

}
