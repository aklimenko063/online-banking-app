package org.javaacademy.onlinebankingapp.dto;

import lombok.Data;

@Data
public class UserAuthenticateDtoRq {
    private String phoneNumber;
    private String pinCode;
}

