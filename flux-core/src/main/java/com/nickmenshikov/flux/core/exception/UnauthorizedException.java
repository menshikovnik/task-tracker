package com.nickmenshikov.flux.core.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends AppException {
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, "unauthorized", message);
    }
}
