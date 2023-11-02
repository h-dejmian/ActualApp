package com.example.ActualApp.controller.dto;

import com.example.ActualApp.repository.entity.CategoryType;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

public record NewCategoryDto(
        @NotBlank
        String name,
        @Range(min = 1, max = 7, message = "Priority should be number between 1 and 7")
        int priority,
        UUID userId,
        CategoryType categoryType
) {
}
