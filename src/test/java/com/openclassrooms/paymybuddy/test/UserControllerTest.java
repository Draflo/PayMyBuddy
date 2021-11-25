package com.openclassrooms.paymybuddy.test;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.openclassrooms.paymybuddy.security.controller.UserController;
import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.model.Users;
import com.openclassrooms.paymybuddy.security.service.BuddyService;
import com.openclassrooms.paymybuddy.security.service.UserService;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private BuddyService buddyService;

	@MockBean
	private UserDetailsService userDetailsService;
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Before
	void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void testGetAllUsers() throws Exception {
		Users user1 = new Users();
		user1.setUsername("Test1");
		user1.setPassword("password");
		Buddy buddy1 = new Buddy();
		buddy1.setUsers(user1);
		buddy1.setFirstName("Buddy1");
		buddy1.setLastName("Last1");
		Users user2 = new Users();
		user2.setUsername("Test2");
		user2.setPassword("password");
		Buddy buddy2 = new Buddy();
		buddy2.setUsers(user2);
		buddy2.setFirstName("Buddy2");
		buddy2.setLastName("Last2");
		List<Users> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);
		when(userService.findAll()).thenReturn(users);
		when(buddyService.findByUsersUsername("Test1")).thenReturn(buddy1);
		when(buddyService.findByUsersUsername("Test2")).thenReturn(buddy2);
		mockMvc.perform(get("/users")).andExpect(status().isOk());
	}

	@Test
	void testWelcome() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("welcomePage"));
	}

	@Test
	void testLogin() throws Exception {
		mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(view().name("login"));
	}

	@Test
	void testShowCreateUserForm() throws Exception {
		mockMvc.perform(get("/createUser")).andExpect(status().isOk()).andExpect(view().name("createUser"));
	}

	@Test
	void testCreateUser() throws Exception {
		mockMvc.perform(post("/createUser").with(csrf().asHeader()).param("username", "TestCreate").param("password", passwordEncoder.encode("password")).param("enabled", "true")).andExpect(view().name("redirect:/login"));
	}
}
