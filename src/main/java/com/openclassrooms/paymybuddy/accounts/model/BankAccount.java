package com.openclassrooms.paymybuddy.accounts.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.openclassrooms.paymybuddy.security.model.Buddy;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BankAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String IBAN;
	
	@OneToOne(targetEntity = Buddy.class)
	@JoinColumn(name = "owner")
	private Buddy owner;

}