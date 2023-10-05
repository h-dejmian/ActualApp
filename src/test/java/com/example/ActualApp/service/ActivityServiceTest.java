package com.example.ActualApp.service;

import com.example.ActualApp.controller.dto.ActivityDto;
import com.example.ActualApp.mapper.ActivityMapper;
import com.example.ActualApp.repository.ActivityCategoryRepository;
import com.example.ActualApp.repository.ActivityRepository;
import com.example.ActualApp.repository.entity.Activity;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ActivityServiceTest {

    private final ActivityMapper activityMapper = Mockito.mock(ActivityMapper.class);
    private ActivityRepository activityRepository = Mockito.mock(ActivityRepository.class);
    private final ActivityCategoryRepository categoryRepository = Mockito.mock(ActivityCategoryRepository.class);
    private final ActivityService activityService = new ActivityService(activityRepository, activityMapper, categoryRepository);

    @Test
    void shouldReturnAllActivities() {
        //Given
        Activity testActivity1 = Instancio.create(Activity.class);
        Activity testActivity2 = Instancio.create(Activity.class);
        ActivityDto testActivityDto1 = activityMapper.mapActivityToDto(testActivity1);
        ActivityDto testActivityDto2 = activityMapper.mapActivityToDto(testActivity2);

        List<ActivityDto> expectedList = new ArrayList<>();
        expectedList.add(testActivityDto1);
        expectedList.add(testActivityDto2);

        Mockito.when(activityRepository.findAll()).thenReturn(List.of(testActivity1, testActivity2));

        //When
        List<ActivityDto> activities = activityService.getAllActivities();

        //Then
        Assertions.assertThat(activities.size()).isEqualTo(expectedList.size());
    }

    @Test
    void shouldReturnActivityWithGivenId() {
        //Given
        Activity activity = Instancio.create(Activity.class);
        Mockito.when(activityRepository.findById(activity.getId())).thenReturn(Optional.of(activity));

        ActivityDto activityDto = Instancio.create(ActivityDto.class);
        Mockito.when(activityMapper.mapActivityToDto(activity)).thenReturn(activityDto);

        //When
        ActivityDto actual = activityService.getActivityById(activity.getId());

        //Then
        Assertions.assertThat(actual).isEqualTo(activityDto);
    }

    @Test
    void shouldThrowResponseStatusExceptionWhenActivityNotFound() {
        //Given
        UUID testId = UUID.fromString("bc1ec7c3-1516-40da-a1fc-da475a95c081");
        Mockito.when(activityRepository.findById(testId)).thenReturn(Optional.empty());

        //When
        Throwable throwable = Assertions.catchThrowable(() -> activityService.getActivityById(testId));

        //Then
        Assertions.assertThat(throwable).isInstanceOf(ResponseStatusException.class);
    }
}