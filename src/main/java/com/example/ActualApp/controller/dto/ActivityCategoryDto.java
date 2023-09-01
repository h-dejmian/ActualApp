package com.example.ActualApp.controller.dto;

import com.example.ActualApp.repository.entity.Activity;

import java.util.List;
import java.util.UUID;

public record ActivityCategoryDto(
        UUID id,
        String name,
        int priority,
        List<Activity> activities
) {
}
