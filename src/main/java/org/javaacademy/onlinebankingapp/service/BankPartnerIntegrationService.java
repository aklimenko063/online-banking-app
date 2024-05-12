package org.javaacademy.onlinebankingapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebankingapp.config.BankProperties;
import org.javaacademy.onlinebankingapp.dto.OperationDtoRs;
import org.javaacademy.onlinebankingapp.dto.OperationReceiveDtoRq;
import org.javaacademy.onlinebankingapp.dto.TransferDtoRq;
import org.javaacademy.onlinebankingapp.exception.ServiceIntegrationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BankPartnerIntegrationService {
    private final static String API_URL = "/api/v1";
    private final static String POSTFIX_URL = "/operations/receive";
    private final RestTemplate restTemplate;
    private final BankProperties bankProperties;
    private final ConverterComponent converterComponent;
    private final ObjectMapper objectMapper;

    public OperationDtoRs transferMoneyToBankPartner(TransferDtoRq dtoRq) {
        String baseUrl = String.format("%s:%s%s",
                bankProperties.getPartnerUrl(),
                bankProperties.getPartnerPort(),
                API_URL);
        OperationReceiveDtoRq receiveDtoRq = converterComponent.convertTransferDtoRqToOperationReceiveRq(dtoRq);

        String jsonBody;
        try {
            jsonBody = objectMapper.writeValueAsString(receiveDtoRq);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        RequestEntity<String> requestBankReceive = RequestEntity
                .post(baseUrl + POSTFIX_URL)
                .headers(headers)
                .body(jsonBody);
        ResponseEntity<OperationDtoRs> responseBankReceive;
        try {
            responseBankReceive = restTemplate.exchange(requestBankReceive, OperationDtoRs.class);
        } catch (RuntimeException e) {
            throw new ServiceIntegrationException("Проблемы с интеграцией!");
        }
        return responseBankReceive.getBody();
    }
}
