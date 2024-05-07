package org.javaacademy.onlinebankingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.javaacademy.onlinebankingapp.entity.User;

@Data
@AllArgsConstructor
@Builder
public class UserDtoRs {
    private String ownerFullName;
    private String phoneNumber;
    private User user;
}
