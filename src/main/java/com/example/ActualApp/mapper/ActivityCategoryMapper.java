package com.example.ActualApp.mapper;

import com.example.ActualApp.controller.dto.ActivityCategoryDto;
import com.example.ActualApp.controller.dto.NameAndCountDto;
import com.example.ActualApp.controller.dto.NewActivityCategoryDto;
import com.example.ActualApp.repository.entity.Activity;
import com.example.ActualApp.repository.entity.ActivityCategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActivityCategoryMapper {
    private static final int DEFAULT_PRIORITY = 3;

    public ActivityCategoryDto mapActivityCategoryToDto(ActivityCategory category) {
        return new ActivityCategoryDto(
                category.getId(),
                category.getName(),
                category.getPriority(),
                category.getActivities().size(),
                getActivitiesTimeSum(category)
        );
    }

    private static long getActivitiesTimeSum(ActivityCategory category) {
        return category.getActivities().stream().map(Activity::getTimeSpentInMinutes).mapToLong(Long::longValue).sum();
    }

    public ActivityCategory mapNewActivityCategoryDtoToCategory(NewActivityCategoryDto category) {
        return new ActivityCategory(
                category.name(),
                DEFAULT_PRIORITY
        );
    }

    public List<NameAndCountDto> mapToNameAndCountDto(List<List<Object>> descAndTime) {
        return descAndTime.stream()
                .map(li -> new NameAndCountDto((String)li.get(0), (long)li.get(1)))
                .toList();
    }

}
