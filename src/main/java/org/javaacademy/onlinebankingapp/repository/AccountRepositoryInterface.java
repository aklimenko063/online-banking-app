package org.javaacademy.onlinebankingapp.repository;

import org.javaacademy.onlinebankingapp.entity.Account;
import org.javaacademy.onlinebankingapp.entity.User;

import java.util.List;
import java.util.Optional;

public interface AccountRepositoryInterface {

	Optional<Account> getByNumberAccount(String number);

	boolean searchByNumber(String number);

	void addNewAccount(Account account);

	List<Account> getAllUserAccounts(User user);

	boolean isEmpty();

	String getLastNumberAccount();
}
