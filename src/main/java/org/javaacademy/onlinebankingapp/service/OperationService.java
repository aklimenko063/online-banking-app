package org.javaacademy.onlinebankingapp.service;

import lombok.Data;
import org.javaacademy.onlinebankingapp.dto.OperationPayDtoRq;
import org.javaacademy.onlinebankingapp.dto.OperationDtoRs;
import org.javaacademy.onlinebankingapp.dto.OperationReceiveDtoRq;
import org.javaacademy.onlinebankingapp.entity.Operation;
import org.javaacademy.onlinebankingapp.repository.OperationRepositoryInterface;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.function.Function;

import static org.javaacademy.onlinebankingapp.enums.TypeOperation.INCOME;
import static org.javaacademy.onlinebankingapp.enums.TypeOperation.OUTCOME;

@Service
@Data
public class OperationService {
	private final OperationRepositoryInterface operationRepository;
	private final AccountService accountService;
	private final ConverterComponent converterComponent;

	public SortedSet<Operation> getAllOperationByNumberAccount(String numberAccount) {
		SortedSet<Operation> operations = new TreeSet<>(comparator(Operation::getCreationDateTime));
		List<Operation> allOperationByNumberAccount = operationRepository
				.getAllOperationByNumberAccount(numberAccount);
		operations.addAll(allOperationByNumberAccount);
		return operations;
	}

	public OperationDtoRs addPayOperation(OperationPayDtoRq dtoRq) {
		Operation operation = converterComponent.convertOperationPayRqToOperationEntity(dtoRq);
		operation.setTypeOperation(OUTCOME);
		Operation operationDb = operationRepository.addOperation(operation);
		return converterComponent.convertOperationEntityToOperationDtoRs(operationDb);
	}

	public OperationDtoRs addReceiveOperation(OperationReceiveDtoRq dtoRq) {
		Operation operation = converterComponent.convertOperationReceiveRqToOperationEntity(dtoRq);
		operation.setTypeOperation(INCOME);
		Operation operationDb = operationRepository.addOperation(operation);
		return converterComponent.convertOperationEntityToOperationDtoRs(operationDb);
	}

	public SortedSet<OperationDtoRs> getAllOperationByUser(String token) {
		List<String> allUserAccounts = accountService.getAllUserAccounts(token);
		SortedSet<Operation> operations = new TreeSet<>(comparator(Operation::getCreationDateTime));
		SortedSet<OperationDtoRs> allOperations =
				new TreeSet<>(comparator(OperationDtoRs::getCreationDateTime));
		allUserAccounts.forEach(e -> operations.addAll(getAllOperationByNumberAccount(e)));
		allOperations.addAll(operations.stream()
				.map(e -> converterComponent.convertOperationEntityToOperationDtoRs(e))
				.toList());
		return allOperations;
	}

	private <T, U extends Comparable<? super U>> Comparator<T> comparator(Function<T, U> keyExtractor) {
		return Comparator.comparing(keyExtractor).reversed();
	}
}
