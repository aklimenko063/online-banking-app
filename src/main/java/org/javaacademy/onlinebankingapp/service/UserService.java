package org.javaacademy.onlinebankingapp.service;

import lombok.Data;
import org.javaacademy.onlinebankingapp.dto.UserAuthenticateDtoRq;
import org.javaacademy.onlinebankingapp.dto.UserDtoRs;
import org.javaacademy.onlinebankingapp.dto.UserRegistrationDtoRq;
import org.javaacademy.onlinebankingapp.entity.User;
import org.javaacademy.onlinebankingapp.exception.AuthenticateException;
import org.javaacademy.onlinebankingapp.exception.RegistrationException;
import org.javaacademy.onlinebankingapp.repository.UserRepositoryInterface;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@Data
public class UserService {
    private final UserRepositoryInterface userRepository;
    private final AuthenticationService authenticationService;
    private final SecurityService securityService;
    private final ConverterService converterService;

    public String userRegistration(UserRegistrationDtoRq userRegistrationDtoRq) {
        if (userRepository.searchUserByPhoneNumber(userRegistrationDtoRq.getPhoneNumber())) {
            throw new RegistrationException("Пользователь с таким телефоном уже зарегистрирован в системе!");
        }
        User addedUser = userRepository.addUser(converterService.convertUserRegistrationDtoRqToUserEntity(userRegistrationDtoRq));
        String pinCode = securityService.generatePin();
        authenticationService.addNewUserData(addedUser.getUuid(), pinCode);
        return pinCode;
    }

    public String authenticateUser(UserAuthenticateDtoRq userAuthenticateDtoRq) {
        User user = userRepository.getUserByPhoneNumber(userAuthenticateDtoRq.getPhoneNumber())
                .orElseThrow(() -> new AuthenticateException("Пользователь не зарегистрирован в системе!"));
        if (!authenticationService.authenticateUser(user.getUuid(), userAuthenticateDtoRq.getPinCode())) {
            throw new AuthenticateException("Предоставленный пин-код не совпадает!");
        }
        String token = securityService.generateToken(user.getUuid());
        return token;
    }

    public UserDtoRs getUserByToken(String token) {
        UUID uuid = securityService.getUserUuidByToken(token);
        User user = userRepository.getUserByUuid(uuid)
                .orElseThrow(() -> new AuthenticateException("Пользователь по токену не найден!"));
        return converterService.convertUserEntityToUserDtoRs(user);
    }
}
