package com.nickmenshikov.flux.core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegistrationRequest (
        @NotBlank(message = "Username must not be empty")
        String username,

        @NotBlank(message = "Email must not be empty")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password must not be empty")
        String password,

        @NotBlank(message = "Confirm password must not be empty")
        String confirmPassword) {
}
