package com.example.ActualApp.auth;

import java.util.UUID;

public record RegisteredUserDto(
        UUID id,
        String login
) {

}
