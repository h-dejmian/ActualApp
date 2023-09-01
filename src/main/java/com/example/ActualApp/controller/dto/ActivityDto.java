package com.example.ActualApp.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;
import java.util.UUID;

public record ActivityDto (
         UUID id,
         String description,
         long timeSpentInMinutes,
         LocalDate date,
         boolean completed
) {
}
