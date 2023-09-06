package com.example.ActualApp.service;

import com.example.ActualApp.controller.dto.ActivityCategoryDto;
import com.example.ActualApp.controller.dto.NameAndCountDto;
import com.example.ActualApp.controller.dto.NewActivityCategoryDto;
import com.example.ActualApp.mapper.ActivityCategoryMapper;
import com.example.ActualApp.repository.ActivityCategoryRepository;
import org.springframework.stereotype.Service;

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
}
