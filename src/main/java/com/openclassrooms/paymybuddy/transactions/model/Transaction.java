package com.openclassrooms.paymybuddy.transactions.model;

import java.sql.Date;

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
@Setter
@Getter
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private Date transactionDate;
	
	@OneToOne
	private Accounts senderAccounts;
	
	@OneToOne
	private Accounts receiverAccounts;
	
	@OneToOne
	private BankAccount beneficiaryBankAccount;
	private String name;
	private String description;
	private double amount;
	private double fee;

}
