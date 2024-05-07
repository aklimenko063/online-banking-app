package org.javaacademy.onlinebankingapp.repository;

import org.javaacademy.onlinebankingapp.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryInterface {

    boolean searchUserByPhoneNumber(String phoneNumber);

    User addUser(User newUser);

    Optional<User> getUserByPhoneNumber(String phoneNumber);

    Optional<User> getUserByUuid(UUID uuid);
}
