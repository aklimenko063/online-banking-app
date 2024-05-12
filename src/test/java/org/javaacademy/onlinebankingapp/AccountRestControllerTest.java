package org.javaacademy.onlinebankingapp;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.javaacademy.onlinebankingapp.dto.AccountDtoRq;
import org.javaacademy.onlinebankingapp.dto.UserAuthenticateDtoRq;
import org.javaacademy.onlinebankingapp.dto.UserRegistrationDtoRq;
import org.javaacademy.onlinebankingapp.entity.User;
import org.javaacademy.onlinebankingapp.repository.impl.AccountRepository;
import org.javaacademy.onlinebankingapp.repository.impl.AuthenticationRepository;
import org.javaacademy.onlinebankingapp.repository.impl.UserRepository;
import org.javaacademy.onlinebankingapp.service.SecurityService;
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
public class AccountRestControllerTest {
    private final static String BASE_URL = "/api/v1/account";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationRepository authenticationRepository;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void addNewAccountSuccessCode201() {
        userRegistration();
        User user = userRepository.getUserByPhoneNumber("+79276532094").get();
        authenticateUser(user);
        String token = securityService.generateToken(user.getUuid());
        AccountDtoRq accountDtoRq = new AccountDtoRq();
        accountDtoRq.setToken(token);
        RestAssured
                .given()
                .body(accountDtoRq)
                .contentType(ContentType.JSON)
                .log().all()
                .port(8082)
                .post("/api/v1/account")
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value());
        int expected = 1;
        Assertions.assertEquals(expected, accountRepository.getAccountRepositoryBd().size());
    }

    @Test
    void addNewAccountSuccessCode403() {
        userRegistration();
        User user = userRepository.getUserByPhoneNumber("+79276532094").get();
        authenticateUser(user);
        AccountDtoRq accountDtoRq = new AccountDtoRq();
        accountDtoRq.setToken("");
        RestAssured
                .given()
                .body(accountDtoRq)
                .contentType(ContentType.JSON)
                .log().all()
                .port(8082)
                .post("/api/v1/account")
                .then()
                .log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void getAllUserAccountsSuccessCode200() {
        userRegistration();
        User user = userRepository.getUserByPhoneNumber("+79276532094").get();
        authenticateUser(user);
        String token = securityService.generateToken(user.getUuid());
        RestAssured
                .given()
                .queryParam("token", token)
                .log().all()
                .port(8082)
                .get("/api/v1/account")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());
        int expected = 0;
        Assertions.assertEquals(expected, accountRepository.getAccountRepositoryBd().size());
    }

    @Test
    void getAllUserAccountsSuccessCode403() {
        userRegistration();
        User user = userRepository.getUserByPhoneNumber("+79276532094").get();
        authenticateUser(user);
        String token = "";
        RestAssured
                .given()
                .queryParam("token", token)
                .log().all()
                .port(8082)
                .get("/api/v1/account")
                .then()
                .log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void getAccountBalanceSuccessCode200() {
        addNewAccount();
        User user = userRepository.getUserByPhoneNumber("+79276532094").get();
        authenticateUser(user);
        String token = securityService.generateToken(user.getUuid());
        RestAssured
                .given()
                .header("token", token)
                .pathParam("numberAccount", "4081808200000001")
                .when()
                .log().all()
                .port(8082)
                .get("/api/v1/account/{numberAccount}")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void getAccountBalanceSuccessTokenCode403() {
        addNewAccount();
        User user = userRepository.getUserByPhoneNumber("+79276532094").get();
        authenticateUser(user);
        String token = "";
        RestAssured
                .given()
                .header("token", token)
                .pathParam("numberAccount", "4081808200000001")
                .when()
                .log().all()
                .port(8082)
                .get("/api/v1/account/{numberAccount}")
                .then()
                .log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void getAccountBalanceSuccessAccountCode403() {
        addNewAccount();
        User user = userRepository.getUserByPhoneNumber("+79276532094").get();
        authenticateUser(user);
        String token = securityService.generateToken(user.getUuid());
        RestAssured
                .given()
                .header("token", token)
                .pathParam("numberAccount", "4081808200000002")
                .when()
                .log().all()
                .port(8082)
                .get("/api/v1/account/{numberAccount}")
                .then()
                .log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
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
                .post("/api/v1/user/signup")
                .then()
                .log().all()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

    private void authenticateUser(User user) {
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
                .post("/api/v1/user/auth")
                .then()
                .log().all()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

    void addNewAccount() {
        userRegistration();
        User user = userRepository.getUserByPhoneNumber("+79276532094").get();
        authenticateUser(user);
        String token = securityService.generateToken(user.getUuid());
        AccountDtoRq accountDtoRq = new AccountDtoRq();
        accountDtoRq.setToken(token);
        RestAssured
                .given()
                .body(accountDtoRq)
                .contentType(ContentType.JSON)
                .log().all()
                .port(8082)
                .post("/api/v1/account")
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value());
    }
}
