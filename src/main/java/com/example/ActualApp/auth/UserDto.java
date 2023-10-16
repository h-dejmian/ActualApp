package com.example.ActualApp.auth;

import java.util.UUID;

public record UserDto(
        UUID id,
        String login
) {

}
