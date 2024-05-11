package org.javaacademy.onlinebankingapp.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebankingapp.dto.OperationPayDtoRq;
import org.javaacademy.onlinebankingapp.dto.OperationReceiveDtoRq;
import org.javaacademy.onlinebankingapp.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/operations")
@RequiredArgsConstructor
public class OperationRestController {
	private final BankService bankService;

	@GetMapping
	public ResponseEntity<?> getAllOperationByUser(@RequestParam String token) {
		try {
			return status(ACCEPTED).body(bankService.getAllOperationByToken(token));
		} catch (Exception e) {
			return status(FORBIDDEN).body(e.getMessage());
		}
	}

	@PostMapping("/pay")
	public ResponseEntity<?> pay(@RequestBody OperationPayDtoRq dtoRq) {
		try {
			return status(CREATED).body(bankService.pay(dtoRq));
		} catch (Exception e) {
			return status(FORBIDDEN).body(e.getMessage());
		}
	}

	@PostMapping("/receive")
	public ResponseEntity<?> receive(@RequestBody OperationReceiveDtoRq dtoRq) {
		try {
			return status(CREATED).body(bankService.receive(dtoRq));
		} catch (Exception e) {
			return status(FORBIDDEN).body(e.getMessage());
		}
	}
}
