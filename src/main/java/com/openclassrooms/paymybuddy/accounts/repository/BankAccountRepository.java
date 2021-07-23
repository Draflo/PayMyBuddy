package com.openclassrooms.paymybuddy.accounts.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.accounts.model.BankAccount;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {


}
