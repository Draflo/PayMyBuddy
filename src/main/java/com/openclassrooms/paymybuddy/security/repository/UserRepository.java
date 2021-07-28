package com.openclassrooms.paymybuddy.security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.security.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

}
