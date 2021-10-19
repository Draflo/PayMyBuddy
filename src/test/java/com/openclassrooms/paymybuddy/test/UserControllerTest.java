package com.openclassrooms.paymybuddy.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.paymybuddy.security.controller.UserController;
import com.openclassrooms.paymybuddy.security.model.Users;
import com.openclassrooms.paymybuddy.security.service.UserService;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	public static List<Users> users = new ArrayList<>();
	
	static {
		Users user1 = new Users();
		user1.setUsername("Test1");
		user1.setPassword("password");
		Users user2 = new Users();
		user2.setUsername("Test2");
		user2.setPassword("password");
		users.add(user1);
		users.add(user2);
		
	}

	@Test
	final void testGetAllUsers() throws Exception {
		when(userService.findAll()).thenReturn(users);
		System.out.println(users.toString());
		mockMvc.perform(get("/users")).andExpect(status().isOk());
	}

}
