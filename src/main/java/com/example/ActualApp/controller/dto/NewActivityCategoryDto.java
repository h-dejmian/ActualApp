package com.example.ActualApp.controller.dto;

import com.example.ActualApp.repository.entity.Activity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record NewActivityCategoryDto(
        @NotBlank
        String name,
        @Range(min = 1, max = 7, message = "Priority should be number between 1 and 7")
        int priority,
        List<Activity> activities
) {
}
