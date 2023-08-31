package com.example.ActualApp.mapper;

import com.example.ActualApp.controller.dto.ActivityDto;
import com.example.ActualApp.controller.dto.NewActivityDto;
import com.example.ActualApp.repository.entity.Activity;
import com.example.ActualApp.repository.entity.ActivityCategory;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {
    public ActivityDto mapActivityToDto(Activity entity) {
        return new ActivityDto(
                entity.getId(),
                entity.getDescription(),
                entity.getTimeSpentInMinutes(),
                entity.getDate(),
                entity.isCompleted()
        );
    }

    public Activity mapNewActivityDtoToEntity(NewActivityDto activity, ActivityCategory category) {
        return new Activity(
                activity.description(),
                activity.timeSpentInMinutes(),
                activity.date(),
                activity.completed(),
                category
        );

    }
}
