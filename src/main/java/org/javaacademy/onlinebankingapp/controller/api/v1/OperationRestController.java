package org.javaacademy.onlinebankingapp.controller.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebankingapp.dto.OperationDtoRs;
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
@Tag(name = "Operation controller", description = "Методы по работе с операциями")
public class OperationRestController {
	private final BankService bankService;

	@GetMapping
	@Operation(summary = "Получение всех операций")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = OperationDtoRs.class))),
			@ApiResponse(responseCode = "403")})
	public ResponseEntity<?> getAllOperationByUser(
			@RequestParam @Parameter(description = "Токен") String token) {
		try {
			return status(ACCEPTED).body(bankService.getAllOperationByToken(token));
		} catch (Exception e) {
			return status(FORBIDDEN).body(e.getMessage());
		}
	}

	@PostMapping("/pay")
	@Operation(summary = "Операция списания")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = OperationDtoRs.class))),
			@ApiResponse(responseCode = "403")})
	public ResponseEntity<?> pay(@RequestBody OperationPayDtoRq dtoRq) {
		try {
			return status(CREATED).body(bankService.pay(dtoRq));
		} catch (Exception e) {
			return status(FORBIDDEN).body(e.getMessage());
		}
	}

	@PostMapping("/receive")
	@Operation(summary = "Операция зачисления")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = OperationDtoRs.class))),
			@ApiResponse(responseCode = "403")})
	public ResponseEntity<?> receive(@RequestBody OperationReceiveDtoRq dtoRq) {
		try {
			return status(CREATED).body(bankService.receive(dtoRq));
		} catch (Exception e) {
			return status(FORBIDDEN).body(e.getMessage());
		}
	}
}
