package org.javaacademy.onlinebankingapp.dto;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public class TransferDtoRq {
    private String bankName;
    @NonNull
    private BigDecimal sum;
    @NonNull
    private String purposeOfPayment;
    @NonNull
    private String numberAccountTo;
}
