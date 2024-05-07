package org.javaacademy.onlinebankingapp.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebankingapp.dto.OperationDtoRq;
import org.javaacademy.onlinebankingapp.dto.UserDtoRs;
import org.javaacademy.onlinebankingapp.service.BankService;
import org.javaacademy.onlinebankingapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/operations")
@RequiredArgsConstructor
public class OperationControllerV1 {
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
	public ResponseEntity<?> pay(@RequestBody OperationDtoRq dtoRq) {
		try {
			return status(CREATED).body(bankService.pay(dtoRq));
		} catch (Exception e) {
			return status(FORBIDDEN).body(e.getMessage());
		}
	}

	@PostMapping("/receive")
	public ResponseEntity<?> receive(@RequestBody OperationDtoRq dtoRq) {
		try {
			return status(CREATED).body(bankService.receive(dtoRq));
		} catch (Exception e) {
			return status(FORBIDDEN).body(e.getMessage());
		}
	}
}
