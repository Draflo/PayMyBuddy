package com.openclassrooms.paymybuddy.transactions.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.paymybuddy.accounts.model.Accounts;
import com.openclassrooms.paymybuddy.accounts.model.BankAccount;
import com.openclassrooms.paymybuddy.accounts.repository.AccountsRepository;
import com.openclassrooms.paymybuddy.accounts.service.BankAccountService;
import com.openclassrooms.paymybuddy.exception.BankAccountDoesNotExist;
import com.openclassrooms.paymybuddy.exception.InsufficientFundsException;
import com.openclassrooms.paymybuddy.transactions.model.WithdrawalDeposit;
import com.openclassrooms.paymybuddy.transactions.repository.WithdrawalDepositRepository;

@Service
public class WithdrawalDepositService {

	@Autowired
	private WithdrawalDepositRepository withdrawalDepositRepository;

	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private BankAccountService bankAccountService;

	@Transactional
	public WithdrawalDeposit saveWithdrawalDeposit(WithdrawalDeposit withdrawalDeposit) {
		return withdrawalDepositRepository.save(withdrawalDeposit);
	}

	@Transactional
	public WithdrawalDeposit validation(Accounts myAccounts, WithdrawalDeposit WDInfo) throws InsufficientFundsException, BankAccountDoesNotExist {
		if (myAccounts.getBalance() < (WDInfo.getAmount())) {
			System.err.println("You don't have enough funds for this transfer");
			throw new InsufficientFundsException("InsufficientFunds", "You don't have enough funds for this transfer");
		}
		
		if (bankAccountService.findByBuddyEmail(myAccounts.getBuddy().getEmail()) == null) {
			System.err.println(
					"You curently don't have any BankAccount registered. Please register one to withdraw or deposit money ! ");
			throw new BankAccountDoesNotExist();
		}
		return null;
	}

	@Transactional
	public WithdrawalDeposit balanceUpdate(Accounts myAccounts, BankAccount myBankAccount, WithdrawalDeposit WDInfo) {
		myAccounts.setBalance(myAccounts.getBalance() - (WDInfo.getAmount()));
		accountsRepository.save(myAccounts);
		return null;
	}

	public List<WithdrawalDeposit> findByUsersUsername(String username) {
		List<WithdrawalDeposit> myWithdrawalDeposits = withdrawalDepositRepository.findByUsersUsername(username);
		return myWithdrawalDeposits;
	}
}
