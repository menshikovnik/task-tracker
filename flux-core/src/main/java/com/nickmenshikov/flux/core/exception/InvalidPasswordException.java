package com.nickmenshikov.flux.core.exception;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends AppException {
    public InvalidPasswordException(String message) {
        super(HttpStatus.UNAUTHORIZED, "invalid_password", message);
    }
}
