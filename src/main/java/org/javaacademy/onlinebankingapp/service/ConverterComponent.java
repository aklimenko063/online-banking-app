package org.javaacademy.onlinebankingapp.service;

import org.javaacademy.onlinebankingapp.dto.*;
import org.javaacademy.onlinebankingapp.entity.Operation;
import org.javaacademy.onlinebankingapp.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ConverterComponent {

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

	public OperationDtoRs convertOperationEntityToOperationDtoRs(Operation operation) {
		return OperationDtoRs.builder()
				.uuid(operation.getUuid())
				.creationDateTime(operation.getCreationDateTime())
				.accountNumber(operation.getAccountNumber())
				.typeOperation(operation.getTypeOperation())
				.sum(operation.getSum())
				.purposeOfPayment(operation.getPurposeOfPayment())
				.build();
	}

	public Operation convertOperationPayRqToOperationEntity(OperationPayDtoRq dtoRq) {
		return new Operation(
					LocalDateTime.now(),
					dtoRq.getAccountNumberFrom(),
					dtoRq.getSum(),
					dtoRq.getPurposeOfPayment());
	}

	public Operation convertOperationReceiveRqToOperationEntity(OperationReceiveDtoRq dtoRq) {
		return new Operation(
				LocalDateTime.now(),
				dtoRq.getAccountNumber(),
				dtoRq.getSum(),
				dtoRq.getPurposeOfPayment());
	}

	public TransferDtoRq convertOperationPayRqToTransferDtoRq(OperationPayDtoRq dtoRq) {
		return new TransferDtoRq(
				dtoRq.getSum(),
				dtoRq.getPurposeOfPayment(),
				dtoRq.getAccountNumberTo());
	}

	public OperationReceiveDtoRq convertTransferDtoRqToOperationReceiveRq(TransferDtoRq dtoRq) {
		return new OperationReceiveDtoRq(
				dtoRq.getSum(),
				dtoRq.getNumberAccountTo(),
				dtoRq.getPurposeOfPayment());
	}
}
