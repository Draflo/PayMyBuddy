package com.openclassrooms.paymybuddy.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.openclassrooms.paymybuddy.security.model.Users;
import com.openclassrooms.paymybuddy.security.repository.UserRepository;
import com.openclassrooms.paymybuddy.security.service.UserService;

@DataJpaTest
@RunWith(SpringRunner.class)
@Import(UserService.class)
class UserTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Autowired
	private UserService userService;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	final void testGetAllUsers() throws Exception {
		Users user1 = new Users();
		user1.setUsername("Test1");
		user1.setPassword("password");
		Users user2 = new Users();
		user2.setUsername("Test2");
		user2.setPassword("password");
		testEntityManager.persist(user1);
		testEntityManager.persist(user2);
		Iterable<Users> users = userService.findAll();
		
		assertThat(users.toString().contains("Test1"));
		assertThat(users.toString().contains("Test2"));
		
	}

	@Test
	final void testFindUserByUsername() throws Exception {
		Users users = new Users();
		users.setUsername("TestEntity");
		users.setPassword("password");
		testEntityManager.persist(users);
		
		Users foundUsers = userRepository.findByUsername("TestEntity");

		assertThat(foundUsers.getUsername()).isEqualTo("TestEntity");
		
	}

}
