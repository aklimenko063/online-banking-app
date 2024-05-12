package org.javaacademy.onlinebankingapp.controller.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebankingapp.dto.AccountDtoRq;
import org.javaacademy.onlinebankingapp.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Tag(name = "Account controller", description = "Методы по работе со счетами пользователей")
public class AccountRestController {
    private final AccountService accountService;

    @PostMapping
    @Operation(summary = "Создание счета пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "403")})
    public ResponseEntity<String> addNewAccount(@RequestBody AccountDtoRq dtoRq) {
        try {
            return status(CREATED).body(accountService.addNewAccount(dtoRq));
        } catch (Exception e) {
            return status(FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Получение всех открытых счетов пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "403")})
    public ResponseEntity<?> getAllUserAccounts(
            @RequestParam @Parameter(description = "Токен") String token) {
        try {
            return status(OK).body(accountService.getAllUserAccounts(token));
        } catch (Exception e) {
            return status(FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/{numberAccount}")
    @Operation(summary = "Получение баланса по счету")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BigDecimal.class))),
            @ApiResponse(responseCode = "403")})
    public ResponseEntity<?> getBalanceAccounts(
            @PathVariable @Parameter(description = "Номер счета") String numberAccount,
            @RequestHeader @Parameter(description = "Токен") String token) {
        try {
            return status(OK).body(accountService.getAccountBalance(numberAccount, token));
        } catch (Exception e) {
            return status(FORBIDDEN).body(e.getMessage());
        }
    }
}
