package com.openclassrooms.paymybuddy.test;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.repository.BuddyRepository;
import com.openclassrooms.paymybuddy.security.service.BuddyService;
import com.openclassrooms.paymybuddy.security.service.UserService;
import com.openclassrooms.paymybuddy.transactions.controller.TransactionController;
import com.openclassrooms.paymybuddy.transactions.service.TransactionService;

@WebMvcTest(controllers = TransactionController.class)
class TransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	
	@MockBean
	private BuddyRepository buddyRepository;

	@MockBean
	private BuddyService buddyService;

	@MockBean
	private UserDetailsService userDetailsService;
	
	@MockBean
	private TransactionService transactionService;
	
	@MockBean
	private AccountsRepository accountsRepository;

	@Test
	@WithMockUser(roles = "user")
	final void testShowTransferForm() throws Exception {
		Buddy myself = new Buddy();
		myself.setEmail("myself@mail.com");
		Accounts senderAccounts = new Accounts();
		senderAccounts.setBalance(100);
		when(buddyRepository.findByUsersUsername("user")).thenReturn(myself);
		when(accountsRepository.findByBuddyEmail("myself@mail.com")).thenReturn(senderAccounts);
		mockMvc.perform(get("/transfer")).andExpect(status().isOk()).andExpect(view().name("transfer"));
	}

	@Test
	@WithMockUser(roles = "user")
	final void testTransfer() throws Exception {
		Buddy myself = new Buddy();
		myself.setEmail("myself@mail.com");
		Accounts senderAccounts = new Accounts();
		senderAccounts.setBalance(100);
		Accounts receiver = new Accounts();
		when(buddyRepository.findByUsersUsername("user")).thenReturn(myself);
		when(accountsRepository.findByBuddyEmail("myself@mail.com")).thenReturn(senderAccounts);
		when(accountsRepository.findByBuddyEmail("test@mail.com")).thenReturn(receiver);
		mockMvc.perform(post("/transfer").with(csrf().asHeader()).param("email", "test@mail.com").param("amount", "50").param("description", "TestTransfer"))
				.andExpect(view().name("redirect:/myAccount"));
	}

}
