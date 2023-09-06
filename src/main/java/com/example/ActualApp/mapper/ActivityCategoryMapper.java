package com.example.ActualApp.mapper;

import com.example.ActualApp.controller.dto.ActivityCategoryDto;
import com.example.ActualApp.controller.dto.NameAndCountDto;
import com.example.ActualApp.controller.dto.NewActivityCategoryDto;
import com.example.ActualApp.repository.entity.ActivityCategory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActivityCategoryMapper {

    public ActivityCategoryDto mapActivityCategoryToDto(ActivityCategory category) {
        return new ActivityCategoryDto(
                category.getId(),
                category.getName(),
                category.getPriority(),
                category.getActivities()
        );
    }

    public ActivityCategory mapNewActivityCategoryDtoToCategory(NewActivityCategoryDto category) {
        return new ActivityCategory(
                category.name(),
                category.priority(),
                category.activities()
        );
    }

    public List<NameAndCountDto> mapToNameAndCountDto(List<List<Object>> descAndTime) {
        return descAndTime.stream()
                .map(li -> new NameAndCountDto((String)li.get(0), (long)li.get(1)))
                .toList();
    }

}
