package org.javaacademy.onlinebankingapp.repository;

import java.util.Optional;
import java.util.UUID;

public interface AuthenticationRepositoryInterface {

    void addNewUserData(UUID uuid, String pinCode);

    Optional<String> getData(UUID uuid);
}
