package com.openclassrooms.paymybuddy.accounts.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.openclassrooms.paymybuddy.security.model.Users;

public class MyUserPrincipal implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Users users;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		return this.users.getPassword();
	}

	@Override
	public String getUsername() {
		return this.users.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.users.isEnabled();
	}

	public MyUserPrincipal(Users user) {
		this.setUser(user);
	}

	public Users getUser() {
		return users;
	}

	public void setUser(Users user) {
		this.users = user;
	}
}
