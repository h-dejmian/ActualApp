package com.example.ActualApp.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    void shouldReturnActivitiesByTime() {
        //When
        List<List<Object>> actual = activityRepository.getActivitiesByTimeAndUserId();
        List<Object> innerList =  actual.get(0);

        //Then
        Assertions.assertThat(actual.size()).isEqualTo(2);
        Assertions.assertThat(innerList.get(1)).isEqualTo(240L);
    }

    @Test
    void shouldReturnMostOftenNotCompletedActivity() {
        //When
        List<List<Object>> actual = activityRepository.getMostOftenNotCompletedActivity();
        List<Object> innerList =  actual.get(0);

        //Then
        Assertions.assertThat(actual.size()).isEqualTo(1);
        Assertions.assertThat(innerList.get(0)).isEqualTo("TestCompletedFalse");
    }
}