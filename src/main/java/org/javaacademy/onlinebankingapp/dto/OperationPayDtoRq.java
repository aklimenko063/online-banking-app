package org.javaacademy.onlinebankingapp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OperationPayDtoRq {
    private String token;
    private BigDecimal sum;
    private String accountNumberFrom;
    private String accountNumberTo;
    private String purposeOfPayment;
}
