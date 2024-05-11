package org.javaacademy.onlinebankingapp.repository;

import org.javaacademy.onlinebankingapp.entity.Operation;

import java.util.List;
import java.util.UUID;

public interface OperationRepositoryInterface {

    List<Operation> getAllOperationByNumberAccount(String numberAccount);

    Operation addOperation(Operation operation);

}
