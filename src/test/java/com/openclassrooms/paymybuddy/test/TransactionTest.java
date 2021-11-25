package com.openclassrooms.paymybuddy.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.exception.AccountsDoesNotExistException;
import com.openclassrooms.paymybuddy.exception.ConnectionDoesNotExistException;
import com.openclassrooms.paymybuddy.exception.InsufficientFundsException;
import com.openclassrooms.paymybuddy.security.repository.BuddyRepository;
import com.openclassrooms.paymybuddy.transactions.model.Transaction;
import com.openclassrooms.paymybuddy.transactions.repository.TransactionRepository;
import com.openclassrooms.paymybuddy.transactions.service.TransactionService;

@WebMvcTest(TransactionService.class)
class TransactionTest {

	@MockBean
	private UserDetailsService userDetailsService;
	
	@MockBean
	private BuddyRepository buddyRepository;
	
	@MockBean
	private AccountsRepository accountsRepository;
	
	@MockBean
	private TransactionRepository transactionRepository;
	
	@Autowired
	private TransactionService transactionService;
	
	@Test
	@WithMockUser
	final void testFindByUsersUsername() {
		Transaction transaction = new Transaction();
		Accounts senderAccounts = new Accounts();
		Accounts receiver = new Accounts();
		LocalDate localDate = LocalDate.now();
		transaction.setAmount(20);
		transaction.setDescription("Test");
		transaction.setFee(1);
		transaction.setId(1);
		transaction.setTransactionDate(localDate);
		transaction.setReceiverAccounts(receiver);
		transaction.setSenderAccounts(senderAccounts);
		List<Transaction> myTransactions = new ArrayList<>();
		myTransactions.add(transaction);
		
		when(transactionRepository.findByUsersUsername("user")).thenReturn(myTransactions);
		
		List<Transaction> listFound = transactionService.findByUsersUsername("user");
		
		assertThat(listFound).isEqualTo(myTransactions);
	}
	
	@Test
	final void testSaveTransaction() throws AccountsDoesNotExistException {
		Transaction transaction = new Transaction();
		Accounts senderAccounts = new Accounts();
		Accounts receiver = new Accounts();
		LocalDate localDate = LocalDate.now();
		transaction.setAmount(20);
		transaction.setDescription("Test");
		transaction.setFee(1);
		transaction.setId(1);
		transaction.setTransactionDate(localDate);
		transaction.setReceiverAccounts(receiver);
		transaction.setSenderAccounts(senderAccounts);
		
		when(transactionRepository.save(transaction)).thenReturn(transaction);
		
		Transaction savedTransaction = transactionService.saveTransaction(transaction);
		
		assertThat(savedTransaction.getAmount()).isEqualTo(20);
		assertThat(savedTransaction.getDescription()).isEqualTo("Test");
		assertThat(savedTransaction.getFee()).isEqualTo(1);
		assertThat(savedTransaction.getId()).isEqualTo(1);
		assertThat(savedTransaction.getReceiverAccounts()).isEqualTo(receiver);
		assertThat(savedTransaction.getSenderAccounts()).isEqualTo(senderAccounts);
		assertThat(savedTransaction.getTransactionDate()).isEqualTo(localDate);
		
	}
	
	@Test
	final void testBalanceUpdate() {
		Accounts myAccounts = new Accounts();
		myAccounts.setBalance(100);
		Accounts beneficiary = new Accounts();
		beneficiary.setBalance(0);
		Transaction transaction = new Transaction();
		transaction.setAmount(20);
		transaction.setFee(transaction.getAmount()*0.005);
		transaction.setReceiverAccounts(beneficiary);
		transaction.setSenderAccounts(myAccounts);
		when(accountsRepository.save(myAccounts)).thenReturn(myAccounts);
		when(accountsRepository.save(beneficiary)).thenReturn(beneficiary);
		
		transactionService.balanceUpdate(myAccounts, beneficiary, transaction);
		
		assertThat(myAccounts.getBalance()).isEqualTo(79.9);
		assertThat(beneficiary.getBalance()).isEqualTo(20);
		
	}
	
	@Test
	final void testValidation() throws InsufficientFundsException, ConnectionDoesNotExistException {
		Accounts myAccounts = new Accounts();
		Accounts beneficiary = new Accounts();
		Set<Accounts> connections = new TreeSet<Accounts>();
		connections.add(beneficiary);
		myAccounts.setConnections(connections);
		myAccounts.setBalance(100);
		Transaction transaction = new Transaction();
		transaction.setAmount(20);
		transaction.setFee(transaction.getAmount()*0.005);
		transaction.setSenderAccounts(myAccounts);
		transaction.setReceiverAccounts(beneficiary);
		
		transactionService.validation(myAccounts, transaction);
		
	}
	
	@Test
	final void testValidationRejectedByInsufficientFunds() throws InsufficientFundsException {
		Accounts myAccounts = new Accounts();
		Accounts beneficiary = new Accounts();
		Set<Accounts> connections = new TreeSet<Accounts>();
		connections.add(beneficiary);
		myAccounts.setConnections(connections);
		myAccounts.setBalance(100);
		Transaction transaction = new Transaction();
		transaction.setAmount(1000);
		transaction.setFee(transaction.getAmount()*0.005);
		transaction.setSenderAccounts(myAccounts);
		transaction.setReceiverAccounts(beneficiary);
		
		assertThrows(InsufficientFundsException.class, () -> transactionService.validation(myAccounts, transaction));
	}
	
	@Test
	final void testValidationRejectedByConnectionDoesNotExist() throws ConnectionDoesNotExistException {
		Accounts myAccounts = new Accounts();
		Accounts beneficiary = new Accounts();
		myAccounts.setBalance(100);
		Transaction transaction = new Transaction();
		transaction.setAmount(20);
		transaction.setFee(transaction.getAmount()*0.005);
		transaction.setSenderAccounts(myAccounts);
		transaction.setReceiverAccounts(beneficiary);
		
		assertThrows(ConnectionDoesNotExistException.class, () -> transactionService.validation(myAccounts, transaction));
	}
}
