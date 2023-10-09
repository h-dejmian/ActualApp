package com.example.ActualApp.controller;

import com.example.ActualApp.controller.dto.ActivityDto;
import com.example.ActualApp.controller.dto.DescriptionDto;
import com.example.ActualApp.controller.dto.NameAndCountDto;
import com.example.ActualApp.controller.dto.NewActivityDto;
import com.example.ActualApp.service.ActivityService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.ActualApp.auth.config.SpringSecurityConfig.ACTIVITIES_READ;
import static com.example.ActualApp.auth.config.SpringSecurityConfig.ACTIVITIES_WRITE;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {
    private final ActivityService activityService;


    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @RolesAllowed(ACTIVITIES_READ)
    @GetMapping
    public List<ActivityDto> getAllActivities() {
        return activityService.getAllActivities();
    }

    @RolesAllowed(ACTIVITIES_READ)
    @GetMapping(params = {"groupByTime"})
    public List<NameAndCountDto> getActivitiesByTime() {
         return activityService.getActivitiesByTimeSpent();
    }

    @RolesAllowed(ACTIVITIES_READ)
    @GetMapping(params = {"mostOftenNotCompleted"})
    public List<NameAndCountDto> getMostOftenNotCompletedActivity() {
        return activityService.getMostOftenNotCompletedActivity();
    }

    @RolesAllowed(ACTIVITIES_READ)
    @GetMapping(params = {"date"})
    public List<ActivityDto> getActivitiesByDate(@RequestParam String date) {
        return activityService.getActivitiesByDate(date);
    }

    @RolesAllowed(ACTIVITIES_READ)
    @GetMapping(params = {"sort"})
    public List<ActivityDto> getAllActivitiesSorted(Pageable pageable) {
        return activityService.getAllActivities(pageable);
    }

    @RolesAllowed(ACTIVITIES_READ)
    @GetMapping("/{id}")
    public ActivityDto getActivityById(@PathVariable UUID id) {
        return activityService.getActivityById(id);
    }

    @RolesAllowed(ACTIVITIES_READ)
    @GetMapping("/categories/{categoryId}")
    public List<ActivityDto> getActivitiesByCategory(@PathVariable UUID categoryId) {
        return activityService.getActivitiesByCategory(categoryId);
    }

    @RolesAllowed(ACTIVITIES_WRITE)
    @PostMapping
    public ActivityDto createNewActivity(@Valid @RequestBody NewActivityDto newActivity) {
        return activityService.saveNewActivity(newActivity);
    }

    @RolesAllowed(ACTIVITIES_WRITE)
    @PatchMapping("/{id}")
    public ActivityDto toggleCompleted(@PathVariable UUID id) {
        return activityService.toggleCompleted(id);
    }

    @RolesAllowed(ACTIVITIES_WRITE)
    @PatchMapping(value = "/{id}", params = {"description"})
    public ActivityDto updateDescription(@PathVariable UUID id, @RequestBody DescriptionDto description) {
        return activityService.updateDescription(id, description);
    }

    @RolesAllowed(ACTIVITIES_WRITE)
    @PutMapping(value = "/{id}")
    public ActivityDto updateActivity(@PathVariable UUID id, @RequestBody ActivityDto activity) {
        return activityService.updateActivity(id, activity);
    }

    @RolesAllowed(ACTIVITIES_WRITE)
    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable UUID id) {
        activityService.deleteActivity(id);
    }

}
