package org.javaacademy.onlinebankingapp.repository.impl;

import org.javaacademy.onlinebankingapp.entity.Account;
import org.javaacademy.onlinebankingapp.entity.User;
import org.javaacademy.onlinebankingapp.repository.AccountRepositoryInterface;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class AccountRepository implements AccountRepositoryInterface {
    private final SortedMap<String, Account> accountRepositoryBd = new TreeMap<>();

    @Override
    public Optional<Account> getByNumberAccount(String number) {
        return Optional.ofNullable(accountRepositoryBd.get(number));
    }

    @Override
    public boolean searchByNumber(String number) {
        return accountRepositoryBd.containsKey(number);
    }

    @Override
    public void addNewAccount(Account account) {
        accountRepositoryBd.put(account.getNumber(), account);
    }

    @Override
    public List<Account> getAllUserAccounts(User user) {
        return accountRepositoryBd.values()
                .stream()
                .filter(e -> user.equals(e.getOwnerAccount()))
                .toList();
    }

    @Override
    public boolean isEmpty() {
        return accountRepositoryBd.isEmpty();
    }

    @Override
    public String getLastNumberAccount() {
        return accountRepositoryBd.lastKey();
    }
}
