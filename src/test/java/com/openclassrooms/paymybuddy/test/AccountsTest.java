package com.openclassrooms.paymybuddy.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.model.BankAccount;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.accounts.service.AccountsService;
import com.openclassrooms.paymybuddy.exception.AccountsAlreadyExistException;
import com.openclassrooms.paymybuddy.exception.AccountsDoesNotExistException;
import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.repository.BuddyRepository;

@WebMvcTest(AccountsService.class)
class AccountsTest {
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	@MockBean
	private BuddyRepository buddyRepository;
	
	@MockBean
	private AccountsRepository accountsRepository;
	
	@Autowired
	private AccountsService accountsService;

	@Test
	final void testAccountNumber() throws Exception {
		
	}
	
	@Test
	final void testFindAccountByBuddyEmail() throws AccountsDoesNotExistException {
		Buddy buddy1 = new Buddy();
		buddy1.setEmail("TestMail");
		Accounts accounts = new Accounts();

		when(accountsRepository.findByBuddyEmail("TestMail")).thenReturn(accounts);

		Accounts foundAccounts = accountsService.findByBuddyEmail("TestMail");

		assertThat(foundAccounts).isEqualTo(accounts);
	}
	
	@Test
	final void testSaveAccount() throws AccountsDoesNotExistException {
		Buddy buddy = new Buddy();
		buddy.setId(1);
		buddy.setEmail("Testmail");
		Accounts accountsToSave = new Accounts();
		Set<Accounts> connections = new TreeSet<>();
		Accounts friend = new Accounts();
		BankAccount bankAccount = new BankAccount();
		connections.add(friend);
		accountsToSave.setBalance(0);
		accountsToSave.setId(1);
		accountsToSave.setBuddy(buddy);
		accountsToSave.setConnections(connections);
		accountsToSave.setAccountNumber("123456");
		accountsToSave.setBankAccount(bankAccount);
		
		when(accountsRepository.save(accountsToSave)).thenReturn(accountsToSave);
		when(accountsRepository.findByBuddyEmail("Testmail")).thenReturn(accountsToSave);
		
		Accounts accountsSaved = accountsService.saveAccount(buddy);
		
		assertThat(accountsSaved.getAccountNumber()).isEqualTo("123456");
		assertThat(accountsSaved.getBalance()).isEqualTo(0);
		assertThat(accountsSaved.getBuddy()).isEqualTo(buddy);
		assertThat(accountsSaved.getId()).isEqualTo(1);
		assertThat(accountsSaved.getConnections()).contains(friend);
		assertThat(accountsSaved.getBankAccount()).isEqualTo(bankAccount);
		
	}
	
	@Test
	final void testCreateAccount() throws AccountsAlreadyExistException {
		Buddy buddy = new Buddy();
		buddy.setId(1);
		buddy.setEmail("Testmail");
		
		
		when(accountsRepository.findByBuddyEmail("Testmail")).thenReturn(null);
		
		Accounts createdAccounts = accountsService.createAccount(buddy);
		
		assertThat(createdAccounts.getAccountNumber()).hasSize(7);
		assertThat(createdAccounts.getBalance()).isEqualTo(0);
		assertThat(createdAccounts.getBuddy()).isEqualTo(buddy);
		assertThat(createdAccounts.getId()).isEqualTo(0);
		
	}
	
	@Test
	final void testTrytoSaveANonExistingAccountGetAccountsDoesNotExistException() throws AccountsDoesNotExistException {
		Buddy buddy = new Buddy();
		assertThrows(AccountsDoesNotExistException.class, () -> accountsService.saveAccount(buddy));
	}
	
	@Test
	final void testTrytoCreateAnAlreadyExistingAccountGetAccountsAlreadyExistException() throws AccountsAlreadyExistException {
		Buddy buddy = new Buddy();
		buddy.setEmail("TestMail");
		Accounts accounts = new Accounts();
		accounts.setBuddy(buddy);
		when(accountsRepository.findByBuddyEmail("TestMail")).thenReturn(accounts);
		assertThrows(AccountsAlreadyExistException.class, () -> accountsService.createAccount(buddy));
	}

}
