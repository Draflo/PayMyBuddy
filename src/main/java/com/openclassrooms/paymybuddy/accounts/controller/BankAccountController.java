package com.openclassrooms.paymybuddy.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.paymybuddy.accounts.model.BankAccount;
import com.openclassrooms.paymybuddy.accounts.service.BankAccountService;

@RestController
public class BankAccountController {
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@PostMapping("/addBankAccount")
	public ResponseEntity<BankAccount> addBankAccount(@RequestBody BankAccount bankAccount) {
		BankAccount saveBankAccount = bankAccountService.saveBankAccount(bankAccount);
		return ResponseEntity.created(null).body(saveBankAccount);
	}
		
			

}
