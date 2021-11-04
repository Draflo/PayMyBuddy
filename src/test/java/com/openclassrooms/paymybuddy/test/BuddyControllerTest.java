package com.openclassrooms.paymybuddy.test;

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

import com.openclassrooms.paymybuddy.security.controller.BuddyController;
import com.openclassrooms.paymybuddy.security.service.BuddyService;
import com.openclassrooms.paymybuddy.security.service.UserService;

@WebMvcTest(controllers = BuddyController.class)
class BuddyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BuddyService buddyService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private UserDetailsService userDetailsService;

	@Test
	@WithMockUser(roles = "user")
	final void testHomePage() throws Exception {
		mockMvc.perform(get("/home")).andExpect(status().isOk()).andExpect(view().name("home"));
	}

	@Test
	@WithMockUser(roles = "user")
	final void testShowCreateBuddyForm() throws Exception {
		mockMvc.perform(get("/createBuddy")).andExpect(status().isOk()).andExpect(view().name("createBuddy"));
	}

	@Test
	@WithMockUser(roles = "user")
	final void testCreateBuddy() throws Exception {
		mockMvc.perform(post("/createBuddy").with(csrf().asHeader()).param("firstName", "TestFirst")
				.param("lastName", "TestLast").param("email", "test@mail.com").param("birthdate", "12/12/2012"))
				.andExpect(view().name("redirect:/createAccount"));
	}

}
