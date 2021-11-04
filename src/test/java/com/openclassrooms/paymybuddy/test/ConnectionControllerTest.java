package com.openclassrooms.paymybuddy.test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.accounts.service.AccountsService;
import com.openclassrooms.paymybuddy.accounts.service.BankAccountService;
import com.openclassrooms.paymybuddy.accounts.service.ConnectionService;
import com.openclassrooms.paymybuddy.security.service.BuddyService;
import com.openclassrooms.paymybuddy.security.service.UserService;
import com.openclassrooms.paymybuddy.transactions.service.TransactionService;

@WebMvcTest
class ConnectionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private BuddyService buddyService;
	
	@MockBean
	private AccountsService accountsService;
	
	@MockBean
	private AccountsRepository accountsRepository;
	
	@MockBean
	private BankAccountService bankAccountService;
	
	@MockBean
	private ConnectionService connectionService;
	
	@MockBean
	private TransactionService transactionService;

	@MockBean
	private UserDetailsService userDetailsService;
	
	@Autowired
	private WebApplicationContext context;
	
	@Before
	void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}
	
	@Test
	@WithMockUser(roles = "USER")
	final void testFriendList() throws Exception {
		mockMvc.perform(get("/friendList")).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(roles = "USER")
	final void testAddAFriend() throws Exception {
		mockMvc.perform(get("/addAFriend")).andExpect(status().isOk()).andExpect(view().name("addAFriend"));
	}
	
	@Test
	@WithMockUser(roles = "USER")
	final void testAddConnection() throws Exception {
		mockMvc.perform(post("/addConnection").with(csrf().asHeader()).param("email", "test@email.com")).andExpect(view().name("redirect:/friendList"));
	}

}
