package org.javaacademy.onlinebankingapp.entity;

import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
public class User {
    private UUID uuid;
    @NonNull
    private final String ownerFullName;
    @NonNull
    private String phoneNumber;
}
