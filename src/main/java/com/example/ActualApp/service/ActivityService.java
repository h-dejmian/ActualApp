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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ActivityMapper activityMapper;


    public ActivityService(ActivityRepository activityRepository,
                           ActivityMapper activityMapper, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List<ActivityDto> getAllActivities() {
        return activityRepository.findAll().stream()
                .map(activityMapper::mapActivityToDto)
                .toList();
    }

    public List<ActivityDto> getAllActivities(Pageable pageable) {
        return activityRepository.findAllBy(pageable).stream()
                .map(activityMapper::mapActivityToDto)
                .toList();
    }

    public List<PlannedActivityDto> getActivitiesWithTimeRangeByDate(LocalDate date, UUID userId) {
        return activityRepository.findAllWithTimeRangeByDate(date, userId).stream()
                .map(activityMapper::mapPlannedActivityToDto)
                .toList();
    }

    public ActivityDto getActivityById(UUID id) {
        return activityRepository.findById(id)
                .map(activityMapper::mapActivityToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<ActivityDto> getActivitiesByCategory(UUID id) {
        return categoryRepository.findById(id)
                .map(Category::getActivities)
                .map(activities -> activities.stream()
                        .map(activityMapper::mapActivityToDto).toList())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<ActivityDto> getActivitiesByDate(String date, UUID user_id) {
        List<Activity> activities = activityRepository.findAllRegularByDateAndUserId(LocalDate.parse(date), user_id);
        return activities.stream()
                .map(activityMapper::mapActivityToDto)
                .toList();
    }

    public List<NameAndCountDto> getActivitiesByTimeSpent(UUID userId) {
        return activityMapper.mapToNameAndCountDto(activityRepository.getActivitiesByTimeAndUserId(userId));
    }

    public List<NameAndCountDto> getMostOftenNotCompletedActivity() {
        return activityMapper.mapToNameAndCountDto(activityRepository.getMostOftenNotCompletedActivity());
    }

    public ActivityDto saveNewActivity(NewActivityDto newActivity, String type) {
        CategoryType categoryType = Enum.valueOf(CategoryType.class, type.toUpperCase());

        Category category = categoryRepository.findByNameAndCategoryType(newActivity.categoryName(), categoryType)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        User user = userRepository.findById(newActivity.user_Id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Activity activity = activityRepository.save(activityMapper.mapNewActivityDtoToEntity(newActivity, category, user));
        return activityMapper.mapActivityToDto(activity);
    }

    public ActivityDto toggleCompleted(UUID id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        activity.setCompleted(!activity.isCompleted());
        activityRepository.save(activity);
        return activityMapper.mapActivityToDto(activity);
    }

    public void deleteActivity(UUID id) {
        activityRepository.deleteById(id);
    }


    public ActivityDto updateActivity(UUID id, ActivityDto activity) {
        Activity activityToUpdate = activityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Category category = categoryRepository.findByName(activity.categoryName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        activityToUpdate.setDescription(activity.description());
        activityToUpdate.setTimeSpentInMinutes(activity.timeSpentInMinutes());
        activityToUpdate.setCategory(category);
        activityToUpdate.setStartTime(activity.startTime());
        activityToUpdate.setEndTime(activity.endTime());

        return activityMapper.mapActivityToDto(activityRepository.save(activityToUpdate));
    }
}
