package com.example.ActualApp.controller;

import com.example.ActualApp.controller.dto.*;
import com.example.ActualApp.service.CategoryService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.ActualApp.auth.config.SpringSecurityConfig.ADMIN;
import static com.example.ActualApp.auth.config.SpringSecurityConfig.USER;

@CrossOrigin(originPatterns = "http://localhost:3000/*")
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RolesAllowed({ADMIN, USER})
    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @RolesAllowed({ADMIN, USER})
    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable UUID id) {
        return categoryService.getCategoryById(id);
    }

    @RolesAllowed({ADMIN, USER})
    @GetMapping(params = {"categoriesWithTimeSpent"})
    public List<NameAndCountDto> getCategoriesWithTimeSpent() {
        return categoryService.getCategoriesWithTimeSpent();
    }

    @RolesAllowed({ADMIN, USER})
    @PostMapping
    public CategoryDto saveNewCategory(@Valid @RequestBody NewCategoryDto category) {
        return categoryService.saveNewCategory(category);
    }

    @RolesAllowed({ADMIN, USER})
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }

    @RolesAllowed({ADMIN, USER})
    @PutMapping("/{id}")
    public CategoryDto updateCategory(@PathVariable UUID id, @RequestBody NewCategoryDto activityCategoryDto) {
        return categoryService.updateCategory(id, activityCategoryDto);
    }

}
