package org.javaacademy.onlinebankingapp.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebankingapp.dto.UserAuthenticateDtoRq;
import org.javaacademy.onlinebankingapp.dto.UserRegistrationDtoRq;
import org.javaacademy.onlinebankingapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserControllerV1 {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> getPinCode(@RequestBody UserRegistrationDtoRq dtoRq) {
        try {
            return status(ACCEPTED).body(userService.userRegistration(dtoRq));
        } catch (Exception e) {
            return status(FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<String> getToken(@RequestBody UserAuthenticateDtoRq dtoRq) {
        try {
            return status(ACCEPTED).body(userService.authenticateUser(dtoRq));
        } catch (Exception e) {
            return status(UNAUTHORIZED).body(e.getMessage());
        }
    }
}
