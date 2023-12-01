package com.example.ActualApp.controller;

import com.example.ActualApp.controller.dto.ActivityDto;
import com.example.ActualApp.controller.dto.NameAndCountDto;
import com.example.ActualApp.controller.dto.NewActivityDto;
import com.example.ActualApp.controller.dto.PlannedActivityDto;
import com.example.ActualApp.service.ActivityService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.example.ActualApp.auth.config.SpringSecurityConfig.ADMIN;
import static com.example.ActualApp.auth.config.SpringSecurityConfig.USER;

@CrossOrigin(originPatterns = "http://localhost:3000/*")
@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {
    private final ActivityService activityService;


    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @RolesAllowed({ADMIN, USER})
    @GetMapping
    public List<ActivityDto> getAllActivities() {
        return activityService.getAllActivities();
    }

    @RolesAllowed({ADMIN, USER})
    @GetMapping(params = {"userId", "date"})
    public List<ActivityDto> getActivitiesByDate(@RequestParam UUID userId, @RequestParam String date) {
        return activityService.getActivitiesByDate(date, userId);
    }

    @RolesAllowed({ADMIN, USER})
    @GetMapping(params={"planned", "userId", "date"})
    public List<PlannedActivityDto> getAllActivitiesWithTimeRange(@RequestParam UUID userId, @RequestParam LocalDate date) {
        return activityService.getActivitiesWithTimeRangeByDate(date, userId);
    }

    @RolesAllowed({ADMIN, USER})
    @GetMapping(params = {"groupByTime", "userId", "month"})
    public List<NameAndCountDto> getActivitiesByTime(@RequestParam UUID userId, @RequestParam Integer month) {
        if(month >= 0) {
            return activityService.getActivitiesByTimeSpentInMonth(userId, month);
        }
        else {
            return activityService.getActivitiesByTimeSpent(userId);
        }
    }

    @RolesAllowed({ADMIN, USER})
    @GetMapping(params = {"mostOftenNotCompleted"})
    public List<NameAndCountDto> getMostOftenNotCompletedActivity() {
        return activityService.getMostOftenNotCompletedActivity();
    }

    @RolesAllowed({ADMIN, USER})
    @GetMapping(params = {"sort"})
    public List<ActivityDto> getAllActivitiesSorted(Pageable pageable) {
        return activityService.getAllActivities(pageable);
    }

    @RolesAllowed({ADMIN, USER})
    @GetMapping("/{id}")
    public ActivityDto getActivityById(@PathVariable UUID id) {
        return activityService.getActivityById(id);
    }

    @RolesAllowed({ADMIN, USER})
    @GetMapping("/categories/{categoryId}")
    public List<ActivityDto> getActivitiesByCategory(@PathVariable UUID categoryId) {
        return activityService.getActivitiesByCategory(categoryId);
    }

    @RolesAllowed({ADMIN, USER})
    @PostMapping(params={"type"})
    public ActivityDto createNewActivity(@Valid @RequestBody NewActivityDto newActivity, @RequestParam String type) {
        return activityService.saveNewActivity(newActivity, type);
    }

    @RolesAllowed({ADMIN, USER})
    @PatchMapping("/{id}")
    public ActivityDto toggleCompleted(@PathVariable UUID id) {
        return activityService.toggleCompleted(id);
    }


    @RolesAllowed({ADMIN, USER})
    @PutMapping(value = "/{id}", params={"type"})
    public ActivityDto updateActivity(@PathVariable UUID id, @RequestBody ActivityDto activity, @RequestParam String type) {
        return activityService.updateActivity(id, activity, type);
    }

    @RolesAllowed({ADMIN, USER})
    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable UUID id) {
        activityService.deleteActivity(id);
    }

}
