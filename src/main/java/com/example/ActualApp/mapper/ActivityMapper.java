package com.example.ActualApp.mapper;

import com.example.ActualApp.auth.user.User;
import com.example.ActualApp.controller.dto.NameAndCountDto;
import com.example.ActualApp.controller.dto.ActivityDto;
import com.example.ActualApp.controller.dto.NewActivityDto;
import com.example.ActualApp.repository.entity.Activity;
import com.example.ActualApp.repository.entity.ActivityCategory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActivityMapper {
    public ActivityDto mapActivityToDto(Activity entity) {
        return new ActivityDto(
                entity.getId(),
                entity.getDescription(),
                entity.getTimeSpentInMinutes(),
                entity.getDate(),
                entity.isCompleted(),
                entity.getCategory().getName()
        );
    }

    public Activity mapNewActivityDtoToEntity(NewActivityDto activity, ActivityCategory category, User user) {
        return new Activity(
                activity.description(),
                activity.timeSpentInMinutes(),
                activity.date(),
                activity.completed(),
                category,
                user
        );
    }

    public List<NameAndCountDto> mapToNameAndCountDto(List<List<Object>> descAndTime) {
        return descAndTime.stream()
                .map(li -> new NameAndCountDto((String)li.get(0), (long)li.get(1)))
                .toList();
    }
}
