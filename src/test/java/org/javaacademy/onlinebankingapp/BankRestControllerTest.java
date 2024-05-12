package org.javaacademy.onlinebankingapp;

import io.restassured.RestAssured;
import org.javaacademy.onlinebankingapp.service.BankService;
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
public class BankRestControllerTest {
    private final static String BASE_URL = "/api/v1";
    @Autowired
    private BankService bankService;

    @Test
    void getBankInfoSuccessCode200() {
        RestAssured
                .given()
                .log().all()
                .port(8082)
                .get(BASE_URL + "/bank-info")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
