package org.javaacademy.onlinebankingapp;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.javaacademy.onlinebankingapp.dto.UserAuthenticateDtoRq;
import org.javaacademy.onlinebankingapp.dto.UserRegistrationDtoRq;
import org.javaacademy.onlinebankingapp.entity.User;
import org.javaacademy.onlinebankingapp.repository.impl.AuthenticationRepository;
import org.javaacademy.onlinebankingapp.repository.impl.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("MoneyBank")
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class UserRestControllerTest {
    private final static String BASE_URL = "/api/v1/user";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Test
    void userRegistrationSuccessCode202() {
        userRegistration();
        Assertions.assertTrue(userRepository.searchUserByPhoneNumber("+79276532094"));
    }

    @Test
    void userRegistrationSuccessCode403() {
        userRegistration();
        UserRegistrationDtoRq userRegistrationDtoRq = new UserRegistrationDtoRq();
        userRegistrationDtoRq.setFullName("KlimenkoAV");
        userRegistrationDtoRq.setPhoneNumber("+79276532094");
        RestAssured
                .given()
                .body(userRegistrationDtoRq)
                .contentType(ContentType.JSON)
                .log().all()
                .port(8082)
                .post(BASE_URL + "/signup")
                .then()
                .log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void authenticateUserSuccessCode202() {
        userRegistration();
        User user = userRepository.getUserByPhoneNumber("+79276532094").get();
        String pin = authenticationRepository.getData(user.getUuid()).get();
        UserAuthenticateDtoRq userAuthenticateDtoRq = new UserAuthenticateDtoRq();
        userAuthenticateDtoRq.setPhoneNumber("+79276532094");
        userAuthenticateDtoRq.setPinCode(pin);
        RestAssured
                .given()
                .body(userAuthenticateDtoRq)
                .contentType(ContentType.JSON)
                .log().all()
                .port(8082)
                .post(BASE_URL + "/auth")
                .then()
                .log().all()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

    @Test
    void authenticateUserSuccessCode401() {
        UserAuthenticateDtoRq userAuthenticateDtoRq = new UserAuthenticateDtoRq();
        userAuthenticateDtoRq.setPhoneNumber("+79276532094");
        userAuthenticateDtoRq.setPinCode("####");
        RestAssured
                .given()
                .body(userAuthenticateDtoRq)
                .contentType(ContentType.JSON)
                .log().all()
                .port(8082)
                .post(BASE_URL + "/auth")
                .then()
                .log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    private void userRegistration() {
        UserRegistrationDtoRq userRegistrationDtoRq = new UserRegistrationDtoRq();
        userRegistrationDtoRq.setFullName("KlimenkoAV");
        userRegistrationDtoRq.setPhoneNumber("+79276532094");
        RestAssured
                .given()
                .body(userRegistrationDtoRq)
                .contentType(ContentType.JSON)
                .log().all()
                .port(8082)
                .post(BASE_URL + "/signup")
                .then()
                .log().all()
                .statusCode(HttpStatus.ACCEPTED.value());
    }
}
