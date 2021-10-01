package com.openclassrooms.paymybuddy.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.openclassrooms.paymybuddy.accounts.service.ConnectionService;


@Controller
public class ConnectionController {

	@Autowired
	private ConnectionService connectionService;
	
	@GetMapping("/friendList")
	public String friendList (Model model) {
		
		return "friendList";
	}
	
	@GetMapping("/addAFriend")
	public String addafriend (Model model) {
	return "addAFriend";
	}
	
	@PostMapping("/addConnection")
	public String addConnection(Model model, @RequestParam String email) throws Exception {
	connectionService.addConnection(email);	
	return "redirect:/friendList";
	}
}
