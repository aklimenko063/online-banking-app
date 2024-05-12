package org.javaacademy.onlinebankingapp.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebankingapp.config.BankProperties;
import org.javaacademy.onlinebankingapp.dto.*;
import org.javaacademy.onlinebankingapp.exception.BankNotFoundException;
import org.javaacademy.onlinebankingapp.exception.OperationException;
import org.springframework.stereotype.Service;

import java.util.Objects;
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
		return operationService.getAllOperationByUser(token);
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

	public OperationDtoRs transferMoney(OperationPayDtoRq dtoRq) {

		if (checkCurrentBank(dtoRq.getBankNameTo())) {
			OperationReceiveDtoRq receiveDtoRq = converterComponent
					.convertOperationPayRqToOperationReceiveDtoRq(dtoRq);
			receive(receiveDtoRq);
		} else if (checkPartnerBank(dtoRq.getBankNameTo())) {
			TransferDtoRq transferDto = converterComponent.convertOperationPayRqToTransferDtoRq(dtoRq);
			bankPartnerIntegrationService.transferMoneyToBankPartner(transferDto);
		} else {
			throw new BankNotFoundException("Неверно заполнен банк получатель!");
		}
		return pay(dtoRq);
	}

	private boolean checkCurrentBank(String bankName) {
		return Objects.equals(bankName, bankProperties.getName());
	}

	private boolean checkPartnerBank(String bankName) {
		return Objects.equals(bankName, bankProperties.getPartnerBankName());
	}
}
