package com.openclassrooms.paymybuddy.accounts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.accounts.model.BankAccount;
import com.openclassrooms.paymybuddy.accounts.repository.BankAccountRepository;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class BankAccountService {
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	public BankAccount saveBankAccount(BankAccount bankAccount) {
		return bankAccountRepository.save(bankAccount);
	}

}
