package com.example.ActualApp.repository;

import com.example.ActualApp.repository.entity.Category;
import com.example.ActualApp.repository.entity.CategoryType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldReturnAllCategories() {
        //When
        List<Category> actual = categoryRepository.findAll();

        //Then
        Assertions.assertThat(actual.size()).isEqualTo(1);
    }

    @Test
    void shouldReturnAllCategoriesByTypeAndUserId() {
        //When
        List<Category> actual = categoryRepository.findAllByCategoryTypeAndUserId(CategoryType.REGULAR, UUID.fromString("2f85b8fe-2888-4afb-b022-3d34ee604192"));

        //Then
        Assertions.assertThat(actual.size()).isEqualTo(1);
    }

    @Test
    void shouldReturnCategoryByNameAndType() {
        //When
        Category actual = categoryRepository.findByNameAndCategoryType("Rozrywka", CategoryType.REGULAR)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //Then
        Assertions.assertThat(actual.getName()).isEqualTo("Rozrywka");
        Assertions.assertThat(actual.getCategoryType()).isEqualTo(CategoryType.REGULAR);
    }

    @Test
    void shouldReturnCategoriesWithTimeSpent() {
        //When
        List<List<Object>> queryResult = categoryRepository.getCategoriesWithTimeSpent();
        List<Object> singleRow = queryResult.get(0);

        //Then
        Assertions.assertThat(singleRow.get(0)).isEqualTo("Rozrywka");
        Assertions.assertThat(singleRow.get(1)).isEqualTo(300L);
    }

    @Test
    void shouldReturnCategoriesWithTimeSpentByMonth() {
        //Given
        UUID userId = UUID.fromString("2f85b8fe-2888-4afb-b022-3d34ee604192");
        int month = 9;

        //When
        List<List<Object>> queryResult = categoryRepository.getCategoriesWithTimeSpentByMonth(month, userId);
        List<Object> singleRow = queryResult.get(0);

        //Then
        Assertions.assertThat(singleRow.get(0)).isEqualTo("Rozrywka");
        Assertions.assertThat(singleRow.get(1)).isEqualTo(300L);
    }
}