package com.example.ActualApp.service;

import com.example.ActualApp.controller.dto.*;
import com.example.ActualApp.mapper.ActivityCategoryMapper;
import com.example.ActualApp.repository.ActivityCategoryRepository;
import com.example.ActualApp.repository.entity.Activity;
import com.example.ActualApp.repository.entity.ActivityCategory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityCategoryService {

    private final ActivityCategoryRepository categoryRepository;
    private final ActivityCategoryMapper categoryMapper;

    public ActivityCategoryService(ActivityCategoryRepository categoryRepository, ActivityCategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<ActivityCategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::mapActivityCategoryToDto)
                .toList();
    }

    public ActivityCategoryDto getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::mapActivityCategoryToDto)
                .orElseThrow();
    }

    public ActivityCategoryDto getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .map(categoryMapper::mapActivityCategoryToDto)
                .orElseThrow();
    }

    public List<NameAndCountDto> getCategoriesWithTimeSpent() {
        return categoryMapper.mapToNameAndCountDto(categoryRepository.getCategoriesWithTimeSpent());
    }

    public NewActivityCategoryDto saveNewCategory(NewActivityCategoryDto category) {
        categoryRepository.save(categoryMapper.mapNewActivityCategoryDtoToCategory(category));
        return category;
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }

    public ActivityCategoryDto updateDescription(UUID id, DescriptionDto description) {
        ActivityCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        category.setName(description.description());
        categoryRepository.save(category);
        return categoryMapper.mapActivityCategoryToDto(category);
    }
}
