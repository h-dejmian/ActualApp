package com.example.ActualApp.repository.entity;

import com.example.ActualApp.auth.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank
    @Column(unique = true)
    @Setter
    private String name;
    @Range(min = 1, max = 7, message = "Priority should be number between 1 and 7")
    @Setter
    private int priority;
    @JsonManagedReference
    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<Activity> activities = new ArrayList<>();
    @JsonBackReference
    @ManyToOne
    private User user;

    public Category(String name, int priority, User user) {
        this.name = name;
        this.priority = priority;
        this.user = user;
    }
}
