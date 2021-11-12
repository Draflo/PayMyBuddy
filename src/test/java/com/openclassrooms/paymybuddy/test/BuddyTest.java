package com.openclassrooms.paymybuddy.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.repository.BuddyRepository;
import com.openclassrooms.paymybuddy.security.service.BuddyService;

@WebMvcTest(BuddyService.class)
class BuddyTest {
	
	@MockBean
	private BuddyRepository buddyRepository;
	
	@Autowired
	private BuddyService buddyService;
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	public static List<Buddy> buddyList = new ArrayList<>();
	
	static {
		Buddy buddy1 = new Buddy();
		Buddy buddy2 = new Buddy();
		buddy1.setFirstName("TestName1");
		buddy2.setFirstName("TestName2");
		buddyList.add(buddy1);
		buddyList.add(buddy2);
		
	}

	@Test
	final void testGetAllBuddy() throws Exception {
		when(buddyRepository.findAll()).thenReturn(buddyList);

		Iterable<Buddy> buddies = buddyService.findAll();

		assertThat(buddies.toString().contains("TestName1"));
		assertThat(buddies.toString().contains("TestName2"));

	}

	@Test
	final void testFindBuddyByUsersUsername() throws Exception {
		Buddy buddy1 = new Buddy();
		buddy1.setFirstName("TestName1");

		when(buddyRepository.findByUsersUsername(Mockito.anyString())).thenReturn(buddy1);

		Buddy foundBuddy = buddyService.findByUsersUsername(Mockito.anyString());

		assertThat(foundBuddy.getFirstName()).isEqualTo("TestName1");
	}
	
	@Test
	final void testFindBuddyByAccountsId() throws Exception {
		Buddy buddy1 = new Buddy();
		buddy1.setFirstName("TestName1");

		when(buddyRepository.findByAccountsId(Mockito.anyLong())).thenReturn(buddy1);

		Buddy foundBuddy = buddyService.findByAccountsId(Mockito.anyLong());

		assertThat(foundBuddy.getFirstName()).isEqualTo("TestName1");
	}
	
	@Test
	final void testSaveUser() {
		Buddy buddyToSave = new Buddy();
		buddyToSave.setFirstName("Saved");
		
		when(buddyRepository.save(buddyToSave)).thenReturn(buddyToSave);
		
		Buddy buddySaved = buddyService.saveBuddy(buddyToSave);
		
		assertThat(buddySaved.getFirstName()).isEqualTo("Saved");
	}

}
