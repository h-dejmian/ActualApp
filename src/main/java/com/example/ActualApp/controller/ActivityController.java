package com.example.ActualApp.controller;

import com.example.ActualApp.controller.dto.DescriptionDto;
import com.example.ActualApp.controller.dto.NameAndCountDto;
import com.example.ActualApp.controller.dto.ActivityDto;
import com.example.ActualApp.controller.dto.NewActivityDto;
import com.example.ActualApp.repository.ActivityCategoryRepository;
import com.example.ActualApp.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {
    private final ActivityService activityService;


    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public List<ActivityDto> getAllActivities() {
        return activityService.getAllActivities();
    }

    @GetMapping(params = {"groupByTime"})
    public List<NameAndCountDto> getActivitiesByTime() {
         return activityService.getActivitiesByTimeSpent();
    }

    @GetMapping(params = {"mostOftenNotCompleted"})
    public List<NameAndCountDto> getMostOftenNotCompletedActivity() {
        return activityService.getMostOftenNotCompletedActivity();
    }

    @GetMapping(params = {"date"})
    public List<ActivityDto> getActivitiesByDate(@RequestParam String date) {
        return activityService.getActivitiesByDate(date);
    }

    @GetMapping(params = {"sort"})
    public List<ActivityDto> getAllActivitiesSorted(Pageable pageable) {
        return activityService.getAllActivities(pageable);
    }

    @GetMapping("/{id}")
    public ActivityDto getActivityById(@PathVariable UUID id) {
        return activityService.getActivityById(id);
    }

    @GetMapping("/categories/{categoryId}")
    public List<ActivityDto> getActivitiesByCategory(@PathVariable UUID categoryId) {
        return activityService.getActivitiesByCategory(categoryId);
    }

    @PostMapping
    public ActivityDto createNewActivity(@Valid @RequestBody NewActivityDto newActivity) {
        return activityService.saveNewActivity(newActivity);
    }

    @PatchMapping("/{id}")
    public ActivityDto toggleCompleted(@PathVariable UUID id) {
        return activityService.toggleCompleted(id);
    }

    @PatchMapping(value = "/{id}", params = {"description"})
    public ActivityDto updateDescription(@PathVariable UUID id, @RequestBody DescriptionDto description) {
        return activityService.updateDescription(id, description);
    }

    @PutMapping(value = "/{id}")
    public ActivityDto updateActivity(@PathVariable UUID id, @RequestBody ActivityDto activity) {
        return activityService.updateActivity(id, activity);
    }

    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable UUID id) {
        activityService.deleteActivity(id);
    }

}
