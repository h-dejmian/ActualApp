package com.example.ActualApp.service;

import com.example.ActualApp.auth.user.UserRepository;
import com.example.ActualApp.controller.dto.CategoryDto;
import com.example.ActualApp.mapper.CategoryMapper;
import com.example.ActualApp.repository.CategoryRepository;
import com.example.ActualApp.repository.entity.Category;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


class CategoryServiceTest {

    private final CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final CategoryMapper categoryMapper = Mockito.mock(CategoryMapper.class);
    private final CategoryService categoryService = new CategoryService(categoryRepository, userRepository, categoryMapper);

    @Test
    void shouldReturnAllCategories() {
        //Given
        Category testCategory = Instancio.create(Category.class);
        Category testCategory2 = Instancio.create(Category.class);
        CategoryDto testCategoryDto1 = categoryMapper.mapActivityCategoryToDto(testCategory);
        CategoryDto testCategoryDto2 = categoryMapper.mapActivityCategoryToDto(testCategory2);

        List<CategoryDto> expectedlist = new ArrayList<>();
        expectedlist.add(testCategoryDto1);
        expectedlist.add(testCategoryDto2);

        Mockito.when(categoryRepository.findAll()).thenReturn(List.of(testCategory, testCategory2));

        //When
        List<CategoryDto> categories = categoryService.getAllCategories();

        //Then
        Assertions.assertThat(categories.size()).isEqualTo(expectedlist.size());
    }

    @Test
    void shouldReturnCategoryWithGivenId() {
        //Given
        Category category = Instancio.create(Category.class);
        Mockito.when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        CategoryDto categoryDto = Instancio.create(CategoryDto.class);
        Mockito.when(categoryMapper.mapActivityCategoryToDto(category)).thenReturn(categoryDto);

        //When
        CategoryDto actual = categoryService.getCategoryById(category.getId());

        //Then
        Assertions.assertThat(actual).isEqualTo(categoryDto);
    }

    @Test
    void shouldThrowResponseStatusExceptionWhenCategoryNotFound() {
        //Given
        UUID testId = UUID.fromString("bc1ec7c3-1516-40da-a1fc-da475a95c081");
        Mockito.when(categoryRepository.findById(testId)).thenReturn(Optional.empty());

        //When
        Throwable throwable = Assertions.catchThrowable(() -> categoryService.getCategoryById(testId));

        //Then
        Assertions.assertThat(throwable).isInstanceOf(ResponseStatusException.class);
    }
}