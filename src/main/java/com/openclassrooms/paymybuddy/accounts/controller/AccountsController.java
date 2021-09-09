package com.openclassrooms.paymybuddy.accounts.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.service.AccountsService;

@Controller
public class AccountsController {
	
	@Autowired
	private AccountsService accountsService;
	
	@RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }
	
	@RequestMapping(value = "/createAccount", method = {RequestMethod.GET, RequestMethod.POST})
	public String saveAccounts(Model model, @ModelAttribute("accounts") Accounts accounts) {
		accounts.setBalance(0);
		accountsService.saveAccount(null);
		model.addAttribute("balance", accounts.getBalance());
		model.addAttribute("numberAcc", accounts.getAccountNumber());
		return "redirect:/createdAccount";
	}
	
	@GetMapping("/myAccount")
	public String myAccount (Model model) {
		Accounts accounts = accountsService.findByOwnerEmail(null);
		model.addAttribute("balance", accounts.getBalance());
		model.addAttribute("numberAcc", accounts.getAccountNumber());
		return "myAccount";
	}

}
