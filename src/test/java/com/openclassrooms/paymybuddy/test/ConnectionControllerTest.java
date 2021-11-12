package com.openclassrooms.paymybuddy.test;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.accounts.service.AccountsService;
import com.openclassrooms.paymybuddy.accounts.service.BankAccountService;
import com.openclassrooms.paymybuddy.accounts.service.ConnectionService;
import com.openclassrooms.paymybuddy.security.model.Buddy;
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
		Buddy testBuddy = new Buddy();
		testBuddy.setEmail("testmail.com");
		Accounts testAccounts = new Accounts();
		Accounts friend1 = new Accounts();
		Accounts friend2 = new Accounts();
		Buddy buddy1 = new Buddy();
		Buddy buddy2 = new Buddy();
		Set<Accounts> friends = new TreeSet<Accounts>();
		friends.add(friend1);
		friends.add(friend2);
		testAccounts.setConnections(friends);
		when(buddyService.findByUsersUsername(Mockito.anyString())).thenReturn(testBuddy);
		when(accountsRepository.findByBuddyEmail("testmail.com")).thenReturn(testAccounts);
		when(buddyService.findByAccountsId(friend1.getId())).thenReturn(buddy1);
		when(buddyService.findByAccountsId(friend2.getId())).thenReturn(buddy2);
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
		Buddy testBuddy = new Buddy();
		testBuddy.setEmail("testmail.com");
		Accounts testAccounts = new Accounts();
		when(buddyService.findByUsersUsername(Mockito.anyString())).thenReturn(testBuddy);
		when(accountsRepository.findByBuddyEmail("testmail.com")).thenReturn(testAccounts);
		mockMvc.perform(post("/addConnection").with(csrf().asHeader()).param("email", "test@email.com")).andExpect(view().name("redirect:/friendList"));
	}

}
