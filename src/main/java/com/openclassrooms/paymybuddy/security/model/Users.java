package com.openclassrooms.paymybuddy.security.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "username")
	@NotNull(message = "Usernament cannot be empty")
	@NotEmpty
	private String username;
	
	@Column(name = "password")
	@NotNull(message = "Password cannot be null")
	@Min(value = 5, message = "Password must be at least 5 characters")
	@NotEmpty
	private String password;
	
	@Column
	@NotNull
	private boolean enabled;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "buddy_id", referencedColumnName = "id")
	private Buddy buddy;
}

