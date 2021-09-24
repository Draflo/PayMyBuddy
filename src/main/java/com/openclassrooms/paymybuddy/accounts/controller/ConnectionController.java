package com.openclassrooms.paymybuddy.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.accounts.service.ConnectionService;

@Controller
public class ConnectionController {

	@Autowired
	private ConnectionService connectionService;
	
	@GetMapping("/friendList")
	public String friendList (Model model) {
		
		return "friendList";
	}
	
	@PostMapping("/addConnection")
	public String addConnection(Model model) {
		
	return "friendList";
	}
}
