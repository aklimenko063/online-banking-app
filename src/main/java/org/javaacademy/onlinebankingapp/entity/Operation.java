package org.javaacademy.onlinebankingapp.entity;

import lombok.Data;
import lombok.NonNull;
import org.javaacademy.onlinebankingapp.enums.TypeOperation;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Operation {
    private UUID uuid;
    @NonNull
    private LocalDateTime creationDateTime;
    @NonNull
    private String accountNumber;
    private TypeOperation typeOperation;
    @NonNull
    private BigDecimal sum;
    @NonNull
    private String purposeOfPayment;
}
