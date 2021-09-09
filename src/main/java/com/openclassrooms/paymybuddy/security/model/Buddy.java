package com.openclassrooms.paymybuddy.security.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Table(name = "owner")
public class Buddy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String birthdate;
	
	@OneToOne(targetEntity = Users.class)
	@JoinColumn(name = "users")
	private Users users;
	
}
