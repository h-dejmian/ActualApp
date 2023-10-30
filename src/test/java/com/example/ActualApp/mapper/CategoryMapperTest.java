package com.example.ActualApp.mapper;

import com.example.ActualApp.controller.dto.CategoryDto;
import com.example.ActualApp.controller.dto.NewCategoryDto;
import com.example.ActualApp.repository.entity.Category;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;



class CategoryMapperTest {
    private final CategoryMapper categoryMapper = new CategoryMapper();

    @Test
    void shouldMapActivityCategoryToDto() {
        //Given
        Category category = Instancio.create(Category.class);

        //When
        CategoryDto actual = categoryMapper.mapActivityCategoryToDto(category);

        //Then
        Assertions.assertThat(actual.id()).isEqualTo(category.getId());
        Assertions.assertThat(actual.name()).isEqualTo(category.getName());
        Assertions.assertThat(actual.priority()).isEqualTo(category.getPriority());
        Assertions.assertThat(actual.activitiesNumber()).isEqualTo(category.getActivities().size());
    }

    @Test
    void shouldMapNewActivityCategoryDtoToEntity() {
        //Given
        NewCategoryDto newCategoryDto = Instancio.create(NewCategoryDto.class);

        //When
        Category actual = categoryMapper.mapNewActivityCategoryDtoToCategory(newCategoryDto);

        //Then
        Assertions.assertThat(actual.getName()).isEqualTo(newCategoryDto.name());
    }
}