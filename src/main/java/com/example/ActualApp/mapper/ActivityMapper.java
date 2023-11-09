package com.example.ActualApp.mapper;

import com.example.ActualApp.auth.user.User;
import com.example.ActualApp.controller.dto.NameAndCountDto;
import com.example.ActualApp.controller.dto.ActivityDto;
import com.example.ActualApp.controller.dto.NewActivityDto;
import com.example.ActualApp.controller.dto.PlannedActivityDto;
import com.example.ActualApp.repository.entity.Activity;
import com.example.ActualApp.repository.entity.Category;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
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

    public PlannedActivityDto mapPlannedActivityToDto(Activity entity) {
        return new PlannedActivityDto(
                entity.getId(),
                entity.getDescription(),
                entity.getTimeSpentInMinutes(),
                entity.getDate(),
                entity.isCompleted(),
                entity.getCategory().getName(),
                entity.getStartTime(),
                entity.getEndTime()
        );
    }

    public Activity mapNewActivityDtoToEntity(NewActivityDto activity, Category category, User user) {
        return new Activity(
                activity.description(),
                activity.timeSpentInMinutes(),
                activity.date(),
                activity.completed(),
                category,
                user,
                activity.startTime(),
                activity.endTime()
        );
    }

    public List<NameAndCountDto> mapToNameAndCountDto(List<List<Object>> descAndTime) {
        return descAndTime.stream()
                .map(li -> new NameAndCountDto((String)li.get(0), (long)li.get(1)))
                .toList();
    }

//    private LocalTime parseTime(String time) {
//        LocalTime t = LocalTime.parse(time);
//        return t;
//    }

}
