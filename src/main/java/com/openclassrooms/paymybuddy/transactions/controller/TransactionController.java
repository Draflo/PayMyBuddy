package com.openclassrooms.paymybuddy.transactions.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.transactions.model.Transaction;
import com.openclassrooms.paymybuddy.transactions.service.TransactionService;

@Controller
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@GetMapping("/myTransactions")
	public String myTransactions (Model model) {
		List<Transaction> myTransactions = transactionService.findByOwner(null);
		model.addAttribute("myTransactions", myTransactions);
		return "myTransactions";
	}
	
	@GetMapping("/transfer")
	public String transferForm(Model model) {
		Transaction transaction = new Transaction();
		model.addAttribute("transaction", transaction);
		return "transfer";
	}
	
	@PostMapping("/transfer")
	public String transfer (@ModelAttribute("transfer") Transaction transaction, Model model) {
		return "sucessTransfer";
	}

}
