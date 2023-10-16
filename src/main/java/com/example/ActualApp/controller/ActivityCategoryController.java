package com.example.ActualApp.controller;

import com.example.ActualApp.controller.dto.*;
import com.example.ActualApp.service.ActivityCategoryService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.ActualApp.auth.config.SpringSecurityConfig.ADMIN;

@CrossOrigin(originPatterns = "http://localhost:3000/*")
@RestController
@RequestMapping("/api/v1/categories")
public class ActivityCategoryController {

    private final ActivityCategoryService categoryService;

    public ActivityCategoryController(ActivityCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RolesAllowed(ADMIN)
    @GetMapping
    public List<ActivityCategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @RolesAllowed(ADMIN)
    @GetMapping("/{id}")
    public ActivityCategoryDto getCategoryById(@PathVariable UUID id) {
        return categoryService.getCategoryById(id);
    }

    @RolesAllowed(ADMIN)
    @GetMapping(params = {"categoriesWithTimeSpent"})
    public List<NameAndCountDto> getCategoriesWithTimeSpent() {
        return categoryService.getCategoriesWithTimeSpent();
    }

    @RolesAllowed(ADMIN)
    @PostMapping
    public NewActivityCategoryDto saveNewCategory(@Valid @RequestBody NewActivityCategoryDto category) {
        return categoryService.saveNewCategory(category);
    }

    @RolesAllowed(ADMIN)
    @PatchMapping(value = "/{id}", params = {"description"})
    public ActivityCategoryDto updateDescription(@PathVariable UUID id, @RequestBody DescriptionDto description) {
        return categoryService.updateDescription(id, description);
    }

    @RolesAllowed(ADMIN)
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }

}
