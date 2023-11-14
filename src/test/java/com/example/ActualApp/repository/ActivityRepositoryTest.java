package com.example.ActualApp.repository;

import com.example.ActualApp.auth.user.User;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
}