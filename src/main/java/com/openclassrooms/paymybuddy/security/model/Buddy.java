package com.openclassrooms.paymybuddy.security.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the User class of PMB
 * 
 * 
 * @author Draflosword
 *
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "buddy")
public class Buddy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String birthdate;
	
	@OneToOne(mappedBy = "buddy")
	@JoinColumn(name = "users_id", referencedColumnName = "id")
	private Users users;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Accounts accounts;
	
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "bankaccount_id", referencedColumnName = "id")
//	private BankAccount bankAccount;
	
}
