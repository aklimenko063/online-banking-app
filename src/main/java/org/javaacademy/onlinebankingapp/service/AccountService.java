package org.javaacademy.onlinebankingapp.service;

import lombok.Data;
import org.javaacademy.onlinebankingapp.config.BankProperties;
import org.javaacademy.onlinebankingapp.dto.UserDtoRs;
import org.javaacademy.onlinebankingapp.entity.Account;
import org.javaacademy.onlinebankingapp.exception.AccountBalanceLessZeroException;
import org.javaacademy.onlinebankingapp.exception.AccountNotFoundException;
import org.javaacademy.onlinebankingapp.repository.AccountRepositoryInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@Data
public class AccountService {
    private final static long FIRST_NUMBER_ACCOUNT = 0000_0000_0001L;
    private final AccountRepositoryInterface accountRepository;
    private final BankProperties bankProperties;

    public String addNewAccount(UserDtoRs dtoRs) {
        Account newAccount = new Account(generateAccountNumber(), dtoRs.getUser());
        accountRepository.addNewAccount(newAccount);
        return newAccount.getNumber();
    }

    public List<String> getAllUserAccounts(UserDtoRs dtoRs) {
        return accountRepository.getAllUserAccounts(dtoRs.getUser())
                .stream()
                .map(e -> e.getNumber())
                .toList();
    }

    public BigDecimal getAccountBalance(String numberAccount, UserDtoRs dtoRs) {
        checkUserAccount(dtoRs, numberAccount);
        Account account = findByNumberAccount(numberAccount);
        if (!checkUserAccount(dtoRs, numberAccount)) {
            throw new AccountNotFoundException("Некорректный токен. Счет не принадлежит пользователю!");
        }
        return account.getBalance();
    }

    public void addIncome(String numberAccount, BigDecimal sum) {
        Account account = findByNumberAccount(numberAccount);
        account.setBalance(account.getBalance().add(sum));
    }

    public void addOutcome(String numberAccount, BigDecimal sum) {
        Account account = findByNumberAccount(numberAccount);
        BigDecimal newSum = account.getBalance().subtract(sum);
        if (newSum.signum() < 0) {
            throw new AccountBalanceLessZeroException("Недостаточно средств для выполнения операции!");
        }
        account.setBalance(newSum);
    }

    public boolean checkUserAccount(UserDtoRs dtoRs, String numberAccount) {
        Account account = findByNumberAccount(numberAccount);
        return Objects.equals(dtoRs.getUser(), account.getOwnerAccount());
    }

    private String generateAccountNumber() {
        if (accountRepository.isEmpty()) {
            return String.valueOf(bankProperties.getAccountPrefixNumber() + FIRST_NUMBER_ACCOUNT);
        } else {
            String lastNumberAccount = accountRepository.getLastNumberAccount();
            Long lastNumber = Long.parseLong(lastNumberAccount);
            return String.valueOf(lastNumber + 1);
        }
    }

    private Account findByNumberAccount(String numberAccount) {
        return accountRepository.getByNumberAccount(numberAccount)
                .orElseThrow(() -> new AccountNotFoundException("Счета с таким номером не существует!"));
    }
}
