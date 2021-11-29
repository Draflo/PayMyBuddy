package com.openclassrooms.paymybuddy.accounts.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.accounts.service.ConnectionService;
import com.openclassrooms.paymybuddy.exception.AccountsDoesNotExistException;
import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.service.BuddyService;

@Controller
public class ConnectionController {

	@Autowired
	private ConnectionService connectionService;

	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private BuddyService buddyService;
	
	private static List<Buddy> friendList = new ArrayList<Buddy>();

	@GetMapping("/friendList")
	public String friendList(Model model, Authentication authentication) {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		Buddy buddy = buddyService.findByUsersUsername(username);
		Accounts myAccounts = accountsRepository.findByBuddyEmail(buddy.getEmail());
		for (Accounts accounts : myAccounts.getConnections() ) {
			Buddy myFriend =  buddyService.findByAccountsId(accounts.getId());
			friendList.add(myFriend);
		}
		model.addAttribute("friendList", friendList);
		return "friendList";
	}

	@GetMapping("/addAFriend")
	public String addafriend(Model model) {
		return "addAFriend";
	}

	@PostMapping("/addConnection")
	public String addConnection(Model model, Authentication authentication, @RequestParam String email) {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		Buddy buddy = buddyService.findByUsersUsername(username);
		Accounts myAccounts = accountsRepository.findByBuddyEmail(buddy.getEmail());
		String message = "This accounts does not exists";
		try {
			connectionService.addConnection(email, myAccounts);
		} catch (AccountsDoesNotExistException e) {
			model.addAttribute("error", message);
				return "addAFriend";
		}
		return "redirect:/friendList";
	}
}
