package com.example.ActualApp.controller.dto;

import com.example.ActualApp.repository.entity.Activity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.util.List;
import java.util.UUID;

public record ActivityCategoryDto(
        UUID id,
        String name,
        int priority,
        List<Activity> activities
) {
}
