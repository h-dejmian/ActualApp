package com.example.ActualApp.controller;

import com.example.ActualApp.controller.dto.ActivityDescAndTimeDto;
import com.example.ActualApp.controller.dto.ActivityDto;
import com.example.ActualApp.controller.dto.NewActivityDto;
import com.example.ActualApp.repository.ActivityCategoryRepository;
import com.example.ActualApp.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {
    private final ActivityService activityService;
    private final ActivityCategoryRepository categoryRepository;

    public ActivityController(ActivityService activityService, ActivityCategoryRepository categoryRepository) {
        this.activityService = activityService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<ActivityDto> getAllActivities() {
        return activityService.getAllActivities();
    }

    @GetMapping(params = {"groupByTime"})
    public List<ActivityDescAndTimeDto> getActivitiesByTime() {
         return activityService.getActivitiesByTimeSpent();
    }

    @GetMapping(params = {"date"})
    public List<ActivityDto> getActivitiesByDate(@RequestParam String date) {
        return activityService.getActivitiesByDate(date);
    }

    @GetMapping("/{id}")
    public ActivityDto getActivityById(@PathVariable UUID id) {
        return activityService.getActivityById(id);
    }

    @GetMapping(params = {"sort"})
    public List<ActivityDto> getAllActivitiesSorted(Pageable pageable) {
        return activityService.getAllActivities(pageable);
    }

    @GetMapping(params = {"mostOftenNotCompleted"})
    public List<ActivityDescAndTimeDto> getMostOftenNotCompletedActivity() {
        return activityService.getMostOftenNotCompletedActivity();
    }


    @GetMapping("/categories/{categoryId}")
    public List<ActivityDto> getActivitiesByCategory(@PathVariable UUID categoryId) {
        return activityService.getActivitiesByCategory(categoryId);
    }

    @PostMapping
    public NewActivityDto createNewActivity(@Valid @RequestBody NewActivityDto newActivity) {
        return activityService.saveNewActivity(newActivity);
    }

    @PatchMapping("/{id}")
    public ActivityDto toggleCompleted(@PathVariable UUID id) {
        return activityService.toggleCompleted(id);
    }

    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable UUID id) {
        activityService.deleteActivity(id);
    }

}
