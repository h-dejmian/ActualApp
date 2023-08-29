package com.example.ActualApp.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Activity {
    private static final int MINUTES_IN_A_DAY = 1440;

    public Activity(String description, int timeSpentInMinutes, @NotNull LocalDate date, boolean completed) {
        this.description = description;
        this.timeSpentInMinutes = timeSpentInMinutes;
        this.date = date;
        this.completed = completed;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank
    private String description;
    @Range(min = 0, max = MINUTES_IN_A_DAY, message = "Time spent should not be less than 0 and greater than 1440")
    private int timeSpentInMinutes;
    @ManyToOne
    private ActivityCategory category;
    @NotNull
    private LocalDate date;
    private boolean completed;
}
