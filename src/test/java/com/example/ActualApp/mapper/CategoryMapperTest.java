package com.example.ActualApp.mapper;

import com.example.ActualApp.controller.dto.ActivityCategoryDto;
import com.example.ActualApp.controller.dto.NewActivityCategoryDto;
import com.example.ActualApp.repository.entity.Category;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;



class CategoryMapperTest {
    private final ActivityCategoryMapper activityCategoryMapper = new ActivityCategoryMapper();

    @Test
    void shouldMapActivityCategoryToDto() {
        //Given
        Category category = Instancio.create(Category.class);

        //When
        ActivityCategoryDto actual = activityCategoryMapper.mapActivityCategoryToDto(category);

        //Then
        Assertions.assertThat(actual.id()).isEqualTo(category.getId());
        Assertions.assertThat(actual.name()).isEqualTo(category.getName());
        Assertions.assertThat(actual.priority()).isEqualTo(category.getPriority());
        Assertions.assertThat(actual.activitiesNumber()).isEqualTo(category.getActivities().size());
    }

    @Test
    void shouldMapNewActivityCategoryDtoToEntity() {
        //Given
        NewActivityCategoryDto newActivityCategoryDto = Instancio.create(NewActivityCategoryDto.class);

        //When
        Category actual = activityCategoryMapper.mapNewActivityCategoryDtoToCategory(newActivityCategoryDto);

        //Then
        Assertions.assertThat(actual.getName()).isEqualTo(newActivityCategoryDto.name());
    }
}