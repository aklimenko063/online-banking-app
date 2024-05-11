package org.javaacademy.onlinebankingapp.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebankingapp.dto.AccountDtoRq;
import org.javaacademy.onlinebankingapp.dto.UserDtoRs;
import org.javaacademy.onlinebankingapp.service.AccountService;
import org.javaacademy.onlinebankingapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountRestController {
    private final AccountService accountService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> addNewAccount(@RequestBody AccountDtoRq dtoRq) {
        try {
            UserDtoRs userDtoRs = userService.getUserByToken(dtoRq.getToken());
            return status(CREATED).body(accountService.addNewAccount(userDtoRs));
        } catch (Exception e) {
            return status(FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUserAccounts(@RequestParam String token) {
        try {
            UserDtoRs userDtoRs = userService.getUserByToken(token);
            return status(ACCEPTED).body(accountService.getAllUserAccounts(userDtoRs));
        } catch (Exception e) {
            return status(FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/{numberAccount}")
    public ResponseEntity<?> getBalanceAccounts(@PathVariable String numberAccount,
                                                @RequestHeader String token) {
        try {
            UserDtoRs userDtoRs = userService.getUserByToken(token);
            return status(ACCEPTED).body(accountService.getAccountBalance(numberAccount, userDtoRs));
        } catch (Exception e) {
            return status(FORBIDDEN).body(e.getMessage());
        }
    }
}
