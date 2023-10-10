package com.example.ActualApp.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationDto(
        @NotBlank(message = "username cannot be empty")
        String login,
        @Size(min = 5, message = "Password must have at least 5 characters")
        String password
) {
}