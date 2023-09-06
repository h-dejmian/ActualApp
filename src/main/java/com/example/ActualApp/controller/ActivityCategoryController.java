package com.example.ActualApp.controller;

import com.example.ActualApp.controller.dto.*;
import com.example.ActualApp.service.ActivityCategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
public class ActivityCategoryController {

    private final ActivityCategoryService categoryService;

    public ActivityCategoryController(ActivityCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<ActivityCategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ActivityCategoryDto getCategoryById(@PathVariable UUID id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping(params = {"categoriesWithTimeSpent"})
    public List<NameAndCountDto> getCategoriesWithTimeSpent() {
        return categoryService.getCategoriesWithTimeSpent();
    }

    @PostMapping
    public NewActivityCategoryDto saveNewCategory(@Valid @RequestBody NewActivityCategoryDto category) {
        return categoryService.saveNewCategory(category);
    }

    @PatchMapping(value = "/{id}", params = {"description"})
    public ActivityCategoryDto updateDescription(@PathVariable UUID id, @RequestBody DescriptionDto description) {
        return categoryService.updateDescription(id, description);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }

}
