package com.example.ActualApp.mapper;

import com.example.ActualApp.auth.user.User;
import com.example.ActualApp.controller.dto.CategoryDto;
import com.example.ActualApp.controller.dto.NameAndCountDto;
import com.example.ActualApp.controller.dto.NewCategoryDto;
import com.example.ActualApp.repository.entity.Activity;
import com.example.ActualApp.repository.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {

    public CategoryDto mapActivityCategoryToDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getPriority(),
                category.getActivities().size(),
                getActivitiesTimeSum(category)
        );
    }

    public Category mapNewActivityCategoryDtoToCategory(NewCategoryDto category, User user) {
        return new Category(
                category.name(),
                category.priority(),
                user,
                category.categoryType()
        );
    }

    public List<NameAndCountDto> mapToNameAndCountDto(List<List<Object>> descAndTime) {
        return descAndTime.stream()
                .map(li -> new NameAndCountDto((String)li.get(0), (long)li.get(1)))
                .toList();
    }

    private static long getActivitiesTimeSum(Category category) {
        return category.getActivities().stream().map(Activity::getTimeSpentInMinutes).mapToLong(Long::longValue).sum();
    }

}
