package org.javaacademy.onlinebankingapp.controller.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User controller", description = "Методы по работе с пользователем")
public class UserRestController {
    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "Регистрация. Получение пин-кода пользователя")
    public ResponseEntity<String> getPinCode(@RequestBody UserRegistrationDtoRq dtoRq) {
        try {
            return status(ACCEPTED).body(userService.userRegistration(dtoRq));
        } catch (Exception e) {
            return status(FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/auth")
    @Operation(summary = "Авторизация. Получение токена пользователя")
    public ResponseEntity<String> getToken(@RequestBody UserAuthenticateDtoRq dtoRq) {
        try {
            return status(ACCEPTED).header("token", userService.authenticateUser(dtoRq)).build();
        } catch (Exception e) {
            return status(UNAUTHORIZED).body(e.getMessage());
        }
    }
}
