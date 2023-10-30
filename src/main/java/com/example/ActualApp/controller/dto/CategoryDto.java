package com.example.ActualApp.controller.dto;

import java.util.UUID;

public record CategoryDto(
        UUID id,
        String name,
        int priority,
        int activitiesNumber,
        long timeSpentInMinutes
) {
}
