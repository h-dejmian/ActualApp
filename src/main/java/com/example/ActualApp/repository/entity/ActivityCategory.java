package com.example.ActualApp.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ActivityCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank
    private String name;
    @Range(min = 1, max = 7, message = "Priority should be number between 1 and 7")
    private int priority;
    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<Activity> activities = new ArrayList<>();

    public ActivityCategory(String name, int priority, List<Activity> activities) {
        this.name = name;
        this.priority = priority;
        this.activities = activities;
    }
}
