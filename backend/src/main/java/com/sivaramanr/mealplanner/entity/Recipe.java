package com.sivaramanr.mealplanner.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Recipe {

    @Id
    @Column(length = 36, nullable = false)
    private String id;

    private String groupId;

    @Column(nullable = false)
    private String name;

    private Float portion;

    private String uom;

    @Column(length = 1000)
    private String description;

    @Column(length = 10)
    private String foodType;

}
