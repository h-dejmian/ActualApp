package com.example.ActualApp.repository;

import com.example.ActualApp.repository.entity.ActivityCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActivityCategoryRepository extends JpaRepository<ActivityCategory, UUID> {
}
