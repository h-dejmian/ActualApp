package com.example.ActualApp.controller.dto;

import com.example.ActualApp.repository.entity.ActivityCategory;

import java.time.LocalDate;
import java.util.UUID;

public record ActivityDto (
         UUID id,
         String description,
         long timeSpentInMinutes,
         LocalDate date,
         boolean completed,
         String category
) {
}
