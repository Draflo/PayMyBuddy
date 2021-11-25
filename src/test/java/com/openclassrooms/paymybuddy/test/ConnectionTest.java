package com.openclassrooms.paymybuddy.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.accounts.service.ConnectionService;
import com.openclassrooms.paymybuddy.exception.AccountsDoesNotExistException;

@WebMvcTest(ConnectionService.class)
class ConnectionTest {

	@Autowired
	private ConnectionService connectionService;
	
	@MockBean
	private AccountsRepository accountsRepository;
	
	@MockBean
	private UserDetailsService userDetailsService;

	@Test
	final void testAddConnection() throws AccountsDoesNotExistException {
		Accounts myAccounts = new Accounts();
		Accounts accountsToAdd = new Accounts();
		when(accountsRepository.findByBuddyEmail("AddMe@mail.com")).thenReturn(accountsToAdd);
		connectionService.addConnection("AddMe@mail.com", myAccounts);
		assertThat(myAccounts.getConnections()).contains(accountsToAdd);

	}

	@Test
	final void testTrytoAddANonExistingAccountGetAccountsDoesNotExistException() throws AccountsDoesNotExistException {
		Accounts accounts = new Accounts();
		assertThrows(AccountsDoesNotExistException.class, () -> connectionService.addConnection("IDon't@Exist", accounts));
	}

}
