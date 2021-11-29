package com.openclassrooms.paymybuddy.test;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.paymybuddy.accounts.controller.BankAccountController;
import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.accounts.service.AccountsService;
import com.openclassrooms.paymybuddy.accounts.service.BankAccountService;
import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.service.BuddyService;
import com.openclassrooms.paymybuddy.security.service.UserService;

@WebMvcTest(controllers = BankAccountController.class)
class BankAccountControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BuddyService buddyService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private AccountsService accountsService;

	@MockBean
	private BankAccountService bankAccountService;
	
	@MockBean
	private AccountsRepository accountsRepository;
	
	@MockBean
	private UserDetailsService userDetailsService;

	@Test
	@WithMockUser(roles = "user")
	final void testShowBankAccountForm() throws Exception {
		mockMvc.perform(get("/addABankAccount")).andExpect(status().isOk()).andExpect(view().name("addABankAccount"));
	}

	@Test
	@WithMockUser(roles = "user")
	final void testSaveABankAccount() throws Exception {
		Buddy testBuddy = new Buddy();
		testBuddy.setEmail("testmail.com");
		Accounts testAccounts = new Accounts();
		when(buddyService.findByUsersUsername(Mockito.anyString())).thenReturn(testBuddy);
		when(accountsService.findByBuddyEmail("testmail.com")).thenReturn(testAccounts);
		mockMvc.perform(post("/addABankAccount").with(csrf().asHeader()).param("IBAN", "TestIBAN"))
				.andExpect(view().name("redirect:/bankAccount"));
	}

}
