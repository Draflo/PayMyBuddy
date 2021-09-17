package com.openclassrooms.paymybuddy.accounts.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.openclassrooms.paymybuddy.security.model.Buddy;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "accounts")
public class Accounts {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String accountNumber;
	private double balance;
	
	@OneToOne(mappedBy = "accounts")
	private Buddy buddy;
	
}
