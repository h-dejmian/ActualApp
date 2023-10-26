package com.example.ActualApp.repository;

import com.example.ActualApp.controller.dto.ActivityCategoryDto;
import com.example.ActualApp.repository.entity.ActivityCategory;
import jakarta.persistence.JoinColumn;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActivityCategoryRepository extends JpaRepository<ActivityCategory, UUID> {
    @Override
    @Query("SELECT ac FROM ActivityCategory ac FULL OUTER JOIN FETCH ac.activities")
    List<ActivityCategory> findAll();

    Optional<ActivityCategory> findByName(String name);

    @Query("SELECT ac.name, SUM(a.timeSpentInMinutes) as sum FROM ActivityCategory ac " +
            "JOIN Activity a ON ac.id = a.category.id " +
            "GROUP BY ac.name " +
            "ORDER BY sum DESC")
    List<List<Object>> getCategoriesWithTimeSpent();
}
