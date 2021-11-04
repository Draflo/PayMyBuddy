package com.openclassrooms.paymybuddy.test;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.openclassrooms.paymybuddy.accounts.controller.AccountsController;
import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.service.AccountsService;
import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.model.UserInformation;
import com.openclassrooms.paymybuddy.security.model.Users;
import com.openclassrooms.paymybuddy.security.service.BuddyService;
import com.openclassrooms.paymybuddy.security.service.UserService;

@WebMvcTest(controllers = AccountsController.class)
class AccountsControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;

	@MockBean
	private BuddyService buddyService;

	@MockBean
	public UserInformation userInformation;
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	@Autowired
	private WebApplicationContext context;
	
	static {
		Users testUsers = new Users();
		Buddy testBuddy = new Buddy();
		Accounts testAccounts = new Accounts();
		testAccounts.setBalance(0);
		testAccounts.setAccountNumber("1012345");
		testAccounts.setId(1);
		testAccounts.setBuddy(testBuddy);
		testBuddy.setId(1);
		testBuddy.setAccounts(testAccounts);
		testBuddy.setUsers(testUsers);
		testUsers.setId(1);
		testUsers.setUsername("TestUser");
		testUsers.setBuddy(testBuddy);
		new SimpleGrantedAuthority("USER");		
	}
	
	@MockBean
	private AccountsService accountsService;
	
	@Before
	void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	@WithMockUser(roles = "user")
	final void testCreateAccount() throws Exception {
		mockMvc.perform(post("/createAccount").with(csrf().asHeader())).andExpect(view().name("createdAccount"));
	}
	
	@Test
	@WithUserDetails("TestUser")
	final void testMyAccount() throws Exception {
		Buddy testBuddy = new Buddy();
		Accounts testAccounts = new Accounts();
		testAccounts.setBalance(0);
		testAccounts.setAccountNumber("1012345");
		testAccounts.setBuddy(testBuddy);
		testBuddy.setAccounts(testAccounts);
		when(buddyService.findByUsersUsername(Mockito.anyString())).thenReturn(testBuddy);
		when(accountsService.findByBuddyEmail(Mockito.anyString())).thenReturn(testAccounts);
		mockMvc.perform(get("/myAccount")).andExpect(view().name("myAccount"));
	}

}
