package com.nickmenshikov.tasktracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginRequest(
        @NotBlank(message = "Username must not be empty")
        String username,

        @NotBlank(message = "Password must not be empty")
        String password) {
}
