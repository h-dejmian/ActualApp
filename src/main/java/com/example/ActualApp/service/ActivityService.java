package com.example.ActualApp.service;

import com.example.ActualApp.controller.dto.ActivityDto;
import com.example.ActualApp.controller.dto.NewActivityDto;
import com.example.ActualApp.mapper.ActivityMapper;
import com.example.ActualApp.repository.ActivityRepository;
import com.example.ActualApp.repository.entity.Activity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    public ActivityService(ActivityRepository activityRepository, ActivityMapper activityMapper) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
    }

    public List<ActivityDto> getAllActivities() {
        return activityRepository.findAll().stream()
                .map(activityMapper::mapActivityToDto)
                .toList();
    }

    public NewActivityDto saveNewActivity(NewActivityDto newActivity) {
        Activity activity = activityRepository.save(activityMapper.mapNewActivityDtoToEntity(newActivity));
        return newActivity;
    }
}
