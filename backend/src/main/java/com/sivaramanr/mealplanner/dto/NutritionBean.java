package com.sivaramanr.mealplanner.dto;

import lombok.Data;

@Data
public class NutritionBean {

    private String id;

    private String category;

    private String name;

    private String description;

    private String uom;

    private Float value;

}
