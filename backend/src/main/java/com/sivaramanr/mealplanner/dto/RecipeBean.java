package com.sivaramanr.mealplanner.dto;

import lombok.Data;

@Data
public class RecipeBean {

    private String id;

    private String groupId;

    private String name;

    private Float portion;

    private String uom;

    private String description;

    private String foodType;

}
