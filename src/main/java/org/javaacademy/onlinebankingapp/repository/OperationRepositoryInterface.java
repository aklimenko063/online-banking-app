package org.javaacademy.onlinebankingapp.repository;

import org.javaacademy.onlinebankingapp.entity.Operation;
import java.util.List;

public interface OperationRepositoryInterface {

    List<Operation> getAllOperationByNumberAccount(String numberAccount);

    Operation addOperation(Operation operation);

}
