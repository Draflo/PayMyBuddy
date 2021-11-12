package com.openclassrooms.paymybuddy.security.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.model.BankAccount;


public class UserInformation implements UserDetails { 
	
private static final long serialVersionUID = 1L;
private Users users;
private Buddy buddy;
private Accounts accounts;
private BankAccount bankAccount;
	
	public UserInformation (Users users, Buddy buddy, Accounts accounts, BankAccount bankAccount, Collection<? extends GrantedAuthority> collection) {
		this.users = users;
		this.buddy = buddy;
		this.accounts = accounts;
		this.bankAccount = bankAccount;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	
	public Users getUsers() {
		return users;
	}
	
	public void setUsers(Users users) {
		this.users = users;
	}
	
	@Override
	public String getPassword() {
		return this.users.getPassword();
	}
	
	@Override
	public String getUsername() {
		return this.users.getUsername();
	}
	
	public Long getId() {
		return this.users.getId();
	}
	
	public String getFirstName() {
		return this.buddy.getFirstName();
	}
	
	public String getlastName() {
		return this.buddy.getLastName();
	}
	
	public String getEmail() {
		return this.buddy.getEmail();
	}
	
	public String getBirthdate() {
		return this.buddy.getBirthdate();
	}
	
	public Buddy getBuddy() {
		return this.buddy;
	}
	public Double getBalance() {
		return this.accounts.getBalance();
	}
	
	public String getAccountNumber() {
		return this.accounts.getAccountNumber();
	}
	
	public String getIBAN() {
		return this.bankAccount.getIBAN();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.users.isEnabled();
	}

	

}
