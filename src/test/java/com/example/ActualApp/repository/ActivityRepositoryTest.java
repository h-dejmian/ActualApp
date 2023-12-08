package com.example.ActualApp.repository;

import com.example.ActualApp.repository.entity.Activity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@DataJpaTest
class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    void shouldReturnActivitiesByTime() {
        //When
        List<List<Object>> actual = activityRepository.getActivitiesByTimeAndUserId(UUID.fromString("2f85b8fe-2888-4afb-b022-3d34ee604192"));
        List<Object> innerList =  actual.get(0);

        //Then
        Assertions.assertThat(innerList.get(0)).isEqualTo("Seriale");
        Assertions.assertThat(innerList.get(1)).isEqualTo(240L);
    }

    @Test
    void shouldReturnMostOftenNotCompletedActivity() {
        //When
        List<List<Object>> actual = activityRepository.getMostOftenNotCompletedActivity();
        List<Object> innerList =  actual.get(0);

        //Then
        Assertions.assertThat(innerList.get(0)).isEqualTo("TestCompletedFalse");
        Assertions.assertThat(innerList.get(1)).isEqualTo(1L);
    }

    @Test
    void shouldReturnRegularActivitiesByDateAndUserId() {
        //When
        List<Activity> actual = activityRepository.findAllRegularByDateAndUserId(LocalDate.parse("2023-09-04"), UUID.fromString("2f85b8fe-2888-4afb-b022-3d34ee604192"));

        //Then
        Assertions.assertThat(actual.size()).isEqualTo(2);
    }

    @Test
    void shouldReturnActivitiesWithTimeRange() {
        //When
        List<Activity> actual = activityRepository.findAllWithTimeRange();

        //Then
        Assertions.assertThat(actual.size()).isEqualTo(1);
        Assertions.assertThat(actual.get(0).getStartTime()).isNotNull();
        Assertions.assertThat(actual.get(0).getEndTime()).isNotNull();
    }

    @Test
    void shouldReturnActivitiesWithTimeByUserIdAndMonth() {
        //Given
        UUID userId = UUID.fromString("2f85b8fe-2888-4afb-b022-3d34ee604192");
        int month = 9;

        //When
        List<List<Object>> actual = activityRepository.getActivitiesWithTimeByUserIdAndMonth(userId, month);
        List<Object> innerList =  actual.get(0);

        //Then
        Assertions.assertThat(innerList.get(0)).isEqualTo("Seriale");
        Assertions.assertThat(innerList.get(1)).isEqualTo(240L);
    }
}