package com.openclassrooms.paymybuddy.security.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	
	@NotNull(message = "First Name cannot be empty")
	@NotEmpty
	private String firstName;
	
	@NotNull(message = "Last Name cannot be empty")
	@NotEmpty
	private String lastName;
	
	@NotNull(message = "Email cannot be empty")
	@NotEmpty
	private String email;
	
	@NotNull(message = "Birthdate cannot be empty")
	@NotEmpty
	private String birthdate;
	
	@OneToOne
	@JoinColumn(name = "users_id", referencedColumnName = "id")
	private Users users;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "buddy")
	private Accounts accounts;

	
}
