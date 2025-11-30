package com.sivaramanr.mealplanner.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FoodSession {

    @Id
    @Column(length = 36, nullable = false)
    private String id;

    @Column(nullable = false)
    private String name; // Breakfast, Lunch, Dinner, Snack

    @Column(length = 1000)
    private String description; // Light meal in the morning

    @Column(length = 1000)
    private String timing; // 11:00 AM - 12:00 PM

}
