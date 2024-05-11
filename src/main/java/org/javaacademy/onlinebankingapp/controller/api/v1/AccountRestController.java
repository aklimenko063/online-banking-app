package org.javaacademy.onlinebankingapp.controller.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebankingapp.dto.AccountDtoRq;
import org.javaacademy.onlinebankingapp.dto.UserDtoRs;
import org.javaacademy.onlinebankingapp.service.AccountService;
import org.javaacademy.onlinebankingapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Tag(name = "Account controller", description = "Методы по работе со счетами пользователей")
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
    @Operation(summary = "Получение всех открытых счетов пользователя")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    public ResponseEntity<?> getAllUserAccounts(@RequestParam @Parameter(description = "Токен") String token) {
        try {
            UserDtoRs userDtoRs = userService.getUserByToken(token);
            return status(OK).body(accountService.getAllUserAccounts(userDtoRs));
        } catch (Exception e) {
            return status(FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/{numberAccount}")
    public ResponseEntity<?> getBalanceAccounts(@PathVariable String numberAccount,
                                                @RequestHeader String token) {
        try {
            UserDtoRs userDtoRs = userService.getUserByToken(token);
            return status(OK).body(accountService.getAccountBalance(numberAccount, userDtoRs));
        } catch (Exception e) {
            return status(FORBIDDEN).body(e.getMessage());
        }
    }
}
