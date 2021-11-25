//package com.openclassrooms.paymybuddy.security.service;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.openclassrooms.paymybuddy.accounts.model.Accounts;
//import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
//import com.openclassrooms.paymybuddy.security.model.Buddy;
//import com.openclassrooms.paymybuddy.security.model.Privilege;
//import com.openclassrooms.paymybuddy.security.model.Role;
//import com.openclassrooms.paymybuddy.security.model.UserInformation;
//import com.openclassrooms.paymybuddy.security.model.Users;
//import com.openclassrooms.paymybuddy.security.repository.BuddyRepository;
//import com.openclassrooms.paymybuddy.security.repository.UserRepository;
//
//@Service("userDetailsService")
//public class UserInformationService implements UserDetailsService {
//	
//	@Autowired
//	private UserRepository userRepository;
//	
//	@Autowired
//	private BuddyRepository buddyRepository;
//	
//	@Autowired
//	private AccountsRepository accountsRepository;
//	
//	@Override
//	public UserInformation loadUserByUsername(String username) throws UsernameNotFoundException {
//		final Users user = userRepository.findByUsername(username);
//		if (user == null) {
//			throw new UsernameNotFoundException(username);
//		}
//		final Buddy buddy = buddyRepository.findByUsersUsername(username);
//		Accounts accounts = new Accounts();
//		if (buddy != null) {
//			accounts = accountsRepository.findByBuddyEmail(buddy.getEmail());
//		}
//		UserInformation userInformation = new UserInformation(user, buddy, accounts, null, getAuthorities(user.getRoles()));
//		return userInformation;
//	}
//	
//	private Collection<? extends GrantedAuthority> getAuthorities(
//		      Collection<Role> roles) {
//		 
//		        return getGrantedAuthorities(getPrivileges(roles));
//		    }
//
//		    private List<String> getPrivileges(Collection<Role> roles) {
//		 
//		        List<String> privileges = new ArrayList<>();
//		        List<Privilege> collection = new ArrayList<>();
//		        for (Role role : roles) {
//		            privileges.add(role.getName());
//		            collection.addAll(role.getPrivileges());
//		        }
//		        for (Privilege item : collection) {
//		            privileges.add(item.getName());
//		        }
//		        return privileges;
//		    }
//
//		    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
//		        List<GrantedAuthority> authorities = new ArrayList<>();
//		        for (String privilege : privileges) {
//		            authorities.add(new SimpleGrantedAuthority(privilege));
//		        }
//		        return authorities;
//		    }
//}
