package org.javaacademy.onlinebankingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.javaacademy.onlinebankingapp.enums.TypeOperation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OperationDtoRq {
    private String token;
    private BigDecimal sum;
    private String accountNumber;
    private String purposeOfPayment;
}
