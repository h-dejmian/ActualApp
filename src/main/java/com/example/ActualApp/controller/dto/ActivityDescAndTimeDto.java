package com.example.ActualApp.controller.dto;

public record ActivityDescAndTimeDto(
        String description,
        long timeSpentInMinutes
) {
}
