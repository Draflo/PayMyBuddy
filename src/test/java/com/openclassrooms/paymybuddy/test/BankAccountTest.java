package com.openclassrooms.paymybuddy.test;

import static org.assertj.core.api.Assertions.assertThat;
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

@WebMvcTest(BankAccountService.class)
class BankAccountTest {
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@MockBean
	private BankAccountRepository bankAccountRepository;
	
	@MockBean
	private UserDetailsService userDetailsService;

	@Test
	final void testSaveBuddy() {
		BankAccount baToSave = new BankAccount();
		Accounts accounts = new Accounts();
		baToSave.setAccounts(accounts);
		baToSave.setIBAN("TestIBAN");
		baToSave.setId(1);
		
		when(bankAccountRepository.save(baToSave)).thenReturn(baToSave);
		
		BankAccount baSaved = bankAccountService.saveBankAccount(baToSave);
		
		assertThat(baSaved.getIBAN()).isEqualTo("TestIBAN");
		assertThat(baSaved.getAccounts()).isEqualTo(accounts);
		assertThat(baSaved.getId()).isEqualTo(1);
		
	}

}
