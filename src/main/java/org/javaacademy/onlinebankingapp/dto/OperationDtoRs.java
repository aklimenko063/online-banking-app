package org.javaacademy.onlinebankingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.javaacademy.onlinebankingapp.enums.TypeOperation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class OperationDtoRs {
    private UUID uuid;
    private LocalDateTime creationDateTime;
    private String accountNumber;
    private TypeOperation typeOperation;
    private BigDecimal sum;
    private String purposeOfPayment;
}
