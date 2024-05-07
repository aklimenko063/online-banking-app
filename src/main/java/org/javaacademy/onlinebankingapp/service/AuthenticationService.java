package org.javaacademy.onlinebankingapp.service;

import lombok.Data;
import org.javaacademy.onlinebankingapp.repository.AuthenticationRepositoryInterface;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.UUID;

@Service
@Data
public class AuthenticationService {
    private final static String EMPTY_PINCODE = "####";
    private final AuthenticationRepositoryInterface authenticationRepository;

    public void addNewUserData(UUID uuid, String pinCode) {
        authenticationRepository.addNewUserData(uuid, pinCode);
    }

    public boolean authenticateUser(UUID uuid, String pinCode) {
        String pin = authenticationRepository.getData(uuid).orElseGet(() -> EMPTY_PINCODE);
        return Objects.equals(pinCode, pin);
    }
}
