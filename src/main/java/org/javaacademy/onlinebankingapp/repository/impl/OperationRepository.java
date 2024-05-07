package org.javaacademy.onlinebankingapp.repository.impl;

import org.javaacademy.onlinebankingapp.entity.Operation;
import org.javaacademy.onlinebankingapp.entity.User;
import org.javaacademy.onlinebankingapp.repository.OperationRepositoryInterface;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class OperationRepository implements OperationRepositoryInterface {
	private final Map<LocalDateTime, Operation> operationRepositoryDb = new TreeMap<>();

	//TODO изменить сортировку
	public List<Operation> getAllOperationByNumberAccount(String numberAccount) {
		return operationRepositoryDb.values()
				.stream()
				.filter(e -> numberAccount.equals(e.getAccountNumber()))
				.toList();
	}

	public Operation addOperation(Operation operation) {
		operation.setUuid(UUID.randomUUID());
		operationRepositoryDb.put(operation.getCreationDateTime(), operation);
		return operation;
	}
}
