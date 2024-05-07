package org.javaacademy.onlinebankingapp.service;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class SecurityService {
    private final static String TOKEN_PREFIX = "online";
    private final static String TOKEN_POSTFIX = "token";
    private final static String TOKEN_SAMPLE = "online%stoken";
    private final static int RANDOM_LENGTH_LIMIT = 10000;

    public String generatePin() {
        return String.format("%04d", new Random().nextInt(RANDOM_LENGTH_LIMIT));
    }

    public String generateToken(UUID uuid) {
        return String.format(TOKEN_SAMPLE, uuid.toString());
    }

    public UUID getUserUuidByToken(String token) {
        String substringPrefix = token.substring(TOKEN_PREFIX.length());
        int indexPostfix = substringPrefix.indexOf(TOKEN_POSTFIX);
        String substringPostfix = substringPrefix.substring(0, indexPostfix);
        return UUID.fromString(substringPostfix);
    }
}
