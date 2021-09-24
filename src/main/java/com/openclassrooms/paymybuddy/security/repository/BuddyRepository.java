package com.openclassrooms.paymybuddy.security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.security.model.Buddy;

@Repository
public interface BuddyRepository extends CrudRepository<Buddy, Long>{

	 Buddy findByUsersUsername(String username);
	 
}
