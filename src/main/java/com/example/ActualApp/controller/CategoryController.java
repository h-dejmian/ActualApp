package com.example.ActualApp.controller;

import com.example.ActualApp.controller.dto.*;
import com.example.ActualApp.repository.entity.CategoryType;
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
    @GetMapping(params={"type", "userId"})
    public List<CategoryDto> getAllCategories(@RequestParam String type, @RequestParam UUID userId) {
        if(type == null || userId == null) {
            return categoryService.getAllCategories();
        }
        CategoryType categoryType = Enum.valueOf(CategoryType.class, type.toUpperCase());
        return categoryService.getAllCategoriesByTypeAndUserId(categoryType, userId);
    }

    @RolesAllowed({ADMIN, USER})
    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable UUID id) {
        return categoryService.getCategoryById(id);
    }

    @RolesAllowed({ADMIN, USER})
    @GetMapping(params = {"withTimeSpent", "month", "userId"})
    public List<NameAndCountDto> getCategoriesWithTimeSpent(@RequestParam Integer month, @RequestParam UUID userId) {
        if(month != null) {
            return categoryService.getCategoriesWithTimeByMonth(month, userId);
        }
        else {
            return categoryService.getCategoriesWithTimeSpent();
        }
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
