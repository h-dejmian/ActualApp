package com.example.ActualApp.repository;

import com.example.ActualApp.repository.entity.Category;
import com.example.ActualApp.repository.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @Override
    @Query("SELECT ac FROM Category ac FULL OUTER JOIN FETCH ac.activities")
    List<Category> findAll();

    @Query("SELECT ac FROM Category ac FULL OUTER JOIN FETCH ac.activities WHERE ac.categoryType = :type")
    List<Category> findAllByCategoryType(@Param("type") CategoryType type);

    Optional<Category> findByName(String name);

    @Query("SELECT ac.name, SUM(a.timeSpentInMinutes) as sum FROM Category ac " +
            "JOIN Activity a ON ac.id = a.category.id " +
            "GROUP BY ac.name " +
            "ORDER BY sum DESC")
    List<List<Object>> getCategoriesWithTimeSpent();
}
