package org.javaacademy.onlinebankingapp.service;

import lombok.Data;
import org.javaacademy.onlinebankingapp.dto.OperationDtoRq;
import org.javaacademy.onlinebankingapp.dto.OperationDtoRs;
import org.javaacademy.onlinebankingapp.dto.UserDtoRs;
import org.javaacademy.onlinebankingapp.entity.Operation;
import org.javaacademy.onlinebankingapp.enums.TypeOperation;
import org.javaacademy.onlinebankingapp.repository.impl.OperationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Data
public class OperationService {
	private final OperationRepository operationRepository;
	private final AccountService accountService;
	private final ConverterService converterService;

	public List<Operation> getAllOperationByNumberAccount(String numberAccount) {
		return operationRepository.getAllOperationByNumberAccount(numberAccount);
	}

	public OperationDtoRs addOperation(OperationDtoRq dtoRq, TypeOperation typeOperation) {
		Operation operation = converterService.convertOperationRqToOperationEntity(dtoRq);
		operation.setTypeOperation(typeOperation);
		Operation operationDb = operationRepository.addOperation(operation);
		return converterService.convertOperationEntityToOperationRs(operationDb);
	}

	public List<OperationDtoRs> getAllOperationByUser(UserDtoRs userDtoRs) {
		List<String> allUserAccounts = accountService.getAllUserAccounts(userDtoRs);
		List<Operation> allOperation = new ArrayList<>();
		allUserAccounts.forEach(e -> allOperation.addAll(getAllOperationByNumberAccount(e)));
		return allOperation.stream()
				.map(e -> converterService.convertOperationEntityToOperationRs(e))
				.toList();
	}
}
