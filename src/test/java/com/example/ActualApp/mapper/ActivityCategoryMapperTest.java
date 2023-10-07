package com.example.ActualApp.mapper;

import com.example.ActualApp.controller.dto.ActivityCategoryDto;
import com.example.ActualApp.controller.dto.NewActivityCategoryDto;
import com.example.ActualApp.repository.entity.ActivityCategory;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;



class ActivityCategoryMapperTest {
    private final ActivityCategoryMapper activityCategoryMapper = new ActivityCategoryMapper();

    @Test
    void shouldMapActivityCategoryToDto() {
        //Given
        ActivityCategory activityCategory = Instancio.create(ActivityCategory.class);

        //When
        ActivityCategoryDto actual = activityCategoryMapper.mapActivityCategoryToDto(activityCategory);

        //Then
        Assertions.assertThat(actual.id()).isEqualTo(activityCategory.getId());
        Assertions.assertThat(actual.name()).isEqualTo(activityCategory.getName());
        Assertions.assertThat(actual.priority()).isEqualTo(activityCategory.getPriority());
        Assertions.assertThat(actual.activities()).isEqualTo(activityCategory.getActivities());
    }

    @Test
    void shouldMapNewActivityCategoryDtoToEntity() {
        //Given
        NewActivityCategoryDto newActivityCategoryDto = Instancio.create(NewActivityCategoryDto.class);

        //When
        ActivityCategory actual = activityCategoryMapper.mapNewActivityCategoryDtoToCategory(newActivityCategoryDto);

        //Then
        Assertions.assertThat(actual.getName()).isEqualTo(newActivityCategoryDto.name());
    }
}