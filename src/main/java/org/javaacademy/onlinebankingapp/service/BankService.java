package org.javaacademy.onlinebankingapp.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebankingapp.config.BankProperties;
import org.javaacademy.onlinebankingapp.dto.*;
import org.javaacademy.onlinebankingapp.exception.OperationException;
import org.springframework.stereotype.Service;

import java.util.SortedSet;

@Service
@Data
@RequiredArgsConstructor
public class BankService {
	private final OperationService operationService;
	private final UserService userService;
	private final AccountService accountService;
	private final BankProperties bankProperties;
	private final BankPartnerIntegrationService bankPartnerIntegrationService;
	private final ConverterComponent converterComponent;

	public SortedSet<OperationDtoRs> getAllOperationByToken(String token) {
		UserDtoRs userDtoRs = userService.getUserByToken(token);
		return operationService.getAllOperationByUser(userDtoRs);
	}

	public OperationDtoRs pay(OperationPayDtoRq dtoRq) {
		UserDtoRs userDtoRs = userService.getUserByToken(dtoRq.getToken());
		if (!accountService.checkUserAccount(userDtoRs, dtoRq.getAccountNumberFrom())) {
			throw new OperationException("Номер счета не принадлежит данному пользователю!");
		}
		accountService.addOutcome(dtoRq.getAccountNumberFrom(), dtoRq.getSum());
		return operationService.addPayOperation(dtoRq);
	}

	public OperationDtoRs receive(OperationReceiveDtoRq dtoRq) {
		accountService.addIncome(dtoRq.getAccountNumber(), dtoRq.getSum());
		return operationService.addReceiveOperation(dtoRq);
	}

	public String getBankInfo() {
		return bankProperties.getName();
	}

	public OperationDtoRs transferToBankPartner(OperationPayDtoRq dtoRq) {
		TransferDtoRq transferDto = converterComponent.convertOperationPayRqToTransferDtoRq(dtoRq);
		transferDto.setBankName(bankProperties.getName());
		bankPartnerIntegrationService.transferMoney(transferDto);
		return pay(dtoRq);
	}
}
