package com.nickmenshikov.flux.core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        String username,

        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password must not be empty")
        String password) {
}
