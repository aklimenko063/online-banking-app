package org.javaacademy.onlinebankingapp.repository.impl;

import org.javaacademy.onlinebankingapp.entity.User;
import org.javaacademy.onlinebankingapp.repository.UserRepositoryInterface;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository implements UserRepositoryInterface {
    private final Map<String, User> userRepositoryDb = new HashMap<>();

    @Override
    public boolean searchUserByPhoneNumber(String phoneNumber) {
        return userRepositoryDb.containsKey(phoneNumber);
    }

    @Override
    public User addUser(User newUser) {
        newUser.setUuid(UUID.randomUUID());
        userRepositoryDb.put(newUser.getPhoneNumber(), newUser);
        return newUser;
    }

    @Override
    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return Optional.ofNullable(userRepositoryDb.get(phoneNumber));
    }

    @Override
    public Optional<User> getUserByUuid(UUID uuid) {
        return userRepositoryDb.values()
                .stream()
                .filter(e -> e.getUuid().equals(uuid)).findFirst();
    }
}
