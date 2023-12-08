package com.example.ActualApp.service;

import com.example.ActualApp.auth.user.User;
import com.example.ActualApp.auth.user.UserRepository;
import com.example.ActualApp.controller.dto.ActivityDto;
import com.example.ActualApp.controller.dto.NameAndCountDto;
import com.example.ActualApp.controller.dto.NewActivityDto;
import com.example.ActualApp.controller.dto.PlannedActivityDto;
import com.example.ActualApp.mapper.ActivityMapper;
import com.example.ActualApp.repository.CategoryRepository;
import com.example.ActualApp.repository.ActivityRepository;
import com.example.ActualApp.repository.entity.Activity;
import com.example.ActualApp.repository.entity.Category;
import com.example.ActualApp.repository.entity.CategoryType;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

import static org.instancio.Select.field;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ActivityServiceTest {

    private final ActivityMapper activityMapper = Mockito.mock(ActivityMapper.class);
    private final ActivityRepository activityRepository = Mockito.mock(ActivityRepository.class);
    private final CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final ActivityService activityService = new ActivityService(activityRepository, activityMapper, categoryRepository, userRepository);

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

    @Test
    void shouldReturnActivitiesWithTimeRangeByDate() {
        //Given
        UUID userId = UUID.fromString("bc1ec7c3-1516-40da-a1fc-da475a95c081");
        LocalDate date = LocalDate.of(2023, 5, 22);
        Activity activityWithTimeRange = Instancio.create(Activity.class);

        List<Activity> activities = new ArrayList<>();
        activities.add(activityWithTimeRange);

        PlannedActivityDto plannedActivityDto = new PlannedActivityDto(activityWithTimeRange.getId(),
                activityWithTimeRange.getDescription(),
                activityWithTimeRange.getTimeSpentInMinutes(),
                activityWithTimeRange.getDate(),
                activityWithTimeRange.isCompleted(),
                activityWithTimeRange.getCategory().getName(),
                activityWithTimeRange.getStartTime(),
                activityWithTimeRange.getEndTime());

        Mockito.when(activityRepository.findAllWithTimeRangeByDate(date, userId)).thenReturn(activities);
        Mockito.when(activityMapper.mapPlannedActivityToDto(activityWithTimeRange)).thenReturn(plannedActivityDto);

        //When
        List<PlannedActivityDto> actual = activityService.getActivitiesWithTimeRangeByDate(date, userId);

        //Then
        Assertions.assertThat(actual.size()).isEqualTo(1);
        Assertions.assertThat(actual.get(0).startTime()).isNotNull();
        Assertions.assertThat(actual.get(0).endTime()).isNotNull();
    }

    @Test
    void shouldReturnActivitiesByCategory() {
        //Given
        List<Activity> activities = new ArrayList<>();
        Category category = Instancio.of(Category.class)
                .set(field(Category.class, "activities"), activities)
                .create();
        Activity activity1 = Instancio.of(Activity.class)
                .set(field(Activity.class, "category"), category)
                .create();
        Activity activity2 = Instancio.of(Activity.class)
                .set(field(Activity.class, "category"), category)
                .create();

        activities.add(activity1);
        activities.add(activity2);

        ActivityDto activityDto1 = new ActivityDto(activity1.getId(),
                activity1.getDescription(),
                activity1.getTimeSpentInMinutes(),
                activity1.getDate(),
                activity1.isCompleted(),
                activity1.getCategory().getName(),
                activity1.getStartTime(),
                activity1.getEndTime());

        ActivityDto activityDto2 = new ActivityDto(activity1.getId(),
                activity2.getDescription(),
                activity2.getTimeSpentInMinutes(),
                activity2.getDate(),
                activity2.isCompleted(),
                activity2.getCategory().getName(),
                activity2.getStartTime(),
                activity2.getEndTime());

        Mockito.when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        Mockito.when(activityMapper.mapActivityToDto(activity1)).thenReturn(activityDto1);
        Mockito.when(activityMapper.mapActivityToDto(activity2)).thenReturn(activityDto2);

        //When
        List<ActivityDto> actual = activityService.getActivitiesByCategory(category.getId());

        //Then
        Assertions.assertThat(actual.size()).isEqualTo(2);
        Assertions.assertThat(actual.get(0).categoryName()).isEqualTo(category.getName());
        Assertions.assertThat(actual.get(1).categoryName()).isEqualTo(category.getName());
    }

    @Test
    void shouldReturnActivitiesByDate() {
        //Given
        String date = "2023-07-07";
        UUID userId = UUID.fromString("bc1ec7c3-1516-40da-a1fc-da475a95c081");

        Activity activity1 = Instancio.of(Activity.class)
                .set(field(Activity.class, "date"), LocalDate.parse(date))
                .create();
        Activity activity2 = Instancio.of(Activity.class)
                .set(field(Activity.class, "date"), LocalDate.parse(date))
                .create();
        List<Activity> activities = new ArrayList<>();
        activities.add(activity1);
        activities.add(activity2);

        Mockito.when(activityRepository.findAllRegularByDateAndUserId(LocalDate.parse(date), userId)).thenReturn(activities);

        //When
        List<ActivityDto> actual = activityService.getActivitiesByDate(date, userId);

        //Then
        Assertions.assertThat(actual.size()).isEqualTo(2);
    }

    @Test
    void shouldReturnActivitiesByTimeSpent() {
        //Given
        List<List<Object>> nameAndCount = new ArrayList<>();
        List<Object> entry = new ArrayList<>();
        entry.add("Test Activity");
        entry.add(120L);

        UUID userId = UUID.fromString("bc1ec7c3-1516-40da-a1fc-da475a95c081");

        List<NameAndCountDto> expected = new ArrayList<>();
        expected.add(new NameAndCountDto("Test Activity", 120L));

        nameAndCount.add(entry);

        Mockito.when(activityMapper.mapToNameAndCountDto(nameAndCount)).thenReturn(expected);
        Mockito.when(activityRepository.getActivitiesByTimeAndUserId(userId)).thenReturn(nameAndCount);


        //When
        List<NameAndCountDto> actual = activityService.getActivitiesByTimeSpent(userId);

        //Then
        Assertions.assertThat(actual.size()).isEqualTo(expected.size());
        Assertions.assertThat(actual.get(0).description()).isEqualTo(expected.get(0).description());
        Assertions.assertThat(actual.get(0).timeSpentInMinutes()).isEqualTo(expected.get(0).timeSpentInMinutes());
    }

    @Test
    void shouldReturnActivitiesByTimeSpentInMonth() {
        //Given
        List<List<Object>> nameAndCount = new ArrayList<>();
        List<Object> entry = new ArrayList<>();
        entry.add("Test Activity");
        entry.add(120L);

        int testMonth = 10;
        UUID userId = UUID.fromString("bc1ec7c3-1516-40da-a1fc-da475a95c081");

        List<NameAndCountDto> expected = new ArrayList<>();
        expected.add(new NameAndCountDto("Test Activity", 120L));

        nameAndCount.add(entry);

        Mockito.when(activityMapper.mapToNameAndCountDto(nameAndCount)).thenReturn(expected);
        Mockito.when(activityRepository.getActivitiesWithTimeByUserIdAndMonth(userId, testMonth)).thenReturn(nameAndCount);

        //When
        List<NameAndCountDto> actual = activityService.getActivitiesByTimeSpentInMonth(userId, testMonth);

        //Then
        Assertions.assertThat(actual.size()).isEqualTo(expected.size());
        Assertions.assertThat(actual.get(0).description()).isEqualTo(expected.get(0).description());
        Assertions.assertThat(actual.get(0).timeSpentInMinutes()).isEqualTo(expected.get(0).timeSpentInMinutes());
    }

    @Test
    void shouldReturnMostOftenNotCompletedActivity() {
        List<List<Object>> nameAndCount = new ArrayList<>();
        List<Object> entry = new ArrayList<>();
        entry.add("Test Activity");
        entry.add(5);

        List<NameAndCountDto> expected = new ArrayList<>();
        expected.add(new NameAndCountDto("Test Activity", 5));

        nameAndCount.add(entry);

        Mockito.when(activityMapper.mapToNameAndCountDto(nameAndCount)).thenReturn(expected);
        Mockito.when(activityRepository.getMostOftenNotCompletedActivity()).thenReturn(nameAndCount);

        //When
        List<NameAndCountDto> actual = activityService.getMostOftenNotCompletedActivity();

        //Then
        Assertions.assertThat(actual.size()).isEqualTo(expected.size());
        Assertions.assertThat(actual.get(0).description()).isEqualTo(expected.get(0).description());
        Assertions.assertThat(actual.get(0).timeSpentInMinutes()).isEqualTo(expected.get(0).timeSpentInMinutes());
    }

    @Test
    void shouldSaveAndReturnNewActivity() {
        //Given
        Activity activity = Instancio.create(Activity.class);

        NewActivityDto newActivityDto = new NewActivityDto(activity.getDescription(),
                activity.getTimeSpentInMinutes(),
                activity.getDate(),
                activity.isCompleted(),
                activity.getCategory().getName(),
                activity.getUser().getId(),
                activity.getStartTime(),
                activity.getEndTime());

        ActivityDto activityDto = new ActivityDto(activity.getId(),
                activity.getDescription(),
                activity.getTimeSpentInMinutes(),
                activity.getDate(),
                activity.isCompleted(),
                activity.getCategory().getName(),
                activity.getStartTime(),
                activity.getEndTime());

        User user = Instancio.of(User.class)
                .set(field(User.class, "id"), newActivityDto.user_Id())
                .create();

        String type = "REGULAR";
        Category category = Instancio.of(Category.class)
                .set(field(Category.class, "name"), newActivityDto.categoryName())
                .set(field(Category.class, "categoryType"), CategoryType.REGULAR)
                .create();

        Mockito.when(categoryRepository.findByNameAndCategoryType(newActivityDto.categoryName(), CategoryType.REGULAR))
                .thenReturn(Optional.of(category));
        Mockito.when(userRepository.findById(newActivityDto.user_Id())).thenReturn(Optional.of(user));
        Mockito.when(activityRepository.save(activity)).thenReturn(activity);
        Mockito.when(activityMapper.mapActivityToDto(activity)).thenReturn(activityDto);
        Mockito.when(activityMapper.mapNewActivityDtoToEntity(newActivityDto, category, user)).thenReturn(activity);

        //When
        ActivityDto actual = activityService.saveNewActivity(newActivityDto, type);

        //Then
        Assertions.assertThat(actual.description()).isEqualTo(activity.getDescription());
        Assertions.assertThat(actual.timeSpentInMinutes()).isEqualTo(activity.getTimeSpentInMinutes());
        Assertions.assertThat(actual.completed()).isEqualTo(activity.isCompleted());
        Assertions.assertThat(actual.date()).isEqualTo(activity.getDate());
        Assertions.assertThat(actual.categoryName()).isEqualTo(activity.getCategory().getName());
        Assertions.assertThat(actual.startTime()).isEqualTo(activity.getStartTime());
        Assertions.assertThat(actual.endTime()).isEqualTo(activity.getEndTime());
    }

    @Test
    void shouldUpdateActivity() {
        //Given
        Activity activity = Instancio.create(Activity.class);

        Category category = Instancio.of(Category.class)
                .set(field(Category.class, "name"), activity.getCategory().getName())
                .set(field(Category.class, "categoryType"), CategoryType.REGULAR)
                .create();

        Category updatedCategory = Instancio.create(Category.class);

        ActivityDto activityDto = new ActivityDto(activity.getId(),
                                                "updated",
                                                120L,
                                                activity.getDate(),
                                                activity.isCompleted(),
                                                updatedCategory.getName(),
                                                activity.getStartTime(),
                                                activity.getEndTime());


        Mockito.when(activityRepository.findById(activity.getId())).thenReturn(Optional.of(activity));
        Mockito.when(categoryRepository.findByNameAndCategoryType(activityDto.categoryName(), CategoryType.REGULAR)).thenReturn(Optional.of(category));
        Mockito.when(activityRepository.save(activity)).thenReturn(activity);
        Mockito.when(activityMapper.mapActivityToDto(activity)).thenReturn(activityDto);

        //When
        ActivityDto actual = activityService.updateActivity(activity.getId(), activityDto, "REGULAR");

        //Then
        Assertions.assertThat(actual.description()).isEqualTo("updated");
        Assertions.assertThat(actual.timeSpentInMinutes()).isEqualTo(120L);
        Assertions.assertThat(actual.categoryName()).isEqualTo(updatedCategory.getName());
    }

    @Test
    void shouldDeleteActivity() {
        //Given
        UUID activityId = UUID.randomUUID();

        //When
        activityService.deleteActivity(activityId);

        //Then
        verify(activityRepository, times(1)).deleteById(activityId);
    }
}