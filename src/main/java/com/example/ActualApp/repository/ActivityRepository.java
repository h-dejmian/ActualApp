package com.example.ActualApp.repository;

import com.example.ActualApp.repository.entity.Activity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {

    @Query("SELECT a FROM Activity a")
    List<Activity> findAllBy(Pageable pageable);
    @Query("SELECT a FROM Activity a WHERE a.date = :date " +
                                    "AND a.user.id = :userId " +
                                    "AND a.startTime = null " +
                                    "AND a.endTime = null " +
                                    "AND a.category.categoryType = 'REGULAR'")
    List<Activity> findAllRegularByDateAndUserId(@Param("date") LocalDate date, @Param("userId") UUID userId);

    @Query("SELECT a FROM Activity a WHERE a.startTime IS NOT null " +
                                    "AND a.endTime IS NOT null " +
                                    "AND a.date = :date " +
                                    "AND a.user.id = :userId " +
                                    "AND a.category.categoryType = 'REGULAR' " +
                                    "ORDER BY a.startTime")
    List<Activity> findAllWithTimeRangeByDate(@Param("date") LocalDate date, @Param("userId") UUID userId);

    @Query("SELECT a.description, SUM(a.timeSpentInMinutes) as sum FROM Activity a " +
            "WHERE a.category.categoryType = 'REGULAR' AND a.user.id = :userId " +
            "GROUP BY a.description " +
            "ORDER BY sum DESC")
    List<List<Object>> getActivitiesByTimeAndUserId(UUID userId);

    @Query("SELECT a.description, COUNT(1) FROM Activity a " +
            "WHERE a.completed=false " +
            "GROUP BY a.description " +
            "ORDER BY COUNT(1) DESC " +
            "LIMIT 1 ")
    List<List<Object>> getMostOftenNotCompletedActivity();
}
