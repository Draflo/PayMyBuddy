package com.openclassrooms.paymybuddy.accounts.model;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
	
	@OneToOne
	@JoinColumn(name = "buddy_id", referencedColumnName = "id")
	private Buddy buddy;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "accounts")
	private BankAccount bankAccount;
	
	@ManyToMany
	private Set<Accounts> connections = new TreeSet<>();
	
	public void addConnection(final Accounts accounts) {
		this.connections.add(accounts);
	}
	
}
