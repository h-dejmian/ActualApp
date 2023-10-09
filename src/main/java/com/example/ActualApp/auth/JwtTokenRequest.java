package com.example.ActualApp.auth;

import jakarta.validation.constraints.NotBlank;

public record JwtTokenRequest(
        @NotBlank
        String userName,
        @NotBlank
        String password
) {
}
