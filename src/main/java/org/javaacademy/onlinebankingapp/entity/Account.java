package org.javaacademy.onlinebankingapp.entity;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public class Account {
    @NonNull
    private String number;
    @NonNull
    private User ownerAccount;
    private BigDecimal balance = BigDecimal.ZERO.setScale(2);
}
