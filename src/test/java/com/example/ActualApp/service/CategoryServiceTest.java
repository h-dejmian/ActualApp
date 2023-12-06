package com.example.ActualApp.service;

import com.example.ActualApp.auth.user.User;
import com.example.ActualApp.auth.user.UserRepository;
import com.example.ActualApp.controller.dto.CategoryDto;
import com.example.ActualApp.controller.dto.NameAndCountDto;
import com.example.ActualApp.controller.dto.NewCategoryDto;
import com.example.ActualApp.mapper.CategoryMapper;
import com.example.ActualApp.repository.CategoryRepository;
import com.example.ActualApp.repository.entity.Category;
import com.example.ActualApp.repository.entity.CategoryType;
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

    @Test
    void shouldReturnCategoriesWithTimeSpent() {
        //Given
        List<List<Object>> nameAndCount = new ArrayList<>();
        List<Object> entry = new ArrayList<>();
        entry.add("Test Category");
        entry.add(120L);

        nameAndCount.add(entry);

        List<NameAndCountDto> expected = new ArrayList<>();
        expected.add(new NameAndCountDto("Test Category", 120L));

        Mockito.when(categoryMapper.mapToNameAndCountDto(nameAndCount)).thenReturn(expected);
        Mockito.when(categoryRepository.getCategoriesWithTimeSpent()).thenReturn(nameAndCount);

        //When
        List<NameAndCountDto> actual = categoryService.getCategoriesWithTimeSpent();

        //Then
        Assertions.assertThat(actual.get(0).description()).isEqualTo("Test Category");
        Assertions.assertThat(actual.get(0).timeSpentInMinutes()).isEqualTo(120L);
    }

    @Test
    void shouldReturnNewCategory() {
        //Given
        User user = Instancio.create(User.class);
        Category category = Instancio.create(Category.class);
        CategoryDto categoryDto = new CategoryDto(category.getId(),
                                                  category.getName(),
                                                  category.getPriority(),
                                                    category.getActivities().size(),
                                     120L);

        NewCategoryDto newCategoryDto = new NewCategoryDto(category.getName(),
                                                           category.getPriority(),
                                                           user.getId(),
                                                           CategoryType.REGULAR);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(categoryRepository.save(category)).thenReturn(category);
        Mockito.when(categoryMapper.mapNewActivityCategoryDtoToCategory(newCategoryDto, user)).thenReturn(category);
        Mockito.when(categoryMapper.mapActivityCategoryToDto(category)).thenReturn(categoryDto);

        //When
        CategoryDto actual = categoryService.saveNewCategory(newCategoryDto);

        //Then
        Assertions.assertThat(actual.name()).isEqualTo(category.getName());
        Assertions.assertThat(actual.id()).isEqualTo(category.getId());
        Assertions.assertThat(actual.priority()).isEqualTo(category.getPriority());
        Assertions.assertThat(actual.activitiesNumber()).isEqualTo(category.getActivities().size());
        Assertions.assertThat(actual.timeSpentInMinutes()).isEqualTo(120L);
    }

    @Test
    void shouldUpdateCategory() {
        //Given
        Category category = Instancio.create(Category.class);
        User user = Instancio.create(User.class);

        NewCategoryDto newCategoryDto = new NewCategoryDto("Updated name",
                4,
                user.getId(),
                CategoryType.REGULAR);

        CategoryDto categoryDto = new CategoryDto(category.getId(),
                "Updated name",
                4,
                category.getActivities().size(),
                120L);

        Mockito.when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        Mockito.when(categoryMapper.mapActivityCategoryToDto(category)).thenReturn(categoryDto);


        //When
        CategoryDto actual = categoryService.updateCategory(category.getId(), newCategoryDto);

        //Then
        Assertions.assertThat(actual.name()).isEqualTo(category.getName());
        Assertions.assertThat(actual.id()).isEqualTo(category.getId());
        Assertions.assertThat(actual.priority()).isEqualTo(category.getPriority());
        Assertions.assertThat(actual.activitiesNumber()).isEqualTo(category.getActivities().size());
        Assertions.assertThat(actual.timeSpentInMinutes()).isEqualTo(120L);
    }
}