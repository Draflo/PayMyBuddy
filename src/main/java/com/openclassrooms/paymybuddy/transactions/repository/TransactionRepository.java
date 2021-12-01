package com.openclassrooms.paymybuddy.transactions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.transactions.model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

	@Query("SELECT t FROM Transaction t where t.receiverAccounts.id = ?1 OR t.senderAccounts.id = ?1")
	List<Transaction> findByUsersUsername(Long id);
}
