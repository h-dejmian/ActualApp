package com.example.ActualApp.mapper;

import com.example.ActualApp.controller.dto.ActivityDto;
import com.example.ActualApp.controller.dto.NewActivityDto;
import com.example.ActualApp.repository.entity.Activity;
import com.example.ActualApp.repository.entity.ActivityCategory;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityMapperTest {

    private final ActivityMapper activityMapper = new ActivityMapper();

    @Test
    void shouldMapNewActivityDtoToEntity() {
        //Given
        NewActivityDto newActivityDto = Instancio.create(NewActivityDto.class);
        ActivityCategory category = Instancio.create(ActivityCategory.class);

        //When
        Activity actual = activityMapper.mapNewActivityDtoToEntity(newActivityDto, category);

        //Then
        Assertions.assertThat(actual.getDescription()).isEqualTo(newActivityDto.description());
        Assertions.assertThat(actual.getTimeSpentInMinutes()).isEqualTo(newActivityDto.timeSpentInMinutes());
        Assertions.assertThat(actual.getDate()).isEqualTo(newActivityDto.date());
        Assertions.assertThat(actual.isCompleted()).isEqualTo(newActivityDto.completed());
        Assertions.assertThat(actual.getCategory().getName()).isEqualTo(category.getName());
    }

    @Test
    void shouldMapActivityToDto() {
        //Given
        Activity activity = Instancio.create(Activity.class);

        //When
        ActivityDto actual = activityMapper.mapActivityToDto(activity);

        //Then
        Assertions.assertThat(actual.id()).isEqualTo(activity.getId());
        Assertions.assertThat(actual.description()).isEqualTo(activity.getDescription());
        Assertions.assertThat(actual.timeSpentInMinutes()).isEqualTo(activity.getTimeSpentInMinutes());
        Assertions.assertThat(actual.date()).isEqualTo(activity.getDate());
        Assertions.assertThat(actual.completed()).isEqualTo(activity.isCompleted());
        Assertions.assertThat(actual.categoryName()).isEqualTo(activity.getCategory().getName());
    }
}