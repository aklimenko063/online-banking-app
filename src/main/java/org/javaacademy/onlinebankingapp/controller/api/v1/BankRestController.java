package org.javaacademy.onlinebankingapp.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebankingapp.dto.OperationPayDtoRq;
import org.javaacademy.onlinebankingapp.service.BankPartnerIntegrationService;
import org.javaacademy.onlinebankingapp.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BankRestController {
    private final BankService bankService;

    @GetMapping("/bank-info")
    public ResponseEntity<String> getBankInfo() {
        try {
            return status(OK).body(bankService.getBankInfo());
        } catch (Exception e) {
            return status(FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferToBankPartner(@RequestBody OperationPayDtoRq dtoRq) {
        try {
            return status(ACCEPTED).body(bankService.transferToBankPartner(dtoRq));
        } catch (Exception e) {
            return status(SERVICE_UNAVAILABLE).body(e.getMessage());
        }
    }
}
