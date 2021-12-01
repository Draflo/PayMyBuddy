package com.openclassrooms.paymybuddy.transactions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.transactions.model.WithdrawalDeposit;

@Repository
public interface WithdrawalDepositRepository extends CrudRepository<WithdrawalDeposit
, Long> {
	
	@Query("SELECT wd FROM WithdrawalDeposit wd where wd.myAccounts.id = ?1 ")
	List<WithdrawalDeposit> findByUsersUsername(Long id);

}
