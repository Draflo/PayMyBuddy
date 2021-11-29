package com.openclassrooms.paymybuddy.transactions.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.model.BankAccount;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WithdrawalDeposit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private LocalDate transactionDate;
	private double amount;
	
	@OneToOne
	private BankAccount myBankAccount;
	
	@OneToOne
	private Accounts myAccounts;
}
