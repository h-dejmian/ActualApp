package com.example.ActualApp.service;

import com.example.ActualApp.mapper.ActivityCategoryMapper;
import com.example.ActualApp.repository.ActivityCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class ActivityCategoryService {

    private final ActivityCategoryRepository categoryRepository;
    private final ActivityCategoryMapper categoryMapper;

    public ActivityCategoryService(ActivityCategoryRepository categoryRepository, ActivityCategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }


}
