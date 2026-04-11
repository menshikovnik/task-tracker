package com.nickmenshikov.flux.core.exception;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyTakenException extends AppException {
    public UsernameAlreadyTakenException(String message) {
        super(HttpStatus.CONFLICT, "username_already_taken", message);
    }
}
