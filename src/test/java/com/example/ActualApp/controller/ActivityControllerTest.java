package com.example.ActualApp.controller;

import com.example.ActualApp.controller.dto.ActivityDto;
import com.example.ActualApp.controller.dto.NewActivityDto;
import com.example.ActualApp.service.ActivityService;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@WebMvcTest(controllers = ActivityController.class)
class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActivityService activityService;

    @Test
    void shouldReturnActivityByGivenId() throws Exception {
        //Given
        UUID id = UUID.randomUUID();
        ActivityDto activityDto = Instancio.create(ActivityDto.class);
        Mockito.when(activityService.getActivityById(id)).thenReturn(activityDto);

        //When
        var response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/activities/" + id));

        //Then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(activityDto.id().toString()))
                .andExpect(jsonPath("$.description").value(activityDto.description()))
                .andExpect(jsonPath("$.timeSpentInMinutes").value(activityDto.timeSpentInMinutes()))
                .andExpect(jsonPath("$.date").value(activityDto.date().toString()))
                .andExpect(jsonPath("$.completed").value(activityDto.completed()))
                .andExpect(jsonPath("$.categoryName").value(activityDto.categoryName()));
    }

    @Test
    void shouldReturnAllActivities() throws Exception {
        //Given
        List<ActivityDto> activities = Instancio.ofList(ActivityDto.class)
                                                .size(1)
                                                .create();
        ActivityDto activityDto = activities.get(0);

        Mockito.when(activityService.getAllActivities()).thenReturn(activities);

        //When
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/activities"));

        //Then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(activityDto.id().toString()))
                .andExpect(jsonPath("$[0].description").value(activityDto.description()))
                .andExpect(jsonPath("$[0].timeSpentInMinutes").value(activityDto.timeSpentInMinutes()))
                .andExpect(jsonPath("$[0].date").value(activityDto.date().toString()))
                .andExpect(jsonPath("$[0].completed").value(activityDto.completed()))
                .andExpect(jsonPath("$[0].categoryName").value(activityDto.categoryName()));
    }

    @Test
    void shouldReturnNewActivity() throws Exception {
        //Given
        NewActivityDto newActivityDto = new NewActivityDto("Test description", 120,
                LocalDate.of(2023, 10, 10), true, "Test Category");
        ActivityDto activityDto = new ActivityDto(UUID.randomUUID(), "Test description", 120,
                LocalDate.of(2023, 10, 10), true, "Test Category");

        Mockito.when(activityService.saveNewActivity(newActivityDto)).thenReturn(activityDto);

        //When
        var response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/activities")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                      "description" : "Test description",
                                        "timeSpentInMinutes" : 120,
                                        "date" : "2023-10-10",
                                        "completed" : true,
                                        "categoryName" : "Test Category"
                                }
                                """));

        //Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(activityDto.id().toString()))
                .andExpect(jsonPath("$.description").value(activityDto.description()))
                .andExpect(jsonPath("$.timeSpentInMinutes").value(activityDto.timeSpentInMinutes()))
                .andExpect(jsonPath("$.date").value(activityDto.date().toString()))
                .andExpect(jsonPath("$.completed").value(activityDto.completed()))
                .andExpect(jsonPath("$.categoryName").value(activityDto.categoryName()));
    }

    @Test
    void shouldToggleCompleted() throws Exception {
        //Given
        ActivityDto activityDto = Instancio.of(ActivityDto.class).generate(Select.field(ActivityDto::completed))
        Mockito.when(activityService.toggleCompleted(activityDto.id())).thenReturn(activityDto);

        //When
        var response = mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/activities/" + activityDto.id()));

        //Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(!activityDto.completed()));
    }
}