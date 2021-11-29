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
import com.openclassrooms.paymybuddy.accounts.model.BankAccount;
import com.openclassrooms.paymybuddy.accounts.repository.BankAccountRepository;
import com.openclassrooms.paymybuddy.accounts.service.BankAccountService;
import com.openclassrooms.paymybuddy.exception.AccountsAlreadyExistException;
import com.openclassrooms.paymybuddy.exception.AccountsDoesNotExistException;
import com.openclassrooms.paymybuddy.security.model.Buddy;

@WebMvcTest(BankAccountService.class)
class BankAccountTest {
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@MockBean
	private BankAccountRepository bankAccountRepository;
	
	@MockBean
	private UserDetailsService userDetailsService;

	@Test
	final void testSaveBankAccount() throws AccountsAlreadyExistException {
		BankAccount baToSave = new BankAccount();
		Buddy buddy = new Buddy();
		Accounts accounts = new Accounts();
		buddy.setAccounts(accounts);
		baToSave.setAccounts(accounts);
		baToSave.setIBAN("TestIBAN123456");
		baToSave.setId(0);
		
		when(bankAccountRepository.save(baToSave)).thenReturn(baToSave);
		
		BankAccount baSaved = bankAccountService.createBankAccount(buddy, baToSave);
		
		assertThat(baSaved.getIBAN()).isEqualTo("TestIBAN123456");
		assertThat(baSaved.getAccounts()).isEqualTo(accounts);
		assertThat(baSaved.getId()).isEqualTo(0);
		
	}
	
	@Test
	final void testTrytoSaveAnAlreadyExistingBankAccountGetAccountsAlreadyExistException() throws AccountsDoesNotExistException {
		BankAccount myBankAccount = new BankAccount();
		Buddy buddy = new Buddy();
		buddy.setEmail("email@test");
		myBankAccount.setIBAN("IBAN1234567890");
		when(bankAccountRepository.findByAccountsBuddyEmail("email@test")).thenReturn(myBankAccount);
		assertThrows(AccountsAlreadyExistException.class, () -> bankAccountService.createBankAccount(buddy, myBankAccount));
	}
	
	@Test
	final void testFindBankAccountByBuddyEmail() throws AccountsDoesNotExistException {
		Buddy buddy1 = new Buddy();
		buddy1.setEmail("TestMail");
		BankAccount myBankAccount = new BankAccount();

		when(bankAccountRepository.findByAccountsBuddyEmail("TestMail")).thenReturn(myBankAccount);

		BankAccount foundAccounts = bankAccountService.findByBuddyEmail("TestMail");

		assertThat(foundAccounts).isEqualTo(myBankAccount);
	}

}
