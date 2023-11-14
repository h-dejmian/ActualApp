package com.example.ActualApp.mapper;

import com.example.ActualApp.auth.user.User;
import com.example.ActualApp.controller.dto.ActivityDto;
import com.example.ActualApp.controller.dto.NameAndCountDto;
import com.example.ActualApp.controller.dto.NewActivityDto;
import com.example.ActualApp.controller.dto.PlannedActivityDto;
import com.example.ActualApp.repository.entity.Activity;
import com.example.ActualApp.repository.entity.Category;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ActivityMapperTest {

    private final ActivityMapper activityMapper = new ActivityMapper();

    @Test
    void shouldMapNewActivityDtoToEntity() {
        //Given
        NewActivityDto newActivityDto = Instancio.create(NewActivityDto.class);
        Category category = Instancio.create(Category.class);
        User user = Instancio.create(User.class);

        //When
        Activity actual = activityMapper.mapNewActivityDtoToEntity(newActivityDto, category, user);

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

    @Test
    void shouldMapPlannedActivityToDto() {
        //Given
        Activity activity = Instancio.create(Activity.class);

        //When
        PlannedActivityDto actual = activityMapper.mapPlannedActivityToDto(activity);

        //Then
        Assertions.assertThat(actual.id()).isEqualTo(activity.getId());
        Assertions.assertThat(actual.description()).isEqualTo(activity.getDescription());
        Assertions.assertThat(actual.timeSpentInMinutes()).isEqualTo(activity.getTimeSpentInMinutes());
        Assertions.assertThat(actual.date()).isEqualTo(activity.getDate());
        Assertions.assertThat(actual.completed()).isEqualTo(activity.isCompleted());
        Assertions.assertThat(actual.categoryName()).isEqualTo(activity.getCategory().getName());
        Assertions.assertThat(actual.startTime()).isEqualTo(activity.getStartTime());
        Assertions.assertThat(actual.endTime()).isEqualTo(activity.getEndTime());
    }

    @Test
    void shouldMapGivenListToNameAndCountDto() {
        //Given
        List<List<Object>> list = new ArrayList<>();
        List<Object> nameAndCountList1 = new ArrayList<>();
        nameAndCountList1.add("testCategory");
        nameAndCountList1.add((long) 50);
        list.add(nameAndCountList1);

        //When
        List<NameAndCountDto> actual = activityMapper.mapToNameAndCountDto(list);

        //Then
        Assertions.assertThat(actual.get(0).description()).isEqualTo("testCategory");
        Assertions.assertThat(actual.get(0).timeSpentInMinutes()).isEqualTo(50);
    }
}