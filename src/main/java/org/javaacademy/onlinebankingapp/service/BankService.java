package org.javaacademy.onlinebankingapp.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebankingapp.dto.OperationDtoRq;
import org.javaacademy.onlinebankingapp.dto.OperationDtoRs;
import org.javaacademy.onlinebankingapp.dto.UserDtoRs;
import org.javaacademy.onlinebankingapp.entity.Operation;
import org.javaacademy.onlinebankingapp.enums.TypeOperation;
import org.javaacademy.onlinebankingapp.exception.OperationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.javaacademy.onlinebankingapp.enums.TypeOperation.*;

@Service
@Data
@RequiredArgsConstructor
public class BankService {
	private final OperationService operationService;
	private final UserService userService;
	private final AccountService accountService;

	public List<OperationDtoRs> getAllOperationByToken(String token) {
		UserDtoRs userDtoRs = userService.getUserByToken(token);
		return operationService.getAllOperationByUser(userDtoRs);
	}

	public OperationDtoRs pay(OperationDtoRq dtoRq) {
		UserDtoRs userDtoRs = userService.getUserByToken(dtoRq.getToken());
		if (!accountService.checkUserAccount(userDtoRs, dtoRq.getAccountNumber())) {
			throw new OperationException("Номер счета не принадлежит данному пользователю!");
		}
		accountService.addOutcome(dtoRq.getAccountNumber(), dtoRq.getSum());
		return operationService.addOperation(dtoRq, OUTCOME);
	}

	public OperationDtoRs receive(OperationDtoRq dtoRq) {
		UserDtoRs userDtoRs = userService.getUserByToken(dtoRq.getToken());
		if (!accountService.checkUserAccount(userDtoRs, dtoRq.getAccountNumber())) {
			throw new OperationException("Номер счета не принадлежит данному пользователю!");
		}
		accountService.addIncome(dtoRq.getAccountNumber(), dtoRq.getSum());
		return operationService.addOperation(dtoRq, INCOME);
	}
}
