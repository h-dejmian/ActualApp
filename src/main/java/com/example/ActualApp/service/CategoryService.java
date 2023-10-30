package com.example.ActualApp.service;

import com.example.ActualApp.auth.user.User;
import com.example.ActualApp.auth.user.UserRepository;
import com.example.ActualApp.controller.dto.*;
import com.example.ActualApp.mapper.ActivityCategoryMapper;
import com.example.ActualApp.repository.CategoryRepository;
import com.example.ActualApp.repository.entity.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ActivityCategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository, ActivityCategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<ActivityCategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryRepository.findAll().stream()
                .map(categoryMapper::mapActivityCategoryToDto)
                .toList();
    }

    public ActivityCategoryDto getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::mapActivityCategoryToDto)
                .orElseThrow();
    }

    public List<NameAndCountDto> getCategoriesWithTimeSpent() {
        return categoryMapper.mapToNameAndCountDto(categoryRepository.getCategoriesWithTimeSpent());
    }

    public ActivityCategoryDto saveNewCategory(NewActivityCategoryDto newCategory) {
        User user = userRepository.findById(newCategory.user_Id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Category category = categoryRepository.save(categoryMapper.mapNewActivityCategoryDtoToCategory(newCategory, user));
        return categoryMapper.mapActivityCategoryToDto(category);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }

    public ActivityCategoryDto updateCategory(UUID id, NewActivityCategoryDto activityCategoryDto) {
        Category categoryToUpdate = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        categoryToUpdate.setName(activityCategoryDto.name());
        categoryToUpdate.setPriority(activityCategoryDto.priority());

        categoryRepository.save(categoryToUpdate);

        return categoryMapper.mapActivityCategoryToDto(categoryToUpdate);
    }
}
