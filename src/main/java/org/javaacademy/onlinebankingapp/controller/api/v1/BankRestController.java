package org.javaacademy.onlinebankingapp.controller.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebankingapp.dto.OperationDtoRs;
import org.javaacademy.onlinebankingapp.dto.OperationPayDtoRq;
import org.javaacademy.onlinebankingapp.exception.BankNotFoundException;
import org.javaacademy.onlinebankingapp.exception.ServiceIntegrationException;
import org.javaacademy.onlinebankingapp.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Bank controller", description = "Методы по работе с сервисом банка")
public class BankRestController {
    private final BankService bankService;

    @GetMapping("/bank-info")
    @Operation(summary = "Получение имени банка")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "403")})
    public ResponseEntity<String> getBankInfo() {
        try {
            return status(OK).body(bankService.getBankInfo());
        } catch (Exception e) {
            return status(FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/transact")
    @Operation(summary = "Перевод средств на другой счет")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OperationDtoRs.class))),
            @ApiResponse(responseCode = "503"),
            @ApiResponse(responseCode = "403")})
    public ResponseEntity<?> transferMoney(@RequestBody OperationPayDtoRq dtoRq) {
        try {
            return status(ACCEPTED).body(bankService.transferMoney(dtoRq));
        } catch (ServiceIntegrationException e) {
            return status(SERVICE_UNAVAILABLE).body(e.getMessage());
        } catch (BankNotFoundException e) {
            return status(FORBIDDEN).body(e.getMessage());
        }
    }
}
