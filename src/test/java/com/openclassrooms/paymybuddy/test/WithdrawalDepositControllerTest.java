package com.openclassrooms.paymybuddy.test;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.model.BankAccount;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.accounts.repository.BankAccountRepository;
import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.repository.BuddyRepository;
import com.openclassrooms.paymybuddy.security.service.BuddyService;
import com.openclassrooms.paymybuddy.security.service.UserService;
import com.openclassrooms.paymybuddy.transactions.controller.WithdrawalDepositController;
import com.openclassrooms.paymybuddy.transactions.model.WithdrawalDeposit;
import com.openclassrooms.paymybuddy.transactions.service.WithdrawalDepositService;

@WebMvcTest(controllers = WithdrawalDepositController.class)
class WithdrawalDepositControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	
	@MockBean
	private BuddyRepository buddyRepository;
	
	@MockBean
	private BankAccountRepository bankAccountRepository;

	@MockBean
	private BuddyService buddyService;

	@MockBean
	private UserDetailsService userDetailsService;
	
	@MockBean
	private WithdrawalDepositService withdrawalDepositService;
	
	@MockBean
	private AccountsRepository accountsRepository;

	@Test
	@WithMockUser(roles = "user")
	final void testMyTransactions() throws Exception {
		Accounts myAccounts = new Accounts();
		BankAccount myBankAccount = new BankAccount();
		LocalDate localDate = LocalDate.now();
		WithdrawalDeposit transaction = new WithdrawalDeposit();
		transaction.setAmount(20);
		transaction.setId(1);
		transaction.setTransactionDate(localDate);
		transaction.setMyBankAccount(myBankAccount);
		transaction.setMyAccounts(myAccounts);
		List<WithdrawalDeposit> myTransactions = new ArrayList<>();
		myTransactions.add(transaction);
		when(withdrawalDepositService.findByUsersUsername("user")).thenReturn(myTransactions);
		mockMvc.perform(get("/myWD")).andExpect(status().isOk()).andExpect(view().name("myWD"));
	}

	@Test
	@WithMockUser(roles = "user")
	final void testShowWithdrawForm() throws Exception {
		Buddy myself = new Buddy();
		myself.setEmail("myself@mail.com");
		Accounts myAccounts = new Accounts();
		myAccounts.setBalance(100);
		when(buddyRepository.findByUsersUsername("user")).thenReturn(myself);
		when(accountsRepository.findByBuddyEmail("myself@mail.com")).thenReturn(myAccounts);
		mockMvc.perform(get("/withdrawal").with(csrf().asHeader())).andExpect(status().isOk()).andExpect(view().name("withdrawal"));
	}
	
	@Test
	@WithMockUser(roles = "user")
	final void testShowDepositForm() throws Exception {
		Buddy myself = new Buddy();
		myself.setEmail("myself@mail.com");
		Accounts myAccounts = new Accounts();
		myAccounts.setBalance(100);
		when(buddyRepository.findByUsersUsername("user")).thenReturn(myself);
		when(accountsRepository.findByBuddyEmail("myself@mail.com")).thenReturn(myAccounts);
		mockMvc.perform(get("/deposit")).andExpect(status().isOk()).andExpect(view().name("deposit"));
	}

	@Test
	@WithMockUser(roles = "user")
	final void testWithdrawal() throws Exception {
		Buddy myself = new Buddy();
		myself.setEmail("myself@mail.com");
		Accounts myAccounts = new Accounts();
		myAccounts.setBalance(100);
		BankAccount myBankAccount = new BankAccount();
		when(buddyRepository.findByUsersUsername("user")).thenReturn(myself);
		when(accountsRepository.findByBuddyEmail("myself@mail.com")).thenReturn(myAccounts);
		when(bankAccountRepository.findByAccountsBuddyEmail("myself@mail.com")).thenReturn(myBankAccount);
		mockMvc.perform(post("/withdrawal").with(csrf().asHeader()).param("amount", "50").param("description", "TestTransfer"))
				.andExpect(view().name("redirect:/myAccount"));
	}
	
	@Test
	@WithMockUser(roles = "user")
	final void testDeposit() throws Exception {
			Buddy myself = new Buddy();
			myself.setEmail("myself@mail.com");
			Accounts myAccounts = new Accounts();
			myAccounts.setBalance(100);
			BankAccount myBankAccount = new BankAccount();
			when(buddyRepository.findByUsersUsername("user")).thenReturn(myself);
			when(accountsRepository.findByBuddyEmail("myself@mail.com")).thenReturn(myAccounts);
			when(bankAccountRepository.findByAccountsBuddyEmail("myself@mail.com")).thenReturn(myBankAccount);
			mockMvc.perform(post("/deposit").with(csrf().asHeader()).param("amount", "50").param("description", "TestTransfer"))
					.andExpect(view().name("redirect:/myAccount"));
	}

}
