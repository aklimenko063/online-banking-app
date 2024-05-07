package org.javaacademy.onlinebankingapp.service;

import org.javaacademy.onlinebankingapp.dto.OperationDtoRq;
import org.javaacademy.onlinebankingapp.dto.OperationDtoRs;
import org.javaacademy.onlinebankingapp.dto.UserDtoRs;
import org.javaacademy.onlinebankingapp.dto.UserRegistrationDtoRq;
import org.javaacademy.onlinebankingapp.entity.Operation;
import org.javaacademy.onlinebankingapp.entity.User;
import org.javaacademy.onlinebankingapp.enums.TypeOperation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConverterService {

	public User convertUserRegistrationDtoRqToUserEntity(UserRegistrationDtoRq userRegistrationDtoRq) {
		return new User(userRegistrationDtoRq.getFullName(),
				userRegistrationDtoRq.getPhoneNumber());
	}

	public UserDtoRs convertUserEntityToUserDtoRs(User user) {
		return UserDtoRs.builder()
				.ownerFullName(user.getOwnerFullName())
				.phoneNumber(user.getPhoneNumber())
				.user(user)
				.build();
	}

	public OperationDtoRs convertOperationEntityToOperationRs(Operation operation) {
		return OperationDtoRs.builder()
				.uuid(operation.getUuid())
				.creationDateTime(operation.getCreationDateTime())
				.accountNumber(operation.getAccountNumber())
				.typeOperation(operation.getTypeOperation())
				.sum(operation.getSum())
				.purposeOfPayment(operation.getPurposeOfPayment())
				.build();
	}

	public Operation convertOperationRqToOperationEntity(OperationDtoRq dtoRq) {
		return new Operation(
					LocalDateTime.now(),
					dtoRq.getAccountNumber(),
					dtoRq.getSum(),
					dtoRq.getPurposeOfPayment());
	}
}
