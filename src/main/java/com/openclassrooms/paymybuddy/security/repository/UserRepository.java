package com.openclassrooms.paymybuddy.security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.security.model.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, Long>{

}
