package com.example.ActualApp.service;

import com.example.ActualApp.controller.dto.ActivityDto;
import com.example.ActualApp.controller.dto.NewActivityDto;
import com.example.ActualApp.mapper.ActivityMapper;
import com.example.ActualApp.repository.ActivityRepository;
import com.example.ActualApp.repository.entity.Activity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

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
        activityRepository.save(activityMapper.mapNewActivityDtoToEntity(newActivity));
        return newActivity;
    }

    public ActivityDto getActivityById(UUID id) {
        return activityRepository.findById(id)
                .map(activityMapper::mapActivityToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
