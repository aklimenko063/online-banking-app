package org.javaacademy.onlinebankingapp.repository.impl;

import org.javaacademy.onlinebankingapp.repository.AuthenticationRepositoryInterface;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AuthenticationRepository implements AuthenticationRepositoryInterface {
    private final Map<UUID, String> authenticationRepositoryBd = new HashMap<>();

    @Override
    public void addNewUserData(UUID uuid, String pinCode) {
        authenticationRepositoryBd.put(uuid, pinCode);
    }

    @Override
    public Optional<String> getData(UUID uuid) {
        return Optional.ofNullable(authenticationRepositoryBd.get(uuid));
    }
}
