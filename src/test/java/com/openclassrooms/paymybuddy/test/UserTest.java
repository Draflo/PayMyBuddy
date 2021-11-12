package com.openclassrooms.paymybuddy.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.openclassrooms.paymybuddy.security.model.Users;
import com.openclassrooms.paymybuddy.security.repository.UserRepository;
import com.openclassrooms.paymybuddy.security.service.UserService;

@WebMvcTest(UserService.class)
class UserTest {

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@MockBean
	private UserDetailsService userDetailsService;

	public static List<Users> userList = new ArrayList<>();

	static {
		Users user1 = new Users();
		user1.setUsername("Test1");
		user1.setPassword("password");
		Users user2 = new Users();
		user2.setUsername("Test2");
		user2.setPassword("password");
		userList.add(user1);
		userList.add(user2);
	}

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	final void testGetAllUsers() throws Exception {
		when(userRepository.findAll()).thenReturn(userList);

		Iterable<Users> users = userService.findAll();

		assertThat(users.toString().contains("Test1"));
		assertThat(users.toString().contains("Test2"));

	}

	@Test
	final void testFindUserByUsername() throws Exception {
		Users user1 = new Users();
		user1.setUsername("Test1");
		user1.setPassword("password");

		when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user1);

		Users foundUsers = userService.findByUsername(Mockito.anyString());

		assertThat(foundUsers.getUsername()).isEqualTo("Test1");
	}
	
	@Test
	final void testSaveUser() {
		Users userToSave = new Users();
		userToSave.setUsername("Saved");
		
		when(userRepository.save(userToSave)).thenReturn(userToSave);
		
		Users userSaved = userService.saveUser(userToSave);
		
		assertThat(userSaved.getUsername()).isEqualTo("Saved");
	}

}
