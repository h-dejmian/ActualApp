package com.example.ActualApp.service;

import com.example.ActualApp.repository.ActivityRepository;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
}
