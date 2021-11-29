package com.openclassrooms.paymybuddy.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.model.BankAccount;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.accounts.repository.BankAccountRepository;
import com.openclassrooms.paymybuddy.accounts.service.BankAccountService;
import com.openclassrooms.paymybuddy.exception.AccountsDoesNotExistException;
import com.openclassrooms.paymybuddy.exception.BankAccountDoesNotExist;
import com.openclassrooms.paymybuddy.exception.InsufficientFundsException;
import com.openclassrooms.paymybuddy.security.model.Buddy;
import com.openclassrooms.paymybuddy.security.repository.BuddyRepository;
import com.openclassrooms.paymybuddy.transactions.model.WithdrawalDeposit;
import com.openclassrooms.paymybuddy.transactions.repository.WithdrawalDepositRepository;
import com.openclassrooms.paymybuddy.transactions.service.WithdrawalDepositService;

@WebMvcTest(WithdrawalDepositService.class)
class WithdrawalDepositTest {

	@MockBean
	private UserDetailsService userDetailsService;
	
	@MockBean
	private BuddyRepository buddyRepository;
	
	@MockBean
	private AccountsRepository accountsRepository;
	
	@MockBean
	private BankAccountRepository bankAccountRepository;
	
	@MockBean
	private BankAccountService bankAccountService;
	
	@MockBean
	private WithdrawalDepositRepository withdrawalDepositRepository;
	
	@Autowired
	private WithdrawalDepositService withdrawalDepositService;
	
	@Test
	@WithMockUser
	final void testFindByUsersUsername() {
		WithdrawalDeposit withdrawalDeposit = new WithdrawalDeposit();
		Accounts myAccounts = new Accounts();
		BankAccount myBankAccount = new BankAccount();
		LocalDate localDate = LocalDate.now();
		withdrawalDeposit.setAmount(20);
		withdrawalDeposit.setId(1);
		withdrawalDeposit.setTransactionDate(localDate);
		withdrawalDeposit.setMyAccounts(myAccounts);
		withdrawalDeposit.setMyBankAccount(myBankAccount);
		List<WithdrawalDeposit> myTransactions = new ArrayList<>();
		myTransactions.add(withdrawalDeposit);
		
		when(withdrawalDepositRepository.findByUsersUsername("user")).thenReturn(myTransactions);
		
		List<WithdrawalDeposit> listFound = withdrawalDepositService.findByUsersUsername("user");
		
		assertThat(listFound).isEqualTo(myTransactions);
	}
	
	@Test
	final void testSaveTransaction() throws AccountsDoesNotExistException {
		WithdrawalDeposit withdrawalDeposit = new WithdrawalDeposit();
		Accounts myAccounts = new Accounts();
		BankAccount myBankAccount = new BankAccount();
		LocalDate localDate = LocalDate.now();
		withdrawalDeposit.setAmount(20);
		withdrawalDeposit.setId(1);
		withdrawalDeposit.setTransactionDate(localDate);
		withdrawalDeposit.setMyAccounts(myAccounts);
		withdrawalDeposit.setMyBankAccount(myBankAccount);
		
		when(withdrawalDepositRepository.save(withdrawalDeposit)).thenReturn(withdrawalDeposit);
		
		WithdrawalDeposit savedWithdrawalDeposit = withdrawalDepositService.saveWithdrawalDeposit(withdrawalDeposit);
		
		assertThat(savedWithdrawalDeposit.getAmount()).isEqualTo(20);
		assertThat(savedWithdrawalDeposit.getId()).isEqualTo(1);
		assertThat(savedWithdrawalDeposit.getMyAccounts()).isEqualTo(myAccounts);
		assertThat(savedWithdrawalDeposit.getMyBankAccount()).isEqualTo(myBankAccount);
		assertThat(savedWithdrawalDeposit.getTransactionDate()).isEqualTo(localDate);
		
	}
	
	@Test
	final void testBalanceUpdate() {
		Accounts myAccounts = new Accounts();
		myAccounts.setBalance(100);
		BankAccount myBankAccount = new BankAccount();
		WithdrawalDeposit withdrawal = new WithdrawalDeposit();
		withdrawal.setAmount(20);
		withdrawal.setMyBankAccount(myBankAccount);
		withdrawal.setMyAccounts(myAccounts);
		when(accountsRepository.save(myAccounts)).thenReturn(myAccounts);
		
		withdrawalDepositService.balanceUpdate(myAccounts, myBankAccount, withdrawal);
		
		assertThat(myAccounts.getBalance()).isEqualTo(80);
		
		
	}
	
	@Test
	final void testValidation() throws InsufficientFundsException, BankAccountDoesNotExist {
		Accounts myAccounts = new Accounts();
		Buddy myself = new Buddy();
		myself.setEmail("testmail.com");
		myAccounts.setBuddy(myself);
		BankAccount myBankAccount = new BankAccount();
		myAccounts.setBalance(1000);
		WithdrawalDeposit withdrawal = new WithdrawalDeposit();
		withdrawal.setAmount(10);
		withdrawal.setMyAccounts(myAccounts);
		withdrawal.setMyBankAccount(myBankAccount);
		when(bankAccountService.findByBuddyEmail("testmail.com")).thenReturn(myBankAccount);
		
		withdrawalDepositService.validation(myAccounts, withdrawal);
		
	}
	
	@Test
	final void testValidationRejectedByInsufficientFunds() throws InsufficientFundsException {
		Accounts myAccounts = new Accounts();
		BankAccount myBankAccount = new BankAccount();
		myAccounts.setBalance(0);
		WithdrawalDeposit withdrawal = new WithdrawalDeposit();
		withdrawal.setAmount(20);
		withdrawal.setMyAccounts(myAccounts);
		withdrawal.setMyBankAccount(myBankAccount);
		
		assertThrows(InsufficientFundsException.class, () -> withdrawalDepositService.validation(myAccounts, withdrawal));
	}
	
	@Test
	final void testValidationRejectedByBankAccountDoesNotExist() throws BankAccountDoesNotExist {
		Accounts myAccounts = new Accounts();
		myAccounts.setBalance(100);
		Buddy myself = new Buddy();
		myself.setEmail("testmail.com");
		myAccounts.setBuddy(myself);
		WithdrawalDeposit withdrawal = new WithdrawalDeposit();
		withdrawal.setAmount(20);
		withdrawal.setMyAccounts(myAccounts);
		when(bankAccountService.findByBuddyEmail("testmail.com")).thenReturn(null);
		
		assertThrows(BankAccountDoesNotExist.class, () -> withdrawalDepositService.validation(myAccounts, withdrawal));
	}

}
