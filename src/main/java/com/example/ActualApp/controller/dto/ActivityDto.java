package com.example.ActualApp.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ActivityDto (
         UUID id,
         String description,
         long timeSpentInMinutes,
         LocalDate date,
         boolean completed,
         String categoryName,
         LocalTime startTime,
         LocalTime endTime
) {
}
