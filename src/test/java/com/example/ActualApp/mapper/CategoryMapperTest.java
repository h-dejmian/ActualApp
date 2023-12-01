package com.example.ActualApp.mapper;

import com.example.ActualApp.auth.user.User;
import com.example.ActualApp.controller.dto.CategoryDto;
import com.example.ActualApp.controller.dto.NameAndCountDto;
import com.example.ActualApp.controller.dto.NewCategoryDto;
import com.example.ActualApp.repository.entity.Activity;
import com.example.ActualApp.repository.entity.Category;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class CategoryMapperTest {
    private final CategoryMapper categoryMapper = new CategoryMapper();

    @Test
    void shouldMapActivityCategoryToDto() {
        //Given
        Category category = Instancio.create(Category.class);

        //When
        CategoryDto actual = categoryMapper.mapActivityCategoryToDto(category);

        //Then
        Assertions.assertThat(actual.id()).isEqualTo(category.getId());
        Assertions.assertThat(actual.name()).isEqualTo(category.getName());
        Assertions.assertThat(actual.priority()).isEqualTo(category.getPriority());
        Assertions.assertThat(actual.activitiesNumber()).isEqualTo(category.getActivities().size());
    }

    @Test
    void shouldMapNewActivityCategoryDtoToEntity() {
        //Given
        NewCategoryDto newCategoryDto = Instancio.create(NewCategoryDto.class);
        User user = Instancio.create(User.class);

        //When
        Category actual = categoryMapper.mapNewActivityCategoryDtoToCategory(newCategoryDto, user);

        //Then
        Assertions.assertThat(actual.getName()).isEqualTo(newCategoryDto.name());
    }

    @Test
    void shouldMapQueryResultToNameAndCountDto() {
        //Given
        List<Object> innerList1 = new ArrayList<>();
        List<Object> innerList2 = new ArrayList<>();

        innerList1.add(("testCategory1"));
        innerList1.add((50L));

        innerList2.add(("testCategory2"));
        innerList2.add((60L));

        List<List<Object>> testList = new ArrayList<>(List.of(innerList1, innerList2));

        //When
        List<NameAndCountDto> nameAndCountList = categoryMapper.mapToNameAndCountDto(testList);

        //Then
        Assertions.assertThat(nameAndCountList.get(0).description()).isEqualTo("testCategory1");
        Assertions.assertThat(nameAndCountList.get(0).timeSpentInMinutes()).isEqualTo(50);
        Assertions.assertThat(nameAndCountList.get(1).description()).isEqualTo("testCategory2");
        Assertions.assertThat(nameAndCountList.get(1).timeSpentInMinutes()).isEqualTo(60);

    }
}