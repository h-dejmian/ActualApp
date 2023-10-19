package com.example.ActualApp.repository;

import com.example.ActualApp.controller.dto.ActivityCategoryDto;
import com.example.ActualApp.repository.entity.ActivityCategory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActivityCategoryRepository extends JpaRepository<ActivityCategory, UUID> {

    Optional<ActivityCategory> findByName(String name);

    @Query("SELECT ac.name, SUM(a.timeSpentInMinutes) FROM ActivityCategory ac " +
            "JOIN Activity a ON ac.id = a.category.id " +
            "GROUP BY ac.name")
    List<List<Object>> getCategoriesWithTimeSpent();
}
