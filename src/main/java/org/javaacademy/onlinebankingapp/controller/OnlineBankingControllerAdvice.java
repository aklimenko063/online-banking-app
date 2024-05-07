package org.javaacademy.onlinebankingapp.controller;

import org.javaacademy.onlinebankingapp.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OnlineBankingControllerAdvice {

	@ExceptionHandler(AccountBalanceLessZeroException.class)
	public ResponseEntity<String> handleAccountBalanceLessZeroException(Exception e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(e.getMessage());
	}

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<String> handleAccountNotFoundException(Exception e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(e.getMessage());
	}

	@ExceptionHandler(AuthenticateException.class)
	public ResponseEntity<String> handleAuthenticateException(Exception e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(e.getMessage());
	}

	@ExceptionHandler(OperationException.class)
	public ResponseEntity<String> handleOperationException(Exception e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(e.getMessage());
	}

	@ExceptionHandler(RegistrationException.class)
	public ResponseEntity<String> handleRegistrationException(Exception e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("На сайте наблюдаются проблемы, мы знаем о ней и решаем проблему!");
	}
}
