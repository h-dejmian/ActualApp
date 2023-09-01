package com.example.ActualApp.repository;

import com.example.ActualApp.repository.entity.Activity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {

    @Query("SELECT a FROM Activity a")
    List<Activity> findAllBy(Pageable pageable);

    @Query("SELECT a.description, SUM(a.timeSpentInMinutes) FROM Activity a GROUP BY a.description")
    List<List<Object>> getActivitiesByTime();
}
