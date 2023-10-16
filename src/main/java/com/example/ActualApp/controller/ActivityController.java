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

import static com.example.ActualApp.auth.config.SpringSecurityConfig.USER;
import static com.example.ActualApp.auth.config.SpringSecurityConfig.ADMIN;

@CrossOrigin(originPatterns = "http://localhost:3000/*")
@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {
    private final ActivityService activityService;


    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @RolesAllowed(ADMIN)
    @GetMapping
    public List<ActivityDto> getAllActivities() {
        return activityService.getAllActivities();
    }

    @RolesAllowed(ADMIN)
    @GetMapping(params = {"groupByTime"})
    public List<NameAndCountDto> getActivitiesByTime() {
         return activityService.getActivitiesByTimeSpent();
    }

    @RolesAllowed(ADMIN)
    @GetMapping(params = {"mostOftenNotCompleted"})
    public List<NameAndCountDto> getMostOftenNotCompletedActivity() {
        return activityService.getMostOftenNotCompletedActivity();
    }

    @RolesAllowed(ADMIN)
    @GetMapping(params = {"date"})
    public List<ActivityDto> getActivitiesByDate(@RequestParam String date) {
        return activityService.getActivitiesByDate(date);
    }

    @RolesAllowed(ADMIN)
    @GetMapping(params = {"sort"})
    public List<ActivityDto> getAllActivitiesSorted(Pageable pageable) {
        return activityService.getAllActivities(pageable);
    }

    @RolesAllowed(ADMIN)
    @GetMapping("/{id}")
    public ActivityDto getActivityById(@PathVariable UUID id) {
        return activityService.getActivityById(id);
    }

    @RolesAllowed(ADMIN)
    @GetMapping("/categories/{categoryId}")
    public List<ActivityDto> getActivitiesByCategory(@PathVariable UUID categoryId) {
        return activityService.getActivitiesByCategory(categoryId);
    }

    @RolesAllowed(ADMIN)
    @PostMapping
    public ActivityDto createNewActivity(@Valid @RequestBody NewActivityDto newActivity) {
        return activityService.saveNewActivity(newActivity);
    }

    @RolesAllowed(ADMIN)
    @PatchMapping("/{id}")
    public ActivityDto toggleCompleted(@PathVariable UUID id) {
        return activityService.toggleCompleted(id);
    }

    @RolesAllowed(ADMIN)
    @PatchMapping(value = "/{id}", params = {"description"})
    public ActivityDto updateDescription(@PathVariable UUID id, @RequestBody DescriptionDto description) {
        return activityService.updateDescription(id, description);
    }

    @RolesAllowed(ADMIN)
    @PutMapping(value = "/{id}")
    public ActivityDto updateActivity(@PathVariable UUID id, @RequestBody ActivityDto activity) {
        return activityService.updateActivity(id, activity);
    }

    @RolesAllowed(ADMIN)
    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable UUID id) {
        activityService.deleteActivity(id);
    }

}
